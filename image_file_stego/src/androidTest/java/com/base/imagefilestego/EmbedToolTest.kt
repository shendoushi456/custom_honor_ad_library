package com.base.imagefilestego

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertArrayEquals
import org.junit.Assume.assumeTrue
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File
import java.io.FileOutputStream
import java.security.MessageDigest
import kotlinx.coroutines.runBlocking

/**
 * 一次性工具测试（非回归测试）：把 androidTest assets/stego_work 下的载体图片与 so
 * 用密码 123456 嵌入生成 home_banner.png / home_banner2.png，落盘后从磁盘重新读取
 * 提取，校验提取内容与原始 so 的 MD5 完全一致。
 *
 * 输入（src/androidTest/assets/stego_work/，输入缺失的用例自动跳过）：
 *   img2.jpg   载体图片（1920x1080）
 *   main.so    要嵌入的主 so（生成 home_banner.png，对应 app assets 同名文件）
 *   shell.so   要嵌入的 shell so（生成 home_banner2.png，对应 ShellSoLoader 的 assets 文件）
 * 输出（instrumentation 应用外部私有目录 stego_work/）：
 *   home_banner.png  / home_banner2.png
 *
 * 运行：
 *   ./gradlew :image_file_stego:connectedDebugAndroidTest \
 *     -Pandroid.testInstrumentationRunnerArguments.class=com.base.imagefilestego.EmbedToolTest
 *
 * 各 MD5 通过 logcat tag "EmbedTool" 输出。
 */
@RunWith(AndroidJUnit4::class)
class EmbedToolTest {

    private val instrumentation = InstrumentationRegistry.getInstrumentation()

    private fun md5(bytes: ByteArray) =
        MessageDigest.getInstance("MD5").digest(bytes).joinToString("") { "%02x".format(it) }

    /** 通用流程：assetName 的 so 嵌入 img2.jpg 生成 outputName，回读提取并逐字节校验 */
    private fun embedAndVerify(assetName: String, outputName: String): Unit = runBlocking {
        val assets = instrumentation.context.assets
        val original = try {
            assets.open("stego_work/$assetName").use { it.readBytes() }
        } catch (e: Exception) {
            assumeTrue("assets/stego_work/$assetName 不存在，跳过（本测试为按需运行的工具）", false)
            return@runBlocking
        }

        val cover = StegoSource.fromStream("img2.jpg", -1L) {
            assets.open("stego_work/img2.jpg")
        }
        val payload = StegoSource.fromStream(assetName, original.size.toLong()) {
            assets.open("stego_work/$assetName")
        }

        val sdk = ImageFileStego(instrumentation.targetContext)
        val info = sdk.inspect(cover, assetName)
        Log.i(TAG, "载体 img2.jpg: ${info.width}x${info.height}, 容量上限=${info.maxFileBytes}, " +
                "载荷=${original.size} 字节, withinLimits=${info.withinLimits}")
        assumeTrue("载体容量不足或超限: ${info.limitation}", info.withinLimits)

        val outDir = File(instrumentation.context.getExternalFilesDir(null), "stego_work")
        outDir.mkdirs()
        val output = File(outDir, outputName)
        sdk.embed(cover, payload, PASSWORD).use { png ->
            FileOutputStream(output).use { out -> png.writeTo(out) }
        }
        Log.i(TAG, "产物 ${output.absolutePath} (${output.length()} 字节), pngMd5=${md5(output.readBytes())}")
        Log.i(TAG, "原始 $assetName md5=${md5(original)}")

        // 回读校验：从磁盘重新读取生成的 PNG，提取内容与原始 so 逐字节比对
        val outSource = StegoSource.fromStream(output.name, output.length()) { output.inputStream() }
        sdk.extract(outSource, PASSWORD).use { recovered ->
            val recoveredBytes = recovered.openStream().readBytes()
            Log.i(TAG, "提取文件名=${recovered.fileName}, 提取大小=${recoveredBytes.size}, " +
                    "提取md5=${md5(recoveredBytes)}")
            assertArrayEquals("提取内容与原始 $assetName 不一致", original, recoveredBytes)
        }
        Log.i(TAG, "验证通过：$outputName 提取内容与原始 $assetName 完全一致")
    }

    /** 主 so（libchlcore.so）→ splash_bg.jpg，app 侧 StegoSoLoader 使用 */
    @Test fun embedMainSo() = embedAndVerify("main.so", "splash_bg.jpg")

    companion object {
        private const val TAG = "EmbedTool"
        // 与壳侧 BuildConfig.KEY_SEED / config.gradle keySeed 同源
        private val PASSWORD = "ea1eff10d457007bfc541642b52276bc".toCharArray()
    }
}
