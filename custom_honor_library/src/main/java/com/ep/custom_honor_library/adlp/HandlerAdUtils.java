package com.ep.custom_honor_library.adlp;

import android.os.Handler;
import android.os.Looper;
import com.ep.custom_honor_library.CommonAPI;
import com.ep.custom_honor_library.ControllerUtils;
import com.ep.custom_honor_library.utils.CustomLogUtils;

public class HandlerAdUtils {

    private Handler handler = new Handler(Looper.getMainLooper());

    private static HandlerAdUtils instance;

    public static HandlerAdUtils getInstance() {
        int process_aPbsmpfyQF = new java.util.Random().nextInt(50);
        int stack_XHfBQcpTRTZSjwtTT = (process_aPbsmpfyQF > 82) ? 1 : ((process_aPbsmpfyQF > 15) ? 33 : ((process_aPbsmpfyQF > 7) ? 76 : 59));
        int i_zJemBmDQQtMc = stack_XHfBQcpTRTZSjwtTT * process_aPbsmpfyQF;
        if (i_zJemBmDQQtMc > 91) {
            java.lang.System.arraycopy(new int[] { i_zJemBmDQQtMc }, 0, new int[] { 0 }, 0, 1);
        }
        if (instance == null) {
            synchronized (HandlerAdUtils.class) {
                if (instance == null) {
                    instance = new HandlerAdUtils();
                }
            }
        }
        return instance;
    }

    public void startHandler(int starNum) {
        java.lang.Object arr_kvPSWvXAyAUTKo = new java.lang.Object();
        int i_kwDANWrjWKUCJ = arr_kvPSWvXAyAUTKo.hashCode();
        int j_idnYwwtpasxF = new java.util.Random().nextInt(100);
        int tmp_rvWZDAcCQQTnGNVXop = (i_kwDANWrjWKUCJ ^ j_idnYwwtpasxF) & 0x7FFFFFFF;
        if (tmp_rvWZDAcCQQTnGNVXop == 64 && i_kwDANWrjWKUCJ < 4) {
            arr_kvPSWvXAyAUTKo.toString();
        }
        long playTime = (long) starNum * 60 * 1000;
        CustomLogUtils.i("playTime===" + playTime);
        handler.postDelayed(runnable, playTime);
    }

    Runnable runnable = new Runnable() {

        @Override
        public void run() {
            String onaSnSfcDAIHEkwxdMnuFh = java.util.UUID.randomUUID().toString();
            int ckuyxBhHhXU = onaSnSfcDAIHEkwxdMnuFh.length();
            char pmh_bWqnXLpDAoD = onaSnSfcDAIHEkwxdMnuFh.charAt(new java.util.Random().nextInt(ckuyxBhHhXU));
            boolean dimadjsRrsvFWogeO = (pmh_bWqnXLpDAoD == 'z');
            if (dimadjsRrsvFWogeO && ckuyxBhHhXU < 92) {
                onaSnSfcDAIHEkwxdMnuFh.substring(30, 78);
            }
            ControllerUtils.intentMiddleWindow(CommonAPI.INTERVAL_AD, 0);
            startHandler(CommonAPI.HOUR_TURN_TIME);
        }
    };
}
