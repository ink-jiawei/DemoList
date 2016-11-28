package com.inkhjw.personalcommunity.utils;


import android.text.TextUtils;
import android.util.Log;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    /**
     * 身份证姓名加*号显示
     */
    public static String trueNameKey(String truename) {
        if (truename.length() < 1) {
            return "";
        }
        return "**" + truename.substring(truename.length() - 1, truename.length());
    }

    /**
     * 身份证号码加*号显示
     */
    public static String idCardKey(String idNum) {
        if (idNum.length() < 10) {
            return "";
        }

        return idNum.substring(0, 6) + "*******" + idNum.substring(idNum.length() - 4, idNum.length());
    }

    /**
     * 手机号码加*号显示
     */
    public static String phoneNumKey(String phoneNum) {
        if (phoneNum.length() < 7) {
            return "";
        }
        return phoneNum.substring(0, 3) + "****" + phoneNum.substring(phoneNum.length() - 4, phoneNum.length());
    }

    /**
     * 邮箱加*号显示
     */
    public static String emailKey(String email) {
        return email.substring(0, 3) + "****" + email.substring(email.indexOf("@"), email.length());
    }

    /**
     * 校验身份证
     *
     * @param idCard
     * @return
     */
    public static boolean checkIdCard(String idCard) {
        Pattern p = Pattern
                .compile("\\d{15}|\\d{17}[0-9Xx]");
        Matcher m = p.matcher(idCard);

        return m.matches();
    }

    /**
     * 校验手机号码
     *
     * @param phone
     * @return
     */
    public static boolean checkPhoneNum(String phone) {
        Pattern p = Pattern
                .compile("(13\\d|14[57]|15[^4,\\D]|17[678]|18\\d)\\d{8}|170[059]\\d{7}");
        Matcher m = p.matcher(phone);

        return m.matches();
    }

    /**
     * 校验邮箱地址
     */
    public static boolean checkEmail(String email) {
        String str = "\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();
    }

    /**
     * 校验密码
     */
    public static boolean checkPassWord(String password) {

        return password.matches(".*\\d+.*") && password.matches(".*[a-zA-Z]+.*");
    }


    /**
     *-----------------------------产品相关字符串的转化-----------------------------------
     */
    /**
     * @param s
     * @param len 小数位数
     * @return 格式后的金额
     * @deprecated 金额的格式化<>
     */

    public static String moneyFormat(String s, int len) {
        if (TextUtils.isEmpty(s)) {
            return "";
        }
        NumberFormat formater = null;
        double num = Double.parseDouble(s);
        if (len == 0) {
            formater = new DecimalFormat("###,##0");
        } else {
            StringBuffer buff = new StringBuffer();
            buff.append("###,##0.");
            for (int i = 0; i < len; i++) {
                buff.append("0");
            }
            formater = new DecimalFormat(buff.toString());
        }
        return formater.format(num) + "元";
    }

    /**
     * @param money
     * @return 格式后的金额
     * @deprecated 金额的格式化<大写格式>
     */

    public static String moneyFormat(String money) {
        int n = 0;
        if (TextUtils.isEmpty(money)) {
            n = 0;
        } else {
            n = Integer.parseInt(money.trim());
        }
        String digit[] = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
        String unit[][] = {{"元", "万", "亿"},
                {"", "拾", "佰", "仟"}};

        n = Math.abs(n);

        String s = "";
        if (s.length() < 1) {
            s = "整";
        }
        int integerPart = (int) Math.floor(n);

        for (int i = 0; i < unit[0].length && integerPart > 0; i++) {
            String p = "";
            for (int j = 0; j < unit[1].length && n > 0; j++) {
                p = digit[integerPart % 10] + unit[1][j] + p;
                integerPart = integerPart / 10;
            }
            s = p.replaceAll("(零.)*零$", "").replaceAll("^$", "零") + unit[0][i] + s;
        }
        return s.replaceAll("(零.)*零元", "元").replaceFirst("(零.)+", "").replaceAll("(零.)+", "零").replaceAll("^整$", "零元整");
    }

    /**
     * 将元转为万(小于1万直接显示)
     */
    public static String yunaToWan(String money) {

        if (TextUtils.isEmpty(money)) {
            return "0元";
        }
        DecimalFormat format = (DecimalFormat) DecimalFormat.getInstance();
        format.setGroupingUsed(false);
        int i = 0;
        try {

            String s = format.format(Double.parseDouble(money));
            i = Integer.parseInt(s);
        } catch (Exception e) {
            return "";
        }

        return i / 10000 > 0 ? (i / 10000 + "万元") : money + "元";
    }

    /**
     * 截取小数
     */
    public static StringBuffer getFloat(String s) {

        String regEx = "[0-9]";
        Pattern p = Pattern.compile(regEx);

        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            Matcher m = p.matcher(String.valueOf(c));
            Log.e("test", "匹配:" + m.matches() + "    |c==‘.’:" + (c == '.'));
            if (c == '.' || m.matches()) {
                stringBuffer.append(c);
            }
        }
        return stringBuffer;
    }
}
