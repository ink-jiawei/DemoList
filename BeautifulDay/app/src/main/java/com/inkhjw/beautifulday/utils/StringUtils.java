package com.inkhjw.beautifulday.utils;


public class StringUtils {

    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        if (str == null) {
            return true;
        } else if (str.length() == 0) {
            return true;
        } else if (str.trim().length() == 0) {
            return true;
        }
        return false;
    }
}
