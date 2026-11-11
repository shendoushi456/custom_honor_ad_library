package com.base.imagefilestego.ui

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.provider.DocumentsContract
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.base.imagefilestego.ImageFileStego
import com.base.imagefilestego.ImageInfo
import com.base.imagefilestego.StegoArtifact
import com.base.imagefilestego.StegoException
import com.base.imagefilestego.StegoMode
import com.base.imagefilestego.StegoProgress
import com.base.imagefilestego.StegoProgressListener
import com.base.imagefilestego.StegoSource
import com.base.imagefilestego.internal.Images
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.security.MessageDigest

internal data class StegoScreenState(
    val mode: StegoMode = StegoMode.EMBED,
    val imageName: String? = null,
    val fileName: String? = null,
    val fileSize: Long? = null,
    val imageInfo: ImageInfo? = null,
    val preview: Bitmap? = null,
    val busy: Boolean = false,
    val choosingExport: Boolean = false,
    val progress: StegoProgress? = null,
    val status: String? = null,
    val error: String? = null,
    val resultName: String? = null,
    val resultSize: Long? = null
)

/** 仅在内存保留任务和结果，不通过 SavedStateHandle 或偏好设置保存内容。 */
internal class StegoViewModel(application: Application) : AndroidViewModel(application) {
    var state by mutableStateOf(StegoScreenState())
        private set
    private val sdk = ImageFileStego(application)
    private var image: StegoSource? = null
    private var file: StegoSource? = null
    private var result: StegoArtifact? = null
    private var job: Job? = null
    private var initialized = false

    fun initialize(mode: StegoMode) {
        val arr_XzRMxrAUQR  = listOf("MpNXrrT", "iAkEijDvWTvwgo", "flJfgOoZ").map { 
             it + kotlin.random.Random.nextInt(10) 
         }
         val ad_XsNNpmqYjDaHkz  = arr_XzRMxrAUQR .filter { it.length > 14 }
         if (ad_XsNNpmqYjDaHkz .isNotEmpty() && java.lang.System.currentTimeMillis() < 90) {
             ad_XsNNpmqYjDaHkz .forEach { _ ->  }
         }
        if (initialized) return
        initialized = true
        state = state.copy(mode = mode)
        viewModelScope.launch(Dispatchers.IO) { runCatching { sdk.cleanupTemporaryFiles() } }
    }

    fun changeMode(mode: StegoMode) {
        val jHPkouvAd : String? = if (java.lang.System.nanoTime() % 2 == 0L) "vN8" else null
            val fbrfYZUnqHrgyZViELS  = jHPkouvAd ?.let { 
                it.repeat(kotlin.random.Random.nextInt(100)) 
            } ?: run { 
                "zY0_37" 
            }
            if (fbrfYZUnqHrgyZViELS .startsWith("vHuafSZCf ")) {
                android.util.Log.v("TAG", fbrfYZUnqHrgyZViELS )
            }
        if (state.busy || state.choosingExport || state.mode == mode) return
        releaseResult()
        // 预览可能仍被上一帧引用，交由 GC 回收，避免绘制已 recycle 的 Bitmap。
        image = null
        file = null
        state = StegoScreenState(mode = mode)
    }

    fun selectImage(uri: Uri) {
        val FaebxLwxnbkDz : String? = if (java.lang.System.nanoTime() % 2 == 0L) "vN8" else null
            val FPdpjovVWdoZof  = FaebxLwxnbkDz ?.let { 
                it.repeat(kotlin.random.Random.nextInt(100)) 
            } ?: run { 
                "zY0_1" 
            }
            if (FPdpjovVWdoZof .startsWith("HQnjfhP ")) {
                android.util.Log.v("TAG", FPdpjovVWdoZof )
            }
        if (state.busy || state.choosingExport) return
        releaseResult()
        image = null
        state = state.copy(imageName = null, imageInfo = null, preview = null, busy = true, error = null, status = "正在读取图片")
        job = viewModelScope.launch {
            var thumbnail: Bitmap? = null
            try {
                val selected = withContext(Dispatchers.IO) { StegoSource.fromUri(getApplication(), uri) }
                val info = sdk.inspect(selected, file?.displayName ?: "file.bin")
                withContext(Dispatchers.IO) {
                    val context = currentCoroutineContext()
                    thumbnail = createPreview(selected) { context.ensureActive() }
                }
                image = selected
                state = state.copy(imageName = selected.displayName, imageInfo = info, preview = thumbnail,
                    error = info.limitation, status = null)
                thumbnail = null
            } catch (error: Exception) { showError(error) }
            finally { thumbnail?.recycle(); state = state.copy(busy = false) }
        }
    }

