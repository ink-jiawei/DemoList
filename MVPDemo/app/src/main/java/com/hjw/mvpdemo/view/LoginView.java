package com.hjw.mvpdemo.view;

/**
 * @author hjw
 * @deprecated 登录的view层, 对应于布局文件中的数据
 */
public interface LoginView {
    String getUserName();

    String getPassWord();

    void openProgress();

    void closeProgress();

}
