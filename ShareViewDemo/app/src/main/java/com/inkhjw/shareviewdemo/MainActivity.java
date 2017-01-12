package com.inkhjw.shareviewdemo;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.View;

import com.inkhjw.shareviewdemo.shareview.ShareViewDelegate;

public class MainActivity extends AppCompatActivity {
    View view;
    View view2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = findViewById(R.id.view);
        view2 = findViewById(R.id.view2);


        //一个元素共享
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NextActivity.class);
                ShareViewDelegate.create(ShareViewDelegate.ACTIVITY_START)
                        .with(MainActivity.this)
                        .target(view)
                        .bindIntent(intent);
            }
        });

        //多个元素共享
        view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NextActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Pair pair1 = new Pair(view, ViewCompat.getTransitionName(view));
                    Pair pair2 = new Pair(view2, ViewCompat.getTransitionName(view2));
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pair1, pair2).toBundle());
                }
            }
        });
    }
}
