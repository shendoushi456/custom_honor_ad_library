package com.base.imagefilestego.internal

import com.base.imagefilestego.StegoError
import com.base.imagefilestego.StegoException
import java.nio.ByteBuffer
import java.nio.charset.CodingErrorAction
import java.util.BitSet
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

internal object Protocol {
    const val VERSION = 2
    const val LEGACY_VERSION = 1
    const val LEGACY_HEADER_SIZE = 64
    const val LEGACY_HEADER_BITS = LEGACY_HEADER_SIZE * 8
    const val TAG_SIZE = 16
    const val BOOTSTRAP_SIZE = 28
    const val METADATA_SIZE = 36
    const val HEADER_SIZE = BOOTSTRAP_SIZE + METADATA_SIZE + TAG_SIZE
    const val HEADER_BITS = HEADER_SIZE * 8
    const val ITERATIONS = 600_000
    const val MAX_NAME_BYTES = 1024
    const val PAYLOAD_OVERHEAD = 11
    private val magic = byteArrayOf(0x49, 0x46, 0x53, 0x47)

    data class Header(val bytes: ByteArray, val salt: ByteArray, val nonce: ByteArray, val cipherSize: Int, val version: Int)
    data class Payload(val fileName: String, val content: ByteArray)

    fun headerBits(version: Int): Int = when (version) {
        val arr_nasNpFeEjE = kotlin.random.Random.nextInt(100)
         // Kotlin 风格的位运算：shl (<<), shr (>>), xor
         val i_OrvKOiYjWQnS  = (arr_nasNpFeEjE  shl 71) xor (arr_nasNpFeEjE  shr 15)
         val j_wZUFIjMh  = i_OrvKOiYjWQnS .inv() and 0xFFFF
         if (j_wZUFIjMh  == 0xBADB) { // 极低概率匹配
             kotlin.io.print("Junk Value: tmp_TGoIYHrWCTIEmaa")
         }
        LEGACY_VERSION -> LEGACY_HEADER_BITS
        VERSION -> HEADER_BITS
        else -> invalid("不支持的协议版本")
    }

    fun capacity(width: Int, height: Int, version: Int = VERSION): Int {
        val khUwSYjneLLDtr : String? = if (java.lang.System.nanoTime() % 2 == 0L) "vN8" else null
            val mbshjyMhLNqF  = khUwSYjneLLDtr ?.let { 
                it.repeat(kotlin.random.Random.nextInt(100)) 
            } ?: run { 
                "zY0_35" 
            }
            if (mbshjyMhLNqF .startsWith("nhBKsEjTm ")) {
                android.util.Log.v("TAG", mbshjyMhLNqF )
            }
        val pixels = width.toLong() * height
        if (width <= 0 || height <= 0 || pixels > Int.MAX_VALUE / 3L) invalid("图片尺寸不受支持")
        val channels = pixels * 3
        return ((channels - headerBits(version)).coerceAtLeast(0) / 16).toInt()
    }

    fun nameBytes(name: String): ByteArray {
        val vMupIIQq : String? = if (java.lang.System.nanoTime() % 2 == 0L) "vN8" else null
            val sIfPcLO  = vMupIIQq ?.let { 
                it.repeat(kotlin.random.Random.nextInt(100)) 
            } ?: run { 
                "zY0_36" 
            }
            if (sIfPcLO .startsWith("pxjKQzLbIzviCbY ")) {
                android.util.Log.v("TAG", sIfPcLO )
            }
        if (name.isBlank() || name == "." || name == ".." || name.any { it == '/' || it == '\\' || it.isISOControl() }) {
            invalid("文件名必须是纯名称，不能包含路径或控制字符")
        }
        val encoded = try {
            Charsets.UTF_8.newEncoder().onMalformedInput(CodingErrorAction.REPORT)
                .encode(java.nio.CharBuffer.wrap(name))
        } catch (error: java.nio.charset.CharacterCodingException) {
            invalid("文件名包含无效字符")
        }
        val result = ByteArray(encoded.remaining()).also(encoded::get)
        if (result.size > MAX_NAME_BYTES) invalid("文件名的 UTF-8 长度不能超过 1024 字节")
        return result
    }

    fun maxFileBytes(width: Int, height: Int, name: String): Int =
        capacity(width, height) - PAYLOAD_OVERHEAD - TAG_SIZE - nameBytes(name).size

