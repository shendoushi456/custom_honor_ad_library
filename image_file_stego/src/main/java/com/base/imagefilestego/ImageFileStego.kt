package com.base.imagefilestego

import android.content.Context
import android.graphics.Bitmap
import com.base.imagefilestego.internal.Crypto
import com.base.imagefilestego.internal.HeaderCodec
import com.base.imagefilestego.internal.Images
import com.base.imagefilestego.internal.MemoryBuffer
import com.base.imagefilestego.internal.MemoryStorage
import com.base.imagefilestego.internal.PngOutput
import com.base.imagefilestego.internal.Positions
import com.base.imagefilestego.internal.Protocol
import com.base.imagefilestego.internal.TempLease
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.io.Closeable
import java.io.File
import java.io.IOException
import java.security.MessageDigest
import java.util.concurrent.Executor
import java.util.concurrent.atomic.AtomicBoolean

/** 无 UI 处理入口。只持有 Application Context，不请求权限或启动页面。 */
class ImageFileStego @JvmOverloads constructor(context: Context, val options: StegoOptions = StegoOptions()) {
    private val tempRoot = File(context.applicationContext.cacheDir, "image_file_stego")

    @JvmOverloads
    suspend fun inspect(image: StegoSource, fileName: String = "file.bin"): ImageInfo = operation { check ->
        Images.inspect(Images.bounds(image, false, check), options, fileName)
    }

    @JvmOverloads
    suspend fun embed(
        image: StegoSource,
        file: StegoSource,
        password: CharArray,
        progress: StegoProgressListener? = null
    ): StegoArtifact = withPassword(password) { secret ->
        operation { check ->
            val report = reporter(progress, check)
            report(StegoStage.INSPECTING, 0f)
            val bounds = Images.bounds(image, false, check)
            Images.requireBudget(bounds, options)
            val width = bounds.outputWidth
            val height = bounds.outputHeight
            val maxFile = Protocol.maxFileBytes(width, height, file.displayName)
            if (maxFile < 0 || (file.sizeBytes ?: 0) > maxFile) capacityError()
            report(StegoStage.READING, 0f)
            val content = readFile(file, maxFile, check)
            val originalDigest = MessageDigest.getInstance("SHA-256").digest(content)
            val payload = try { Protocol.pack(file.displayName, content) } finally { content.fill(0) }
            try {
                report(StegoStage.ENCRYPTING, 0f)
                HeaderCodec.create(payload.size + Protocol.TAG_SIZE, secret, width, height, check = check).use { opened ->
                    val header = opened.header
                    val keys = opened.keys
                    check()
                    val encrypted = try { Crypto.encrypt(payload, keys, header, width, height) } finally { payload.fill(0) }
                    try {
                        report(StegoStage.EMBEDDING, 0f)
                        val png = encode(image, bounds, header, encrypted, keys, report, check)
                        try {
                            report(StegoStage.VERIFYING, 0f)
                            // 先释放原 Bitmap，再解码 PNG 校验；复用本次密钥，无需重复执行 KDF。
                            val recovered = decode(
                                StegoSource.fromStream(png.fileName, png.sizeBytes) { png.openStream() },
                                secret, keys, report, check, verifying = true
                            )
                            recovered.use {
                                if (it.fileName != file.displayName || it.sha256 != hex(originalDigest)) {
                                    throw StegoException(StegoError.AUTHENTICATION_FAILED, "生成图片回读验证失败")
                                }
                            }
                            report(StegoStage.COMPLETE, 1f)
                            png
                        } catch (error: Throwable) {
                            closeAfterFailure(png, error)
                            throw error
                        }
                    } finally { encrypted.fill(0) }
                }
            } finally { payload.fill(0); originalDigest.fill(0) }
        }
    }

    @JvmOverloads
    suspend fun extract(image: StegoSource, password: CharArray, progress: StegoProgressListener? = null): StegoArtifact =
        withPassword(password) { secret ->
            operation { check ->
                val report = reporter(progress, check)
                val result = decode(image, secret, null, report, check, false)
                try { report(StegoStage.COMPLETE, 1f); result }
                catch (error: Throwable) { closeAfterFailure(result, error); throw error }
            }
        }

    suspend fun cleanupTemporaryFiles() = withContext(Dispatchers.IO) { TempLease.cleanup(tempRoot) }

    @JvmOverloads
    fun inspectAsync(image: StegoSource, callback: StegoCallback<ImageInfo>, executor: Executor = directExecutor): StegoTask =
        submit(callback, executor) { inspect(image) }

    @JvmOverloads
    fun inspectAsync(image: StegoSource, fileName: String, callback: StegoCallback<ImageInfo>, executor: Executor = directExecutor): StegoTask =
        submit(callback, executor) { inspect(image, fileName) }

