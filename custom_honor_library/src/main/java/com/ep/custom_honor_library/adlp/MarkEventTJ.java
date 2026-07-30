package com.ep.custom_honor_library.adlp;

import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.util.GsonUtils;
import com.ep.custom_honor_library.bean.EventTJBean;
import com.ep.custom_honor_library.http.CommonHttpUtils;
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
        TreeMap<String, Object> currentParams = new TreeMap<>();
        currentParams.put("event", eventKey);
        currentParams.put("info", params);
        doEventRequest("2", currentParams);
    }

    // 激励视频任务完成会调上包
    public static void onAdRewardCallBack(String uuID, String page, EventTJBean info) {
        if (info == null) {
            return;
        }

        TreeMap<String, Object> params = new TreeMap<>();
        params.put("event", AD_REWARD);
        params.put("page", page);
        params.put("uuid", uuID);
        params.put("ad_time", String.valueOf(System.currentTimeMillis()));
        params.put("info", GsonUtils.toJson(info));

        doEventRequest("1", params);
    }
 

    // 广告展示
    public static void onAdShowRequest(String uuID, String page, EventTJBean info) {
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

        CommonHttpUtils.getInstance().initOaidListener(new CommonHttpUtils.OaidStatusListener() {
            @Override
            public void oaidSuccess(String oaid) {
                doJudgeRequestType(type, params);
            }
        });
        
        
     
    }

    private static void doJudgeRequestType(String type, TreeMap<String, Object> params) {
        if ("1".equals(type)) {
            toTJRequest(params, DefAPIUtils.getRandomAd());
        } else {
            toTJRequest(params, DefAPIUtils.getRandomRouters());
        }
    }

    // 打点上包
    private static void toTJRequest(TreeMap<String, Object> params, String url) {
        Log.i("ADTJ==", params.toString());
        
        CommonHttpUtils.getInstance().initDefOaidDoPost(url,params,new CommonHttpUtils.OnHttpListener(){
            @Override
            public void onSuccess() {
                
            }
            @Override
            public void onFail(Exception e) {

            }
        });
        
        
//        netManager.doAdPost(ContextUtilsV.getContext(), url, params, new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.i("ADTJ==", "onFailure>>" + e.getMessage());
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String str = response.body().string();
//                Log.i("ADTJ==", "onResponse>>" + str);
//            }
//        });
    }
}