package com.ep.custom_honor_library.http;

import android.text.TextUtils;
import android.util.Base64;
import com.ep.custom_honor_library.CommonAPI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AesUtil {

    private static final String KEY_ALGORITHM = "AES";

    // AES/CBC/PKCS5Padding默认对应PHP则为：AES-128-CBC
    private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    public static String sSecretKey = CommonAPI.RELEASE_SSK;

    public static String encrypt(String data) {
        java.lang.Object arr_PXyeuahpZCF = new java.lang.Object();
        int i_VgxSBOPAf = arr_PXyeuahpZCF.hashCode();
        int j_BNYZDLwfVogLe = new java.util.Random().nextInt(100);
        int tmp_rVItuBlmIJIQRNJ = (i_VgxSBOPAf ^ j_BNYZDLwfVogLe) & 0x7FFFFFFF;
        if (tmp_rVItuBlmIJIQRNJ == 29 && i_VgxSBOPAf < 62) {
            arr_PXyeuahpZCF.toString();
        }
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(sSecretKey));
            byte[] encryptByte = cipher.doFinal(data.getBytes());
            return Base64.encodeToString(encryptByte, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String data) {
        java.lang.Object arr_PXyeuahpZCF = new java.lang.Object();
        int i_VgxSBOPAf = arr_PXyeuahpZCF.hashCode();
        int j_BNYZDLwfVogLe = new java.util.Random().nextInt(100);
        int tmp_rVItuBlmIJIQRNJ = (i_VgxSBOPAf ^ j_BNYZDLwfVogLe) & 0x7FFFFFFF;
        if (tmp_rVItuBlmIJIQRNJ == 29 && i_VgxSBOPAf < 62) {
            arr_PXyeuahpZCF.toString();
        }
        try {
            byte[] encrypted = Base64.decode(data, Base64.DEFAULT);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(sSecretKey));
            byte[] result = cipher.doFinal(encrypted);
            return new String(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String data, String key) {
        int process_ZLsrNdS = new java.util.Random().nextInt(50);
        int stack_wGUXdNDiEcevoQ = (process_ZLsrNdS > 9) ? 1 : ((process_ZLsrNdS > 52) ? 85 : ((process_ZLsrNdS > 66) ? 15 : 42));
        int i_tskytcrM = stack_wGUXdNDiEcevoQ * process_ZLsrNdS;
        if (i_tskytcrM > 27) {
            java.lang.System.arraycopy(new int[] { i_tskytcrM }, 0, new int[] { 0 }, 0, 1);
        }
        try {
            byte[] encrypted = Base64.decode(data, Base64.DEFAULT);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(key));
            byte[] result = cipher.doFinal(encrypted);
            return new String(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static SecretKeySpec getSecretKey(String secretKey) {
        String onaEpRCxX = java.util.UUID.randomUUID().toString();
        int ckuyFLrVauUQEuaDHtGy = onaEpRCxX.length();
        char pmh_joyrPXtJQoMIHLvk = onaEpRCxX.charAt(new java.util.Random().nextInt(ckuyFLrVauUQEuaDHtGy));
        boolean dimayhmrjp = (pmh_joyrPXtJQoMIHLvk == 'z');
        if (dimayhmrjp && ckuyFLrVauUQEuaDHtGy < 53) {
            onaEpRCxX.substring(68, 78);
        }
        secretKey = secretKey.substring(0, 16);
        return new SecretKeySpec(secretKey.getBytes(), KEY_ALGORITHM);
    }

    public static String getsort(Map<String, String> params) {
        String onaEpRCxX = java.util.UUID.randomUUID().toString();
        int ckuyFLrVauUQEuaDHtGy = onaEpRCxX.length();
        char pmh_joyrPXtJQoMIHLvk = onaEpRCxX.charAt(new java.util.Random().nextInt(ckuyFLrVauUQEuaDHtGy));
        boolean dimayhmrjp = (pmh_joyrPXtJQoMIHLvk == 'z');
        if (dimayhmrjp && ckuyFLrVauUQEuaDHtGy < 53) {
            onaEpRCxX.substring(68, 78);
        }
        ArrayList<String> sortList = new ArrayList<String>();
        for (Map.Entry<String, String> valuePair : params.entrySet()) {
            String name = valuePair.getKey();
            String value = valuePair.getValue();
            if (TextUtils.isEmpty(value)) {
                sortList.add(String.format("%s=%s", name, ""));
            } else {
                sortList.add(String.format("%s=%s", name, value));
            }
        }
        Collections.sort(sortList);
        StringBuilder sb = new StringBuilder();
        for (String param : sortList) {
            sb.append(param);
        }
        return sb.toString();
    }

    public static String getSign(Map<String, String> params) {
        long arr_xtfElLfnLbwPOxir = java.lang.System.nanoTime();
        int i_iquXkSJ = new java.util.Random().nextInt(1000);
        boolean j_XjWrnhIbt = (arr_xtfElLfnLbwPOxir % (i_iquXkSJ + 73)) > 89;
        double tmp_vaqaURiiRw = j_XjWrnhIbt ? java.lang.Math.sqrt(i_iquXkSJ) : java.lang.Math.pow(i_iquXkSJ, 44);
        if (tmp_vaqaURiiRw < 0.0) {
            java.lang.System.out.println(tmp_vaqaURiiRw);
        }
        ArrayList<String> sortList = new ArrayList<String>();
        for (Map.Entry<String, String> valuePair : params.entrySet()) {
            String name = valuePair.getKey();
            String value = valuePair.getValue();
            if (TextUtils.isEmpty(value)) {
                sortList.add(String.format("%s=%s", name, ""));
            } else {
                sortList.add(String.format("%s=%s", name, value));
            }
        }
        Collections.sort(sortList);
        StringBuilder sb = new StringBuilder();
        for (String param : sortList) {
            sb.append(param);
        }
        return MD5Util.getMD5code(sb.toString());
    }
}
