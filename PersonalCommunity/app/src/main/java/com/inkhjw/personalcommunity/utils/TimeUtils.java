package com.inkhjw.personalcommunity.utils;

import java.util.Date;

/**
 * @author hjw
 * @deprecated
 */

public class TimeUtils {

    /**
     * 获取与当前时间的时间差、返回字符串格式
     *
     * @param time
     */
    public static String getHourMinture(long time) {
        long curTime = new Date().getTime();
        long tar = Math.abs(time - curTime);

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
        String result = minutes + ":" + secend + ":" + millSecend;
        return result;
    }
}
