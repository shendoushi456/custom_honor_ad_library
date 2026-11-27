package com.ep.custom_honor_library.tobid;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ScreenUtils;
import com.bytedance.sdk.openadsdk.mediation.manager.MediationAdEcpmInfo;
import com.bytedance.sdk.openadsdk.mediation.manager.MediationSplashManager;
import com.ep.custom_honor_library.gm.SuperAdClazz;
import com.ep.custom_honor_library.utils.CommonSpUtils;
import com.ep.custom_honor_library.utils.CustomLogUtils;
import com.windmill.sdk.WMConstants;
import com.windmill.sdk.WindMillError;
import com.windmill.sdk.models.AdInfo;
import com.windmill.sdk.splash.WMSplashAd;
import com.windmill.sdk.splash.WMSplashAdListener;
import com.windmill.sdk.splash.WMSplashAdRequest;

import java.util.HashMap;
import java.util.Map;

public class SigMobMediaSplashAd extends SuperAdClazz {

    public static final String TAG = "AD_LOG_SigMobMediaSplashAd";
    private OnShowStatusListener mOnShowStatusListener;
    private WMSplashAd mWindSplashAD;

    private static volatile SigMobMediaSplashAd instance;

    public static SigMobMediaSplashAd getInstance() {
        if (instance == null) {
            synchronized (SigMobMediaSplashAd.class) {
                if (instance == null) {
                    instance = new SigMobMediaSplashAd();
                }
            }
        }
        return instance;
    }



    @Override
    public void loadAd(OnloadStatusListener onloadStatusListener) {
        Log.i(TAG,"开始load 开屏SigMob（）");
        if (getAdInfo()==null){
            CustomLogUtils.e("loadAd===getAdInfo == null  please setAdInfo", CommonSpUtils.AD_LOG,null);
            return;
        }

        Log.i(TAG,"开屏广告开始load（）");

//
        Map<String, Object> options = new HashMap<>();
        options.put(WMConstants.AD_WIDTH, ScreenUtils.getScreenWidth());//单位px
        options.put(WMConstants.AD_HEIGHT,ScreenUtils.getScreenHeight());//单位px
        WMSplashAdRequest adRequest = new WMSplashAdRequest(getAdInfo().getPlacement_id(), "", options);
        adRequest.setDisableAutoHideAd(true); //广告结束，广告内容是否自动隐藏.默认是false
        mWindSplashAD = new WMSplashAd(getControlBean().getWrContext().get(), adRequest);
        mWindSplashAD.setSplashAdListener(new WMSplashAdListener() {
            @Override
            public void onSplashAdFailToPresent(WindMillError windMillError, String s) {
                // 广告展示失败
                Log.d(TAG,"SigMob 开屏广告 onSplashAdFailToPresent");
//                callLoadFail();
//                mAdStatus.setReady(false);
                CustomLogUtils.i("onSplashRenderFail");
                isHasReady = false;
                if (onloadStatusListener!=null){
                    onloadStatusListener.loadFail();
                }
            }

            @Override
            public void onSplashAdSuccessPresent(AdInfo adInfo) {
                // 广告展示成功
                Log.d(TAG,"SIgMob 开屏广告展示成功 onSplashAdSuccessPresent");
//                callAdShow(NAdBeanCreator.createSigMobNATAdInfo(adInfo));
//                splashAdAutoSkip();
                createNATAdInfo(adInfo);

                if(mOnShowStatusListener != null){
                    mOnShowStatusListener.showAdSuccess();
                }

            }

            @Override
            public void onSplashAdSuccessLoad(String placementId) {
                // Splash load 成功。若使用 loadAdOnly()，可在这里调用 showAd()
                Log.d(TAG,"SigMob 开屏广告 onSplashAdSuccessLoad");
//                mAdStatus.setReady(true);
//                callLoadSuccess();
                CustomLogUtils.i("开屏广告加载成功");
                isHasReady = true;
                if (onloadStatusListener!=null){
                    onloadStatusListener.loadSuccess();
                }
            }

            @Override
            public void onSplashAdFailToLoad(WindMillError error, String placementId) {
                // load 失败，建议直接跳转首页
                Log.d(TAG,"SigMob 开屏广告 onSplashAdFailToLoad"+error.getMessage()+"---"+error.getErrorCode());
//        callLoadFail();
//        mAdStatus.setReady(false);
                isHasReady = false;
                if (onloadStatusListener!=null){
                    onloadStatusListener.loadFail();
                }
            }

            @Override
            public void onSplashAdClicked(AdInfo adInfo) {
                // 广告点击
                Log.d(TAG,"SigMob 开屏广告点击 onSplashAdClicked");
                //callAdClicked(NAdBeanCreator.createSigMobNATAdInfo(adInfo));
            }

            @Override
            public void onSplashClosed(AdInfo adInfo) {
                // 广告关闭，释放资源
                //callAdClose();
                Log.d(TAG,"SigMob 开屏广告 onSplashClosed");
            }
        });
        mWindSplashAD.loadAdOnly();
    }

    @Override
    public void showAd(OnShowStatusListener onShowStatusListener) {
        if (getAdInfo() == null){
            isHasReady = false;
            return;
        }

        mOnShowStatusListener = onShowStatusListener;

        CustomLogUtils.i("开始展示开屏广告"+isHasReadAd());
        ViewGroup mViewGroup = getControlBean().getWrViewGroup().get();
        if (isHasReadAd() && mWindSplashAD != null && mViewGroup!=null){
            Log.i(TAG,"开始Show（）");
            mViewGroup.removeAllViews();
            mViewGroup.setVisibility(View.VISIBLE);
            mWindSplashAD.showAd(mViewGroup);
            isHasReady = false;
        }
    }

    @Override
    public boolean isHasReadAd() {
        return  mWindSplashAD!=null && mWindSplashAD.isReady();
    }
}
