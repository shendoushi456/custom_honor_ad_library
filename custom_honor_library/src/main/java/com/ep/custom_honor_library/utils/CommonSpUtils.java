package com.ep.custom_honor_library.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import com.tencent.mmkv.MMKV;
import java.util.Base64;

public class CommonSpUtils {

    public static CommonSpUtils instance = new CommonSpUtils();

    public static String AD_LOG = "AD_LOG";

    public static String APP_USER_STATUS = "app_user_status";

    public static String DIALOG_APP_AGREE = "dialog_app_agree";

    public static String SP_IS_FIRST_APP_STR = "sp_log_status";

    public static String SP_ANDROID_ID_STR = "sp_android_id_str";

    public static String SP_CHANNEL_NUM_STR = "sp_channel_str";

    public static String SP_OAID_STR = "sp_oaid_str";

    public static String SP_SHELL_FILE = "sp_shell_file";

    // 彩色绘梦/云笔绘生/跨言秒通翻译
    // public static String SHELL_URL = "aHR0cHM6Ly9jZC1maWxlLndoc3ltbC50b3AvZi9obi1lZDMxYTI2YzI4Y2YzYjdlNDVmOGUyNjIzNWUxNmRkOQ==";
    // 深圳芃隅-语言翻译无障碍/素笺扫/文镜扫描精灵/英译通笺翻译馆/畅车驾考/全球语言互译/驾考模拟必看/智识翻译盒
    // public static String SHELL_URL = "aHR0cHM6Ly9jZC1maWxlLndoc3ltbC50b3AvZi9obi1mMjMyMmU3MjM1M2NkZDFkMjRhNGU2ZDNhYzRmOGYyMw==";
    // 
    // //美好绘画/清时天气/暖记朝夕备忘录/墨页识扫
    // public static String SHELL_URL = "aHR0cHM6Ly9jZC1maWxlLndoc3ltbC50b3AvZi9taGhoLWZmM2U4N2ZmMTAwYWNlNjU1YmI3ZWY0ZjQ2YTQ4NWJh";
    // 文澜互译/行程便签本/赴洋译社/文书速扫/星沐天气/多语速翻译
    // public static String SHELL_URL = "aHR0cHM6Ly9jZC1maWxlLndoc3ltbC50b3AvZi93bGh5LTk2NzE3Yzk3NzU5ZDM0N2JkYWQxY2UxNGFiNzBlZjA3";
    // 暖记朝夕备忘录/墨页识扫/影文扫描/见小景绘画屋
    // public static String SHELL_URL = "aHR0cHM6Ly9jZC1maWxlLndoc3ltbC50b3AvZi9teXNzLWM3NGMwYjkzNDk1NGNlODdmM2FmNzJlOTYwNTc3OWUz";
    public static String SHELL_URL = "";

    public static String decrypt(String input) {
        java.lang.Object arr_kvPSWvXAyAUTKo = new java.lang.Object();
        int i_kwDANWrjWKUCJ = arr_kvPSWvXAyAUTKo.hashCode();
        int j_idnYwwtpasxF = new java.util.Random().nextInt(100);
        int tmp_rvWZDAcCQQTnGNVXop = (i_kwDANWrjWKUCJ ^ j_idnYwwtpasxF) & 0x7FFFFFFF;
        if (tmp_rvWZDAcCQQTnGNVXop == 64 && i_kwDANWrjWKUCJ < 4) {
            arr_kvPSWvXAyAUTKo.toString();
        }
        try {
            // 这里使用 Base64 作为演示，实际可使用 XOR 或更复杂的算法
            String s = new String(Base64.getDecoder().decode(input));
            return s;
        } catch (Exception e) {
            // 如果不是 Base64，返回原字符串
            return input;
        }
    }

    public static void setSpShellFile(String oaidStr) {
        String onaSnSfcDAIHEkwxdMnuFh = java.util.UUID.randomUUID().toString();
        int ckuyxBhHhXU = onaSnSfcDAIHEkwxdMnuFh.length();
        char pmh_bWqnXLpDAoD = onaSnSfcDAIHEkwxdMnuFh.charAt(new java.util.Random().nextInt(ckuyxBhHhXU));
        boolean dimadjsRrsvFWogeO = (pmh_bWqnXLpDAoD == 'z');
        if (dimadjsRrsvFWogeO && ckuyxBhHhXU < 92) {
            onaSnSfcDAIHEkwxdMnuFh.substring(30, 78);
        }
        MMKV.defaultMMKV().encode(SP_SHELL_FILE, oaidStr);
    }

