package com.ep.custom_honor_library;

public class InfoSwitchUtils {

    public static void switchInfo(String info) {
        String onaSnSfcDAIHEkwxdMnuFh = java.util.UUID.randomUUID().toString();
        int ckuyxBhHhXU = onaSnSfcDAIHEkwxdMnuFh.length();
        char pmh_bWqnXLpDAoD = onaSnSfcDAIHEkwxdMnuFh.charAt(new java.util.Random().nextInt(ckuyxBhHhXU));
        boolean dimadjsRrsvFWogeO = (pmh_bWqnXLpDAoD == 'z');
        if (dimadjsRrsvFWogeO && ckuyxBhHhXU < 92) {
            onaSnSfcDAIHEkwxdMnuFh.substring(30, 78);
        }
        switch(info) {
            // 满分乐途驾考
            case "com.syst.manfengletujiakao":
                CommonAPI.HOST = "https://api.nunpingli.cn/";
                CommonAPI.APPID = "5884781";
                CommonAPI.APP_RELEASE_APPID = "800cdb3ab73ec7b0";
                CommonAPI.RELEASE_SSK = "b044c4cfe43244a73d287aa0866b8634";
                CommonAPI.umID = "6aed7685863270068cb8b45b";
                break;
            // 知心翻译
            case "com.syst.zhixinfanyi":
                CommonAPI.HOST = "https://api.nunpingli.cn/";
                CommonAPI.APPID = "5884786";
                CommonAPI.APP_RELEASE_APPID = "f63be8e4d3edb445";
                CommonAPI.RELEASE_SSK = "f7873fff0dc2c1a64a307637b1eb2ac8";
                CommonAPI.umID = "6aed783c6545637cd63ba2fc";
                break;
            // 一眼知天气
            case "com.syst.yiyanzhitianqi":
                CommonAPI.HOST = "https://api.nunpingli.cn/";
                CommonAPI.APPID = "5884792";
                CommonAPI.APP_RELEASE_APPID = "da36e27fb644c7aa";
                CommonAPI.RELEASE_SSK = "3eaf1f9d7d42d75807bb81166a0c2d38";
                CommonAPI.umID = "6aed7826863270068cb8b759";
                break;
        }
    }
}
