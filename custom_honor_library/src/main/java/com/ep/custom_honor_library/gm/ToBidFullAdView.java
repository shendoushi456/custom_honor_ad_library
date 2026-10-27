package com.ep.custom_honor_library.gm;

import com.bytedance.sdk.openadsdk.mediation.manager.MediationAdEcpmInfo;
import com.bytedance.sdk.openadsdk.mediation.manager.MediationFullScreenManager;
import com.ep.custom_honor_library.ControllerUtils;
import com.ep.custom_honor_library.utils.CustomLogUtils;
import com.windmill.sdk.WindMillError;
import com.windmill.sdk.interstitial.WMInterstitialAd;
import com.windmill.sdk.interstitial.WMInterstitialAdListener;
import com.windmill.sdk.interstitial.WMInterstitialAdRequest;
import com.windmill.sdk.models.AdInfo;

import java.util.HashMap;

public class ToBidFullAdView extends SuperAdClazz{

    private static ToBidFullAdView instance;
    private WMInterstitialAd interstitialAd;

    public  static ToBidFullAdView  getGMFullViewInstance(){
        if (instance == null){
            synchronized (ToBidFullAdView.class){
                if (instance == null){
                    instance = new ToBidFullAdView();
                }
            }
        }
        return instance;
    }


    @Override
    public void loadAd(OnloadStatusListener onloadStatusListener) {
        WMInterstitialAdRequest request = new WMInterstitialAdRequest(getAdInfo().getPlacement_id(), "", new HashMap<String, Object>());
        WMInterstitialAd interstitialAd = new WMInterstitialAd(getControlBean().getWrContext().get(), request);

        interstitialAd.setInterstitialAdListener(new WMInterstitialAdListener() {
            @Override
            public void onInterstitialAdLoadSuccess (String placementId){
                // 广告加载成功
                ControllerUtils.isHasShowAd = true;
                isHasReady = true;
                if (onloadStatusListener!=null){
                    onloadStatusListener.loadSuccess();
                }
            }

            @Override
            public void onInterstitialAdLoadError (WindMillError error, String placementId){
                // 广告加载失败
                // 可通过 error 查看失败原因
                isHasReady = false;
                if (onloadStatusListener!=null){
                    onloadStatusListener.loadFail();
                }
            }

            @Override
            public void onInterstitialAdPlayStart (AdInfo adInfo){
                // 广告开始展示，可在这里记录曝光

                CustomLogUtils.i("onAdShow===插屏广告展示成功====");

                    if (adInfo!=null){
                        createSigMobNATAdInfo(adInfo);
                    }

                if (onloadStatusListener!=null){
                    onloadStatusListener.sh();
                }

            }

            @Override
            public void onInterstitialAdPlayError (WindMillError error, String placementId){
                // 广告展示失败
            }

            @Override
            public void onInterstitialAdPlayEnd (AdInfo adInfo){
                // 广告播放结束
            }

            @Override
            public void onInterstitialAdClicked (AdInfo adInfo){
                // 广告被点击
            }

            @Override
            public void onInterstitialAdClosed (AdInfo adInfo){
                // 广告关闭，建议在这里恢复页面交互
            }
        });

        interstitialAd.loadAd();


    }

    @Override
    public void showAd(OnShowStatusListener onShowStatusListener) {
        if(interstitialAd!=null && interstitialAd.isReady()){
            interstitialAd.showAd(getControlBean().getWrContext().get(),new HashMap<String, String>());
        }
    }

    @Override
    public boolean isHasReadAd() {
        return interstitialAd!=null && interstitialAd.isReady();
    }
}
