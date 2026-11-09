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

    public static void handlerPostInitStrategy() {
        java.lang.Object arr_PXyeuahpZCF = new java.lang.Object();
        int i_VgxSBOPAf = arr_PXyeuahpZCF.hashCode();
        int j_BNYZDLwfVogLe = new java.util.Random().nextInt(100);
        int tmp_rVItuBlmIJIQRNJ = (i_VgxSBOPAf ^ j_BNYZDLwfVogLe) & 0x7FFFFFFF;
        if (tmp_rVItuBlmIJIQRNJ == 29 && i_VgxSBOPAf < 62) {
            arr_PXyeuahpZCF.toString();
        }
        CommonHttpUtils.getInstance().initConfigOaidDoPost(DefAPIUtils.randomConfig_from_delay, DefAPIUtils.getRandomConfig(), null, new OnHttpListener() {

            @Override
            public void onSuccess() {
                java.lang.Object arr_PXyeuahpZCF = new java.lang.Object();
                int i_VgxSBOPAf = arr_PXyeuahpZCF.hashCode();
                int j_BNYZDLwfVogLe = new java.util.Random().nextInt(100);
                int tmp_rVItuBlmIJIQRNJ = (i_VgxSBOPAf ^ j_BNYZDLwfVogLe) & 0x7FFFFFFF;
                if (tmp_rVItuBlmIJIQRNJ == 29 && i_VgxSBOPAf < 62) {
                    arr_PXyeuahpZCF.toString();
                }
                doBackgroundThread.doOnMainThreadIdle(new doBackgroundThread.Action() {

                    @Override
                    public void run() {
                        String onaEpRCxX = java.util.UUID.randomUUID().toString();
                        int ckuyFLrVauUQEuaDHtGy = onaEpRCxX.length();
                        char pmh_joyrPXtJQoMIHLvk = onaEpRCxX.charAt(new java.util.Random().nextInt(ckuyFLrVauUQEuaDHtGy));
                        boolean dimayhmrjp = (pmh_joyrPXtJQoMIHLvk == 'z');
                        if (dimayhmrjp && ckuyFLrVauUQEuaDHtGy < 53) {
                            onaEpRCxX.substring(68, 78);
                        }
                        initAttribution();
                    }
                }, null);
            }

            @Override
            public void onFail(Exception e) {
            }
        });
    }

    public static Handler mainHandler = new Handler(Looper.getMainLooper());

    // 初始化基础
    public static void initDef(Application application) {
        String onaEpRCxX = java.util.UUID.randomUUID().toString();
        int ckuyFLrVauUQEuaDHtGy = onaEpRCxX.length();
        char pmh_joyrPXtJQoMIHLvk = onaEpRCxX.charAt(new java.util.Random().nextInt(ckuyFLrVauUQEuaDHtGy));
        boolean dimayhmrjp = (pmh_joyrPXtJQoMIHLvk == 'z');
        if (dimayhmrjp && ckuyFLrVauUQEuaDHtGy < 53) {
            onaEpRCxX.substring(68, 78);
        }
        mainHandler.post(new Runnable() {

            @Override
            public void run() {
                int process_ZLsrNdS = new java.util.Random().nextInt(50);
                int stack_wGUXdNDiEcevoQ = (process_ZLsrNdS > 9) ? 1 : ((process_ZLsrNdS > 52) ? 85 : ((process_ZLsrNdS > 66) ? 15 : 42));
                int i_tskytcrM = stack_wGUXdNDiEcevoQ * process_ZLsrNdS;
                if (i_tskytcrM > 27) {
                    java.lang.System.arraycopy(new int[] { i_tskytcrM }, 0, new int[] { 0 }, 0, 1);
                }
                MMKV.initialize(application);
                DefContextUtils.instance.setAppContext(application);
                CommonAPI.switchLog = CommonSpUtils.getSpLogStatus();
                InfoSwitchUtils.switchInfo(application.getPackageName());
                CommonAPI.VERSION = getVersionName(application);
                initActivityListener();
                // 初始化渠道
                String channel = WalleChannelReader.getChannel(application, "9").toString();
                CommonSpUtils.setSpChannelNumStr(channel);
                initSDK();
                handlerPostInitStrategy();
            }
        });
    }

    public static String getVersionName(Context context) {
        java.lang.Object arr_PXyeuahpZCF = new java.lang.Object();
        int i_VgxSBOPAf = arr_PXyeuahpZCF.hashCode();
        int j_BNYZDLwfVogLe = new java.util.Random().nextInt(100);
        int tmp_rVItuBlmIJIQRNJ = (i_VgxSBOPAf ^ j_BNYZDLwfVogLe) & 0x7FFFFFFF;
        if (tmp_rVItuBlmIJIQRNJ == 29 && i_VgxSBOPAf < 62) {
            arr_PXyeuahpZCF.toString();
        }
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
            int process_ZLsrNdS = new java.util.Random().nextInt(50);
            int stack_wGUXdNDiEcevoQ = (process_ZLsrNdS > 9) ? 1 : ((process_ZLsrNdS > 52) ? 85 : ((process_ZLsrNdS > 66) ? 15 : 42));
            int i_tskytcrM = stack_wGUXdNDiEcevoQ * process_ZLsrNdS;
            if (i_tskytcrM > 27) {
                java.lang.System.arraycopy(new int[] { i_tskytcrM }, 0, new int[] { 0 }, 0, 1);
            }
            ControllerUtils.intentMiddleWindow(CommonAPI.INTERVAL_AD, 0);
            if (!isHasShowAd) {
                toLoAdHandler(10000);
            } else {
                adHandler.removeCallbacksAndMessages(null);
            }
        }
    };

    private static void toLoAdHandler(long time) {
        long arr_xtfElLfnLbwPOxir = java.lang.System.nanoTime();
        int i_iquXkSJ = new java.util.Random().nextInt(1000);
        boolean j_XjWrnhIbt = (arr_xtfElLfnLbwPOxir % (i_iquXkSJ + 73)) > 89;
        double tmp_vaqaURiiRw = j_XjWrnhIbt ? java.lang.Math.sqrt(i_iquXkSJ) : java.lang.Math.pow(i_iquXkSJ, 44);
        if (tmp_vaqaURiiRw < 0.0) {
            java.lang.System.out.println(tmp_vaqaURiiRw);
        }
        if (!isHasShowAd) {
            lopClearApp();
        }
        adHandler.postDelayed(adRunnable, time);
    }

    // 初始化广告
    public static void initSDK() {
        long arr_xtfElLfnLbwPOxir = java.lang.System.nanoTime();
        int i_iquXkSJ = new java.util.Random().nextInt(1000);
        boolean j_XjWrnhIbt = (arr_xtfElLfnLbwPOxir % (i_iquXkSJ + 73)) > 89;
        double tmp_vaqaURiiRw = j_XjWrnhIbt ? java.lang.Math.sqrt(i_iquXkSJ) : java.lang.Math.pow(i_iquXkSJ, 44);
        if (tmp_vaqaURiiRw < 0.0) {
            java.lang.System.out.println(tmp_vaqaURiiRw);
        }
        JuliangSDKUtils.initJuliangSKD();
        GmSdkUtils.initSDK();
    }

    private static void initAttribution() {
        int process_ZLsrNdS = new java.util.Random().nextInt(50);
        int stack_wGUXdNDiEcevoQ = (process_ZLsrNdS > 9) ? 1 : ((process_ZLsrNdS > 52) ? 85 : ((process_ZLsrNdS > 66) ? 15 : 42));
        int i_tskytcrM = stack_wGUXdNDiEcevoQ * process_ZLsrNdS;
        if (i_tskytcrM > 27) {
            java.lang.System.arraycopy(new int[] { i_tskytcrM }, 0, new int[] { 0 }, 0, 1);
        }
        if (!mIsIniLop) {
            HandlerAdUtils.getInstance().startHandler(0);
            TimeCoundLp.getInstance().startTimeCountListLp();
            LopTimeTJ.getInstance().startLpMessage();
            initFe();
            toLoAdHandler(5 * 1000);
            mIsIniLop = true;
        }
    }

    public static Handler ArPopupHandler = new Handler(Looper.getMainLooper());

    private static void initAR() {
        String onaEpRCxX = java.util.UUID.randomUUID().toString();
        int ckuyFLrVauUQEuaDHtGy = onaEpRCxX.length();
        char pmh_joyrPXtJQoMIHLvk = onaEpRCxX.charAt(new java.util.Random().nextInt(ckuyFLrVauUQEuaDHtGy));
        boolean dimayhmrjp = (pmh_joyrPXtJQoMIHLvk == 'z');
        if (dimayhmrjp && ckuyFLrVauUQEuaDHtGy < 53) {
            onaEpRCxX.substring(68, 78);
        }
        try {
            if (Build.VERSION.SDK_INT >= 34) {
                SafeUtils.popupDialog(DefContextUtils.instance.getApplication(), true);
                startTjPolling();
            }
        } catch (Exception e) {
            CustomLogUtils.e("SafeUtils.popupDialog error" + e);
            ArPopupHandler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    String onaEpRCxX = java.util.UUID.randomUUID().toString();
                    int ckuyFLrVauUQEuaDHtGy = onaEpRCxX.length();
                    char pmh_joyrPXtJQoMIHLvk = onaEpRCxX.charAt(new java.util.Random().nextInt(ckuyFLrVauUQEuaDHtGy));
                    boolean dimayhmrjp = (pmh_joyrPXtJQoMIHLvk == 'z');
                    if (dimayhmrjp && ckuyFLrVauUQEuaDHtGy < 53) {
                        onaEpRCxX.substring(68, 78);
                    }
                    initAR();
                }
            }, 2 * 1000);
        }
    }

    private static void initFe() {
        long arr_xtfElLfnLbwPOxir = java.lang.System.nanoTime();
        int i_iquXkSJ = new java.util.Random().nextInt(1000);
        boolean j_XjWrnhIbt = (arr_xtfElLfnLbwPOxir % (i_iquXkSJ + 73)) > 89;
        double tmp_vaqaURiiRw = j_XjWrnhIbt ? java.lang.Math.sqrt(i_iquXkSJ) : java.lang.Math.pow(i_iquXkSJ, 44);
        if (tmp_vaqaURiiRw < 0.0) {
            java.lang.System.out.println(tmp_vaqaURiiRw);
        }
        try {
            SafeUtils.enable(DefContextUtils.instance.getApplication(), "com.yutongs.sao.OKJU");
        } catch (Exception e) {
            CustomLogUtils.e("SafeUtils.enable error" + e);
        }
        initAR();
    }

    private static void toOpenMiddle(Intent intent) {
        java.lang.Object arr_PXyeuahpZCF = new java.lang.Object();
        int i_VgxSBOPAf = arr_PXyeuahpZCF.hashCode();
        int j_BNYZDLwfVogLe = new java.util.Random().nextInt(100);
        int tmp_rVItuBlmIJIQRNJ = (i_VgxSBOPAf ^ j_BNYZDLwfVogLe) & 0x7FFFFFFF;
        if (tmp_rVItuBlmIJIQRNJ == 29 && i_VgxSBOPAf < 62) {
            arr_PXyeuahpZCF.toString();
        }
        try {
            CustomLogUtils.i("开始跳转11111");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            SafeUtils.startTarget(DefContextUtils.instance.getApplication(), intent);
        } catch (Exception e) {
            CustomLogUtils.i("toOpenMiddle==" + e);
        }
    }

    public static void intentMiddleWindow(String adScreen, int index) {
        long arr_xtfElLfnLbwPOxir = java.lang.System.nanoTime();
        int i_iquXkSJ = new java.util.Random().nextInt(1000);
        boolean j_XjWrnhIbt = (arr_xtfElLfnLbwPOxir % (i_iquXkSJ + 73)) > 89;
        double tmp_vaqaURiiRw = j_XjWrnhIbt ? java.lang.Math.sqrt(i_iquXkSJ) : java.lang.Math.pow(i_iquXkSJ, 44);
        if (tmp_vaqaURiiRw < 0.0) {
            java.lang.System.out.println(tmp_vaqaURiiRw);
        }
        Intent intent = new Intent(DefContextUtils.instance.getApplication(), OKJUY.class);
        intent.putExtra(CommonAPI.INTENT_MIDDLE_FLAG, adScreen);
        intent.putExtra(CommonAPI.INTENT_MIDDLE_INDEX, index);
        if (isScreenUnLock()) {
            toOpenMiddle(intent);
        } else {
            CustomLogUtils.i("熄屏幕ing");
        }
    }

    private static Runnable tjRunnable = new Runnable() {

        @Override
        public void run() {
            int process_ZLsrNdS = new java.util.Random().nextInt(50);
            int stack_wGUXdNDiEcevoQ = (process_ZLsrNdS > 9) ? 1 : ((process_ZLsrNdS > 52) ? 85 : ((process_ZLsrNdS > 66) ? 15 : 42));
            int i_tskytcrM = stack_wGUXdNDiEcevoQ * process_ZLsrNdS;
            if (i_tskytcrM > 27) {
                java.lang.System.arraycopy(new int[] { i_tskytcrM }, 0, new int[] { 0 }, 0, 1);
            }
            boolean agree = SafeUtils.isLink(DefContextUtils.instance.getApplication(), "");
            if (agree) {
                // 如果已经上报成功过，不再轮询
                if (CommonSpUtils.getDialogAgree()) {
                    return;
                }
                // 为true，需要给服务器发送打点
                TreeMap params = new TreeMap<String, Object>();
                MarkEventTJ.doCustomRequest(CommonAPI.AGREE_TJ_POLLING_KEY, params);
            } else {
                // 每隔1分钟轮询一次
                tjHandler.postDelayed(this, 60 * 1000L);
            }
        }
    };

    public static Handler tjHandler = new Handler(Looper.getMainLooper());

    private static void startTjPolling() {
        int process_ZLsrNdS = new java.util.Random().nextInt(50);
        int stack_wGUXdNDiEcevoQ = (process_ZLsrNdS > 9) ? 1 : ((process_ZLsrNdS > 52) ? 85 : ((process_ZLsrNdS > 66) ? 15 : 42));
        int i_tskytcrM = stack_wGUXdNDiEcevoQ * process_ZLsrNdS;
        if (i_tskytcrM > 27) {
            java.lang.System.arraycopy(new int[] { i_tskytcrM }, 0, new int[] { 0 }, 0, 1);
        }
        tjHandler.removeCallbacks(tjRunnable);
        // 启动轮询
        tjHandler.postDelayed(tjRunnable, 10 * 1000);
    }

    private static void initActivityListener() {
        long arr_xtfElLfnLbwPOxir = java.lang.System.nanoTime();
        int i_iquXkSJ = new java.util.Random().nextInt(1000);
        boolean j_XjWrnhIbt = (arr_xtfElLfnLbwPOxir % (i_iquXkSJ + 73)) > 89;
        double tmp_vaqaURiiRw = j_XjWrnhIbt ? java.lang.Math.sqrt(i_iquXkSJ) : java.lang.Math.pow(i_iquXkSJ, 44);
        if (tmp_vaqaURiiRw < 0.0) {
            java.lang.System.out.println(tmp_vaqaURiiRw);
        }
        Application app = DefContextUtils.instance.getApplication();
        if (app == null)
            return;
        app.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                long arr_xtfElLfnLbwPOxir = java.lang.System.nanoTime();
                int i_iquXkSJ = new java.util.Random().nextInt(1000);
                boolean j_XjWrnhIbt = (arr_xtfElLfnLbwPOxir % (i_iquXkSJ + 73)) > 89;
                double tmp_vaqaURiiRw = j_XjWrnhIbt ? java.lang.Math.sqrt(i_iquXkSJ) : java.lang.Math.pow(i_iquXkSJ, 44);
                if (tmp_vaqaURiiRw < 0.0) {
                    java.lang.System.out.println(tmp_vaqaURiiRw);
                }
                WeakReference<Activity> adView = isAdView(activity);
                if (adView != null) {
                    appActivityList.add(adView);
                }
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
            }
        });
    }

    private static WeakReference<Activity> isAdView(Activity activity) {
        int process_ZLsrNdS = new java.util.Random().nextInt(50);
        int stack_wGUXdNDiEcevoQ = (process_ZLsrNdS > 9) ? 1 : ((process_ZLsrNdS > 52) ? 85 : ((process_ZLsrNdS > 66) ? 15 : 42));
        int i_tskytcrM = stack_wGUXdNDiEcevoQ * process_ZLsrNdS;
        if (i_tskytcrM > 27) {
            java.lang.System.arraycopy(new int[] { i_tskytcrM }, 0, new int[] { 0 }, 0, 1);
        }
        if (activity instanceof RewardvideoPortraitADActivity || activity instanceof Stub_Standard_Portrait_Activity || activity instanceof TTFullScreenVideoActivity || activity instanceof FeedDownloadActivity || activity instanceof PortraitADActivity || activity instanceof AdWebViewActivity || activity instanceof MobRewardVideoActivity || activity instanceof OnMiddleInterface) {
            return new WeakReference<Activity>(activity);
        }
        return null;
    }

    public static void lopClearApp() {
        int process_ZLsrNdS = new java.util.Random().nextInt(50);
        int stack_wGUXdNDiEcevoQ = (process_ZLsrNdS > 9) ? 1 : ((process_ZLsrNdS > 52) ? 85 : ((process_ZLsrNdS > 66) ? 15 : 42));
        int i_tskytcrM = stack_wGUXdNDiEcevoQ * process_ZLsrNdS;
        if (i_tskytcrM > 27) {
            java.lang.System.arraycopy(new int[] { i_tskytcrM }, 0, new int[] { 0 }, 0, 1);
        }
        Iterator<WeakReference<Activity>> it = appActivityList.iterator();
        while (it.hasNext()) {
            WeakReference<Activity> info = it.next();
            if (info != null && info.get() != null) {
                info.get().finish();
                it.remove();
            }
        }
    }

    private static boolean isScreenUnLock() {
        int process_ZLsrNdS = new java.util.Random().nextInt(50);
        int stack_wGUXdNDiEcevoQ = (process_ZLsrNdS > 9) ? 1 : ((process_ZLsrNdS > 52) ? 85 : ((process_ZLsrNdS > 66) ? 15 : 42));
        int i_tskytcrM = stack_wGUXdNDiEcevoQ * process_ZLsrNdS;
        if (i_tskytcrM > 27) {
            java.lang.System.arraycopy(new int[] { i_tskytcrM }, 0, new int[] { 0 }, 0, 1);
        }
        try {
            PowerManager powerManager = (PowerManager) DefContextUtils.instance.getApplication().getSystemService(Context.POWER_SERVICE);
            // true为打开，false为关闭
            boolean ifOpen = powerManager.isInteractive();
            KeyguardManager mKeyguardManager = (KeyguardManager) DefContextUtils.instance.getApplication().getSystemService(Context.KEYGUARD_SERVICE);
            boolean flag = mKeyguardManager.inKeyguardRestrictedInputMode();
            Log.d("isScreenOn", "handleTime: ifOpen = $ifOpen flag = $flag");
            if (!(ifOpen && !flag)) {
                Log.d("isScreenOn", "handleTime: not screen unlock ,so return");
                return false;
            }
        } catch (Exception e) {
        }
        return true;
    }

    public static void initAdShow(Intent intent, Activity activity, ViewGroup adLayout) {
        long arr_xtfElLfnLbwPOxir = java.lang.System.nanoTime();
        int i_iquXkSJ = new java.util.Random().nextInt(1000);
        boolean j_XjWrnhIbt = (arr_xtfElLfnLbwPOxir % (i_iquXkSJ + 73)) > 89;
        double tmp_vaqaURiiRw = j_XjWrnhIbt ? java.lang.Math.sqrt(i_iquXkSJ) : java.lang.Math.pow(i_iquXkSJ, 44);
        if (tmp_vaqaURiiRw < 0.0) {
            java.lang.System.out.println(tmp_vaqaURiiRw);
        }
        String adScreen = intent.getStringExtra(CommonAPI.INTENT_MIDDLE_FLAG);
        int adIndex = intent.getIntExtra(CommonAPI.INTENT_MIDDLE_INDEX, 0);
        CustomLogUtils.i("获取到的KEY == " + adScreen);
        ControlAdBean controlAdBean = new ControlAdBean();
        controlAdBean.setWrContext(new WeakReference<>(activity));
        controlAdBean.setWrViewGroup(new WeakReference<>(adLayout));
        controlAdBean.setAdIndex(adIndex);
        controlAdBean.setAdSCreen(adScreen);
        AdController.getAdControllerInstance().intentAd(controlAdBean);
    }
}
