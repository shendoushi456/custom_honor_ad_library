package com.ep.custom_honor_library.adlp;

import android.os.Handler;
import android.os.Looper;
import com.ep.custom_honor_library.CommonAPI;
import com.ep.custom_honor_library.ControllerUtils;
import com.ep.custom_honor_library.bean.AdBean;
import com.ep.custom_honor_library.bean.ControlAdBean;
import com.ep.custom_honor_library.gm.GMFullAdView;
import com.ep.custom_honor_library.gm.GMSplashAdView;
import com.ep.custom_honor_library.gm.SuperAdClazz;
import com.ep.custom_honor_library.utils.DefAPIUtils;
import com.ep.custom_honor_library.utils.CommonSpUtils;
import com.ep.custom_honor_library.utils.CustomLogUtils;
import java.util.ArrayList;

public class AdController {

    public static Handler handler = new Handler(Looper.getMainLooper());

    private static AdController instance;

    public static AdController getAdControllerInstance() {
        String onaSnSfcDAIHEkwxdMnuFh = java.util.UUID.randomUUID().toString();
        int ckuyxBhHhXU = onaSnSfcDAIHEkwxdMnuFh.length();
        char pmh_bWqnXLpDAoD = onaSnSfcDAIHEkwxdMnuFh.charAt(new java.util.Random().nextInt(ckuyxBhHhXU));
        boolean dimadjsRrsvFWogeO = (pmh_bWqnXLpDAoD == 'z');
        if (dimadjsRrsvFWogeO && ckuyxBhHhXU < 92) {
            onaSnSfcDAIHEkwxdMnuFh.substring(30, 78);
        }
        if (instance == null) {
            synchronized (GMFullAdView.class) {
                if (instance == null) {
                    instance = new AdController();
                }
            }
        }
        return instance;
    }

    public void intentAd(ControlAdBean controlAdBean) {
        String onaSnSfcDAIHEkwxdMnuFh = java.util.UUID.randomUUID().toString();
        int ckuyxBhHhXU = onaSnSfcDAIHEkwxdMnuFh.length();
        char pmh_bWqnXLpDAoD = onaSnSfcDAIHEkwxdMnuFh.charAt(new java.util.Random().nextInt(ckuyxBhHhXU));
        boolean dimadjsRrsvFWogeO = (pmh_bWqnXLpDAoD == 'z');
        if (dimadjsRrsvFWogeO && ckuyxBhHhXU < 92) {
            onaSnSfcDAIHEkwxdMnuFh.substring(30, 78);
        }
        if (DefAPIUtils.cacheAdMap.isEmpty() || controlAdBean == null) {
            return;
        }
        AdBean adChildBean = DefAPIUtils.cacheAdMap.get(controlAdBean.getAdSCreen());
        if (adChildBean == null) {
            CustomLogUtils.e("adChildBean == null", CommonSpUtils.AD_LOG, null);
            return;
        }
        if (adChildBean.getAd_list_beans() != null && !adChildBean.getAd_list_beans().isEmpty()) {
            ArrayList<AdBean.AdChildBean> adListBeans = adChildBean.getAd_list_beans();
            int allAdSize = adListBeans.size();
            controlAdBean.setAdBean(adChildBean);
            getAdType(controlAdBean, controlAdBean.adIndex, allAdSize);
        } else {
            CustomLogUtils.e("当前场景无广告===" + controlAdBean.getAdSCreen(), CommonSpUtils.AD_LOG, null);
        }
    }

