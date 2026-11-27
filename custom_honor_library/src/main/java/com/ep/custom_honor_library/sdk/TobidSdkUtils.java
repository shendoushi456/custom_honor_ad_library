package com.ep.custom_honor_library.sdk;

import android.util.Log;

import com.ep.custom_honor_library.CommonAPI;
import com.ep.custom_honor_library.utils.DefContextUtils;
import com.windmill.sdk.WMAdConfig;
import com.windmill.sdk.WindMillAd;

public class TobidSdkUtils {

    private static boolean sigmobInit;
    public static void initSdk(){
        if(!sigmobInit){
            WindMillAd ads = WindMillAd.sharedAds();
            ads.setAdult(true);// 是否成年（可选参数，默认是成年用户）
            ads.setPersonalizedAdvertisingOn(true);// 是否开启个性化推荐接口（可选参数，默认是开启）
            ads.setDebugEnable(CommonAPI.switchLog);// 设置调试模式 true开启、false关闭（默认开启）
            ads.setLocalStrategyAssetPath(DefContextUtils.instance.getApplication(), "localStrategy");
            // 创建初始化配置
            WMAdConfig config = new WMAdConfig.Builder().build();
//           ads.startWithAppId(APPAllContext.getContext(), "appId");
            ads.startWithAppId(DefContextUtils.instance.getApplication(),CommonAPI.SIGMOB_JL_APPID,config,new WindMillAd.WindMillAdInitListener(){

                @Override
                public void onInitSuccess() {
                    sigmobInit = true;
                    Log.i("AD_LOG","Sigmob SDK 初始化成功");
                }

                @Override
                public void onInitFailed(int i, String s) {
                    Log.i("AD_LOG","Sigmob SDK 初始化失败----"+i+"----"+s);
                }
            });

        }
    }
}