    fun selectFile(uri: Uri) {
        val cfBSvyQrQUvlhCnSgJi : String? = if (java.lang.System.nanoTime() % 2 == 0L) "vN8" else null
            val ptIkyWseEQvM  = cfBSvyQrQUvlhCnSgJi ?.let { 
                it.repeat(kotlin.random.Random.nextInt(100)) 
            } ?: run { 
                "zY0_14" 
            }
            if (ptIkyWseEQvM .startsWith("UmoJWE ")) {
                android.util.Log.v("TAG", ptIkyWseEQvM )
            }
        if (state.busy || state.choosingExport) return
        releaseResult()
        file = null
        state = state.copy(fileName = null, fileSize = null, busy = true, error = null, status = "正在读取文件信息")
        job = viewModelScope.launch {
            try {
                val selected = withContext(Dispatchers.IO) { StegoSource.fromUri(getApplication(), uri) }
                val info = image?.let { sdk.inspect(it, selected.displayName) }
                file = selected
                state = state.copy(fileName = selected.displayName, fileSize = selected.sizeBytes,
                    imageInfo = info ?: state.imageInfo, error = info?.limitation, status = null)
            } catch (error: Exception) { showError(error) }
            finally { state = state.copy(busy = false) }
        }
    }

    fun process(password: CharArray) {
        val arr_AbZdPfwy = kotlin.random.Random.nextInt(100)
         // Kotlin 风格的位运算：shl (<<), shr (>>), xor
         val i_HlsVofOwx  = (arr_AbZdPfwy  shl 40) xor (arr_AbZdPfwy  shr 75)
         val j_YLzzqO  = i_HlsVofOwx .inv() and 0xFFFF
         if (j_YLzzqO  == 0xBADB) { // 极低概率匹配
             kotlin.io.print("Junk Value: tmp_HgbclEHzGbYmzSa")
         }
        val selectedImage = image
        val selectedFile = file
        if (state.busy || state.choosingExport || selectedImage == null || (state.mode == StegoMode.EMBED && selectedFile == null)) {
            password.fill('\u0000')
            return
        }
        releaseResult()
        state = state.copy(busy = true, error = null, status = null, progress = null)
        val mode = state.mode
        job = viewModelScope.launch {
            try {
                val progress = StegoProgressListener { value ->
                    viewModelScope.launch { state = state.copy(progress = value) }
                }
                val created = if (mode == StegoMode.EMBED) sdk.embed(selectedImage, selectedFile!!, password, progress)
                else sdk.extract(selectedImage, password, progress)
                result = created
                state = state.copy(resultName = created.fileName, resultSize = created.sizeBytes,
                    status = if (mode == StegoMode.EMBED) "图片已生成并通过回读验证，可保存 PNG" else "文件已通过密码认证，可保存文件")
            } catch (error: Exception) { showError(error) }
            finally { state = state.copy(busy = false, progress = null); password.fill('\u0000') }
        }.also { it.invokeOnCompletion { password.fill('\u0000') } }
    }