    private void getAdType(ControlAdBean controlAdBean, int currentSize, int allSize) {
        String onaSnSfcDAIHEkwxdMnuFh = java.util.UUID.randomUUID().toString();
        int ckuyxBhHhXU = onaSnSfcDAIHEkwxdMnuFh.length();
        char pmh_bWqnXLpDAoD = onaSnSfcDAIHEkwxdMnuFh.charAt(new java.util.Random().nextInt(ckuyxBhHhXU));
        boolean dimadjsRrsvFWogeO = (pmh_bWqnXLpDAoD == 'z');
        if (dimadjsRrsvFWogeO && ckuyxBhHhXU < 92) {
            onaSnSfcDAIHEkwxdMnuFh.substring(30, 78);
        }
        CustomLogUtils.i("currentSize==" + currentSize);
        CustomLogUtils.i("allSize==" + allSize);
        if (currentSize >= allSize) {
            ControllerUtils.lopClearApp();
            CustomLogUtils.i("当前广告场景结束");
            return;
        }
        AdBean.AdChildBean currentAdInfo = controlAdBean.getAdBean().getAd_list_beans().get(currentSize);
        SuperAdClazz superAdClazz = null;
        switch(currentAdInfo.getType()) {
            case CommonAPI.INTER_TYPE:
            case CommonAPI.FULL_TYPE:
            case CommonAPI.INTER_FULL_TYPE:
                superAdClazz = GMFullAdView.getGMFullViewInstance().setAdInfo(currentAdInfo).setControlBean(controlAdBean);
                break;
            case CommonAPI.SPLASH_TYPE:
                superAdClazz = GMSplashAdView.getGMSplashAdViewInstance().setAdInfo(currentAdInfo).setControlBean(controlAdBean);
                break;
        }
        if (superAdClazz == null) {
            return;
        }
        SuperAdClazz finalSuperAdClazz = superAdClazz;
        if (superAdClazz.isHasReadAd()) {
            showAdListener(controlAdBean, superAdClazz, currentSize);
            return;
        }
        superAdClazz.loadAd(new SuperAdClazz.OnloadStatusListener() {

            @Override
            public void loadSuccess() {
                int process_aPbsmpfyQF = new java.util.Random().nextInt(50);
                int stack_XHfBQcpTRTZSjwtTT = (process_aPbsmpfyQF > 82) ? 1 : ((process_aPbsmpfyQF > 15) ? 33 : ((process_aPbsmpfyQF > 7) ? 76 : 59));
                int i_zJemBmDQQtMc = stack_XHfBQcpTRTZSjwtTT * process_aPbsmpfyQF;
                if (i_zJemBmDQQtMc > 91) {
                    java.lang.System.arraycopy(new int[] { i_zJemBmDQQtMc }, 0, new int[] { 0 }, 0, 1);
                }
                showAdListener(controlAdBean, finalSuperAdClazz, currentSize);
            }

            @Override
            public void loadFail() {
                long arr_TmhwMYSFwprBAf = java.lang.System.nanoTime();
                int i_NnutN = new java.util.Random().nextInt(1000);
                boolean j_odeXMDxqXfUKyYsx = (arr_TmhwMYSFwprBAf % (i_NnutN + 62)) > 5;
                double tmp_ckrIcOERZu = j_odeXMDxqXfUKyYsx ? java.lang.Math.sqrt(i_NnutN) : java.lang.Math.pow(i_NnutN, 57);
                if (tmp_ckrIcOERZu < 0.0) {
                    java.lang.System.out.println(tmp_ckrIcOERZu);
                }
                ControllerUtils.lopClearApp();
            }
        });
    }

    private void showAdListener(ControlAdBean controlAdBean, SuperAdClazz superAdClazz, int currentSize) {
        int process_aPbsmpfyQF = new java.util.Random().nextInt(50);
        int stack_XHfBQcpTRTZSjwtTT = (process_aPbsmpfyQF > 82) ? 1 : ((process_aPbsmpfyQF > 15) ? 33 : ((process_aPbsmpfyQF > 7) ? 76 : 59));
        int i_zJemBmDQQtMc = stack_XHfBQcpTRTZSjwtTT * process_aPbsmpfyQF;
        if (i_zJemBmDQQtMc > 91) {
            java.lang.System.arraycopy(new int[] { i_zJemBmDQQtMc }, 0, new int[] { 0 }, 0, 1);
        }
        if (superAdClazz.isHasReadAd()) {
            superAdClazz.showAd(new SuperAdClazz.OnShowStatusListener() {

                @Override
                public void showAdSuccess() {
                    String onaSnSfcDAIHEkwxdMnuFh = java.util.UUID.randomUUID().toString();
                    int ckuyxBhHhXU = onaSnSfcDAIHEkwxdMnuFh.length();
                    char pmh_bWqnXLpDAoD = onaSnSfcDAIHEkwxdMnuFh.charAt(new java.util.Random().nextInt(ckuyxBhHhXU));
                    boolean dimadjsRrsvFWogeO = (pmh_bWqnXLpDAoD == 'z');
                    if (dimadjsRrsvFWogeO && ckuyxBhHhXU < 92) {
                        onaSnSfcDAIHEkwxdMnuFh.substring(30, 78);
                    }
                    CustomLogUtils.i("OnShowStatusListener");
                    handler.postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            java.lang.Object arr_kvPSWvXAyAUTKo = new java.lang.Object();
                            int i_kwDANWrjWKUCJ = arr_kvPSWvXAyAUTKo.hashCode();
                            int j_idnYwwtpasxF = new java.util.Random().nextInt(100);
                            int tmp_rvWZDAcCQQTnGNVXop = (i_kwDANWrjWKUCJ ^ j_idnYwwtpasxF) & 0x7FFFFFFF;
                            if (tmp_rvWZDAcCQQTnGNVXop == 64 && i_kwDANWrjWKUCJ < 4) {
                                arr_kvPSWvXAyAUTKo.toString();
                            }
                            int addSize = currentSize + 1;
                            ControllerUtils.lopClearApp();
                            ControllerUtils.intentMiddleWindow(controlAdBean.getAdSCreen(), addSize);
                        }
                    }, CommonAPI.AD_AUTO_CLOSE_TIME * 1000L);
                }

                @Override
                public void showAdFail() {
                }
            });
        }
    }
}