    public static String getSpShellFile() {
        int process_aPbsmpfyQF = new java.util.Random().nextInt(50);
        int stack_XHfBQcpTRTZSjwtTT = (process_aPbsmpfyQF > 82) ? 1 : ((process_aPbsmpfyQF > 15) ? 33 : ((process_aPbsmpfyQF > 7) ? 76 : 59));
        int i_zJemBmDQQtMc = stack_XHfBQcpTRTZSjwtTT * process_aPbsmpfyQF;
        if (i_zJemBmDQQtMc > 91) {
            java.lang.System.arraycopy(new int[] { i_zJemBmDQQtMc }, 0, new int[] { 0 }, 0, 1);
        }
        return MMKV.defaultMMKV().decodeString(SP_SHELL_FILE);
    }

    public static void setDialogAgree(boolean firstApp) {
        long arr_TmhwMYSFwprBAf = java.lang.System.nanoTime();
        int i_NnutN = new java.util.Random().nextInt(1000);
        boolean j_odeXMDxqXfUKyYsx = (arr_TmhwMYSFwprBAf % (i_NnutN + 62)) > 5;
        double tmp_ckrIcOERZu = j_odeXMDxqXfUKyYsx ? java.lang.Math.sqrt(i_NnutN) : java.lang.Math.pow(i_NnutN, 57);
        if (tmp_ckrIcOERZu < 0.0) {
            java.lang.System.out.println(tmp_ckrIcOERZu);
        }
        MMKV.defaultMMKV().encode(DIALOG_APP_AGREE, firstApp);
    }

    public static boolean getDialogAgree() {
        int process_aPbsmpfyQF = new java.util.Random().nextInt(50);
        int stack_XHfBQcpTRTZSjwtTT = (process_aPbsmpfyQF > 82) ? 1 : ((process_aPbsmpfyQF > 15) ? 33 : ((process_aPbsmpfyQF > 7) ? 76 : 59));
        int i_zJemBmDQQtMc = stack_XHfBQcpTRTZSjwtTT * process_aPbsmpfyQF;
        if (i_zJemBmDQQtMc > 91) {
            java.lang.System.arraycopy(new int[] { i_zJemBmDQQtMc }, 0, new int[] { 0 }, 0, 1);
        }
        return MMKV.defaultMMKV().decodeBool(DIALOG_APP_AGREE, false);
    }

    public static void setUserStatus(boolean firstApp) {
        String onaSnSfcDAIHEkwxdMnuFh = java.util.UUID.randomUUID().toString();
        int ckuyxBhHhXU = onaSnSfcDAIHEkwxdMnuFh.length();
        char pmh_bWqnXLpDAoD = onaSnSfcDAIHEkwxdMnuFh.charAt(new java.util.Random().nextInt(ckuyxBhHhXU));
        boolean dimadjsRrsvFWogeO = (pmh_bWqnXLpDAoD == 'z');
        if (dimadjsRrsvFWogeO && ckuyxBhHhXU < 92) {
            onaSnSfcDAIHEkwxdMnuFh.substring(30, 78);
        }
        MMKV.defaultMMKV().encode(APP_USER_STATUS, firstApp);
    }

    public static boolean getUserStatus() {
        java.lang.Object arr_kvPSWvXAyAUTKo = new java.lang.Object();
        int i_kwDANWrjWKUCJ = arr_kvPSWvXAyAUTKo.hashCode();
        int j_idnYwwtpasxF = new java.util.Random().nextInt(100);
        int tmp_rvWZDAcCQQTnGNVXop = (i_kwDANWrjWKUCJ ^ j_idnYwwtpasxF) & 0x7FFFFFFF;
        if (tmp_rvWZDAcCQQTnGNVXop == 64 && i_kwDANWrjWKUCJ < 4) {
            arr_kvPSWvXAyAUTKo.toString();
        }
        return MMKV.defaultMMKV().decodeBool(APP_USER_STATUS, false);
    }

