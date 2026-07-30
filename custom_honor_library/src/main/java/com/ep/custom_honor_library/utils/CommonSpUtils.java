package com.ep.custom_honor_library.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.tencent.mmkv.MMKV;

public class CommonSpUtils {
    public static CommonSpUtils instance = new CommonSpUtils();
    public static String AD_LOG = "AD_LOG";
    public static String APP_USER_STATUS = "app_user_status";

    public static String SP_IS_FIRST_APP_STR = "sp_first_start_app";
    public static String SP_OAID_STR = "sp_oaid_str";
    public static String SP_ANDROID_ID_STR = "sp_android_id_str";
    public static String SP_CHANNEL_NUM_STR = "sp_channel_str";


    public static void setUserStatus(boolean firstApp){
        MMKV.defaultMMKV().encode(APP_USER_STATUS,firstApp);
    }

    public static boolean getUserStatus(){
        return MMKV.defaultMMKV().decodeBool(APP_USER_STATUS,false);
    }


    public static void setSpIsFirstAppStr(boolean firstApp){
        MMKV.defaultMMKV().encode(SP_IS_FIRST_APP_STR,firstApp);
    }

    public static boolean getSpIsFirstAppStr(){
        return MMKV.defaultMMKV().decodeBool(SP_IS_FIRST_APP_STR,true);
    }


    public static void setSpOaidStr(String oaidStr){
        MMKV.defaultMMKV().encode(SP_OAID_STR,oaidStr);
    }

    public static String getSpOaidStr(){
        return MMKV.defaultMMKV().decodeString(SP_OAID_STR);
    }


    public static void setSpAndroidIdStr(String androidID){
        MMKV.defaultMMKV().encode(SP_ANDROID_ID_STR,androidID);
    }

    public static String getSpAndroidIdStr(){
        String androidID = MMKV.defaultMMKV().decodeString(SP_ANDROID_ID_STR, "");
        if (TextUtils.isEmpty(androidID)) {
            androidID = Settings.System.getString(
                    DefContextUtils.instance.getApplication() != null ?
                            DefContextUtils.instance.getApplication().getContentResolver() : null,
                    Settings.Secure.ANDROID_ID);
            setSpAndroidIdStr(androidID);
        }
        Log.d(AD_LOG, "getAndroidId: id:" + androidID);
        return androidID;
    }

    public static void setSpChannelNumStr(String channelType){
        MMKV.defaultMMKV().encode(SP_CHANNEL_NUM_STR,channelType);
    }

    public static String getSpChannelNumStr(){
        return MMKV.defaultMMKV().decodeString(SP_CHANNEL_NUM_STR,"0");
    }




    public static String getPhoneImei() {
        try {
            String imei = MMKV.defaultMMKV().decodeString("device:imei");
            if (!TextUtils.isEmpty(imei)) {
                return imei;
            }
            TelephonyManager mTelephonyMgr = (TelephonyManager)
                    DefContextUtils.instance.getApplication().getApplicationContext()
                            .getSystemService(Context.TELEPHONY_SERVICE);
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
        String sPhoneImsi = MMKV.defaultMMKV().decodeString("device:imsi");
        if (!TextUtils.isEmpty(sPhoneImsi)) {
            return sPhoneImsi;
        }
        try {
            TelephonyManager mTelephonyMgr = (TelephonyManager)
                    DefContextUtils.instance.getApplication().getApplicationContext()
                            .getSystemService(Context.TELEPHONY_SERVICE);
             sPhoneImsi = mTelephonyMgr.getSubscriberId();
             MMKV.defaultMMKV().encode("device:imsi",sPhoneImsi);
            return sPhoneImsi != null ? sPhoneImsi : "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }




}
