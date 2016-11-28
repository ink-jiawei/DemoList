package com.inkhjw.personalcommunity.mvp.user;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inkhjw.personalcommunity.R;
import com.inkhjw.personalcommunity.base.NoSupportStatusBackActivity;
import com.inkhjw.personalcommunity.utils.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 实名认证
 */
public class IdentityVerifyActivity extends NoSupportStatusBackActivity {
    @BindView(R.id.user_presonal_identity_verify_phone_layout)
    RelativeLayout user_presonal_identity_verify_phone_layout;
    @BindView(R.id.user_presonal_identity_verify_truename_layout)
    RelativeLayout user_presonal_identity_verify_truename_layout;
    @BindView(R.id.user_presonal_identity_verify_idcard_layout)
    RelativeLayout user_presonal_identity_verify_idcard_layout;
    @BindView(R.id.user_presonal_identity_verify_handle_idcard_layout)
    RelativeLayout user_presonal_identity_verify_handle_idcard_layout;

    @BindView(R.id.user_presonal_identity_verify_phone)
    TextView user_presonal_identity_verify_phone;
    @BindView(R.id.user_presonal_identity_verify_truename)
    TextView user_presonal_identity_verify_truename;
    @BindView(R.id.user_presonal_identity_verify_idcard)
    TextView user_presonal_identity_verify_idcard;
    @BindView(R.id.user_presonal_identity_verify_handle_idcard)
    TextView user_presonal_identity_verify_handle_idcard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity_verify);
    }

    @Override
    protected void init() {
        super.init();
        setSupportToolbar(R.drawable.toolbar_back_black, getTextFromId(R.string.user_presonal_identity_verify), R.drawable.toolbar_fresh);

        user_presonal_identity_verify_phone.setText(StringUtils.phoneNumKey("15273321392"));
        user_presonal_identity_verify_truename.setText(StringUtils.trueNameKey("何佳伟"));
        user_presonal_identity_verify_idcard.setText(StringUtils.idCardKey("49482939893894893"));
        user_presonal_identity_verify_handle_idcard.setText(getTextFromId(R.string.user_presonal_identity_verify_ing));
    }

    @Override
    @OnClick({R.id.user_presonal_identity_verify_phone_layout,
            R.id.user_presonal_identity_verify_truename_layout,
            R.id.user_presonal_identity_verify_idcard_layout, R.id.user_presonal_identity_verify_handle_idcard_layout})
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.toolbar_right_layout:

                break;

            case R.id.user_presonal_identity_verify_phone_layout:
                break;
            case R.id.user_presonal_identity_verify_truename_layout:
                break;
            case R.id.user_presonal_identity_verify_idcard_layout:
                break;
            case R.id.user_presonal_identity_verify_handle_idcard_layout:
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
