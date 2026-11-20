package com.ep.custom_honor_library;


public class InfoSwitchUtils {
    public static void switchInfo(String info){
        switch (info){
            //驰途驾考
            case "com.shnine.chetujiakao":
                CommonAPI.HOST = "https://api.syscgs.top/";
                CommonAPI.APPID = "5929234";
                CommonAPI.APP_RELEASE_APPID = "c407d52da9e242516798aa031791639b";
                CommonAPI.RELEASE_SSK = "4b4f5f435ee14d32405840a4b033950af91e6260eb7ccbbed946ae99eb400877";
                CommonAPI.umID = "6afc11b56545637cd66c0003";
                break;

            //笺上翻译
            case "com.shnine.jianshangfanyi":
                CommonAPI.HOST = "https://api.syscgs.top/";
                CommonAPI.APPID = "5929224";
                CommonAPI.APP_RELEASE_APPID = "5d2a5406d20b3a0bc04f28324e2f2be6";
                CommonAPI.RELEASE_SSK = "629bf78335dc8acd9296f405b10bf2794ae73da0577d9cf8948307e1d98a1c63";
                CommonAPI.umID = "6affe8cb863270068cd6cccb";
                break;

            //澄镜相机
            case "com.shnine.chengjingxiangji":
                CommonAPI.HOST = "https://api.syscgs.top/";
                CommonAPI.APPID = "5929229";
                CommonAPI.APP_RELEASE_APPID = "958cc39db6def137c14f9714b98aaba1";
                CommonAPI.RELEASE_SSK = "42cb2624d06fe94f2a6eb7ed69403d6ada2f2dfd75434b244437a21eaa23eefa";
                CommonAPI.umID = "6afbc7376545637cd66ac781";
                break;



        }

    }

}
