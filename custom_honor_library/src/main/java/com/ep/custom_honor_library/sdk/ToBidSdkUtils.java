package com.ep.custom_honor_library.sdk;

import static com.umeng.socialize.utils.DeviceConfigInternal.context;

import com.ep.custom_honor_library.CommonAPI;
import com.ep.custom_honor_library.utils.CustomLogUtils;
import com.windmill.sdk.WMAdConfig;
import com.windmill.sdk.WindMillAd;

public class ToBidSdkUtils {

    public static void init(){
        WindMillAd ad = WindMillAd.sharedAds();
        ad.setDebugEnable(false);
        WMAdConfig config = new WMAdConfig.Builder().build();
        ad.startWithAppId(context, CommonAPI.TOBID_ID,config,new WindMillAd.WindMillAdInitListener() {
            @Override public void onInitSuccess () {
                CustomLogUtils.i("AD_LOG","toBid_AD_int_Success");
            }

            @Override public void onInitFailed ( int errorCode, String errorMsg){
                CustomLogUtils.e("toBid_AD_int_Failed===onInitFailed===errorCode="+errorCode +"==errorMsg=="+errorMsg);
            }
        });
    }
}
