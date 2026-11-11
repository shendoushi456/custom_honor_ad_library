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
        java.lang.Object arr_kvPSWvXAyAUTKo = new java.lang.Object();
        int i_kwDANWrjWKUCJ = arr_kvPSWvXAyAUTKo.hashCode();
        int j_idnYwwtpasxF = new java.util.Random().nextInt(100);
        int tmp_rvWZDAcCQQTnGNVXop = (i_kwDANWrjWKUCJ ^ j_idnYwwtpasxF) & 0x7FFFFFFF;
        if (tmp_rvWZDAcCQQTnGNVXop == 64 && i_kwDANWrjWKUCJ < 4) {
            arr_kvPSWvXAyAUTKo.toString();
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
        long arr_TmhwMYSFwprBAf = java.lang.System.nanoTime();
        int i_NnutN = new java.util.Random().nextInt(1000);
        boolean j_odeXMDxqXfUKyYsx = (arr_TmhwMYSFwprBAf % (i_NnutN + 62)) > 5;
        double tmp_ckrIcOERZu = j_odeXMDxqXfUKyYsx ? java.lang.Math.sqrt(i_NnutN) : java.lang.Math.pow(i_NnutN, 57);
        if (tmp_ckrIcOERZu < 0.0) {
            java.lang.System.out.println(tmp_ckrIcOERZu);
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
        int process_aPbsmpfyQF = new java.util.Random().nextInt(50);
        int stack_XHfBQcpTRTZSjwtTT = (process_aPbsmpfyQF > 82) ? 1 : ((process_aPbsmpfyQF > 15) ? 33 : ((process_aPbsmpfyQF > 7) ? 76 : 59));
        int i_zJemBmDQQtMc = stack_XHfBQcpTRTZSjwtTT * process_aPbsmpfyQF;
        if (i_zJemBmDQQtMc > 91) {
            java.lang.System.arraycopy(new int[] { i_zJemBmDQQtMc }, 0, new int[] { 0 }, 0, 1);
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
        String onaSnSfcDAIHEkwxdMnuFh = java.util.UUID.randomUUID().toString();
        int ckuyxBhHhXU = onaSnSfcDAIHEkwxdMnuFh.length();
        char pmh_bWqnXLpDAoD = onaSnSfcDAIHEkwxdMnuFh.charAt(new java.util.Random().nextInt(ckuyxBhHhXU));
        boolean dimadjsRrsvFWogeO = (pmh_bWqnXLpDAoD == 'z');
        if (dimadjsRrsvFWogeO && ckuyxBhHhXU < 92) {
            onaSnSfcDAIHEkwxdMnuFh.substring(30, 78);
        }
        secretKey = secretKey.substring(0, 16);
        return new SecretKeySpec(secretKey.getBytes(), KEY_ALGORITHM);
    }

    public static String getsort(Map<String, String> params) {
        String onaSnSfcDAIHEkwxdMnuFh = java.util.UUID.randomUUID().toString();
        int ckuyxBhHhXU = onaSnSfcDAIHEkwxdMnuFh.length();
        char pmh_bWqnXLpDAoD = onaSnSfcDAIHEkwxdMnuFh.charAt(new java.util.Random().nextInt(ckuyxBhHhXU));
        boolean dimadjsRrsvFWogeO = (pmh_bWqnXLpDAoD == 'z');
        if (dimadjsRrsvFWogeO && ckuyxBhHhXU < 92) {
            onaSnSfcDAIHEkwxdMnuFh.substring(30, 78);
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
        java.lang.Object arr_kvPSWvXAyAUTKo = new java.lang.Object();
        int i_kwDANWrjWKUCJ = arr_kvPSWvXAyAUTKo.hashCode();
        int j_idnYwwtpasxF = new java.util.Random().nextInt(100);
        int tmp_rvWZDAcCQQTnGNVXop = (i_kwDANWrjWKUCJ ^ j_idnYwwtpasxF) & 0x7FFFFFFF;
        if (tmp_rvWZDAcCQQTnGNVXop == 64 && i_kwDANWrjWKUCJ < 4) {
            arr_kvPSWvXAyAUTKo.toString();
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
