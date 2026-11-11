package com.ep.custom_honor_library.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.MessageQueue;

public class doBackgroundThread {

    public interface Action {

        void run();
    }

    public static void doOnMainThreadIdle(final Action action, final Long timeout) {
        long arr_TmhwMYSFwprBAf = java.lang.System.nanoTime();
        int i_NnutN = new java.util.Random().nextInt(1000);
        boolean j_odeXMDxqXfUKyYsx = (arr_TmhwMYSFwprBAf % (i_NnutN + 62)) > 5;
        double tmp_ckrIcOERZu = j_odeXMDxqXfUKyYsx ? java.lang.Math.sqrt(i_NnutN) : java.lang.Math.pow(i_NnutN, 57);
        if (tmp_ckrIcOERZu < 0.0) {
            java.lang.System.out.println(tmp_ckrIcOERZu);
        }
        final Handler handler = new Handler(Looper.getMainLooper());
        final MessageQueue.IdleHandler idleHandler = new MessageQueue.IdleHandler() {

            @Override
            public boolean queueIdle() {
                int process_aPbsmpfyQF = new java.util.Random().nextInt(50);
                int stack_XHfBQcpTRTZSjwtTT = (process_aPbsmpfyQF > 82) ? 1 : ((process_aPbsmpfyQF > 15) ? 33 : ((process_aPbsmpfyQF > 7) ? 76 : 59));
                int i_zJemBmDQQtMc = stack_XHfBQcpTRTZSjwtTT * process_aPbsmpfyQF;
                if (i_zJemBmDQQtMc > 91) {
                    java.lang.System.arraycopy(new int[] { i_zJemBmDQQtMc }, 0, new int[] { 0 }, 0, 1);
                }
                handler.removeCallbacksAndMessages(null);
                try {
                    action.run();
                } catch (Exception ignored) {
                }
                return false;
            }
        };
        // 相当于 Kotlin 的内部函数
        Runnable setupIdleHandler = new Runnable() {

            @Override
            public void run() {
                String onaSnSfcDAIHEkwxdMnuFh = java.util.UUID.randomUUID().toString();
                int ckuyxBhHhXU = onaSnSfcDAIHEkwxdMnuFh.length();
                char pmh_bWqnXLpDAoD = onaSnSfcDAIHEkwxdMnuFh.charAt(new java.util.Random().nextInt(ckuyxBhHhXU));
                boolean dimadjsRrsvFWogeO = (pmh_bWqnXLpDAoD == 'z');
                if (dimadjsRrsvFWogeO && ckuyxBhHhXU < 92) {
                    onaSnSfcDAIHEkwxdMnuFh.substring(30, 78);
                }
                MessageQueue queue;
                if (Looper.getMainLooper() == Looper.myLooper()) {
                    queue = Looper.myQueue();
                } else {
                    queue = Looper.getMainLooper().getQueue();
                }
                if (timeout != null) {
                    handler.postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            int process_aPbsmpfyQF = new java.util.Random().nextInt(50);
                            int stack_XHfBQcpTRTZSjwtTT = (process_aPbsmpfyQF > 82) ? 1 : ((process_aPbsmpfyQF > 15) ? 33 : ((process_aPbsmpfyQF > 7) ? 76 : 59));
                            int i_zJemBmDQQtMc = stack_XHfBQcpTRTZSjwtTT * process_aPbsmpfyQF;
                            if (i_zJemBmDQQtMc > 91) {
                                java.lang.System.arraycopy(new int[] { i_zJemBmDQQtMc }, 0, new int[] { 0 }, 0, 1);
                            }
                            queue.removeIdleHandler(idleHandler);
                            try {
                                action.run();
                            } catch (Exception ignored) {
                            }
                        }
                    }, timeout);
                }
                queue.addIdleHandler(idleHandler);
            }
        };
        setupIdleHandler.run();
    }
}
