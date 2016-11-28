package com.inkhjw.personalcommunity.mvp.user;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inkhjw.personalcommunity.R;
import com.inkhjw.personalcommunity.base.NoSupportStatusBackActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 设置
 */
public class SettingActivity extends NoSupportStatusBackActivity {

    @BindView(R.id.user_presonal_setting_announced_n)
    RelativeLayout user_presonal_setting_announced_n;
    @BindView(R.id.user_presonal_setting_problems)
    RelativeLayout user_presonal_setting_problems;
    @BindView(R.id.user_presonal_setting_update)
    RelativeLayout user_presonal_setting_update;
    @BindView(R.id.user_presonal_setting_about)
    RelativeLayout user_presonal_setting_about;
    @BindView(R.id.user_presonal_setting_clear_cache)
    RelativeLayout user_presonal_setting_clear_cache;

    @BindView(R.id.user_presonal_setting_announced_status)
    TextView user_presonal_setting_announced_status;
    @BindView(R.id.user_presonal_setting_update_version)
    TextView user_presonal_setting_update_version;
    @BindView(R.id.user_presonal_setting_clear_cache_size)
    TextView user_presonal_setting_clear_cache_size;

    @BindView(R.id.user_presonal_setting_logout)
    Button user_presonal_setting_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    @Override
    protected void init() {
        super.init();
        setSupportToolbar(R.drawable.toolbar_back_black, getTextFromId(R.string.user_presonal_setting), 0);
        user_presonal_setting_announced_status.setText("已开启");
        user_presonal_setting_update_version.setText("2.4.1");
        user_presonal_setting_clear_cache_size.setText("3.9MB");
    }

    @Override
    @OnClick({R.id.user_presonal_setting_announced_n, R.id.user_presonal_setting_problems, R.id.user_presonal_setting_update, R.id.user_presonal_setting_about, R.id.user_presonal_setting_clear_cache, R.id.user_presonal_setting_logout})
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.user_presonal_setting_announced_n:
                break;
            case R.id.user_presonal_setting_problems:
                break;
            case R.id.user_presonal_setting_update:
                break;
            case R.id.user_presonal_setting_about:
                break;
            case R.id.user_presonal_setting_clear_cache:
                break;
            case R.id.user_presonal_setting_logout:
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