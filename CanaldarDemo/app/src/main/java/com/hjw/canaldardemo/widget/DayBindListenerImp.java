package com.hjw.canaldardemo.widget;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;

/**
 * @author hjw
 * @deprecated 日历视图中每一天的，视图创建/数据绑定、刷新的监听实现
 */
public class DayBindListenerImp extends DayBindListener {
    private Context context;

    public DayBindListenerImp(Context context) {
        this.context = context;
    }

    @Override
    TextView createDayView(ViewGroup viewGroup) {

        TextView textView = new TextView(context);
        textView.setTextColor(Color.WHITE);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(12);

        return textView;
    }

    @Override
    void bindDayView(TextView textView, Calendar calendar) {
        textView.setText(calendar.get(Calendar.DAY_OF_MONTH) + "");
    }
}
