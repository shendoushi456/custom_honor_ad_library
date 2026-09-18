package com.ep.custom_honor_library.bean;

import java.io.Serializable;

public class SerlisChildArrBean implements Serializable {
    @Override
    public String toString() {
        return "AdChildBean{" +
                "gm_id='" + ggGM_id + '\'' +
                ", type='" + ggGMType + '\'' +
                ", key='" + ggKeyLin + '\'' +
                ", allName='" + allName + '\'' +
                '}';
    }

    public String ggGM_id;
    public String ggGMType;
    public String ggKeyLin;
    public String allName;

    public String getGgGM_id() {
        return ggGM_id;
    }

    public void setGgGM_id(String gm_id) {
        this.ggGM_id = gm_id;
    }



    public String getGgGMType() {
        return ggGMType;
    }

    public void setGgGMType(String type) {
        this.ggGMType = type;
    }

    public String getGgKeyLin() {
        return ggKeyLin;
    }

    public void setGgKeyLin(String key) {
        this.ggKeyLin = key;
    }

    public String getAllName() {
        return allName;
    }

    public void setAllName(String allName) {
        this.allName = allName;
    }
}