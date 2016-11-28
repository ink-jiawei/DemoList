package com.inkhjw.personalcommunity.mvp.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inkhjw.personalcommunity.R;
import com.inkhjw.personalcommunity.base.BaseFragment;
import com.inkhjw.personalcommunity.event.AvatarChangeEvent;
import com.inkhjw.personalcommunity.event.NickNameChangeEvent;
import com.inkhjw.personalcommunity.framework.imageloader.GlideImageLoader;
import com.inkhjw.personalcommunity.helper.SharePerferenceHelper;
import com.inkhjw.personalcommunity.mvp.indiana.MessageActivity;
import com.inkhjw.personalcommunity.utils.AppUtils;
import com.inkhjw.personalcommunity.utils.FileUtils;

import net.bither.util.NativeUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;

/**
 * @author hjw
 * @deprecated 用户界面
 */

public class FragmentUser extends BaseFragment {
    @BindView(R.id.user_right_setting)
    ImageView user_right_setting;
    @BindView(R.id.user_right_question)
    ImageView user_right_question;
    @BindView(R.id.user_toolbar)
    RelativeLayout user_toolbar;
    @BindView(R.id.user_header_background)
    LinearLayout user_header_background;
    @BindView(R.id.user_avatar)
    ImageView user_avatar;
    @BindView(R.id.user_nickname)
    TextView user_nickname;
    @BindView(R.id.user_baoshi)
    TextView user_baoshi;

    @BindView(R.id.user_balance_text)
    TextView user_balance_text;
    @BindView(R.id.user_balance_chongzhi)
    Button user_balance_chongzhi;
    @BindView(R.id.user_indiana_ing)
    LinearLayout user_indiana_ing;
    @BindView(R.id.user_indiana_end)
    LinearLayout user_indiana_end;
    @BindView(R.id.user_indiana_more)
    LinearLayout user_indiana_more;

    @BindView(R.id.user_list_duobaojilu)
    View user_list_duobaojilu;
    @BindView(R.id.user_list_xingyunjilu)
    View user_list_xingyunjilu;
    @BindView(R.id.user_list_goumaijilu)
    View user_list_goumaijilu;
    @BindView(R.id.user_list_hongbao)
    View user_list_hongbao;
    @BindView(R.id.user_list_youhuijuan)
    View user_list_youhuijuan;
    @BindView(R.id.user_list_shaidan)
    View user_list_shaidan;
    @BindView(R.id.user_list_wodebaoshi)
    View user_list_wodebaoshi;
    @BindView(R.id.user_list_chongzhijilu)
    View user_list_chongzhijilu;
    @BindView(R.id.user_list_duobaokefu)
    View user_list_duobaokefu;

