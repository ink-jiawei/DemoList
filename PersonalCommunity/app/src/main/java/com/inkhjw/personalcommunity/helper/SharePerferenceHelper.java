package com.inkhjw.personalcommunity.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.inkhjw.personalcommunity.base.BaseApplication;

/**
 * @author hjw
 * @deprecated 存储本地参数数据的帮助类
 */
public class SharePerferenceHelper {
    /**
     * 一般数据sp------------------------------------------------------------------------------------
     */
    public static class StoreData {
        private static String spName = "store_data";

        public static SharedPreferences getSp() {
            return BaseApplication.baseContext.getSharedPreferences(spName, Context.MODE_PRIVATE);
        }

        /**
         * add历史搜索
         */
        public static void addHistroySearch(String histroy) {
            String histroys = getHistroySearch();
            if (histroys.contains(histroy + ",")) {
                return;
            }

            StringBuilder s = new StringBuilder(histroys);
            s.append(histroy + ",");

            getSp().edit().putString("histroys", s.toString()).commit();
        }

        /**
         * 获得历史搜索
         */
        public static String getHistroySearch() {
            if (getSp() != null) {
                return getSp().getString("histroys", "");
            }
            return "";
        }

        public static void clear() {
            getSp().edit().clear().commit();
        }
    }

    /**
     * 用户sp------------------------------------------------------------------------------------
     */
    public static class User {
        private static String spName = "user_data";

        /**
         * 用户sp
         *
         * @return
         */
        public static SharedPreferences getUserSp() {
            return BaseApplication.baseContext.getSharedPreferences(spName, Context.MODE_PRIVATE);
        }

        /**
         * 设置保存请求的Cookies
         *
         * @param cookies
         */
        public static void setCookies(String cookies) {
            getUserSp().edit().putString("cookies", cookies).commit();
        }

        /**
         * 获得保存请求的Cookies
         */
        public static String getCookies() {
            if (getUserSp() != null) {
                return getUserSp().getString("cookies", "");
            }
            return "";
        }


        /**
         * 设置登录的状态
         *
         * @param userIsLogin
         */
        public static void setIsLogin(Boolean userIsLogin) {
            getUserSp().edit().putBoolean("userIsLogin", userIsLogin).commit();
        }

        /**
         * 获得登录的状态
         */
        public static Boolean getIsLogin() {
            if (getUserSp() != null) {
                return getUserSp().getBoolean("userIsLogin", false);
            }
            return false;
        }

        /**
         * 设置最新登录用户的用户名<登录时的账号：可能是suername|phonenum>
         *
         * @param loginUserName
         */
        public static void setLoginUserName(String loginUserName) {
            getUserSp().edit().putString("loginUserName", loginUserName).commit();
        }

        /**
         * 获得最新登录用户的用户名<登录时的账号：可能是suername|phonenum>
         */
        public static String getLoginUserName() {
            if (getUserSp() != null) {
                return getUserSp().getString("loginUserName", "");
            }
            return "";
        }

        /**
         * 设置最新登录用户的昵称
         *
         * @param loginNickName
         */
        public static void setLoginNickName(String loginNickName) {
            getUserSp().edit().putString("loginNickName", loginNickName).commit();
        }

        /**
         * 获得最新登录用户的昵称
         */
        public static String getLoginNickName() {
            if (getUserSp() != null) {
                return getUserSp().getString("loginNickName", "");
            }
            return "";
        }

        /**
         * 获得最新登录用户的密码
         *
         * @param loginPassWord
         */
        public static void setLoginPassWord(String loginPassWord) {
            getUserSp().edit().putString("loginPassWord", loginPassWord).commit();
        }

        /**
         * 获得最新登录用户的密码
         */
        public static String getLoginPassWord() {
            if (getUserSp() != null) {
                return getUserSp().getString("loginPassWord", "");
            }
            return "";
        }


        /**
         * 设置记住密码后保存到本地的User密码
         *
         * @param rememberPassword
         */
        public static void setRememberPassword(String rememberPassword) {
            getUserSp().edit().putString("rememberPassword", rememberPassword).commit();
        }

