package com.inkhjw.personalcommunity.mvp.user;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.inkhjw.personalcommunity.R;
import com.inkhjw.personalcommunity.base.NoSupportStatusBackActivity;
import com.inkhjw.personalcommunity.event.NickNameChangeEvent;
import com.inkhjw.personalcommunity.helper.SharePerferenceHelper;
import com.inkhjw.personalcommunity.helper.ToastHelper;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 个人资料
 */
public class NickNameChangeActivity extends NoSupportStatusBackActivity {


    @BindView(R.id.user_presonal_info_nickname_input)
    EditText user_presonal_info_nickname_input;
    @BindView(R.id.user_presonal_info_nickname_input_clear)
    ImageView user_presonal_info_nickname_input_clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_nickname_change);
    }

    @Override
    protected void init() {
        super.init();
        setSupportToolbar(R.drawable.toolbar_back_black, getTextFromId(R.string.user_presonal_change_nickname), getTextFromId(R.string.save));
        setRightTextColor(R.color.announced_progress_text_color);
        user_presonal_info_nickname_input.setText(SharePerferenceHelper.User.getLoginNickName());
    }

    @OnClick({R.id.user_presonal_info_nickname_input_clear})
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.toolbar_right_layout:
                String input = user_presonal_info_nickname_input.getText().toString().trim();
                if (TextUtils.isEmpty(input)) {
                    ToastHelper.showToast(NickNameChangeActivity.this, "昵称不能为空!");
                    return;
                }
                EventBus.getDefault().post(new NickNameChangeEvent(input));

                finish();
                overridePendingTransition(R.anim.translate_zero, R.anim.translate_right_out);

                SharePerferenceHelper.User.setLoginNickName(input);
                break;
            case R.id.user_presonal_info_nickname_input_clear:
                user_presonal_info_nickname_input.setText(null);
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}