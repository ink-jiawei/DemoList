package com.inkhjw.personalcommunity.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inkhjw.personalcommunity.R;
import com.inkhjw.personalcommunity.utils.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class BaseFragment extends Fragment {
    Unbinder unbinder;
    public boolean isInit = false;

    @BindView(R.id.toolbar_left_layout)
    LinearLayout toolbar_left_layout;
    @BindView(R.id.toolbar_left_image)
    ImageView toolbar_left_image;
    @BindView(R.id.toolbar_left_text)
    TextView toolbar_left_text;

    @BindView(R.id.toolbar_center_layout)
    LinearLayout toolbar_center_layout;
    @BindView(R.id.toolbar_center_image)
    ImageView toolbar_center_image;
    @BindView(R.id.toolbar_center_text)
    TextView toolbar_center_text;

    @BindView(R.id.toolbar_right_layout)
    LinearLayout toolbar_right_layout;
    @BindView(R.id.toolbar_right_image)
    ImageView toolbar_right_image;
    @BindView(R.id.toolbar_right_text)
    TextView toolbar_right_text;

    @BindView(R.id.toolbar_statusbar)
    FrameLayout toolbar_statusbar;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;

    private LinearLayout parentLinearLayout;//把父类activity和子类activity的view都add到这里

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return null;
    }

    public void init(View view) {
        unbinder = ButterKnife.bind(this, parentLinearLayout);
        setSupportBarTint();
    }

    public View setContentView(LayoutInflater inflater, int layoutResID) {
        initContentView(inflater, R.layout.toolbar);
        inflater.inflate(layoutResID, parentLinearLayout, true);
        init(parentLinearLayout);
        return parentLinearLayout;
    }

    /**
     * 初始化contentview
     */
    private void initContentView(LayoutInflater inflater, int layoutResID) {
        parentLinearLayout = new LinearLayout(getSuperActivity());
        parentLinearLayout.setOrientation(LinearLayout.VERTICAL);
        inflater.inflate(layoutResID, parentLinearLayout, true);
    }

    /**
     * toolbar的单击事件
     *
     * @param view
     */
    @OnClick({R.id.toolbar_left_layout, R.id.toolbar_center_layout, R.id.toolbar_right_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_layout:
                break;
            case R.id.toolbar_center_layout:
                break;
            case R.id.toolbar_right_layout:
                break;
        }
    }

    /**
     * 设置支持沉浸式状态栏
     */
    private void setSupportBarTint() {
        int systemStatusHeight = AppUtils.getSystemDimensionSize(getResources(), AppUtils.STATUS_BAR_HEIGHT);

        // 4.4及以上版本开启
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelOffset(R.dimen.y27));
            params.setMargins(0, systemStatusHeight, 0, 0);
            toolbar.setLayoutParams(params);
        }
    }

    public void showToolbar() {
        if (toolbar == null) {
            return;
        }
        toolbar.setVisibility(View.VISIBLE);
    }

    public void hideToolbar() {
        if (toolbar == null) {
            return;
        }
        toolbar.setVisibility(View.GONE);
    }

    public void showToolbarStatusbar() {
        if (toolbar_statusbar == null) {
            return;
        }
        toolbar_statusbar.setVisibility(View.VISIBLE);
    }

    public void hideToolbarStatusbar() {
        if (toolbar_statusbar == null) {
            return;
        }
        toolbar_statusbar.setVisibility(View.GONE);
    }

    private void initToobar() {
        if (toolbar_left_layout == null || toolbar_center_layout == null || toolbar_right_layout == null) {
            throw new IllegalStateException("请确保在布局初始化之后调用!");
        }

        toolbar_left_layout.setVisibility(View.GONE);
        toolbar_center_layout.setVisibility(View.GONE);
        toolbar_right_layout.setVisibility(View.GONE);
    }

    /**
     * @param leftImageId
     * @param centerImageId
     * @param rightImageId
     * @deprecated 在布局加载完成后调用、单击事件的响应直接通过clickListener监听
     * 如果资源id==0、String==null、String==""则表示隐藏
     */
    public void setSupportToolbar(int leftImageId, int centerImageId, int rightImageId) {
        initToobar();

        if (leftImageId != 0) {
            setLeftImage(leftImageId);
        }
        if (centerImageId != 0) {
            setCenterImage(centerImageId);
        }
        if (rightImageId != 0) {
            setRightImage(rightImageId);
        }
    }

    /**
     * @param leftImageId
     * @param centerText
     * @param rightImageId
     * @deprecated 在布局加载完成后调用、单击事件的响应直接通过clickListener监听
     * 如果资源id==0、String==null、String==""则表示隐藏
     */
    public void setSupportToolbar(int leftImageId, String centerText, int rightImageId) {
        initToobar();

        if (leftImageId != 0) {
            setLeftImage(leftImageId);
        }
        if (!TextUtils.isEmpty(centerText)) {
            setCenterText(centerText);
        }
        if (rightImageId != 0) {
            setRightImage(rightImageId);
        }
    }

    /**
     * @param leftImageId
     * @param centerImageId
     * @param rightText
     * @deprecated 在布局加载完成后调用、单击事件的响应直接通过clickListener监听
     * 如果资源id==0、String==null、String==""则表示隐藏
     */
    public void setSupportToolbar(int leftImageId, int centerImageId, String rightText) {
        initToobar();

        if (leftImageId != 0) {
            setLeftImage(leftImageId);
        }
        if (centerImageId != 0) {
            setCenterImage(centerImageId);
        }
        if (!TextUtils.isEmpty(rightText)) {
            setRightText(rightText);
        }
    }

    /**
     * @param leftImageId
     * @param centerText
     * @param rightText
     * @deprecated 在布局加载完成后调用、单击事件的响应直接通过clickListener监听
     * 如果资源id==0、String==null、String==""则表示隐藏
     */
    public void setSupportToolbar(int leftImageId, String centerText, String rightText) {
        initToobar();

        if (leftImageId != 0) {
            setLeftImage(leftImageId);
        }
        if (!TextUtils.isEmpty(centerText)) {
            setCenterText(centerText);
        }
        if (!TextUtils.isEmpty(rightText)) {
            setRightText(rightText);
        }
    }

    /**
     * @param leftText
     * @param centerImageId
     * @param rightImageId
     * @deprecated 在布局加载完成后调用、单击事件的响应直接通过clickListener监听
     * 如果资源id==0、String==null、String==""则表示隐藏
     */
    public void setSupportToolbar(String leftText, int centerImageId, int rightImageId) {
        initToobar();

        if (!TextUtils.isEmpty(leftText)) {
            setLeftText(leftText);
        }
        if (centerImageId != 0) {
            setCenterImage(centerImageId);
        }
        if (rightImageId != 0) {
            setRightImage(rightImageId);
        }
    }

    /**
     * @param leftText
     * @param centerText
     * @param rightImageId
     * @deprecated 在布局加载完成后调用、单击事件的响应直接通过clickListener监听
     * 如果资源id==0、String==null、String==""则表示隐藏
     */
    public void setSupportToolbar(String leftText, String centerText, int rightImageId) {
        initToobar();

        if (!TextUtils.isEmpty(leftText)) {
            setLeftText(leftText);
        }
        if (!TextUtils.isEmpty(centerText)) {
            setCenterText(centerText);
        }
        if (rightImageId != 0) {
            setRightImage(rightImageId);
        }
    }

    /**
     * @param leftText
     * @param centerImageId
     * @param rightText
     * @deprecated 在布局加载完成后调用、单击事件的响应直接通过clickListener监听
     * 如果资源id==0、String==null、String==""则表示隐藏
     */
    public void setSupportToolbar(String leftText, int centerImageId, String rightText) {
        initToobar();

        if (!TextUtils.isEmpty(leftText)) {
            setLeftText(leftText);
        }
        if (centerImageId != 0) {
            setCenterImage(centerImageId);
        }
        if (!TextUtils.isEmpty(rightText)) {
            setRightText(rightText);
        }
    }

    /**
     * @param leftText
     * @param centerText
     * @param rightText
     * @deprecated 在布局加载完成后调用、单击事件的响应直接通过clickListener监听
     * 如果资源id==0、String==null、String==""则表示隐藏
     */
    public void setSupportToolbar(String leftText, String centerText, String rightText) {
        initToobar();

        if (!TextUtils.isEmpty(leftText)) {
            setLeftText(leftText);
        }
        if (!TextUtils.isEmpty(centerText)) {
            setCenterText(centerText);
        }
        if (!TextUtils.isEmpty(rightText)) {
            setRightText(rightText);
        }
    }

    /**
     * toolbar左边图标
     *
     * @param leftImageId
     */
    public void setLeftImage(int leftImageId) {
        if (toolbar_left_layout == null || toolbar_left_image == null || toolbar_left_text == null) {
            throw new NullPointerException("请确认是否调用setSupportToolbar()方法支持toolbar!");
        }
        toolbar_left_image.setImageResource(leftImageId);
        toolbar_left_layout.setVisibility(View.VISIBLE);
        toolbar_left_image.setVisibility(View.VISIBLE);
        toolbar_left_text.setVisibility(View.GONE);
    }

    /**
     * toolbar左边文本
     *
     * @param leftTextId
     */
    public void setLeftText(int leftTextId) {
        if (toolbar_left_layout == null || toolbar_left_image == null || toolbar_left_text == null) {
            throw new NullPointerException("请确认是否调用setSupportToolbar()方法支持toolbar!");
        }
        toolbar_left_text.setText(leftTextId);
        toolbar_left_layout.setVisibility(View.VISIBLE);
        toolbar_left_text.setVisibility(View.VISIBLE);
        toolbar_left_image.setVisibility(View.GONE);
    }

    /**
     * toolbar左边文本
     *
     * @param leftText
     */
    public void setLeftText(String leftText) {
        if (toolbar_left_layout == null || toolbar_left_image == null || toolbar_left_text == null) {
            throw new NullPointerException("请确认是否调用setSupportToolbar()方法支持toolbar!");
        }
        toolbar_left_text.setText(leftText);
        toolbar_left_layout.setVisibility(View.VISIBLE);
        toolbar_left_text.setVisibility(View.VISIBLE);
        toolbar_left_image.setVisibility(View.GONE);
    }

    /**
     * toolbar中间图标
     *
     * @param centerImageId
     */
    public void setCenterImage(int centerImageId) {
        if (toolbar_center_layout == null || toolbar_center_image == null || toolbar_center_text == null) {
            throw new NullPointerException("请确认是否调用setSupportToolbar()方法支持toolbar!");
        }
        toolbar_center_image.setImageResource(centerImageId);
        toolbar_center_layout.setVisibility(View.VISIBLE);
        toolbar_center_image.setVisibility(View.VISIBLE);
        toolbar_center_text.setVisibility(View.GONE);
    }

    /**
     * toolbar中间标题
     *
     * @param centerTextId
     */
    public void setCenterText(int centerTextId) {
        if (toolbar_center_layout == null || toolbar_center_image == null || toolbar_center_text == null) {
            throw new NullPointerException("请确认是否调用setSupportToolbar()方法支持toolbar!");
        }
        toolbar_center_text.setText(centerTextId);
        toolbar_center_layout.setVisibility(View.VISIBLE);
        toolbar_center_text.setVisibility(View.VISIBLE);
        toolbar_center_image.setVisibility(View.GONE);
    }

    /**
     * toolbar中间标题
     *
     * @param centerText
     */
    public void setCenterText(String centerText) {
        if (toolbar_center_layout == null || toolbar_center_image == null || toolbar_center_text == null) {
            throw new NullPointerException("请确认是否调用setSupportToolbar()方法支持toolbar!");
        }
        toolbar_center_text.setText(centerText);
        toolbar_center_layout.setVisibility(View.VISIBLE);
        toolbar_center_text.setVisibility(View.VISIBLE);
        toolbar_center_image.setVisibility(View.GONE);
    }

    /**
     * toolbar右边图标
     *
     * @param rightImageId
     */
    public void setRightImage(int rightImageId) {
        if (toolbar_right_layout == null || toolbar_right_image == null || toolbar_right_text == null) {
            throw new NullPointerException("请确认是否调用setSupportToolbar()方法支持toolbar!");
        }
        toolbar_right_image.setImageResource(rightImageId);
        toolbar_right_layout.setVisibility(View.VISIBLE);
        toolbar_right_image.setVisibility(View.VISIBLE);
        toolbar_right_text.setVisibility(View.GONE);
    }


    /**
     * toolbar右边文本
     *
     * @param rightTextId
     */
    public void setRightText(int rightTextId) {
        if (toolbar_right_layout == null || toolbar_right_image == null || toolbar_right_text == null) {
            throw new NullPointerException("请确认是否调用setSupportToolbar()方法支持toolbar!");
        }
        toolbar_right_text.setText(rightTextId);
        toolbar_right_layout.setVisibility(View.VISIBLE);
        toolbar_right_text.setVisibility(View.VISIBLE);
        toolbar_right_image.setVisibility(View.GONE);
    }

    /**
     * toolbar右边文本
     *
     * @param rightText
     */
    public void setRightText(String rightText) {
        if (toolbar_right_layout == null || toolbar_right_image == null || toolbar_right_text == null) {
            throw new NullPointerException("请确认是否调用setSupportToolbar()方法支持toolbar!");
        }
        toolbar_right_text.setText(rightText);
        toolbar_right_layout.setVisibility(View.VISIBLE);
        toolbar_right_text.setVisibility(View.VISIBLE);
        toolbar_right_image.setVisibility(View.GONE);
    }


    /**
     * toolbar右边文本颜色
     *
     * @param color
     */
    public void setRightTextColor(int color) {
        if (toolbar_right_layout == null || toolbar_right_image == null || toolbar_right_text == null) {
            throw new NullPointerException("请确认是否调用setSupportToolbar()方法支持toolbar!");
        }
        toolbar_right_text.setTextColor(color);
    }

    /**
     * toolbar右边文本
     */
    public String getRightText() {
        if (toolbar_right_text == null) {
            throw new NullPointerException("请确认是否调用setSupportToolbar()方法支持toolbar!");
        }
        return toolbar_right_text.getText().toString().trim();

    }

    @Override
    public void onStart() {
        super.onStart();
        //Log.e("test", "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        //Log.e("test", "onResume");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Log.e("test", "onCreate");
    }

    @Override
    public void onPause() {
        super.onPause();
        //Log.e("test", "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        //Log.e("test", "onStop");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //Log.e("test", "onAttach");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //Log.e("test", "onDetach");
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
        //Log.e("test", "onDestroy");
        isInit = false;
    }

    public String getTextFromId(int textId) {
        if (getResources() == null) {
            return "";
        }
        return getResources().getString(textId);
    }

    public FragmentActivity getSuperActivity() {
        FragmentActivity activity = getActivity();
        if (activity == null) {
            return (FragmentActivity) BaseApplication.baseContext;
        }
        return activity;
    }
}
