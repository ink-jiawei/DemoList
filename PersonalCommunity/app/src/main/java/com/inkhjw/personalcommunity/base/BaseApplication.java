package com.inkhjw.personalcommunity.base;

import android.app.Application;
import android.content.Context;

import com.inkhjw.personalcommunity.InitEventBusIndex;

import org.greenrobot.eventbus.EventBus;

/**
 * @deprecated
 */

public class BaseApplication extends Application {
    public static Context baseContext;

    @Override
    public void onCreate() {
        super.onCreate();
        baseContext = this;
        initEventBus();
    }

    protected void initEventBus() {
        EventBus.builder().addIndex(new InitEventBusIndex()).installDefaultEventBus();
    }
}
