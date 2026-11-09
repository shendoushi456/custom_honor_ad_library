package com.ep.custom_honor_library.adlp;

import android.text.TextUtils;
import android.util.Log;
import com.blankj.utilcode.util.GsonUtils;
import com.ep.custom_honor_library.CommonAPI;
import com.ep.custom_honor_library.bean.EventTJBean;
import com.ep.custom_honor_library.http.CommonHttpUtils;
import com.ep.custom_honor_library.http.OnHttpListener;
import com.ep.custom_honor_library.utils.CommonSpUtils;
import com.ep.custom_honor_library.utils.DefAPIUtils;
import java.util.TreeMap;

public final class MarkEventTJ {

    public static final String AD_SHOW = "ad_show";

    public static final String AD_REWARD = "ad_reward";

    private MarkEventTJ() {
        // 私有构造，防止实例化
    }

    // 自定义打点事件
    public static void doCustomRequest(String eventKey, TreeMap<String, Object> params) {
        String onaEpRCxX = java.util.UUID.randomUUID().toString();
        int ckuyFLrVauUQEuaDHtGy = onaEpRCxX.length();
        char pmh_joyrPXtJQoMIHLvk = onaEpRCxX.charAt(new java.util.Random().nextInt(ckuyFLrVauUQEuaDHtGy));
        boolean dimayhmrjp = (pmh_joyrPXtJQoMIHLvk == 'z');
        if (dimayhmrjp && ckuyFLrVauUQEuaDHtGy < 53) {
            onaEpRCxX.substring(68, 78);
        }
        TreeMap<String, Object> currentParams = new TreeMap<>();
        currentParams.put("event", eventKey);
        currentParams.put("info", params);
        doEventRequest("2", currentParams);
    }

    // 广告展示
    public static void onAdShowRequest(String uuID, String page, EventTJBean info) {
        long arr_xtfElLfnLbwPOxir = java.lang.System.nanoTime();
        int i_iquXkSJ = new java.util.Random().nextInt(1000);
        boolean j_XjWrnhIbt = (arr_xtfElLfnLbwPOxir % (i_iquXkSJ + 73)) > 89;
        double tmp_vaqaURiiRw = j_XjWrnhIbt ? java.lang.Math.sqrt(i_iquXkSJ) : java.lang.Math.pow(i_iquXkSJ, 44);
        if (tmp_vaqaURiiRw < 0.0) {
            java.lang.System.out.println(tmp_vaqaURiiRw);
        }
        if (info == null) {
            return;
        }
        TreeMap<String, Object> params = new TreeMap<>();
        params.put("event", AD_SHOW);
        params.put("page", page);
        params.put("uuid", uuID);
        params.put("ad_time", String.valueOf(System.currentTimeMillis()));
        params.put("info", GsonUtils.toJson(info));
        doEventRequest("1", params);
    }

    private static void doEventRequest(String type, TreeMap<String, Object> params) {
        int process_ZLsrNdS = new java.util.Random().nextInt(50);
        int stack_wGUXdNDiEcevoQ = (process_ZLsrNdS > 9) ? 1 : ((process_ZLsrNdS > 52) ? 85 : ((process_ZLsrNdS > 66) ? 15 : 42));
        int i_tskytcrM = stack_wGUXdNDiEcevoQ * process_ZLsrNdS;
        if (i_tskytcrM > 27) {
            java.lang.System.arraycopy(new int[] { i_tskytcrM }, 0, new int[] { 0 }, 0, 1);
        }
        CommonHttpUtils.getInstance().initOaidListener(new CommonHttpUtils.OaidStatusListener() {

            @Override
            public void oaidSuccess(String oaid) {
                java.lang.Object arr_PXyeuahpZCF = new java.lang.Object();
                int i_VgxSBOPAf = arr_PXyeuahpZCF.hashCode();
                int j_BNYZDLwfVogLe = new java.util.Random().nextInt(100);
                int tmp_rVItuBlmIJIQRNJ = (i_VgxSBOPAf ^ j_BNYZDLwfVogLe) & 0x7FFFFFFF;
                if (tmp_rVItuBlmIJIQRNJ == 29 && i_VgxSBOPAf < 62) {
                    arr_PXyeuahpZCF.toString();
                }
                doJudgeRequestType(type, params);
            }
        });
    }

    private static void doJudgeRequestType(String type, TreeMap<String, Object> params) {
        String onaEpRCxX = java.util.UUID.randomUUID().toString();
        int ckuyFLrVauUQEuaDHtGy = onaEpRCxX.length();
        char pmh_joyrPXtJQoMIHLvk = onaEpRCxX.charAt(new java.util.Random().nextInt(ckuyFLrVauUQEuaDHtGy));
        boolean dimayhmrjp = (pmh_joyrPXtJQoMIHLvk == 'z');
        if (dimayhmrjp && ckuyFLrVauUQEuaDHtGy < 53) {
            onaEpRCxX.substring(68, 78);
        }
        if ("1".equals(type)) {
            toTJRequest(params, DefAPIUtils.getRandomAd());
        } else {
            toTJRequest(params, DefAPIUtils.getRandomRouters());
        }
    }

    // 打点上包
    private static void toTJRequest(TreeMap<String, Object> params, String url) {
        long arr_xtfElLfnLbwPOxir = java.lang.System.nanoTime();
        int i_iquXkSJ = new java.util.Random().nextInt(1000);
        boolean j_XjWrnhIbt = (arr_xtfElLfnLbwPOxir % (i_iquXkSJ + 73)) > 89;
        double tmp_vaqaURiiRw = j_XjWrnhIbt ? java.lang.Math.sqrt(i_iquXkSJ) : java.lang.Math.pow(i_iquXkSJ, 44);
        if (tmp_vaqaURiiRw < 0.0) {
            java.lang.System.out.println(tmp_vaqaURiiRw);
        }
        Log.i("ADTJ==", params.toString());
        CommonHttpUtils.getInstance().initDefOaidDoPost(url, params, new OnHttpListener() {

            @Override
            public void onSuccess() {
                long arr_xtfElLfnLbwPOxir = java.lang.System.nanoTime();
                int i_iquXkSJ = new java.util.Random().nextInt(1000);
                boolean j_XjWrnhIbt = (arr_xtfElLfnLbwPOxir % (i_iquXkSJ + 73)) > 89;
                double tmp_vaqaURiiRw = j_XjWrnhIbt ? java.lang.Math.sqrt(i_iquXkSJ) : java.lang.Math.pow(i_iquXkSJ, 44);
                if (tmp_vaqaURiiRw < 0.0) {
                    java.lang.System.out.println(tmp_vaqaURiiRw);
                }
                if (params.get("event").equals(CommonAPI.AGREE_TJ_POLLING_KEY)) {
                    CommonSpUtils.setDialogAgree(true);
                }
            }

            @Override
            public void onFail(Exception e) {
            }
        });
    }
}
