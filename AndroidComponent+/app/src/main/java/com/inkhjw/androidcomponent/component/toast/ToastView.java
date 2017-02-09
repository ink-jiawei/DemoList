package com.inkhjw.androidcomponent.component.toast;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.inkhjw.androidcomponent.utils.AppUtils;


/**
 */
public final class ToastView {
    private static Toast result = null;

    /**
     * 弹出提示信息(在这里指定显示的布局)
     */
    public static void showToast(Context context, String text) {

        int[] screen = AppUtils.getScreenDispaly(context);
        int screenHeight = screen[1];

        FrameLayout container = generateContainerLayout(context);
        TextView message = generateMessageView(context);
        message.setText(text);
        container.addView(message);

        if (result == null) {
            result = new Toast(context);
            result.setView(container);
            result.setDuration(Toast.LENGTH_SHORT);
        } else {
            result.setView(container);
            result.setDuration(Toast.LENGTH_SHORT);
        }
        result.setGravity(Gravity.CENTER, 0, screenHeight / 4);//在这里指定显示的位置
        result.show();
    }

    private static FrameLayout generateContainerLayout(Context context) {
        //利用代码创建shape
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.parseColor("#88020202"));//整体颜色
        gd.setCornerRadius(5);
        gd.setStroke(1, Color.parseColor("#88999999"));//边框的颜色

        FrameLayout container = new FrameLayout(context);
        container.setPadding(10, 10, 10, 10);
        container.setBackgroundDrawable(gd);
        return container;
    }

    private static TextView generateMessageView(Context context) {
        TextView message = new TextView(context);
        message.setTextColor(Color.WHITE);
        message.setTextSize(16);
        return message;
    }
}