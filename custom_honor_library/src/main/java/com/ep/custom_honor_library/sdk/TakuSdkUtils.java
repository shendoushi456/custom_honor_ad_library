package com.ep.custom_honor_library.sdk;

import static com.ep.custom_honor_library.sdk.GmSdkUtils.initUmSDK;

import android.content.Context;
import android.util.Log;

import com.anythink.core.api.ATAdConst;
import com.anythink.core.api.ATInitConfig;
import com.anythink.core.api.ATNetworkConfig;
import com.anythink.core.api.ATSDK;
import com.ep.custom_honor_library.CommonAPI;

import java.util.ArrayList;
import java.util.List;

public class TakuSdkUtils {
    private static boolean sInitialized = false;
    public static void initSdk(Context ctx) {
        if (sInitialized) {
            return;
        }
//        //在taku sdk初始化之前调用
//        ATSDK.setATUserDeviceInfo(atUserDeviceInfo);
        ATSDK.setNetworkLogDebug(CommonAPI.switchLog);
        if (CommonAPI.switchLog) {
            //  com.anythink.maplehaze.adapter.LogUtil.setLogEnable(true);
            ATSDK.integrationChecking(ctx);
        }
        ATSDK.setPersonalizedAdStatus(ATAdConst.PRIVACY.PERSIONALIZED_ALLOW_STATUS);
        ATNetworkConfig networkConfig =buildMaplehazeNetworkConfig(ctx);
        // 初始化SDK
        ATSDK.init(ctx, CommonAPI.TAKU_APPID, CommonAPI.TAKU_APPKEY,networkConfig);
        // v6.2.95+，针对国内SDK，调用start启动SDK。
        ATSDK.start();
        sInitialized = true;
        initUmSDK();
    }
    private static ATNetworkConfig buildMaplehazeNetworkConfig(Context context) {
        List<ATInitConfig> initConfigList = new ArrayList<>();
        return new ATNetworkConfig.Builder()
                .withInitConfigList(initConfigList)
                .build();
    }

}
