package com.inkhjw.beautifulday.main.view.user;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatButton;
import android.widget.EditText;
import android.widget.TextView;

import com.inkhjw.beautifulday.R;
import com.inkhjw.beautifulday.base.BaseActivity;
import com.inkhjw.beautifulday.http.Api;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author hjw
 * @deprecated
 */
public class LoginActivity extends BaseActivity {

    @Bind(R.id.user_login_username)
    EditText user_login_username;
    @Bind(R.id.user_login_password)
    EditText user_login_password;
    @Bind(R.id.user_login_btn)
    AppCompatButton user_login_btn;

    @Bind(R.id.text_result)
    TextView text_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login_activity);
        ButterKnife.bind(this);
    }

    @Override
    public void initView() {
        super.initView();
        initToolbar(false, "登录", 0);
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @OnClick(R.id.user_login_btn)
    public void onClick() {
        RequestBody formBody = new FormBody.Builder()
                .add("username", user_login_username.getText().toString().trim())
                .add("password", user_login_password.getText().toString().trim())
                .build();

        OkHttpClient mOkHttpClient = new OkHttpClient();

        //创建一个Request
        final Request request = new Request.Builder()
                .url(Api.USER_LOGIN)
                .post(formBody)
                .build();
        //new call
        Call call = mOkHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = handler.obtainMessage();
                message.obj = e.toString();
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message message = handler.obtainMessage();
                message.obj = response.body().string();
                handler.sendMessage(message);
            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            text_result.setText("" + msg.obj);
        }
    };
}
