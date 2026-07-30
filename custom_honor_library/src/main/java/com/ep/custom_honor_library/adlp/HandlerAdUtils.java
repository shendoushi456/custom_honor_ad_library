package com.ep.custom_honor_library.adlp;

import android.os.Handler;
import android.os.Looper;

import com.ep.custom_honor_library.ControllerUtils;
import com.ep.custom_honor_library.utils.CommonAPI;
import com.ep.custom_honor_library.utils.CustomLogUtils;

public class HandlerAdUtils {

    private Handler handler = new Handler(Looper.getMainLooper());

    private static HandlerAdUtils instance;

    public static HandlerAdUtils getInstance(){
        if (instance == null){
            synchronized (HandlerAdUtils.class){
                if (instance == null){
                    instance = new HandlerAdUtils();
                }
            }
        }
        return instance;
    }



    public void startHandler(int starNum){
        long playTime = (long) starNum * 60 * 1000;

        CustomLogUtils.i("playTime==="+playTime);
        handler.postDelayed(runnable,playTime);
    }



    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            ControllerUtils.intentMiddleWindow(CommonAPI.INTERVAL_AD,0);
            startHandler(CommonAPI.HOUR_TURN_TIME);

        }
    };


}
