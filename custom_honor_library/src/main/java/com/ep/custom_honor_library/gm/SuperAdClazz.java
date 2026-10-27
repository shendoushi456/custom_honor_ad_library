package com.ep.custom_honor_library.gm;

import android.text.TextUtils;
import android.util.Log;
import com.bytedance.sdk.openadsdk.mediation.manager.MediationAdEcpmInfo;
import com.ep.custom_honor_library.adlp.MarkEventTJ;
import com.ep.custom_honor_library.bean.AdBean;
import com.ep.custom_honor_library.bean.ControlAdBean;
import com.ep.custom_honor_library.bean.EventTJBean;
import com.windmill.sdk.models.AdInfo;

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



    //SigMob SDK
    public static EventTJBean createSigMobNATAdInfo(AdInfo adInfo){
        EventTJBean natAdInfo;
        if (adInfo != null) {
            // String getSdkName() 获取SDK名称
            //getCustomSdkName() 获取自定义ADN名称
            // String getSlotId()  获取代码位ID
            // String getEcpm()  获取价格 单位是分
            // String getRitType() 获取代码位类型
            // int getReqBiddingType() 获取bidding类型
            // String getErrorMsg() 获取错误信息
            // String getRequestId() 获取请求唯一标识 广告对应的request_id,目前只有穿山甲和mintegral才有，其余返回为空
            String ecpm = adInfo.geteCPM();
            Log.d("NAdBeanFactory","ecpm值>> "+ecpm);
            if(TextUtils.isEmpty(ecpm)){
                ecpm = "0";
            }
            String sdkName = adInfo.getNetworkName();
            if(TextUtils.isEmpty(sdkName)){
                sdkName = "sdkname";
            }
            String slotId = adInfo.getPlacementId();
            if(TextUtils.isEmpty(slotId)){
                slotId = "111111";
            }
            natAdInfo = new EventTJBean(sdkName, slotId, Double.parseDouble(ecpm)/100+"",adInfo.getAdType());
        } else {
            natAdInfo = new EventTJBean("","","",0);
        }
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
