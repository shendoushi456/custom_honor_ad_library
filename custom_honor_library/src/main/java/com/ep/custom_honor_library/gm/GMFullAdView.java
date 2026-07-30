package com.ep.custom_honor_library.gm;

import static com.umeng.socialize.utils.DeviceConfigInternal.context;


import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.bytedance.sdk.openadsdk.TTFullScreenVideoAd;
import com.bytedance.sdk.openadsdk.mediation.ad.MediationAdSlot;
import com.bytedance.sdk.openadsdk.mediation.manager.MediationAdEcpmInfo;
import com.bytedance.sdk.openadsdk.mediation.manager.MediationFullScreenManager;
import com.ep.custom_honor_library.utils.CommonSpUtils;
import com.ep.custom_honor_library.utils.CustomLogUtils;


public class GMFullAdView extends SuperAdClazz{

    public static GMFullAdView instance;
    private TTFullScreenVideoAd mTTFullAd;

    public  static GMFullAdView  getGMFullViewInstance(){
        if (instance == null){
            synchronized (GMFullAdView.class){
                if (instance == null){
                    instance = new GMFullAdView();
                }
            }
        }
        return instance;
    }


    @Override
    public void loadAd(OnloadStatusListener onloadStatusListener) {

        if (getAdInfo()==null){
            CustomLogUtils.e("loadAd===getAdInfo == null  please setAdInfo", CommonSpUtils.AD_LOG,null);
            return;
        }
        CustomLogUtils.i("开始load 插屏");
        AdSlot adSlot =  new AdSlot.Builder()
                .setCodeId(getAdInfo().getGm_id())
                .setOrientation(TTAdConstant.ORIENTATION_VERTICAL)
                .setMediationAdSlot(new MediationAdSlot.Builder()
                        .setMuted(false)
                        .setVolume(0.6f)
                        .setBidNotify(true).build()).build();

        TTAdNative adNative = TTAdSdk.getAdManager().createAdNative(getControlBean().getWrContext().get());

        adNative.loadFullScreenVideoAd(adSlot,new TTAdNative.FullScreenVideoAdListener(){

            @Override
            public void onError(int i, String s) {
                CustomLogUtils.e("插屏广告加载失败！！"+s,CommonSpUtils.AD_LOG,null);
                isHasReady = false;
                if (onloadStatusListener!=null){
                    onloadStatusListener.loadFail();
                }

            }

            @Override
            public void onFullScreenVideoAdLoad(TTFullScreenVideoAd ttFullScreenVideoAd) {
                mTTFullAd = ttFullScreenVideoAd;
                CustomLogUtils.i("插屏广告加载成功！===="+mTTFullAd.getMediationManager().isReady());
                isHasReady = true;
                if (onloadStatusListener!=null){
                    onloadStatusListener.loadSuccess();
                }

            }
            @Override
            public void onFullScreenVideoCached() {
            }
            @Override
            public void onFullScreenVideoCached(TTFullScreenVideoAd ttFullScreenVideoAd) {
                mTTFullAd = ttFullScreenVideoAd;
            }
        });
    }

    @Override
    public void showAd(OnShowStatusListener onShowStatusListener) {

        if (getAdInfo()==null){
            CustomLogUtils.e("showAd===getAdInfo == null  please setAdInfo", CommonSpUtils.AD_LOG,null);
            isHasReady = false;
            return;
        }

        CustomLogUtils.i("开始展示插屏广告===="+isHasReadAd());
        if (isHasReadAd()){
            if (mTTFullAd == null) return;

            mTTFullAd.setFullScreenVideoAdInteractionListener(new TTFullScreenVideoAd.FullScreenVideoAdInteractionListener() {
                @Override
                public void onAdShow() {
                    CustomLogUtils.i("onAdShow===插屏广告展示成功====");
                    if (mTTFullAd !=null){
                        MediationFullScreenManager mediationManager = mTTFullAd.getMediationManager();
                        if (mediationManager!=null){
                            MediationAdEcpmInfo showEcpm = mediationManager.getShowEcpm();
                            createNATAdInfo(showEcpm,getAdInfo());
                        }
                    }

                    if (onShowStatusListener!=null){
                        onShowStatusListener.showAdSuccess();
                    }


                }

                @Override
                public void onAdVideoBarClick() {
                    if (mTTFullAd!=null){
                        MediationFullScreenManager mediationManager = mTTFullAd.getMediationManager();
                        if (mediationManager!=null){
                            MediationAdEcpmInfo showEcpm = mediationManager.getShowEcpm();
//                            callAdClicked(CreateAdBean.createNATAdInfo(showEcpm));
                        }
                    }
                }

                @Override
                public void onAdClose() {
                    CustomLogUtils.i("onAdClose");
                }

                @Override
                public void onVideoComplete() {
                    CustomLogUtils.i("onVideoComplete");
                }

                @Override
                public void onSkippedVideo() {
                    CustomLogUtils.i("onSkippedVideo");
                }
            });
            mTTFullAd.showFullScreenVideoAd(getControlBean().getWrContext().get());
        }else{
            CustomLogUtils.i("插屏广告未准备好播放");
        }

        isHasReady = false;

    }

    @Override
    public boolean isHasReadAd() {
        return mTTFullAd!=null && isHasReady;
    }

}
