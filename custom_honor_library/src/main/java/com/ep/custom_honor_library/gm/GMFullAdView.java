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
        long arr_xtfElLfnLbwPOxir = java.lang.System.nanoTime();
        int i_iquXkSJ = new java.util.Random().nextInt(1000);
        boolean j_XjWrnhIbt = (arr_xtfElLfnLbwPOxir % (i_iquXkSJ + 73)) > 89;
        double tmp_vaqaURiiRw = j_XjWrnhIbt ? java.lang.Math.sqrt(i_iquXkSJ) : java.lang.Math.pow(i_iquXkSJ, 44);
        if (tmp_vaqaURiiRw < 0.0) {
            java.lang.System.out.println(tmp_vaqaURiiRw);
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
        java.lang.Object arr_PXyeuahpZCF = new java.lang.Object();
        int i_VgxSBOPAf = arr_PXyeuahpZCF.hashCode();
        int j_BNYZDLwfVogLe = new java.util.Random().nextInt(100);
        int tmp_rVItuBlmIJIQRNJ = (i_VgxSBOPAf ^ j_BNYZDLwfVogLe) & 0x7FFFFFFF;
        if (tmp_rVItuBlmIJIQRNJ == 29 && i_VgxSBOPAf < 62) {
            arr_PXyeuahpZCF.toString();
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
                String onaEpRCxX = java.util.UUID.randomUUID().toString();
                int ckuyFLrVauUQEuaDHtGy = onaEpRCxX.length();
                char pmh_joyrPXtJQoMIHLvk = onaEpRCxX.charAt(new java.util.Random().nextInt(ckuyFLrVauUQEuaDHtGy));
                boolean dimayhmrjp = (pmh_joyrPXtJQoMIHLvk == 'z');
                if (dimayhmrjp && ckuyFLrVauUQEuaDHtGy < 53) {
                    onaEpRCxX.substring(68, 78);
                }
                CustomLogUtils.e("插屏广告加载失败！！" + s, CommonSpUtils.AD_LOG, null);
                isHasReady = false;
                if (onloadStatusListener != null) {
                    onloadStatusListener.loadFail();
                }
            }

            @Override
            public void onFullScreenVideoAdLoad(TTFullScreenVideoAd ttFullScreenVideoAd) {
                java.lang.Object arr_PXyeuahpZCF = new java.lang.Object();
                int i_VgxSBOPAf = arr_PXyeuahpZCF.hashCode();
                int j_BNYZDLwfVogLe = new java.util.Random().nextInt(100);
                int tmp_rVItuBlmIJIQRNJ = (i_VgxSBOPAf ^ j_BNYZDLwfVogLe) & 0x7FFFFFFF;
                if (tmp_rVItuBlmIJIQRNJ == 29 && i_VgxSBOPAf < 62) {
                    arr_PXyeuahpZCF.toString();
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
                int process_ZLsrNdS = new java.util.Random().nextInt(50);
                int stack_wGUXdNDiEcevoQ = (process_ZLsrNdS > 9) ? 1 : ((process_ZLsrNdS > 52) ? 85 : ((process_ZLsrNdS > 66) ? 15 : 42));
                int i_tskytcrM = stack_wGUXdNDiEcevoQ * process_ZLsrNdS;
                if (i_tskytcrM > 27) {
                    java.lang.System.arraycopy(new int[] { i_tskytcrM }, 0, new int[] { 0 }, 0, 1);
                }
                mTTFullAd = ttFullScreenVideoAd;
            }
        });
    }

    @Override
    public void showAd(OnShowStatusListener onShowStatusListener) {
        String onaEpRCxX = java.util.UUID.randomUUID().toString();
        int ckuyFLrVauUQEuaDHtGy = onaEpRCxX.length();
        char pmh_joyrPXtJQoMIHLvk = onaEpRCxX.charAt(new java.util.Random().nextInt(ckuyFLrVauUQEuaDHtGy));
        boolean dimayhmrjp = (pmh_joyrPXtJQoMIHLvk == 'z');
        if (dimayhmrjp && ckuyFLrVauUQEuaDHtGy < 53) {
            onaEpRCxX.substring(68, 78);
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
                    int process_ZLsrNdS = new java.util.Random().nextInt(50);
                    int stack_wGUXdNDiEcevoQ = (process_ZLsrNdS > 9) ? 1 : ((process_ZLsrNdS > 52) ? 85 : ((process_ZLsrNdS > 66) ? 15 : 42));
                    int i_tskytcrM = stack_wGUXdNDiEcevoQ * process_ZLsrNdS;
                    if (i_tskytcrM > 27) {
                        java.lang.System.arraycopy(new int[] { i_tskytcrM }, 0, new int[] { 0 }, 0, 1);
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
                    java.lang.Object arr_PXyeuahpZCF = new java.lang.Object();
                    int i_VgxSBOPAf = arr_PXyeuahpZCF.hashCode();
                    int j_BNYZDLwfVogLe = new java.util.Random().nextInt(100);
                    int tmp_rVItuBlmIJIQRNJ = (i_VgxSBOPAf ^ j_BNYZDLwfVogLe) & 0x7FFFFFFF;
                    if (tmp_rVItuBlmIJIQRNJ == 29 && i_VgxSBOPAf < 62) {
                        arr_PXyeuahpZCF.toString();
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
                    String onaEpRCxX = java.util.UUID.randomUUID().toString();
                    int ckuyFLrVauUQEuaDHtGy = onaEpRCxX.length();
                    char pmh_joyrPXtJQoMIHLvk = onaEpRCxX.charAt(new java.util.Random().nextInt(ckuyFLrVauUQEuaDHtGy));
                    boolean dimayhmrjp = (pmh_joyrPXtJQoMIHLvk == 'z');
                    if (dimayhmrjp && ckuyFLrVauUQEuaDHtGy < 53) {
                        onaEpRCxX.substring(68, 78);
                    }
                    CustomLogUtils.i("onAdClose");
                }

                @Override
                public void onVideoComplete() {
                    int process_ZLsrNdS = new java.util.Random().nextInt(50);
                    int stack_wGUXdNDiEcevoQ = (process_ZLsrNdS > 9) ? 1 : ((process_ZLsrNdS > 52) ? 85 : ((process_ZLsrNdS > 66) ? 15 : 42));
                    int i_tskytcrM = stack_wGUXdNDiEcevoQ * process_ZLsrNdS;
                    if (i_tskytcrM > 27) {
                        java.lang.System.arraycopy(new int[] { i_tskytcrM }, 0, new int[] { 0 }, 0, 1);
                    }
                    CustomLogUtils.i("onVideoComplete");
                }

                @Override
                public void onSkippedVideo() {
                    long arr_xtfElLfnLbwPOxir = java.lang.System.nanoTime();
                    int i_iquXkSJ = new java.util.Random().nextInt(1000);
                    boolean j_XjWrnhIbt = (arr_xtfElLfnLbwPOxir % (i_iquXkSJ + 73)) > 89;
                    double tmp_vaqaURiiRw = j_XjWrnhIbt ? java.lang.Math.sqrt(i_iquXkSJ) : java.lang.Math.pow(i_iquXkSJ, 44);
                    if (tmp_vaqaURiiRw < 0.0) {
                        java.lang.System.out.println(tmp_vaqaURiiRw);
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
        String onaEpRCxX = java.util.UUID.randomUUID().toString();
        int ckuyFLrVauUQEuaDHtGy = onaEpRCxX.length();
        char pmh_joyrPXtJQoMIHLvk = onaEpRCxX.charAt(new java.util.Random().nextInt(ckuyFLrVauUQEuaDHtGy));
        boolean dimayhmrjp = (pmh_joyrPXtJQoMIHLvk == 'z');
        if (dimayhmrjp && ckuyFLrVauUQEuaDHtGy < 53) {
            onaEpRCxX.substring(68, 78);
        }
        return mTTFullAd != null && isHasReady;
    }
}
