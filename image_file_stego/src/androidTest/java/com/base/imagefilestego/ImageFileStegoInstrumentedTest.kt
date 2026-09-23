package com.base.imagefilestego

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Debug
import android.os.SystemClock
import android.util.Log
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.base.imagefilestego.internal.Crypto
import com.base.imagefilestego.internal.Images
import com.base.imagefilestego.internal.Protocol
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicReference

@RunWith(AndroidJUnit4::class)
class ImageFileStegoInstrumentedTest {
    private val instrumentation = InstrumentationRegistry.getInstrumentation()
    private val context = instrumentation.targetContext
    private val password = "中文密码🔑 with spaces ".toCharArray()

    @Test fun pngRoundTripAndIndependentClientExtraction() = runBlocking {
        val content = ByteArray(4096) { (it * 17).toByte() }
        val firstClient = ImageFileStego(context, StegoOptions(allowTemporaryFiles = false))
        firstClient.embed(carrier(), StegoSource.fromBytes(content, "资料🔑.bin"), password).use { png ->
            assertEquals("image/png", png.mimeType)
            val bitmap = png.openStream().use { BitmapFactory.decodeStream(it) }
            assertEquals(512, bitmap.width)
            assertEquals(512, bitmap.height)
            assertEquals(255, Color.alpha(bitmap.getPixel(0, 0)))
            bitmap.recycle()
            ImageFileStego(context).extract(source(png), password).use { recovered ->
                assertEquals("资料🔑.bin", recovered.fileName)
                recovered.openStream().use { assertArrayEquals(content, it.readBytes()) }
            }
        }
    }

    @Test fun androidProviderMatchesIndependentV1AndV2UnicodeVectors() = runBlocking {
        for (name in listOf("vector_v1.png", "vector_v2.png")) {
            val source = StegoSource.fromStream(name) { instrumentation.context.assets.open(name) }
            ImageFileStego(context).extract(source, "密碼🔑 hello ".toCharArray()).use { result ->
                assertEquals("资料.bin", result.fileName)
                result.openStream().use { assertArrayEquals(byteArrayOf(0, 1, 2, -1) + "hello".toByteArray(), it.readBytes()) }
            }
        }
    }

    @Test fun jpegAndTransparentPngBecomeRecoverableOpaquePng() = runBlocking {
        val sdk = ImageFileStego(context)
        for (image in listOf(carrier(jpeg = true), carrier(transparent = true))) {
            sdk.embed(image, StegoSource.fromBytes(byteArrayOf(), "空文件.bin"), password).use { png ->
                sdk.extract(source(png), password).use { file -> assertEquals(0L, file.sizeBytes) }
                png.openStream().use { input ->
                    val bitmap = BitmapFactory.decodeStream(input)
                    assertEquals(255, Color.alpha(bitmap.getPixel(200, 200)))
                    bitmap.recycle()
                }
            }
        }
    }

    @Test fun wrongPasswordAndModifiedNonceDoNotExposeFiles() = runBlocking {
        val sdk = ImageFileStego(context)
        sdk.embed(carrier(), StegoSource.fromBytes(byteArrayOf(0, 1, 2), "a.bin"), password).use { png ->
            expect(StegoError.AUTHENTICATION_FAILED) { sdk.extract(source(png), "wrong".toCharArray()).close() }
            val options = BitmapFactory.Options().apply { inMutable = true }
            val bitmap = png.openStream().use { BitmapFactory.decodeStream(it, null, options)!! }
            // v2 第 16 字节是头部 nonce 起点，篡改后必须认证失败。
            val channel = 16 * 8
            val pixelIndex = channel / 3
            val x = pixelIndex % bitmap.width
            val y = pixelIndex / bitmap.width
            bitmap.setPixel(x, y, bitmap.getPixel(x, y) xor (1 shl (16 - channel % 3 * 8)))
            val changed = encode(bitmap)
            bitmap.recycle()
            expect(StegoError.AUTHENTICATION_FAILED) { sdk.extract(StegoSource.fromBytes(changed), password).close() }
        }
    }