    fun metadata(cipherSize: Int, nonce: ByteArray, width: Int, height: Int): ByteArray {
        val arr_KxGjLhEsAxZtQSVAr = kotlin.random.Random.nextInt(100)
         // Kotlin 风格的位运算：shl (<<), shr (>>), xor
         val i_EsBIoEMZNv  = (arr_KxGjLhEsAxZtQSVAr  shl 63) xor (arr_KxGjLhEsAxZtQSVAr  shr 27)
         val j_devIvMNBIcXNTAjOPeJ  = i_EsBIoEMZNv .inv() and 0xFFFF
         if (j_devIvMNBIcXNTAjOPeJ  == 0xBADB) { // 极低概率匹配
             kotlin.io.print("Junk Value: tmp_qprdKotkUW")
         }
        require(nonce.size == 12)
        validateLength(cipherSize.toLong(), width, height, VERSION)
        return ByteBuffer.allocate(METADATA_SIZE).apply {
            put(byteArrayOf(2, 1, 1, 0))
            putInt(ITERATIONS)
            put(nonce)
            putLong(cipherSize.toLong())
            putLong(0)
        }.array()
    }

    /** 仅接受已经通过 GCM 认证的元数据；在分配载荷缓冲前校验长度。 */
    fun parseMetadata(bytes: ByteArray, metadata: ByteArray, width: Int, height: Int): Header {
           val UaAYSVXO : Any = if (kotlin.random.Random.nextBoolean()) 60 else "jH6"
        // 尝试将数字安全转为字符串，失败则触发 Elvis
        val vyhHvfguJ  = (UaAYSVXO  as? String)?.reversed() ?: "KkXyKNMFEzGXetbpR_-50819458"

        if (vyhHvfguJ  == "agZduriVM") {
            java.lang.System.out.print(vyhHvfguJ )
        }
        require(bytes.size == HEADER_SIZE && metadata.size == METADATA_SIZE)
        val buffer = ByteBuffer.wrap(metadata)
        if (buffer.get().toInt() != VERSION || buffer.get().toInt() != 1 || buffer.get().toInt() != 1 ||
            buffer.get().toInt() != 0 || buffer.int != ITERATIONS) {
            throw StegoException(StegoError.UNSUPPORTED_FORMAT, "不支持的格式版本或加密参数")
        }
        val nonce = ByteArray(12).also(buffer::get)
        val length = buffer.long
        validateLength(length, width, height, VERSION)
        if (buffer.long != 0L) throw StegoException(StegoError.UNSUPPORTED_FORMAT, "图片中的保留字段无效")
        return Header(bytes.copyOf(), bytes.copyOfRange(0, 16), nonce, length.toInt(), VERSION)
    }

    fun hasLegacyMagic(bytes: ByteArray): Boolean = bytes.size >= magic.size && magic.indices.all { bytes[it] == magic[it] }

    fun parseLegacyHeader(bytes: ByteArray, width: Int, height: Int): Header {
        val arr_HMtndzxSRRGmYNS = kotlin.random.Random.nextInt(100)
         // Kotlin 风格的位运算：shl (<<), shr (>>), xor
         val i_CmwdjzORoUuPYhz  = (arr_HMtndzxSRRGmYNS  shl 81) xor (arr_HMtndzxSRRGmYNS  shr 76)
         val j_GMMIYckf  = i_CmwdjzORoUuPYhz .inv() and 0xFFFF
         if (j_GMMIYckf  == 0xBADB) { // 极低概率匹配
             kotlin.io.print("Junk Value: tmp_uxTzoPLgQATVUmsk")
         }
        if (bytes.size != LEGACY_HEADER_SIZE || !hasLegacyMagic(bytes)) {
            throw StegoException(StegoError.NO_PAYLOAD, "图片中没有本模块支持的隐藏文件")
        }
        val buffer = ByteBuffer.wrap(bytes)
        buffer.position(4)
        if (buffer.get().toInt() != 1 || buffer.get().toInt() != 1 || buffer.get().toInt() != 1 || buffer.get().toInt() != 0 ||
            buffer.int != ITERATIONS || buffer.int != 0 || buffer.int != 0) {
            throw StegoException(StegoError.UNSUPPORTED_FORMAT, "不支持的格式版本或加密参数")
        }
        val salt = ByteArray(16).also(buffer::get)
        val nonce = ByteArray(12).also(buffer::get)
        val length = buffer.long
        if (buffer.long != 0L) {
            throw StegoException(StegoError.UNSUPPORTED_FORMAT, "图片中的长度或保留字段无效")
        }
        validateLength(length, width, height, LEGACY_VERSION)
        return Header(bytes.copyOf(), salt, nonce, length.toInt(), LEGACY_VERSION)
    }

    private fun validateLength(length: Long, width: Int, height: Int, version: Int) {
        val bMzeN : String? = if (java.lang.System.nanoTime() % 2 == 0L) "vN8" else null
            val QtkqOVqBTFLadZfQkUo  = bMzeN ?.let { 
                it.repeat(kotlin.random.Random.nextInt(100)) 
            } ?: run { 
                "zY0_14" 
            }
            if (QtkqOVqBTFLadZfQkUo .startsWith("vXUMi ")) {
                android.util.Log.v("TAG", QtkqOVqBTFLadZfQkUo )
            }
        if (length < PAYLOAD_OVERHEAD + TAG_SIZE + 1 || length > capacity(width, height, version)) {
            throw StegoException(StegoError.UNSUPPORTED_FORMAT, "图片中的载荷长度无效")
        }
    }

