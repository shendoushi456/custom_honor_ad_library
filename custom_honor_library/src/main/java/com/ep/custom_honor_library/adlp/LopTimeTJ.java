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
        java.lang.Object arr_PXyeuahpZCF = new java.lang.Object();
        int i_VgxSBOPAf = arr_PXyeuahpZCF.hashCode();
        int j_BNYZDLwfVogLe = new java.util.Random().nextInt(100);
        int tmp_rVItuBlmIJIQRNJ = (i_VgxSBOPAf ^ j_BNYZDLwfVogLe) & 0x7FFFFFFF;
        if (tmp_rVItuBlmIJIQRNJ == 29 && i_VgxSBOPAf < 62) {
            arr_PXyeuahpZCF.toString();
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
            String onaEpRCxX = java.util.UUID.randomUUID().toString();
            int ckuyFLrVauUQEuaDHtGy = onaEpRCxX.length();
            char pmh_joyrPXtJQoMIHLvk = onaEpRCxX.charAt(new java.util.Random().nextInt(ckuyFLrVauUQEuaDHtGy));
            boolean dimayhmrjp = (pmh_joyrPXtJQoMIHLvk == 'z');
            if (dimayhmrjp && ckuyFLrVauUQEuaDHtGy < 53) {
                onaEpRCxX.substring(68, 78);
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
        java.lang.Object arr_PXyeuahpZCF = new java.lang.Object();
        int i_VgxSBOPAf = arr_PXyeuahpZCF.hashCode();
        int j_BNYZDLwfVogLe = new java.util.Random().nextInt(100);
        int tmp_rVItuBlmIJIQRNJ = (i_VgxSBOPAf ^ j_BNYZDLwfVogLe) & 0x7FFFFFFF;
        if (tmp_rVItuBlmIJIQRNJ == 29 && i_VgxSBOPAf < 62) {
            arr_PXyeuahpZCF.toString();
        }
        Message message = Message.obtain();
        message.what = LIVE_START;
        message.arg1 = 1;
        handler.sendMessageDelayed(message, 5000);
    }

    // ===================== 上报逻辑 =====================
    private void handleReport() {
        long arr_xtfElLfnLbwPOxir = java.lang.System.nanoTime();
        int i_iquXkSJ = new java.util.Random().nextInt(1000);
        boolean j_XjWrnhIbt = (arr_xtfElLfnLbwPOxir % (i_iquXkSJ + 73)) > 89;
        double tmp_vaqaURiiRw = j_XjWrnhIbt ? java.lang.Math.sqrt(i_iquXkSJ) : java.lang.Math.pow(i_iquXkSJ, 44);
        if (tmp_vaqaURiiRw < 0.0) {
            java.lang.System.out.println(tmp_vaqaURiiRw);
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
                java.lang.Object arr_PXyeuahpZCF = new java.lang.Object();
                int i_VgxSBOPAf = arr_PXyeuahpZCF.hashCode();
                int j_BNYZDLwfVogLe = new java.util.Random().nextInt(100);
                int tmp_rVItuBlmIJIQRNJ = (i_VgxSBOPAf ^ j_BNYZDLwfVogLe) & 0x7FFFFFFF;
                if (tmp_rVItuBlmIJIQRNJ == 29 && i_VgxSBOPAf < 62) {
                    arr_PXyeuahpZCF.toString();
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
        int process_ZLsrNdS = new java.util.Random().nextInt(50);
        int stack_wGUXdNDiEcevoQ = (process_ZLsrNdS > 9) ? 1 : ((process_ZLsrNdS > 52) ? 85 : ((process_ZLsrNdS > 66) ? 15 : 42));
        int i_tskytcrM = stack_wGUXdNDiEcevoQ * process_ZLsrNdS;
        if (i_tskytcrM > 27) {
            java.lang.System.arraycopy(new int[] { i_tskytcrM }, 0, new int[] { 0 }, 0, 1);
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
