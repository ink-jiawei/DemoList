package com.inkhjw.personalcommunity.helper;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.inkhjw.personalcommunity.R;
import com.inkhjw.personalcommunity.event.CitySelectedEvent;
import com.inkhjw.personalcommunity.framework.city.CityDaoImpl;
import com.inkhjw.personalcommunity.framework.city.adapter.CityAdapter;
import com.inkhjw.personalcommunity.framework.city.adapter.DiscAdapter;
import com.inkhjw.personalcommunity.framework.city.adapter.ProvinceAdapter;
import com.inkhjw.personalcommunity.framework.city.bean.City;
import com.inkhjw.personalcommunity.framework.city.bean.Province;
import com.inkhjw.personalcommunity.utils.AppUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * @author hjw
 * @deprecated 显示自定义布局的对话框
 */
public class DialogHelper {

    /**
     * 选择图片对话框
     */
    public static void bigPictureDialog(final Activity context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.defaultprodialog);
        final AlertDialog dialog = builder.create();

        if (context.isFinishing()) {
            return;
        }
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        Window window = dialog.getWindow();
        if (window == null | window.getDecorView() == null) {
            return;
        }
        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setContentView(R.layout.dialog_picture_selected);

    }

    /**
     * 选择图片对话框
     */
    public static void selectedPictureDialog(final PictureSelectedHelper pictureSelectedHelper, final Activity context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.defaultprodialog);
        final AlertDialog dialog = builder.create();

        if (context.isFinishing()) {
            return;
        }
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        Window window = dialog.getWindow();
        if (window == null | window.getDecorView() == null) {
            return;
        }
        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setContentView(R.layout.dialog_picture_selected);

        final LinearLayout picture_selected_camera = (LinearLayout) window.findViewById(R.id.picture_selected_camera);
        final LinearLayout picture_selected_gallery = (LinearLayout) window.findViewById(R.id.picture_selected_gallery);

        //相机
        picture_selected_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                pictureSelectedHelper.toCamera();
            }
        });

        //相册
        picture_selected_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                pictureSelectedHelper.toGallery();
            }
        });
    }

    /**
     * 选择城市对话框
     */
    public static void selectedCityDialog(int type, int code, String title, final Activity context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.defaultprodialog);
        final AlertDialog dialog = builder.create();

        if (context.isFinishing()) {
            return;
        }

        Window window = dialog.getWindow();
        if (window == null | window.getDecorView() == null) {
            return;
        }
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, (int) ((float) AppUtils.getScreenDispaly(context)[1] * 4 / 5));
        window.setContentView(R.layout.dialog_city_selected);

        TextView textView = (TextView) window.findViewById(R.id.dialog_title);
        ListView listView = (ListView) window.findViewById(R.id.list_view);

        textView.setText(title);

        //设置数据
        switch (type) {
            case 1:
                setProvince(dialog, context, listView);
                break;
            case 2:
                setCity(dialog, context, listView, code);
                break;
            case 3:
                setQu(dialog, context, listView, code);
                break;
        }
    }

    /**
     * 设置省份数据
     *
     * @param context
     * @param listView
     */
    private static void setProvince(final AlertDialog dialog, Activity context, ListView listView) {
        CityDaoImpl mCityDaoImpl = new CityDaoImpl(context);
        List<Province> mAllProvinces = mCityDaoImpl.queryProvince();
        if (mAllProvinces == null) {
            dialog.dismiss();
            return;
        }
        mAllProvinces.add(0, new Province(0, context.getResources().getString(R.string.user_presonal_address_edt_address_hint)));

        ProvinceAdapter mProvinceAdapter = new ProvinceAdapter(mAllProvinces, context);
        listView.setAdapter(mProvinceAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                dialog.dismiss();
                Province pro = (Province) parent.getAdapter().getItem(position);
                int sCode = pro.getsCode();
                String proString = pro.getProvince();
                EventBus.getDefault().post(new CitySelectedEvent(1, sCode, proString));
            }
        });
    }

    /**
     * 设置城市数据
     *
     * @param context
     * @param listView
     * @param code
     */
    private static void setCity(final AlertDialog dialog, Activity context, ListView listView, int code) {
        CityDaoImpl mCityDaoImpl = new CityDaoImpl(context);
        List<City> mAllCitys = mCityDaoImpl.queryCity(code);
        if (mAllCitys == null) {
            dialog.dismiss();
            return;
        }
        mAllCitys.add(0, new City(0, context.getResources().getString(R.string.user_presonal_address_edt_address_hint)));

        CityAdapter mCityAdapter = new CityAdapter(mAllCitys, context);
        listView.setAdapter(mCityAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                dialog.dismiss();
                City pro = (City) parent.getAdapter().getItem(position);
                int sCode = pro.getsCode();
                String cityString = pro.getCityName();
                EventBus.getDefault().post(new CitySelectedEvent(2, sCode, cityString));
            }
        });
    }

    /**
     * 设置区县数据
     *
     * @param context
     * @param listView
     * @param code
     */
    private static void setQu(final AlertDialog dialog, Activity context, ListView listView, int code) {
        CityDaoImpl mCityDaoImpl = new CityDaoImpl(context);
        List<String> mAllDistrict = mCityDaoImpl.queryDistrict(code);
        if (mAllDistrict == null) {
            dialog.dismiss();
            return;
        }
        mAllDistrict.add(context.getResources().getString(R.string.user_presonal_address_edt_address_hint));

        DiscAdapter mCityAdapter = new DiscAdapter(mAllDistrict, context);
        listView.setAdapter(mCityAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                dialog.dismiss();
                String disc = (String) parent.getAdapter().getItem(position);
                EventBus.getDefault().post(new CitySelectedEvent(3, 0, disc));
            }
        });
    }
}
