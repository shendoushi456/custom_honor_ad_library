package com.ep;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;

import com.anythink.core.api.ATAdInfo;
import com.anythink.core.api.AdError;
import com.anythink.interstitial.api.ATInterstitial;
import com.anythink.interstitial.api.ATInterstitialListener;

import java.util.List;


public class TaKuInterstitial  {

    public static final String TAG =  "TaKuInterstitial";

    private ATInterstitial mInterstitialAd;

    private static TaKuInterstitial instance;

    private double mEcpm = 0;

    public static TaKuInterstitial getTaKuInterAdInstance(String InterId) {

        if (instance == null) {
            synchronized (TaKuInterstitial.class) {
                if (instance == null) {
                    instance = new TaKuInterstitial(InterId);
                }
            }
        }
        return instance;
    }

    public TaKuInterstitial(String placementId) {

    }

    
    public double getEcpm() {
        Log.d(TAG,"当前TaKu inter 获取的ecpm>> "+mEcpm);
        return mEcpm;
    }

    
    protected void load(Context context) {
        if (mInterstitialAd == null) {
            mInterstitialAd = new ATInterstitial(context, "");
        }
        mInterstitialAd.setAdListener(new ATInterstitialListener() {

            
            public void onInterstitialAdLoaded() {
                Log.d(TAG, "taku onInterstitialAdLoaded 广告加载成功");
                //查询当前广告位的所有缓存信息的AdInfo对象、在广告加载成功后调用。
                List<ATAdInfo> adInfos = mInterstitialAd.checkValidAdCaches();
                if (adInfos != null && !adInfos.isEmpty()) {
                    ATAdInfo adInfo = adInfos.get(0);
                    if (adInfo != null) {
                        try {
                            mEcpm = adInfo.getEcpm();
                        } catch (Exception e) {
                            mEcpm = 0;
                        }
                    }
                }
            }

            
            public void onInterstitialAdLoadFail(AdError adError) {
                Log.e(TAG, "onInterstitialAdLoadFail: " + adError.getFullErrorInfo());

            }

            
            public void onInterstitialAdClicked(ATAdInfo atAdInfo) {
                Log.d(TAG, "onInterstitialAdClicked");
            }

            
            public void onInterstitialAdShow(ATAdInfo atAdInfo) {
                Log.d(TAG, "taku inter 广告展示成功 onInterstitialAdShow");
                if (atAdInfo != null) {
                    double tempEcpm = atAdInfo.getEcpm();
                    Log.d(TAG,"taku inter 广告展示的 ecpm 值>>>>"+tempEcpm);
                }

            }

            
            public void onInterstitialAdClose(ATAdInfo atAdInfo) {
                Log.d(TAG, "onInterstitialAdClose");

            }

            
            public void onInterstitialAdVideoStart(ATAdInfo atAdInfo) {
                Log.d(TAG, "onInterstitialAdVideoStart");
            }

            
            public void onInterstitialAdVideoEnd(ATAdInfo atAdInfo) {

                Log.d(TAG, "onInterstitialAdVideoEnd");
            }

            
            public void onInterstitialAdVideoError(AdError adError) {
                Log.e(TAG, "onInterstitialAdVideoError: " + adError.getFullErrorInfo());
            }
        });
        mInterstitialAd.load();
    }


    interface  AdCompleterListener {

    }
    
    public void show(Activity appCompatActivity, AdCompleterListener iViewOnCloseListener) {
        if(isReady()){
            try {
                mInterstitialAd.show(appCompatActivity);
            } catch (Exception e) {
                Log.e(TAG, "show exception: " + e.getMessage());
//                if (callback != null) {
//                    callback.onAdFailed(e.getMessage());
//                }
            }
        }

    }


    
    public boolean isReady() {

        return mInterstitialAd != null && mInterstitialAd.isAdReady();
    }

    
    protected int getType() {
        return 0;
    }

    // private void startAutoClose() {
    // removeAutoClose();
    // 
    // long delay = 5000;
    // if (DefConfig.adAutoCloseTime != 0) {
    // delay = DefConfig.adAutoCloseTime * 1000;
    // }
    // 
    // handler.postDelayed(closeRunnable = new Runnable() {
    // 
    // public void run() {
    // Log.d(TAG, "Auto close ad");
    // ContextUtilsV.clearAdActivity();
    // callAdClose();
    // }
    // }, delay);
    // }
    // 
    // private void removeAutoClose() {
    // if (closeRunnable != null) {
    // handler.removeCallbacksAndMessages(closeRunnable);
    // closeRunnable = null;
    // }
    // }

    
    public boolean isNeedContentViewShow() {
        return false;
    }


}
