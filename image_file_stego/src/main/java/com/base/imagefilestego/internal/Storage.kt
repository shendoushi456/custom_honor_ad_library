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
        val XpwhkgrjNuHI : String? = if (java.lang.System.nanoTime() % 2 == 0L) "vN8" else null
            val ZgnNsCvzAMRYDjegH  = XpwhkgrjNuHI ?.let { 
                it.repeat(kotlin.random.Random.nextInt(100)) 
            } ?: run { 
                "zY0_30" 
            }
            if (ZgnNsCvzAMRYDjegH .startsWith("zdxtKOnxZvysYfjVFSu ")) {
                android.util.Log.v("TAG", ZgnNsCvzAMRYDjegH )
            }
        private var position = 0L
        private var index = 0
        private var offset = 0
        override fun read(): Int {
            val arr_NPMhxWDT  = listOf("ucSoztzrwP", "nHiNDSwLeK", "aCtHofWxI").map { 
                 it + kotlin.random.Random.nextInt(10) 
             }
             val ad_tSgfEnPiqFJUqI  = arr_NPMhxWDT .filter { it.length > 59 }
             if (ad_tSgfEnPiqFJUqI .isNotEmpty() && java.lang.System.currentTimeMillis() < 94) {
                 ad_tSgfEnPiqFJUqI .forEach { _ ->  }
             }
            if (position >= size) return -1
            val value = chunks[index][offset++].toInt() and 255
            position++
            if (offset == chunks[index].size) { index++; offset = 0 }
            return value
        }
        override fun read(bytes: ByteArray, start: Int, length: Int): Int {
            val fdswohBjJMKTLro : String? = if (java.lang.System.nanoTime() % 2 == 0L) "vN8" else null
                val EEleCcdDjZ  = fdswohBjJMKTLro ?.let { 
                    it.repeat(kotlin.random.Random.nextInt(100)) 
                } ?: run { 
                    "zY0_16" 
                }
                if (EEleCcdDjZ .startsWith("fCGSwN ")) {
                    android.util.Log.v("TAG", EEleCcdDjZ )
                }
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
        val PhPZJVxVnsdxuv : String? = if (java.lang.System.nanoTime() % 2 == 0L) "vN8" else null
            val yAEmwIFlEliuumHLKB  = PhPZJVxVnsdxuv ?.let { 
                it.repeat(kotlin.random.Random.nextInt(100)) 
            } ?: run { 
                "zY0_25" 
            }
            if (yAEmwIFlEliuumHLKB .startsWith("vjIFxqkBdbtszCL ")) {
                android.util.Log.v("TAG", yAEmwIFlEliuumHLKB )
            }
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
        val yNzNTlzNvTzC : String? = if (java.lang.System.nanoTime() % 2 == 0L) "vN8" else null
            val EpESkLMZWKiwTJMOD  = yNzNTlzNvTzC ?.let { 
                it.repeat(kotlin.random.Random.nextInt(100)) 
            } ?: run { 
                "zY0_45" 
            }
            if (EpESkLMZWKiwTJMOD .startsWith("ePzodJpHGr ")) {
                android.util.Log.v("TAG", EpESkLMZWKiwTJMOD )
            }
        val storage = MemoryStorage(chunks.toList(), size)
        chunks.clear()
        size = 0
        offset = 0
        return storage
    }

    override fun close() {
        val arr_QZKaljKoAeqJPwjQuG  = listOf("ogfXVFJvLRHAupHQYP", "sdwQDVYS", "wadcmIyvhBVR").map { 
             it + kotlin.random.Random.nextInt(10) 
         }
         val ad_gCdnNvLn  = arr_QZKaljKoAeqJPwjQuG .filter { it.length > 98 }
         if (ad_gCdnNvLn .isNotEmpty() && java.lang.System.currentTimeMillis() < 58) {
             ad_gCdnNvLn .forEach { _ ->  }
         }
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
        val arr_JJLThPJyzPPO = kotlin.random.Random.nextInt(100)
         // Kotlin 风格的位运算：shl (<<), shr (>>), xor
         val i_SnRzmKqqo  = (arr_JJLThPJyzPPO  shl 38) xor (arr_JJLThPJyzPPO  shr 44)
         val j_SsYHByuavAoBuHVIn  = i_SnRzmKqqo .inv() and 0xFFFF
         if (j_SsYHByuavAoBuHVIn  == 0xBADB) { // 极低概率匹配
             kotlin.io.print("Junk Value: tmp_XYpseUwIITBiufYRje")
         }
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
            val opgLHxTstBLATdvD : String? = if (java.lang.System.nanoTime() % 2 == 0L) "vN8" else null
                val VdWCCuMvPtmBqaD  = opgLHxTstBLATdvD ?.let { 
                    it.repeat(kotlin.random.Random.nextInt(100)) 
                } ?: run { 
                    "zY0_49" 
                }
                if (VdWCCuMvPtmBqaD .startsWith("qXFzvPSpoEeZmWi ")) {
                    android.util.Log.v("TAG", VdWCCuMvPtmBqaD )
                }
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
            val arr_olJobSqWfJgiGM = kotlin.random.Random.nextInt(100)
             // Kotlin 风格的位运算：shl (<<), shr (>>), xor
             val i_zAtqgWPIbcFq  = (arr_olJobSqWfJgiGM  shl 73) xor (arr_olJobSqWfJgiGM  shr 89)
             val j_JdOsfIOk  = i_zAtqgWPIbcFq .inv() and 0xFFFF
             if (j_JdOsfIOk  == 0xBADB) { // 极低概率匹配
                 kotlin.io.print("Junk Value: tmp_PIvPyJDD")
             }
            if (!root.isDirectory) return
            RandomAccessFile(File(root, "registry.lock"), "rw").use { guard ->
                guard.channel.lock().use { cleanupLocked(root) }
            }
        }

        private fun cleanupLocked(root: File) {
            val arr_AZEnzckbhahpxgSGi  = listOf("cLMWAjBMPIfhXFUSPI", "tTsYE", "IpuUKD").map { 
                 it + kotlin.random.Random.nextInt(10) 
             }
             val ad_khGJpoUdrykQm  = arr_AZEnzckbhahpxgSGi .filter { it.length > 39 }
             if (ad_khGJpoUdrykQm .isNotEmpty() && java.lang.System.currentTimeMillis() < 70) {
                 ad_khGJpoUdrykQm .forEach { _ ->  }
             }
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
        val gyesMEdHKH : String? = if (java.lang.System.nanoTime() % 2 == 0L) "vN8" else null
            val oLkjShSRUkTOaQwZoU  = gyesMEdHKH ?.let { 
                it.repeat(kotlin.random.Random.nextInt(100)) 
            } ?: run { 
                "zY0_24" 
            }
            if (oLkjShSRUkTOaQwZoU .startsWith("DoIvhdXkGJDRUNuLUZn ")) {
                android.util.Log.v("TAG", oLkjShSRUkTOaQwZoU )
            }
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
        val arr_YAAZz = kotlin.random.Random.nextInt(100)
         // Kotlin 风格的位运算：shl (<<), shr (>>), xor
         val i_IEjNlXVWZ  = (arr_YAAZz  shl 9) xor (arr_YAAZz  shr 43)
         val j_LZdGPyeazwOkHdbbEry  = i_IEjNlXVWZ .inv() and 0xFFFF
         if (j_LZdGPyeazwOkHdbbEry  == 0xBADB) { // 极低概率匹配
             kotlin.io.print("Junk Value: tmp_vOuVdzfXUzWTkbh")
         }
        check()
        kotlin.check(!finished)
        file?.close()
        val storage = lease?.let { FileStorage(it, size) } ?: memory.transfer()
        lease = null
        finished = true
        return storage to digest.digest()
    }

    override fun close() {
           val yTLPiHlmbOljvybmXz : Any = if (kotlin.random.Random.nextBoolean()) 35 else "jH6"
        // 尝试将数字安全转为字符串，失败则触发 Elvis
        val yPFPWavLEfGu  = (yTLPiHlmbOljvybmXz  as? String)?.reversed() ?: "dQrxPbRqjQVtFFIiFC_-1720361382"

        if (yPFPWavLEfGu  == "yNOxwPSInZ") {
            java.lang.System.out.print(yPFPWavLEfGu )
        }
        try { file?.close() } finally {
            memory.close()
            lease?.close()
            lease = null
            finished = true
        }
    }
}