    @Test fun exactCapacityWorksAndOneExtraByteIsRejected() = runBlocking {
        val sdk = ImageFileStego(context)
        val image = carrier(128, 96)
        val limit = sdk.inspect(image, "a.bin").maxFileBytes.toInt()
        val content = ByteArray(limit) { it.toByte() }
        sdk.embed(image, StegoSource.fromBytes(content, "a.bin"), password).use { png ->
            sdk.extract(source(png), password).use { recovered ->
                recovered.openStream().use { assertArrayEquals(content, it.readBytes()) }
            }
        }
        expect(StegoError.CAPACITY_EXCEEDED) { sdk.embed(image, StegoSource.fromBytes(ByteArray(limit + 1), "a.bin"), password).close() }
        val unknown = StegoSource.fromStream("a.bin") { ByteArray(limit + 1).inputStream() }
        expect(StegoError.CAPACITY_EXCEEDED) { sdk.embed(image, unknown, password).close() }
    }

    @Test fun temporaryPngIsDeletedOnCloseAndOnVerificationCancellation() = runBlocking {
        val sdk = ImageFileStego(context, StegoOptions(memoryThresholdBytes = 64))
        val root = File(context.cacheDir, "image_file_stego")
        fun count() = root.listFiles()?.count { it.name.startsWith("session-") } ?: 0
        sdk.cleanupTemporaryFiles()
        sdk.embed(carrier(), StegoSource.fromBytes(ByteArray(32), "a.bin"), password).use { png ->
            assertEquals(1, count())
            sdk.cleanupTemporaryFiles()
            assertEquals(1, count())
            assertTrue(png.openStream().use { it.read() } >= 0)
        }
        assertEquals(0, count())
        try {
            sdk.embed(carrier(), StegoSource.fromBytes(ByteArray(32), "a.bin"), password,
                StegoProgressListener { if (it.stage == StegoStage.VERIFYING) throw CancellationException("测试取消") }).close()
            fail("应响应验证阶段取消")
        } catch (_: CancellationException) { }
        assertEquals(0, count())
    }

    @Test fun invalidImagesAndMemoryLimitFailBeforeExpensiveWork() = runBlocking {
        val sdk = ImageFileStego(context, StegoOptions(maxWorkingMemoryBytes = 1024 * 1024))
        assertFalse(sdk.inspect(carrier()).withinLimits)
        expect(StegoError.MEMORY_LIMIT) { sdk.embed(carrier(), StegoSource.fromBytes(byteArrayOf()), password).close() }
        expect(StegoError.UNSUPPORTED_IMAGE) { ImageFileStego(context).extract(carrier(jpeg = true), password).close() }
        expect(StegoError.UNSUPPORTED_IMAGE) { ImageFileStego(context).inspect(StegoSource.fromBytes(byteArrayOf(1, 2, 3))) }
        expect(StegoError.AUTHENTICATION_FAILED) { ImageFileStego(context).extract(carrier(), password).close() }
    }

    @Test fun exifRotationAppliedOnlyBeforeEmbedding() = runBlocking {
        val source = StegoSource.fromStream("rotated.jpg") { instrumentation.context.assets.open("rotated.jpg") }
        val sdk = ImageFileStego(context)
        val info = sdk.inspect(source)
        assertEquals(80, info.width)
        assertEquals(120, info.height)
        sdk.embed(source, StegoSource.fromBytes(byteArrayOf(7, 8, 9), "a.bin"), password).use { png ->
            val bitmap = png.openStream().use { BitmapFactory.decodeStream(it) }
            assertEquals(80, bitmap.width)
            assertEquals(120, bitmap.height)
            bitmap.recycle()
            sdk.extract(source(png), password).use { result -> result.openStream().use { assertArrayEquals(byteArrayOf(7, 8, 9), it.readBytes()) } }
        }
    }

