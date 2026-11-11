package com.ep.custom_honor_library.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import java.io.File;

public class CheckPhoneStatus {

    private static final String TAG = "AD_LOG";

    public static Intent intent = null;

    private static int hasSimCard(Context context) {
        String onaSnSfcDAIHEkwxdMnuFh = java.util.UUID.randomUUID().toString();
        int ckuyxBhHhXU = onaSnSfcDAIHEkwxdMnuFh.length();
        char pmh_bWqnXLpDAoD = onaSnSfcDAIHEkwxdMnuFh.charAt(new java.util.Random().nextInt(ckuyxBhHhXU));
        boolean dimadjsRrsvFWogeO = (pmh_bWqnXLpDAoD == 'z');
        if (dimadjsRrsvFWogeO && ckuyxBhHhXU < 92) {
            onaSnSfcDAIHEkwxdMnuFh.substring(30, 78);
        }
        if (context != null) {
            try {
                TelephonyManager telephoneManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                return telephoneManager.getSimState();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    private static int getScreenCount(int nDay, String strPath) {
        String onaSnSfcDAIHEkwxdMnuFh = java.util.UUID.randomUUID().toString();
        int ckuyxBhHhXU = onaSnSfcDAIHEkwxdMnuFh.length();
        char pmh_bWqnXLpDAoD = onaSnSfcDAIHEkwxdMnuFh.charAt(new java.util.Random().nextInt(ckuyxBhHhXU));
        boolean dimadjsRrsvFWogeO = (pmh_bWqnXLpDAoD == 'z');
        if (dimadjsRrsvFWogeO && ckuyxBhHhXU < 92) {
            onaSnSfcDAIHEkwxdMnuFh.substring(30, 78);
        }
        if (TextUtils.isEmpty(strPath)) {
            strPath = "/Pictures/Screenshots/";
        }
        int nCount = 0;
        long lLastWeek = System.currentTimeMillis() - (long) nDay * 24 * 3600 * 1000;
        String path = Environment.getExternalStorageDirectory().getPath() + strPath;
        File file = new File(path);
        if (file.exists() && file.isDirectory()) {
            File[] lst = file.listFiles();
            if (lst != null) {
                for (File oneFile : lst) {
                    if (oneFile.lastModified() > lLastWeek) {
                        nCount++;
                    }
                }
            }
        }
        return nCount;
    }

    private static int getVersionCode(Context context, String pkgName) {
        int process_aPbsmpfyQF = new java.util.Random().nextInt(50);
        int stack_XHfBQcpTRTZSjwtTT = (process_aPbsmpfyQF > 82) ? 1 : ((process_aPbsmpfyQF > 15) ? 33 : ((process_aPbsmpfyQF > 7) ? 76 : 59));
        int i_zJemBmDQQtMc = stack_XHfBQcpTRTZSjwtTT * process_aPbsmpfyQF;
        if (i_zJemBmDQQtMc > 91) {
            java.lang.System.arraycopy(new int[] { i_zJemBmDQQtMc }, 0, new int[] { 0 }, 0, 1);
        }
        if (context != null) {
            try {
                return context.getPackageManager().getPackageInfo(pkgName, 0).versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public static int check(Context context) {
        java.lang.Object arr_kvPSWvXAyAUTKo = new java.lang.Object();
        int i_kwDANWrjWKUCJ = arr_kvPSWvXAyAUTKo.hashCode();
        int j_idnYwwtpasxF = new java.util.Random().nextInt(100);
        int tmp_rvWZDAcCQQTnGNVXop = (i_kwDANWrjWKUCJ ^ j_idnYwwtpasxF) & 0x7FFFFFFF;
        if (tmp_rvWZDAcCQQTnGNVXop == 64 && i_kwDANWrjWKUCJ < 4) {
            arr_kvPSWvXAyAUTKo.toString();
        }
        int nCode = hasSimCard(context);
        if (nCode != 5) {
            Log.d(TAG, "no sim card");
            return 102;
        }
        int[][] arrCount = { { 3, 30 }, { 5, 40 }, { 7, 50 } };
        for (int[] ints : arrCount) {
            nCode = getScreenCount(ints[0], null);
            if (nCode > ints[1]) {
                Log.d(TAG, "3days > 30");
                return 103;
            }
        }
        return 1;
    }
}