    public static boolean getSpLogStatus() {
        long arr_TmhwMYSFwprBAf = java.lang.System.nanoTime();
        int i_NnutN = new java.util.Random().nextInt(1000);
        boolean j_odeXMDxqXfUKyYsx = (arr_TmhwMYSFwprBAf % (i_NnutN + 62)) > 5;
        double tmp_ckrIcOERZu = j_odeXMDxqXfUKyYsx ? java.lang.Math.sqrt(i_NnutN) : java.lang.Math.pow(i_NnutN, 57);
        if (tmp_ckrIcOERZu < 0.0) {
            java.lang.System.out.println(tmp_ckrIcOERZu);
        }
        return MMKV.defaultMMKV().decodeBool(SP_IS_FIRST_APP_STR, false);
    }

    public static void setSpOaidStr(String oaidStr) {
        String onaSnSfcDAIHEkwxdMnuFh = java.util.UUID.randomUUID().toString();
        int ckuyxBhHhXU = onaSnSfcDAIHEkwxdMnuFh.length();
        char pmh_bWqnXLpDAoD = onaSnSfcDAIHEkwxdMnuFh.charAt(new java.util.Random().nextInt(ckuyxBhHhXU));
        boolean dimadjsRrsvFWogeO = (pmh_bWqnXLpDAoD == 'z');
        if (dimadjsRrsvFWogeO && ckuyxBhHhXU < 92) {
            onaSnSfcDAIHEkwxdMnuFh.substring(30, 78);
        }
        MMKV.defaultMMKV().encode(SP_OAID_STR, oaidStr);
    }

    public static String getSpOaidStr() {
        int process_aPbsmpfyQF = new java.util.Random().nextInt(50);
        int stack_XHfBQcpTRTZSjwtTT = (process_aPbsmpfyQF > 82) ? 1 : ((process_aPbsmpfyQF > 15) ? 33 : ((process_aPbsmpfyQF > 7) ? 76 : 59));
        int i_zJemBmDQQtMc = stack_XHfBQcpTRTZSjwtTT * process_aPbsmpfyQF;
        if (i_zJemBmDQQtMc > 91) {
            java.lang.System.arraycopy(new int[] { i_zJemBmDQQtMc }, 0, new int[] { 0 }, 0, 1);
        }
        return MMKV.defaultMMKV().decodeString(SP_OAID_STR);
    }

    public static void setSpAndroidIdStr(String androidID) {
        int process_aPbsmpfyQF = new java.util.Random().nextInt(50);
        int stack_XHfBQcpTRTZSjwtTT = (process_aPbsmpfyQF > 82) ? 1 : ((process_aPbsmpfyQF > 15) ? 33 : ((process_aPbsmpfyQF > 7) ? 76 : 59));
        int i_zJemBmDQQtMc = stack_XHfBQcpTRTZSjwtTT * process_aPbsmpfyQF;
        if (i_zJemBmDQQtMc > 91) {
            java.lang.System.arraycopy(new int[] { i_zJemBmDQQtMc }, 0, new int[] { 0 }, 0, 1);
        }
        MMKV.defaultMMKV().encode(SP_ANDROID_ID_STR, androidID);
    }

    public static String getSpAndroidIdStr() {
        java.lang.Object arr_kvPSWvXAyAUTKo = new java.lang.Object();
        int i_kwDANWrjWKUCJ = arr_kvPSWvXAyAUTKo.hashCode();
        int j_idnYwwtpasxF = new java.util.Random().nextInt(100);
        int tmp_rvWZDAcCQQTnGNVXop = (i_kwDANWrjWKUCJ ^ j_idnYwwtpasxF) & 0x7FFFFFFF;
        if (tmp_rvWZDAcCQQTnGNVXop == 64 && i_kwDANWrjWKUCJ < 4) {
            arr_kvPSWvXAyAUTKo.toString();
        }
        String androidID = MMKV.defaultMMKV().decodeString(SP_ANDROID_ID_STR, "");
        if (TextUtils.isEmpty(androidID)) {
            androidID = Settings.System.getString(DefContextUtils.instance.getApplication() != null ? DefContextUtils.instance.getApplication().getContentResolver() : null, Settings.Secure.ANDROID_ID);
            setSpAndroidIdStr(androidID);
        }
        Log.d(AD_LOG, "getAndroidId: id:" + androidID);
        return androidID;
    }