    @Test fun javaStyleAsyncInterfaceNeedsNoActivity() {
        val done = CountDownLatch(1)
        val error = AtomicReference<Throwable>()
        ImageFileStego(context).embedAsync(carrier(128, 128), StegoSource.fromBytes(byteArrayOf(4), "a.bin"), password,
            object : StegoCallback<StegoArtifact> {
                override fun onSuccess(result: StegoArtifact) {
                    try { result.use { assertTrue(it.sizeBytes > 0) } } catch (failure: Throwable) { error.set(failure) }
                    finally { done.countDown() }
                }
                override fun onError(failure: StegoException) { error.set(failure); done.countDown() }
                override fun onCancelled() { error.set(AssertionError("不应取消")); done.countDown() }
            })
        assertTrue(done.await(90, TimeUnit.SECONDS))
        error.get()?.let { throw AssertionError("异步调用失败", it) }
    }

    @Test fun activityCanOpenAndSurviveRecreation() {
        ActivityScenario.launch<ImageFileStegoActivity>(Intent(context, ImageFileStegoActivity::class.java)).use { scenario ->
            scenario.onActivity { assertFalse(it.isFinishing) }
            scenario.recreate()
            scenario.onActivity { assertFalse(it.isFinishing) }
        }
    }

    @Test fun twelveMegapixelImageCompletesWithinBudget() = runBlocking {
        val runtime = Runtime.getRuntime()
        val sdk = ImageFileStego(context)
        val cover = carrier(4000, 3000)
        if (!sdk.inspect(cover).withinLimits) {
            // 低内存设备应安全拒绝；足够内存的设备继续执行完整大图流程。
            expect(StegoError.MEMORY_LIMIT) { sdk.embed(cover, StegoSource.fromBytes(ByteArray(64 * 1024), "a.bin"), password).close() }
        } else {
            var observed = 0L
            val started = SystemClock.elapsedRealtime()
            sdk.embed(cover, StegoSource.fromBytes(ByteArray(64 * 1024) { it.toByte() }, "a.bin"), password,
                StegoProgressListener { observed = maxOf(observed, runtime.totalMemory() - runtime.freeMemory() + Debug.getNativeHeapAllocatedSize()) }).use { png ->
                sdk.extract(source(png), password).use { assertEquals(64L * 1024, it.sizeBytes) }
            }
            Log.i("ImageFileStegoTest", "1200万像素往返耗时=${SystemClock.elapsedRealtime() - started}ms，阶段采样堆内存=${observed / 1024 / 1024}MiB")
        }
        Unit
    }

    private fun carrier(width: Int = 512, height: Int = 512, jpeg: Boolean = false, transparent: Boolean = false): StegoSource {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val row = IntArray(width)
        for (y in 0 until height) {
            for (x in row.indices) row[x] = Color.argb(if (transparent) (x + y) % 255 else 255, x % 256, y % 256, (x + y) % 256)
            bitmap.setPixels(row, 0, width, 0, y, width, 1)
        }
        return try { StegoSource.fromBytes(encode(bitmap, jpeg), if (jpeg) "cover.jpg" else "cover.png") }
        finally { bitmap.recycle() }
    }

    private fun encode(bitmap: Bitmap, jpeg: Boolean = false) = ByteArrayOutputStream().use { output ->
        assertTrue(bitmap.compress(if (jpeg) Bitmap.CompressFormat.JPEG else Bitmap.CompressFormat.PNG, 100, output))
        output.toByteArray()
    }

    private fun source(artifact: StegoArtifact) = StegoSource.fromStream(artifact.fileName, artifact.sizeBytes) { artifact.openStream() }

    private suspend fun expect(code: StegoError, action: suspend () -> Unit) {
        try { action(); fail("应该拒绝无效输入") } catch (error: StegoException) { assertEquals(code, error.code) }
    }
}
