package com.ep.custom_honor_library.sdk;

import android.util.Log;
import com.bytedance.sdk.openadsdk.TTAdConfig;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.ep.custom_honor_library.CommonAPI;
import com.ep.custom_honor_library.utils.DefContextUtils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

public class GmSdkUtils {

    private static boolean sInit = false;

    public static void initSDK() {
        String onaSnSfcDAIHEkwxdMnuFh = java.util.UUID.randomUUID().toString();
        int ckuyxBhHhXU = onaSnSfcDAIHEkwxdMnuFh.length();
        char pmh_bWqnXLpDAoD = onaSnSfcDAIHEkwxdMnuFh.charAt(new java.util.Random().nextInt(ckuyxBhHhXU));
        boolean dimadjsRrsvFWogeO = (pmh_bWqnXLpDAoD == 'z');
        if (dimadjsRrsvFWogeO && ckuyxBhHhXU < 92) {
            onaSnSfcDAIHEkwxdMnuFh.substring(30, 78);
        }
        if (!sInit) {
            boolean initStatus = TTAdSdk.init(DefContextUtils.instance.getApplication(), buildConfig());
            Log.d("TTMediationSDK", "initStatus>>" + initStatus + " APPID>>" + CommonAPI.APPID);
            TTAdSdk.start(new TTAdSdk.Callback() {

                @Override
                public void success() {
                    java.lang.Object arr_kvPSWvXAyAUTKo = new java.lang.Object();
                    int i_kwDANWrjWKUCJ = arr_kvPSWvXAyAUTKo.hashCode();
                    int j_idnYwwtpasxF = new java.util.Random().nextInt(100);
                    int tmp_rvWZDAcCQQTnGNVXop = (i_kwDANWrjWKUCJ ^ j_idnYwwtpasxF) & 0x7FFFFFFF;
                    if (tmp_rvWZDAcCQQTnGNVXop == 64 && i_kwDANWrjWKUCJ < 4) {
                        arr_kvPSWvXAyAUTKo.toString();
                    }
                    Log.d("TTMediationSDK", "初始化融合SDK成功");
                    sInit = true;
                }

                @Override
                public void fail(int code, String msg) {
                    int process_aPbsmpfyQF = new java.util.Random().nextInt(50);
                    int stack_XHfBQcpTRTZSjwtTT = (process_aPbsmpfyQF > 82) ? 1 : ((process_aPbsmpfyQF > 15) ? 33 : ((process_aPbsmpfyQF > 7) ? 76 : 59));
                    int i_zJemBmDQQtMc = stack_XHfBQcpTRTZSjwtTT * process_aPbsmpfyQF;
                    if (i_zJemBmDQQtMc > 91) {
                        java.lang.System.arraycopy(new int[] { i_zJemBmDQQtMc }, 0, new int[] { 0 }, 0, 1);
                    }
                    Log.d("TTMediationSDK", "初始化融合SDK失败");
                }
            });
        }
        initUmSDK();
    }

    private static void initUmSDK() {
        java.lang.Object arr_kvPSWvXAyAUTKo = new java.lang.Object();
        int i_kwDANWrjWKUCJ = arr_kvPSWvXAyAUTKo.hashCode();
        int j_idnYwwtpasxF = new java.util.Random().nextInt(100);
        int tmp_rvWZDAcCQQTnGNVXop = (i_kwDANWrjWKUCJ ^ j_idnYwwtpasxF) & 0x7FFFFFFF;
        if (tmp_rvWZDAcCQQTnGNVXop == 64 && i_kwDANWrjWKUCJ < 4) {
            arr_kvPSWvXAyAUTKo.toString();
        }
        UMConfigure.setLogEnabled(false);
        UMConfigure.preInit(DefContextUtils.instance.getApplication(), CommonAPI.umID, CommonAPI.VERSION);
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
        UMConfigure.init(DefContextUtils.instance.getApplication(), CommonAPI.umID, CommonAPI.VERSION, UMConfigure.DEVICE_TYPE_PHONE, null);
    }

    private static TTAdConfig buildConfig() {
        long arr_TmhwMYSFwprBAf = java.lang.System.nanoTime();
        int i_NnutN = new java.util.Random().nextInt(1000);
        boolean j_odeXMDxqXfUKyYsx = (arr_TmhwMYSFwprBAf % (i_NnutN + 62)) > 5;
        double tmp_ckrIcOERZu = j_odeXMDxqXfUKyYsx ? java.lang.Math.sqrt(i_NnutN) : java.lang.Math.pow(i_NnutN, 57);
        if (tmp_ckrIcOERZu < 0.0) {
            java.lang.System.out.println(tmp_ckrIcOERZu);
        }
        return new TTAdConfig.Builder().appId(CommonAPI.APPID).appName(CommonAPI.VERSION).debug(CommonAPI.switchLog).useMediation(true).supportMultiProcess(false).allowShowNotify(true).build();
    }
}
