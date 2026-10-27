package com.ep.custom_honor_library.gm;

import com.blankj.utilcode.util.ScreenUtils;
import com.windmill.sdk.WMConstants;
import com.windmill.sdk.WindMillError;
import com.windmill.sdk.interstitial.WMInterstitialAd;
import com.windmill.sdk.models.AdInfo;
import com.windmill.sdk.splash.WMSplashAd;
import com.windmill.sdk.splash.WMSplashAdListener;
import com.windmill.sdk.splash.WMSplashAdRequest;

import java.util.HashMap;
import java.util.Map;

public class ToBidSplashAdView extends SuperAdClazz{
    private static ToBidSplashAdView instance;

    public  static ToBidSplashAdView  getGMFullViewInstance(){
        if (instance == null){
            synchronized (ToBidSplashAdView.class){
                if (instance == null){
                    instance = new ToBidSplashAdView();
                }
            }
        }
        return instance;
    }


    @Override
    public void loadAd(OnloadStatusListener onloadStatusListener) {

        Map<String, Object> options = new HashMap<>();
        options.put(WMConstants.AD_WIDTH, ScreenUtils.getScreenWidth());
        options.put(WMConstants.AD_HEIGHT, ScreenUtils.getScreenHeight());

        WMSplashAdRequest request = new WMSplashAdRequest(getAdInfo().getPlacement_id(), "", options);
        WMSplashAd splashAd = new WMSplashAd(getControlBean().getWrContext().get(), request);
        splashAd.setSplashAdListener(new WMSplashAdListener() {
            @Override
            public void onSplashAdSuccessLoad (String placementId){
                // Splash load 成功。若使用 loadAdOnly()，可在这里调用 showAd()
            }

            @Override
            public void onSplashAdFailToLoad (WindMillError error, String placementId){
                // load 失败，建议直接跳转首页
            }

            @Override
            public void onSplashAdSuccessPresent (AdInfo adInfo){
                // 广告展示成功
            }

            @Override
            public void onSplashAdFailToPresent (WindMillError error, String placementId){
                // 广告展示失败
            }

            @Override
            public void onSplashAdClicked (AdInfo adInfo){
                // 广告点击
            }

            @Override
            public void onSplashClosed (AdInfo adInfo){
                // 广告关闭，释放资源
            }
        });
        splashAd.loadAdAndShow(getControlBean().getWrViewGroup().get());
    }

    @Override
    public void showAd(OnShowStatusListener onShowStatusListener) {

    }

    @Override
    public boolean isHasReadAd() {
        return false;
    }
}
