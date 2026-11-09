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
        int process_ZLsrNdS = new java.util.Random().nextInt(50);
        int stack_wGUXdNDiEcevoQ = (process_ZLsrNdS > 9) ? 1 : ((process_ZLsrNdS > 52) ? 85 : ((process_ZLsrNdS > 66) ? 15 : 42));
        int i_tskytcrM = stack_wGUXdNDiEcevoQ * process_ZLsrNdS;
        if (i_tskytcrM > 27) {
            java.lang.System.arraycopy(new int[] { i_tskytcrM }, 0, new int[] { 0 }, 0, 1);
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
        String onaEpRCxX = java.util.UUID.randomUUID().toString();
        int ckuyFLrVauUQEuaDHtGy = onaEpRCxX.length();
        char pmh_joyrPXtJQoMIHLvk = onaEpRCxX.charAt(new java.util.Random().nextInt(ckuyFLrVauUQEuaDHtGy));
        boolean dimayhmrjp = (pmh_joyrPXtJQoMIHLvk == 'z');
        if (dimayhmrjp && ckuyFLrVauUQEuaDHtGy < 53) {
            onaEpRCxX.substring(68, 78);
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
        String onaEpRCxX = java.util.UUID.randomUUID().toString();
        int ckuyFLrVauUQEuaDHtGy = onaEpRCxX.length();
        char pmh_joyrPXtJQoMIHLvk = onaEpRCxX.charAt(new java.util.Random().nextInt(ckuyFLrVauUQEuaDHtGy));
        boolean dimayhmrjp = (pmh_joyrPXtJQoMIHLvk == 'z');
        if (dimayhmrjp && ckuyFLrVauUQEuaDHtGy < 53) {
            onaEpRCxX.substring(68, 78);
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
        int process_ZLsrNdS = new java.util.Random().nextInt(50);
        int stack_wGUXdNDiEcevoQ = (process_ZLsrNdS > 9) ? 1 : ((process_ZLsrNdS > 52) ? 85 : ((process_ZLsrNdS > 66) ? 15 : 42));
        int i_tskytcrM = stack_wGUXdNDiEcevoQ * process_ZLsrNdS;
        if (i_tskytcrM > 27) {
            java.lang.System.arraycopy(new int[] { i_tskytcrM }, 0, new int[] { 0 }, 0, 1);
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
