package com.inkhjw.personalcommunity.mvp.indiana;

import android.os.Bundle;
import android.view.View;

import com.inkhjw.personalcommunity.R;
import com.inkhjw.personalcommunity.base.NoSupportStatusBackActivity;

/**
 * 分类
 */
public class TypeActivity extends NoSupportStatusBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);
    }

    @Override
    protected void init() {
        super.init();
        setSupportToolbar(R.drawable.toolbar_back_black, getTextFromId(R.string.indiana_type_look), 0);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.toolbar_right_layout:

                break;
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}