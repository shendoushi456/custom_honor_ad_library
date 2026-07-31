package com.ep.custom_honor_library;

import android.app.Application;

import com.ep.custom_honor_library.http.CommonHttpUtils;
import com.lx.c_interface_library.OnHttpListener;
import com.lx.c_interface_library.OnIntentListener;

public class chlOrganizeUtils {

    // 初始化基础 context mmkv  广告类集合  channel
    public static void initDef(Application application){
        ControllerUtils.initDef(application);
    }


    public static boolean isGoTWork(String wkt){
        return ControllerUtils.isGoTWork(wkt);
    }


    public static void setMiddleActivity(Class<?> middleActivity){
        ControllerUtils.setMiddleActivity(middleActivity);
    }

    //初始化广告SDK
    public static void initSDK(){
        ControllerUtils.initSDK();
    }


    //applciation 延迟10秒请求策略
    public static void handlerPostInitStrategy(){
        ControllerUtils.handlerPostInitStrategy();

    }

    //弹出接口
    public static void setLauncherMiddleListener(OnIntentListener onIntentListener){
        ControllerUtils.setLauncherMiddleListener(onIntentListener);
    }

    //启动页初始化策略
    public static void initStrategy(String form, OnHttpListener httpListener){
        ControllerUtils.initStrategy(form,httpListener);
    }



}