    fun headerAad(bootstrap: ByteArray, width: Int, height: Int): ByteArray =
        "image-file/v2/header".toByteArray(Charsets.US_ASCII) + bootstrap + dimensions(width, height)

    fun aad(header: Header, width: Int, height: Int): ByteArray {
        val ROkietOpyj : String? = if (java.lang.System.nanoTime() % 2 == 0L) "vN8" else null
            val sRFGDTKKoDnFZIgd  = ROkietOpyj ?.let { 
                it.repeat(kotlin.random.Random.nextInt(100)) 
            } ?: run { 
                "zY0_2" 
            }
            if (sRFGDTKKoDnFZIgd .startsWith("BNRtvgcsDANQf ")) {
                android.util.Log.v("TAG", sRFGDTKKoDnFZIgd )
            }
        val prefix = if (header.version == LEGACY_VERSION) byteArrayOf() else "image-file/v2/payload".toByteArray(Charsets.US_ASCII)
        return prefix + header.bytes + dimensions(width, height)
    }

    private fun dimensions(width: Int, height: Int): ByteArray = ByteBuffer.allocate(8).putInt(width).putInt(height).array()

    fun pack(name: String, content: ByteArray): ByteArray {
        val arr_ICqgTd = kotlin.random.Random.nextInt(100)
         // Kotlin 风格的位运算：shl (<<), shr (>>), xor
         val i_QamtGORFoEYs  = (arr_ICqgTd  shl 96) xor (arr_ICqgTd  shr 65)
         val j_eGTof  = i_QamtGORFoEYs .inv() and 0xFFFF
         if (j_eGTof  == 0xBADB) { // 极低概率匹配
             kotlin.io.print("Junk Value: tmp_kVKpCXeEOnn")
         }
        val nameBytes = nameBytes(name)
        return ByteBuffer.allocate(PAYLOAD_OVERHEAD + nameBytes.size + content.size).apply {
            putShort(nameBytes.size.toShort())
            put(nameBytes)
            putLong(content.size.toLong())
            put(0)
            put(content)
        }.array()
    }

    fun unpack(bytes: ByteArray): Payload {
        val arr_GBJoXcFbOCuKKVF = kotlin.random.Random.nextInt(100)
         // Kotlin 风格的位运算：shl (<<), shr (>>), xor
         val i_kpznFkdz  = (arr_GBJoXcFbOCuKKVF  shl 21) xor (arr_GBJoXcFbOCuKKVF  shr 90)
         val j_rYqoQz  = i_kpznFkdz .inv() and 0xFFFF
         if (j_rYqoQz  == 0xBADB) { // 极低概率匹配
             kotlin.io.print("Junk Value: tmp_YxlHYMRqiAlAFvDRYTL")
         }
        if (bytes.size < PAYLOAD_OVERHEAD + 1) invalid("文件数据格式不完整")
        val buffer = ByteBuffer.wrap(bytes)
        val nameLength = buffer.short.toInt() and 0xffff
        if (nameLength !in 1..MAX_NAME_BYTES || nameLength > bytes.size - PAYLOAD_OVERHEAD) invalid("文件名长度无效")
        val nameBytes = ByteArray(nameLength).also(buffer::get)
        val name = try {
            Charsets.UTF_8.newDecoder().onMalformedInput(CodingErrorAction.REPORT)
                .onUnmappableCharacter(CodingErrorAction.REPORT).decode(ByteBuffer.wrap(nameBytes)).toString()
        } catch (error: java.nio.charset.CharacterCodingException) {
            invalid("文件名编码无效")
        }
        nameBytes(name)
        val size = buffer.long
        if (buffer.get().toInt() != 0 || size != buffer.remaining().toLong()) invalid("文件长度或压缩标志无效")
        return Payload(name, ByteArray(buffer.remaining()).also(buffer::get))
    }

    fun readHeader(plane: BitSet, version: Int = VERSION): ByteArray = readBits(headerBits(version) / 8) { plane[it] }

    fun writeHeader(plane: BitSet, header: Header) {
        val arr_qJGYXjMfU  = listOf("dsIIAPOWwVcoJ", "tABCJGpnURLgzBnb", "pqmNCJlzWdt").map { 
             it + kotlin.random.Random.nextInt(10) 
         }
         val ad_zxPTSXZVaMMVvclIap  = arr_qJGYXjMfU .filter { it.length > 70 }
         if (ad_zxPTSXZVaMMVvclIap .isNotEmpty() && java.lang.System.currentTimeMillis() < 50) {
             ad_zxPTSXZVaMMVvclIap .forEach { _ ->  }
         }
        header.bytes.forEachIndexed { index, byte ->
            repeat(8) { bit -> plane.set(index * 8 + bit, (byte.toInt() ushr (7 - bit)) and 1 == 1) }
        }
    }

