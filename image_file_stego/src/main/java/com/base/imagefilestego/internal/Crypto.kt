package com.base.imagefilestego.internal

import com.base.imagefilestego.StegoError
import com.base.imagefilestego.StegoException
import java.io.Closeable
import javax.crypto.AEADBadTagException
import javax.crypto.Cipher
import javax.crypto.Mac
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

internal object Crypto {
    class Keys(val encryption: ByteArray, val positions: ByteArray, val header: ByteArray, val version: Int) : Closeable {
        override fun close() { encryption.fill(0); positions.fill(0); header.fill(0) }
    }

    fun derive(password: CharArray, salt: ByteArray, version: Int = Protocol.VERSION): Keys {
        require(version == Protocol.VERSION || version == Protocol.LEGACY_VERSION)
        require(salt.size == 16)
        if (password.isEmpty()) throw StegoException(StegoError.INVALID_INPUT, "密码不能为空")
        // JCA 的 SHA-256 PBKDF2 使用 UTF-8 密码编码，不进行 trim 或 Unicode 归一化。
        val spec = PBEKeySpec(password, salt, Protocol.ITERATIONS, 256)
        val master = try {
            SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).encoded
        } finally { spec.clearPassword() }
        try {
            return Keys(hkdf(master, "image-file/v$version/encryption"), hkdf(master, "image-file/v$version/positions"),
                if (version == Protocol.VERSION) hkdf(master, "image-file/v2/header") else byteArrayOf(), version)
        } finally { master.fill(0) }
    }

    private fun hkdf(master: ByteArray, label: String): ByteArray {
        val mac = Mac.getInstance("HmacSHA256")
        mac.init(SecretKeySpec(ByteArray(32), "HmacSHA256"))
        val prk = mac.doFinal(master)
        try {
            mac.init(SecretKeySpec(prk, "HmacSHA256"))
            return mac.doFinal(label.toByteArray(Charsets.US_ASCII) + byteArrayOf(1))
        } finally { prk.fill(0) }
    }

    fun encrypt(payload: ByteArray, keys: Keys, header: Protocol.Header, width: Int, height: Int): ByteArray =
        cipher(Cipher.ENCRYPT_MODE, keys, header, width, height).doFinal(payload)

    fun decrypt(ciphertext: ByteArray, keys: Keys, header: Protocol.Header, width: Int, height: Int): ByteArray = try {
        cipher(Cipher.DECRYPT_MODE, keys, header, width, height).doFinal(ciphertext)
    } catch (error: AEADBadTagException) {
        throw authenticationFailure(error)
    }

    fun encryptHeader(metadata: ByteArray, keys: Keys, bootstrap: ByteArray, width: Int, height: Int): ByteArray =
        headerCipher(Cipher.ENCRYPT_MODE, keys, bootstrap, width, height).doFinal(metadata)

    fun decryptHeader(encrypted: ByteArray, keys: Keys, bootstrap: ByteArray, width: Int, height: Int): ByteArray = try {
        headerCipher(Cipher.DECRYPT_MODE, keys, bootstrap, width, height).doFinal(encrypted)
    } catch (error: AEADBadTagException) {
        throw authenticationFailure(error)
    }

    fun authenticationFailure(cause: Throwable? = null) = StegoException(
        StegoError.AUTHENTICATION_FAILED, "没有可提取的文件、密码错误或图片数据已损坏", cause
    )

    private fun headerCipher(mode: Int, keys: Keys, bootstrap: ByteArray, width: Int, height: Int): Cipher {
        require(keys.version == Protocol.VERSION && bootstrap.size == Protocol.BOOTSTRAP_SIZE)
        return Cipher.getInstance("AES/GCM/NoPadding").apply {
            init(mode, SecretKeySpec(keys.header, "AES"), GCMParameterSpec(128, bootstrap.copyOfRange(16, 28)))
            updateAAD(Protocol.headerAad(bootstrap, width, height))
        }
    }

    private fun cipher(mode: Int, keys: Keys, header: Protocol.Header, width: Int, height: Int): Cipher {
        require(keys.version == header.version)
        return Cipher.getInstance("AES/GCM/NoPadding").apply {
            init(mode, SecretKeySpec(keys.encryption, "AES"), GCMParameterSpec(128, header.nonce))
            updateAAD(Protocol.aad(header, width, height))
        }
    }
}
