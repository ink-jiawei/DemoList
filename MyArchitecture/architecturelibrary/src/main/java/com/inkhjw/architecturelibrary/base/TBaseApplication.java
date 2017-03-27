package com.inkhjw.architecturelibrary.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;

public class TBaseApplication extends Application {

    public static Context baseContext;

    @Override
    public void onCreate() {
        super.onCreate();
        baseContext = this;
    }

    protected void checkSupportCpu() {
        StringBuilder s = new StringBuilder();

        //判断支持的CPU类型
        if (Build.VERSION.SDK_INT >= 21) {
            String[] abis = Build.SUPPORTED_ABIS;

            for (int i = 0; i < abis.length; i++) {
                s.append(abis[i] + ",");
            }
            //ToastHelper.showToast(baseContext, "支持的CPU架构:" + s.toString());
        } else {
            String[] abis = {Build.CPU_ABI, Build.CPU_ABI2};

            for (int i = 0; i < abis.length; i++) {
                s.append(abis[i] + ",");
            }
            //ToastHelper.showToast(baseContext, "支持的CPU架构:" + s.toString());
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }
}
