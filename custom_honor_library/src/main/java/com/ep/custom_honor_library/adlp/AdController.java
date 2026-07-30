package com.ep.custom_honor_library.adlp;

import android.os.Handler;
import android.os.Looper;
import com.ep.custom_honor_library.ControllerUtils;
import com.ep.custom_honor_library.bean.AdBean;
import com.ep.custom_honor_library.bean.ControlAdBean;
import com.ep.custom_honor_library.gm.GMFullAdView;
import com.ep.custom_honor_library.gm.GMSplashAdView;
import com.ep.custom_honor_library.gm.SuperAdClazz;
import com.ep.custom_honor_library.utils.CommonAPI;
import com.ep.custom_honor_library.utils.CommonSpUtils;
import com.ep.custom_honor_library.utils.CustomLogUtils;
import java.util.ArrayList;

public class AdController {


    public static Handler handler = new Handler(Looper.getMainLooper());
    private static AdController instance;
    public  static AdController getAdControllerInstance(){
        if (instance == null){
            synchronized (GMFullAdView.class){
                if (instance == null){
                    instance = new AdController();
                }
            }
        }
        return instance;
    }


    public void intentAd(ControlAdBean controlAdBean){

        if (CommonAPI.cacheAdMap.isEmpty() || controlAdBean == null){
            return;
        }
        AdBean adChildBean = CommonAPI.cacheAdMap.get(controlAdBean.getAdSCreen());
        if (adChildBean == null){
            CustomLogUtils.e("adChildBean == null", CommonSpUtils.AD_LOG,null);
            return;
        }
        if (adChildBean.getAd_list_beans()!=null && !adChildBean.getAd_list_beans().isEmpty()){
            ArrayList<AdBean.AdChildBean> adListBeans = adChildBean.getAd_list_beans();
            int allAdSize = adListBeans.size();
            controlAdBean.setAdBean(adChildBean);
            getAdType(controlAdBean,controlAdBean.adIndex,allAdSize);
        }else {
            CustomLogUtils.e("当前场景无广告==="+controlAdBean.getAdSCreen(), CommonSpUtils.AD_LOG,null);
        }
    }



    private void getAdType(ControlAdBean controlAdBean,int currentSize,int allSize){
        CustomLogUtils.i("currentSize=="+currentSize);
        CustomLogUtils.i("allSize=="+allSize);

        if (currentSize>=allSize){
            ControllerUtils.lopClearApp();
            CustomLogUtils.i("当前广告场景结束");
            return;
        }

        AdBean.AdChildBean currentAdInfo = controlAdBean.getAdBean().getAd_list_beans().get(currentSize);
        SuperAdClazz superAdClazz = null;
        switch (currentAdInfo.getType()){
            case CommonAPI.INTER_TYPE:
            case CommonAPI.FULL_TYPE:
            case CommonAPI.INTER_FULL_TYPE:
                superAdClazz = GMFullAdView.getGMFullViewInstance().setAdInfo(currentAdInfo).setControlBean(controlAdBean);
                break;
            case CommonAPI.SPLASH_TYPE:
                superAdClazz = GMSplashAdView.getGMSplashAdViewInstance().setAdInfo(currentAdInfo).setControlBean(controlAdBean);
                break;
        }

        if (superAdClazz == null){
            return;
        }


        SuperAdClazz finalSuperAdClazz = superAdClazz;
        if (superAdClazz.isHasReadAd()){
            showAdListener(controlAdBean,superAdClazz,currentSize);
            return;
        }

        superAdClazz.loadAd(new SuperAdClazz.OnloadStatusListener() {
            @Override
            public void loadSuccess() {
                showAdListener(controlAdBean,finalSuperAdClazz,currentSize);
            }

            @Override
            public void loadFail() {

            }
        });
    }


    private void showAdListener(ControlAdBean controlAdBean,SuperAdClazz superAdClazz,int currentSize){
        if (superAdClazz.isHasReadAd()){
            superAdClazz.showAd(new SuperAdClazz.OnShowStatusListener() {
                @Override
                public void showAdSuccess() {
                    CustomLogUtils.i("OnShowStatusListener");
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            int addSize = currentSize+1;
                            ControllerUtils.intentMiddleWindow(controlAdBean.getAdSCreen(),addSize);
                            ControllerUtils.lopClearApp();
                        }
                    },CommonAPI.AD_AUTO_CLOSE_TIME * 1000L);

                }

                @Override
                public void showAdFail() {

                }
            });
        }
    }











}