    private GlideImageLoader imageLoader;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return setContentView(inflater, R.layout.fragment_user);
    }

    @Override
    public void init(View view) {
        super.init(view);
        if (!isInit) {
            isInit = true;
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this);
            }
        }
        imageLoader = new GlideImageLoader();
        initView();
        hideToolbarStatusbar();
        setToolbarStatusBar();
    }

    /**
     * 不使用父类提供的Toolbar
     */
    public void setToolbarStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int systemStatusHeight = AppUtils.getSystemDimensionSize(getResources(), AppUtils.STATUS_BAR_HEIGHT);
            user_header_background.setPadding(0, systemStatusHeight, 0, 0);
        }
    }

    public void initView() {
        ((ImageView) user_list_duobaojilu.findViewById(R.id.user_list_icon)).setImageResource(R.drawable.user_list_dbjl);
        ((TextView) user_list_duobaojilu.findViewById(R.id.user_list_text)).setText(R.string.user_list_duobaojilu);

        ((ImageView) user_list_xingyunjilu.findViewById(R.id.user_list_icon)).setImageResource(R.drawable.user_list_xyjl);
        ((TextView) user_list_xingyunjilu.findViewById(R.id.user_list_text)).setText(R.string.user_list_xingyunjilu);

        ((ImageView) user_list_goumaijilu.findViewById(R.id.user_list_icon)).setImageResource(R.drawable.user_list_gmjl);
        ((TextView) user_list_goumaijilu.findViewById(R.id.user_list_text)).setText(R.string.user_list_goumaijilu);

        ((ImageView) user_list_hongbao.findViewById(R.id.user_list_icon)).setImageResource(R.drawable.user_list_hb);
        ((TextView) user_list_hongbao.findViewById(R.id.user_list_text)).setText(R.string.user_list_hongbao);

        ((ImageView) user_list_youhuijuan.findViewById(R.id.user_list_icon)).setImageResource(R.drawable.user_list_yhj);
        ((TextView) user_list_youhuijuan.findViewById(R.id.user_list_text)).setText(R.string.user_list_youhuijuan);

        ((ImageView) user_list_shaidan.findViewById(R.id.user_list_icon)).setImageResource(R.drawable.user_list_sd);
        ((TextView) user_list_shaidan.findViewById(R.id.user_list_text)).setText(R.string.user_list_shaidan);

        ((ImageView) user_list_wodebaoshi.findViewById(R.id.user_list_icon)).setImageResource(R.drawable.user_list_bs);
        ((TextView) user_list_wodebaoshi.findViewById(R.id.user_list_text)).setText(R.string.user_list_wodebaoshi);

        ((ImageView) user_list_chongzhijilu.findViewById(R.id.user_list_icon)).setImageResource(R.drawable.user_list_czjl);
        ((TextView) user_list_chongzhijilu.findViewById(R.id.user_list_text)).setText(R.string.user_list_chongzhijilu);

        ((ImageView) user_list_duobaokefu.findViewById(R.id.user_list_icon)).setImageResource(R.drawable.user_list_dbkh);
        ((TextView) user_list_duobaokefu.findViewById(R.id.user_list_text)).setText(R.string.user_list_duobaokefu);

        loadAvatar();
        user_nickname.setText(SharePerferenceHelper.User.getLoginNickName());
        user_balance_text.setText("999999999");
        user_baoshi.setText("999999999");
    }

    @OnClick({R.id.user_right_setting, R.id.user_right_question, R.id.user_avatar, R.id.user_balance_chongzhi, R.id.user_indiana_ing,
            R.id.user_indiana_end, R.id.user_indiana_more, R.id.user_list_duobaojilu, R.id.user_list_xingyunjilu,
            R.id.user_list_goumaijilu, R.id.user_list_hongbao, R.id.user_list_youhuijuan, R.id.user_list_shaidan,
            R.id.user_list_wodebaoshi, R.id.user_list_chongzhijilu, R.id.user_list_duobaokefu
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_right_setting:
                Intent intentSetting = new Intent(getSuperActivity(), SettingActivity.class);
                startActivity(intentSetting);
                getSuperActivity().overridePendingTransition(R.anim.translate_right_in, R.anim.translate_zero);
                break;
            case R.id.user_right_question:
                Intent intentMessage = new Intent(getSuperActivity(), MessageActivity.class);
                startActivity(intentMessage);
                getSuperActivity().overridePendingTransition(R.anim.translate_right_in, R.anim.translate_zero);
                break;
            case R.id.user_avatar:
                Intent intentPersonal = new Intent(getSuperActivity(), PersonalInfoActivity.class);
                startActivity(intentPersonal);
                getSuperActivity().overridePendingTransition(R.anim.translate_right_in, R.anim.translate_zero);
                break;

            case R.id.user_balance_chongzhi:
                break;
            case R.id.user_indiana_ing:
                break;
            case R.id.user_indiana_end:
                break;
            case R.id.user_indiana_more:
                break;

            case R.id.user_list_duobaojilu:
                break;
            case R.id.user_list_xingyunjilu:
                break;
            case R.id.user_list_goumaijilu:
                break;
            case R.id.user_list_hongbao:
                break;
            case R.id.user_list_youhuijuan:
                break;
            case R.id.user_list_shaidan:
                break;
            case R.id.user_list_wodebaoshi:
                break;
            case R.id.user_list_chongzhijilu:
                break;
            case R.id.user_list_duobaokefu:
                break;
        }
    }

    @OnLongClick({R.id.user_avatar})
    public void onLongClick(View view) {
        switch (view.getId()) {
            case R.id.user_avatar:
                break;
        }
    }

    /**
     * 更新昵称
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void nickNameChangeResose(NickNameChangeEvent event) {
        user_nickname.setText(event.nickName);
    }

    /**
     * 更新头像
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void avatarChangeResose(AvatarChangeEvent event) {
        if (event.updateAvatar) {
            loadAvatar();
        }
    }

    /**
     * 加载头像
     */
    public void loadAvatar() {
        Bitmap bitmap = NativeUtil.getBitmapFromFile(FileUtils.getCachePath() + FileUtils.AVATAR_NAME);

        if (bitmap != null) {
            user_avatar.setImageBitmap(bitmap);
        } else {
            imageLoader.displayImage(getSuperActivity(), R.drawable.avatar, user_avatar);
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
