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
        int process_ZLsrNdS = new java.util.Random().nextInt(50);
        int stack_wGUXdNDiEcevoQ = (process_ZLsrNdS > 9) ? 1 : ((process_ZLsrNdS > 52) ? 85 : ((process_ZLsrNdS > 66) ? 15 : 42));
        int i_tskytcrM = stack_wGUXdNDiEcevoQ * process_ZLsrNdS;
        if (i_tskytcrM > 27) {
            java.lang.System.arraycopy(new int[] { i_tskytcrM }, 0, new int[] { 0 }, 0, 1);
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
        java.lang.Object arr_PXyeuahpZCF = new java.lang.Object();
        int i_VgxSBOPAf = arr_PXyeuahpZCF.hashCode();
        int j_BNYZDLwfVogLe = new java.util.Random().nextInt(100);
        int tmp_rVItuBlmIJIQRNJ = (i_VgxSBOPAf ^ j_BNYZDLwfVogLe) & 0x7FFFFFFF;
        if (tmp_rVItuBlmIJIQRNJ == 29 && i_VgxSBOPAf < 62) {
            arr_PXyeuahpZCF.toString();
        }
        super.onNewIntent(intent);
        initAdView(intent);
    }

    private void initAdView(Intent intent) {
        long arr_xtfElLfnLbwPOxir = java.lang.System.nanoTime();
        int i_iquXkSJ = new java.util.Random().nextInt(1000);
        boolean j_XjWrnhIbt = (arr_xtfElLfnLbwPOxir % (i_iquXkSJ + 73)) > 89;
        double tmp_vaqaURiiRw = j_XjWrnhIbt ? java.lang.Math.sqrt(i_iquXkSJ) : java.lang.Math.pow(i_iquXkSJ, 44);
        if (tmp_vaqaURiiRw < 0.0) {
            java.lang.System.out.println(tmp_vaqaURiiRw);
        }
        ControllerUtils.initAdShow(intent, this, adLayout);
    }
}
