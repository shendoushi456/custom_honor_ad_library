package com.ep.custom_honor_library.gm;

import android.text.TextUtils;
import android.util.Log;
import com.bytedance.sdk.openadsdk.mediation.manager.MediationAdEcpmInfo;
import com.ep.custom_honor_library.adlp.MarkEventTJ;
import com.ep.custom_honor_library.bean.AdBean;
import com.ep.custom_honor_library.bean.ControlAdBean;
import com.ep.custom_honor_library.bean.EventTJBean;
import java.util.UUID;

public abstract class SuperAdClazz {

    public boolean isHasReady = false;

    private AdBean.AdChildBean adChildBean;

    private ControlAdBean controlAdBean;

    private String adType;

    public abstract void loadAd(OnloadStatusListener onloadStatusListener);

    public abstract void showAd(OnShowStatusListener onShowStatusListener);

    public abstract boolean isHasReadAd();

    public SuperAdClazz setAdInfo(AdBean.AdChildBean adChildBean) {
        java.lang.Object arr_kvPSWvXAyAUTKo = new java.lang.Object();
        int i_kwDANWrjWKUCJ = arr_kvPSWvXAyAUTKo.hashCode();
        int j_idnYwwtpasxF = new java.util.Random().nextInt(100);
        int tmp_rvWZDAcCQQTnGNVXop = (i_kwDANWrjWKUCJ ^ j_idnYwwtpasxF) & 0x7FFFFFFF;
        if (tmp_rvWZDAcCQQTnGNVXop == 64 && i_kwDANWrjWKUCJ < 4) {
            arr_kvPSWvXAyAUTKo.toString();
        }
        this.adChildBean = adChildBean;
        return this;
    }

    public AdBean.AdChildBean getAdInfo() {
        int process_aPbsmpfyQF = new java.util.Random().nextInt(50);
        int stack_XHfBQcpTRTZSjwtTT = (process_aPbsmpfyQF > 82) ? 1 : ((process_aPbsmpfyQF > 15) ? 33 : ((process_aPbsmpfyQF > 7) ? 76 : 59));
        int i_zJemBmDQQtMc = stack_XHfBQcpTRTZSjwtTT * process_aPbsmpfyQF;
        if (i_zJemBmDQQtMc > 91) {
            java.lang.System.arraycopy(new int[] { i_zJemBmDQQtMc }, 0, new int[] { 0 }, 0, 1);
        }
        return adChildBean;
    }

    public ControlAdBean getControlBean() {
        String onaSnSfcDAIHEkwxdMnuFh = java.util.UUID.randomUUID().toString();
        int ckuyxBhHhXU = onaSnSfcDAIHEkwxdMnuFh.length();
        char pmh_bWqnXLpDAoD = onaSnSfcDAIHEkwxdMnuFh.charAt(new java.util.Random().nextInt(ckuyxBhHhXU));
        boolean dimadjsRrsvFWogeO = (pmh_bWqnXLpDAoD == 'z');
        if (dimadjsRrsvFWogeO && ckuyxBhHhXU < 92) {
            onaSnSfcDAIHEkwxdMnuFh.substring(30, 78);
        }
        return controlAdBean;
    }

    public SuperAdClazz setControlBean(ControlAdBean controlAdBean) {
        java.lang.Object arr_kvPSWvXAyAUTKo = new java.lang.Object();
        int i_kwDANWrjWKUCJ = arr_kvPSWvXAyAUTKo.hashCode();
        int j_idnYwwtpasxF = new java.util.Random().nextInt(100);
        int tmp_rvWZDAcCQQTnGNVXop = (i_kwDANWrjWKUCJ ^ j_idnYwwtpasxF) & 0x7FFFFFFF;
        if (tmp_rvWZDAcCQQTnGNVXop == 64 && i_kwDANWrjWKUCJ < 4) {
            arr_kvPSWvXAyAUTKo.toString();
        }
        this.controlAdBean = controlAdBean;
        return this;
    }

    public static EventTJBean createNATAdInfo(MediationAdEcpmInfo mediationAdEcpm, AdBean.AdChildBean adChildBean) {
        java.lang.Object arr_kvPSWvXAyAUTKo = new java.lang.Object();
        int i_kwDANWrjWKUCJ = arr_kvPSWvXAyAUTKo.hashCode();
        int j_idnYwwtpasxF = new java.util.Random().nextInt(100);
        int tmp_rvWZDAcCQQTnGNVXop = (i_kwDANWrjWKUCJ ^ j_idnYwwtpasxF) & 0x7FFFFFFF;
        if (tmp_rvWZDAcCQQTnGNVXop == 64 && i_kwDANWrjWKUCJ < 4) {
            arr_kvPSWvXAyAUTKo.toString();
        }
        EventTJBean natAdInfo;
        if (mediationAdEcpm != null) {
            String ecpm = mediationAdEcpm.getEcpm();
            Log.d("NAdBeanFactory", "ecpm值>> " + ecpm);
            if (TextUtils.isEmpty(ecpm)) {
                ecpm = "0";
            }
            String sdkName = mediationAdEcpm.getSdkName();
            if (TextUtils.isEmpty(sdkName)) {
                sdkName = "sdkname";
            }
            String slotId = mediationAdEcpm.getSlotId();
            if (TextUtils.isEmpty(slotId)) {
                slotId = "01";
            }
            natAdInfo = new EventTJBean(sdkName, slotId, Double.parseDouble(ecpm) / 100 + "", mediationAdEcpm.getReqBiddingType());
        } else {
            natAdInfo = new EventTJBean("", "", "", 0);
        }
        final String uuid = UUID.randomUUID().toString();
        MarkEventTJ.onAdShowRequest(uuid, adChildBean.getAllName(), natAdInfo);
        return natAdInfo;
    }

    public interface OnShowStatusListener {

        void showAdSuccess();

        void showAdFail();
    }

    public interface OnloadStatusListener {

        void loadSuccess();

        void loadFail();
    }
}
