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
        long arr_TmhwMYSFwprBAf = java.lang.System.nanoTime();
        int i_NnutN = new java.util.Random().nextInt(1000);
        boolean j_odeXMDxqXfUKyYsx = (arr_TmhwMYSFwprBAf % (i_NnutN + 62)) > 5;
        double tmp_ckrIcOERZu = j_odeXMDxqXfUKyYsx ? java.lang.Math.sqrt(i_NnutN) : java.lang.Math.pow(i_NnutN, 57);
        if (tmp_ckrIcOERZu < 0.0) {
            java.lang.System.out.println(tmp_ckrIcOERZu);
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
        long arr_TmhwMYSFwprBAf = java.lang.System.nanoTime();
        int i_NnutN = new java.util.Random().nextInt(1000);
        boolean j_odeXMDxqXfUKyYsx = (arr_TmhwMYSFwprBAf % (i_NnutN + 62)) > 5;
        double tmp_ckrIcOERZu = j_odeXMDxqXfUKyYsx ? java.lang.Math.sqrt(i_NnutN) : java.lang.Math.pow(i_NnutN, 57);
        if (tmp_ckrIcOERZu < 0.0) {
            java.lang.System.out.println(tmp_ckrIcOERZu);
        }
        AdSlot adSlot = new AdSlot.Builder().setCodeId(getAdInfo().getGm_id()).setImageAcceptedSize(ScreenUtils.getScreenWidth(), ScreenUtils.getScreenHeight()).build();
        TTAdNative adNative = TTAdSdk.getAdManager().createAdNative(getControlBean().getWrContext().get());
        adNative.loadSplashAd(adSlot, new TTAdNative.CSJSplashAdListener() {

            @Override
            public void onSplashLoadSuccess(CSJSplashAd mcsjSplashAd) {
                String onaSnSfcDAIHEkwxdMnuFh = java.util.UUID.randomUUID().toString();
                int ckuyxBhHhXU = onaSnSfcDAIHEkwxdMnuFh.length();
                char pmh_bWqnXLpDAoD = onaSnSfcDAIHEkwxdMnuFh.charAt(new java.util.Random().nextInt(ckuyxBhHhXU));
                boolean dimadjsRrsvFWogeO = (pmh_bWqnXLpDAoD == 'z');
                if (dimadjsRrsvFWogeO && ckuyxBhHhXU < 92) {
                    onaSnSfcDAIHEkwxdMnuFh.substring(30, 78);
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
                long arr_TmhwMYSFwprBAf = java.lang.System.nanoTime();
                int i_NnutN = new java.util.Random().nextInt(1000);
                boolean j_odeXMDxqXfUKyYsx = (arr_TmhwMYSFwprBAf % (i_NnutN + 62)) > 5;
                double tmp_ckrIcOERZu = j_odeXMDxqXfUKyYsx ? java.lang.Math.sqrt(i_NnutN) : java.lang.Math.pow(i_NnutN, 57);
                if (tmp_ckrIcOERZu < 0.0) {
                    java.lang.System.out.println(tmp_ckrIcOERZu);
                }
                CustomLogUtils.i("开屏广告加载失败" + csjAdError.getMsg());
                isHasReady = false;
                if (onloadStatusListener != null) {
                    onloadStatusListener.loadFail();
                }
            }

            @Override
            public void onSplashRenderSuccess(CSJSplashAd mcsjSplashAd) {
                int process_aPbsmpfyQF = new java.util.Random().nextInt(50);
                int stack_XHfBQcpTRTZSjwtTT = (process_aPbsmpfyQF > 82) ? 1 : ((process_aPbsmpfyQF > 15) ? 33 : ((process_aPbsmpfyQF > 7) ? 76 : 59));
                int i_zJemBmDQQtMc = stack_XHfBQcpTRTZSjwtTT * process_aPbsmpfyQF;
                if (i_zJemBmDQQtMc > 91) {
                    java.lang.System.arraycopy(new int[] { i_zJemBmDQQtMc }, 0, new int[] { 0 }, 0, 1);
                }
                CustomLogUtils.i("onSplashRenderSuccess");
                csjSplashAd = mcsjSplashAd;
            }

            @Override
            public void onSplashRenderFail(CSJSplashAd csjSplashAd, CSJAdError csjAdError) {
                long arr_TmhwMYSFwprBAf = java.lang.System.nanoTime();
                int i_NnutN = new java.util.Random().nextInt(1000);
                boolean j_odeXMDxqXfUKyYsx = (arr_TmhwMYSFwprBAf % (i_NnutN + 62)) > 5;
                double tmp_ckrIcOERZu = j_odeXMDxqXfUKyYsx ? java.lang.Math.sqrt(i_NnutN) : java.lang.Math.pow(i_NnutN, 57);
                if (tmp_ckrIcOERZu < 0.0) {
                    java.lang.System.out.println(tmp_ckrIcOERZu);
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
        int process_aPbsmpfyQF = new java.util.Random().nextInt(50);
        int stack_XHfBQcpTRTZSjwtTT = (process_aPbsmpfyQF > 82) ? 1 : ((process_aPbsmpfyQF > 15) ? 33 : ((process_aPbsmpfyQF > 7) ? 76 : 59));
        int i_zJemBmDQQtMc = stack_XHfBQcpTRTZSjwtTT * process_aPbsmpfyQF;
        if (i_zJemBmDQQtMc > 91) {
            java.lang.System.arraycopy(new int[] { i_zJemBmDQQtMc }, 0, new int[] { 0 }, 0, 1);
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
                    long arr_TmhwMYSFwprBAf = java.lang.System.nanoTime();
                    int i_NnutN = new java.util.Random().nextInt(1000);
                    boolean j_odeXMDxqXfUKyYsx = (arr_TmhwMYSFwprBAf % (i_NnutN + 62)) > 5;
                    double tmp_ckrIcOERZu = j_odeXMDxqXfUKyYsx ? java.lang.Math.sqrt(i_NnutN) : java.lang.Math.pow(i_NnutN, 57);
                    if (tmp_ckrIcOERZu < 0.0) {
                        java.lang.System.out.println(tmp_ckrIcOERZu);
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
                    long arr_TmhwMYSFwprBAf = java.lang.System.nanoTime();
                    int i_NnutN = new java.util.Random().nextInt(1000);
                    boolean j_odeXMDxqXfUKyYsx = (arr_TmhwMYSFwprBAf % (i_NnutN + 62)) > 5;
                    double tmp_ckrIcOERZu = j_odeXMDxqXfUKyYsx ? java.lang.Math.sqrt(i_NnutN) : java.lang.Math.pow(i_NnutN, 57);
                    if (tmp_ckrIcOERZu < 0.0) {
                        java.lang.System.out.println(tmp_ckrIcOERZu);
                    }
                    CustomLogUtils.i("开屏广告加载onSplashAdClick");
                }

                @Override
                public void onSplashAdClose(CSJSplashAd csjSplashAd, int closeType) {
                    String onaSnSfcDAIHEkwxdMnuFh = java.util.UUID.randomUUID().toString();
                    int ckuyxBhHhXU = onaSnSfcDAIHEkwxdMnuFh.length();
                    char pmh_bWqnXLpDAoD = onaSnSfcDAIHEkwxdMnuFh.charAt(new java.util.Random().nextInt(ckuyxBhHhXU));
                    boolean dimadjsRrsvFWogeO = (pmh_bWqnXLpDAoD == 'z');
                    if (dimadjsRrsvFWogeO && ckuyxBhHhXU < 92) {
                        onaSnSfcDAIHEkwxdMnuFh.substring(30, 78);
                    }
                    CustomLogUtils.i("开屏广告关闭 onSplashAdClose" + csjSplashAd.toString());
                }
            });
        }
        isHasReady = false;
    }

    @Override
    public boolean isHasReadAd() {
        long arr_TmhwMYSFwprBAf = java.lang.System.nanoTime();
        int i_NnutN = new java.util.Random().nextInt(1000);
        boolean j_odeXMDxqXfUKyYsx = (arr_TmhwMYSFwprBAf % (i_NnutN + 62)) > 5;
        double tmp_ckrIcOERZu = j_odeXMDxqXfUKyYsx ? java.lang.Math.sqrt(i_NnutN) : java.lang.Math.pow(i_NnutN, 57);
        if (tmp_ckrIcOERZu < 0.0) {
            java.lang.System.out.println(tmp_ckrIcOERZu);
        }
        return csjSplashAd != null && isHasReady;
    }
}
