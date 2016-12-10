package com.hjw.canaldardemo.widget;

import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;

/**
 * @author hjw
 * @deprecated 日历视图中每一天的数据绑定、刷新的监听
 */
public abstract class DayBindListener {
    /**
     * 创建每一天的视图
     */
    abstract TextView createDayView(ViewGroup viewGroup);

    /**
     * 绑定每一天的视图数据
     */
    abstract void bindDayView(TextView view, Calendar calendar);
}
