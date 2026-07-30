package com.ep.custom_honor_library.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.kwad.sdk.core.imageloader.core.download.ConnectionConfig;
import com.tencent.mmkv.MMKV;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

//import com.clean.common_ad_libaray.ut.ContextUtilsV;

public class PhoneStateUtils {

    public static String getPhoneState(String phoneState) {

        //返回1: 合格可以正常用；返回：102 无sim卡； 103 sd卡的截屏文件太多； 104 安装了华为内部IM
        int statusCheck = CheckPhoneStatus.check(DefContextUtils.instance.getApplication());

        if (!CommonAPI.switchLog) {
            switch (statusCheck) {
                case 102:
                    phoneState = "client_audit_nosim";
                    break;
                case 103:
                    phoneState = "client_audit_sdcard";
                    break;
                case 104:
                    phoneState = "client_audit_hw_im";
                    break;
            }


            //判断手机是否root 和 开始开发者模式
            if (isDebug()) {
                phoneState = "client_audit_isdebug";
            } else if (isDeviceRooted()) {
                phoneState = "client_audit_isroot";
            }else if (isRunningOnEmulator()){
                phoneState = "client_audit_simulator";
            }else if (isHasVPN()){
                phoneState = "client_audit_hasvpn";
            }
        }
        return phoneState;
    }




    private static boolean isHasVPN(){
        // Android Java 示例
        ConnectivityManager connectivityManager = (ConnectivityManager) DefContextUtils.instance.getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") Network activeNetwork = connectivityManager.getActiveNetwork();
        @SuppressLint("MissingPermission") NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork);
        if (networkCapabilities == null){
            return false;
        }
        return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN);
    }




    private static boolean isRunningOnEmulator() {
        // 检查是否有某些模拟器特有的系统属性
        if (Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.HARDWARE.contains("goldfish")
                || Build.HARDWARE.contains("ranchu")
                || Build.MODEL.contains("(sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MANUFACTURER.contains("Genymotion")
                || Build.MODEL.contains("Android SDK built for x86")) {
            return true;
        }

        // 如果以上都不满足，则不是模拟器
        return false;
    }


    private static boolean isDebug(){
        Settings.Global.getInt(DefContextUtils.instance.getApplication().getContentResolver(), Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0);
        return Settings.Secure.getInt(DefContextUtils.instance.getApplication().getContentResolver(), Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0) != 0;
    }

    public static boolean isDeviceRooted() {
        return checkSuFile() || checkSuCommand() || checkSuperuserApk() || isRooted() || isRoot();
    }


    public static boolean isRootManagerInstalled(Context context) {
        String[] rootApps = {
                "com.topjohnwu.magisk", // Magisk
                "eu.chainfire.supersu",  // SuperSU
                "com.kingroot.kinguser", // KingRoot
                "com.koushikdutta.superuser", // Superuser
                "me.weishu.kernelsu", // KernelSU
                "com.noshufou.android.su" // 经典 Root 授权管理
        };
        PackageManager pm = context.getPackageManager();
        for (String pkg : rootApps) {
            try {
                PackageInfo packageInfo = pm.getPackageInfo(pkg, PackageManager.GET_ACTIVITIES);
                return packageInfo != null;
            } catch (Exception ignored) {
            }
        }
        return false;
    }



    private static boolean checkSuFile() {
        File file = new File("/system/bin/su");
        return file.exists();
    }

    private static boolean checkSuCommand() {
        try {
            Process process = Runtime.getRuntime().exec("su");
            int exitValue = process.waitFor();
            return exitValue == 0;
        } catch (IOException e) {
            // 异常处理
        } catch (InterruptedException e) {
            // 异常处理
        }
        return false;
    }

    private static boolean checkSuperuserApk() {
        String secureProperty = "ro.secure";
        String propertyValue =  getSystemProperty(secureProperty);
        if ("0".equals(propertyValue)) {
            return true;
        }
        return false;
    }



    private static String getSystemProperty(String propertyName) {
        String propertyValue = "";
        try {
            Process process = Runtime.getRuntime().exec("getprop " + propertyName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            propertyValue = reader.readLine();
            reader.close();
            process.destroy();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return propertyValue;
    }

    public static boolean isRooted() {
        String[] cmd = {"su", "-c", "id"}; // 运行 su -c id 命令查看当前用户的 UID
        try {
            Process process = Runtime.getRuntime().exec(cmd);
            int exitValue = process.waitFor();
            return (exitValue == 0) ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    private static boolean isRoot() {
        String[] paths = {"/system/bin/su", "/system/xbin/su", "/sbin/su"}; // 常见的 su 命令路径
        for (String path : paths) {
            if (new File(path).exists())
                return true;
        }
        return false;
    }


    private static String sPhoneImei;

    @SuppressLint("MissingPermission")
    public static String getPhoneImei(Context context) {
        try {
            String imei = MMKV.defaultMMKV().decodeString("device:imei");
            if (!TextUtils.isEmpty(imei)) {
                return imei;
            }
            TelephonyManager mTelephonyMgr = (TelephonyManager) context.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
            sPhoneImei = mTelephonyMgr.getDeviceId();
            if (!TextUtils.isEmpty(sPhoneImei)) {
                MMKV.defaultMMKV().encode("device:imei",sPhoneImei);
            }else{
                sPhoneImei = "";
            }
            return sPhoneImei;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }





    public static String getAndroidId(Context context) {
        try {
            String id = Settings.System.getString(context.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            Log.d("AD_LOG", "getAndroidId: id:" + id );
            return id;
        } catch (Throwable throwable) {
            return "";
        }
    }



    private static String sPhoneImsi;

    @SuppressLint("MissingPermission")
    public static String getPhoneImsi(Context context) {
        if (sPhoneImsi != null) {
            return sPhoneImsi;
        }
        try {
            TelephonyManager mTelephonyMgr = (TelephonyManager) context.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
            sPhoneImsi = mTelephonyMgr.getSubscriberId();
            return sPhoneImsi = TextUtils.isEmpty(sPhoneImsi) ? "" : sPhoneImsi;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }



}
