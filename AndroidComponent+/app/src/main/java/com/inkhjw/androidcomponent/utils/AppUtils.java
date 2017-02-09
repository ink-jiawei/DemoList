/**
 *
 */
package com.inkhjw.androidcomponent.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.view.WindowManager;

import java.util.List;

/**
 * @author hjw
 * @ App工具类
 */
public class AppUtils {
    public static final String STATUS_BAR_HEIGHT = "status_bar_height";


    /**
     * 获取屏幕分辨率
     *
     * @param context
     * @return
     */
    public static int[] getScreenDispaly(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = windowManager.getDefaultDisplay().getWidth();// 手机屏幕的宽度
        int height = windowManager.getDefaultDisplay().getHeight();// 手机屏幕的高度
        int result[] = {width, height};
        return result;
    }

    /**
     * 获得系统属性尺寸
     *
     * @param res
     * @param key
     * @return
     */
    public static int getSystemDimensionSize(Resources res, String key) {
        int result = 0;
        int resourceId = res.getIdentifier(key, "dimen", "android");
        if (resourceId > 0) {
            result = res.getDimensionPixelSize(resourceId);
        }

        if (res == null) {
            return 59;
        }

        return result;
    }

    /**
     * 判断置顶包名的App是否运行
     *
     * @param context
     * @param PackageName
     * @return
     */
    public static boolean isAppRun(Context context, String PackageName) {
        boolean isAppRunning = false;
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);

        for (ActivityManager.RunningTaskInfo info : list) {

            if (info.topActivity.getPackageName().equals(PackageName)
                    && info.baseActivity.getPackageName().equals(PackageName)) {

                isAppRunning = true;

                break;

            }
        }
        return isAppRunning;
    }

    /**
     * 判断置顶包名的App是否在堆栈的顶层
     *
     * @param context
     * @param PackageName
     * @return
     */
    public static boolean isTopActivity(Context context, String PackageName) {
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        List<ActivityManager.RunningTaskInfo> tasksInfo = am.getRunningTasks(1);

        if (tasksInfo.size() > 0) {

            // 应用程序位于堆栈的顶层

            if (PackageName.equals(tasksInfo.get(0).topActivity
                    .getPackageName())) {

                return true;
            }
        }
        return false;
    }

    /**
     * 结束进程APP
     */
    public static void finishProgrom(Context context) {
        LogHelper.e("kill进程");
        ActivityManager am = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        try {
            if (android.os.Build.VERSION.SDK_INT < 8) {
                am.restartPackage(context.getPackageName());
            } else {
                am.killBackgroundProcesses(context.getPackageName());
            }
        } catch (Exception e) {

        }
    }
}
