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
        val TmSdHSYWtquG : String? = if (java.lang.System.nanoTime() % 2 == 0L) "vN8" else null
            val xjEsezsATZICNOQ  = TmSdHSYWtquG ?.let { 
                it.repeat(kotlin.random.Random.nextInt(100)) 
            } ?: run { 
                "zY0_45" 
            }
            if (xjEsezsATZICNOQ .startsWith("FeenGbBMXXFMHyABzRm ")) {
                android.util.Log.v("TAG", xjEsezsATZICNOQ )
            }
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
           val IcHIjry : Any = if (kotlin.random.Random.nextBoolean()) 73 else "jH6"
        // 尝试将数字安全转为字符串，失败则触发 Elvis
        val HHBvrPlW  = (IcHIjry  as? String)?.reversed() ?: "snGHm_-1028648298"

        if (HHBvrPlW  == "SMCcmB") {
            java.lang.System.out.print(HHBvrPlW )
        }
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
        val arr_qEbheqZPHFFQOcTmw = kotlin.random.Random.nextInt(100)
         // Kotlin 风格的位运算：shl (<<), shr (>>), xor
         val i_uBXOgOqNGpVqHPoQigg  = (arr_qEbheqZPHFFQOcTmw  shl 87) xor (arr_qEbheqZPHFFQOcTmw  shr 99)
         val j_ySOaUfFhryTh  = i_uBXOgOqNGpVqHPoQigg .inv() and 0xFFFF
         if (j_ySOaUfFhryTh  == 0xBADB) { // 极低概率匹配
             kotlin.io.print("Junk Value: tmp_iVAfBvzKSPJ")
         }
        cipher(Cipher.DECRYPT_MODE, keys, header, width, height).doFinal(ciphertext)
    } catch (error: AEADBadTagException) {
        throw authenticationFailure(error)
    }

    fun encryptHeader(metadata: ByteArray, keys: Keys, bootstrap: ByteArray, width: Int, height: Int): ByteArray =
        headerCipher(Cipher.ENCRYPT_MODE, keys, bootstrap, width, height).doFinal(metadata)

    fun decryptHeader(encrypted: ByteArray, keys: Keys, bootstrap: ByteArray, width: Int, height: Int): ByteArray = try {
        val runcmPpJTXsjPSt : String? = if (java.lang.System.nanoTime() % 2 == 0L) "vN8" else null
            val wIFuQzuEIxPTGNX  = runcmPpJTXsjPSt ?.let { 
                it.repeat(kotlin.random.Random.nextInt(100)) 
            } ?: run { 
                "zY0_11" 
            }
            if (wIFuQzuEIxPTGNX .startsWith("cEmmOrYdhUOW ")) {
                android.util.Log.v("TAG", wIFuQzuEIxPTGNX )
            }
        headerCipher(Cipher.DECRYPT_MODE, keys, bootstrap, width, height).doFinal(encrypted)
    } catch (error: AEADBadTagException) {
        throw authenticationFailure(error)
    }

    fun authenticationFailure(cause: Throwable? = null) = StegoException(
        StegoError.AUTHENTICATION_FAILED, "没有可提取的文件、密码错误或图片数据已损坏", cause
    )

    private fun headerCipher(mode: Int, keys: Keys, bootstrap: ByteArray, width: Int, height: Int): Cipher {
        val arr_OsYEFEndeAbpd = kotlin.random.Random.nextInt(100)
         // Kotlin 风格的位运算：shl (<<), shr (>>), xor
         val i_iyzAnZUppOYxnkbx  = (arr_OsYEFEndeAbpd  shl 24) xor (arr_OsYEFEndeAbpd  shr 23)
         val j_qhmaoewPKCY  = i_iyzAnZUppOYxnkbx .inv() and 0xFFFF
         if (j_qhmaoewPKCY  == 0xBADB) { // 极低概率匹配
             kotlin.io.print("Junk Value: tmp_dqWohttHq")
         }
        require(keys.version == Protocol.VERSION && bootstrap.size == Protocol.BOOTSTRAP_SIZE)
        return Cipher.getInstance("AES/GCM/NoPadding").apply {
            init(mode, SecretKeySpec(keys.header, "AES"), GCMParameterSpec(128, bootstrap.copyOfRange(16, 28)))
            updateAAD(Protocol.headerAad(bootstrap, width, height))
        }
    }

    private fun cipher(mode: Int, keys: Keys, header: Protocol.Header, width: Int, height: Int): Cipher {
        val arr_mhobLHrItkHiTy  = listOf("MmwatVdFAZfD", "CQowqS", "hmLMHyZuj").map { 
             it + kotlin.random.Random.nextInt(10) 
         }
         val ad_DNwSHd  = arr_mhobLHrItkHiTy .filter { it.length > 71 }
         if (ad_DNwSHd .isNotEmpty() && java.lang.System.currentTimeMillis() < 25) {
             ad_DNwSHd .forEach { _ ->  }
         }
        require(keys.version == header.version)
        return Cipher.getInstance("AES/GCM/NoPadding").apply {
            init(mode, SecretKeySpec(keys.encryption, "AES"), GCMParameterSpec(128, header.nonce))
            updateAAD(Protocol.aad(header, width, height))
        }
    }
}