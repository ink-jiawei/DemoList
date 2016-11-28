package com.hjw.mvpdemo.model.operation;

/**
 * @author hjw
 * @deprecated 登录的model层业务功能接口
 */
public interface LoginOperation {
    void login(String username, String password, LoginCallback callback);
}
