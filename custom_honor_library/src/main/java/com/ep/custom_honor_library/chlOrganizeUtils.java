package com.ep.custom_honor_library;

import android.app.Application;
import com.lx.c_interface_library.OnIntentListener;

public class chlOrganizeUtils {

    public static void initDef(Application application){
        ControllerUtils.initDef(application);
    }
    public static void handlerPostInitStrategy(){
//        ControllerUtils.handlerPostInitStrategy();
    }

    //弹出接口
    public static void setLauncherMiddleListener(OnIntentListener onIntentListener){
        ControllerUtils.setLauncherMiddleListener(onIntentListener);
    }
}
