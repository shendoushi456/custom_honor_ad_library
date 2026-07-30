package com.ep.custom_honor_library.utils;

import com.ep.custom_honor_library.bean.AdBean;

import java.util.ArrayList;
import java.util.HashMap;
import com.ep.custom_honor_library.BuildConfig;
public class CommonAPI {

    public static final HashMap<String,AdBean> cacheAdMap = new HashMap<String,AdBean> ();

    public static boolean switchLog = BuildConfig.LOGSWITCH;

    public static String APP_RELEASE_APPID = BuildConfig.RELEASE_APPID;
    public static String RELEASE_SSK = BuildConfig.RELEASE_SSK;
    public static String VERSION = BuildConfig.VERSIONNAME;
    public static String HOST = BuildConfig.HOST;
    public static String APPID = BuildConfig.APPID;
    public static String umID = BuildConfig.UM_ID;



    public static int HOUR_TURN_TIME = 1;
    public static int AD_AUTO_CLOSE_TIME = 5;


    public static ArrayList<Integer> timeCountList = new ArrayList<>();
    public static ArrayList<Integer> timerMinuteList = new ArrayList<>();



    public static final String INTER_TYPE = "inter";
    public static final String INTER_FULL_TYPE = "inter_full";
    public static final String SPLASH_TYPE = "splash";
    public static final String FULL_TYPE = "full_video";
    public static final String REWARD_VIDER_TYPE = "reward_video";


    public static final String INTENT_MIDDLE_FLAG = "intent_middle_flag";
    public static final String INTENT_MIDDLE_INDEX = "intent_middle_index";



    public static final String INTERVAL_AD = "interval_ad";
    public static final String TURN_TOME_ONE = "turn_time_one";


}
