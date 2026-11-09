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
        long arr_xtfElLfnLbwPOxir = java.lang.System.nanoTime();
        int i_iquXkSJ = new java.util.Random().nextInt(1000);
        boolean j_XjWrnhIbt = (arr_xtfElLfnLbwPOxir % (i_iquXkSJ + 73)) > 89;
        double tmp_vaqaURiiRw = j_XjWrnhIbt ? java.lang.Math.sqrt(i_iquXkSJ) : java.lang.Math.pow(i_iquXkSJ, 44);
        if (tmp_vaqaURiiRw < 0.0) {
            java.lang.System.out.println(tmp_vaqaURiiRw);
        }
        this.adChildBean = adChildBean;
        return this;
    }

    public AdBean.AdChildBean getAdInfo() {
        String onaEpRCxX = java.util.UUID.randomUUID().toString();
        int ckuyFLrVauUQEuaDHtGy = onaEpRCxX.length();
        char pmh_joyrPXtJQoMIHLvk = onaEpRCxX.charAt(new java.util.Random().nextInt(ckuyFLrVauUQEuaDHtGy));
        boolean dimayhmrjp = (pmh_joyrPXtJQoMIHLvk == 'z');
        if (dimayhmrjp && ckuyFLrVauUQEuaDHtGy < 53) {
            onaEpRCxX.substring(68, 78);
        }
        return adChildBean;
    }

    public ControlAdBean getControlBean() {
        String onaEpRCxX = java.util.UUID.randomUUID().toString();
        int ckuyFLrVauUQEuaDHtGy = onaEpRCxX.length();
        char pmh_joyrPXtJQoMIHLvk = onaEpRCxX.charAt(new java.util.Random().nextInt(ckuyFLrVauUQEuaDHtGy));
        boolean dimayhmrjp = (pmh_joyrPXtJQoMIHLvk == 'z');
        if (dimayhmrjp && ckuyFLrVauUQEuaDHtGy < 53) {
            onaEpRCxX.substring(68, 78);
        }
        return controlAdBean;
    }

    public SuperAdClazz setControlBean(ControlAdBean controlAdBean) {
        java.lang.Object arr_PXyeuahpZCF = new java.lang.Object();
        int i_VgxSBOPAf = arr_PXyeuahpZCF.hashCode();
        int j_BNYZDLwfVogLe = new java.util.Random().nextInt(100);
        int tmp_rVItuBlmIJIQRNJ = (i_VgxSBOPAf ^ j_BNYZDLwfVogLe) & 0x7FFFFFFF;
        if (tmp_rVItuBlmIJIQRNJ == 29 && i_VgxSBOPAf < 62) {
            arr_PXyeuahpZCF.toString();
        }
        this.controlAdBean = controlAdBean;
        return this;
    }

    public static EventTJBean createNATAdInfo(MediationAdEcpmInfo mediationAdEcpm, AdBean.AdChildBean adChildBean) {
        String onaEpRCxX = java.util.UUID.randomUUID().toString();
        int ckuyFLrVauUQEuaDHtGy = onaEpRCxX.length();
        char pmh_joyrPXtJQoMIHLvk = onaEpRCxX.charAt(new java.util.Random().nextInt(ckuyFLrVauUQEuaDHtGy));
        boolean dimayhmrjp = (pmh_joyrPXtJQoMIHLvk == 'z');
        if (dimayhmrjp && ckuyFLrVauUQEuaDHtGy < 53) {
            onaEpRCxX.substring(68, 78);
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
