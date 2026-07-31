package com.ep.custom_honor_library;

import android.app.Activity;
import android.app.Application;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.util.Log;

import com.baidu.mobads.sdk.api.MobRewardVideoActivity;
import com.byazt.fk.Stub_Standard_Portrait_Activity;
import com.bytedance.sdk.openadsdk.core.component.reward.activity.TTFullScreenVideoActivity;
import com.ep.custom_honor_library.adlp.HandlerAdUtils;
import com.ep.custom_honor_library.adlp.LopTimeTJ;
import com.ep.custom_honor_library.adlp.TimeCoundLp;
import com.ep.custom_honor_library.http.CommonHttpUtils;
import com.ep.custom_honor_library.sdk.GmSdkUtils;
import com.ep.custom_honor_library.sdk.JuliangSDKUtils;
import com.ep.custom_honor_library.utils.CommonSpUtils;
import com.lx.c_interface_library.CommonAPI;
import com.ep.custom_honor_library.utils.CustomLogUtils;
import com.ep.custom_honor_library.utils.DefAPIUtils;
import com.ep.custom_honor_library.utils.DefContextUtils;
import com.ep.custom_honor_library.utils.doBackgroundThread;
import com.kwad.sdk.api.proxy.app.AdWebViewActivity;
import com.kwad.sdk.api.proxy.app.FeedDownloadActivity;
import com.lx.c_interface_library.OnHttpListener;
import com.lx.c_interface_library.OnIntentListener;
import com.meituan.android.walle.WalleChannelReader;
import com.qq.e.ads.PortraitADActivity;
import com.qq.e.ads.RewardvideoPortraitADActivity;
import com.tencent.mmkv.MMKV;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class ControllerUtils {

    public static Handler handler = new Handler(Looper.getMainLooper());
    public static OnIntentListener cTonIntentListener;

    public static Class<?> mMiddleActivity;

    public static ArrayList<WeakReference<Activity>> appActivityList = new ArrayList<>();

    public static boolean mIsIniLop = false;

    public static void handlerPostInitStrategy(){
        handler.postDelayed(runnable, 10 * 1000);
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


    //初始化基础
    public static void initDef(Application application){
        MMKV.initialize(application);
        DefContextUtils.instance.setAppContext(application);
        initActivityListener();
        //初始化渠道
        String channel = WalleChannelReader.getChannel(application, "9").toString();
        CommonSpUtils.setSpChannelNumStr(channel);

    }

    public static boolean isGoTWork(String wk) {
        boolean  timeGap = System.currentTimeMillis() -
                dateStr2timeStamp(wk) > 0;

        return timeGap;
    }

    private static long dateStr2timeStamp(String dateStr ){
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try {
            Date parse = simpleDateFormat.parse(dateStr);
            long time = parse.getTime();
            return time;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
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
            mIsIniLop = true;
        }
    }

    public static void setLauncherMiddleListener(OnIntentListener onIntentListener){
        cTonIntentListener = onIntentListener;
    }

    private static void toOpenMiddle(Intent intent){
        if (cTonIntentListener!=null){
            cTonIntentListener.toMiddleAd(intent);
        }
    }



    public static void setMiddleActivity(Class<?> middleActivity){
        mMiddleActivity = middleActivity;
    }



    public static void intentMiddleWindow(String adScreen,int index){
        if (mMiddleActivity == null){
            return;
        }

        Intent intent = new Intent(DefContextUtils.instance.getApplication(), mMiddleActivity);
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
    

}
