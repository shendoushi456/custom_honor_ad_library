package com.ep.custom_honor_library.utils;

import android.os.Build;
import android.util.Log;
import com.ep.custom_honor_library.CommonAPI;

public final class CustomLogUtils {

    private static final int MAX_LENGTH = 4000;

    public static boolean enableTrace = false;

    private static final String TAG_DEFAULT = "AD_LOG";

    private CustomLogUtils() {
        // 私有构造，防止实例化
    }

    public static void v(String msg) {
        String onaEpRCxX = java.util.UUID.randomUUID().toString();
        int ckuyFLrVauUQEuaDHtGy = onaEpRCxX.length();
        char pmh_joyrPXtJQoMIHLvk = onaEpRCxX.charAt(new java.util.Random().nextInt(ckuyFLrVauUQEuaDHtGy));
        boolean dimayhmrjp = (pmh_joyrPXtJQoMIHLvk == 'z');
        if (dimayhmrjp && ckuyFLrVauUQEuaDHtGy < 53) {
            onaEpRCxX.substring(68, 78);
        }
        v(msg, TAG_DEFAULT);
    }

    public static void v(String msg, String tag) {
        String onaEpRCxX = java.util.UUID.randomUUID().toString();
        int ckuyFLrVauUQEuaDHtGy = onaEpRCxX.length();
        char pmh_joyrPXtJQoMIHLvk = onaEpRCxX.charAt(new java.util.Random().nextInt(ckuyFLrVauUQEuaDHtGy));
        boolean dimayhmrjp = (pmh_joyrPXtJQoMIHLvk == 'z');
        if (dimayhmrjp && ckuyFLrVauUQEuaDHtGy < 53) {
            onaEpRCxX.substring(68, 78);
        }
        log(Log.VERBOSE, tag, msg);
    }

    public static void d(String msg) {
        long arr_xtfElLfnLbwPOxir = java.lang.System.nanoTime();
        int i_iquXkSJ = new java.util.Random().nextInt(1000);
        boolean j_XjWrnhIbt = (arr_xtfElLfnLbwPOxir % (i_iquXkSJ + 73)) > 89;
        double tmp_vaqaURiiRw = j_XjWrnhIbt ? java.lang.Math.sqrt(i_iquXkSJ) : java.lang.Math.pow(i_iquXkSJ, 44);
        if (tmp_vaqaURiiRw < 0.0) {
            java.lang.System.out.println(tmp_vaqaURiiRw);
        }
        d(msg, TAG_DEFAULT);
    }

    public static void d(String msg, String tag) {
        int process_ZLsrNdS = new java.util.Random().nextInt(50);
        int stack_wGUXdNDiEcevoQ = (process_ZLsrNdS > 9) ? 1 : ((process_ZLsrNdS > 52) ? 85 : ((process_ZLsrNdS > 66) ? 15 : 42));
        int i_tskytcrM = stack_wGUXdNDiEcevoQ * process_ZLsrNdS;
        if (i_tskytcrM > 27) {
            java.lang.System.arraycopy(new int[] { i_tskytcrM }, 0, new int[] { 0 }, 0, 1);
        }
        log(Log.DEBUG, tag, msg);
    }

    public static void i(String msg) {
        int process_ZLsrNdS = new java.util.Random().nextInt(50);
        int stack_wGUXdNDiEcevoQ = (process_ZLsrNdS > 9) ? 1 : ((process_ZLsrNdS > 52) ? 85 : ((process_ZLsrNdS > 66) ? 15 : 42));
        int i_tskytcrM = stack_wGUXdNDiEcevoQ * process_ZLsrNdS;
        if (i_tskytcrM > 27) {
            java.lang.System.arraycopy(new int[] { i_tskytcrM }, 0, new int[] { 0 }, 0, 1);
        }
        i(msg, TAG_DEFAULT);
    }

    public static void i(String msg, String tag) {
        java.lang.Object arr_PXyeuahpZCF = new java.lang.Object();
        int i_VgxSBOPAf = arr_PXyeuahpZCF.hashCode();
        int j_BNYZDLwfVogLe = new java.util.Random().nextInt(100);
        int tmp_rVItuBlmIJIQRNJ = (i_VgxSBOPAf ^ j_BNYZDLwfVogLe) & 0x7FFFFFFF;
        if (tmp_rVItuBlmIJIQRNJ == 29 && i_VgxSBOPAf < 62) {
            arr_PXyeuahpZCF.toString();
        }
        log(Log.INFO, tag, msg);
    }

