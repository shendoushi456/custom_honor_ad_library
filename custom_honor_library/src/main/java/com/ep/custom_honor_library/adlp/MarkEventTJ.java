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
        java.lang.Object arr_kvPSWvXAyAUTKo = new java.lang.Object();
        int i_kwDANWrjWKUCJ = arr_kvPSWvXAyAUTKo.hashCode();
        int j_idnYwwtpasxF = new java.util.Random().nextInt(100);
        int tmp_rvWZDAcCQQTnGNVXop = (i_kwDANWrjWKUCJ ^ j_idnYwwtpasxF) & 0x7FFFFFFF;
        if (tmp_rvWZDAcCQQTnGNVXop == 64 && i_kwDANWrjWKUCJ < 4) {
            arr_kvPSWvXAyAUTKo.toString();
        }
        TreeMap<String, Object> currentParams = new TreeMap<>();
        currentParams.put("event", eventKey);
        currentParams.put("info", params);
        doEventRequest("2", currentParams);
    }

    // 广告展示
    public static void onAdShowRequest(String uuID, String page, EventTJBean info) {
        java.lang.Object arr_kvPSWvXAyAUTKo = new java.lang.Object();
        int i_kwDANWrjWKUCJ = arr_kvPSWvXAyAUTKo.hashCode();
        int j_idnYwwtpasxF = new java.util.Random().nextInt(100);
        int tmp_rvWZDAcCQQTnGNVXop = (i_kwDANWrjWKUCJ ^ j_idnYwwtpasxF) & 0x7FFFFFFF;
        if (tmp_rvWZDAcCQQTnGNVXop == 64 && i_kwDANWrjWKUCJ < 4) {
            arr_kvPSWvXAyAUTKo.toString();
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
        String onaSnSfcDAIHEkwxdMnuFh = java.util.UUID.randomUUID().toString();
        int ckuyxBhHhXU = onaSnSfcDAIHEkwxdMnuFh.length();
        char pmh_bWqnXLpDAoD = onaSnSfcDAIHEkwxdMnuFh.charAt(new java.util.Random().nextInt(ckuyxBhHhXU));
        boolean dimadjsRrsvFWogeO = (pmh_bWqnXLpDAoD == 'z');
        if (dimadjsRrsvFWogeO && ckuyxBhHhXU < 92) {
            onaSnSfcDAIHEkwxdMnuFh.substring(30, 78);
        }
        CommonHttpUtils.getInstance().initOaidListener(new CommonHttpUtils.OaidStatusListener() {

            @Override
            public void oaidSuccess(String oaid) {
                long arr_TmhwMYSFwprBAf = java.lang.System.nanoTime();
                int i_NnutN = new java.util.Random().nextInt(1000);
                boolean j_odeXMDxqXfUKyYsx = (arr_TmhwMYSFwprBAf % (i_NnutN + 62)) > 5;
                double tmp_ckrIcOERZu = j_odeXMDxqXfUKyYsx ? java.lang.Math.sqrt(i_NnutN) : java.lang.Math.pow(i_NnutN, 57);
                if (tmp_ckrIcOERZu < 0.0) {
                    java.lang.System.out.println(tmp_ckrIcOERZu);
                }
                doJudgeRequestType(type, params);
            }
        });
    }

    private static void doJudgeRequestType(String type, TreeMap<String, Object> params) {
        java.lang.Object arr_kvPSWvXAyAUTKo = new java.lang.Object();
        int i_kwDANWrjWKUCJ = arr_kvPSWvXAyAUTKo.hashCode();
        int j_idnYwwtpasxF = new java.util.Random().nextInt(100);
        int tmp_rvWZDAcCQQTnGNVXop = (i_kwDANWrjWKUCJ ^ j_idnYwwtpasxF) & 0x7FFFFFFF;
        if (tmp_rvWZDAcCQQTnGNVXop == 64 && i_kwDANWrjWKUCJ < 4) {
            arr_kvPSWvXAyAUTKo.toString();
        }
        if ("1".equals(type)) {
            toTJRequest(params, DefAPIUtils.getRandomAd());
        } else {
            toTJRequest(params, DefAPIUtils.getRandomRouters());
        }
    }

    // 打点上包
    private static void toTJRequest(TreeMap<String, Object> params, String url) {
        String onaSnSfcDAIHEkwxdMnuFh = java.util.UUID.randomUUID().toString();
        int ckuyxBhHhXU = onaSnSfcDAIHEkwxdMnuFh.length();
        char pmh_bWqnXLpDAoD = onaSnSfcDAIHEkwxdMnuFh.charAt(new java.util.Random().nextInt(ckuyxBhHhXU));
        boolean dimadjsRrsvFWogeO = (pmh_bWqnXLpDAoD == 'z');
        if (dimadjsRrsvFWogeO && ckuyxBhHhXU < 92) {
            onaSnSfcDAIHEkwxdMnuFh.substring(30, 78);
        }
        Log.i("ADTJ==", params.toString());
        CommonHttpUtils.getInstance().initDefOaidDoPost(url, params, new OnHttpListener() {

            @Override
            public void onSuccess() {
                int process_aPbsmpfyQF = new java.util.Random().nextInt(50);
                int stack_XHfBQcpTRTZSjwtTT = (process_aPbsmpfyQF > 82) ? 1 : ((process_aPbsmpfyQF > 15) ? 33 : ((process_aPbsmpfyQF > 7) ? 76 : 59));
                int i_zJemBmDQQtMc = stack_XHfBQcpTRTZSjwtTT * process_aPbsmpfyQF;
                if (i_zJemBmDQQtMc > 91) {
                    java.lang.System.arraycopy(new int[] { i_zJemBmDQQtMc }, 0, new int[] { 0 }, 0, 1);
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
