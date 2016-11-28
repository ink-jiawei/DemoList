package com.hjw.mvpdemo.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.hjw.mvpdemo.model.operation.LoginCallback;
import com.hjw.mvpdemo.model.operation.LoginOperation;
import com.hjw.mvpdemo.model.operation.LoginOperationImp;
import com.hjw.mvpdemo.view.LoginView;

/**
 * @author hjw
 * @deprecated presenter层，根据对应业务，定义逻辑功能
 * <1.通过view层提供的LoginView直接操作view，达到数据更新的效果
 * 2.通过LoginOperation实现类LoginOperationImp的实例实现model层业务功能>
 */
public class LoginPresenter {
    private LoginView loginView;
    private LoginOperation loginOperation;

    private Handler handler = new Handler();

    public LoginPresenter(LoginView loginView) {
        this.loginView = loginView;
        loginOperation = new LoginOperationImp();
    }

    public void login(final Context context) {
        loginView.openProgress();
        loginOperation.login(loginView.getUserName(), loginView.getPassWord(), new LoginCallback() {
            @Override
            public void loginSuccess() {
                Looper.prepare();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        loginView.closeProgress();
                    }
                });
                Toast.makeText(context, "登录成功!", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void loginFailure() {
                Looper.prepare();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        loginView.closeProgress();
                    }
                });
                Toast.makeText(context, "登录失败!", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        });
    }
}
