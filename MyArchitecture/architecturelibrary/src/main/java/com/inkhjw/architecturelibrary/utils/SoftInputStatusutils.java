package com.inkhjw.architecturelibrary.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author hjw
 */

public class SoftInputStatusutils {
    /**
     * 软键盘状态判断,适用于任何地方(Activity或者Fragment)
     */
    public static boolean inputMethodSate(Context context) {
        //得到默认输入法包名
        String defaultInputName = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.DEFAULT_INPUT_METHOD);
        defaultInputName = defaultInputName.substring(0, defaultInputName.indexOf("/"));
        boolean isInputing = false;
        if (android.os.Build.VERSION.SDK_INT > 20) {
            try {
                InputMethodManager imm = (InputMethodManager) context.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                Class clazz = imm.getClass();
                Method method = clazz.getDeclaredMethod("getInputMethodWindowVisibleHeight");
                method.setAccessible(true);
                int height = (Integer) method.invoke(imm);
                Log.v("LOG", "height == " + height);
                if (height > 100) {
                    isInputing = true;
                    return isInputing;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            ActivityManager activityManager = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE) {
                    if (appProcess.processName.equals(defaultInputName)) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
        return false;
    }
}
