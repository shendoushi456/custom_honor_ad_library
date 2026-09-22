package com.base.imagefilestego

import com.base.imagefilestego.internal.HeaderCodec
import com.base.imagefilestego.internal.Crypto
import com.base.imagefilestego.internal.Positions
import com.base.imagefilestego.internal.Protocol
import org.junit.Assert.*
import org.junit.Test
import java.nio.ByteBuffer
import java.util.BitSet

class ProtocolTest {
    // 由 Python hashlib.pbkdf2_hmac、HMAC 和 cryptography AESGCM 独立生成。
    private val headerBytes = hex("4946534701010100000927c00000000000000000000102030405060708090a0b0c0d0e0f101112131415161718191a1b000000000000002e0000000000000000")
    private val ciphertext = hex("06378a17aac6f4a92bc3782f794de017993018427333cfd19add101e4fa6306d2508fb757323ffeea8c1e664513f")

    @Test fun unicodePasswordMatchesIndependentCryptographicVector() {
        val header = Protocol.parseLegacyHeader(headerBytes, 96, 80)
        Crypto.derive("密碼🔑 hello ".toCharArray(), header.salt, Protocol.LEGACY_VERSION).use { keys ->
            assertArrayEquals(hex("b4b3a95b72ddfab1277455bffd7e55ae63eabf58e7345713250af9e731151b1a"), keys.encryption)
            assertArrayEquals(hex("2675c6d79e7127538947ec76ee8956cde9e7f5586efaea51dc1f225f58f79ca4"), keys.positions)
            val payload = Protocol.pack("资料.bin", byteArrayOf(0, 1, 2, -1) + "hello".toByteArray())
            assertArrayEquals(ciphertext, Crypto.encrypt(payload, keys, header, 96, 80))
            assertArrayEquals(payload, Crypto.decrypt(ciphertext, keys, header, 96, 80))
            val positions = Positions(keys.positions, header.nonce, 96, 80, Protocol.LEGACY_VERSION) {}
            assertEquals(listOf(22329, 590, 21673, 14627, 12463, 10944, 864, 22099, 16372, 1521, 10596, 1018,
                11872, 15076, 14961, 11233, 10878, 4965, 11856, 6096, 20301, 8019, 6028, 12163), List(24) { positions.next() })
        }
    }

    @Test fun halfCapacityPositionsAreUniqueAndReproducible() {
        val key = ByteArray(32) { it.toByte() }
        val first = Positions(key, ByteArray(12), 128, 96) {}
        val second = Positions(key, ByteArray(12), 128, 96) {}
        val used = HashSet<Int>()
        repeat((128 * 96 * 3 - Protocol.HEADER_BITS) / 2) {
            val position = first.next()
            assertTrue(position in Protocol.HEADER_BITS until 128 * 96 * 3)
            assertTrue(used.add(position))
            assertEquals(position, second.next())
        }
    }

    @Test fun bitPackingRoundTripsAtCapacity() {
        val width = 128
        val height = 96
        val header = HeaderCodec.create(Protocol.capacity(width, height), "test".toCharArray(), width, height, check = {}).use { it.header }
        val bytes = ByteArray(header.cipherSize) { (it * 37).toByte() }
        val plane = BitSet(width * height * 3)
        val key = ByteArray(32)
        Protocol.writeHeader(plane, header)
        Protocol.writeCipher(plane, bytes, Positions(key, header.nonce, width, height) {}) {}
        assertArrayEquals(header.bytes, Protocol.readHeader(plane))
        assertArrayEquals(bytes, Protocol.readCipher(plane, bytes.size, Positions(key, header.nonce, width, height) {}) {})
    }

    @Test fun headerRejectsResourceAbuseBeforeKdf() {
        for (mutate in listOf<(ByteArray) -> Unit>(
            { it[4] = 2 }, { ByteBuffer.wrap(it).putInt(8, Int.MAX_VALUE) },
            { ByteBuffer.wrap(it).putLong(48, Long.MAX_VALUE) }, { it[63] = 1 }, { it[7] = 1 }
        )) {
            expect(StegoError.UNSUPPORTED_FORMAT) { Protocol.parseLegacyHeader(headerBytes.copyOf().also(mutate), 96, 80) }
        }
        expect(StegoError.NO_PAYLOAD) { Protocol.parseLegacyHeader(ByteArray(64), 96, 80) }
        expect(StegoError.UNSUPPORTED_FORMAT) { Protocol.parseLegacyHeader(headerBytes, 1, 1) }
    }

    @Test fun metadataSupportsEmptyFilesAndRejectsUnsafeNamesAndLengths() {
        val result = Protocol.unpack(Protocol.pack("中文🔑.zip", byteArrayOf()))
        assertEquals("中文🔑.zip", result.fileName)
        assertArrayEquals(byteArrayOf(), result.content)
        listOf("../secret", "a\\b", ".", "..", "\u0000", "a".repeat(1025)).forEach { name ->
            expect(StegoError.INVALID_INPUT) { Protocol.pack(name, byteArrayOf()) }
        }
        val malformed = Protocol.pack("a", byteArrayOf(1))
        malformed[10] = 20
        expect(StegoError.INVALID_INPUT) { Protocol.unpack(malformed) }
        expect(StegoError.INVALID_INPUT) { Protocol.unpack(byteArrayOf(0, 127)) }
    }

    @Test fun aadAndCiphertextTamperingNeverReturnsPlaintext() {
        val header = Protocol.parseLegacyHeader(headerBytes, 96, 80)
        val key = hex("b4b3a95b72ddfab1277455bffd7e55ae63eabf58e7345713250af9e731151b1a")
        Crypto.Keys(key, ByteArray(32), byteArrayOf(), Protocol.LEGACY_VERSION).use { keys ->
            expect(StegoError.AUTHENTICATION_FAILED) { Crypto.decrypt(ciphertext.copyOf().also { it[0] = 0 }, keys, header, 96, 80) }
            expect(StegoError.AUTHENTICATION_FAILED) { Crypto.decrypt(ciphertext, keys, header, 80, 96) }
            expect(StegoError.AUTHENTICATION_FAILED) { Crypto.decrypt(ciphertext, keys, header.copy(bytes = header.bytes.copyOf().also { it[63] = 1 }), 96, 80) }
        }
        Crypto.Keys(ByteArray(32), ByteArray(32), byteArrayOf(), Protocol.LEGACY_VERSION).use { keys ->
            expect(StegoError.AUTHENTICATION_FAILED) { Crypto.decrypt(ciphertext, keys, header, 96, 80) }
        }
    }

    @Test fun capacityIncludesAllMetadataAndAuthenticationOverhead() {
        val maximum = Protocol.maxFileBytes(4000, 3000, "a".repeat(64))
        assertEquals(2249869, maximum)
        assertEquals(Protocol.capacity(4000, 3000, Protocol.LEGACY_VERSION) - 8, Protocol.capacity(4000, 3000))
        assertEquals(Protocol.capacity(4000, 3000), maximum + 64 + 11 + 16)
    }

    private fun expect(code: StegoError, action: () -> Unit) {
        try { action(); fail("应该拒绝无效输入") }
        catch (error: StegoException) { assertEquals(code, error.code) }
    }

    private fun hex(value: String) = value.chunked(2).map { it.toInt(16).toByte() }.toByteArray()
}
