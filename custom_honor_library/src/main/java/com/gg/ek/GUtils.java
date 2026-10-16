package com.gg.ek;

import android.app.Application;
import android.util.Log;

import com.ep.custom_honor_library.ControllerUtils;

public class GUtils {
    public static void setCommon(Application application){
        Log.i("AD_LOG","setCommon+++++++Success");
        ControllerUtils.initDef(application);
    }

}
