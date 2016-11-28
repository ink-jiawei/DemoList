package com.hjw.mvpdemo.model.bean;

/**
 * @author hjw
 * @deprecated 用户的实体
 */
public class UserBean {
    String userName;
    String passWord;

    public UserBean(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
