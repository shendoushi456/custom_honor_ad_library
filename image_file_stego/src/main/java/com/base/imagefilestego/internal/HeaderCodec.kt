package com.base.imagefilestego.internal

import com.base.imagefilestego.StegoError
import com.base.imagefilestego.StegoException
import java.io.Closeable
import java.security.SecureRandom
import java.util.BitSet

/** 负责头部认证、版本识别及密钥生命周期；新图片只写入 v2。 */
internal object HeaderCodec {
    class Opened(val header: Protocol.Header, val keys: Crypto.Keys, private val ownsKeys: Boolean = true) : Closeable {
        override fun close() { if (ownsKeys) keys.close() }
    }

    fun create(cipherSize: Int, password: CharArray, width: Int, height: Int,
               random: SecureRandom = SecureRandom(), check: () -> Unit): Opened {
        val bootstrap = ByteArray(Protocol.BOOTSTRAP_SIZE).also(random::nextBytes)
        check()
        val keys = Crypto.derive(password, bootstrap.copyOfRange(0, 16))
        try {
            check()
            val nonce = ByteArray(12).also(random::nextBytes)
            val metadata = Protocol.metadata(cipherSize, nonce, width, height)
            val encrypted = try { Crypto.encryptHeader(metadata, keys, bootstrap, width, height) }
            finally { metadata.fill(0) }
            val header = Protocol.Header(bootstrap + encrypted, bootstrap.copyOfRange(0, 16), nonce, cipherSize, Protocol.VERSION)
            return Opened(header, keys)
        } catch (error: Throwable) {
            keys.close()
            throw error
        }
    }

    fun read(plane: BitSet, password: CharArray, width: Int, height: Int,
             existingKeys: Crypto.Keys? = null, check: () -> Unit): Opened {
        check()
        val channels = width.toLong() * height * 3
        if (channels >= Protocol.HEADER_BITS) {
            try {
                return readV2(Protocol.readHeader(plane), password, width, height, existingKeys, check)
            } catch (error: StegoException) {
                // 优先认证 v2，避免随机 salt 恰好以 IFSG 开头时被误判为旧格式。
                if (existingKeys != null || error.code != StegoError.AUTHENTICATION_FAILED) throw error
            }
        }
        check()
        if (existingKeys == null && channels >= Protocol.LEGACY_HEADER_BITS) {
            val bytes = Protocol.readHeader(plane, Protocol.LEGACY_VERSION)
            if (Protocol.hasLegacyMagic(bytes)) {
                val header = try { Protocol.parseLegacyHeader(bytes, width, height) }
                catch (_: StegoException) { throw Crypto.authenticationFailure() }
                val keys = Crypto.derive(password, header.salt, Protocol.LEGACY_VERSION)
                try { check(); return Opened(header, keys) }
                catch (error: Throwable) { keys.close(); throw error }
            }
        }
        throw Crypto.authenticationFailure()
    }

    private fun readV2(bytes: ByteArray, password: CharArray, width: Int, height: Int,
                       existingKeys: Crypto.Keys?, check: () -> Unit): Opened {
        val bootstrap = bytes.copyOfRange(0, Protocol.BOOTSTRAP_SIZE)
        val keys = existingKeys ?: Crypto.derive(password, bootstrap.copyOfRange(0, 16))
        try {
            check()
            val metadata = Crypto.decryptHeader(bytes.copyOfRange(Protocol.BOOTSTRAP_SIZE, Protocol.HEADER_SIZE),
                keys, bootstrap, width, height)
            val header = try { Protocol.parseMetadata(bytes, metadata, width, height) }
            finally { metadata.fill(0) }
            return Opened(header, keys, ownsKeys = existingKeys == null)
        } catch (error: Throwable) {
            if (existingKeys == null) keys.close()
            throw error
        }
    }
}
