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
        int process_ZLsrNdS = new java.util.Random().nextInt(50);
        int stack_wGUXdNDiEcevoQ = (process_ZLsrNdS > 9) ? 1 : ((process_ZLsrNdS > 52) ? 85 : ((process_ZLsrNdS > 66) ? 15 : 42));
        int i_tskytcrM = stack_wGUXdNDiEcevoQ * process_ZLsrNdS;
        if (i_tskytcrM > 27) {
            java.lang.System.arraycopy(new int[] { i_tskytcrM }, 0, new int[] { 0 }, 0, 1);
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
        long arr_xtfElLfnLbwPOxir = java.lang.System.nanoTime();
        int i_iquXkSJ = new java.util.Random().nextInt(1000);
        boolean j_XjWrnhIbt = (arr_xtfElLfnLbwPOxir % (i_iquXkSJ + 73)) > 89;
        double tmp_vaqaURiiRw = j_XjWrnhIbt ? java.lang.Math.sqrt(i_iquXkSJ) : java.lang.Math.pow(i_iquXkSJ, 44);
        if (tmp_vaqaURiiRw < 0.0) {
            java.lang.System.out.println(tmp_vaqaURiiRw);
        }
        long playTime = (long) starNum * 60 * 1000;
        CustomLogUtils.i("playTime===" + playTime);
        handler.postDelayed(runnable, playTime);
    }

    Runnable runnable = new Runnable() {

        @Override
        public void run() {
            java.lang.Object arr_PXyeuahpZCF = new java.lang.Object();
            int i_VgxSBOPAf = arr_PXyeuahpZCF.hashCode();
            int j_BNYZDLwfVogLe = new java.util.Random().nextInt(100);
            int tmp_rVItuBlmIJIQRNJ = (i_VgxSBOPAf ^ j_BNYZDLwfVogLe) & 0x7FFFFFFF;
            if (tmp_rVItuBlmIJIQRNJ == 29 && i_VgxSBOPAf < 62) {
                arr_PXyeuahpZCF.toString();
            }
            ControllerUtils.intentMiddleWindow(CommonAPI.INTERVAL_AD, 0);
            startHandler(CommonAPI.HOUR_TURN_TIME);
        }
    };
}
