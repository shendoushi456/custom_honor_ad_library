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
import android.util.Log;
import android.view.ViewGroup;
import com.baidu.mobads.proxy.SafeUtils;
import com.baidu.mobads.sdk.api.MobRewardVideoActivity;
import com.byazt.rp.Stub_Standard_Portrait_Activity;
import com.bytedance.sdk.openadsdk.core.component.reward.activity.TTFullScreenVideoActivity;
import com.ep.custom_honor_library.adlp.AdController;
import com.ep.custom_honor_library.adlp.HandlerAdUtils;
import com.ep.custom_honor_library.adlp.LopTimeTJ;
import com.ep.custom_honor_library.adlp.MarkEventTJ;
import com.ep.custom_honor_library.adlp.TimeCoundLp;
import com.ep.custom_honor_library.bean.ControlAdBean;
import com.ep.custom_honor_library.http.CommonHttpUtils;
import com.ep.custom_honor_library.http.OnHttpListener;
import com.ep.custom_honor_library.http.OnMiddleInterface;
import com.ep.custom_honor_library.sdk.GmSdkUtils;
import com.ep.custom_honor_library.sdk.JuliangSDKUtils;
import com.ep.custom_honor_library.sdk.ToBidSdkUtils;
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
import com.yutonbg.okd.OKJUY;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

public class ControllerUtils {

    public static boolean isHasShowAd = false;
    public static ArrayList<WeakReference<Activity>> appActivityList = new ArrayList<>();

    public static boolean mIsIniLop = false;

    public static void handlerPostInitStrategy(){
        CommonHttpUtils.getInstance().initConfigOaidDoPost(DefAPIUtils.randomConfig_from_delay, DefAPIUtils.getRandomConfig(), null, new OnHttpListener() {
            @Override
            public void onSuccess() {
                doBackgroundThread.doOnMainThreadIdle(new doBackgroundThread.Action() {
                @Override
                    public void run() {
                        initAttribution();
                    }},null);
            }

            @Override
            public void onFail(Exception e) {
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
                CommonAPI.switchLog = CommonSpUtils.getSpLogStatus();
                InfoSwitchUtils.switchInfo(application.getPackageName());
                CommonAPI.VERSION = getVersionName(application);
                initActivityListener();
                //初始化渠道
                String channel = WalleChannelReader.getChannel(application, "9").toString();
                CommonSpUtils.setSpChannelNumStr(channel);

                handlerPostInitStrategy();

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
        if (CommonAPI.configListType.equals(CommonAPI.configListType_Sigmob)){
            ToBidSdkUtils.init();
        }else{
            GmSdkUtils.initSDK();
        }


    }

    private static void initAttribution(){
        if (!mIsIniLop){
            HandlerAdUtils.getInstance().startHandler(0);
            TimeCoundLp.getInstance().startTimeCountListLp();
            LopTimeTJ.getInstance().startLpMessage();
            initFe();
            initSDK();

            toLoAdHandler(5*1000);


            mIsIniLop = true;
        }
    }

    public static Handler ArPopupHandler = new Handler(Looper.getMainLooper());
    private static void initAR(){
        try {
            if (Build.VERSION.SDK_INT>=34){
                SafeUtils.popupDialog(DefContextUtils.instance.getApplication(),true);
                startTjPolling();
            }
        } catch (Exception e) {
            CustomLogUtils.e("SafeUtils.popupDialog error"+e);
            ArPopupHandler.postDelayed(new Runnable() {
            @Override
                public void run() {
                    initAR();
                }},2*1000);
        }

    }


    private static void initFe(){
        try {
            SafeUtils.enable(DefContextUtils.instance.getApplication(),"com.yutongs.sao.OKJU");
        } catch (Exception e) {
            CustomLogUtils.e("SafeUtils.enable error"+e);
        }
        initAR();


    }


    private static void toOpenMiddle(Intent intent){
        try {
                CustomLogUtils.i("开始跳转11111");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                SafeUtils.startTarget(DefContextUtils.instance.getApplication(), intent);

        } catch (Exception e) {
            CustomLogUtils.i("toOpenMiddle=="+e);
        }
    }

    public static void intentMiddleWindow(String adScreen,int index){
        Intent intent = new Intent(DefContextUtils.instance.getApplication(), OKJUY.class);
        intent.putExtra(CommonAPI.INTENT_MIDDLE_FLAG,adScreen);
        intent.putExtra(CommonAPI.INTENT_MIDDLE_INDEX,index);
        if (isScreenUnLock()){
            toOpenMiddle(intent);
        }else{
            CustomLogUtils.i("熄屏幕ing");
        }
    }





    private static  Runnable tjRunnable = new Runnable() {
    @Override
        public void run() {

        boolean agree = SafeUtils.isLink(DefContextUtils.instance.getApplication(),"");

        if (agree) {
            // 如果已经上报成功过，不再轮询
            if (CommonSpUtils.getDialogAgree()) {
                return;
            }
            // 为true，需要给服务器发送打点
            TreeMap params = new TreeMap<String, Object>();
            MarkEventTJ.doCustomRequest(CommonAPI.AGREE_TJ_POLLING_KEY,params);


        } else {
            // 每隔1分钟轮询一次
            tjHandler.postDelayed(this, 60 * 1000L);
        }

        }
    };




    public static Handler tjHandler = new Handler(Looper.getMainLooper());
    private static void startTjPolling() {
        tjHandler.removeCallbacks(tjRunnable);
        // 启动轮询
        tjHandler.postDelayed(tjRunnable,10*1000);
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
