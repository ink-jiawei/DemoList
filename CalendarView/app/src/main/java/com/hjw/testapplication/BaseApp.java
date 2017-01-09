package com.hjw.testapplication;

import android.app.Application;
import android.content.Context;

/**
 * @author hjw
 */

public class BaseApp extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
}
