package com.ep.custom_honor_library.sdk;

import android.util.Log;

import com.bytedance.sdk.openadsdk.TTAdConfig;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.ep.custom_honor_library.CommonAPI;
import com.ep.custom_honor_library.utils.DefContextUtils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

public class GmSdkUtils {
    private static boolean sInit = false;
    public static void initSDK() {

        if (!sInit) {

            boolean initStatus = TTAdSdk.init(DefContextUtils.instance.getApplication(), buildConfig());

            Log.d("TTMediationSDK",
                    "initStatus>>" + initStatus +
                            " APPID>>" + CommonAPI.APPID);

            TTAdSdk.start(new TTAdSdk.Callback() {
                @Override
                public void success() {
                    Log.d("TTMediationSDK", "初始化融合SDK成功");
                    sInit = true;
                }

                @Override
                public void fail(int code, String msg) {
                    Log.d("TTMediationSDK", "初始化融合SDK失败");
                }
            });
        }

        initUmSDK();
    }


    private static void initUmSDK() {

        UMConfigure.setLogEnabled(false);

        UMConfigure.preInit(
                DefContextUtils.instance.getApplication(),
                CommonAPI.umID,
                CommonAPI.VERSION
        );

        // 友盟合规回调：本方法整体运行在用户同意隐私协议之后（由壳的
        // initAdSource 时序保证），故此处直接提交已授权状态。
        // 不调用会导致友盟 SDK 持续告警“检测到未调用隐私授权API”，
        // 且数据上报受限于 SDK 内部合规状态机。
        UMConfigure.submitPolicyGrantResult(
                DefContextUtils.instance.getApplication(),
                true
        );

        MobclickAgent.setPageCollectionMode(
                MobclickAgent.PageMode.AUTO
        );

        UMConfigure.init(
                DefContextUtils.instance.getApplication(),
                CommonAPI.umID,
                CommonAPI.VERSION,
                UMConfigure.DEVICE_TYPE_PHONE,
                null
        );
    }




    private static TTAdConfig buildConfig() {
        return new TTAdConfig.Builder()
                .appId(CommonAPI.APPID)
                .appName(CommonAPI.VERSION)
                .debug(CommonAPI.switchLog)
                .useMediation(true)
                .supportMultiProcess(false)
                .allowShowNotify(true)
                .build();
    }


}
