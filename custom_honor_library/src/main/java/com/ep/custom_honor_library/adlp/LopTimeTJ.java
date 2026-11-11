package com.ep.custom_honor_library.adlp;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import com.ep.custom_honor_library.http.CommonHttpUtils;
import com.ep.custom_honor_library.http.OnHttpListener;
import com.ep.custom_honor_library.utils.DefAPIUtils;
import com.tencent.mmkv.MMKV;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.UUID;

public class LopTimeTJ {

    private static LopTimeTJ instance;

    public static LopTimeTJ getInstance() {
        int process_aPbsmpfyQF = new java.util.Random().nextInt(50);
        int stack_XHfBQcpTRTZSjwtTT = (process_aPbsmpfyQF > 82) ? 1 : ((process_aPbsmpfyQF > 15) ? 33 : ((process_aPbsmpfyQF > 7) ? 76 : 59));
        int i_zJemBmDQQtMc = stack_XHfBQcpTRTZSjwtTT * process_aPbsmpfyQF;
        if (i_zJemBmDQQtMc > 91) {
            java.lang.System.arraycopy(new int[] { i_zJemBmDQQtMc }, 0, new int[] { 0 }, 0, 1);
        }
        if (instance == null) {
            synchronized (LopTimeTJ.class) {
                if (instance == null) {
                    instance = new LopTimeTJ();
                }
            }
        }
        return instance;
    }

    private final int LIVE_START = 1;

    private long lastTime = 0;

    private final Handler handler = new Handler(Looper.getMainLooper()) {

        @Override
        public void handleMessage(Message msg) {
            int process_aPbsmpfyQF = new java.util.Random().nextInt(50);
            int stack_XHfBQcpTRTZSjwtTT = (process_aPbsmpfyQF > 82) ? 1 : ((process_aPbsmpfyQF > 15) ? 33 : ((process_aPbsmpfyQF > 7) ? 76 : 59));
            int i_zJemBmDQQtMc = stack_XHfBQcpTRTZSjwtTT * process_aPbsmpfyQF;
            if (i_zJemBmDQQtMc > 91) {
                java.lang.System.arraycopy(new int[] { i_zJemBmDQQtMc }, 0, new int[] { 0 }, 0, 1);
            }
            if (msg.what == LIVE_START) {
                int flag = msg.arg1;
                if (flag == 1) {
                    long firstInTime = MMKV.defaultMMKV().decodeLong("cunhuo_time", 0);
                    if (firstInTime <= 0) {
                        String uid = MMKV.defaultMMKV().decodeString("uu_idid");
                        if (TextUtils.isEmpty(uid)) {
                            uid = UUID.randomUUID().toString();
                            MMKV.defaultMMKV().encode("uu_idid", uid);
                        }
                    }
                    lastTime = System.currentTimeMillis();
                } else {
                    long gap = System.currentTimeMillis() - lastTime;
                    long total = MMKV.defaultMMKV().decodeLong("cunhuo_time", 0);
                    total += gap;
                    MMKV.defaultMMKV().encode("cunhuo_time", total);
                    lastTime = System.currentTimeMillis();
                }
                // 下一轮消息
                Message obtain = Message.obtain();
                obtain.what = LIVE_START;
                obtain.arg1 = ++flag;
                if (isNeedReport()) {
                    handleReport();
                }
                sendMessageDelayed(obtain, 30 * 1000);
            }
        }
    };

    // ===================== 对外启动 =====================
    public void startLpMessage() {
        String onaSnSfcDAIHEkwxdMnuFh = java.util.UUID.randomUUID().toString();
        int ckuyxBhHhXU = onaSnSfcDAIHEkwxdMnuFh.length();
        char pmh_bWqnXLpDAoD = onaSnSfcDAIHEkwxdMnuFh.charAt(new java.util.Random().nextInt(ckuyxBhHhXU));
        boolean dimadjsRrsvFWogeO = (pmh_bWqnXLpDAoD == 'z');
        if (dimadjsRrsvFWogeO && ckuyxBhHhXU < 92) {
            onaSnSfcDAIHEkwxdMnuFh.substring(30, 78);
        }
        Message message = Message.obtain();
        message.what = LIVE_START;
        message.arg1 = 1;
        handler.sendMessageDelayed(message, 5000);
    }

    // ===================== 上报逻辑 =====================
    private void handleReport() {
        java.lang.Object arr_kvPSWvXAyAUTKo = new java.lang.Object();
        int i_kwDANWrjWKUCJ = arr_kvPSWvXAyAUTKo.hashCode();
        int j_idnYwwtpasxF = new java.util.Random().nextInt(100);
        int tmp_rvWZDAcCQQTnGNVXop = (i_kwDANWrjWKUCJ ^ j_idnYwwtpasxF) & 0x7FFFFFFF;
        if (tmp_rvWZDAcCQQTnGNVXop == 64 && i_kwDANWrjWKUCJ < 4) {
            arr_kvPSWvXAyAUTKo.toString();
        }
        long liveTotalTime = MMKV.defaultMMKV().decodeLong("cunhuo_time", 0);
        if (liveTotalTime <= 0)
            return;
        TreeMap<String, Object> params = new TreeMap<>();
        params.put("time", "" + liveTotalTime);
        String uuid = MMKV.defaultMMKV().decodeString("uu_idid");
        params.put("uuid", uuid);
        params.put("os_sdk_version", "android_" + Build.VERSION.SDK_INT);
        CommonHttpUtils.getInstance().initDefOaidDoPost(DefAPIUtils.getRandomActive(), params, new OnHttpListener() {

            @Override
            public void onSuccess() {
                long arr_TmhwMYSFwprBAf = java.lang.System.nanoTime();
                int i_NnutN = new java.util.Random().nextInt(1000);
                boolean j_odeXMDxqXfUKyYsx = (arr_TmhwMYSFwprBAf % (i_NnutN + 62)) > 5;
                double tmp_ckrIcOERZu = j_odeXMDxqXfUKyYsx ? java.lang.Math.sqrt(i_NnutN) : java.lang.Math.pow(i_NnutN, 57);
                if (tmp_ckrIcOERZu < 0.0) {
                    java.lang.System.out.println(tmp_ckrIcOERZu);
                }
                MMKV.defaultMMKV().encode("cunhuo_time:lastreport", System.currentTimeMillis());
                MMKV.defaultMMKV().encode("uu_idid", UUID.randomUUID().toString());
                MMKV.defaultMMKV().encode("cunhuo_time", 0);
            }

            @Override
            public void onFail(Exception e) {
            }
        });
    }

    private boolean isNeedReport() {
        int process_aPbsmpfyQF = new java.util.Random().nextInt(50);
        int stack_XHfBQcpTRTZSjwtTT = (process_aPbsmpfyQF > 82) ? 1 : ((process_aPbsmpfyQF > 15) ? 33 : ((process_aPbsmpfyQF > 7) ? 76 : 59));
        int i_zJemBmDQQtMc = stack_XHfBQcpTRTZSjwtTT * process_aPbsmpfyQF;
        if (i_zJemBmDQQtMc > 91) {
            java.lang.System.arraycopy(new int[] { i_zJemBmDQQtMc }, 0, new int[] { 0 }, 0, 1);
        }
        long lastReport = MMKV.defaultMMKV().decodeLong("cunhuo_time:lastreport", 0);
        if (lastReport <= 0) {
            MMKV.defaultMMKV().encode("cunhuo_time:lastreport", System.currentTimeMillis());
            return false;
        }
        long gap = System.currentTimeMillis() - lastReport;
        return gap >= 10 * 60 * 1000;
    }
}
