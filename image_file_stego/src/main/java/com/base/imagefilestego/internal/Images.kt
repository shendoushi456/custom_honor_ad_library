package com.base.imagefilestego.internal

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorSpace
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.RectF
import androidx.exifinterface.media.ExifInterface
import com.base.imagefilestego.ImageInfo
import com.base.imagefilestego.StegoError
import com.base.imagefilestego.StegoException
import com.base.imagefilestego.StegoOptions
import com.base.imagefilestego.StegoSource
import java.io.FilterInputStream
import java.io.InputStream
import java.util.BitSet

internal object Images {
    private const val MIB = 1024L * 1024
    private const val MAX_IMAGE_BYTES = 128 * MIB
    data class Bounds(val width: Int, val height: Int, val mime: String, val orientation: Int) {
        val swapped: Boolean get() = orientation in 5..8
        val outputWidth: Int get() = if (swapped) height else width
        val outputHeight: Int get() = if (swapped) width else height
    }

    fun bounds(source: StegoSource, extracting: Boolean, check: () -> Unit): Bounds {
        check()
        val options = BitmapFactory.Options().apply { inJustDecodeBounds = true; inScaled = false }
        open(source, check).use { BitmapFactory.decodeStream(it, null, options) }
        if (options.outWidth <= 0 || options.outHeight <= 0 || options.outMimeType !in listOf("image/png", "image/jpeg")) {
            throw StegoException(StegoError.UNSUPPORTED_IMAGE, "请选择有效的 JPEG 或 PNG 图片")
        }
        if (extracting && options.outMimeType != "image/png") {
            throw StegoException(StegoError.UNSUPPORTED_IMAGE, "提取需要原始 PNG 文件，不能使用 JPEG、截图或缩放后的图片")
        }
        val orientation = if (extracting) 1 else open(source, check).use {
            ExifInterface(it).getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
                .takeIf { value -> value in 1..8 } ?: 1
        }
        return Bounds(options.outWidth, options.outHeight, options.outMimeType!!, orientation)
    }

    fun estimate(bounds: Bounds, options: StegoOptions): Long {
        val pixels = bounds.width.toLong() * bounds.height
        if (pixels > options.maxPixels) return Long.MAX_VALUE
        // 按容量稍大的 v1 保守估算，兼容旧图片提取时的内存预算。
        val payload = ((pixels * 3 - Protocol.LEGACY_HEADER_BITS).coerceAtLeast(0) / 16)
        val processing = pixels * 4 + (pixels * 3 + 7) / 8 * 2 + payload * 4 +
            options.memoryThresholdBytes + bounds.width.toLong() * 4 + 16 * MIB
        val orientation = pixels * (if (bounds.orientation == 1) 4 else 8) + 16 * MIB
        return maxOf(processing, orientation)
    }

    fun budget(options: StegoOptions): Long {
        val runtime = Runtime.getRuntime()
        val available = runtime.maxMemory() - (runtime.totalMemory() - runtime.freeMemory())
        return minOf(options.maxWorkingMemoryBytes, runtime.maxMemory() / 2, (available - 16 * MIB).coerceAtLeast(0))
    }

    fun inspect(bounds: Bounds, options: StegoOptions, name: String): ImageInfo {
        val pixels = bounds.width.toLong() * bounds.height
        val estimate = estimate(bounds, options)
        val limit = when {
            pixels > options.maxPixels -> "图片超过 ${options.maxPixels / 1_000_000} 百万像素上限，请选择较小图片"
            estimate > budget(options) -> "当前可用内存不足，请选择较小图片或释放其他结果"
            pixels * 3 < Protocol.HEADER_BITS -> "图片太小，无法存放格式信息"
            else -> null
        }
        val fileCapacity = if (pixels <= Int.MAX_VALUE / 3L) {
            Protocol.maxFileBytes(bounds.width, bounds.height, name).coerceAtLeast(0).toLong()
        } else 0
        return ImageInfo(bounds.outputWidth, bounds.outputHeight, bounds.mime, fileCapacity, estimate, limit == null, limit)
    }

    fun requireBudget(bounds: Bounds, options: StegoOptions, extracting: Boolean = false) {
        if (bounds.width.toLong() * bounds.height > options.maxPixels || estimate(bounds, options) > budget(options)) {
            throw StegoException(StegoError.MEMORY_LIMIT, "图片超过像素或内存预算，请选择较小图片或先释放其他结果")
        }
        val headerBits = if (extracting) Protocol.LEGACY_HEADER_BITS else Protocol.HEADER_BITS
        if (bounds.width.toLong() * bounds.height * 3 < headerBits) {
            throw StegoException(StegoError.CAPACITY_EXCEEDED, "图片太小，无法存放格式信息")
        }
    }

