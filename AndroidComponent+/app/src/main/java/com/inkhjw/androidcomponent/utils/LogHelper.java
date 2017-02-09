package com.inkhjw.androidcomponent.utils;

import android.text.TextUtils;
import android.util.Log;

public final class LogHelper {
    private static boolean isDebug = true;
    private static final String TAG = "debug";

    public static boolean isDebug() {
        return isDebug;
    }

    public static void setDebug(boolean debug) {
        isDebug = debug;
    }

    public static void e(String log) {
        if (TextUtils.isEmpty(log)) {
            throw new NullPointerException("log message can not null");
        }
        if (isDebug) {
            Log.e(TAG, log);
        }
    }

    public static void e(String tag, String log) {
        if (TextUtils.isEmpty(log)) {
            throw new NullPointerException("log message can not null");
        }
        if (isDebug) {
            Log.e(tag + "", log);
        }
    }
}
