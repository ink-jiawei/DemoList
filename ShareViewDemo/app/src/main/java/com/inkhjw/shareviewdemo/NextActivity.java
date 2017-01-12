package com.inkhjw.shareviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.inkhjw.shareviewdemo.shareview.ShareViewDelegate;

public class NextActivity extends AppCompatActivity {

    private View view;
    ShareViewDelegate delegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nexta_ctivity);
        view = findViewById(R.id.view);

        delegate = ShareViewDelegate.create(ShareViewDelegate.ACTIVITY_END)
                .with(this)
                .target(view)
                .parseIntent(getIntent())
                .makeSceneTransitionAnimation(1000);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delegate.exitSceneTransitionAnimation();
            }
        });
    }
}