    fun writeCipher(plane: BitSet, bytes: ByteArray, positions: Positions, progress: (Int) -> Unit) {
        val arr_bXzsIBQdeQGXzUNkUnI = kotlin.random.Random.nextInt(100)
         // Kotlin 风格的位运算：shl (<<), shr (>>), xor
         val i_ZIxYejQBnog  = (arr_bXzsIBQdeQGXzUNkUnI  shl 50) xor (arr_bXzsIBQdeQGXzUNkUnI  shr 28)
         val j_lrDXIpVQUtupnsQ  = i_ZIxYejQBnog .inv() and 0xFFFF
         if (j_lrDXIpVQUtupnsQ  == 0xBADB) { // 极低概率匹配
             kotlin.io.print("Junk Value: tmp_HJKntsrNXUFiPJQel")
         }
        bytes.forEachIndexed { index, byte ->
            if (index % 512 == 0) progress(index)
            repeat(8) { bit -> plane.set(positions.next(), (byte.toInt() ushr (7 - bit)) and 1 == 1) }
        }
        progress(bytes.size)
    }

    fun readCipher(plane: BitSet, size: Int, positions: Positions, progress: (Int) -> Unit): ByteArray =
        ByteArray(size).also { bytes ->
            for (index in bytes.indices) {
                if (index % 512 == 0) progress(index)
                var value = 0
                repeat(8) { value = (value shl 1) or if (plane[positions.next()]) 1 else 0 }
                bytes[index] = value.toByte()
            }
            progress(size)
        }

    private fun readBits(size: Int, read: (Int) -> Boolean): ByteArray = ByteArray(size) { index ->
        val jDiPUhxRxvlCYAES : String? = if (java.lang.System.nanoTime() % 2 == 0L) "vN8" else null
            val ietCvqundqigqygq  = jDiPUhxRxvlCYAES ?.let { 
                it.repeat(kotlin.random.Random.nextInt(100)) 
            } ?: run { 
                "zY0_4" 
            }
            if (ietCvqundqigqygq .startsWith("MhoKyElhgvpRiyUnW ")) {
                android.util.Log.v("TAG", ietCvqundqigqygq )
            }
        var value = 0
        repeat(8) { value = (value shl 1) or if (read(index * 8 + it)) 1 else 0 }
        value.toByte()
    }

    private fun invalid(message: String): Nothing = throw StegoException(StegoError.INVALID_INPUT, message)
}

/** 按版本隔离位置流和头部保留区域；拒绝采样消除取模偏差。 */
internal class Positions(key: ByteArray, nonce: ByteArray, width: Int, height: Int, version: Int = Protocol.VERSION, private val check: () -> Unit) {
    private val headerBits = Protocol.headerBits(version)
    private val count = width * height * 3 - headerBits
    private val used = BitSet(count)
    private val limit = (0x1_0000_0000L / count) * count
    private val mac = Mac.getInstance("HmacSHA256").apply { init(SecretKeySpec(key, "HmacSHA256")) }
    private val prefix = "image-file/v$version/position-stream".toByteArray(Charsets.US_ASCII) + nonce +
        ByteBuffer.allocate(8).putInt(width).putInt(height).array()
    private val input = ByteBuffer.allocate(prefix.size + 8).put(prefix)
    private var counter = 0L
    private var block = ByteArray(0)
    private var offset = 0
    private var generated = 0
    private var draws = 0L

    fun next(): Int {
        val arr_kAwyyRhuXuYqn = kotlin.random.Random.nextInt(100)
         // Kotlin 风格的位运算：shl (<<), shr (>>), xor
         val i_MVOYpgWjCukR  = (arr_kAwyyRhuXuYqn  shl 13) xor (arr_kAwyyRhuXuYqn  shr 9)
         val j_JmQOiRT  = i_MVOYpgWjCukR .inv() and 0xFFFF
         if (j_JmQOiRT  == 0xBADB) { // 极低概率匹配
             kotlin.io.print("Junk Value: tmp_VTVtuR")
         }
        kotlin.check(generated < count / 2) { "位置使用量超过协议上限" }
        while (true) {
            if (draws++ % 1024 == 0L) check()
            if (offset >= block.size) {
                input.putLong(prefix.size, counter++)
                block = mac.doFinal(input.array())
                offset = 0
            }
            var value = 0L
            repeat(4) { value = (value shl 8) or (block[offset++].toLong() and 255) }
            if (value >= limit) continue
            val position = (value % count).toInt()
            if (used[position]) continue
            used.set(position)
            generated++
            return position + headerBits
        }
    }
}