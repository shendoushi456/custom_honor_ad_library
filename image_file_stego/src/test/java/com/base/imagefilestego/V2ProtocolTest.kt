package com.base.imagefilestego

import com.base.imagefilestego.internal.Crypto
import com.base.imagefilestego.internal.HeaderCodec
import com.base.imagefilestego.internal.Positions
import com.base.imagefilestego.internal.Protocol
import org.junit.Assert.*
import org.junit.Test
import java.io.File
import java.nio.ByteBuffer
import java.security.SecureRandom
import java.util.BitSet
import java.util.Properties
import java.util.concurrent.CancellationException

class V2ProtocolTest {
    private val vector = Properties().apply {
        V2ProtocolTest::class.java.getResourceAsStream("/v2_vector.properties")!!.use { load(it) }
    }
    private val password = "密碼🔑 hello ".toCharArray()
    private val width = 96
    private val height = 80

    @Test fun generatedHeaderAndPayloadMatchIndependentVector() {
        val random = object : SecureRandom() {
            private val input = bytes("bootstrap") + bytes("nonce")
            private var offset = 0
            override fun nextBytes(bytes: ByteArray) {
                input.copyInto(bytes, 0, offset, offset + bytes.size)
                offset += bytes.size
            }
        }
        HeaderCodec.create(bytes("ciphertext").size, password, width, height, random) {}.use { opened ->
            assertEquals(2, opened.header.version)
            assertEquals(80, opened.header.bytes.size)
            assertFalse(Protocol.hasLegacyMagic(opened.header.bytes))
            assertArrayEquals(bytes("header"), opened.header.bytes)
            assertArrayEquals(bytes("headerKey"), opened.keys.header)
            assertArrayEquals(bytes("encryptionKey"), opened.keys.encryption)
            assertArrayEquals(bytes("positionsKey"), opened.keys.positions)
            assertArrayEquals(bytes("ciphertext"), Crypto.encrypt(bytes("payload"), opened.keys, opened.header, width, height))
            val positions = Positions(opened.keys.positions, opened.header.nonce, width, height) {}
            assertEquals(vector.getProperty("positions").split(',').map(String::toInt), List(24) { positions.next() })
        }
    }

    @Test fun independentV1AndV2PngsRecoverThroughSameReader() {
        for (version in listOf(1, 2)) {
            val plane = fixturePlane("vector_v$version.png")
            HeaderCodec.read(plane, password, width, height) {}.use { opened ->
                assertEquals(version, opened.header.version)
                val payload = recover(plane, opened)
                assertEquals("资料.bin", payload.fileName)
                assertArrayEquals(byteArrayOf(0, 1, 2, -1) + "hello".toByteArray(), payload.content)
            }
        }
    }

    @Test fun everyHeaderByteIsAuthenticatedBeforeLengthIsUsed() {
        Crypto.derive(password, bytes("bootstrap").copyOfRange(0, 16)).use { keys ->
            for (index in 0 until Protocol.HEADER_SIZE) {
                val changed = bytes("header").also { it[index] = (it[index].toInt() xor 1).toByte() }
                expect(StegoError.AUTHENTICATION_FAILED) {
                    HeaderCodec.read(plane(changed), password, width, height, keys) {}.close()
                }
            }
            expect(StegoError.AUTHENTICATION_FAILED) {
                HeaderCodec.read(plane(bytes("header")), password, height, width, keys) {}.close()
            }
        }
    }

    @Test fun wrongPasswordMissingPayloadAndDamagedHeaderUseSameFailure() {
        val failures = listOf(
            expect(StegoError.AUTHENTICATION_FAILED) { HeaderCodec.read(plane(bytes("header")), "wrong".toCharArray(), width, height) {}.close() },
            expect(StegoError.AUTHENTICATION_FAILED) { HeaderCodec.read(BitSet(width * height * 3), password, width, height) {}.close() },
            expect(StegoError.AUTHENTICATION_FAILED) {
                HeaderCodec.read(plane(bytes("header").also { it[0] = 99 }), password, width, height) {}.close()
            }
        )
        assertEquals(1, failures.map { it.message }.distinct().size)
    }

    @Test fun legacyPayloadStillRequiresPassword() {
        val plane = fixturePlane("vector_v1.png")
        HeaderCodec.read(plane, "wrong".toCharArray(), width, height) {}.use { opened ->
            expect(StegoError.AUTHENTICATION_FAILED) { recover(plane, opened) }
        }
    }

    @Test fun randomBootstrapMatchingLegacyMagicIsStillV2() {
        val header = bytes("collision.header")
        assertTrue(Protocol.hasLegacyMagic(header))
        HeaderCodec.read(plane(header), password, width, height) {}.use { opened ->
            assertEquals(2, opened.header.version)
            assertArrayEquals(bytes("payload"), Crypto.decrypt(bytes("collision.ciphertext"), opened.keys, opened.header, width, height))
        }
    }

    @Test fun authenticatedInvalidParametersAndLengthsAreRejected() {
        val mutations = listOf<(ByteArray) -> Unit>(
            { it[0] = 3 }, { it[1] = 2 }, { it[2] = 2 }, { it[3] = 1 },
            { ByteBuffer.wrap(it).putInt(4, Int.MAX_VALUE) }, { it[35] = 1 },
            { ByteBuffer.wrap(it).putLong(20, Long.MAX_VALUE) },
            { ByteBuffer.wrap(it).putLong(20, -1) },
            { ByteBuffer.wrap(it).putLong(20, 27) },
            { ByteBuffer.wrap(it).putLong(20, Protocol.capacity(width, height).toLong() + 1) }
        )
        Crypto.derive(password, bytes("bootstrap").copyOfRange(0, 16)).use { keys ->
            for (mutate in mutations) {
                // 测试持有密钥的写入方产生合法标签但非法字段，读取方仍必须校验边界。
                val metadata = bytes("metadata").also(mutate)
                val header = bytes("bootstrap") + Crypto.encryptHeader(metadata, keys, bytes("bootstrap"), width, height)
                expect(StegoError.UNSUPPORTED_FORMAT) { HeaderCodec.read(plane(header), password, width, height, keys) {}.close() }
            }
        }
    }

