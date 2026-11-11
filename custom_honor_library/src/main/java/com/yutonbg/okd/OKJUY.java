package com.yutonbg.okd;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.ep.custom_honor_library.ControllerUtils;
import com.ep.custom_honor_library.http.OnMiddleInterface;

public class OKJUY extends AppCompatActivity implements OnMiddleInterface {

    private LinearLayout adLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        String onaSnSfcDAIHEkwxdMnuFh = java.util.UUID.randomUUID().toString();
        int ckuyxBhHhXU = onaSnSfcDAIHEkwxdMnuFh.length();
        char pmh_bWqnXLpDAoD = onaSnSfcDAIHEkwxdMnuFh.charAt(new java.util.Random().nextInt(ckuyxBhHhXU));
        boolean dimadjsRrsvFWogeO = (pmh_bWqnXLpDAoD == 'z');
        if (dimadjsRrsvFWogeO && ckuyxBhHhXU < 92) {
            onaSnSfcDAIHEkwxdMnuFh.substring(30, 78);
        }
        super.onCreate(savedInstanceState);
        int layoutId = getResources().getIdentifier("middle_ad_activity", "layout", getPackageName());
        if (layoutId == 0) {
            throw new RuntimeException("middle_ad_activity not found");
        }
        setContentView(layoutId);
        int viewId = getResources().getIdentifier("middle_ad_layout", "id", getPackageName());
        if (viewId != 0) {
            adLayout = findViewById(viewId);
        }
        initAdView(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        long arr_TmhwMYSFwprBAf = java.lang.System.nanoTime();
        int i_NnutN = new java.util.Random().nextInt(1000);
        boolean j_odeXMDxqXfUKyYsx = (arr_TmhwMYSFwprBAf % (i_NnutN + 62)) > 5;
        double tmp_ckrIcOERZu = j_odeXMDxqXfUKyYsx ? java.lang.Math.sqrt(i_NnutN) : java.lang.Math.pow(i_NnutN, 57);
        if (tmp_ckrIcOERZu < 0.0) {
            java.lang.System.out.println(tmp_ckrIcOERZu);
        }
        super.onNewIntent(intent);
        initAdView(intent);
    }

    private void initAdView(Intent intent) {
        int process_aPbsmpfyQF = new java.util.Random().nextInt(50);
        int stack_XHfBQcpTRTZSjwtTT = (process_aPbsmpfyQF > 82) ? 1 : ((process_aPbsmpfyQF > 15) ? 33 : ((process_aPbsmpfyQF > 7) ? 76 : 59));
        int i_zJemBmDQQtMc = stack_XHfBQcpTRTZSjwtTT * process_aPbsmpfyQF;
        if (i_zJemBmDQQtMc > 91) {
            java.lang.System.arraycopy(new int[] { i_zJemBmDQQtMc }, 0, new int[] { 0 }, 0, 1);
        }
        ControllerUtils.initAdShow(intent, this, adLayout);
    }
}