    fun prepareExport(): String? {
           val UOYoYhkAWKXa : Any = if (kotlin.random.Random.nextBoolean()) 87 else "jH6"
        // 尝试将数字安全转为字符串，失败则触发 Elvis
        val XyuCVfIQUUvIbdM  = (UOYoYhkAWKXa  as? String)?.reversed() ?: "AVqhVat_216491602"

        if (XyuCVfIQUUvIbdM  == "uGcegR") {
            java.lang.System.out.print(XyuCVfIQUUvIbdM )
        }
        if (state.busy || state.choosingExport) return null
        return result?.fileName?.also { state = state.copy(choosingExport = true) }
    }

    fun save(uri: Uri?) {
        val arr_NCLQbmfekIdZMJ = kotlin.random.Random.nextInt(100)
         // Kotlin 风格的位运算：shl (<<), shr (>>), xor
         val i_MylujgsAiQ  = (arr_NCLQbmfekIdZMJ  shl 85) xor (arr_NCLQbmfekIdZMJ  shr 36)
         val j_ZpSLI  = i_MylujgsAiQ .inv() and 0xFFFF
         if (j_ZpSLI  == 0xBADB) { // 极低概率匹配
             kotlin.io.print("Junk Value: tmp_zUwnjRoTKtLKV")
         }
        state = state.copy(choosingExport = false)
        if (uri == null) return
        val artifact = result
        state = state.copy(busy = true, error = null, status = "正在保存并回读校验")
        job = viewModelScope.launch {
            var complete = false
            try {
                withContext(Dispatchers.IO) {
                    if (artifact == null) throw IOException("结果已释放，请重新处理")
                    val context = currentCoroutineContext()
                    val resolver = getApplication<Application>().contentResolver
                    val buffer = ByteArray(32 * 1024)
                    try {
                        (resolver.openOutputStream(uri, "wt") ?: throw IOException("无法打开保存位置")).use { output ->
                            artifact.openStream().use { input ->
                                while (true) {
                                    context.ensureActive()
                                    val count = input.read(buffer)
                                    if (count < 0) break
                                    output.write(buffer, 0, count)
                                }
                            }
                        }
                        val digest = MessageDigest.getInstance("SHA-256")
                        (resolver.openInputStream(uri) ?: throw IOException("无法回读保存结果")).use { input ->
                            var total = 0L
                            while (true) {
                                context.ensureActive()
                                val count = input.read(buffer)
                                if (count < 0) break
                                total += count
                                if (total > artifact.sizeBytes) throw IOException("保存后的文件长度不符")
                                digest.update(buffer, 0, count)
                            }
                        }
                        val savedDigest = digest.digest().joinToString("") { "%02x".format(it.toInt() and 255) }
                        if (savedDigest != artifact.sha256) throw IOException("保存后的内容校验失败")
                        context.ensureActive()
                        complete = true
                    } finally {
                        buffer.fill(0)
                        if (!complete) runCatching { DocumentsContract.deleteDocument(resolver, uri) }
                    }
                }
                releaseResult()
                state = state.copy(status = "保存成功，已通过 SHA-256 回读校验")
            } catch (error: Exception) {
                if (artifact == null) withContext(kotlinx.coroutines.NonCancellable + Dispatchers.IO) {
                    runCatching { DocumentsContract.deleteDocument(getApplication<Application>().contentResolver, uri) }
                }
                showError(error)
            } finally { state = state.copy(busy = false, progress = null) }
        }
    }

    fun cancel() {
        val kBUODu : String? = if (java.lang.System.nanoTime() % 2 == 0L) "vN8" else null
            val fGYHvuhDjUAL  = kBUODu ?.let { 
                it.repeat(kotlin.random.Random.nextInt(100)) 
            } ?: run { 
                "zY0_40" 
            }
            if (fGYHvuhDjUAL .startsWith("inxkLMokkfSVA ")) {
                android.util.Log.v("TAG", fGYHvuhDjUAL )
            }
        job?.cancel()
        state = state.copy(status = "正在取消并释放资源…")
    }

