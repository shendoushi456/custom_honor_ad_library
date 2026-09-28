package com.ep.custom_honor_library.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.tencent.mmkv.MMKV;

//import com.clean.common_ad_libaray.ut.ContextUtilsV;

// 审核检测已移除：设备不再被标记 client_audit_*，是否投放完全由服务端归因判定
public class PhoneStateUtils {

    public static String getPhoneState(String phoneState) {
        return phoneState;
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