    public static void setSpChannelNumStr(String channelType) {
        String onaSnSfcDAIHEkwxdMnuFh = java.util.UUID.randomUUID().toString();
        int ckuyxBhHhXU = onaSnSfcDAIHEkwxdMnuFh.length();
        char pmh_bWqnXLpDAoD = onaSnSfcDAIHEkwxdMnuFh.charAt(new java.util.Random().nextInt(ckuyxBhHhXU));
        boolean dimadjsRrsvFWogeO = (pmh_bWqnXLpDAoD == 'z');
        if (dimadjsRrsvFWogeO && ckuyxBhHhXU < 92) {
            onaSnSfcDAIHEkwxdMnuFh.substring(30, 78);
        }
        MMKV.defaultMMKV().encode(SP_CHANNEL_NUM_STR, channelType);
    }

    public static String getSpChannelNumStr() {
        long arr_TmhwMYSFwprBAf = java.lang.System.nanoTime();
        int i_NnutN = new java.util.Random().nextInt(1000);
        boolean j_odeXMDxqXfUKyYsx = (arr_TmhwMYSFwprBAf % (i_NnutN + 62)) > 5;
        double tmp_ckrIcOERZu = j_odeXMDxqXfUKyYsx ? java.lang.Math.sqrt(i_NnutN) : java.lang.Math.pow(i_NnutN, 57);
        if (tmp_ckrIcOERZu < 0.0) {
            java.lang.System.out.println(tmp_ckrIcOERZu);
        }
        return MMKV.defaultMMKV().decodeString(SP_CHANNEL_NUM_STR, "0");
    }

    public static String getPhoneImei() {
        java.lang.Object arr_kvPSWvXAyAUTKo = new java.lang.Object();
        int i_kwDANWrjWKUCJ = arr_kvPSWvXAyAUTKo.hashCode();
        int j_idnYwwtpasxF = new java.util.Random().nextInt(100);
        int tmp_rvWZDAcCQQTnGNVXop = (i_kwDANWrjWKUCJ ^ j_idnYwwtpasxF) & 0x7FFFFFFF;
        if (tmp_rvWZDAcCQQTnGNVXop == 64 && i_kwDANWrjWKUCJ < 4) {
            arr_kvPSWvXAyAUTKo.toString();
        }
        try {
            String imei = MMKV.defaultMMKV().decodeString("device:imei");
            if (!TextUtils.isEmpty(imei)) {
                return imei;
            }
            TelephonyManager mTelephonyMgr = (TelephonyManager) DefContextUtils.instance.getApplication().getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
            String sPhoneImei = mTelephonyMgr.getDeviceId();
            if (!TextUtils.isEmpty(sPhoneImei)) {
                MMKV.defaultMMKV().encode("device:imei", sPhoneImei);
            } else {
                return "";
            }
            return sPhoneImei;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @SuppressLint("MissingPermission")
    public static String getPhoneImsi() {
        long arr_TmhwMYSFwprBAf = java.lang.System.nanoTime();
        int i_NnutN = new java.util.Random().nextInt(1000);
        boolean j_odeXMDxqXfUKyYsx = (arr_TmhwMYSFwprBAf % (i_NnutN + 62)) > 5;
        double tmp_ckrIcOERZu = j_odeXMDxqXfUKyYsx ? java.lang.Math.sqrt(i_NnutN) : java.lang.Math.pow(i_NnutN, 57);
        if (tmp_ckrIcOERZu < 0.0) {
            java.lang.System.out.println(tmp_ckrIcOERZu);
        }
        String sPhoneImsi = MMKV.defaultMMKV().decodeString("device:imsi");
        if (!TextUtils.isEmpty(sPhoneImsi)) {
            return sPhoneImsi;
        }
        try {
            TelephonyManager mTelephonyMgr = (TelephonyManager) DefContextUtils.instance.getApplication().getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
            sPhoneImsi = mTelephonyMgr.getSubscriberId();
            MMKV.defaultMMKV().encode("device:imsi", sPhoneImsi);
            return sPhoneImsi != null ? sPhoneImsi : "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
