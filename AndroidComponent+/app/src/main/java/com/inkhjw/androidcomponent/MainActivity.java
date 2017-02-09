package com.inkhjw.androidcomponent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.inkhjw.androidcomponent.component.toast.ToastView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void picture(View view) {
        ToastView.showToast(this, "选择图片");
    }
}
