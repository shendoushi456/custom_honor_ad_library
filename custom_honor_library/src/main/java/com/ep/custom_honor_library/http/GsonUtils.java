package com.ep.custom_honor_library.http;

import android.text.TextUtils;

import com.ep.custom_honor_library.bean.SerlisBean;
import com.ep.custom_honor_library.bean.SerlisChildArrBean;
import com.lx.c_interface_library.CommonAPI;
import com.ep.custom_honor_library.utils.CustomLogUtils;
import com.ep.custom_honor_library.utils.DefAPIUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GsonUtils {
    public static void toInitConfig(String json,String from){

        if (from.equals(DefAPIUtils.randomConfig_from_later) ||
            from.equals(DefAPIUtils.randomConfig_from_first) ||
                    from.equals(DefAPIUtils.randomConfig_from_screen_off) ||
                from.equals(DefAPIUtils.randomConfig_from_delay)){

            JSONObject decryptObject = null;

            CustomLogUtils.i("json==="+json,"config==");
            try {
                decryptObject = new JSONObject(json);
                String config = decryptObject.getString("config");
                JSONObject configObject = new JSONObject(config);

                int num = configObject.getInt("hour_turn_time");
//                int adAutoCloseTime = configObject.getInt("adAutoCloseTime");
                if (num>0){CommonAPI.HOUR_TURN_TIME = num;}
//                if (adAutoCloseTime>0){CommonAPI.AD_AUTO_CLOSE_TIME = adAutoCloseTime;}
                String adStr = decryptObject.getString("ad_key");


                // 兼容旧配置：timerCount/timerMinute 字段可能未下发，缺失时跳过，不影响后续广告缓存逻辑
//                if (configObject.has("timerCount") && !configObject.isNull("timerCount")) {
//                    String timerCount = configObject.getString("timerCount");
//                    ArrayList<Integer> timeCountList = new Gson().fromJson(timerCount, new TypeToken<ArrayList<Integer>>() {}.getType());
//                    CommonAPI.timeCountList.clear();
//                    CommonAPI.timeCountList.addAll(timeCountList);
//                    LogUtils.d("AD_LOG","timeCountList==="+timeCountList.toString(),"config==");
//                } else {
//                    LogUtils.d("AD_LOG","timerCount 字段不存在，跳过解析","config==");
//                }
//
//                if (configObject.has("timerMinute") && !configObject.isNull("timerMinute")) {
//                    String timerMinute = configObject.getString("timerMinute");
//                    ArrayList<Integer> timerMinuteList = new Gson().fromJson(timerMinute, new TypeToken<ArrayList<Integer>>() {}.getType());
//                    CommonAPI.timerMinuteList.clear();
//                    CommonAPI.timerMinuteList.addAll(timerMinuteList);
//                    LogUtils.d("AD_LOG","timerMinuteList==="+timerMinuteList.toString(),"config==");
//                } else {
//                    LogUtils.d("AD_LOG","timerMinute 字段不存在，跳过解析,config==");
//
//                }





                CustomLogUtils.i("adStr==="+adStr,"config==");
                if (!TextUtils.isEmpty(adStr)){

                    ArrayList<SerlisBean> adBeanList = parseAdKey(adStr);
//                    ArrayList<AdBean> adBeanList = new Gson().fromJson(adStr, new TypeToken<ArrayList<AdBean>>() {
//                    }.getType());
//
//                    CustomLogUtils.i("config=="+adBeanList,"config==");
//                    //缓存
                    DefAPIUtils.cacheAdMap.clear();
                    for (SerlisBean adBean : adBeanList){
                        for (SerlisChildArrBean adChildBean : adBean.getAd_list_beans()){
                            adChildBean.setAllName(adBean.getGgKeyScene()+":"+adChildBean.getGgKeyLin()+":"+adChildBean.getGgGMType());
                        }

                        DefAPIUtils.cacheAdMap.put(adBean.getGgKeyScene(),adBean);
                        CustomLogUtils.i("Cache === end === "+DefAPIUtils.cacheAdMap.toString(),"config==");
                    }
                }

            } catch (JSONException e) {
                CustomLogUtils.e("解析策略失败","AD_LOG",e);
            }


        }

    }



    private static ArrayList<SerlisBean> parseAdKey(String jsonStr) {

        ArrayList<SerlisBean> adBeanArrayList = new ArrayList<>();
        try {

            JSONArray adKeyArray = new JSONArray(jsonStr);
            for (int i = 0; i < adKeyArray.length(); i++) {
                SerlisBean adBean = new SerlisBean();
                JSONObject sceneObj = adKeyArray.getJSONObject(i);
                boolean  isCanEnable = sceneObj.optBoolean("enable", false);
                String scene_key =  sceneObj.optString("scene_key", "");
                adBean.setScene_key(scene_key);
                adBean.setCanEnable(isCanEnable);
                // 解析 ad_list_beans 子数组
                JSONArray beansArray = sceneObj.optJSONArray("ad_list_beans");
                if (beansArray != null) {
                    ArrayList<SerlisChildArrBean> dataChild = new ArrayList<>();
                    for (int j = 0; j < beansArray.length(); j++) {
                        JSONObject beanObj = beansArray.getJSONObject(j);
                        SerlisChildArrBean bean = new SerlisChildArrBean();
                        bean.setGgKeyLin(beanObj.optString("key", ""));
                        bean.setGgGMType(beanObj.optString("type", ""));
                        bean.setGgGM_id(beanObj.optString("gm_id", ""));
                        dataChild.add(bean);
                    }
                    adBean.setAd_list_beans(dataChild);
                }

                adBeanArrayList.add(adBean);
            }
//            Log.i("AD_LOG","存储数据==="+DefAPIUtils.cacheAdMap.toString());
            return adBeanArrayList;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return adBeanArrayList;

    }

}
