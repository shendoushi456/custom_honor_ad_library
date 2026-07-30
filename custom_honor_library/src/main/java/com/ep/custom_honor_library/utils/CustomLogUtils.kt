package com.ep.custom_honor_library.utils

import android.util.Log


object CustomLogUtils {

    /** 单段最大字符数，预留 tag/序号/前缀空间，取 4000 比较安全 */
    private const val MAX_LENGTH = 4000

    /** 是否输出调用位置信息（文件名:行号），发布版本可置为 false */
    var enableTrace = true

    private const val TAG_DEFAULT = "AD_LOG"

    // ───────────────────────────── 对外 API ─────────────────────────────

    @JvmStatic
    fun v(msg: String?, tag: String = TAG_DEFAULT) = log(Log.VERBOSE, tag, msg)

    @JvmStatic
    fun d(msg: String?, tag: String = TAG_DEFAULT) = log(Log.DEBUG, tag, msg)
    @JvmStatic
    fun i(msg: String?) = i(msg,TAG_DEFAULT)
    @JvmStatic
    fun i(msg: String?, tag: String = TAG_DEFAULT) = log(Log.INFO, tag, msg)

    @JvmStatic
    fun w(msg: String?, tag: String = TAG_DEFAULT) = log(Log.WARN, tag, msg)


    @JvmStatic
    fun e(msg: String?, tag: String = TAG_DEFAULT, tr: Throwable? = null) {
        val content = buildString {
            append(msg ?: "null")
            tr?.let {
                append("\n").append(Log.getStackTraceString(it))
            }
        }
        log(Log.ERROR, tag, content)
    }

    /**
     * 打印超长日志，强制按 [MAX_LENGTH] 切片（不保留原始换行结构）。
     * 适合打印 JSON、大段文本等连续字符串。
     */
    @JvmStatic
    fun long(msg: String?, level: Int = Log.DEBUG, tag: String = TAG_DEFAULT) {
        if (msg.isNullOrEmpty()) {
            print(level, tag, "null")
            return
        }
        val total = msg.length
        var index = 0
        var part = 0
        while (index < total) {
            val end = (index + MAX_LENGTH).coerceAtMost(total)
            val chunk = msg.substring(index, end)
            print(level, tag, "[${++part}/${(total + MAX_LENGTH - 1) / MAX_LENGTH}] $chunk")
            index = end
        }
    }

    // ───────────────────────────── 内部实现 ─────────────────────────────

    private fun log(level: Int, tag: String, msg: String?) {
        val content = msg ?: "null"
        val prefix = if (enableTrace) "${getTraceInfo()} " else ""
        if (content.length <= MAX_LENGTH) {
            print(level, tag, prefix + content)
            return
        }
        // 优先按换行符切分，保持可读性；单行长于阈值时再强制切片
        val lines = content.split("\n")
        val sb = StringBuilder()
        var part = 0
        val totalParts = estimateParts(lines)
        for (line in lines) {
            if (line.length > MAX_LENGTH) {
                // 单行超长，单独切片
                if (sb.isNotEmpty()) {
                    print(level, tag, prefix + "[${++part}/$totalParts] " + sb)
                    sb.clear()
                }
                long(line, level, tag)
            } else if (sb.length + line.length + 1 > MAX_LENGTH) {
                print(level, tag, prefix + "[${++part}/$totalParts] " + sb)
                sb.clear().append(line)
            } else {
                if (sb.isNotEmpty()) sb.append("\n")
                sb.append(line)
            }
        }
        if (sb.isNotEmpty()) {
            print(level, tag, prefix + "[${++part}/$totalParts] " + sb)
        }
    }

    private fun estimateParts(lines: List<String>): Int {
        var count = 0
        var len = 0
        for (line in lines) {
            if (line.length > MAX_LENGTH) {
                count += (line.length + MAX_LENGTH - 1) / MAX_LENGTH
                len = 0
            } else if (len + line.length + 1 > MAX_LENGTH) {
                count++
                len = line.length
            } else {
                len += if (len == 0) line.length else line.length + 1
            }
        }
        if (len > 0) count++
        return count.coerceAtLeast(1)
    }

    private fun print(level: Int, tag: String, msg: String) {
        when (level) {
            Log.VERBOSE -> Log.v(tag, msg)
            Log.DEBUG -> Log.d(tag, msg)
            Log.INFO -> Log.i(tag, msg)
            Log.WARN -> Log.w(tag, msg)
            Log.ERROR -> Log.e(tag, msg)
            else -> Log.d(tag, msg)
        }
    }

    /** 从当前调用栈中找到 LogUtil 之外的最近一帧，返回 (文件名:行号) 及方法名 */
    private fun getTraceInfo(): String {
        val stack = Throwable().stackTrace
        // stack[0]=getTraceInfo, stack[1]=log, stack[2]=v/d/i/w/e, stack[3]=调用者
        for (i in stack.indices) {
            val element = stack[i]
            if (element.className != CustomLogUtils::class.java.name &&
                element.className != "LogUtil" // 兼容 Java 编译后的匿名/静态形式
            ) {
                val className = element.className.substringAfterLast('.')
                return "($className.${element.methodName}:${element.lineNumber})"
            }
        }
        return ""
    }
}
