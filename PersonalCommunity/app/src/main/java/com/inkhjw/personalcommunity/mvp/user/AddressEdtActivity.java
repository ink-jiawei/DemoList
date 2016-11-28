package com.inkhjw.personalcommunity.mvp.user;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inkhjw.personalcommunity.R;
import com.inkhjw.personalcommunity.base.NoSupportStatusBackActivity;
import com.inkhjw.personalcommunity.bean.AddressListBean;
import com.inkhjw.personalcommunity.event.CitySelectedEvent;
import com.inkhjw.personalcommunity.helper.DialogHelper;
import com.inkhjw.personalcommunity.helper.ToastHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 地址编辑
 */
public class AddressEdtActivity extends NoSupportStatusBackActivity {


    @BindView(R.id.user_presonal_address_edt_sheng_layout)
    LinearLayout user_presonal_address_edt_sheng_layout;
    @BindView(R.id.user_presonal_address_edt_city_layout)
    LinearLayout user_presonal_address_edt_city_layout;
    @BindView(R.id.user_presonal_address_edt_qu_layout)
    LinearLayout user_presonal_address_edt_qu_layout;

    @BindView(R.id.user_presonal_address_edt_shouhuoren)
    EditText user_presonal_address_edt_shouhuoren;
    @BindView(R.id.user_presonal_address_edt_phone)
    EditText user_presonal_address_edt_phone;
    @BindView(R.id.user_presonal_address_edt_detail)
    EditText user_presonal_address_edt_detail;

    @BindView(R.id.user_presonal_address_edt_sheng)
    TextView user_presonal_address_edt_sheng;
    @BindView(R.id.user_presonal_address_edt_city)
    TextView user_presonal_address_edt_city;
    @BindView(R.id.user_presonal_address_edt_qu)
    TextView user_presonal_address_edt_qu;

    @BindView(R.id.user_presonal_address_edt_set_default)
    ImageView user_presonal_address_edt_set_default;

    private int cityCode = 0;
    private int quCode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_address_edt);
    }

    @Override
    protected void init() {
        super.init();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        setSupportToolbar(R.drawable.toolbar_back_black, getTextFromId(R.string.user_presonal_address_edt), getTextFromId(R.string.save));
        setRightTextColor(R.color.announced_progress_text_color);
    }

    @Override
    @OnClick({R.id.user_presonal_address_edt_sheng_layout, R.id.user_presonal_address_edt_city_layout,
            R.id.user_presonal_address_edt_qu_layout, R.id.user_presonal_address_edt_set_default})
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.toolbar_right_layout:
                saveAddress();
                break;
            case R.id.user_presonal_address_edt_sheng_layout:
                DialogHelper.selectedCityDialog(1, 0, getTextFromId(R.string.user_presonal_address_edt_sheng_dialog_title), this);
                break;
            case R.id.user_presonal_address_edt_city_layout:
                if (user_presonal_address_edt_sheng.getText().toString().trim().equals(getTextFromId(R.string.user_presonal_address_edt_address_hint))) {
                    ToastHelper.showToast(AddressEdtActivity.this, "请先选择省份！");
                    return;
                }
                DialogHelper.selectedCityDialog(2, cityCode, getTextFromId(R.string.user_presonal_address_edt_city_dialog_title), this);
                break;
            case R.id.user_presonal_address_edt_qu_layout:
                if (user_presonal_address_edt_city.getText().toString().trim().equals(getTextFromId(R.string.user_presonal_address_edt_address_hint))) {
                    ToastHelper.showToast(AddressEdtActivity.this, "请先选择城市！");
                    return;
                }
                DialogHelper.selectedCityDialog(3, quCode, getTextFromId(R.string.user_presonal_address_edt_qu_dialog_title), this);
                break;

            case R.id.user_presonal_address_edt_set_default:
                user_presonal_address_edt_set_default.setSelected(!user_presonal_address_edt_set_default.isSelected());
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reposeCitySelected(CitySelectedEvent event) {
        switch (event.type) {
            case 1:
                cityCode = event.code;
                user_presonal_address_edt_sheng.setText(event.name);
                user_presonal_address_edt_city.setText(getTextFromId(R.string.user_presonal_address_edt_address_hint));
                user_presonal_address_edt_qu.setText(getTextFromId(R.string.user_presonal_address_edt_address_hint));
                break;
            case 2:
                quCode = event.code;
                user_presonal_address_edt_city.setText(event.name);
                user_presonal_address_edt_qu.setText(getTextFromId(R.string.user_presonal_address_edt_address_hint));
                break;
            case 3:
                user_presonal_address_edt_qu.setText(event.name);
                break;
        }
    }

    public void saveAddress() {
        String name = user_presonal_address_edt_shouhuoren.getText().toString().trim();
        String phone = user_presonal_address_edt_phone.getText().toString().trim();
        String sheng = user_presonal_address_edt_sheng.getText().toString().trim();
        String city = user_presonal_address_edt_city.getText().toString().trim();
        String qu = user_presonal_address_edt_qu.getText().toString().trim();
        String detail = user_presonal_address_edt_detail.getText().toString().trim();
        boolean isDefault = user_presonal_address_edt_set_default.isSelected();

        AddressListBean bean = new AddressListBean(name, phone, sheng, city, qu, detail, isDefault);
        EventBus.getDefault().post(bean);

        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}