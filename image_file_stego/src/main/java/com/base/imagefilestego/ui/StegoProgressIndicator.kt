package com.base.imagefilestego.ui

import android.content.res.ColorStateList
import android.widget.ProgressBar
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import kotlin.math.roundToInt

/** 使用系统进度条，避开旧版 Material3 与 Compose Animation 的关键帧二进制兼容问题。 */
@Composable
internal fun StegoProgressIndicator(fraction: Float?, modifier: Modifier = Modifier) {
    val indicatorTint = ColorStateList.valueOf(MaterialTheme.colorScheme.primary.toArgb())
    val trackTint = ColorStateList.valueOf(MaterialTheme.colorScheme.surfaceVariant.toArgb())
    // 未知或无效进度显示循环动画；有效进度限定在 0 到 1 之间。
    val progress = fraction?.takeIf { it.isFinite() }?.coerceIn(0f, 1f)

    AndroidView(
        modifier = modifier.height(4.dp),
        factory = { context ->
            ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal).apply {
                max = 10_000
                setPadding(0, 0, 0, 0)
            }
        },
        update = { view ->
            view.progressTintList = indicatorTint
            view.indeterminateTintList = indicatorTint
            view.progressBackgroundTintList = trackTint
            // 同一个原生 View 随处理阶段切换模式，并保留系统进度条的无障碍信息。
            view.isIndeterminate = progress == null
            if (progress != null) {
                view.setProgress((progress * view.max).roundToInt(), false)
            }
        }
    )
}
