package com.inkhjw.personalcommunity.mvp.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inkhjw.personalcommunity.R;
import com.inkhjw.personalcommunity.base.NoSupportStatusBackActivity;
import com.inkhjw.personalcommunity.event.AvatarChangeEvent;
import com.inkhjw.personalcommunity.event.NickNameChangeEvent;
import com.inkhjw.personalcommunity.framework.imageloader.GlideImageLoader;
import com.inkhjw.personalcommunity.helper.DialogHelper;
import com.inkhjw.personalcommunity.helper.PictureSelectedHelper;
import com.inkhjw.personalcommunity.helper.SharePerferenceHelper;
import com.inkhjw.personalcommunity.utils.FileUtils;
import com.inkhjw.personalcommunity.widget.AvatarView;

import net.bither.util.NativeUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 个人资料
 */
public class PersonalInfoActivity extends NoSupportStatusBackActivity {

    @BindView(R.id.user_presonal_info_avatar)
    RelativeLayout user_presonal_info_avatar;
    @BindView(R.id.user_presonal_info_id)
    RelativeLayout user_presonal_info_id;
    @BindView(R.id.user_presonal_info_nickname)
    RelativeLayout user_presonal_info_nickname;
    @BindView(R.id.user_presonal_info_phone)
    RelativeLayout user_presonal_info_phone;
    @BindView(R.id.user_presonal_info_truename)
    RelativeLayout user_presonal_info_truename;
    @BindView(R.id.user_presonal_info_address)
    RelativeLayout user_presonal_info_address;

    @BindView(R.id.user_presonal_info_avatar_image)
    AvatarView user_presonal_info_avatar_image;
    @BindView(R.id.user_presonal_info_id_text)
    TextView user_presonal_info_id_text;
    @BindView(R.id.user_presonal_info_nickname_text)
    TextView user_presonal_info_nickname_text;

    private GlideImageLoader imageLoader;
    private PictureSelectedHelper pictureSelectedHelper;

    private boolean isUpdateAvatar = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
    }


    @Override
    protected void init() {
        super.init();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        imageLoader = new GlideImageLoader();

        setSupportToolbar(R.drawable.toolbar_back_black, getTextFromId(R.string.user_presonal_info), 0);
        loadAvatar();
        user_presonal_info_id_text.setText("1009023");
        user_presonal_info_nickname_text.setText(SharePerferenceHelper.User.getLoginNickName());

        pictureSelectedHelper = new PictureSelectedHelper(this);
    }

    @OnClick({R.id.user_presonal_info_avatar, R.id.user_presonal_info_id, R.id.user_presonal_info_nickname, R.id.user_presonal_info_phone, R.id.user_presonal_info_truename, R.id.user_presonal_info_address})
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.user_presonal_info_avatar:
                DialogHelper.selectedPictureDialog(pictureSelectedHelper, PersonalInfoActivity.this);
                break;
            case R.id.user_presonal_info_id:
                break;
            case R.id.user_presonal_info_nickname:
                Intent intentPersonal = new Intent(PersonalInfoActivity.this, NickNameChangeActivity.class);
                startActivity(intentPersonal);
                overridePendingTransition(R.anim.translate_right_in, R.anim.translate_zero);
                break;
            case R.id.user_presonal_info_phone:
                break;
            case R.id.user_presonal_info_truename:
                Intent intentVerify = new Intent(PersonalInfoActivity.this, IdentityVerifyActivity.class);
                startActivity(intentVerify);
                overridePendingTransition(R.anim.translate_right_in, R.anim.translate_zero);
                break;
            case R.id.user_presonal_info_address:
                Intent intentAddress = new Intent(PersonalInfoActivity.this, AddressManagerActivity.class);
                startActivity(intentAddress);
                overridePendingTransition(R.anim.translate_right_in, R.anim.translate_zero);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void nickNameChangeResose(NickNameChangeEvent event) {
        user_presonal_info_nickname_text.setText(event.nickName);
    }

    /**
     * 加载头像
     */
    public void loadAvatar() {
        Bitmap bitmap = NativeUtil.getBitmapFromFile(FileUtils.getCachePath() + FileUtils.AVATAR_NAME);

        if (bitmap != null) {
            user_presonal_info_avatar_image.setImageBitmap(bitmap);
        } else {
            imageLoader.displayImage(this, R.drawable.avatar, user_presonal_info_avatar_image);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap = pictureSelectedHelper.reposePictureSelected(requestCode, resultCode, data);
        if (bitmap != null) {
            String cachePath = FileUtils.getCachePath() + FileUtils.AVATAR_NAME;

            File dir = new File(FileUtils.getCachePath());
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(cachePath);
            //采用jni压缩图片
            NativeUtil.compressBitmap(bitmap, file.getPath());
            user_presonal_info_avatar_image.setImageBitmap(bitmap);
            isUpdateAvatar = true;
        } else {
            //ToastHelper.showToast(this, "获取图片为null");
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().post(new AvatarChangeEvent(isUpdateAvatar));
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}