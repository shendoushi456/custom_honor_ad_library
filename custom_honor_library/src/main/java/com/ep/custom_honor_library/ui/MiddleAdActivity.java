package com.ep.custom_honor_library.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ep.custom_honor_library.chlOrganizeUtils;
import com.lx.c_interface_library.OnMiddleInterface;


public class MiddleAdActivity extends AppCompatActivity implements OnMiddleInterface {



    private LinearLayout adLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int layoutId = getResources().getIdentifier(
                "middle_ad_activity",
                "layout",
                getPackageName()
        );

        if (layoutId == 0) {
            throw new RuntimeException(
                    "middle_ad_activity not found"
            );
        }

        setContentView(layoutId);


        int viewId = getResources().getIdentifier(
                "middle_ad_layout",
                "id",
                getPackageName()
        );


        if (viewId != 0) {
            adLayout = findViewById(viewId);
        }

        initAdView(getIntent());
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        initAdView(intent);
    }


    private void initAdView(Intent intent){
        chlOrganizeUtils.initAdShow(intent,this,adLayout);
    }

}
