package com.ep.custom_honor_library.taku;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.anythink.core.api.ATAdInfo;
import com.anythink.core.api.ATAdStatusInfo;
import com.anythink.core.api.AdError;
import com.anythink.splashad.api.ATSplashAd;
import com.anythink.splashad.api.ATSplashAdExtraInfo;
import com.anythink.splashad.api.ATSplashAdListener;
import com.ep.custom_honor_library.gm.SuperAdClazz;
import com.ep.custom_honor_library.utils.CustomLogUtils;

import java.util.List;

public class XinTaKuSplashAd extends SuperAdClazz {

    public static final String TAG =  "AD_LOG_TaKuSplash";
    private static volatile XinTaKuSplashAd instance;
    private ATSplashAd mATSplashAd;
    private OnShowStatusListener mOnShowStatusListener;
    private double mEcpm = 0;

    public static XinTaKuSplashAd getInstance(){
        if(instance == null){
            synchronized (XinTaKuSplashAd.class){
                if(instance == null){
                    instance = new XinTaKuSplashAd();
                }
            }
        }
        return instance;
    }


    @Override
    public void loadAd(OnloadStatusListener onloadStatusListener) {
        Log.d(TAG, "loadAd taku splash called>>"+getAdInfo().getGm_id());
        if (getAdInfo() == null){
            isHasReady = false;
            return;
        }
        ATSplashAdListener listener = new ATSplashAdListener() {

            @Override
            public void onAdLoaded(boolean isTimeout) {
                Log.d(TAG, "taku splash 广告加载成功 onAdLoaded, isTimeout: " + isTimeout);
                ATAdStatusInfo mATAdStatusInfo = mATSplashAd.checkAdStatus();
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
//                mAdStatus.setReady(true);
//                callLoadSuccess();
                isHasReady = true;
                if (onloadStatusListener!=null){
                    onloadStatusListener.loadSuccess();
                }
            }

            @Override
            public void onAdLoadTimeout() {
                Log.d(TAG, "onAdLoadTimeout");
                isHasReady = false;
//                callLoadFail();
//                mAdStatus.setReady(false);

            }

            @Override
            public void onNoAdError(AdError adError) {
                Log.d(TAG, "onNoAdError: " + adError.getFullErrorInfo());
                isHasReady = false;
//                callLoadFail();
//                mAdStatus.setReady(false);
                if (onloadStatusListener!=null){
                    onloadStatusListener.loadFail();
                }
            }

            @Override
            public void onAdShow(ATAdInfo entity) {
                Log.d(TAG, "taku Splash onAdShow");
                // handleAdShow(CreateAdBean.createNATAdInfo(entity));
                if (entity != null) {
                    double ecpm = entity.getEcpm();
                    Log.d(TAG,"taku Splash 广告展示的 ecpm 值>>>>"+ecpm);
                    createNATAdInfo(entity,getAdInfo());
                }
//                callAdShow(NAdBeanCreator.createNATAdInfo(entity));
//                splashAdAutoSkip();
                if(mOnShowStatusListener !=null){
                    mOnShowStatusListener.showAdSuccess();
                }
            }

            @Override
            public void onAdClick(ATAdInfo atAdInfo) {
                Log.d(TAG, "onAdClick");

//                callAdClicked(NAdBeanCreator.createNATAdInfo(atAdInfo));
            }

            @Override
            public void onAdDismiss(ATAdInfo entity, ATSplashAdExtraInfo splashAdExtraInfo) {
                int dismissType = splashAdExtraInfo.getDismissType();
                Log.d(TAG, "onAdDismiss, dismissType: " + dismissType);
//                callAdClose();
            }
        };
        if (mATSplashAd == null) {
            List<String> takuIds = getAdInfo().getTakuIds();
            String takuId = (takuIds == null || takuIds.isEmpty()) ? "" : takuIds.get(0);
            Log.d(TAG, " TAKU load splash id>> "+ takuId);
            mATSplashAd = new ATSplashAd(getControlBean().getWrContext().get(), takuId, listener, 5000);
        } else {
            mATSplashAd.setAdListener(listener);
        }
        if (!mATSplashAd.isAdReady()) {
            mATSplashAd.loadAd();
        } else {
            Log.d(TAG, "ad already ready");
        }
    }

    @Override
    public void showAd(OnShowStatusListener onShowStatusListener) {
        if (getAdInfo() == null){
            isHasReady = false;
            return;
        }
        CustomLogUtils.i("开始展示开屏广告"+isHasReadAd());
        mOnShowStatusListener = onShowStatusListener;
        if(isHasReadAd() && mATSplashAd!=null){
            ViewGroup mViewGroup = getControlBean().getWrViewGroup().get();
            mViewGroup.removeAllViews();
            mViewGroup.setVisibility(View.VISIBLE);
            try {
                mATSplashAd.show(getControlBean().getWrContext().get(), mViewGroup);
                Log.i(TAG, "开屏广告展示成功");
            } catch (Exception e) {
                Log.e(TAG, "show exception: " + e.getMessage());
            }
//            if(mAdStatus == null) return;
//            mAdStatus.setReady(false);
            isHasReady = false;
        }
    }

    @Override
    public boolean isHasReadAd() {
        return false;
    }
}
