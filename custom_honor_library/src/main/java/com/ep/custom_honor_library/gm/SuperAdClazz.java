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

    public SuperAdClazz setAdInfo(AdBean.AdChildBean adChildBean){
      this.adChildBean = adChildBean;
        return this;
    }

    public AdBean.AdChildBean getAdInfo(){
        return adChildBean;
    }

    public ControlAdBean getControlBean(){
        return controlAdBean;
    }

    public SuperAdClazz setControlBean(ControlAdBean controlAdBean){
         this.controlAdBean =controlAdBean;
         return this;
    }



    public static EventTJBean createNATAdInfo(MediationAdEcpmInfo mediationAdEcpm,AdBean.AdChildBean adChildBean){
        EventTJBean natAdInfo;
        if (mediationAdEcpm != null) {
            String ecpm = mediationAdEcpm.getEcpm();
            Log.d("NAdBeanFactory","ecpm值>> "+ecpm);
            if(TextUtils.isEmpty(ecpm)){
                ecpm = "0";
            }
            String sdkName = mediationAdEcpm.getSdkName();
            if(TextUtils.isEmpty(sdkName)){
                sdkName = "sdkname";
            }
            String slotId = mediationAdEcpm.getSlotId();
            if(TextUtils.isEmpty(slotId)){
                slotId = "01";
            }
            natAdInfo = new EventTJBean(sdkName, slotId, Double.parseDouble(ecpm)/100+"",mediationAdEcpm.getReqBiddingType());
        } else {
            natAdInfo = new EventTJBean("","","",0);
        }

        final String uuid = UUID.randomUUID().toString();
        MarkEventTJ.onAdShowRequest(uuid,adChildBean.getAllName(),natAdInfo);
        return natAdInfo;
    }



    public interface OnShowStatusListener{
        void showAdSuccess();
        void showAdFail();
    }


    public interface OnloadStatusListener{
        void loadSuccess();
        void loadFail();
    }

}
