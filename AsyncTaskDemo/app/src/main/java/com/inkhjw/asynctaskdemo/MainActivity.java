package com.inkhjw.asynctaskdemo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyAsyncTask task = new MyAsyncTask();
        task.execute("", "");
    }

    public static class MyAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return null;
        }
    }
}
