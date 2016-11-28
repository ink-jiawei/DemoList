package com.inkhjw.personalcommunity.mvp.indiana;

import android.os.Bundle;
import android.view.View;

import com.inkhjw.personalcommunity.R;
import com.inkhjw.personalcommunity.base.NoSupportStatusBackActivity;

/**
 * 常见问题
 */
public class CommonProblemsActivity extends NoSupportStatusBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_problems);
    }

    @Override
    protected void init() {
        super.init();
        setSupportToolbar(R.drawable.toolbar_back_black, getTextFromId(R.string.indiana_hot_question), R.drawable.toolbar_fresh);
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