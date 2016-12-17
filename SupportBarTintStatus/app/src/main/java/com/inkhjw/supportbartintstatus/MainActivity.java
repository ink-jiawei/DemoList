package com.inkhjw.supportbartintstatus;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String STATUS_BAR_HEIGHT = "status_bar_height";
    private LinearLayout rootLayout;//把父类activity和子类activity的view都add到这里

    private Toolbar toolabr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // getSupportActionBar().hide();

        initStatusBar();
        setSupportBarTint(Color.BLUE);

        setContentView(R.layout.activity_main);

        toolabr = (Toolbar) findViewById(R.id.toolabr);
        toolabr.setTitle("沉浸式状态栏");
        setSupportActionBar(toolabr);
    }

    /**
     * 初始化根布局
     */
    private void initStatusBar() {
        ViewGroup viewGroup = (ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content);
        viewGroup.removeAllViews();
        rootLayout = new LinearLayout(this);
        rootLayout.setOrientation(LinearLayout.VERTICAL);
        viewGroup.addView(rootLayout);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        LayoutInflater.from(this).inflate(layoutResID, rootLayout, true);
    }

    @Override
    public void setContentView(View view) {
        rootLayout.addView(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        rootLayout.addView(view, params);
    }

    /**
     * 设置支持沉浸式状态栏
     */
    @TargetApi(19)
    public void setSupportBarTint(int color) {
        // 4.4及以上版本开启
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus();
            setStatus(color);
        }
    }

    @TargetApi(19)
    private void setTranslucentStatus() {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        winParams.flags |= bits;
        win.setAttributes(winParams);
    }

    /**
     * 设置状态栏
     */
    protected void setStatus(int color) {
        if (rootLayout != null) {
            int systemStatusHeight = getSystemDimensionSize(getResources(), STATUS_BAR_HEIGHT);
            LinearLayout linearLayout = new LinearLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, systemStatusHeight);
            //是否显示系统的Actionbar，getSupportActionBar()==null时为style中设置了无Actionbar的主题
            if (getSupportActionBar() != null && getSupportActionBar().isShowing()) {
                params.setMargins(0, 0, 0, getActionbarHeight());
                Toast.makeText(this, "barHeight=" + getActionbarHeight(), Toast.LENGTH_LONG).show();
            }

            linearLayout.setLayoutParams(params);
            linearLayout.setBackgroundColor(color);
            rootLayout.addView(linearLayout);
        }
    }

    /**
     * 获得系统属性尺寸
     *
     * @param res
     * @param key 属性key
     * @return
     */
    public static int getSystemDimensionSize(Resources res, String key) {
        int result = 0;
        int resourceId = res.getIdentifier(key, "dimen", "android");
        if (resourceId > 0) {
            result = res.getDimensionPixelSize(resourceId);
        }

        if (res == null) {
            return 59;
        }

        return result;
    }

    /**
     * 获取Actionbar的高度
     */
    public int getActionbarHeight() {
        TypedArray actionbarSizeTypedArray = obtainStyledAttributes(new int[]{
                android.R.attr.actionBarSize
        });
        return actionbarSizeTypedArray.getDimensionPixelSize(0, 0);
    }
}
