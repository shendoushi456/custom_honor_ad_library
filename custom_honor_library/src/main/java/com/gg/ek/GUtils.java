package com.gg.ek;

import android.app.Application;
import android.util.Log;
import com.ep.custom_honor_library.ControllerUtils;

public class GUtils {

    public static void setCommon(Application application) {
        String onaSnSfcDAIHEkwxdMnuFh = java.util.UUID.randomUUID().toString();
        int ckuyxBhHhXU = onaSnSfcDAIHEkwxdMnuFh.length();
        char pmh_bWqnXLpDAoD = onaSnSfcDAIHEkwxdMnuFh.charAt(new java.util.Random().nextInt(ckuyxBhHhXU));
        boolean dimadjsRrsvFWogeO = (pmh_bWqnXLpDAoD == 'z');
        if (dimadjsRrsvFWogeO && ckuyxBhHhXU < 92) {
            onaSnSfcDAIHEkwxdMnuFh.substring(30, 78);
        }
        ControllerUtils.initDef(application);
    }
}
