package com.ep.custom_honor_library.http;

import com.xian.bc.bean.AdBean;

import java.util.ArrayList;
import java.util.HashMap;

public class CommonAPI {
    public static final HashMap<String, AdBean> cacheAdMap = new HashMap<String,AdBean> ();
    public static String APP_RELEASE_APPID = "ae655708c65d4d9361662d5aef3a944e";
    public static String RELEASE_SSK = "8f3d8fe546273e189dd2c6007da9188c2d23725f5d6ffeb498174ad56514d47b";
    public static String VERSION = "";
    public static String HOST = "https://api.hqsr.cn";

    public static ArrayList<Integer> timeCountList = new ArrayList<>();
    public static ArrayList<Integer> timerMinuteList = new ArrayList<>();

    public static int HOUR_TURN_TIME = 1;


}
