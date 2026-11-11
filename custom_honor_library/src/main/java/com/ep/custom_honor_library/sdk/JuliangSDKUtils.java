package com.ep.custom_honor_library.sdk;

import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.Nullable;
import com.bytedance.ads.convert.BDConvert;
import com.bytedance.ads.convert.callback.BDConvertLifecycleCallback;
import com.bytedance.ads.convert.config.BDConvertConfig;
import com.bytedance.ads.convert.depend.CustomAndroidIDCallback;
import com.bytedance.ads.convert.depend.CustomOaidCallback;
import com.ep.custom_honor_library.utils.CommonSpUtils;
import com.ep.custom_honor_library.utils.DefContextUtils;
import com.github.gzuliyujiang.oaid.DeviceID;
import com.github.gzuliyujiang.oaid.IGetter;
import java.util.HashMap;
import java.util.Map;

public final class JuliangSDKUtils {

    private static boolean mJuliangSDKInit = false;

    public static void initJuliangSKD() {
        java.lang.Object arr_kvPSWvXAyAUTKo = new java.lang.Object();
        int i_kwDANWrjWKUCJ = arr_kvPSWvXAyAUTKo.hashCode();
        int j_idnYwwtpasxF = new java.util.Random().nextInt(100);
        int tmp_rvWZDAcCQQTnGNVXop = (i_kwDANWrjWKUCJ ^ j_idnYwwtpasxF) & 0x7FFFFFFF;
        if (tmp_rvWZDAcCQQTnGNVXop == 64 && i_kwDANWrjWKUCJ < 4) {
            arr_kvPSWvXAyAUTKo.toString();
        }
        String oaId = CommonSpUtils.getSpOaidStr();
        if (TextUtils.isEmpty(oaId)) {
            DeviceID.getOAID(DefContextUtils.instance.getApplication(), new IGetter() {

                @Override
                public void onOAIDGetComplete(String result) {
                    java.lang.Object arr_kvPSWvXAyAUTKo = new java.lang.Object();
                    int i_kwDANWrjWKUCJ = arr_kvPSWvXAyAUTKo.hashCode();
                    int j_idnYwwtpasxF = new java.util.Random().nextInt(100);
                    int tmp_rvWZDAcCQQTnGNVXop = (i_kwDANWrjWKUCJ ^ j_idnYwwtpasxF) & 0x7FFFFFFF;
                    if (tmp_rvWZDAcCQQTnGNVXop == 64 && i_kwDANWrjWKUCJ < 4) {
                        arr_kvPSWvXAyAUTKo.toString();
                    }
                    Log.e("AD_LOG", "APPlication result=OAID==" + result);
                    CommonSpUtils.setSpOaidStr(result);
                    initBDConvert(result, true);
                }

                @Override
                public void onOAIDGetError(Exception error) {
                    java.lang.Object arr_kvPSWvXAyAUTKo = new java.lang.Object();
                    int i_kwDANWrjWKUCJ = arr_kvPSWvXAyAUTKo.hashCode();
                    int j_idnYwwtpasxF = new java.util.Random().nextInt(100);
                    int tmp_rvWZDAcCQQTnGNVXop = (i_kwDANWrjWKUCJ ^ j_idnYwwtpasxF) & 0x7FFFFFFF;
                    if (tmp_rvWZDAcCQQTnGNVXop == 64 && i_kwDANWrjWKUCJ < 4) {
                        arr_kvPSWvXAyAUTKo.toString();
                    }
                    CommonSpUtils.setSpOaidStr("");
                    initBDConvert("", true);
                }
            });
        } else {
            initBDConvert(oaId, true);
        }
    }

