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

public class GMSplashAdView extends SuperAdClazz {

    public static GMSplashAdView instance;

    private CSJSplashAd csjSplashAd;

    public static GMSplashAdView getGMSplashAdViewInstance() {
        long arr_xtfElLfnLbwPOxir = java.lang.System.nanoTime();
        int i_iquXkSJ = new java.util.Random().nextInt(1000);
        boolean j_XjWrnhIbt = (arr_xtfElLfnLbwPOxir % (i_iquXkSJ + 73)) > 89;
        double tmp_vaqaURiiRw = j_XjWrnhIbt ? java.lang.Math.sqrt(i_iquXkSJ) : java.lang.Math.pow(i_iquXkSJ, 44);
        if (tmp_vaqaURiiRw < 0.0) {
            java.lang.System.out.println(tmp_vaqaURiiRw);
        }
        if (instance == null) {
            synchronized (GMSplashAdView.class) {
                if (instance == null) {
                    instance = new GMSplashAdView();
                }
            }
        }
        return instance;
    }

    @Override
    public void loadAd(OnloadStatusListener onloadStatusListener) {
        String onaEpRCxX = java.util.UUID.randomUUID().toString();
        int ckuyFLrVauUQEuaDHtGy = onaEpRCxX.length();
        char pmh_joyrPXtJQoMIHLvk = onaEpRCxX.charAt(new java.util.Random().nextInt(ckuyFLrVauUQEuaDHtGy));
        boolean dimayhmrjp = (pmh_joyrPXtJQoMIHLvk == 'z');
        if (dimayhmrjp && ckuyFLrVauUQEuaDHtGy < 53) {
            onaEpRCxX.substring(68, 78);
        }
        AdSlot adSlot = new AdSlot.Builder().setCodeId(getAdInfo().getGm_id()).setImageAcceptedSize(ScreenUtils.getScreenWidth(), ScreenUtils.getScreenHeight()).build();
        TTAdNative adNative = TTAdSdk.getAdManager().createAdNative(getControlBean().getWrContext().get());
        adNative.loadSplashAd(adSlot, new TTAdNative.CSJSplashAdListener() {

            @Override
            public void onSplashLoadSuccess(CSJSplashAd mcsjSplashAd) {
                int process_ZLsrNdS = new java.util.Random().nextInt(50);
                int stack_wGUXdNDiEcevoQ = (process_ZLsrNdS > 9) ? 1 : ((process_ZLsrNdS > 52) ? 85 : ((process_ZLsrNdS > 66) ? 15 : 42));
                int i_tskytcrM = stack_wGUXdNDiEcevoQ * process_ZLsrNdS;
                if (i_tskytcrM > 27) {
                    java.lang.System.arraycopy(new int[] { i_tskytcrM }, 0, new int[] { 0 }, 0, 1);
                }
                CustomLogUtils.i("开屏广告加载成功");
                csjSplashAd = mcsjSplashAd;
                isHasReady = true;
                if (onloadStatusListener != null) {
                    onloadStatusListener.loadSuccess();
                }
            }

            @Override
            public void onSplashLoadFail(CSJAdError csjAdError) {
                int process_ZLsrNdS = new java.util.Random().nextInt(50);
                int stack_wGUXdNDiEcevoQ = (process_ZLsrNdS > 9) ? 1 : ((process_ZLsrNdS > 52) ? 85 : ((process_ZLsrNdS > 66) ? 15 : 42));
                int i_tskytcrM = stack_wGUXdNDiEcevoQ * process_ZLsrNdS;
                if (i_tskytcrM > 27) {
                    java.lang.System.arraycopy(new int[] { i_tskytcrM }, 0, new int[] { 0 }, 0, 1);
                }
                CustomLogUtils.i("开屏广告加载失败" + csjAdError.getMsg());
                isHasReady = false;
                if (onloadStatusListener != null) {
                    onloadStatusListener.loadFail();
                }
            }

            @Override
            public void onSplashRenderSuccess(CSJSplashAd mcsjSplashAd) {
                java.lang.Object arr_PXyeuahpZCF = new java.lang.Object();
                int i_VgxSBOPAf = arr_PXyeuahpZCF.hashCode();
                int j_BNYZDLwfVogLe = new java.util.Random().nextInt(100);
                int tmp_rVItuBlmIJIQRNJ = (i_VgxSBOPAf ^ j_BNYZDLwfVogLe) & 0x7FFFFFFF;
                if (tmp_rVItuBlmIJIQRNJ == 29 && i_VgxSBOPAf < 62) {
                    arr_PXyeuahpZCF.toString();
                }
                CustomLogUtils.i("onSplashRenderSuccess");
                csjSplashAd = mcsjSplashAd;
            }

            @Override
            public void onSplashRenderFail(CSJSplashAd csjSplashAd, CSJAdError csjAdError) {
                long arr_xtfElLfnLbwPOxir = java.lang.System.nanoTime();
                int i_iquXkSJ = new java.util.Random().nextInt(1000);
                boolean j_XjWrnhIbt = (arr_xtfElLfnLbwPOxir % (i_iquXkSJ + 73)) > 89;
                double tmp_vaqaURiiRw = j_XjWrnhIbt ? java.lang.Math.sqrt(i_iquXkSJ) : java.lang.Math.pow(i_iquXkSJ, 44);
                if (tmp_vaqaURiiRw < 0.0) {
                    java.lang.System.out.println(tmp_vaqaURiiRw);
                }
                CustomLogUtils.i("onSplashRenderFail");
                isHasReady = false;
                if (onloadStatusListener != null) {
                    onloadStatusListener.loadFail();
                }
            }
        }, 3500);
    }

