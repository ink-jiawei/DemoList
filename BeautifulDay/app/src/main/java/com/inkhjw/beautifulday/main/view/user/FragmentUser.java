package com.inkhjw.beautifulday.main.view.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.inkhjw.beautifulday.R;
import com.inkhjw.beautifulday.base.BaseFragment;
import com.inkhjw.beautifulday.utils.NativeImageLoader;
import com.inkhjw.beautifulday.widget.AvatarImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * author:何佳伟
 * 用户
 */
public class FragmentUser extends BaseFragment {

    @Bind(R.id.user_avatar)
    AvatarImageView user_avatar;
    @Bind(R.id.setting)
    TextView setting;

    public static int PHOTO_REQUEST = 0x001;//选择图片的请求码
    public static int PHOTO_RESULT = 0x002;//选择图片的结果码
    @Bind(R.id.login)
    AppCompatButton login;

    public FragmentUser() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = initView(inflater);
        ButterKnife.bind(this, v);
        return v;
    }

    public View initView(LayoutInflater inflater) {
        View v = inflater.inflate(R.layout.fragment_user, null);
        return v;
    }

    @OnClick({R.id.user_avatar, R.id.setting, R.id.login})
    public void onClick(View view) {
        switch (view.getId()) {
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

            case R.id.login:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_REQUEST) {
            if (resultCode == PHOTO_RESULT) {
                String path = data.getStringExtra("picture");
                if (path != null) {
                    //利用NativeImageLoader类加载本地图片
                    Bitmap bitmap = NativeImageLoader.getInstance().loadNativeImage(path, new NativeImageLoader.NativeImageCallBack() {

                        @Override
                        public void onImageLoader(Bitmap bitmap, String path) {
                            Log.e("test", path);
                            if (bitmap != null) {
                                user_avatar.setImageBitmap(bitmap);
                                Toast.makeText(getActivity(), bitmap.getByteCount() / 1024 + "kb", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                } else {
                    Toast.makeText(getActivity(), "选择的图片为null", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.login)
    public void onClick() {
    }
}