    fun decode(source: StegoSource, bounds: Bounds, check: () -> Unit): Bitmap {
        check()
        val options = BitmapFactory.Options().apply {
            inPreferredConfig = Bitmap.Config.ARGB_8888
            inPreferredColorSpace = ColorSpace.get(ColorSpace.Named.SRGB)
            inScaled = false
            inSampleSize = 1
            inMutable = true
        }
        var decoded: Bitmap? = null
        var rotated: Bitmap? = null
        var reusable: Bitmap? = null
        try {
            // 固定可复用缓冲区，避免输入在检查后被替换成更大图片时重新分配超限 Bitmap。
            reusable = Bitmap.createBitmap(bounds.width, bounds.height, Bitmap.Config.ARGB_8888)
            options.inBitmap = reusable
            decoded = open(source, check).use { BitmapFactory.decodeStream(it, null, options) }
                ?: throw StegoException(StegoError.UNSUPPORTED_IMAGE, "图片解码失败")
            if (decoded === reusable) reusable = null
            if (decoded.width != bounds.width || decoded.height != bounds.height || decoded.config != Bitmap.Config.ARGB_8888 || !decoded.isMutable) {
                throw StegoException(StegoError.UNSUPPORTED_IMAGE, "图片发生变化或解码配置不受支持")
            }
            check()
            if (bounds.orientation == 1) return decoded.also { decoded = null }
            val matrix = Matrix().apply {
                when (bounds.orientation) {
                    2 -> setScale(-1f, 1f)
                    3 -> setRotate(180f)
                    4 -> setScale(1f, -1f)
                    5 -> { setRotate(90f); postScale(-1f, 1f) }
                    6 -> setRotate(90f)
                    7 -> { setRotate(270f); postScale(-1f, 1f) }
                    8 -> setRotate(270f)
                }
                val rect = RectF(0f, 0f, bounds.width.toFloat(), bounds.height.toFloat())
                mapRect(rect)
                postTranslate(-rect.left, -rect.top)
            }
            rotated = Bitmap.createBitmap(bounds.outputWidth, bounds.outputHeight, Bitmap.Config.ARGB_8888)
            Canvas(rotated).apply {
                drawColor(Color.WHITE)
                drawBitmap(decoded, matrix, Paint())
            }
            rotated.setHasAlpha(false)
            check()
            return rotated.also { rotated = null }
        } finally {
            reusable?.recycle()
            decoded?.recycle()
            rotated?.recycle()
        }
    }

    fun readPlane(bitmap: Bitmap, normalize: Boolean, check: () -> Unit): BitSet {
        val plane = BitSet(bitmap.width * bitmap.height * 3)
        val row = IntArray(bitmap.width)
        try {
            for (y in 0 until bitmap.height) {
                check()
                bitmap.getPixels(row, 0, bitmap.width, 0, y, bitmap.width, 1)
                for (x in row.indices) {
                    var pixel = row[x]
                    val alpha = pixel ushr 24
                    if (alpha != 255) {
                        if (!normalize) throw StegoException(StegoError.UNSUPPORTED_IMAGE, "隐藏文件的 PNG 应为不透明图片")
                        fun channel(shift: Int): Int = (((pixel ushr shift) and 255) * alpha + 255 * (255 - alpha) + 127) / 255
                        pixel = (255 shl 24) or (channel(16) shl 16) or (channel(8) shl 8) or channel(0)
                        row[x] = pixel
                    }
                    val base = (y * bitmap.width + x) * 3
                    plane.set(base, pixel ushr 16 and 1 == 1)
                    plane.set(base + 1, pixel ushr 8 and 1 == 1)
                    plane.set(base + 2, pixel and 1 == 1)
                }
                if (normalize) bitmap.setPixels(row, 0, bitmap.width, 0, y, bitmap.width, 1)
            }
            if (normalize) bitmap.setHasAlpha(false)
            return plane
        } finally { row.fill(0) }
    }

    fun writePlane(bitmap: Bitmap, plane: BitSet, check: () -> Unit) {
        val row = IntArray(bitmap.width)
        try {
            for (y in 0 until bitmap.height) {
                check()
                bitmap.getPixels(row, 0, bitmap.width, 0, y, bitmap.width, 1)
                for (x in row.indices) {
                    val base = (y * bitmap.width + x) * 3
                    row[x] = (row[x] and 0xfffefefe.toInt()) or
                        (if (plane[base]) 1 shl 16 else 0) or
                        (if (plane[base + 1]) 1 shl 8 else 0) or
                        (if (plane[base + 2]) 1 else 0)
                }
                bitmap.setPixels(row, 0, bitmap.width, 0, y, bitmap.width, 1)
            }
        } finally { row.fill(0) }
    }

    private fun open(source: StegoSource, check: () -> Unit): InputStream {
        if ((source.sizeBytes ?: 0) > MAX_IMAGE_BYTES) throw StegoException(StegoError.CAPACITY_EXCEEDED, "输入图片超过 128 MiB 上限")
        return object : FilterInputStream(source.openStream()) {
            private var count = 0L
            private fun consumed(amount: Long) {
                count += amount
                if (count > MAX_IMAGE_BYTES) throw StegoException(StegoError.CAPACITY_EXCEEDED, "输入图片超过 128 MiB 上限")
            }
            override fun read(): Int { check(); return `in`.read().also { if (it >= 0) consumed(1) } }
            override fun read(bytes: ByteArray, offset: Int, length: Int): Int {
                check()
                return `in`.read(bytes, offset, length).also { if (it > 0) consumed(it.toLong()) }
            }
            override fun skip(amount: Long): Long { check(); return `in`.skip(amount).also(::consumed) }
        }
    }
}