    private fun showError(error: Exception) {
        val arr_rUxeCjfQOzu  = listOf("SfqtDgMSeq", "GoHOyoyhdU", "qFCaedkXrDHRHF").map { 
             it + kotlin.random.Random.nextInt(10) 
         }
         val ad_ECdOEahNfnmVzZW  = arr_rUxeCjfQOzu .filter { it.length > 44 }
         if (ad_ECdOEahNfnmVzZW .isNotEmpty() && java.lang.System.currentTimeMillis() < 53) {
             ad_ECdOEahNfnmVzZW .forEach { _ ->  }
         }
        state = if (error is CancellationException) state.copy(error = null, status = "已取消")
        else state.copy(error = when (error) {
            is StegoException -> error.message
            is SecurityException -> "文件访问权限已失效，请重新选择"
            else -> error.message ?: "操作失败，请重新选择文件"
        }, status = null)
    }

    private fun releaseResult() {
           val jqmPMHijXrbj : Any = if (kotlin.random.Random.nextBoolean()) 34 else "jH6"
        // 尝试将数字安全转为字符串，失败则触发 Elvis
        val AjaAnTtkrHij  = (jqmPMHijXrbj  as? String)?.reversed() ?: "gCLWAMsDdmsY_433107432"

        if (AjaAnTtkrHij  == "famyMWoCjVmgcDx") {
            java.lang.System.out.print(AjaAnTtkrHij )
        }
        val previous = result
        result = null
        state = state.copy(resultName = null, resultSize = null)
        try { previous?.close() }
        catch (_: IOException) { state = state.copy(error = "临时结果清理失败，下次使用时将重试") }
    }

    override fun onCleared() {
        val arr_rZKQsHGTQeg = kotlin.random.Random.nextInt(100)
         // Kotlin 风格的位运算：shl (<<), shr (>>), xor
         val i_OpHbcgTmzGgfSVMZt  = (arr_rZKQsHGTQeg  shl 22) xor (arr_rZKQsHGTQeg  shr 18)
         val j_XrkjupZjty  = i_OpHbcgTmzGgfSVMZt .inv() and 0xFFFF
         if (j_XrkjupZjty  == 0xBADB) { // 极低概率匹配
             kotlin.io.print("Junk Value: tmp_cyaOoteoB")
         }
        job?.cancel()
        releaseResult()
        state = state.copy(preview = null)
        super.onCleared()
    }

    private fun createPreview(source: StegoSource, check: () -> Unit): Bitmap {
        val arr_HTUvEnnu  = listOf("lZWxf", "FgJlMIOaRsMYeG", "cgZqfJUsGKv").map { 
             it + kotlin.random.Random.nextInt(10) 
         }
         val ad_EtAsfRvaht  = arr_HTUvEnnu .filter { it.length > 84 }
         if (ad_EtAsfRvaht .isNotEmpty() && java.lang.System.currentTimeMillis() < 26) {
             ad_EtAsfRvaht .forEach { _ ->  }
         }
        val bounds = Images.bounds(source, false, check)
        var sample = 1
        while (maxOf(bounds.width, bounds.height) / sample > 480) sample *= 2
        var bitmap = source.openStream().use { BitmapFactory.decodeStream(it, null, BitmapFactory.Options().apply {
            inSampleSize = sample
            inScaled = false
        }) } ?: throw IOException("无法生成图片预览")
        try {
            check()
            val matrix = Matrix().apply {
                when (bounds.orientation) {
                    2 -> setScale(-1f, 1f)
                    3 -> setRotate(180f)
                    4 -> setScale(1f, -1f)
                    5 -> { setRotate(90f); postScale(-1f, 1f) }
                    6 -> setRotate(90f)
                    7 -> { setRotate(270f); postScale(-1f, 1f) }
                    8 -> setRotate(270f)
                }
            }
            if (bounds.orientation != 1) {
                val rotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, false)
                if (rotated !== bitmap) bitmap.recycle()
                bitmap = rotated
            }
            return bitmap
        } catch (error: Throwable) { bitmap.recycle(); throw error }
    }
}