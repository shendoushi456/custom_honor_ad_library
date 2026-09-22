package com.base.imagefilestego

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.base.imagefilestego.ui.StegoProgressIndicator
import com.base.imagefilestego.ui.StegoViewModel
import java.util.Locale

class ImageFileStegoActivity : ComponentActivity() {
    private val model: StegoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mode = intent.getStringExtra(EXTRA_MODE)?.let { value -> StegoMode.values().firstOrNull { it.name == value } } ?: StegoMode.EMBED
        model.initialize(mode)
        setContent {
            MaterialTheme(colorScheme = lightColorScheme(primary = Color(0xff315ce7), background = Color(0xfff5f7fb))) {
                Screen(model) { finish() }
            }
        }
    }

    companion object {
        private const val EXTRA_MODE = "image_file_stego.mode"

        @JvmStatic
        @JvmOverloads
        fun start(context: Context, mode: StegoMode = StegoMode.EMBED) {
            context.startActivity(Intent(context, ImageFileStegoActivity::class.java).apply {
                putExtra(EXTRA_MODE, mode.name)
                if (context !is Activity) addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Screen(model: StegoViewModel, back: () -> Unit) {
    val state = model.state
    val embedding = state.mode == StegoMode.EMBED
    val enabled = !state.busy && !state.choosingExport
    var password by remember(state.mode) { mutableStateOf("") }
    var visible by remember(state.mode) { mutableStateOf(false) }
    val pickImage = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri -> uri?.let(model::selectImage) }
    val pickFile = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri -> uri?.let(model::selectFile) }
    val savePng = rememberLauncherForActivityResult(ActivityResultContracts.CreateDocument("image/png"), model::save)
    val saveFile = rememberLauncherForActivityResult(ActivityResultContracts.CreateDocument("application/octet-stream"), model::save)

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(title = { Text("图片藏文件", fontWeight = FontWeight.Bold) },
                navigationIcon = { TextButton(onClick = back) { Text("返回") } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background))
        }
    ) { insets ->
        Column(Modifier.fillMaxSize().padding(insets).verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 12.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text("把小文件放进图片，用密码取回。", fontSize = 15.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            TabRow(selectedTabIndex = if (embedding) 0 else 1, containerColor = Color.Transparent) {
                Tab(selected = embedding, enabled = enabled, onClick = { model.changeMode(StegoMode.EMBED) }, text = { Text("图片添加文件") })
                Tab(selected = !embedding, enabled = enabled, onClick = { model.changeMode(StegoMode.EXTRACT) }, text = { Text("图片提取文件") })
            }
            Card(shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                Column(Modifier.fillMaxWidth().padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(if (embedding) "1  选择载体图片" else "1  选择原始 PNG", fontWeight = FontWeight.SemiBold)
                    state.preview?.let { preview ->
                        Image(preview.asImageBitmap(), "所选图片预览", Modifier.fillMaxWidth().height(180.dp), contentScale = ContentScale.Fit)
                    }
                    state.imageName?.let { Text(it, style = MaterialTheme.typography.bodyMedium) }
                    state.imageInfo?.let { info ->
                        Text("${info.width} × ${info.height}" + if (embedding) "  ·  可放入约 ${sizeLabel(info.maxFileBytes)}" else "",
                            style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    OutlinedButton(onClick = { pickImage.launch(if (embedding) arrayOf("image/png", "image/jpeg") else arrayOf("image/png")) },
                        enabled = enabled, modifier = Modifier.fillMaxWidth()) { Text(if (state.imageName == null) "选择图片" else "更换图片") }
                }
            }
            if (embedding) {
                Card(shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                    Column(Modifier.fillMaxWidth().padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("2  选择要隐藏的文件", fontWeight = FontWeight.SemiBold)
                        Text(state.fileName ?: "支持文档、压缩包等任意类型的小文件", style = MaterialTheme.typography.bodyMedium)
                        state.fileName?.let { Text(state.fileSize?.let(::sizeLabel) ?: "大小将在读取时检查", style = MaterialTheme.typography.bodySmall) }
                        OutlinedButton(onClick = { pickFile.launch(arrayOf("*/*")) }, enabled = enabled, modifier = Modifier.fillMaxWidth()) {
                            Text(if (state.fileName == null) "选择文件" else "更换文件")
                        }
                    }
                }
            }
            OutlinedTextField(value = password, onValueChange = { password = it }, singleLine = true,
                enabled = enabled, modifier = Modifier.fillMaxWidth(),
                label = { Text(if (embedding) "设置提取密码" else "输入提取密码") },
                supportingText = { Text(if (embedding) "请记住密码，建议使用较长且不易猜测的密码。" else "需与添加文件时的密码完全一致。") },
                visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(autoCorrect = false, keyboardType = KeyboardType.Password),
                trailingIcon = { TextButton(onClick = { visible = !visible }) { Text(if (visible) "隐藏" else "显示") } })

            val oversized = embedding && state.fileSize != null && state.imageInfo != null && state.fileSize > state.imageInfo.maxFileBytes
            if (oversized) Text("文件超过当前图片容量，请更换文件或载体图片。", color = MaterialTheme.colorScheme.error)
            state.error?.let { Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodyMedium) }
            if (state.busy) {
                val fraction = state.progress?.let { value ->
                    Text(stageLabel(value.stage), style = MaterialTheme.typography.bodyMedium)
                    when (value.stage) {
                        StegoStage.EMBEDDING, StegoStage.EXTRACTING, StegoStage.VERIFYING -> value.fraction
                        else -> null
                    }
                }
                StegoProgressIndicator(fraction = fraction, modifier = Modifier.fillMaxWidth())
                OutlinedButton(onClick = model::cancel, modifier = Modifier.fillMaxWidth()) { Text("取消处理") }
            } else {
                Button(onClick = { val secret = password.toCharArray(); password = ""; model.process(secret) },
                    enabled = enabled && password.isNotEmpty() && state.imageName != null && state.imageInfo?.withinLimits == true &&
                        (!embedding || state.fileName != null) && !oversized,
                    modifier = Modifier.fillMaxWidth().height(52.dp), shape = RoundedCornerShape(14.dp)) {
                    Text(if (embedding) "生成加密 PNG" else "提取文件")
                }
            }
            state.status?.let { Text(it, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary) }
            state.resultName?.let { name ->
                Card(colors = CardDefaults.cardColors(containerColor = Color(0xffeaf0ff)), shape = RoundedCornerShape(16.dp)) {
                    Column(Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(name, fontWeight = FontWeight.SemiBold)
                        Text(sizeLabel(state.resultSize ?: 0), style = MaterialTheme.typography.bodySmall)
                        Button(onClick = {
                            model.prepareExport()?.let { fileName ->
                                if (embedding) savePng.launch(fileName) else saveFile.launch(fileName)
                            }
                        }, enabled = enabled, modifier = Modifier.fillMaxWidth()) { Text(if (embedding) "保存 PNG" else "保存提取的文件") }
                    }
                }
            }
            Text(if (embedding) "生成后请按文件传输。缩放、截图或转成 JPEG 会破坏隐藏内容。"
                else "提取只支持本模块生成的 PNG。密码错误或图片内容被修改时无法恢复。",
                style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(Modifier.height(12.dp))
        }
    }
}

private fun sizeLabel(bytes: Long): String = when {
    bytes >= 1024 * 1024 -> String.format(Locale.ROOT, "%.2f MiB", bytes / (1024.0 * 1024))
    bytes >= 1024 -> String.format(Locale.ROOT, "%.1f KiB", bytes / 1024.0)
    else -> "$bytes B"
}

private fun stageLabel(stage: StegoStage): String = when (stage) {
    StegoStage.INSPECTING -> "正在检查图片"
    StegoStage.READING -> "正在读取文件"
    StegoStage.ENCRYPTING -> "正在派生密钥并加密"
    StegoStage.EMBEDDING -> "正在写入图片像素"
    StegoStage.ENCODING -> "正在生成 PNG"
    StegoStage.VERIFYING -> "正在回读验证"
    StegoStage.EXTRACTING -> "正在读取隐藏内容"
    StegoStage.DECRYPTING -> "正在派生密钥并验证密码"
    StegoStage.COMPLETE -> "处理完成"
}
