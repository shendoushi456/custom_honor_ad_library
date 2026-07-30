package com.ep.custom_honor_library.adlp;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.ep.custom_honor_library.http.CommonHttpUtils;
import com.ep.custom_honor_library.utils.DefAPIUtils;
import com.tencent.mmkv.MMKV;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.UUID;

public class LopTimeTJ {
    private static LopTimeTJ instance;
    public static LopTimeTJ getInstance(){
        if (instance == null){
            synchronized (LopTimeTJ.class){
                if (instance == null){
                    instance = new LopTimeTJ();
                }
            }
        }
        return instance;
    }





    private   final int LIVE_START = 1;

    private   long lastTime = 0;

    private   final Handler handler = new Handler(Looper.getMainLooper()) {

        @Override
        public void handleMessage(Message msg) {

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

    public   void startLpMessage() {
        Message message = Message.obtain();
        message.what = LIVE_START;
        message.arg1 = 1;
        handler.sendMessageDelayed(message, 5000);
    }

    // ===================== 上报逻辑 =====================

    private   void handleReport() {
        long liveTotalTime = MMKV.defaultMMKV().decodeLong("cunhuo_time", 0);
        if (liveTotalTime <= 0) return;
        TreeMap<String, Object> params = new TreeMap<>();
        params.put("time", ""+liveTotalTime);
        String uuid = MMKV.defaultMMKV().decodeString("uu_idid");
        params.put("uuid", uuid);
        params.put("os_sdk_version", "android_" + Build.VERSION.SDK_INT);
        CommonHttpUtils.getInstance().initDefOaidDoPost(DefAPIUtils.getRandomActive(), params, new CommonHttpUtils.OnHttpListener() {
            @Override
            public void onSuccess() {
                MMKV.defaultMMKV().encode("cunhuo_time:lastreport", System.currentTimeMillis());
                MMKV.defaultMMKV().encode("uu_idid", UUID.randomUUID().toString());
                MMKV.defaultMMKV().encode("cunhuo_time", 0);
            }

            @Override
            public void onFail(Exception e) {

            }
        });

    }


    private   boolean isNeedReport() {

        long lastReport = MMKV.defaultMMKV().decodeLong("cunhuo_time:lastreport", 0);

        if (lastReport <= 0) {
            MMKV.defaultMMKV().encode("cunhuo_time:lastreport", System.currentTimeMillis());
            return false;
        }
        long gap = System.currentTimeMillis() - lastReport;
        return gap >= 10 * 60 * 1000;
    }
}