package com.inkhjw.nestedscrollview;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private AppBarLayout appBarLayout;
    Toolbar toolbar;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    protected void initView() {
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        linearLayout = (LinearLayout) findViewById(R.id.lv);
    }

    protected void initData() {
        for (int i = 0; i < 20; i++) {
            TextView textView = new TextView(this);
            textView.setPadding(200, 20, 200, 20);
            textView.setText("item" + i);
            linearLayout.addView(textView);
        }
    }
}
