package com.ep.custom_honor_library.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class SerlisBean implements Serializable {

   public String ggKeyScene;
   public boolean isCanEnable;
   public ArrayList<SerlisChildArrBean> ad_list_beans;

    @Override
    public String toString() {
        return "AdBean{" +
                "scene_key='" + ggKeyScene + '\'' +
                ", isCanEnable=" + isCanEnable +
                ", ad_list_beans=" + ad_list_beans +
                '}';
    }

    public String getGgKeyScene() {
        return ggKeyScene;
    }

    public void setScene_key(String scene_key) {
        this.ggKeyScene = scene_key;
    }

    public boolean isCanEnable() {
        return isCanEnable;
    }

    public void setCanEnable(boolean canEnable) {
        isCanEnable = canEnable;
    }

    public ArrayList<SerlisChildArrBean> getAd_list_beans() {
        return ad_list_beans;
    }

    public void setAd_list_beans(ArrayList<SerlisChildArrBean> ad_list_beans) {
        this.ad_list_beans = ad_list_beans;
    }


}
