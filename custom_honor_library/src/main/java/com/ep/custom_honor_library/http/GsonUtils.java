package com.ep.custom_honor_library.http;

import android.text.TextUtils;

import com.ep.custom_honor_library.bean.AdBean;
import com.ep.custom_honor_library.utils.CommonAPI;
import com.ep.custom_honor_library.utils.CustomLogUtils;
import com.ep.custom_honor_library.utils.DefAPIUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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


                String timerCount = configObject.getString("timerCount");
                String timerMinute = configObject.getString("timerMinute");

                ArrayList<Integer> timeCountList = new Gson().fromJson(timerCount, new TypeToken<ArrayList<Integer>>() {}.getType());
                CommonAPI.timeCountList.clear();
                CommonAPI.timeCountList.addAll(timeCountList);

                ArrayList<Integer> timerMinuteList = new Gson().fromJson(timerMinute, new TypeToken<ArrayList<Integer>>() {}.getType());
                CommonAPI.timerMinuteList.clear();
                CommonAPI.timerMinuteList.addAll(timerMinuteList);


                CustomLogUtils.i("timeCountList==="+timeCountList.toString(),"config==");
                CustomLogUtils.i("timerMinuteList==="+timerMinuteList.toString(),"config==");





                CustomLogUtils.i("adStr==="+adStr,"config==");
                if (!TextUtils.isEmpty(adStr)){
                    ArrayList<AdBean> adBeanList = new Gson().fromJson(adStr, new TypeToken<ArrayList<AdBean>>() {
                    }.getType());

                    CustomLogUtils.i("config=="+adBeanList,"config==");
                    //缓存
                    CommonAPI.cacheAdMap.clear();
                    for (AdBean adBean : adBeanList){
                        for (AdBean.AdChildBean adChildBean : adBean.getAd_list_beans()){
                            adChildBean.setAllName(adBean.getScene_key()+":"+adChildBean.getKey()+":"+adChildBean.getType());
                        }

                        CommonAPI.cacheAdMap.put(adBean.getScene_key(),adBean);
                        CustomLogUtils.i("Cache === end === "+CommonAPI.cacheAdMap.toString(),"config==");
                    }
                }

            } catch (JSONException e) {
                CustomLogUtils.e("解析策略失败","AD_LOG",e);
            }


        }

    }
}
