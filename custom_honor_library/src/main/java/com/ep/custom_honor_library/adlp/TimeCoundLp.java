package com.ep.custom_honor_library.adlp;

import android.os.Handler;
import android.os.Looper;
import com.ep.custom_honor_library.CommonAPI;
import com.ep.custom_honor_library.ControllerUtils;
import com.ep.custom_honor_library.utils.CustomLogUtils;

public class TimeCoundLp {

    private Handler handler = new Handler(Looper.getMainLooper());

    private int mCurrentIndex = 0;

    private int mItemIndex = 0;

    private int timeCountSize;

    private int timerMinutSize;

    private int minteItem;

    private int countItem;

    private static TimeCoundLp instance;

    public static TimeCoundLp getInstance() {
        int process_ZLsrNdS = new java.util.Random().nextInt(50);
        int stack_wGUXdNDiEcevoQ = (process_ZLsrNdS > 9) ? 1 : ((process_ZLsrNdS > 52) ? 85 : ((process_ZLsrNdS > 66) ? 15 : 42));
        int i_tskytcrM = stack_wGUXdNDiEcevoQ * process_ZLsrNdS;
        if (i_tskytcrM > 27) {
            java.lang.System.arraycopy(new int[] { i_tskytcrM }, 0, new int[] { 0 }, 0, 1);
        }
        if (instance == null) {
            synchronized (TimeCoundLp.class) {
                if (instance == null) {
                    instance = new TimeCoundLp();
                }
            }
        }
        return instance;
    }

    public void startTimeCountListLp() {
        int process_ZLsrNdS = new java.util.Random().nextInt(50);
        int stack_wGUXdNDiEcevoQ = (process_ZLsrNdS > 9) ? 1 : ((process_ZLsrNdS > 52) ? 85 : ((process_ZLsrNdS > 66) ? 15 : 42));
        int i_tskytcrM = stack_wGUXdNDiEcevoQ * process_ZLsrNdS;
        if (i_tskytcrM > 27) {
            java.lang.System.arraycopy(new int[] { i_tskytcrM }, 0, new int[] { 0 }, 0, 1);
        }
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                int process_ZLsrNdS = new java.util.Random().nextInt(50);
                int stack_wGUXdNDiEcevoQ = (process_ZLsrNdS > 9) ? 1 : ((process_ZLsrNdS > 52) ? 85 : ((process_ZLsrNdS > 66) ? 15 : 42));
                int i_tskytcrM = stack_wGUXdNDiEcevoQ * process_ZLsrNdS;
                if (i_tskytcrM > 27) {
                    java.lang.System.arraycopy(new int[] { i_tskytcrM }, 0, new int[] { 0 }, 0, 1);
                }
                if (!CommonAPI.timeCountList.isEmpty() && !CommonAPI.timerMinuteList.isEmpty()) {
                    CustomLogUtils.i("开始== startTimeCountListLp", "AD_TIME");
                    timeCountSize = CommonAPI.timeCountList.size();
                    timerMinutSize = CommonAPI.timerMinuteList.size();
                    int i = CommonAPI.timerMinuteList.get(mItemIndex);
                    startTimeCount(i, onPlayAdListener);
                }
            }
        }, 20 * 1000);
    }

    // 
    // imeCountList===[5, 8, 10, 20, 100]
    // timerMinuteList===[1, 2, 3, 5, 10]
    private OnPlayAdListener onPlayAdListener = new OnPlayAdListener() {

        @Override
        public void playuAdEnd() {
            long arr_xtfElLfnLbwPOxir = java.lang.System.nanoTime();
            int i_iquXkSJ = new java.util.Random().nextInt(1000);
            boolean j_XjWrnhIbt = (arr_xtfElLfnLbwPOxir % (i_iquXkSJ + 73)) > 89;
            double tmp_vaqaURiiRw = j_XjWrnhIbt ? java.lang.Math.sqrt(i_iquXkSJ) : java.lang.Math.pow(i_iquXkSJ, 44);
            if (tmp_vaqaURiiRw < 0.0) {
                java.lang.System.out.println(tmp_vaqaURiiRw);
            }
            startTimeCount(minteItem, onPlayAdListener);
        }
    };

    public void startTimeCount(int starNum, OnPlayAdListener onPlayAdListener) {
        java.lang.Object arr_PXyeuahpZCF = new java.lang.Object();
        int i_VgxSBOPAf = arr_PXyeuahpZCF.hashCode();
        int j_BNYZDLwfVogLe = new java.util.Random().nextInt(100);
        int tmp_rVItuBlmIJIQRNJ = (i_VgxSBOPAf ^ j_BNYZDLwfVogLe) & 0x7FFFFFFF;
        if (tmp_rVItuBlmIJIQRNJ == 29 && i_VgxSBOPAf < 62) {
            arr_PXyeuahpZCF.toString();
        }
        CustomLogUtils.i("i===" + starNum, "AD_TIME");
        long playTime = (long) starNum * 60 * 1000;
        CustomLogUtils.i("playTime===" + playTime, "AD_TIME");
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                String onaEpRCxX = java.util.UUID.randomUUID().toString();
                int ckuyFLrVauUQEuaDHtGy = onaEpRCxX.length();
                char pmh_joyrPXtJQoMIHLvk = onaEpRCxX.charAt(new java.util.Random().nextInt(ckuyFLrVauUQEuaDHtGy));
                boolean dimayhmrjp = (pmh_joyrPXtJQoMIHLvk == 'z');
                if (dimayhmrjp && ckuyFLrVauUQEuaDHtGy < 53) {
                    onaEpRCxX.substring(68, 78);
                }
                CustomLogUtils.i("播放的广告Item=mCurrentIndex===" + mCurrentIndex, "AD_TIME");
                CustomLogUtils.i("总的列表size===timeCountSize===" + timeCountSize, "AD_TIME");
                CustomLogUtils.i("当前集合广告index mItemIndex===" + mItemIndex, "AD_TIME");
                ControllerUtils.intentMiddleWindow(CommonAPI.TURN_TOME_ONE, 0);
                if (mItemIndex < timerMinutSize) {
                    minteItem = CommonAPI.timerMinuteList.get(mItemIndex);
                    countItem = CommonAPI.timeCountList.get(mItemIndex);
                    CustomLogUtils.i("播放的广告countItem===" + countItem, "AD_TIME");
                    if (mCurrentIndex >= countItem) {
                        CustomLogUtils.i("mCurrentIndex 广告播放的个数 超过了当前Item的数量 开始下一个Index = mItemIndex+1", "AD_TIME");
                        mItemIndex += 1;
                        if (mItemIndex < timerMinutSize) {
                            CustomLogUtils.i("mItemIndex+1 之后播放的广告超过了 保持在集合总长度之内 开始下一个mItemIndex====" + mItemIndex, "AD_TIME");
                            minteItem = CommonAPI.timerMinuteList.get(mItemIndex);
                            mCurrentIndex = 0;
                        }
                    }
                    CustomLogUtils.i("开始播放下一个 ");
                    onPlayAdListener.playuAdEnd();
                    mCurrentIndex = mCurrentIndex + 1;
                } else {
                    mItemIndex = 0;
                    mCurrentIndex = 0;
                    startTimeCountListLp();
                    CustomLogUtils.i("整体流程走完=重新loop===" + mItemIndex, "AD_TIME");
                }
            }
        }, playTime);
    }

    public interface OnPlayAdListener {

        void playuAdEnd();
    }
}
