package com.inkhjw.beautifulday.main.view.user;

import android.os.Bundle;
import android.widget.ImageView;

import com.inkhjw.beautifulday.R;
import com.inkhjw.beautifulday.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 图片处理的Activity
 */
public class PhotoHandlerActivity extends BaseActivity {
    @Bind(R.id.select_image)
    ImageView select_image;
    private final static int SCAN_OK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photohandler);
    }

    @Override
    public void initView() {
        super.initView();
        initToolbar(false, "图片处理", 0);
    }

    @Override
    protected void initData() {
        super.initData();
    }
}