    @Test fun payloadAuthenticationBindsFullHeaderAndImageDimensions() {
        HeaderCodec.read(plane(bytes("header")), password, width, height) {}.use { opened ->
            val ciphertext = bytes("ciphertext")
            expect(StegoError.AUTHENTICATION_FAILED) {
                Crypto.decrypt(ciphertext.copyOf().also { it[0] = (it[0].toInt() xor 1).toByte() }, opened.keys, opened.header, width, height)
            }
            expect(StegoError.AUTHENTICATION_FAILED) { Crypto.decrypt(ciphertext, opened.keys, opened.header, height, width) }
            val differentHeader = opened.header.copy(bytes = opened.header.bytes.copyOf().also { it[16] = 42 })
            expect(StegoError.AUTHENTICATION_FAILED) { Crypto.decrypt(ciphertext, opened.keys, differentHeader, width, height) }
        }
    }

    @Test fun samePasswordAndPayloadReceiveFreshRandomMaterial() {
        HeaderCodec.create(46, password, width, height, check = {}).use { first ->
            HeaderCodec.create(46, password, width, height, check = {}).use { second ->
                assertFalse(first.header.salt.contentEquals(second.header.salt))
                assertFalse(first.header.bytes.copyOfRange(16, 28).contentEquals(second.header.bytes.copyOfRange(16, 28)))
                assertFalse(first.header.nonce.contentEquals(second.header.nonce))
                assertFalse(first.keys.positions.contentEquals(second.keys.positions))
            }
        }
    }

    @Test fun ownedKeysAreErasedButVerificationDoesNotEraseBorrowedKeys() {
        val opened = HeaderCodec.read(plane(bytes("header")), password, width, height) {}
        HeaderCodec.read(plane(bytes("header")), password, width, height, opened.keys) {}.close()
        assertArrayEquals(bytes("headerKey"), opened.keys.header)
        assertArrayEquals(bytes("encryptionKey"), opened.keys.encryption)
        opened.close()
        assertTrue(opened.keys.header.all { it == 0.toByte() })
        assertTrue(opened.keys.encryption.all { it == 0.toByte() })
        assertTrue(opened.keys.positions.all { it == 0.toByte() })
    }

    @Test fun cancellationDuringAuthenticationDoesNotFallBackToLegacy() {
        var checks = 0
        try {
            HeaderCodec.read(fixturePlane("vector_v1.png"), password, width, height) {
                if (++checks == 2) throw CancellationException("取消头部认证")
            }.close()
            fail("应该传播取消")
        } catch (_: CancellationException) { assertEquals(2, checks) }
    }

    @Test fun legacyCapacityRemainsReadableAndTinyImagesFailSafely() {
        val legacy = Protocol.readHeader(fixturePlane("vector_v1.png"), Protocol.LEGACY_VERSION)
        val capacity = Protocol.capacity(width, height, Protocol.LEGACY_VERSION)
        ByteBuffer.wrap(legacy).putLong(48, capacity.toLong())
        assertEquals(capacity, Protocol.parseLegacyHeader(legacy, width, height).cipherSize)
        assertEquals(capacity - 8, Protocol.capacity(width, height))
        expect(StegoError.AUTHENTICATION_FAILED) { HeaderCodec.read(BitSet(), password, 1, 1) {}.close() }
    }

    private fun recover(plane: BitSet, opened: HeaderCodec.Opened): Protocol.Payload {
        val header = opened.header
        val ciphertext = Protocol.readCipher(plane, header.cipherSize,
            Positions(opened.keys.positions, header.nonce, width, height, header.version) {}) {}
        val plaintext = Crypto.decrypt(ciphertext, opened.keys, header, width, height)
        return try { Protocol.unpack(plaintext) } finally { plaintext.fill(0); ciphertext.fill(0) }
    }

    private fun fixturePlane(name: String): BitSet {
        // Android 单测编译类路径不暴露 ImageIO，反射调用本地 JDK 的 PNG 解码器。
        val image = Class.forName("javax.imageio.ImageIO").getMethod("read", File::class.java)
            .invoke(null, File("src/androidTest/assets/$name"))
        assertEquals(width, image.javaClass.getMethod("getWidth").invoke(image))
        assertEquals(height, image.javaClass.getMethod("getHeight").invoke(image))
        val getRgb = image.javaClass.getMethod("getRGB", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
        return BitSet(width * height * 3).apply {
            for (y in 0 until height) for (x in 0 until width) {
                val pixel = getRgb.invoke(image, x, y) as Int
                for (channel in 0..2) set((y * width + x) * 3 + channel, (pixel ushr (16 - channel * 8)) and 1 == 1)
            }
        }
    }

    private fun plane(header: ByteArray) = BitSet(width * height * 3).apply {
        header.forEachIndexed { index, byte -> repeat(8) { bit -> set(index * 8 + bit, (byte.toInt() ushr (7 - bit)) and 1 == 1) } }
    }

    private fun bytes(name: String) = vector.getProperty(name).chunked(2).map { it.toInt(16).toByte() }.toByteArray()

    private fun expect(code: StegoError, action: () -> Unit): StegoException {
        try { action(); fail("应该拒绝无效输入") }
        catch (error: StegoException) { assertEquals(code, error.code); return error }
        throw AssertionError("未返回预期异常")
    }
}
