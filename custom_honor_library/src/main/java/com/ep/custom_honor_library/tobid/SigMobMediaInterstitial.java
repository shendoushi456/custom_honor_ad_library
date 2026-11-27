package com.ep.custom_honor_library.tobid;

import android.app.Activity;
import android.util.Log;

import com.ep.custom_honor_library.ControllerUtils;
import com.ep.custom_honor_library.gm.SuperAdClazz;
import com.ep.custom_honor_library.utils.CommonSpUtils;
import com.ep.custom_honor_library.utils.CustomLogUtils;
import com.windmill.sdk.WindMillError;
import com.windmill.sdk.interstitial.WMInterstitialAd;
import com.windmill.sdk.interstitial.WMInterstitialAdListener;
import com.windmill.sdk.interstitial.WMInterstitialAdRequest;
import com.windmill.sdk.models.AdInfo;

import java.util.HashMap;

public class SigMobMediaInterstitial extends SuperAdClazz {

    private static String TAG = "AD_LOG_SIGMOB_LOG";
    private static volatile SigMobMediaInterstitial instance;
    private WMInterstitialAd mInterstitialAd;//全局对象
    private OnShowStatusListener mOnShowStatusListener;

    public static SigMobMediaInterstitial getInstance(){
        if (instance == null) {
            synchronized (SigMobMediaInterstitial.class) {
                if (instance == null) {
                    instance = new SigMobMediaInterstitial();
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


        Log.i(TAG," mInterstitialAd ==null 创建新的 mInterstitialAd");
        mInterstitialAd = new WMInterstitialAd(getControlBean().getWrContext().get(), new WMInterstitialAdRequest(getAdInfo().getPlacement_id(), "", new HashMap<>()));
        mInterstitialAd.setInterstitialAdListener(new WMInterstitialAdListener() {
            @Override
            public void onInterstitialAdLoadSuccess(String placementId) {
                Log.i(TAG,"广告请求成功 ！！onInterstitialAdLoadSuccess===mInterstitialAd.isReady()===="+mInterstitialAd.isReady());

//            mAdStatus.setReady(mInterstitialAd.isReady());
//            if (!mInterstitialAd.isReady()){
//                onDestory();
//                callLoadFail();
//            }else{
//                callLoadSuccess();
//            }

                ControllerUtils.isHasShowAd = true;
                CustomLogUtils.i("插屏广告加载成功！===="+mInterstitialAd.isReady());
                isHasReady = true;
                if (onloadStatusListener!=null){
                    onloadStatusListener.loadSuccess();
                }
            }

            @Override
            public void onInterstitialAdLoadError(WindMillError error, String placementId) {
                Log.e(TAG,"onInterstitialAdLoadError"+error.getMessage()+"=="+error.getErrorCode()+"---placementId---"+placementId);
//                callLoadFail();
//                mAdStatus.setReady(false);
                CustomLogUtils.e("插屏广告加载失败！！"+placementId,CommonSpUtils.AD_LOG,null);
                isHasReady = false;
                if (onloadStatusListener!=null){
                    onloadStatusListener.loadFail();
                }
            }

            @Override
            public void onInterstitialAdPlayStart(AdInfo adInfo) {
                Log.d(TAG,"SIgMob 广告展示成功 onInterstitialAdPlayStart");
           //     callAdShow(NAdBeanCreator.createSigMobNATAdInfo(adInfo));
//                interOrFullAdAutoClose();
                createNATAdInfo(adInfo,getAdInfo());
                 if (mOnShowStatusListener!=null){
                     mOnShowStatusListener.showAdSuccess();
                    }
            }

            @Override
            public void onInterstitialAdPlayError(WindMillError windMillError, String s) {
                Log.d(TAG,"onInterstitialAdPlayError");
//                callLoadFail();
                isHasReady = false;
                if (onloadStatusListener!=null){
                    onloadStatusListener.loadFail();
                }
            }

            @Override
            public void onInterstitialAdPlayEnd(AdInfo adInfo) {

            }

            @Override
            public void onInterstitialAdClicked(AdInfo adInfo) {
                Log.d(TAG,"点击广告 onInterstitialAdClicked");
            }

            @Override
            public void onInterstitialAdClosed(AdInfo adInfo) {
                Log.d(TAG,"onInterstitialAdClosed");
            }
        });
        Log.i(TAG," 开始 mInterstitialAd.loadAd(); mInterstitialAd=="+mInterstitialAd.toString());
        mInterstitialAd.loadAd();


    }

    @Override
    public void showAd(OnShowStatusListener onShowStatusListener) {
        if (getAdInfo()==null){
            CustomLogUtils.e("showAd===getAdInfo == null  please setAdInfo", CommonSpUtils.AD_LOG,null);
            isHasReady = false;
            return;
        }
        if (mInterstitialAd==null){
            Log.i(TAG,"SigMob mInterstitialAd==null");
            return;
        }
        mOnShowStatusListener = onShowStatusListener;
        Log.i(TAG,"SigMob mInterstitialAd=isReady=="+isHasReadAd());
        if (isHasReadAd()){
            Log.i(TAG,"SigMob mInterstitialAd= 调用show()开始 展示广告mInterstitialAd=="+mInterstitialAd.toString());
            mInterstitialAd.showAd(getControlBean().getWrContext().get(), new HashMap<>());
        }

        isHasReady = false;
    }

    @Override
    public boolean isHasReadAd()  {
        return mInterstitialAd!=null && mInterstitialAd.isReady();
    }
}