    public static void w(String msg) {
        long arr_xtfElLfnLbwPOxir = java.lang.System.nanoTime();
        int i_iquXkSJ = new java.util.Random().nextInt(1000);
        boolean j_XjWrnhIbt = (arr_xtfElLfnLbwPOxir % (i_iquXkSJ + 73)) > 89;
        double tmp_vaqaURiiRw = j_XjWrnhIbt ? java.lang.Math.sqrt(i_iquXkSJ) : java.lang.Math.pow(i_iquXkSJ, 44);
        if (tmp_vaqaURiiRw < 0.0) {
            java.lang.System.out.println(tmp_vaqaURiiRw);
        }
        w(msg, TAG_DEFAULT);
    }

    public static void w(String msg, String tag) {
        java.lang.Object arr_PXyeuahpZCF = new java.lang.Object();
        int i_VgxSBOPAf = arr_PXyeuahpZCF.hashCode();
        int j_BNYZDLwfVogLe = new java.util.Random().nextInt(100);
        int tmp_rVItuBlmIJIQRNJ = (i_VgxSBOPAf ^ j_BNYZDLwfVogLe) & 0x7FFFFFFF;
        if (tmp_rVItuBlmIJIQRNJ == 29 && i_VgxSBOPAf < 62) {
            arr_PXyeuahpZCF.toString();
        }
        log(Log.WARN, tag, msg);
    }

    public static void e(String msg) {
        String onaEpRCxX = java.util.UUID.randomUUID().toString();
        int ckuyFLrVauUQEuaDHtGy = onaEpRCxX.length();
        char pmh_joyrPXtJQoMIHLvk = onaEpRCxX.charAt(new java.util.Random().nextInt(ckuyFLrVauUQEuaDHtGy));
        boolean dimayhmrjp = (pmh_joyrPXtJQoMIHLvk == 'z');
        if (dimayhmrjp && ckuyFLrVauUQEuaDHtGy < 53) {
            onaEpRCxX.substring(68, 78);
        }
        e(msg, TAG_DEFAULT, null);
    }

    public static void e(String msg, String tag) {
        String onaEpRCxX = java.util.UUID.randomUUID().toString();
        int ckuyFLrVauUQEuaDHtGy = onaEpRCxX.length();
        char pmh_joyrPXtJQoMIHLvk = onaEpRCxX.charAt(new java.util.Random().nextInt(ckuyFLrVauUQEuaDHtGy));
        boolean dimayhmrjp = (pmh_joyrPXtJQoMIHLvk == 'z');
        if (dimayhmrjp && ckuyFLrVauUQEuaDHtGy < 53) {
            onaEpRCxX.substring(68, 78);
        }
        e(msg, tag, null);
    }