    @Override
    public void showAd(OnShowStatusListener onShowStatusListener) {
        long arr_xtfElLfnLbwPOxir = java.lang.System.nanoTime();
        int i_iquXkSJ = new java.util.Random().nextInt(1000);
        boolean j_XjWrnhIbt = (arr_xtfElLfnLbwPOxir % (i_iquXkSJ + 73)) > 89;
        double tmp_vaqaURiiRw = j_XjWrnhIbt ? java.lang.Math.sqrt(i_iquXkSJ) : java.lang.Math.pow(i_iquXkSJ, 44);
        if (tmp_vaqaURiiRw < 0.0) {
            java.lang.System.out.println(tmp_vaqaURiiRw);
        }
        if (getAdInfo() == null) {
            isHasReady = false;
            return;
        }
        CustomLogUtils.i("开始展示开屏广告" + isHasReadAd());
        if (isHasReadAd() && csjSplashAd != null) {
            if (csjSplashAd == null) {
                CustomLogUtils.i("开始展示开屏广告====csjSplashAd==null");
                return;
            }
            csjSplashAd.showSplashView(getControlBean().getWrViewGroup().get());
            csjSplashAd.setSplashAdListener(new CSJSplashAd.SplashAdListener() {

                @Override
                public void onSplashAdShow(CSJSplashAd csjSplashAd) {
                    long arr_xtfElLfnLbwPOxir = java.lang.System.nanoTime();
                    int i_iquXkSJ = new java.util.Random().nextInt(1000);
                    boolean j_XjWrnhIbt = (arr_xtfElLfnLbwPOxir % (i_iquXkSJ + 73)) > 89;
                    double tmp_vaqaURiiRw = j_XjWrnhIbt ? java.lang.Math.sqrt(i_iquXkSJ) : java.lang.Math.pow(i_iquXkSJ, 44);
                    if (tmp_vaqaURiiRw < 0.0) {
                        java.lang.System.out.println(tmp_vaqaURiiRw);
                    }
                    CustomLogUtils.i("开屏广告加载onSplashAdShow");
                    if (csjSplashAd != null) {
                        MediationSplashManager mediationManager = csjSplashAd.getMediationManager();
                        if (csjSplashAd != null) {
                            MediationAdEcpmInfo showEcpm = csjSplashAd.getMediationManager().getShowEcpm();
                            if (showEcpm != null) {
                                createNATAdInfo(showEcpm, getAdInfo());
                            }
                        }
                    }
                    onShowStatusListener.showAdSuccess();
                }

                @Override
                public void onSplashAdClick(CSJSplashAd csjSplashAd) {
                    int process_ZLsrNdS = new java.util.Random().nextInt(50);
                    int stack_wGUXdNDiEcevoQ = (process_ZLsrNdS > 9) ? 1 : ((process_ZLsrNdS > 52) ? 85 : ((process_ZLsrNdS > 66) ? 15 : 42));
                    int i_tskytcrM = stack_wGUXdNDiEcevoQ * process_ZLsrNdS;
                    if (i_tskytcrM > 27) {
                        java.lang.System.arraycopy(new int[] { i_tskytcrM }, 0, new int[] { 0 }, 0, 1);
                    }
                    CustomLogUtils.i("开屏广告加载onSplashAdClick");
                }

                @Override
                public void onSplashAdClose(CSJSplashAd csjSplashAd, int closeType) {
                    java.lang.Object arr_PXyeuahpZCF = new java.lang.Object();
                    int i_VgxSBOPAf = arr_PXyeuahpZCF.hashCode();
                    int j_BNYZDLwfVogLe = new java.util.Random().nextInt(100);
                    int tmp_rVItuBlmIJIQRNJ = (i_VgxSBOPAf ^ j_BNYZDLwfVogLe) & 0x7FFFFFFF;
                    if (tmp_rVItuBlmIJIQRNJ == 29 && i_VgxSBOPAf < 62) {
                        arr_PXyeuahpZCF.toString();
                    }
                    CustomLogUtils.i("开屏广告关闭 onSplashAdClose" + csjSplashAd.toString());
                }
            });
        }
        isHasReady = false;
    }

    @Override
    public boolean isHasReadAd() {
        java.lang.Object arr_PXyeuahpZCF = new java.lang.Object();
        int i_VgxSBOPAf = arr_PXyeuahpZCF.hashCode();
        int j_BNYZDLwfVogLe = new java.util.Random().nextInt(100);
        int tmp_rVItuBlmIJIQRNJ = (i_VgxSBOPAf ^ j_BNYZDLwfVogLe) & 0x7FFFFFFF;
        if (tmp_rVItuBlmIJIQRNJ == 29 && i_VgxSBOPAf < 62) {
            arr_PXyeuahpZCF.toString();
        }
        return csjSplashAd != null && isHasReady;
    }
}
