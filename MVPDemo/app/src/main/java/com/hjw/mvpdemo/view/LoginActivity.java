package com.hjw.mvpdemo.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.hjw.mvpdemo.R;
import com.hjw.mvpdemo.base.BaseActivity;
import com.hjw.mvpdemo.presenter.LoginPresenter;

/**
 */
public class LoginActivity extends BaseActivity implements LoginView {
    private EditText userName;
    private EditText passWord;
    private Button login;
    private ProgressBar progress;

    LoginPresenter presenter = new LoginPresenter(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    public void init() {
        userName = (EditText) findViewById(R.id.user_login_username);
        passWord = (EditText) findViewById(R.id.user_login_password);
        login = (Button) findViewById(R.id.user_login_btn);
        progress = (ProgressBar) findViewById(R.id.user_login_progress);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.login(LoginActivity.this);
            }
        });
    }

    @Override
    public String getUserName() {
        return userName.getText().toString();
    }

    @Override
    public String getPassWord() {
        return passWord.getText().toString();
    }

    @Override
    public void openProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void closeProgress() {
        progress.setVisibility(View.GONE);
    }
}

