package com.ep.custom_honor_library;


public class InfoSwitchUtils {
    public static void switchInfo(String info){
        switch (info){
            //速扫智识
            case "com.whhqz.susaozhishi":
                CommonAPI.HOST = "https://api.wuzilan.top/";
                CommonAPI.APPID = "5832260";
                CommonAPI.APP_RELEASE_APPID = "6b2796f3df2cc4690f1cae8fa47506f3";
                CommonAPI.RELEASE_SSK = "243b4247f3d7589d295b36db5fad11eb1eb6dd1d3776db95ec85a24dfdb8afa1";
                CommonAPI.umID = "6b052faf863270068cdd999e";
                break;

            //晴川晓雾
            case "com.whhqz.qingchuanxiaowu":
                CommonAPI.HOST = "https://api.wuzilan.top/";
                CommonAPI.APPID = "5833516";
                CommonAPI.APP_RELEASE_APPID = "443be6b137b80aad1a29bf4e27c70ae4";
                CommonAPI.RELEASE_SSK = "df2c167cb7e9e9d0808ca84351615fd08ac4356ec1fa64116fa65f6e309f1098";
                CommonAPI.umID = "6a2161169a7f376488f012e0";
                break;
            // 雾野灵墨绘画 taku
            case "com.shnine.wuyelingmohuihua":
                CommonAPI.HOST = "https://api.fumanban.cn/";
                CommonAPI.APPID = "5921173";
                CommonAPI.TAKU_APPID = "a6af43b93c85cb";
                CommonAPI.TAKU_APPKEY = "a5fdb1fc70c498748541c52b04cb7b639";
                CommonAPI.APP_RELEASE_APPID = "e1d1592c59bbede84a649dcbff227e46";
                CommonAPI.RELEASE_SSK = "c94eea231b7ae907d32c443dd461230f5440ad90f654343c9f58ef2332c5c7ad";
                CommonAPI.umID = "6b06d0182b097e0d647ffcf8";
                break;

        }

    }

}
