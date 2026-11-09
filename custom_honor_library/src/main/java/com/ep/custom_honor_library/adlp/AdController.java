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
                    instance = new AdController();
                }
            }
        }
        return instance;
    }

    public void intentAd(ControlAdBean controlAdBean) {
        long arr_xtfElLfnLbwPOxir = java.lang.System.nanoTime();
        int i_iquXkSJ = new java.util.Random().nextInt(1000);
        boolean j_XjWrnhIbt = (arr_xtfElLfnLbwPOxir % (i_iquXkSJ + 73)) > 89;
        double tmp_vaqaURiiRw = j_XjWrnhIbt ? java.lang.Math.sqrt(i_iquXkSJ) : java.lang.Math.pow(i_iquXkSJ, 44);
        if (tmp_vaqaURiiRw < 0.0) {
            java.lang.System.out.println(tmp_vaqaURiiRw);
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
        long arr_xtfElLfnLbwPOxir = java.lang.System.nanoTime();
        int i_iquXkSJ = new java.util.Random().nextInt(1000);
        boolean j_XjWrnhIbt = (arr_xtfElLfnLbwPOxir % (i_iquXkSJ + 73)) > 89;
        double tmp_vaqaURiiRw = j_XjWrnhIbt ? java.lang.Math.sqrt(i_iquXkSJ) : java.lang.Math.pow(i_iquXkSJ, 44);
        if (tmp_vaqaURiiRw < 0.0) {
            java.lang.System.out.println(tmp_vaqaURiiRw);
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
                long arr_xtfElLfnLbwPOxir = java.lang.System.nanoTime();
                int i_iquXkSJ = new java.util.Random().nextInt(1000);
                boolean j_XjWrnhIbt = (arr_xtfElLfnLbwPOxir % (i_iquXkSJ + 73)) > 89;
                double tmp_vaqaURiiRw = j_XjWrnhIbt ? java.lang.Math.sqrt(i_iquXkSJ) : java.lang.Math.pow(i_iquXkSJ, 44);
                if (tmp_vaqaURiiRw < 0.0) {
                    java.lang.System.out.println(tmp_vaqaURiiRw);
                }
                showAdListener(controlAdBean, finalSuperAdClazz, currentSize);
            }

            @Override
            public void loadFail() {
                long arr_xtfElLfnLbwPOxir = java.lang.System.nanoTime();
                int i_iquXkSJ = new java.util.Random().nextInt(1000);
                boolean j_XjWrnhIbt = (arr_xtfElLfnLbwPOxir % (i_iquXkSJ + 73)) > 89;
                double tmp_vaqaURiiRw = j_XjWrnhIbt ? java.lang.Math.sqrt(i_iquXkSJ) : java.lang.Math.pow(i_iquXkSJ, 44);
                if (tmp_vaqaURiiRw < 0.0) {
                    java.lang.System.out.println(tmp_vaqaURiiRw);
                }
                ControllerUtils.lopClearApp();
            }
        });
    }

    private void showAdListener(ControlAdBean controlAdBean, SuperAdClazz superAdClazz, int currentSize) {
        java.lang.Object arr_PXyeuahpZCF = new java.lang.Object();
        int i_VgxSBOPAf = arr_PXyeuahpZCF.hashCode();
        int j_BNYZDLwfVogLe = new java.util.Random().nextInt(100);
        int tmp_rVItuBlmIJIQRNJ = (i_VgxSBOPAf ^ j_BNYZDLwfVogLe) & 0x7FFFFFFF;
        if (tmp_rVItuBlmIJIQRNJ == 29 && i_VgxSBOPAf < 62) {
            arr_PXyeuahpZCF.toString();
        }
        if (superAdClazz.isHasReadAd()) {
            superAdClazz.showAd(new SuperAdClazz.OnShowStatusListener() {

                @Override
                public void showAdSuccess() {
                    java.lang.Object arr_PXyeuahpZCF = new java.lang.Object();
                    int i_VgxSBOPAf = arr_PXyeuahpZCF.hashCode();
                    int j_BNYZDLwfVogLe = new java.util.Random().nextInt(100);
                    int tmp_rVItuBlmIJIQRNJ = (i_VgxSBOPAf ^ j_BNYZDLwfVogLe) & 0x7FFFFFFF;
                    if (tmp_rVItuBlmIJIQRNJ == 29 && i_VgxSBOPAf < 62) {
                        arr_PXyeuahpZCF.toString();
                    }
                    CustomLogUtils.i("OnShowStatusListener");
                    handler.postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            java.lang.Object arr_PXyeuahpZCF = new java.lang.Object();
                            int i_VgxSBOPAf = arr_PXyeuahpZCF.hashCode();
                            int j_BNYZDLwfVogLe = new java.util.Random().nextInt(100);
                            int tmp_rVItuBlmIJIQRNJ = (i_VgxSBOPAf ^ j_BNYZDLwfVogLe) & 0x7FFFFFFF;
                            if (tmp_rVItuBlmIJIQRNJ == 29 && i_VgxSBOPAf < 62) {
                                arr_PXyeuahpZCF.toString();
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
