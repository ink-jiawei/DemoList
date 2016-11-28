package com.inkhjw.personalcommunity.framework.city.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.inkhjw.personalcommunity.R;
import com.inkhjw.personalcommunity.framework.city.bean.City;

import java.util.List;

public class CityAdapter extends BaseAdapter {

    private List<City> mListDatas;
    private Context mContext;

    public CityAdapter(List<City> mListDatas, Context mContext) {
        super();
        this.mListDatas = mListDatas;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mListDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mListDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = new TextView(mContext);

            int left = (int) mContext.getResources().getDimension(R.dimen.x22);
            int top = (int) mContext.getResources().getDimension(R.dimen.x8);
            convertView.setPadding(left, top, left, top);
            ((TextView) convertView).setTextSize(mContext.getResources().getDimension(R.dimen.x12));
            ((TextView) convertView).setTextColor(mContext.getResources().getColor(R.color.text_color_normal_title));
        }
        ((TextView) convertView)
                .setText(mListDatas.get(position).getCityName());
        return convertView;
    }

}
