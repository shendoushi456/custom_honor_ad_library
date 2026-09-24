package com.base.imagefilestego

/** 可稳定判断的失败原因；异常消息仅用于展示，不包含密码或文件内容。 */
enum class StegoError {
    INVALID_INPUT, UNSUPPORTED_IMAGE, NO_PAYLOAD, UNSUPPORTED_FORMAT,
    CAPACITY_EXCEEDED, MEMORY_LIMIT, AUTHENTICATION_FAILED, IO_ERROR
}

class StegoException(val code: StegoError, message: String, cause: Throwable? = null) :
    Exception(message, cause)

data class StegoOptions @JvmOverloads constructor(
    val allowTemporaryFiles: Boolean = true,
    val memoryThresholdBytes: Int = 8 * 1024 * 1024,
    val maxWorkingMemoryBytes: Long = 128L * 1024 * 1024,
    val maxPixels: Int = 24_000_000
) {
    init {
        require(memoryThresholdBytes in 1..8 * 1024 * 1024)
        require(maxWorkingMemoryBytes >= 1024 * 1024)
        require(maxPixels in 1..24_000_000)
    }
}

data class ImageInfo(
    val width: Int,
    val height: Int,
    val mimeType: String,
    val maxFileBytes: Long,
    val estimatedWorkingBytes: Long,
    val withinLimits: Boolean,
    val limitation: String?
)

enum class StegoStage {
    INSPECTING, READING, ENCRYPTING, EMBEDDING, ENCODING, VERIFYING,
    EXTRACTING, DECRYPTING, COMPLETE
}

enum class StegoMode { EMBED, EXTRACT }

data class StegoProgress(val stage: StegoStage, val fraction: Float)

fun interface StegoProgressListener {
    /** 在后台线程调用；界面更新需切回主线程。 */
    fun onProgress(progress: StegoProgress)
}

interface StegoCallback<T> {
    /** 成功后，结果的释放责任交给调用方。 */
    fun onSuccess(result: T)
    fun onError(error: StegoException)
    fun onCancelled()
}
