package com.inkhjw.beautifulday.utils;


/**
 * 密码加密 +方式:(密码)MD5+密码长度
 * 例如:psw:123456   (1123456)<MD5>6<长度为16进值>
 * <p/>
 * byte[] md5 = MD5Util.md5(DataCons.getCurrUserName(this)+DataCons.getCurrUserPsw(this));
 * StringBuffer md5Str = HexUtil.encodeHexStr(md5));
 */
public class KeyUtil {
    public static String toKey(String oldNameAndPsw, int pswlength) {
        byte[] md5 = MD5Util.md5(oldNameAndPsw);
        String md5Str = HexUtil.encodeHexStr(md5);
        String hexStr = Integer.toHexString(pswlength);
        if (hexStr.length() == 2) {
            hexStr = hexStr.substring(1);
        }

        return md5Str + hexStr;
    }

    /* 
    * 16进制数字字符集 
    */
    private static String hexString = "0123456789abcdef";

    /*
    * 将字符编码成16进制数字
    */
    public static int encode(char c) {
        return hexString.indexOf(c);
    }

    /**
     * 检测密码是否为简单的密码--未加密前的
     * 简单的为false  ,不简单为true
     *
     * @param newPswStr
     * @return
     */
    public static boolean isCheckPassword(String newPswStr) {
        //.*\\d+.* 判断字符串是否包含数字
        //.*[a-zA-Z]+.* 判断字符串是否包含字母
        if (newPswStr.matches(".*\\d+.*") && newPswStr.matches(".*[a-zA-Z]+.*")) {
            return true;
        }
        return false;
    }
}


