package com.sun.bookread.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sun.bookread.R;
import com.sun.bookread.base.BaseActivity;

/**
 * 设置Activity
 * 2016/4/16 0016
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {
    private TextView filemanager;
    private TextView typemanager;
    private TextView photomanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
    }

    @Override
    public void onCreateCustomToolBar(Toolbar toolbar) {
    }


    public void initView() {
        filemanager = (TextView) findViewById(R.id.setting_filemanager);
        typemanager = (TextView) findViewById(R.id.setting_typemanager);
        photomanager = (TextView) findViewById(R.id.setting_photomanager);
        filemanager.setOnClickListener(this);
        typemanager.setOnClickListener(this);
        photomanager.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
