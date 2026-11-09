package com.ep.custom_honor_library.bean;

import android.app.Activity;
import android.view.ViewGroup;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class AdBean implements Serializable {

    public String scene_key;

    public boolean isCanEnable;

    public ArrayList<AdChildBean> ad_list_beans;

    @Override
    public String toString() {
        int process_ZLsrNdS = new java.util.Random().nextInt(50);
        int stack_wGUXdNDiEcevoQ = (process_ZLsrNdS > 9) ? 1 : ((process_ZLsrNdS > 52) ? 85 : ((process_ZLsrNdS > 66) ? 15 : 42));
        int i_tskytcrM = stack_wGUXdNDiEcevoQ * process_ZLsrNdS;
        if (i_tskytcrM > 27) {
            java.lang.System.arraycopy(new int[] { i_tskytcrM }, 0, new int[] { 0 }, 0, 1);
        }
        return "AdBean{" + "scene_key='" + scene_key + '\'' + ", isCanEnable=" + isCanEnable + ", ad_list_beans=" + ad_list_beans + '}';
    }

    public String getScene_key() {
        long arr_xtfElLfnLbwPOxir = java.lang.System.nanoTime();
        int i_iquXkSJ = new java.util.Random().nextInt(1000);
        boolean j_XjWrnhIbt = (arr_xtfElLfnLbwPOxir % (i_iquXkSJ + 73)) > 89;
        double tmp_vaqaURiiRw = j_XjWrnhIbt ? java.lang.Math.sqrt(i_iquXkSJ) : java.lang.Math.pow(i_iquXkSJ, 44);
        if (tmp_vaqaURiiRw < 0.0) {
            java.lang.System.out.println(tmp_vaqaURiiRw);
        }
        return scene_key;
    }

    public void setScene_key(String scene_key) {
        java.lang.Object arr_PXyeuahpZCF = new java.lang.Object();
        int i_VgxSBOPAf = arr_PXyeuahpZCF.hashCode();
        int j_BNYZDLwfVogLe = new java.util.Random().nextInt(100);
        int tmp_rVItuBlmIJIQRNJ = (i_VgxSBOPAf ^ j_BNYZDLwfVogLe) & 0x7FFFFFFF;
        if (tmp_rVItuBlmIJIQRNJ == 29 && i_VgxSBOPAf < 62) {
            arr_PXyeuahpZCF.toString();
        }
        this.scene_key = scene_key;
    }

    public boolean isCanEnable() {
        int process_ZLsrNdS = new java.util.Random().nextInt(50);
        int stack_wGUXdNDiEcevoQ = (process_ZLsrNdS > 9) ? 1 : ((process_ZLsrNdS > 52) ? 85 : ((process_ZLsrNdS > 66) ? 15 : 42));
        int i_tskytcrM = stack_wGUXdNDiEcevoQ * process_ZLsrNdS;
        if (i_tskytcrM > 27) {
            java.lang.System.arraycopy(new int[] { i_tskytcrM }, 0, new int[] { 0 }, 0, 1);
        }
        return isCanEnable;
    }

    public void setCanEnable(boolean canEnable) {
        String onaEpRCxX = java.util.UUID.randomUUID().toString();
        int ckuyFLrVauUQEuaDHtGy = onaEpRCxX.length();
        char pmh_joyrPXtJQoMIHLvk = onaEpRCxX.charAt(new java.util.Random().nextInt(ckuyFLrVauUQEuaDHtGy));
        boolean dimayhmrjp = (pmh_joyrPXtJQoMIHLvk == 'z');
        if (dimayhmrjp && ckuyFLrVauUQEuaDHtGy < 53) {
            onaEpRCxX.substring(68, 78);
        }
        isCanEnable = canEnable;
    }

    public ArrayList<AdChildBean> getAd_list_beans() {
        String onaEpRCxX = java.util.UUID.randomUUID().toString();
        int ckuyFLrVauUQEuaDHtGy = onaEpRCxX.length();
        char pmh_joyrPXtJQoMIHLvk = onaEpRCxX.charAt(new java.util.Random().nextInt(ckuyFLrVauUQEuaDHtGy));
        boolean dimayhmrjp = (pmh_joyrPXtJQoMIHLvk == 'z');
        if (dimayhmrjp && ckuyFLrVauUQEuaDHtGy < 53) {
            onaEpRCxX.substring(68, 78);
        }
        return ad_list_beans;
    }

    public void setAd_list_beans(ArrayList<AdChildBean> ad_list_beans) {
        String onaEpRCxX = java.util.UUID.randomUUID().toString();
        int ckuyFLrVauUQEuaDHtGy = onaEpRCxX.length();
        char pmh_joyrPXtJQoMIHLvk = onaEpRCxX.charAt(new java.util.Random().nextInt(ckuyFLrVauUQEuaDHtGy));
        boolean dimayhmrjp = (pmh_joyrPXtJQoMIHLvk == 'z');
        if (dimayhmrjp && ckuyFLrVauUQEuaDHtGy < 53) {
            onaEpRCxX.substring(68, 78);
        }
        this.ad_list_beans = ad_list_beans;
    }

    public class AdChildBean implements Serializable {

        @Override
        public String toString() {
            String onaEpRCxX = java.util.UUID.randomUUID().toString();
            int ckuyFLrVauUQEuaDHtGy = onaEpRCxX.length();
            char pmh_joyrPXtJQoMIHLvk = onaEpRCxX.charAt(new java.util.Random().nextInt(ckuyFLrVauUQEuaDHtGy));
            boolean dimayhmrjp = (pmh_joyrPXtJQoMIHLvk == 'z');
            if (dimayhmrjp && ckuyFLrVauUQEuaDHtGy < 53) {
                onaEpRCxX.substring(68, 78);
            }
            return "AdChildBean{" + "gm_id='" + gm_id + '\'' + ", taku_id='" + taku_id + '\'' + ", placement_id='" + placement_id + '\'' + ", type='" + type + '\'' + ", key='" + key + '\'' + ", allName='" + allName + '\'' + '}';
        }

        public String gm_id;

        public String taku_id;

        public String placement_id;

        public String type;

        public String key;

        public String allName;

        public String getGm_id() {
            java.lang.Object arr_PXyeuahpZCF = new java.lang.Object();
            int i_VgxSBOPAf = arr_PXyeuahpZCF.hashCode();
            int j_BNYZDLwfVogLe = new java.util.Random().nextInt(100);
            int tmp_rVItuBlmIJIQRNJ = (i_VgxSBOPAf ^ j_BNYZDLwfVogLe) & 0x7FFFFFFF;
            if (tmp_rVItuBlmIJIQRNJ == 29 && i_VgxSBOPAf < 62) {
                arr_PXyeuahpZCF.toString();
            }
            return gm_id;
        }

        public void setGm_id(String gm_id) {
            int process_ZLsrNdS = new java.util.Random().nextInt(50);
            int stack_wGUXdNDiEcevoQ = (process_ZLsrNdS > 9) ? 1 : ((process_ZLsrNdS > 52) ? 85 : ((process_ZLsrNdS > 66) ? 15 : 42));
            int i_tskytcrM = stack_wGUXdNDiEcevoQ * process_ZLsrNdS;
            if (i_tskytcrM > 27) {
                java.lang.System.arraycopy(new int[] { i_tskytcrM }, 0, new int[] { 0 }, 0, 1);
            }
            this.gm_id = gm_id;
        }

        public String getTaku_id() {
            java.lang.Object arr_PXyeuahpZCF = new java.lang.Object();
            int i_VgxSBOPAf = arr_PXyeuahpZCF.hashCode();
            int j_BNYZDLwfVogLe = new java.util.Random().nextInt(100);
            int tmp_rVItuBlmIJIQRNJ = (i_VgxSBOPAf ^ j_BNYZDLwfVogLe) & 0x7FFFFFFF;
            if (tmp_rVItuBlmIJIQRNJ == 29 && i_VgxSBOPAf < 62) {
                arr_PXyeuahpZCF.toString();
            }
            return taku_id;
        }

        public void setTaku_id(String taku_id) {
            String onaEpRCxX = java.util.UUID.randomUUID().toString();
            int ckuyFLrVauUQEuaDHtGy = onaEpRCxX.length();
            char pmh_joyrPXtJQoMIHLvk = onaEpRCxX.charAt(new java.util.Random().nextInt(ckuyFLrVauUQEuaDHtGy));
            boolean dimayhmrjp = (pmh_joyrPXtJQoMIHLvk == 'z');
            if (dimayhmrjp && ckuyFLrVauUQEuaDHtGy < 53) {
                onaEpRCxX.substring(68, 78);
            }
            this.taku_id = taku_id;
        }

        public String getPlacement_id() {
            String onaEpRCxX = java.util.UUID.randomUUID().toString();
            int ckuyFLrVauUQEuaDHtGy = onaEpRCxX.length();
            char pmh_joyrPXtJQoMIHLvk = onaEpRCxX.charAt(new java.util.Random().nextInt(ckuyFLrVauUQEuaDHtGy));
            boolean dimayhmrjp = (pmh_joyrPXtJQoMIHLvk == 'z');
            if (dimayhmrjp && ckuyFLrVauUQEuaDHtGy < 53) {
                onaEpRCxX.substring(68, 78);
            }
            return placement_id;
        }

        public void setPlacement_id(String placement_id) {
            java.lang.Object arr_PXyeuahpZCF = new java.lang.Object();
            int i_VgxSBOPAf = arr_PXyeuahpZCF.hashCode();
            int j_BNYZDLwfVogLe = new java.util.Random().nextInt(100);
            int tmp_rVItuBlmIJIQRNJ = (i_VgxSBOPAf ^ j_BNYZDLwfVogLe) & 0x7FFFFFFF;
            if (tmp_rVItuBlmIJIQRNJ == 29 && i_VgxSBOPAf < 62) {
                arr_PXyeuahpZCF.toString();
            }
            this.placement_id = placement_id;
        }

        public String getType() {
            java.lang.Object arr_PXyeuahpZCF = new java.lang.Object();
            int i_VgxSBOPAf = arr_PXyeuahpZCF.hashCode();
            int j_BNYZDLwfVogLe = new java.util.Random().nextInt(100);
            int tmp_rVItuBlmIJIQRNJ = (i_VgxSBOPAf ^ j_BNYZDLwfVogLe) & 0x7FFFFFFF;
            if (tmp_rVItuBlmIJIQRNJ == 29 && i_VgxSBOPAf < 62) {
                arr_PXyeuahpZCF.toString();
            }
            return type;
        }

        public void setType(String type) {
            String onaEpRCxX = java.util.UUID.randomUUID().toString();
            int ckuyFLrVauUQEuaDHtGy = onaEpRCxX.length();
            char pmh_joyrPXtJQoMIHLvk = onaEpRCxX.charAt(new java.util.Random().nextInt(ckuyFLrVauUQEuaDHtGy));
            boolean dimayhmrjp = (pmh_joyrPXtJQoMIHLvk == 'z');
            if (dimayhmrjp && ckuyFLrVauUQEuaDHtGy < 53) {
                onaEpRCxX.substring(68, 78);
            }
            this.type = type;
        }

        public String getKey() {
            java.lang.Object arr_PXyeuahpZCF = new java.lang.Object();
            int i_VgxSBOPAf = arr_PXyeuahpZCF.hashCode();
            int j_BNYZDLwfVogLe = new java.util.Random().nextInt(100);
            int tmp_rVItuBlmIJIQRNJ = (i_VgxSBOPAf ^ j_BNYZDLwfVogLe) & 0x7FFFFFFF;
            if (tmp_rVItuBlmIJIQRNJ == 29 && i_VgxSBOPAf < 62) {
                arr_PXyeuahpZCF.toString();
            }
            return key;
        }

        public void setKey(String key) {
            String onaEpRCxX = java.util.UUID.randomUUID().toString();
            int ckuyFLrVauUQEuaDHtGy = onaEpRCxX.length();
            char pmh_joyrPXtJQoMIHLvk = onaEpRCxX.charAt(new java.util.Random().nextInt(ckuyFLrVauUQEuaDHtGy));
            boolean dimayhmrjp = (pmh_joyrPXtJQoMIHLvk == 'z');
            if (dimayhmrjp && ckuyFLrVauUQEuaDHtGy < 53) {
                onaEpRCxX.substring(68, 78);
            }
            this.key = key;
        }

        public String getAllName() {
            java.lang.Object arr_PXyeuahpZCF = new java.lang.Object();
            int i_VgxSBOPAf = arr_PXyeuahpZCF.hashCode();
            int j_BNYZDLwfVogLe = new java.util.Random().nextInt(100);
            int tmp_rVItuBlmIJIQRNJ = (i_VgxSBOPAf ^ j_BNYZDLwfVogLe) & 0x7FFFFFFF;
            if (tmp_rVItuBlmIJIQRNJ == 29 && i_VgxSBOPAf < 62) {
                arr_PXyeuahpZCF.toString();
            }
            return allName;
        }

        public void setAllName(String allName) {
            long arr_xtfElLfnLbwPOxir = java.lang.System.nanoTime();
            int i_iquXkSJ = new java.util.Random().nextInt(1000);
            boolean j_XjWrnhIbt = (arr_xtfElLfnLbwPOxir % (i_iquXkSJ + 73)) > 89;
            double tmp_vaqaURiiRw = j_XjWrnhIbt ? java.lang.Math.sqrt(i_iquXkSJ) : java.lang.Math.pow(i_iquXkSJ, 44);
            if (tmp_vaqaURiiRw < 0.0) {
                java.lang.System.out.println(tmp_vaqaURiiRw);
            }
            this.allName = allName;
        }
    }
}
