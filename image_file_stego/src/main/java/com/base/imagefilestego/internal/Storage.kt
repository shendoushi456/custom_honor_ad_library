package com.base.imagefilestego.internal

import com.base.imagefilestego.StegoError
import com.base.imagefilestego.StegoException
import java.io.Closeable
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.io.RandomAccessFile
import java.nio.channels.FileLock
import java.nio.channels.OverlappingFileLockException
import java.security.MessageDigest
import java.util.UUID

internal interface Storage : Closeable {
    val size: Long
    fun open(): InputStream
}

/** 分块存储避免 ByteArrayOutputStream 扩容以及 toByteArray 带来的整份复制。 */
internal class MemoryStorage(private val chunks: List<ByteArray>, override val size: Long) : Storage {
    override fun open(): InputStream = object : InputStream() {
        private var position = 0L
        private var index = 0
        private var offset = 0
        override fun read(): Int {
            if (position >= size) return -1
            val value = chunks[index][offset++].toInt() and 255
            position++
            if (offset == chunks[index].size) { index++; offset = 0 }
            return value
        }
        override fun read(bytes: ByteArray, start: Int, length: Int): Int {
            require(start >= 0 && length >= 0 && start <= bytes.size - length)
            if (length == 0) return 0
            if (position >= size) return -1
            var remaining = minOf(length.toLong(), size - position).toInt()
            val result = remaining
            var target = start
            while (remaining > 0) {
                val count = minOf(remaining, chunks[index].size - offset)
                chunks[index].copyInto(bytes, target, offset, offset + count)
                position += count
                offset += count
                target += count
                remaining -= count
                if (offset == chunks[index].size) { index++; offset = 0 }
            }
            return result
        }
    }
    override fun close() = chunks.forEach { it.fill(0) }

    companion object {
        fun own(bytes: ByteArray): MemoryStorage = MemoryStorage(listOf(bytes), bytes.size.toLong())
    }
}

internal class MemoryBuffer : OutputStream() {
    private val chunks = ArrayList<ByteArray>()
    private var offset = 0
    var size = 0L
        private set

    override fun write(value: Int) = write(byteArrayOf(value.toByte()), 0, 1)
    override fun write(bytes: ByteArray, start: Int, length: Int) {
        require(start >= 0 && length >= 0 && start <= bytes.size - length)
        var source = start
        var remaining = length
        while (remaining > 0) {
            if (chunks.isEmpty() || offset == chunks.last().size) {
                chunks.add(ByteArray(64 * 1024))
                offset = 0
            }
            val count = minOf(remaining, chunks.last().size - offset)
            bytes.copyInto(chunks.last(), offset, source, source + count)
            size += count
            offset += count
            source += count
            remaining -= count
        }
    }

    fun transfer(): MemoryStorage {
        val storage = MemoryStorage(chunks.toList(), size)
        chunks.clear()
        size = 0
        offset = 0
        return storage
    }

    override fun close() {
        chunks.forEach { it.fill(0) }
        chunks.clear()
        size = 0
        offset = 0
    }
}

/** 仅管理本模块创建的目录，持有文件锁直至结果被释放。 */
internal class TempLease private constructor(
    private val directory: File,
    private val lockFile: RandomAccessFile,
    private val lock: FileLock
) : Closeable {
    val file = File(directory, "result.png")
    private var closed = false

    override fun close() {
        if (closed) return
        closed = true
        try {
            if (file.exists() && !file.delete()) throw IOException("无法删除临时结果，将在下次使用时重试")
        } finally {
            try { lock.release() } finally { lockFile.close() }
            File(directory, "lease.lock").delete()
            directory.delete()
        }
    }

    companion object {
        @Synchronized
        fun create(root: File): TempLease {
            if (!root.isDirectory && !root.mkdirs()) throw IOException("无法创建临时目录")
            // 发布目录前取得锁，避免另一个进程清理刚创建的活跃会话。
            val guard = RandomAccessFile(File(root, "registry.lock"), "rw")
            guard.use {
                it.channel.lock().use {
                    cleanupLocked(root)
                    val directory = File(root, "session-${UUID.randomUUID()}")
                    if (!directory.mkdir()) throw IOException("无法创建临时会话")
                    val handle = RandomAccessFile(File(directory, "lease.lock"), "rw")
                    try {
                        return TempLease(directory, handle, handle.channel.lock())
                    } catch (error: Throwable) {
                        handle.close()
                        File(directory, "lease.lock").delete()
                        directory.delete()
                        throw error
                    }
                }
            }
        }

        @Synchronized
        fun cleanup(root: File) {
            if (!root.isDirectory) return
            RandomAccessFile(File(root, "registry.lock"), "rw").use { guard ->
                guard.channel.lock().use { cleanupLocked(root) }
            }
        }

        private fun cleanupLocked(root: File) {
            root.listFiles()?.filter { it.name.startsWith("session-") && it.isDirectory && it.canonicalFile.parentFile == root.canonicalFile }
                ?.forEach { directory ->
                    val lockPath = File(directory, "lease.lock")
                    try {
                        RandomAccessFile(lockPath, "rw").use { handle ->
                            val lock = try { handle.channel.tryLock() } catch (_: OverlappingFileLockException) { null }
                            if (lock != null) {
                                lock.use {
                                    // 固定文件名清理，不递归遍历目录或跟随外部路径。
                                    File(directory, "result.png").delete()
                                }
                                lockPath.delete()
                                directory.delete()
                            }
                        }
                    } catch (_: IOException) {
                        // 缓存可能被系统同时清理，下一次初始化仍会重试残留目录。
                    }
                }
        }
    }
}

private class FileStorage(private val lease: TempLease, override val size: Long) : Storage {
    override fun open(): InputStream = FileInputStream(lease.file)
    override fun close() = lease.close()
}

/** 仅 PNG 编码结果可溢写磁盘，文件明文不经过此类。 */
internal class PngOutput(
    private val root: File,
    private val threshold: Int,
    private val allowTemp: Boolean,
    private val maxMemoryBytes: Long,
    private val check: () -> Unit
) : OutputStream() {
    private var memory = MemoryBuffer()
    private var lease: TempLease? = null
    private var file: OutputStream? = null
    private val digest = MessageDigest.getInstance("SHA-256")
    private var finished = false
    private var size = 0L

    override fun write(value: Int) = write(byteArrayOf(value.toByte()), 0, 1)
    override fun write(bytes: ByteArray, offset: Int, length: Int) {
        check()
        kotlin.check(!finished)
        if (size + length > 128L * 1024 * 1024) throw StegoException(StegoError.CAPACITY_EXCEEDED, "PNG 结果超过 128 MiB 上限")
        if (file == null && size + length > threshold && allowTemp) {
            lease = TempLease.create(root)
            file = FileOutputStream(lease!!.file)
            memory.transfer().use { buffered -> buffered.open().use { it.copyTo(file!!) } }
        }
        if (file == null && size + length > maxMemoryBytes) {
            throw StegoException(StegoError.MEMORY_LIMIT, "PNG 结果超过内存预算，可允许使用临时文件或选择更小的图片")
        }
        (file ?: memory).write(bytes, offset, length)
        digest.update(bytes, offset, length)
        size += length
    }

    fun finish(): Pair<Storage, ByteArray> {
        check()
        kotlin.check(!finished)
        file?.close()
        val storage = lease?.let { FileStorage(it, size) } ?: memory.transfer()
        lease = null
        finished = true
        return storage to digest.digest()
    }

    override fun close() {
        try { file?.close() } finally {
            memory.close()
            lease?.close()
            lease = null
            finished = true
        }
    }
}
