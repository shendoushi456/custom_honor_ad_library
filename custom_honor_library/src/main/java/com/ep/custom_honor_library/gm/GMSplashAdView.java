package com.ep.custom_honor_library.gm;

import android.util.Log;

import com.blankj.utilcode.util.ScreenUtils;
import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.CSJAdError;
import com.bytedance.sdk.openadsdk.CSJSplashAd;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.bytedance.sdk.openadsdk.TTFullScreenVideoAd;
import com.bytedance.sdk.openadsdk.mediation.manager.MediationAdEcpmInfo;
import com.bytedance.sdk.openadsdk.mediation.manager.MediationSplashManager;
import com.ep.custom_honor_library.utils.CustomLogUtils;

public class GMSplashAdView extends SuperAdClazz{

    public static GMSplashAdView instance;

    private CSJSplashAd csjSplashAd;
    public  static GMSplashAdView  getGMSplashAdViewInstance(){
        if (instance == null){
            synchronized (GMSplashAdView.class){
                if (instance == null){
                    instance = new GMSplashAdView();
                }
            }
        }
        return instance;
    }


    @Override
    public void loadAd(OnloadStatusListener onloadStatusListener) {

        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(getAdInfo().getGm_id())
                .setImageAcceptedSize(ScreenUtils.getScreenWidth(),ScreenUtils.getScreenHeight())
                .build();

        TTAdNative adNative = TTAdSdk.getAdManager().createAdNative(getControlBean().getWrContext().get());
        adNative.loadSplashAd(adSlot, new TTAdNative.CSJSplashAdListener() {
            @Override
            public void onSplashLoadSuccess(CSJSplashAd mcsjSplashAd) {
                CustomLogUtils.i("开屏广告加载成功");
                csjSplashAd = mcsjSplashAd;
                isHasReady = true;
                if (onloadStatusListener!=null){
                    onloadStatusListener.loadSuccess();
                }
            }

            @Override
            public void onSplashLoadFail(CSJAdError csjAdError) {
                CustomLogUtils.i("开屏广告加载失败"+csjAdError.getMsg());
                isHasReady = false;
                if (onloadStatusListener!=null){
                    onloadStatusListener.loadFail();
                }
            }

            @Override
            public void onSplashRenderSuccess(CSJSplashAd mcsjSplashAd) {
                CustomLogUtils.i("onSplashRenderSuccess");
                csjSplashAd = mcsjSplashAd;
            }

            @Override
            public void onSplashRenderFail(CSJSplashAd csjSplashAd, CSJAdError csjAdError) {
                CustomLogUtils.i("onSplashRenderFail");
                isHasReady = false;
                if (onloadStatusListener!=null){
                    onloadStatusListener.loadFail();
                }


            }
        },3500);

    }

    @Override
    public void showAd(OnShowStatusListener onShowStatusListener) {

        if (getAdInfo() == null){
            isHasReady = false;
            return;
        }

        CustomLogUtils.i("开始展示开屏广告"+isHasReadAd());
        if (isHasReadAd() && csjSplashAd!=null){
            if (csjSplashAd ==null){
                CustomLogUtils.i("开始展示开屏广告====csjSplashAd==null");
                return;
            }

            csjSplashAd.showSplashView(getControlBean().getWrViewGroup().get());
            csjSplashAd.setSplashAdListener(new CSJSplashAd.SplashAdListener() {
                @Override
                public void onSplashAdShow(CSJSplashAd csjSplashAd) {
                    CustomLogUtils.i( "开屏广告加载onSplashAdShow");
                    if (csjSplashAd !=null){
                        MediationSplashManager mediationManager = csjSplashAd.getMediationManager();
                        if (csjSplashAd!=null){
                            MediationAdEcpmInfo showEcpm = csjSplashAd.getMediationManager().getShowEcpm();
                            if (showEcpm!=null){
                                createNATAdInfo(showEcpm,getAdInfo());
                            }
                        }
                    }
                    onShowStatusListener.showAdSuccess();
                }

                @Override
                public void onSplashAdClick(CSJSplashAd csjSplashAd) {
                    CustomLogUtils.i("开屏广告加载onSplashAdClick");

                }

                @Override
                public void onSplashAdClose(CSJSplashAd csjSplashAd, int closeType) {
                    CustomLogUtils.i("开屏广告关闭 onSplashAdClose"+csjSplashAd.toString());
                }
            });




        }
        isHasReady = false;




    }

    @Override
    public boolean isHasReadAd() {
        return csjSplashAd!=null && isHasReady;
    }
}
