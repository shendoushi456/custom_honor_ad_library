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

        }

    }

}
