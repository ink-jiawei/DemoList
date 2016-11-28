package com.inkhjw.personalcommunity.base;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.inkhjw.personalcommunity.R;
import com.inkhjw.personalcommunity.utils.AppUtils;

import butterknife.ButterKnife;

public abstract class SpeicalToolbarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        init();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        init();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        init();
    }

    /**
     * 初始化
     */
    protected void init() {
        ButterKnife.bind(this);
    }

    /**
     * 设置支持沉浸式状态栏
     */
    public void setSupportBarTint(View toolbar) {

        // 4.4及以上版本开启
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            if (toolbar != null) {
                int systemStatusHeight = AppUtils.getSystemDimensionSize(getResources(), AppUtils.STATUS_BAR_HEIGHT);

                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelOffset(R.dimen.y27));
                params.setMargins(0, systemStatusHeight, 0, 0);
                toolbar.setLayoutParams(params);
            }
        }
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


    public String getTextFromId(int textId) {
        if (getResources() == null) {
            return "";
        }
        return getResources().getString(textId);
    }

    /**
     * 重写Activity的Back键的单击事件
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //按下的如果是BACK，同时没有重复
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
            overridePendingTransition(R.anim.translate_zero, R.anim.translate_right_out);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}