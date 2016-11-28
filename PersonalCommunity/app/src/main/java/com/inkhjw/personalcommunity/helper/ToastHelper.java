package com.inkhjw.personalcommunity.helper;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.inkhjw.personalcommunity.R;
import com.inkhjw.personalcommunity.utils.AppUtils;


/**
 * @author hjw
 * @deprecated 提示工具类
 */
public class ToastHelper {
    private static Toast result = null;

    /**
     * 弹出提示信息(在这里指定显示的布局)
     */
    public static void showToast(Context context, String text) {

        int[] screen = AppUtils.getScreenDispaly(context);
        int screenHeight = screen[1];

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
}
