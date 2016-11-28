package com.inkhjw.personalcommunity.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.inkhjw.personalcommunity.R;

/**
 * @deprecated 不支持沉浸式状态栏
 */

public abstract class NoSupportStatusBackActivity extends BackActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
    }

    @Override
    protected void init() {
        super.init();
        setToolBarStatusColor(getResources().getColor(R.color.black), getResources().getColor(R.color.background_color));
    }

    public void setToolBarStatusColor(int statusColor, int toolbarColor) {
        toolbar_statusbar.setBackgroundColor(statusColor);
        toolbar.setBackgroundColor(toolbarColor);
        toolbar_left_text.setTextColor(getResources().getColor(R.color.text_color_normal_title));
        toolbar_center_text.setTextColor(getResources().getColor(R.color.text_color_normal_title));
        toolbar_right_text.setTextColor(getResources().getColor(R.color.text_color_normal_title));
    }
}