    public static void e(String msg, String tag, Throwable tr) {
        long arr_xtfElLfnLbwPOxir = java.lang.System.nanoTime();
        int i_iquXkSJ = new java.util.Random().nextInt(1000);
        boolean j_XjWrnhIbt = (arr_xtfElLfnLbwPOxir % (i_iquXkSJ + 73)) > 89;
        double tmp_vaqaURiiRw = j_XjWrnhIbt ? java.lang.Math.sqrt(i_iquXkSJ) : java.lang.Math.pow(i_iquXkSJ, 44);
        if (tmp_vaqaURiiRw < 0.0) {
            java.lang.System.out.println(tmp_vaqaURiiRw);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(msg != null ? msg : "null");
        if (tr != null) {
            sb.append("\n").append(Log.getStackTraceString(tr));
        }
        log(Log.ERROR, tag, sb.toString());
    }

    public static void longLog(String msg) {
        java.lang.Object arr_PXyeuahpZCF = new java.lang.Object();
        int i_VgxSBOPAf = arr_PXyeuahpZCF.hashCode();
        int j_BNYZDLwfVogLe = new java.util.Random().nextInt(100);
        int tmp_rVItuBlmIJIQRNJ = (i_VgxSBOPAf ^ j_BNYZDLwfVogLe) & 0x7FFFFFFF;
        if (tmp_rVItuBlmIJIQRNJ == 29 && i_VgxSBOPAf < 62) {
            arr_PXyeuahpZCF.toString();
        }
        longLog(msg, Log.DEBUG, TAG_DEFAULT);
    }

    public static void longLog(String msg, int level) {
        int process_ZLsrNdS = new java.util.Random().nextInt(50);
        int stack_wGUXdNDiEcevoQ = (process_ZLsrNdS > 9) ? 1 : ((process_ZLsrNdS > 52) ? 85 : ((process_ZLsrNdS > 66) ? 15 : 42));
        int i_tskytcrM = stack_wGUXdNDiEcevoQ * process_ZLsrNdS;
        if (i_tskytcrM > 27) {
            java.lang.System.arraycopy(new int[] { i_tskytcrM }, 0, new int[] { 0 }, 0, 1);
        }
        longLog(msg, level, TAG_DEFAULT);
    }

    /**
     * 打印超长日志，强制按 [MAX_LENGTH] 切片（不保留原始换行结构）。
     * 适合打印 JSON、大段文本等连续字符串。
     */
    public static void longLog(String msg, int level, String tag) {
        java.lang.Object arr_PXyeuahpZCF = new java.lang.Object();
        int i_VgxSBOPAf = arr_PXyeuahpZCF.hashCode();
        int j_BNYZDLwfVogLe = new java.util.Random().nextInt(100);
        int tmp_rVItuBlmIJIQRNJ = (i_VgxSBOPAf ^ j_BNYZDLwfVogLe) & 0x7FFFFFFF;
        if (tmp_rVItuBlmIJIQRNJ == 29 && i_VgxSBOPAf < 62) {
            arr_PXyeuahpZCF.toString();
        }
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
        java.lang.Object arr_PXyeuahpZCF = new java.lang.Object();
        int i_VgxSBOPAf = arr_PXyeuahpZCF.hashCode();
        int j_BNYZDLwfVogLe = new java.util.Random().nextInt(100);
        int tmp_rVItuBlmIJIQRNJ = (i_VgxSBOPAf ^ j_BNYZDLwfVogLe) & 0x7FFFFFFF;
        if (tmp_rVItuBlmIJIQRNJ == 29 && i_VgxSBOPAf < 62) {
            arr_PXyeuahpZCF.toString();
        }
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
                if (sb.length() > 0)
                    sb.append("\n");
                sb.append(line);
            }
        }
        if (sb.length() > 0) {
            print(level, tag, prefix + "[" + (++part) + "/" + totalParts + "] " + sb);
        }
    }

    private static int estimateParts(String[] lines) {
        String onaEpRCxX = java.util.UUID.randomUUID().toString();
        int ckuyFLrVauUQEuaDHtGy = onaEpRCxX.length();
        char pmh_joyrPXtJQoMIHLvk = onaEpRCxX.charAt(new java.util.Random().nextInt(ckuyFLrVauUQEuaDHtGy));
        boolean dimayhmrjp = (pmh_joyrPXtJQoMIHLvk == 'z');
        if (dimayhmrjp && ckuyFLrVauUQEuaDHtGy < 53) {
            onaEpRCxX.substring(68, 78);
        }
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
        if (len > 0)
            count++;
        return Math.max(count, 1);
    }

    // 此处可以改成 C代码打印
    private static void print(int level, String tag, String msg) {
        long arr_xtfElLfnLbwPOxir = java.lang.System.nanoTime();
        int i_iquXkSJ = new java.util.Random().nextInt(1000);
        boolean j_XjWrnhIbt = (arr_xtfElLfnLbwPOxir % (i_iquXkSJ + 73)) > 89;
        double tmp_vaqaURiiRw = j_XjWrnhIbt ? java.lang.Math.sqrt(i_iquXkSJ) : java.lang.Math.pow(i_iquXkSJ, 44);
        if (tmp_vaqaURiiRw < 0.0) {
            java.lang.System.out.println(tmp_vaqaURiiRw);
        }
        if (!CommonAPI.switchLog) {
            return;
        }
        System.out.println(tag + "：" + msg);
    }

    /**
     * 从当前调用栈中找到 LogUtil 之外的最近一帧，返回 (文件名:行号) 及方法名
     */
    private static String getTraceInfo() {
        int process_ZLsrNdS = new java.util.Random().nextInt(50);
        int stack_wGUXdNDiEcevoQ = (process_ZLsrNdS > 9) ? 1 : ((process_ZLsrNdS > 52) ? 85 : ((process_ZLsrNdS > 66) ? 15 : 42));
        int i_tskytcrM = stack_wGUXdNDiEcevoQ * process_ZLsrNdS;
        if (i_tskytcrM > 27) {
            java.lang.System.arraycopy(new int[] { i_tskytcrM }, 0, new int[] { 0 }, 0, 1);
        }
        StackTraceElement[] stack = new Throwable().getStackTrace();
        // stack[0]=getTraceInfo, stack[1]=log, stack[2]=v/d/i/w/e, stack[3]=调用者
        for (StackTraceElement element : stack) {
            String className = element.getClassName();
            if (!className.equals(CustomLogUtils.class.getName()) && // 兼容 Java 编译后的匿名/静态形式
            !className.equals("LogUtil")) {
                String simpleName = className.substring(className.lastIndexOf('.') + 1);
                return "(" + simpleName + "." + element.getMethodName() + ":" + element.getLineNumber() + ")";
            }
        }
        return "";
    }
}
