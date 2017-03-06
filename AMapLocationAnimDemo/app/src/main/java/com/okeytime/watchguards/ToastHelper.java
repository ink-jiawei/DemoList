package com.okeytime.watchguards;


import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

/**
 */
public final class ToastHelper {
    private static Toast result = null;

    /**
     * 弹出提示信息(在这里指定显示的布局)
     */
    public static void showToast(Context context, String text) {
        int screenHeight = getScreenHeight(context);

        View view = LayoutInflater.from(context).inflate(R.layout.toast, null);
        TextView toast_message_text = (TextView) view.findViewById(R.id.toast_text);
        toast_message_text.setText(text);

        if (result == null) {
            result = new Toast(context);
            result.setView(view);
            result.setDuration(Toast.LENGTH_SHORT);
        } else {
            result.setView(view);
            result.setDuration(Toast.LENGTH_SHORT);
        }
        result.setGravity(Gravity.CENTER, 0, screenHeight / 4);//在这里指定显示的位置
        result.show();
    }

    public static int getScreenHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int height = windowManager.getDefaultDisplay().getHeight();
        return height;
//        DisplayMetrics metrics = new DisplayMetrics();
//        getWindow().getWindowManager().getDefaultDisplay().getMetrics(metrics);
//        int height = metrics.heightPixels;
//        int width =  metrics.widthPixels;
    }

    public static int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = windowManager.getDefaultDisplay().getWidth();
        return width;
//        DisplayMetrics metrics = new DisplayMetrics();
//        getWindow().getWindowManager().getDefaultDisplay().getMetrics(metrics);
//        int height = metrics.heightPixels;
//        int width =  metrics.widthPixels;
    }

}