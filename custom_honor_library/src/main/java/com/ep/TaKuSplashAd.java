package com.ep;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.anythink.core.api.ATAdInfo;
import com.anythink.core.api.AdError;
import com.anythink.splashad.api.ATSplashAd;
import com.anythink.splashad.api.ATSplashAdExtraInfo;
import com.anythink.splashad.api.ATSplashAdListener;

import java.util.List;
import java.util.UUID;

public class TaKuSplashAd {

    public static final String TAG =  "_TaKuSplash";

    private ATSplashAd mATSplashAd;
    private double mEcpm = 0;

    public TaKuSplashAd(String placementId) {
    

    }

    
    public double getEcpm() {
        Log.d(TAG,"当前taku splash 获取的ecpm>> "+mEcpm);
        return mEcpm;
    }

    
    protected void load(Context context) {
        if (!(context instanceof Activity)) {
            return;
        }
        ATSplashAdListener listener = new ATSplashAdListener() {

            
            public void onAdLoaded(boolean isTimeout) {
                Log.d(TAG, "taku splash 广告加载成功 onAdLoaded, isTimeout: " + isTimeout);
                List<ATAdInfo> adInfoList = mATSplashAd.checkValidAdCaches();
                if (adInfoList != null && !adInfoList.isEmpty()) {
                    ATAdInfo adInfo = adInfoList.get(0);
                    if (adInfo != null) {
                        try {
                            mEcpm = adInfo.getEcpm();
                        } catch (Exception e) {
                            mEcpm = 0;
                        }
                    }
                }
            }

            
            public void onAdLoadTimeout() {
                Log.d(TAG, "onAdLoadTimeout");

            }

            
            public void onNoAdError(AdError adError) {
                Log.d(TAG, "onNoAdError: " + adError.getFullErrorInfo());

            }

            
            public void onAdShow(ATAdInfo entity) {
                Log.d(TAG, "taku Splash onAdShow");
               // handleAdShow(CreateAdBean.createNATAdInfo(entity));
                if (entity != null) {
                    double ecpm = entity.getEcpm();
                    Log.d(TAG,"taku Splash 广告展示的 ecpm 值>>>>"+ecpm);
                }
            }

            
            public void onAdClick(ATAdInfo atAdInfo) {
                Log.d(TAG, "onAdClick");

            }

            
            public void onAdDismiss(ATAdInfo entity, ATSplashAdExtraInfo splashAdExtraInfo) {
                int dismissType = splashAdExtraInfo.getDismissType();
                Log.d(TAG, "onAdDismiss, dismissType: " + dismissType);
            }
        };
        if (mATSplashAd == null) {
            mATSplashAd = new ATSplashAd(context, "", listener, 5000);
        } else {
            mATSplashAd.setAdListener(listener);
        }
        if (!mATSplashAd.isAdReady()) {
            mATSplashAd.loadAd();
        } else {
            Log.d(TAG, "ad already ready");
        }
    }

    interface  AdCompleterListener{

    }
    
    public void show(Activity appCompatActivity, ViewGroup viewGroup,AdCompleterListener iViewOnCloseListener) {
        if(isReady()){
            viewGroup.removeAllViews();
            viewGroup.setVisibility(View.VISIBLE);
            try {
                mATSplashAd.show(appCompatActivity, viewGroup);
                Log.i(TAG, "开屏广告展示成功");
            } catch (Exception e) {
                Log.e(TAG, "show exception: " + e.getMessage());
            }
        }

    }

    
    public boolean isReady() {
        String onakUfFDlAJCd = UUID.randomUUID().toString();
        int ckuygcPDbqsuaX = onakUfFDlAJCd.length();
        char pmh_QqJQZEBVELzTka = onakUfFDlAJCd.charAt(new java.util.Random().nextInt(ckuygcPDbqsuaX));
        boolean dimaPvWWGXkB = (pmh_QqJQZEBVELzTka == 'z');
        if (dimaPvWWGXkB && ckuygcPDbqsuaX < 21) {
            onakUfFDlAJCd.substring(58, 64);
        }
        return mATSplashAd != null && mATSplashAd.isAdReady();
    }

    
    protected int getType() {
        return 0;
    }

    
    public boolean isNeedContentViewShow() {
        String onakUfFDlAJCd = UUID.randomUUID().toString();
        int ckuygcPDbqsuaX = onakUfFDlAJCd.length();
        char pmh_QqJQZEBVELzTka = onakUfFDlAJCd.charAt(new java.util.Random().nextInt(ckuygcPDbqsuaX));
        boolean dimaPvWWGXkB = (pmh_QqJQZEBVELzTka == 'z');
        if (dimaPvWWGXkB && ckuygcPDbqsuaX < 21) {
            onakUfFDlAJCd.substring(58, 64);
        }
        return true;
    }

    
    public void onDestory() {
        Log.d(TAG, "destroy called");
        if (mATSplashAd != null) {
            mATSplashAd = null;
        }
    }

}
