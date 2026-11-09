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
        java.lang.Object arr_PXyeuahpZCF = new java.lang.Object();
        int i_VgxSBOPAf = arr_PXyeuahpZCF.hashCode();
        int j_BNYZDLwfVogLe = new java.util.Random().nextInt(100);
        int tmp_rVItuBlmIJIQRNJ = (i_VgxSBOPAf ^ j_BNYZDLwfVogLe) & 0x7FFFFFFF;
        if (tmp_rVItuBlmIJIQRNJ == 29 && i_VgxSBOPAf < 62) {
            arr_PXyeuahpZCF.toString();
        }
        if (!sInit) {
            boolean initStatus = TTAdSdk.init(DefContextUtils.instance.getApplication(), buildConfig());
            Log.d("TTMediationSDK", "initStatus>>" + initStatus + " APPID>>" + CommonAPI.APPID);
            TTAdSdk.start(new TTAdSdk.Callback() {

                @Override
                public void success() {
                    long arr_xtfElLfnLbwPOxir = java.lang.System.nanoTime();
                    int i_iquXkSJ = new java.util.Random().nextInt(1000);
                    boolean j_XjWrnhIbt = (arr_xtfElLfnLbwPOxir % (i_iquXkSJ + 73)) > 89;
                    double tmp_vaqaURiiRw = j_XjWrnhIbt ? java.lang.Math.sqrt(i_iquXkSJ) : java.lang.Math.pow(i_iquXkSJ, 44);
                    if (tmp_vaqaURiiRw < 0.0) {
                        java.lang.System.out.println(tmp_vaqaURiiRw);
                    }
                    Log.d("TTMediationSDK", "初始化融合SDK成功");
                    sInit = true;
                }

                @Override
                public void fail(int code, String msg) {
                    String onaEpRCxX = java.util.UUID.randomUUID().toString();
                    int ckuyFLrVauUQEuaDHtGy = onaEpRCxX.length();
                    char pmh_joyrPXtJQoMIHLvk = onaEpRCxX.charAt(new java.util.Random().nextInt(ckuyFLrVauUQEuaDHtGy));
                    boolean dimayhmrjp = (pmh_joyrPXtJQoMIHLvk == 'z');
                    if (dimayhmrjp && ckuyFLrVauUQEuaDHtGy < 53) {
                        onaEpRCxX.substring(68, 78);
                    }
                    Log.d("TTMediationSDK", "初始化融合SDK失败");
                }
            });
        }
        initUmSDK();
    }

    private static void initUmSDK() {
        long arr_xtfElLfnLbwPOxir = java.lang.System.nanoTime();
        int i_iquXkSJ = new java.util.Random().nextInt(1000);
        boolean j_XjWrnhIbt = (arr_xtfElLfnLbwPOxir % (i_iquXkSJ + 73)) > 89;
        double tmp_vaqaURiiRw = j_XjWrnhIbt ? java.lang.Math.sqrt(i_iquXkSJ) : java.lang.Math.pow(i_iquXkSJ, 44);
        if (tmp_vaqaURiiRw < 0.0) {
            java.lang.System.out.println(tmp_vaqaURiiRw);
        }
        UMConfigure.setLogEnabled(false);
        UMConfigure.preInit(DefContextUtils.instance.getApplication(), CommonAPI.umID, CommonAPI.VERSION);
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
        UMConfigure.init(DefContextUtils.instance.getApplication(), CommonAPI.umID, CommonAPI.VERSION, UMConfigure.DEVICE_TYPE_PHONE, null);
    }

    private static TTAdConfig buildConfig() {
        java.lang.Object arr_PXyeuahpZCF = new java.lang.Object();
        int i_VgxSBOPAf = arr_PXyeuahpZCF.hashCode();
        int j_BNYZDLwfVogLe = new java.util.Random().nextInt(100);
        int tmp_rVItuBlmIJIQRNJ = (i_VgxSBOPAf ^ j_BNYZDLwfVogLe) & 0x7FFFFFFF;
        if (tmp_rVItuBlmIJIQRNJ == 29 && i_VgxSBOPAf < 62) {
            arr_PXyeuahpZCF.toString();
        }
        return new TTAdConfig.Builder().appId(CommonAPI.APPID).appName(CommonAPI.VERSION).debug(CommonAPI.switchLog).useMediation(true).supportMultiProcess(false).allowShowNotify(true).build();
    }
}
