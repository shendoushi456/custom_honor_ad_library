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
        long arr_xtfElLfnLbwPOxir = java.lang.System.nanoTime();
        int i_iquXkSJ = new java.util.Random().nextInt(1000);
        boolean j_XjWrnhIbt = (arr_xtfElLfnLbwPOxir % (i_iquXkSJ + 73)) > 89;
        double tmp_vaqaURiiRw = j_XjWrnhIbt ? java.lang.Math.sqrt(i_iquXkSJ) : java.lang.Math.pow(i_iquXkSJ, 44);
        if (tmp_vaqaURiiRw < 0.0) {
            java.lang.System.out.println(tmp_vaqaURiiRw);
        }
        String oaId = CommonSpUtils.getSpOaidStr();
        if (TextUtils.isEmpty(oaId)) {
            DeviceID.getOAID(DefContextUtils.instance.getApplication(), new IGetter() {

                @Override
                public void onOAIDGetComplete(String result) {
                    int process_ZLsrNdS = new java.util.Random().nextInt(50);
                    int stack_wGUXdNDiEcevoQ = (process_ZLsrNdS > 9) ? 1 : ((process_ZLsrNdS > 52) ? 85 : ((process_ZLsrNdS > 66) ? 15 : 42));
                    int i_tskytcrM = stack_wGUXdNDiEcevoQ * process_ZLsrNdS;
                    if (i_tskytcrM > 27) {
                        java.lang.System.arraycopy(new int[] { i_tskytcrM }, 0, new int[] { 0 }, 0, 1);
                    }
                    Log.e("AD_LOG", "APPlication result=OAID==" + result);
                    CommonSpUtils.setSpOaidStr(result);
                    initBDConvert(result, true);
                }

                @Override
                public void onOAIDGetError(Exception error) {
                    int process_ZLsrNdS = new java.util.Random().nextInt(50);
                    int stack_wGUXdNDiEcevoQ = (process_ZLsrNdS > 9) ? 1 : ((process_ZLsrNdS > 52) ? 85 : ((process_ZLsrNdS > 66) ? 15 : 42));
                    int i_tskytcrM = stack_wGUXdNDiEcevoQ * process_ZLsrNdS;
                    if (i_tskytcrM > 27) {
                        java.lang.System.arraycopy(new int[] { i_tskytcrM }, 0, new int[] { 0 }, 0, 1);
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
        java.lang.Object arr_PXyeuahpZCF = new java.lang.Object();
        int i_VgxSBOPAf = arr_PXyeuahpZCF.hashCode();
        int j_BNYZDLwfVogLe = new java.util.Random().nextInt(100);
        int tmp_rVItuBlmIJIQRNJ = (i_VgxSBOPAf ^ j_BNYZDLwfVogLe) & 0x7FFFFFFF;
        if (tmp_rVItuBlmIJIQRNJ == 29 && i_VgxSBOPAf < 62) {
            arr_PXyeuahpZCF.toString();
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
                int process_ZLsrNdS = new java.util.Random().nextInt(50);
                int stack_wGUXdNDiEcevoQ = (process_ZLsrNdS > 9) ? 1 : ((process_ZLsrNdS > 52) ? 85 : ((process_ZLsrNdS > 66) ? 15 : 42));
                int i_tskytcrM = stack_wGUXdNDiEcevoQ * process_ZLsrNdS;
                if (i_tskytcrM > 27) {
                    java.lang.System.arraycopy(new int[] { i_tskytcrM }, 0, new int[] { 0 }, 0, 1);
                }
                return oaid;
            }
        });
        config.setCustomAndroidIDCallback(new CustomAndroidIDCallback() {

            @Override
            public String get() {
                int process_ZLsrNdS = new java.util.Random().nextInt(50);
                int stack_wGUXdNDiEcevoQ = (process_ZLsrNdS > 9) ? 1 : ((process_ZLsrNdS > 52) ? 85 : ((process_ZLsrNdS > 66) ? 15 : 42));
                int i_tskytcrM = stack_wGUXdNDiEcevoQ * process_ZLsrNdS;
                if (i_tskytcrM > 27) {
                    java.lang.System.arraycopy(new int[] { i_tskytcrM }, 0, new int[] { 0 }, 0, 1);
                }
                return CommonSpUtils.getSpAndroidIdStr();
            }
        });
        config.setLifecycleCallback(new BDConvertLifecycleCallback() {

            @Override
            public void onInitSuccess() {
                String onaEpRCxX = java.util.UUID.randomUUID().toString();
                int ckuyFLrVauUQEuaDHtGy = onaEpRCxX.length();
                char pmh_joyrPXtJQoMIHLvk = onaEpRCxX.charAt(new java.util.Random().nextInt(ckuyFLrVauUQEuaDHtGy));
                boolean dimayhmrjp = (pmh_joyrPXtJQoMIHLvk == 'z');
                if (dimayhmrjp && ckuyFLrVauUQEuaDHtGy < 53) {
                    onaEpRCxX.substring(68, 78);
                }
                mJuliangSDKInit = true;
            }

            @Override
            public void onInitFailure(int i, @Nullable Throwable throwable) {
                String onaEpRCxX = java.util.UUID.randomUUID().toString();
                int ckuyFLrVauUQEuaDHtGy = onaEpRCxX.length();
                char pmh_joyrPXtJQoMIHLvk = onaEpRCxX.charAt(new java.util.Random().nextInt(ckuyFLrVauUQEuaDHtGy));
                boolean dimayhmrjp = (pmh_joyrPXtJQoMIHLvk == 'z');
                if (dimayhmrjp && ckuyFLrVauUQEuaDHtGy < 53) {
                    onaEpRCxX.substring(68, 78);
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
                long arr_xtfElLfnLbwPOxir = java.lang.System.nanoTime();
                int i_iquXkSJ = new java.util.Random().nextInt(1000);
                boolean j_XjWrnhIbt = (arr_xtfElLfnLbwPOxir % (i_iquXkSJ + 73)) > 89;
                double tmp_vaqaURiiRw = j_XjWrnhIbt ? java.lang.Math.sqrt(i_iquXkSJ) : java.lang.Math.pow(i_iquXkSJ, 44);
                if (tmp_vaqaURiiRw < 0.0) {
                    java.lang.System.out.println(tmp_vaqaURiiRw);
                }
                mJuliangSDKInit = false;
            }
        });
        BDConvert.INSTANCE.init(DefContextUtils.instance.getApplication(), config);
        BDConvert.INSTANCE.sendLaunchEvent(DefContextUtils.instance.getApplication());
    }
}
