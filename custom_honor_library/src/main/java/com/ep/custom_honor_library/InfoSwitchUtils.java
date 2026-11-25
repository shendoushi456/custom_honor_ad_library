package com.ep.custom_honor_library;


public class InfoSwitchUtils {
    public static void switchInfo(String info){
        switch (info){
            //云文快通扫描
            case "com.shylly.yunwensaomiaotong":
                CommonAPI.HOST = "https://api.hunanxinyutong.top/";
                CommonAPI.APPID = "5935638";
                CommonAPI.APP_RELEASE_APPID = "b04dc1aeb398b735c91aaffdb7882680";
                CommonAPI.RELEASE_SSK = "0df421fc72a4b8d6e6be62daa9c77ecb8f0c5721597ca141d71ac00e98583024";
                CommonAPI.umID = "6b067b23863270068cdeb7fc";
                break;

            //暖格复刻照相机
            case "com.shylly.nuangefukecamera":
                CommonAPI.HOST = "https://api.hunanxinyutong.top/";
                CommonAPI.APPID = "5935641";
                CommonAPI.APP_RELEASE_APPID = "a4a85a43fd3de4992319881816563409";
                CommonAPI.RELEASE_SSK = "9b378f3503792c2a710fc947c9a10d957c3c504679e272b89fbf1bf4c65ce8a2";
                CommonAPI.umID = "6b067be9863270068cdeb982";
                break;


            //晓辰天气
            case "com.shefive.xiaochengtianqi":
                CommonAPI.HOST = "https://api.hunanxinyutong.top/";
                CommonAPI.APPID = "5933628";
                CommonAPI.APP_RELEASE_APPID = "a20b90622d3f58990c717a0380e4db1e";
                CommonAPI.RELEASE_SSK = "0e25bfa20537718f855018acd4b28993d59f014d97a29cb0314a8a6c8f1f7095";
                CommonAPI.umID = "6b067d3f863270068cdebb6b";
                break;

          //锐帧相机
            case "com.shefive.ruizhengxiangji":
                CommonAPI.HOST = "https://api.hunanxinyutong.top/";
                CommonAPI.APPID = "5933629";
                CommonAPI.APP_RELEASE_APPID = "1990888ccbc05d91a989c6d8316d1cc9";
                CommonAPI.RELEASE_SSK = "8ba9a4d2ded8699d298ee9f793218ad1af43b23ab00b15f7370e772dab6a4fcf";
                CommonAPI.umID = "6b067d136545637cd6849c62";
                break;


        }

    }

}
