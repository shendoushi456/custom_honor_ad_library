package com.base.imagefilestego

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import java.io.ByteArrayInputStream
import java.io.InputStream

/** 每次打开必须得到同一份内容的新流。SDK 会关闭自己打开的流。 */
abstract class StegoSource(val displayName: String, val sizeBytes: Long?) {
    abstract fun openStream(): InputStream

    fun interface StreamFactory {
        fun open(): InputStream
    }

    companion object {
        /** 查询文档元数据可能产生 I/O，建议在后台线程创建。 */
        @JvmStatic
        fun fromUri(context: Context, uri: Uri): StegoSource {
            val resolver = context.applicationContext.contentResolver
            var name = "file.bin"
            var size: Long? = null
            resolver.query(uri, arrayOf(OpenableColumns.DISPLAY_NAME, OpenableColumns.SIZE), null, null, null)?.use {
                if (it.moveToFirst()) {
                    val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    val sizeIndex = it.getColumnIndex(OpenableColumns.SIZE)
                    if (nameIndex >= 0 && !it.isNull(nameIndex)) name = it.getString(nameIndex)
                    if (sizeIndex >= 0 && !it.isNull(sizeIndex)) size = it.getLong(sizeIndex).takeIf { value -> value >= 0 }
                }
            }
            return fromStream(name, size ?: -1) {
                resolver.openInputStream(uri) ?: throw java.io.IOException("无法打开所选文件")
            }
        }

        /** 为避免额外副本，不复制数组；操作完成前调用方不得修改数组。 */
        @JvmStatic
        @JvmOverloads
        fun fromBytes(bytes: ByteArray, displayName: String = "file.bin"): StegoSource =
            fromStream(displayName, bytes.size.toLong()) { ByteArrayInputStream(bytes) }

        @JvmStatic
        @JvmOverloads
        fun fromStream(displayName: String, sizeBytes: Long = -1, factory: StreamFactory): StegoSource =
            object : StegoSource(displayName, sizeBytes.takeIf { it >= 0 }) {
                override fun openStream(): InputStream = factory.open()
            }
    }
}
