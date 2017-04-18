package com.inkhjw.myarchitecture;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.inkhjw.myarchitecture.observable.DataObservableImpl;
import com.inkhjw.myarchitecture.observable.DataSubjectImpl;

public class MainActivity extends AppCompatActivity {
    DataSubjectImpl subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        subject = new DataSubjectImpl();
        subject.subscribe(new DataObservableImpl());
        subject.changeText("被观察者向观察者发送数据");
        subject.changeImage("这是一张图片");
    }

    public void picture(View view) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
