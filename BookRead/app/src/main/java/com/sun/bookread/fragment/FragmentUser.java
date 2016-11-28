package com.sun.bookread.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sun.bookread.R;
import com.sun.bookread.main.FileDirectoryActivity;
import com.sun.bookread.main.PhotoGroupActivity;
import com.sun.bookread.main.PhotoSelectActivity;
import com.sun.bookread.main.SettingActivity;
import com.sun.bookread.view.AvatarImageView;

/**
 * author:何佳伟
 * 用户
 */
public class FragmentUser extends Fragment implements View.OnClickListener {
    public static int PHOTO_REQUEST = 0x001;//选择图片的请求码
    /**
     * 头像
     */
    private AvatarImageView avatar;
    private TextView setting;

    public FragmentUser() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = initView(inflater);
        return v;
    }

    public View initView(LayoutInflater inflater) {
        View v = inflater.inflate(R.layout.fragment_user, null);
        setting = (TextView) v.findViewById(R.id.setting);
        setting.setOnClickListener(this);
        avatar = (AvatarImageView) v.findViewById(R.id.user_avatar);
        avatar.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //头像
            case R.id.user_avatar:
                Intent avatarIntent = new Intent(getActivity(), PhotoSelectActivity.class);
                startActivityForResult(avatarIntent, PHOTO_REQUEST);
                break;

            //设置
            case R.id.setting:
                Intent settingIntent = new Intent(getActivity(), SettingActivity.class);
                startActivity(settingIntent);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_REQUEST) {
            Bitmap bitmap = data.getParcelableExtra("picture");
            if (bitmap != null) {
                avatar.setImageBitmap(bitmap);
            } else {
                Toast.makeText(getActivity(), "选择的图片为null", Toast.LENGTH_LONG).show();
            }
        }
    }
}