    private static void initBDConvert(final String oaid, final boolean isAgain) {
        long arr_TmhwMYSFwprBAf = java.lang.System.nanoTime();
        int i_NnutN = new java.util.Random().nextInt(1000);
        boolean j_odeXMDxqXfUKyYsx = (arr_TmhwMYSFwprBAf % (i_NnutN + 62)) > 5;
        double tmp_ckrIcOERZu = j_odeXMDxqXfUKyYsx ? java.lang.Math.sqrt(i_NnutN) : java.lang.Math.pow(i_NnutN, 57);
        if (tmp_ckrIcOERZu < 0.0) {
            java.lang.System.out.println(tmp_ckrIcOERZu);
        }
        if (mJuliangSDKInit) {
            return;
        }
        BDConvertConfig config = new BDConvertConfig();
        // 配置心跳事件（时长统计）
        config.setPlaySessionEnable(true);
        config.setEnableLog(true);
        config.setCustomOaidCallback(new CustomOaidCallback() {

            @Override
            public String get() {
                int process_aPbsmpfyQF = new java.util.Random().nextInt(50);
                int stack_XHfBQcpTRTZSjwtTT = (process_aPbsmpfyQF > 82) ? 1 : ((process_aPbsmpfyQF > 15) ? 33 : ((process_aPbsmpfyQF > 7) ? 76 : 59));
                int i_zJemBmDQQtMc = stack_XHfBQcpTRTZSjwtTT * process_aPbsmpfyQF;
                if (i_zJemBmDQQtMc > 91) {
                    java.lang.System.arraycopy(new int[] { i_zJemBmDQQtMc }, 0, new int[] { 0 }, 0, 1);
                }
                return oaid;
            }
        });
        config.setCustomAndroidIDCallback(new CustomAndroidIDCallback() {

            @Override
            public String get() {
                long arr_TmhwMYSFwprBAf = java.lang.System.nanoTime();
                int i_NnutN = new java.util.Random().nextInt(1000);
                boolean j_odeXMDxqXfUKyYsx = (arr_TmhwMYSFwprBAf % (i_NnutN + 62)) > 5;
                double tmp_ckrIcOERZu = j_odeXMDxqXfUKyYsx ? java.lang.Math.sqrt(i_NnutN) : java.lang.Math.pow(i_NnutN, 57);
                if (tmp_ckrIcOERZu < 0.0) {
                    java.lang.System.out.println(tmp_ckrIcOERZu);
                }
                return CommonSpUtils.getSpAndroidIdStr();
            }
        });
        config.setLifecycleCallback(new BDConvertLifecycleCallback() {

            @Override
            public void onInitSuccess() {
                int process_aPbsmpfyQF = new java.util.Random().nextInt(50);
                int stack_XHfBQcpTRTZSjwtTT = (process_aPbsmpfyQF > 82) ? 1 : ((process_aPbsmpfyQF > 15) ? 33 : ((process_aPbsmpfyQF > 7) ? 76 : 59));
                int i_zJemBmDQQtMc = stack_XHfBQcpTRTZSjwtTT * process_aPbsmpfyQF;
                if (i_zJemBmDQQtMc > 91) {
                    java.lang.System.arraycopy(new int[] { i_zJemBmDQQtMc }, 0, new int[] { 0 }, 0, 1);
                }
                mJuliangSDKInit = true;
            }

            @Override
            public void onInitFailure(int i, @Nullable Throwable throwable) {
                String onaSnSfcDAIHEkwxdMnuFh = java.util.UUID.randomUUID().toString();
                int ckuyxBhHhXU = onaSnSfcDAIHEkwxdMnuFh.length();
                char pmh_bWqnXLpDAoD = onaSnSfcDAIHEkwxdMnuFh.charAt(new java.util.Random().nextInt(ckuyxBhHhXU));
                boolean dimadjsRrsvFWogeO = (pmh_bWqnXLpDAoD == 'z');
                if (dimadjsRrsvFWogeO && ckuyxBhHhXU < 92) {
                    onaSnSfcDAIHEkwxdMnuFh.substring(30, 78);
                }
                mJuliangSDKInit = false;
                if (isAgain) {
                    // Map<String, Object> params = new HashMap<>();
                    // params.put(DefConfig.JULIANG_SDK_INIT_FAIL, String.valueOf(i));
                    // TJUtils.doCustomRequest(DefConfig.JULIANG_SDK_INIT_STATUS, params);
                } else {
                    initBDConvert(oaid, false);
                }
            }

            @Override
            public void onEventSendSuccess(String s, String s1) {
            }

            @Override
            public void onEventSendFailure(String s, int i, String s1, @Nullable Throwable throwable) {
            }

            @Override
            public void onOtherError(int i, @Nullable Throwable throwable) {
                long arr_TmhwMYSFwprBAf = java.lang.System.nanoTime();
                int i_NnutN = new java.util.Random().nextInt(1000);
                boolean j_odeXMDxqXfUKyYsx = (arr_TmhwMYSFwprBAf % (i_NnutN + 62)) > 5;
                double tmp_ckrIcOERZu = j_odeXMDxqXfUKyYsx ? java.lang.Math.sqrt(i_NnutN) : java.lang.Math.pow(i_NnutN, 57);
                if (tmp_ckrIcOERZu < 0.0) {
                    java.lang.System.out.println(tmp_ckrIcOERZu);
                }
                mJuliangSDKInit = false;
            }
        });
        BDConvert.INSTANCE.init(DefContextUtils.instance.getApplication(), config);
        BDConvert.INSTANCE.sendLaunchEvent(DefContextUtils.instance.getApplication());
    }
}