    @JvmOverloads
    fun embedAsync(
        image: StegoSource, file: StegoSource, password: CharArray,
        callback: StegoCallback<StegoArtifact>, progress: StegoProgressListener? = null,
        executor: Executor = directExecutor
    ): StegoTask {
        val copy = password.copyOf()
        return submit(callback, executor, { copy.fill('\u0000') }) { embed(image, file, copy, progress) }
    }

    @JvmOverloads
    fun extractAsync(
        image: StegoSource, password: CharArray, callback: StegoCallback<StegoArtifact>,
        progress: StegoProgressListener? = null, executor: Executor = directExecutor
    ): StegoTask {
        val copy = password.copyOf()
        return submit(callback, executor, { copy.fill('\u0000') }) { extract(image, copy, progress) }
    }

    private fun encode(
        source: StegoSource, bounds: Images.Bounds, header: Protocol.Header, ciphertext: ByteArray,
        keys: Crypto.Keys, report: (StegoStage, Float) -> Unit, check: () -> Unit
    ): StegoArtifact {
        val memoryLimit = Images.budget(options)
        val bitmap = Images.decode(source, bounds, check)
        try {
            val plane = Images.readPlane(bitmap, true, check)
            Protocol.writeHeader(plane, header)
            Protocol.writeCipher(plane, ciphertext, Positions(keys.positions, header.nonce, bitmap.width, bitmap.height, header.version, check)) {
                report(StegoStage.EMBEDDING, it.toFloat() / ciphertext.size)
            }
            Images.writePlane(bitmap, plane, check)
            plane.clear()
            report(StegoStage.ENCODING, 0f)
            val memoryForOutput = (memoryLimit - Images.estimate(bounds, options) + options.memoryThresholdBytes).coerceAtLeast(0)
            PngOutput(tempRoot, options.memoryThresholdBytes, options.allowTemporaryFiles, memoryForOutput, check).use { output ->
                if (!bitmap.compress(Bitmap.CompressFormat.PNG, 100, output)) throw IOException("PNG 编码失败")
                val (storage, digest) = output.finish()
                try {
                    return StegoArtifact("hidden-${System.currentTimeMillis()}.png", "image/png", storage, digest)
                } catch (error: Throwable) {
                    closeAfterFailure(storage, error)
                    throw error
                }
            }
        } finally { bitmap.recycle() }
    }

    private fun decode(
        source: StegoSource, password: CharArray, existingKeys: Crypto.Keys?,
        report: (StegoStage, Float) -> Unit, check: () -> Unit, verifying: Boolean
    ): StegoArtifact {
        if (!verifying) report(StegoStage.INSPECTING, 0f)
        val bounds = Images.bounds(source, true, check)
        Images.requireBudget(bounds, options, extracting = true)
        val bitmap = Images.decode(source, bounds, check)
        val plane = try { Images.readPlane(bitmap, false, check) } finally { bitmap.recycle() }
        try {
            if (!verifying) report(StegoStage.DECRYPTING, 0f)
            HeaderCodec.read(plane, password, bounds.width, bounds.height, existingKeys, check).use { opened ->
                val header = opened.header
                val keys = opened.keys
                check()
                val ciphertext = Protocol.readCipher(plane, header.cipherSize,
                    Positions(keys.positions, header.nonce, bounds.width, bounds.height, header.version, check)) {
                    report(if (verifying) StegoStage.VERIFYING else StegoStage.EXTRACTING, it.toFloat() / header.cipherSize)
                }
                plane.clear()
                val plaintext = try {
                    Crypto.decrypt(ciphertext, keys, header, bounds.width, bounds.height)
                } finally { ciphertext.fill(0) }
                try {
                    check()
                    val payload = Protocol.unpack(plaintext)
                    try {
                        val digest = MessageDigest.getInstance("SHA-256").digest(payload.content)
                        check()
                        return StegoArtifact(payload.fileName, "application/octet-stream", MemoryStorage.own(payload.content), digest)
                    } catch (error: Throwable) { payload.content.fill(0); throw error }
                } finally { plaintext.fill(0) }
            }
        } finally { plane.clear() }
    }

    private suspend fun <T> operation(action: (() -> Unit) -> T): T {
        var result: T? = null
        try {
            return withContext(Dispatchers.Default) {
                operations.withLock {
                    val context = currentCoroutineContext()
                    val check = { context.ensureActive() }
                    check()
                    TempLease.cleanup(tempRoot)
                    action(check).also { result = it; check() }
                }
            }
        } catch (error: Throwable) {
            (result as? Closeable)?.let { closeAfterFailure(it, error) }
            if (error is CancellationException) throw error
            if (error is OutOfMemoryError) throw StegoException(StegoError.MEMORY_LIMIT, "当前内存不足，请选择较小图片", error)
            if (error is Exception) throw mapError(error)
            throw error
        }
    }

