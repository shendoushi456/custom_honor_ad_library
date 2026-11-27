package com.ep.custom_honor_library;


public class InfoSwitchUtils {
    public static void switchInfo(String info){
        switch (info){
            //青阡时序天气
            case "com.shnineh.qingmoshixuweather":
                CommonAPI.HOST = "https://api.fumanban.cn/";
                CommonAPI.APPID = "5901030";
                CommonAPI.APP_RELEASE_APPID = "7232c2ee7d990591e2cdd2779465f3b8";
                CommonAPI.RELEASE_SSK = "dfee4b6acfe096cf6d78908472c9db7d8e15a14acfa97084190d1deb79937f36";
                CommonAPI.umID = "6b06c9e22b097e0d647ffb0a";
                break;

            //清朔文译翻译
            case "com.shnineh.qingshuowenyifanyi":
                CommonAPI.HOST = "https://api.fumanban.cn/";
                CommonAPI.APPID = "5900856";
                CommonAPI.APP_RELEASE_APPID = "47e7d138f7fa15bd9b43ed67dc9e8983";
                CommonAPI.RELEASE_SSK = "bcb347db62ad52b102c91e065ce1d1801ff725fd6ee6e3c882ba3787d3c68c4f";
                CommonAPI.umID = "6b06ca9a2b097e0d647ffb14";
                break;

            //澜纱幻画绘画
            case "com.shnineh.lanshahuanhuahuihua":
                CommonAPI.HOST = "https://api.fumanban.cn/";
                CommonAPI.APPID = "5900914";
                CommonAPI.APP_RELEASE_APPID = "72c3d91567593e2d9ac25bd83431d97f";
                CommonAPI.RELEASE_SSK = "b34b43c1687de92f147bbc9c66bca3c55976d9bb9d27aa04e7d3ed11e69619d9";
                CommonAPI.umID = "6b06cab22b097e0d647ffb15";
                break;

                //雾野灵墨绘画
            case "com.shnine.wuyelingmohuihua":
                CommonAPI.HOST = "https://api.fumanban.cn/";
                CommonAPI.APPID = "5921173";
                CommonAPI.APP_RELEASE_APPID = "e1d1592c59bbede84a649dcbff227e46";
                CommonAPI.RELEASE_SSK = "c94eea231b7ae907d32c443dd461230f5440ad90f654343c9f58ef2332c5c7ad";
                CommonAPI.umID = "6b06d0182b097e0d647ffcf8";
                break;

               //岚霄全域天气
            case "com.shnine.lanxiaoquanyuweather":
                CommonAPI.HOST = "https://api.fumanban.cn/";
                CommonAPI.APPID = "5921179";
                CommonAPI.SIGMOB_JL_APPID = "85727";
                CommonAPI.APP_RELEASE_APPID = "04119350de11987a9d54fac968bdf00d";
                CommonAPI.RELEASE_SSK = "52297c51a75a2ab023018e2e43361c3f4592b5c9e2c9650e2bb4a190a84724aa";
                CommonAPI.umID = "6b06d0582b097e0d647ffd30";
                break;

            //星隅幻彩相机
            case "com.shnine.xingyuhuancaixiangji":
                CommonAPI.HOST = "https://api.fumanban.cn/";
                CommonAPI.APPID = "5921188";
                CommonAPI.APP_RELEASE_APPID = "ad68bc13c1f9fb5fa64e9c70054e3f8c";
                CommonAPI.RELEASE_SSK = "25faba8c96551515bc35e4017bb080c0d0def2aba3e76bbc32d7776adb29bead";
                CommonAPI.umID = "6b06d0902b097e0d647ffd31";
                break;


            //天气随便查
            case "com.whmcy.tianqisuibiancha":
                CommonAPI.HOST = "https://api.xujinle1.top/";
                CommonAPI.APPID = "5927628";
                CommonAPI.APP_RELEASE_APPID = "083bf12366a5a806a12d4a90134b8391";
                CommonAPI.RELEASE_SSK = "be9a65edb7efbc1bdfb24bf1791beb2e6c795f09d75289bdea5e04c4e6ed2390";
                CommonAPI.umID = "6b06d133a0e89126de6a83b9";
                break;



            //扫描秒识别
            case "com.whmcy.saomiaomiaoshibie":
                CommonAPI.HOST = "https://api.xujinle1.top/";
                CommonAPI.APPID = "5927626";
                CommonAPI.APP_RELEASE_APPID = "459e9487ab130317d179a0169171bb69";
                CommonAPI.RELEASE_SSK = "abecf73a6e3dffbd238d60424a51704a08fec68227076db82ca81f93c5a08e39";
                CommonAPI.umID = "6b06d0902b097e0d647ffd31";
                break;


            //驾考驭行识
            case "com.whmcy.jiakaoyuxingshi":
                CommonAPI.HOST = "https://api.xujinle1.top/";
                CommonAPI.APPID = "5927623";
                CommonAPI.APP_RELEASE_APPID = "081504906ed3264c1751fb801ae21f83";
                CommonAPI.RELEASE_SSK = "4c84474ec0f876024537b5c8155a0fbe6b28d69957ccd05529c5f66faf5568f4";
                CommonAPI.umID = "6b06d1aaa0e89126de6a83e6";
                break;


            //景曜天气
            case "com.gljy.jingyaotianqi":
                CommonAPI.HOST = "https://api.hqsr.cn/";
                CommonAPI.APPID = "5937452";
                CommonAPI.APP_RELEASE_APPID = "c2ae27f0a6ede2ba95c0be7c7a134789";
                CommonAPI.RELEASE_SSK = "819045a266c948f7a817ecb511f3076e23da178e382fad5a6a4d8d850cf8ebc6";
                CommonAPI.umID = "6b06d203a0e89126de6a83f1";
                break;


            //云语霏扫描
            case "com.gljy.yunyufeisaomiao":
                CommonAPI.HOST = "https://api.hqsr.cn/";
                CommonAPI.APPID = "5938606";
                CommonAPI.APP_RELEASE_APPID = "eb584e1780aad8560036fd6bd068c1b4";
                CommonAPI.RELEASE_SSK = "c97c4693f57ebedc8d5616c019acc29e42f6b2fabc5b1318557238dfaaf5ce45";
                CommonAPI.umID = "6b06d284a0e89126de6a8448";
                break;


            //雾岚潮汐照相机
            case "com.gljy.wulanchaoxixiangji":
                CommonAPI.HOST = "https://api.hqsr.cn/";
                CommonAPI.APPID = "5938607";
                CommonAPI.APP_RELEASE_APPID = "a6316b84a94014a9adeb1f7238286ad2";
                CommonAPI.RELEASE_SSK = "900030a802b389a7e6c599bd86a919d67c0f1ca1de95c3ba0a4bcee733fa319e";
                CommonAPI.umID = "6b06d2b6a0e89126de6a8465";
                break;




        }

    }

}
