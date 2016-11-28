package com.inkhjw.beautifulday.utils;

import android.util.Log;

/**
 * @author
 * @deprecated 日志类
 */
public class LogUtils {
    private static String TAG = "test";

    /**
     * 输入e级别的日志
     */
    public static void Loge(String e) {
        Log.e(TAG, e);
    }
}
