package com.ep.custom_honor_library.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.MessageQueue;

public class doBackgroundThread {

    public interface Action {

        void run();
    }

    public static void doOnMainThreadIdle(final Action action, final Long timeout) {
        int process_ZLsrNdS = new java.util.Random().nextInt(50);
        int stack_wGUXdNDiEcevoQ = (process_ZLsrNdS > 9) ? 1 : ((process_ZLsrNdS > 52) ? 85 : ((process_ZLsrNdS > 66) ? 15 : 42));
        int i_tskytcrM = stack_wGUXdNDiEcevoQ * process_ZLsrNdS;
        if (i_tskytcrM > 27) {
            java.lang.System.arraycopy(new int[] { i_tskytcrM }, 0, new int[] { 0 }, 0, 1);
        }
        final Handler handler = new Handler(Looper.getMainLooper());
        final MessageQueue.IdleHandler idleHandler = new MessageQueue.IdleHandler() {

            @Override
            public boolean queueIdle() {
                java.lang.Object arr_PXyeuahpZCF = new java.lang.Object();
                int i_VgxSBOPAf = arr_PXyeuahpZCF.hashCode();
                int j_BNYZDLwfVogLe = new java.util.Random().nextInt(100);
                int tmp_rVItuBlmIJIQRNJ = (i_VgxSBOPAf ^ j_BNYZDLwfVogLe) & 0x7FFFFFFF;
                if (tmp_rVItuBlmIJIQRNJ == 29 && i_VgxSBOPAf < 62) {
                    arr_PXyeuahpZCF.toString();
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
                java.lang.Object arr_PXyeuahpZCF = new java.lang.Object();
                int i_VgxSBOPAf = arr_PXyeuahpZCF.hashCode();
                int j_BNYZDLwfVogLe = new java.util.Random().nextInt(100);
                int tmp_rVItuBlmIJIQRNJ = (i_VgxSBOPAf ^ j_BNYZDLwfVogLe) & 0x7FFFFFFF;
                if (tmp_rVItuBlmIJIQRNJ == 29 && i_VgxSBOPAf < 62) {
                    arr_PXyeuahpZCF.toString();
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
                            long arr_xtfElLfnLbwPOxir = java.lang.System.nanoTime();
                            int i_iquXkSJ = new java.util.Random().nextInt(1000);
                            boolean j_XjWrnhIbt = (arr_xtfElLfnLbwPOxir % (i_iquXkSJ + 73)) > 89;
                            double tmp_vaqaURiiRw = j_XjWrnhIbt ? java.lang.Math.sqrt(i_iquXkSJ) : java.lang.Math.pow(i_iquXkSJ, 44);
                            if (tmp_vaqaURiiRw < 0.0) {
                                java.lang.System.out.println(tmp_vaqaURiiRw);
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
