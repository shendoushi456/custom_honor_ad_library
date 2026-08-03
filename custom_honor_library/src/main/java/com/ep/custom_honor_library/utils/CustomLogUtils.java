package com.ep.custom_honor_library.utils;

import android.util.Log;

public final class CustomLogUtils {
    private static final int MAX_LENGTH = 4000;
    public static boolean enableTrace = false;
    private static final String TAG_DEFAULT = "AD_LOG";

    private CustomLogUtils() {
        // 私有构造，防止实例化
    }


    public static void v(String msg) {
        v(msg, TAG_DEFAULT);
    }

    public static void v(String msg, String tag) {
        log(Log.VERBOSE, tag, msg);
    }

    public static void d(String msg) {
        d(msg, TAG_DEFAULT);
    }

    public static void d(String msg, String tag) {
        log(Log.DEBUG, tag, msg);
    }

    public static void i(String msg) {
        i(msg, TAG_DEFAULT);
    }

    public static void i(String msg, String tag) {
        log(Log.INFO, tag, msg);
    }

    public static void w(String msg) {
        w(msg, TAG_DEFAULT);
    }

    public static void w(String msg, String tag) {
        log(Log.WARN, tag, msg);
    }

    public static void e(String msg) {
        e(msg, TAG_DEFAULT, null);
    }

    public static void e(String msg, String tag) {
        e(msg, tag, null);
    }

    public static void e(String msg, String tag, Throwable tr) {
        StringBuilder sb = new StringBuilder();
        sb.append(msg != null ? msg : "null");
        if (tr != null) {
            sb.append("\n").append(Log.getStackTraceString(tr));
        }
        log(Log.ERROR, tag, sb.toString());
    }

    public static void longLog(String msg) {
        longLog(msg, Log.DEBUG, TAG_DEFAULT);
    }

    public static void longLog(String msg, int level) {
        longLog(msg, level, TAG_DEFAULT);
    }

    /**
     * 打印超长日志，强制按 [MAX_LENGTH] 切片（不保留原始换行结构）。
     * 适合打印 JSON、大段文本等连续字符串。
     */
    public static void longLog(String msg, int level, String tag) {
        if (msg == null || msg.isEmpty()) {
            print(level, tag, "null");
            return;
        }
        int total = msg.length();
        int index = 0;
        int part = 0;
        while (index < total) {
            int end = Math.min(index + MAX_LENGTH, total);
            String chunk = msg.substring(index, end);
            print(level, tag, "[" + (++part) + "/" + ((total + MAX_LENGTH - 1) / MAX_LENGTH) + "] " + chunk);
            index = end;
        }
    }

    // ───────────────────────────── 内部实现 ─────────────────────────────

    private static void log(int level, String tag, String msg) {
        String content = msg != null ? msg : "null";
        String prefix = enableTrace ? getTraceInfo() + " " : "";
        if (content.length() <= MAX_LENGTH) {
            print(level, tag, prefix + content);
            return;
        }
        // 优先按换行符切分，保持可读性；单行长于阈值时再强制切片
        String[] lines = content.split("\n");
        StringBuilder sb = new StringBuilder();
        int part = 0;
        int totalParts = estimateParts(lines);
        for (String line : lines) {
            if (line.length() > MAX_LENGTH) {
                // 单行超长，单独切片
                if (sb.length() > 0) {
                    print(level, tag, prefix + "[" + (++part) + "/" + totalParts + "] " + sb);
                    sb.setLength(0);
                }
                longLog(line, level, tag);
            } else if (sb.length() + line.length() + 1 > MAX_LENGTH) {
                print(level, tag, prefix + "[" + (++part) + "/" + totalParts + "] " + sb);
                sb.setLength(0);
                sb.append(line);
            } else {
                if (sb.length() > 0) sb.append("\n");
                sb.append(line);
            }
        }
        if (sb.length() > 0) {
            print(level, tag, prefix + "[" + (++part) + "/" + totalParts + "] " + sb);
        }
    }

    private static int estimateParts(String[] lines) {
        int count = 0;
        int len = 0;
        for (String line : lines) {
            if (line.length() > MAX_LENGTH) {
                count += (line.length() + MAX_LENGTH - 1) / MAX_LENGTH;
                len = 0;
            } else if (len + line.length() + 1 > MAX_LENGTH) {
                count++;
                len = line.length();
            } else {
                len += len == 0 ? line.length() : line.length() + 1;
            }
        }
        if (len > 0) count++;
        return Math.max(count, 1);
    }



    //此处可以改成 C代码打印
    private static void print(int level, String tag, String msg) {
        switch (level) {
            case Log.VERBOSE:
                Log.v(tag, msg);
                break;
            case Log.DEBUG:
                Log.d(tag, msg);
                break;
            case Log.INFO:
                Log.i(tag, msg);
                break;
            case Log.WARN:
                Log.w(tag, msg);
                break;
            case Log.ERROR:
                Log.e(tag, msg);
                break;
            default:
                Log.d(tag, msg);
                break;
        }
    }

    /** 从当前调用栈中找到 LogUtil 之外的最近一帧，返回 (文件名:行号) 及方法名 */
    private static String getTraceInfo() {
        StackTraceElement[] stack = new Throwable().getStackTrace();
        // stack[0]=getTraceInfo, stack[1]=log, stack[2]=v/d/i/w/e, stack[3]=调用者
        for (StackTraceElement element : stack) {
            String className = element.getClassName();
            if (!className.equals(CustomLogUtils.class.getName())
                    && !className.equals("LogUtil") // 兼容 Java 编译后的匿名/静态形式
            ) {
                String simpleName = className.substring(className.lastIndexOf('.') + 1);
                return "(" + simpleName + "." + element.getMethodName() + ":" + element.getLineNumber() + ")";
            }
        }
        return "";
    }
}
