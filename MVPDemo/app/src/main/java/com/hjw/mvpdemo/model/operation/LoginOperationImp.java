package com.hjw.mvpdemo.model.operation;

/**
 * @author hjw
 * @deprecated 登录的model层业务功能接口实现类
 */
public class LoginOperationImp implements LoginOperation {
    /**
     * 此方法运行在子线程中，所以在Activity中不能直接更新UI
     *
     * @param username
     * @param password
     * @param callback
     */
    @Override
    public void login(final String username, final String password, final LoginCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (username.equals("hjw") && password.equals("123456789")) {
                    callback.loginSuccess();
                } else {
                    callback.loginFailure();
                }
            }
        }).start();
    }
}
