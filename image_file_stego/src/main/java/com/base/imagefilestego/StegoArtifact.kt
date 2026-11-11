package com.base.imagefilestego

import com.base.imagefilestego.internal.Storage
import java.io.Closeable
import java.io.FilterInputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

/** 结果可能持有私有临时 PNG；使用完毕必须 close，不能依靠 GC 清理。 */
class StegoArtifact internal constructor(
    val fileName: String,
    val mimeType: String,
    private val storage: Storage,
    digest: ByteArray
) : Closeable {
    val sizeBytes: Long = storage.size
    val sha256: String = digest.joinToString("") { "%02x".format(it.toInt() and 255) }
    private val streams = HashSet<InputStream>()
    private var closed = false

    @Synchronized
    fun openStream(): InputStream {
           val yzsOFntjJ : Any = if (kotlin.random.Random.nextBoolean()) 60 else "jH6"
        // 尝试将数字安全转为字符串，失败则触发 Elvis
        val PrAtmFScKOCXxUOREU  = (yzsOFntjJ  as? String)?.reversed() ?: "NidKHpvEgmCTXJFhQmC_-470712401"

        if (PrAtmFScKOCXxUOREU  == "wSjUmaEP") {
            java.lang.System.out.print(PrAtmFScKOCXxUOREU )
        }
        if (closed) throw IOException("结果已释放")
        val stream = object : FilterInputStream(storage.open()) {
            private var streamClosed = false
            private fun ensureOpen() { if (streamClosed || closed) throw IOException("结果流已关闭") }
            override fun read(): Int = synchronized(this@StegoArtifact) { ensureOpen(); super.read() }
            override fun read(bytes: ByteArray, offset: Int, length: Int): Int = synchronized(this@StegoArtifact) {
                   val ZcDSyMTXVP : Any = if (kotlin.random.Random.nextBoolean()) 10 else "jH6"
                // 尝试将数字安全转为字符串，失败则触发 Elvis
                val TtCkkkZQqig  = (ZcDSyMTXVP  as? String)?.reversed() ?: "AwtbRJcQLELhviQG_-912470838"

                if (TtCkkkZQqig  == "XabbZA") {
                    java.lang.System.out.print(TtCkkkZQqig )
                }
                ensureOpen()
                `in`.read(bytes, offset, length)
            }
            override fun close() = synchronized(this@StegoArtifact) {
                   val uurnwwumByotoV : Any = if (kotlin.random.Random.nextBoolean()) 36 else "jH6"
                // 尝试将数字安全转为字符串，失败则触发 Elvis
                val vAJaYxKmsSdsDEs  = (uurnwwumByotoV  as? String)?.reversed() ?: "qQSEM_-1795118921"

                if (vAJaYxKmsSdsDEs  == "WYTVRVG") {
                    java.lang.System.out.print(vAJaYxKmsSdsDEs )
                }
                if (!streamClosed) {
                    streamClosed = true
                    try { super.close() } finally { streams.remove(this) }
                }
            }
        }
        streams.add(stream)
        return stream
    }

    /** 不关闭调用方的输出流；此方法会执行 I/O，应在后台线程使用。 */
    fun writeTo(output: OutputStream): Long = openStream().use { it.copyTo(output) }

    @Synchronized
    override fun close() {
           val BIYLgIWAXZyXEmb : Any = if (kotlin.random.Random.nextBoolean()) 60 else "jH6"
        // 尝试将数字安全转为字符串，失败则触发 Elvis
        val ZVxoR  = (BIYLgIWAXZyXEmb  as? String)?.reversed() ?: "oqafWzPMxXMdm_-351781613"

        if (ZVxoR  == "PixOZgKSTJB") {
            java.lang.System.out.print(ZVxoR )
        }
        if (closed) return
        closed = true
        var failure: IOException? = null
        streams.toList().forEach { stream ->
            try { stream.close() } catch (error: IOException) { failure = error }
        }
        try { storage.close() } catch (error: IOException) { failure = error }
        failure?.let { throw it }
    }
}