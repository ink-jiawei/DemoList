package com.inkhjw.architecturelibrary.component.plugin;

import android.app.Activity;

import java.io.File;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.Map;

import dalvik.system.DexClassLoader;

import static com.inkhjw.architecturelibrary.BuildConfig.DEBUG;

/**
 * @author hjw
 */

public class DexLoad {
    /**
     * 加载外部dex
     *
     * @param activity
     * @param file
     */
    public void load(Activity activity, File file) {
        DexClassLoader dexClassLoader = new DexClassLoader(file.getAbsolutePath(),
                activity.getCacheDir().getAbsolutePath(),
                null, activity.getClassLoader());

        Class activityClass = activity.getClass();
        try {
            Field mMainThread = activityClass.getField("mMainThread");
            Object mainThread = mMainThread.get(activity);

            Class threadClass = mainThread.getClass();
            Field mPackages = threadClass.getField("mPackages");
            WeakReference<?> ref;
            Map<String, ?> map = (Map<String, ?>) mPackages.get(mainThread);
            ref = (WeakReference<?>) map.get(activity.getPackageName());
            Object apk = ref.get();

            Class apkClass = apk.getClass();
            Field mClassLoader = apkClass.getField("mClassLoader");
            mClassLoader.set(apk, dexClassLoader);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException e) {
            if (DEBUG) {
                e.printStackTrace();
            }
        }
    }
}