        /**
         * 获得记住密码后保存到本地的User密码
         */
        public static String getRememberPassword() {
            if (getUserSp() != null) {
                return getUserSp().getString("rememberPassword", "");
            }
            return "";
        }

        /**
         * 设置是否记住密码<5>
         *
         * @param isRememberPassword
         */
        public static void setIsRememberPassword(Boolean isRememberPassword) {
            getUserSp().edit().putBoolean("isRememberPassword", isRememberPassword).commit();
        }

        /**
         * 获得是否记住密码
         */
        public static Boolean getIsRememberPassword() {
            if (getUserSp() != null) {
                return getUserSp().getBoolean("isRememberPassword", false);
            }
            return false;
        }

        /**
         * 设置最新登录用户的用户名
         *
         * @param userName
         */
        public static void setUserName(String userName) {
            getUserSp().edit().putString("userName", userName).commit();
        }

        /**
         * 获得最新登录用户的户的用户名
         */
        public static String getUserName() {
            if (getUserSp() != null) {
                return getUserSp().getString("userName", "");
            }
            return "";
        }

        /**
         * 设置最新登录用户的手机号码
         *
         * @param loginPhoneNum
         */
        public static void setLoginPhoneNum(String loginPhoneNum) {
            getUserSp().edit().putString("loginPhoneNum", loginPhoneNum).commit();
        }

        /**
         * 获得最新登录用户的手机号码
         */
        public static String getLoginPhoneNum() {
            if (getUserSp() != null) {
                return getUserSp().getString("loginPhoneNum", "");
            }
            return "";
        }

        /**
         * 设置最新登录用户的身份证姓名
         *
         * @param loginTrueName
         */
        public static void setLoginTrueName(String loginTrueName) {
            getUserSp().edit().putString("loginTrueName", loginTrueName).commit();
        }

        /**
         * 获得最新登录用户的身份证姓名
         */
        public static String getLoginTrueName() {
            if (getUserSp() != null) {
                return getUserSp().getString("loginTrueName", "");
            }
            return "";
        }

        /**
         * 设置最新登录用户的身份证号码
         *
         * @param loginIdNumber
         */
        public static void setLoginIdNumber(String loginIdNumber) {
            getUserSp().edit().putString("loginIdNumber", loginIdNumber).commit();
        }

        /**
         * 获得最新登录用户的身份证号码
         */
        public static String getLoginIdNumber() {
            if (getUserSp() != null) {
                return getUserSp().getString("loginIdNumber", "");
            }
            return "";
        }

        /**
         * 设置最新登录用户的邮箱
         *
         * @param loginEmail
         */
        public static void setLoginEmail(String loginEmail) {
            getUserSp().edit().putString("loginEmail", loginEmail).commit();
        }

        /**
         * 获得最新登录用户的邮箱
         */
        public static String getLoginEmail() {
            if (getUserSp() != null) {
                return getUserSp().getString("loginEmail", "");
            }
            return "";
        }

        /**
         * 设置最新登录用户的邮箱是否通过验证
         *
         * @param loginEmailIsVali
         */
        public static void setLoginEmailIsVali(String loginEmailIsVali) {
            getUserSp().edit().putString("loginEmailIsVali", loginEmailIsVali).commit();
        }

        /**
         * 获得最新登录用户的邮箱是否通过验证
         */
        public static String getLoginEmailIsVali() {
            if (getUserSp() != null) {
                return getUserSp().getString("loginEmailIsVali", "");
            }
            return "";
        }

        /**
         * 退出登录时的clear操作<测试用>
         */
        public static void loginOutClear() {
            if (getUserSp() != null) {
                setIsLogin(false);
            }
        }

        /**
         * 安全退出时的clear操作
         */
        public static void anQuanLoginOutClear() {
            if (getUserSp() != null) {
                setLoginPassWord(null);
                setRememberPassword(null);
                setUserName(null);
                setLoginPhoneNum(null);
                setLoginTrueName(null);
                setLoginIdNumber(null);
                setLoginEmail(null);
                setLoginEmailIsVali(null);
                setIsLogin(false);
                setIsRememberPassword(false);
            }
        }
    }
}
