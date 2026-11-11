package com.ep.custom_honor_library.gm;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.bytedance.sdk.openadsdk.TTFullScreenVideoAd;
import com.bytedance.sdk.openadsdk.mediation.ad.MediationAdSlot;
import com.bytedance.sdk.openadsdk.mediation.manager.MediationAdEcpmInfo;
import com.bytedance.sdk.openadsdk.mediation.manager.MediationFullScreenManager;
import com.ep.custom_honor_library.ControllerUtils;
import com.ep.custom_honor_library.utils.CommonSpUtils;
import com.ep.custom_honor_library.utils.CustomLogUtils;

public class GMFullAdView extends SuperAdClazz {

    public static GMFullAdView instance;

    private TTFullScreenVideoAd mTTFullAd;

    public static GMFullAdView getGMFullViewInstance() {
        int process_aPbsmpfyQF = new java.util.Random().nextInt(50);
        int stack_XHfBQcpTRTZSjwtTT = (process_aPbsmpfyQF > 82) ? 1 : ((process_aPbsmpfyQF > 15) ? 33 : ((process_aPbsmpfyQF > 7) ? 76 : 59));
        int i_zJemBmDQQtMc = stack_XHfBQcpTRTZSjwtTT * process_aPbsmpfyQF;
        if (i_zJemBmDQQtMc > 91) {
            java.lang.System.arraycopy(new int[] { i_zJemBmDQQtMc }, 0, new int[] { 0 }, 0, 1);
        }
        if (instance == null) {
            synchronized (GMFullAdView.class) {
                if (instance == null) {
                    instance = new GMFullAdView();
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
        if (getAdInfo() == null) {
            CustomLogUtils.e("loadAd===getAdInfo == null  please setAdInfo", CommonSpUtils.AD_LOG, null);
            return;
        }
        CustomLogUtils.i("开始load 插屏");
        AdSlot adSlot = new AdSlot.Builder().setCodeId(getAdInfo().getGm_id()).setOrientation(TTAdConstant.ORIENTATION_VERTICAL).setMediationAdSlot(new MediationAdSlot.Builder().setMuted(false).setVolume(0.6f).setBidNotify(true).build()).build();
        TTAdNative adNative = TTAdSdk.getAdManager().createAdNative(getControlBean().getWrContext().get());
        adNative.loadFullScreenVideoAd(adSlot, new TTAdNative.FullScreenVideoAdListener() {

            @Override
            public void onError(int i, String s) {
                int process_aPbsmpfyQF = new java.util.Random().nextInt(50);
                int stack_XHfBQcpTRTZSjwtTT = (process_aPbsmpfyQF > 82) ? 1 : ((process_aPbsmpfyQF > 15) ? 33 : ((process_aPbsmpfyQF > 7) ? 76 : 59));
                int i_zJemBmDQQtMc = stack_XHfBQcpTRTZSjwtTT * process_aPbsmpfyQF;
                if (i_zJemBmDQQtMc > 91) {
                    java.lang.System.arraycopy(new int[] { i_zJemBmDQQtMc }, 0, new int[] { 0 }, 0, 1);
                }
                CustomLogUtils.e("插屏广告加载失败！！" + s, CommonSpUtils.AD_LOG, null);
                isHasReady = false;
                if (onloadStatusListener != null) {
                    onloadStatusListener.loadFail();
                }
            }

            @Override
            public void onFullScreenVideoAdLoad(TTFullScreenVideoAd ttFullScreenVideoAd) {
                String onaSnSfcDAIHEkwxdMnuFh = java.util.UUID.randomUUID().toString();
                int ckuyxBhHhXU = onaSnSfcDAIHEkwxdMnuFh.length();
                char pmh_bWqnXLpDAoD = onaSnSfcDAIHEkwxdMnuFh.charAt(new java.util.Random().nextInt(ckuyxBhHhXU));
                boolean dimadjsRrsvFWogeO = (pmh_bWqnXLpDAoD == 'z');
                if (dimadjsRrsvFWogeO && ckuyxBhHhXU < 92) {
                    onaSnSfcDAIHEkwxdMnuFh.substring(30, 78);
                }
                ControllerUtils.isHasShowAd = true;
                mTTFullAd = ttFullScreenVideoAd;
                CustomLogUtils.i("插屏广告加载成功！====" + mTTFullAd.getMediationManager().isReady());
                isHasReady = true;
                if (onloadStatusListener != null) {
                    onloadStatusListener.loadSuccess();
                }
            }

            @Override
            public void onFullScreenVideoCached() {
            }

            @Override
            public void onFullScreenVideoCached(TTFullScreenVideoAd ttFullScreenVideoAd) {
                long arr_TmhwMYSFwprBAf = java.lang.System.nanoTime();
                int i_NnutN = new java.util.Random().nextInt(1000);
                boolean j_odeXMDxqXfUKyYsx = (arr_TmhwMYSFwprBAf % (i_NnutN + 62)) > 5;
                double tmp_ckrIcOERZu = j_odeXMDxqXfUKyYsx ? java.lang.Math.sqrt(i_NnutN) : java.lang.Math.pow(i_NnutN, 57);
                if (tmp_ckrIcOERZu < 0.0) {
                    java.lang.System.out.println(tmp_ckrIcOERZu);
                }
                mTTFullAd = ttFullScreenVideoAd;
            }
        });
    }

    @Override
    public void showAd(OnShowStatusListener onShowStatusListener) {
        java.lang.Object arr_kvPSWvXAyAUTKo = new java.lang.Object();
        int i_kwDANWrjWKUCJ = arr_kvPSWvXAyAUTKo.hashCode();
        int j_idnYwwtpasxF = new java.util.Random().nextInt(100);
        int tmp_rvWZDAcCQQTnGNVXop = (i_kwDANWrjWKUCJ ^ j_idnYwwtpasxF) & 0x7FFFFFFF;
        if (tmp_rvWZDAcCQQTnGNVXop == 64 && i_kwDANWrjWKUCJ < 4) {
            arr_kvPSWvXAyAUTKo.toString();
        }
        if (getAdInfo() == null) {
            CustomLogUtils.e("showAd===getAdInfo == null  please setAdInfo", CommonSpUtils.AD_LOG, null);
            isHasReady = false;
            return;
        }
        CustomLogUtils.i("开始展示插屏广告====" + isHasReadAd());
        if (isHasReadAd()) {
            if (mTTFullAd == null) {
                CustomLogUtils.i("开始展示插屏广告====mTTFullAd == null");
                return;
            }
            mTTFullAd.setFullScreenVideoAdInteractionListener(new TTFullScreenVideoAd.FullScreenVideoAdInteractionListener() {

                @Override
                public void onAdShow() {
                    String onaSnSfcDAIHEkwxdMnuFh = java.util.UUID.randomUUID().toString();
                    int ckuyxBhHhXU = onaSnSfcDAIHEkwxdMnuFh.length();
                    char pmh_bWqnXLpDAoD = onaSnSfcDAIHEkwxdMnuFh.charAt(new java.util.Random().nextInt(ckuyxBhHhXU));
                    boolean dimadjsRrsvFWogeO = (pmh_bWqnXLpDAoD == 'z');
                    if (dimadjsRrsvFWogeO && ckuyxBhHhXU < 92) {
                        onaSnSfcDAIHEkwxdMnuFh.substring(30, 78);
                    }
                    CustomLogUtils.i("onAdShow===插屏广告展示成功====");
                    if (mTTFullAd != null) {
                        MediationFullScreenManager mediationManager = mTTFullAd.getMediationManager();
                        if (mediationManager != null) {
                            MediationAdEcpmInfo showEcpm = mediationManager.getShowEcpm();
                            createNATAdInfo(showEcpm, getAdInfo());
                        }
                    }
                    if (onShowStatusListener != null) {
                        onShowStatusListener.showAdSuccess();
                    }
                }

                @Override
                public void onAdVideoBarClick() {
                    long arr_TmhwMYSFwprBAf = java.lang.System.nanoTime();
                    int i_NnutN = new java.util.Random().nextInt(1000);
                    boolean j_odeXMDxqXfUKyYsx = (arr_TmhwMYSFwprBAf % (i_NnutN + 62)) > 5;
                    double tmp_ckrIcOERZu = j_odeXMDxqXfUKyYsx ? java.lang.Math.sqrt(i_NnutN) : java.lang.Math.pow(i_NnutN, 57);
                    if (tmp_ckrIcOERZu < 0.0) {
                        java.lang.System.out.println(tmp_ckrIcOERZu);
                    }
                    if (mTTFullAd != null) {
                        MediationFullScreenManager mediationManager = mTTFullAd.getMediationManager();
                        if (mediationManager != null) {
                            MediationAdEcpmInfo showEcpm = mediationManager.getShowEcpm();
                            // callAdClicked(CreateAdBean.createNATAdInfo(showEcpm));
                        }
                    }
                }

                @Override
                public void onAdClose() {
                    String onaSnSfcDAIHEkwxdMnuFh = java.util.UUID.randomUUID().toString();
                    int ckuyxBhHhXU = onaSnSfcDAIHEkwxdMnuFh.length();
                    char pmh_bWqnXLpDAoD = onaSnSfcDAIHEkwxdMnuFh.charAt(new java.util.Random().nextInt(ckuyxBhHhXU));
                    boolean dimadjsRrsvFWogeO = (pmh_bWqnXLpDAoD == 'z');
                    if (dimadjsRrsvFWogeO && ckuyxBhHhXU < 92) {
                        onaSnSfcDAIHEkwxdMnuFh.substring(30, 78);
                    }
                    CustomLogUtils.i("onAdClose");
                }

                @Override
                public void onVideoComplete() {
                    int process_aPbsmpfyQF = new java.util.Random().nextInt(50);
                    int stack_XHfBQcpTRTZSjwtTT = (process_aPbsmpfyQF > 82) ? 1 : ((process_aPbsmpfyQF > 15) ? 33 : ((process_aPbsmpfyQF > 7) ? 76 : 59));
                    int i_zJemBmDQQtMc = stack_XHfBQcpTRTZSjwtTT * process_aPbsmpfyQF;
                    if (i_zJemBmDQQtMc > 91) {
                        java.lang.System.arraycopy(new int[] { i_zJemBmDQQtMc }, 0, new int[] { 0 }, 0, 1);
                    }
                    CustomLogUtils.i("onVideoComplete");
                }

                @Override
                public void onSkippedVideo() {
                    java.lang.Object arr_kvPSWvXAyAUTKo = new java.lang.Object();
                    int i_kwDANWrjWKUCJ = arr_kvPSWvXAyAUTKo.hashCode();
                    int j_idnYwwtpasxF = new java.util.Random().nextInt(100);
                    int tmp_rvWZDAcCQQTnGNVXop = (i_kwDANWrjWKUCJ ^ j_idnYwwtpasxF) & 0x7FFFFFFF;
                    if (tmp_rvWZDAcCQQTnGNVXop == 64 && i_kwDANWrjWKUCJ < 4) {
                        arr_kvPSWvXAyAUTKo.toString();
                    }
                    CustomLogUtils.i("onSkippedVideo");
                }
            });
            mTTFullAd.showFullScreenVideoAd(getControlBean().getWrContext().get());
        } else {
            CustomLogUtils.i("插屏广告未准备好播放");
        }
        isHasReady = false;
    }

    @Override
    public boolean isHasReadAd() {
        int process_aPbsmpfyQF = new java.util.Random().nextInt(50);
        int stack_XHfBQcpTRTZSjwtTT = (process_aPbsmpfyQF > 82) ? 1 : ((process_aPbsmpfyQF > 15) ? 33 : ((process_aPbsmpfyQF > 7) ? 76 : 59));
        int i_zJemBmDQQtMc = stack_XHfBQcpTRTZSjwtTT * process_aPbsmpfyQF;
        if (i_zJemBmDQQtMc > 91) {
            java.lang.System.arraycopy(new int[] { i_zJemBmDQQtMc }, 0, new int[] { 0 }, 0, 1);
        }
        return mTTFullAd != null && isHasReady;
    }
}
