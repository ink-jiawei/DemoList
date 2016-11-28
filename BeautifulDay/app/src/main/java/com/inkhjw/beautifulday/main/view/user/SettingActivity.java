package com.inkhjw.beautifulday.main.view.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.inkhjw.beautifulday.R;
import com.inkhjw.beautifulday.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * 设置Activity
 * 2016/4/16 0016
 */
public class SettingActivity extends BaseActivity {
    @Bind(R.id.setting_filemanager)
    TextView settingFilemanager;
    @Bind(R.id.setting_typemanager)
    TextView settingTypemanager;
    @Bind(R.id.setting_photomanager)
    TextView settingPhotomanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    @Override
    public void initView() {
        super.initView();
        initToolbar(false, "设置", 0);
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @OnClick({R.id.setting_filemanager, R.id.setting_typemanager, R.id.setting_photomanager})
    public void onClick(View view) {
        switch (view.getId()) {
            //文件管理
            case R.id.setting_filemanager:
                Intent fileIntent = new Intent(SettingActivity.this, FileDirectoryActivity.class);
                startActivity(fileIntent);
                break;

            //文件分类
            case R.id.setting_typemanager:
                Intent typeIntent = new Intent(SettingActivity.this, FileDirectoryActivity.class);
                startActivity(typeIntent);
                break;

            //相册
            case R.id.setting_photomanager:
                Intent photoIntent = new Intent(SettingActivity.this, PhotoGroupActivity.class);
                startActivity(photoIntent);
                break;
        }
    }
}
