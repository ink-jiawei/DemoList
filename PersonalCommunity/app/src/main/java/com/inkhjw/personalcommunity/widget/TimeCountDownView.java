package com.inkhjw.personalcommunity.widget;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.Date;

public class TimeCountDownView extends TextView implements Runnable {
    private long curTime = 0;
    private long time = 0;
    private final long DELAYED_TIME = 10;


    private MyHandler myHandler;

    public TimeCountDownView(Context context) {
        super(context);
        init();
    }

    public TimeCountDownView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TimeCountDownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化
     */
    public void init() {
        myHandler = new MyHandler();
    }

    /**
     * 设置倒计时时间
     *
     * @param time
     */
    public void setTimeCoundDown(long time) {
        this.time = time;
        curTime = new Date().getTime();
        long tar = this.time - curTime;

        if (tar > 0) {
            setText(getHourMinture(tar));
            if (myHandler == null) {
                postDelayed(this, DELAYED_TIME);
            } else {
                myHandler.postDelayed(this, DELAYED_TIME);
            }
        } else {
            setText("已揭晓");
        }
    }

    @Override
    public void run() {
        setTimeCoundDown(this.time);
    }

    class MyHandler extends Handler {

    }

    /**
     * 获取时间差、返回字符串格式
     *
     * @return
     */
    public static String getHourMinture(long tar) {
        long days = tar / (1000 * 60 * 60 * 24);
        long hours = (tar - days * (1000 * 60 * 60 * 24))
                / (1000 * 60 * 60);
        long minutes = (tar - days * (1000 * 60 * 60 * 24) - hours
                * (1000 * 60 * 60))
                / (1000 * 60);
        long secend = (tar - days * (1000 * 60 * 60 * 24) - hours
                * (1000 * 60 * 60) - minutes * (1000 * 60))
                / 1000;
        long millSecend = (tar - days * (1000 * 60 * 60 * 24) - hours
                * (1000 * 60 * 60) - minutes * (1000 * 60) - secend * 1000)
                / 10;

        StringBuilder s = new StringBuilder();
        s.append(minutes > 9 ? minutes : "0" + minutes);
        s.append(":");
        s.append(secend > 9 ? secend : "0" + secend);
        s.append(":");
        s.append(millSecend);

        return s.toString();
    }
}