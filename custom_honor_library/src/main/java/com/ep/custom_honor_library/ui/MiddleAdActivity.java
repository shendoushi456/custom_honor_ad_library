package com.ep.custom_honor_library.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ep.custom_honor_library.R;
import com.ep.custom_honor_library.chlOrganizeUtils;
import com.lx.c_interface_library.OnMiddleInterface;


public class MiddleAdActivity extends AppCompatActivity implements OnMiddleInterface {



    private LinearLayout adLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.middle_ad_activity);
        adLayout = findViewById(R.id.middle_ad_layout);
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
