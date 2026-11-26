package com.ep.custom_honor_library.taku;

import static com.umeng.socialize.utils.DeviceConfigInternal.context;

import android.app.Activity;
import android.util.Log;

import com.anythink.core.api.ATAdInfo;
import com.anythink.core.api.ATAdStatusInfo;
import com.anythink.core.api.AdError;
import com.anythink.interstitial.api.ATInterstitial;
import com.anythink.interstitial.api.ATInterstitialListener;
import com.ep.custom_honor_library.ControllerUtils;
import com.ep.custom_honor_library.gm.SuperAdClazz;
import com.ep.custom_honor_library.utils.CommonSpUtils;
import com.ep.custom_honor_library.utils.CustomLogUtils;

import java.util.List;

public class XinTaKuInterstitial extends SuperAdClazz {

    public static final String TAG =  "AD_LOG_TaKuInterstitial";
    private static XinTaKuInterstitial instance;;
    private ATInterstitial mInterstitialAd;
    private OnShowStatusListener mOnShowStatusListener;
    private double mEcpm = 0;

    public static XinTaKuInterstitial getInstance(){
        if (instance == null){
            synchronized (XinTaKuInterstitial.class){
                if (instance == null){
                    instance = new XinTaKuInterstitial();
                }
            }
        }
        return instance;
    }

    @Override
    public void loadAd(OnloadStatusListener onloadStatusListener) {
        Log.d(TAG, "loadAd taku inter called ");
        if (getAdInfo()==null){
            CustomLogUtils.e("loadAd===getAdInfo == null  please setAdInfo", CommonSpUtils.AD_LOG,null);
            return;
        }
        if (mInterstitialAd == null) {
            List<String> takuIds = getAdInfo().getTakuIds();
            String takuId = (takuIds == null || takuIds.isEmpty()) ? "" : takuIds.get(0);
            Log.d(TAG, " TAKU load inter id>> "+ takuId);
            mInterstitialAd = new ATInterstitial(getControlBean().getWrContext().get(), takuId);
        }
        mInterstitialAd.setAdListener(new ATInterstitialListener() {

            @Override
            public void onInterstitialAdLoaded() {
                Log.d(TAG, "taku onInterstitialAdLoaded 广告加载成功");
                ControllerUtils.isHasShowAd = true;
                //查询当前广告位的所有缓存信息的AdInfo对象、在广告加载成功后调用。
                ATAdStatusInfo mATAdStatusInfo = mInterstitialAd.checkAdStatus();
                if(mATAdStatusInfo != null){
                    ATAdInfo mATAdInfo = mATAdStatusInfo.getATTopAdInfo();
                    if (mATAdInfo != null) {
                        try {
                            mEcpm = mATAdInfo.getEcpm();
                        } catch (Exception e) {
                            mEcpm = 0;
                        }
                    }
                }
                CustomLogUtils.i("插屏广告加载成功！===="+mInterstitialAd.isAdReady());
                isHasReady = true;
                if (onloadStatusListener!=null){
                    onloadStatusListener.loadSuccess();
                }
            }

            @Override
            public void onInterstitialAdLoadFail(AdError adError) {
                Log.e(TAG, "onInterstitialAdLoadFail: " + adError.getFullErrorInfo());
                isHasReady = false;
                if (onloadStatusListener!=null){
                    onloadStatusListener.loadFail();
                }
            }

            @Override
            public void onInterstitialAdClicked(ATAdInfo atAdInfo) {
                Log.d(TAG, "onInterstitialAdClicked");
               // callAdClicked(NAdBeanCreator.createNATAdInfo(atAdInfo));
            }

            @Override
            public void onInterstitialAdShow(ATAdInfo atAdInfo) {
                Log.d(TAG, "taku inter 广告展示成功 onInterstitialAdShow广告源id >> "+atAdInfo.getAdsourceId());
                if (atAdInfo != null) {
                    double tempEcpm = atAdInfo.getEcpm();
                    Log.d(TAG,"taku inter 广告展示的 ecpm 值>>>>"+tempEcpm);
//                    callAdShow(NAdBeanCreator.createNATAdInfo(atAdInfo));
//                    interOrFullAdAutoClose();
                    createNATAdInfo(atAdInfo,getAdInfo());
                }

                if (mOnShowStatusListener!=null){
                    mOnShowStatusListener.showAdSuccess();
                }

            }

            @Override
            public void onInterstitialAdClose(ATAdInfo atAdInfo) {
                Log.d(TAG, "onInterstitialAdClose");
                //callAdClose();
            }

            @Override
            public void onInterstitialAdVideoStart(ATAdInfo atAdInfo) {
                Log.d(TAG, "onInterstitialAdVideoStart");
            }

            @Override
            public void onInterstitialAdVideoEnd(ATAdInfo atAdInfo) {

                Log.d(TAG, "onInterstitialAdVideoEnd");
            }

            @Override
            public void onInterstitialAdVideoError(AdError adError) {
                Log.e(TAG, "onInterstitialAdVideoError: " + adError.getFullErrorInfo());
            }
        });
        mInterstitialAd.load();
    }

    @Override
    public void showAd(OnShowStatusListener onShowStatusListener) {
        if (getAdInfo()==null){
            CustomLogUtils.e("showAd===getAdInfo == null  please setAdInfo", CommonSpUtils.AD_LOG,null);
            isHasReady = false;
            return;
        }

        CustomLogUtils.i("开始展示插屏广告===="+isHasReadAd());
        mOnShowStatusListener = onShowStatusListener;
        if(isHasReadAd()){
            try {
                mInterstitialAd.show(getControlBean().getWrContext().get());
            } catch (Exception e) {
                Log.e(TAG, "show exception: " + e.getMessage());
//                if (callback != null) {
//                    callback.onAdFailed(e.getMessage());
//                }
            }
            isHasReady = false;
        }
    }

    @Override
    public boolean isHasReadAd() {
        return mInterstitialAd!=null && isHasReady;
    }
}
