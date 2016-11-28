package com.inkhjw.beautifulday.base;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inkhjw.beautifulday.R;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author hjw
 * @deprecated
 */
public class BaseActivity extends AppCompatActivity {

    @Bind(R.id.app_toolbar_back)
    ImageView appToolbarBack;
    @Bind(R.id.app_toolbar_title)
    TextView appToolbarTitle;
    @Bind(R.id.app_toolbar_right_image)
    ImageView appToolbarRightImage;

    private LinearLayout parentLinearLayout;//把父类activity和子类activity的view都add到这里

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView(R.layout.activity_toolbar);
    }

    /**
     * 初始化View
     */
    protected void initView() {
        setSupportBarTint();
    }

    /**
     * 初始化数据
     */
    protected void initData() {
    }

    /**
     * 初始化Toolbar
     *
     * @param isHideBack
     * @param title
     * @param rightImage
     */
    protected void initToolbar(Boolean isHideBack, String title, int rightImage) {
        if (isHideBack) {
            hideBack();
        }
        setTitleText(title);
        setRightImage(rightImage);
    }

    /**
     * 初始化contentview
     */
    private void initContentView(int layoutResID) {
        ViewGroup viewGroup = (ViewGroup) findViewById(android.R.id.content);
        viewGroup.removeAllViews();
        parentLinearLayout = new LinearLayout(this);
        parentLinearLayout.setOrientation(LinearLayout.VERTICAL);
        viewGroup.addView(parentLinearLayout);
        LayoutInflater.from(this).inflate(layoutResID, parentLinearLayout, true);
    }

    @Override
    public void setContentView(int layoutResID) {
        LayoutInflater.from(this).inflate(layoutResID, parentLinearLayout, true);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    public void setContentView(View view) {
        parentLinearLayout.addView(view);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        parentLinearLayout.addView(view, params);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    /**
     * Toolbar的单击事件<提供单击事件处理的而方法，曝露给子类>
     *
     * @param view
     */
    @OnClick({R.id.app_toolbar_back, R.id.app_toolbar_title, R.id.app_toolbar_right_image})
    public void onClcik(View view) {
        switch (view.getId()) {
            case R.id.app_toolbar_back:
                back();
                break;
            case R.id.app_toolbar_title:

                break;
            case R.id.app_toolbar_right_image:
                rightClick();
                break;
        }
    }

    protected void back() {
        finish();
        overridePendingTransition(R.anim.translate_zero, R.anim.translate_right_out);
    }

    protected void rightClick() {

    }

    /**
     * 设置支持沉浸式状态栏
     */
    public void setSupportBarTint() {
        // 4.4及以上版本开启
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);
        // 自定义颜色
        tintManager.setTintColor(getResources().getColor(R.color.app_theme));
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


    /**
     * 隐藏返回icon
     */
    private void hideBack() {
        appToolbarBack.setVisibility(View.GONE);
    }

    private void setTitleText(String title) {
        appToolbarTitle.setText(title);
    }

    private void setRightImage(int res) {
        appToolbarRightImage.setImageResource(res);
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
