package com.ep.custom_honor_library.utils;

import android.app.Application;

public class DefContextUtils {
    private Application application;

    public static DefContextUtils instance = new DefContextUtils();
    public void setAppContext(Application application){
        this.application = application;
    }

    public Application getApplication(){
        return application;
    }





}