    private suspend fun <T> withPassword(password: CharArray, action: suspend (CharArray) -> T): T {
        if (password.isEmpty()) throw StegoException(StegoError.INVALID_INPUT, "密码不能为空")
        val copy = password.copyOf()
        return try { action(copy) } finally { copy.fill('\u0000') }
    }

    private fun reporter(listener: StegoProgressListener?, check: () -> Unit): (StegoStage, Float) -> Unit {
        var previousStage: StegoStage? = null
        var previousPercent = -1
        return { stage, fraction ->
            check()
            val percent = (fraction.coerceIn(0f, 1f) * 100).toInt()
            if (stage != previousStage || percent != previousPercent) {
                previousStage = stage
                previousPercent = percent
                listener?.onProgress(StegoProgress(stage, fraction.coerceIn(0f, 1f)))
            }
        }
    }

    private fun readFile(source: StegoSource, limit: Int, check: () -> Unit): ByteArray {
        val buffer = ByteArray(32 * 1024)
        try {
            MemoryBuffer().use { output ->
                source.openStream().use { input ->
                    while (true) {
                        check()
                        val count = input.read(buffer, 0, minOf(buffer.size.toLong(), limit.toLong() - output.size + 1).toInt())
                        if (count < 0) break
                        if (count == 0) {
                            val value = input.read()
                            if (value < 0) break
                            if (output.size == limit.toLong()) capacityError()
                            output.write(value)
                        } else {
                            if (output.size + count > limit) capacityError()
                            output.write(buffer, 0, count)
                        }
                    }
                }
                return output.transfer().use { storage ->
                    ByteArray(storage.size.toInt()).also { bytes ->
                        storage.open().use { input ->
                            var offset = 0
                            while (offset < bytes.size) offset += input.read(bytes, offset, bytes.size - offset)
                        }
                    }
                }
            }
        } finally { buffer.fill(0) }
    }

    private fun <T> submit(
        callback: StegoCallback<T>, executor: Executor, cleanup: () -> Unit = {}, action: suspend () -> T
    ): StegoTask {
        val cancelled = AtomicBoolean(false)
        val delivered = AtomicBoolean(false)
        val job = background.launch {
            var value: T? = null
            try {
                value = action()
                val success = value
                executor.execute {
                    if (delivered.compareAndSet(false, true)) {
                        if (cancelled.get()) {
                            (success as? Closeable)?.close()
                            callback.onCancelled()
                        } else {
                            try { callback.onSuccess(success) }
                            catch (error: Throwable) { (success as? Closeable)?.let { closeAfterFailure(it, error) } }
                        }
                    }
                }
            } catch (error: Exception) {
                (value as? Closeable)?.let { closeAfterFailure(it, error) }
                executor.execute {
                    if (delivered.compareAndSet(false, true)) {
                        if (error is CancellationException || cancelled.get()) callback.onCancelled()
                        else callback.onError(mapError(error))
                    }
                }
            }
        }
        // 即使任务在进入协程体前被取消，也释放密码副本并投递取消事件。
        job.invokeOnCompletion { error ->
            cleanup()
            if (error is CancellationException) executor.execute {
                if (delivered.compareAndSet(false, true)) callback.onCancelled()
            }
        }
        return StegoTask(job, cancelled)
    }

    companion object {
        private val operations = Mutex()
        private val background = CoroutineScope(SupervisorJob() + Dispatchers.Default)
        private val directExecutor = Executor { it.run() }
        private fun hex(bytes: ByteArray): String = bytes.joinToString("") { "%02x".format(it.toInt() and 255) }
        private fun capacityError(): Nothing = throw StegoException(StegoError.CAPACITY_EXCEEDED, "文件超过图片可用容量，请选择更小文件或更大图片")
        private fun mapError(error: Exception): StegoException = when (error) {
            is StegoException -> error
            is IOException, is SecurityException -> StegoException(StegoError.IO_ERROR, "文件读写失败或访问权限已失效", error)
            else -> StegoException(StegoError.INVALID_INPUT, "无法处理当前输入", error)
        }
        private fun closeAfterFailure(closeable: Closeable, error: Throwable) {
            try { closeable.close() } catch (cleanup: Throwable) { error.addSuppressed(cleanup) }
        }
    }
}

class StegoTask internal constructor(private val job: Job, private val cancelled: AtomicBoolean) {
    fun cancel() { cancelled.set(true); job.cancel() }
}
