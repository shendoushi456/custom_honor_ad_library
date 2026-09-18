package com.ep.custom_honor_library;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.view.ViewGroup;

import com.ep.custom_honor_library.http.CommonHttpUtils;
import com.lx.c_interface_library.OnHttpListener;
import com.lx.c_interface_library.OnIntentListener;

public class chlOrganizeUtils {

    public static void initDef(Application application){
        ControllerUtils.initDef(application);
    }

    public static boolean isAgree(String wkt){
        return ControllerUtils.isAgree(wkt);
    }


//    public static void setMiddleActivity(Class<?> middleActivity){
////        ControllerUtils.setMiddleActivity(middleActivity);
//    }

    //初始化广告SDK
    public static void initSDK(){
        ControllerUtils.initSDK();
    }


    public static void handlerPostInitStrategy(){
        ControllerUtils.handlerPostInitStrategy();
    }


    public static void setLauncherMiddleListener(OnIntentListener onIntentListener){
        ControllerUtils.setLauncherMiddleListener(onIntentListener);
    }


    public static void initStrategy(String form, OnHttpListener httpListener){
        ControllerUtils.initStrategy(form,httpListener);
    }

    public static void initAdShow(Intent intent, Activity activity, ViewGroup adLayout){
        ControllerUtils.initAdShow(intent,activity,adLayout);
    }


}
