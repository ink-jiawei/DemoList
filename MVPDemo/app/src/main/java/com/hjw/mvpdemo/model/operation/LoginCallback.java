package com.hjw.mvpdemo.model.operation;

/**
 * @author hjw
 * @deprecated 登录的回调接口
 */
public interface LoginCallback {
    void loginSuccess();

    void loginFailure();
}
