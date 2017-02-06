package com.inkhjw.packagemanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * @author hjw
 */

public class AppAdapter extends BaseAdapter {
    private List<PackageInfo> lists;
    private Context context;

    public AppAdapter(List<PackageInfo> lists, Context context) {
        this.lists = lists;
        this.context = context;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PackageInfo packageInfo = lists.get(position);

        View view = LayoutInflater.from(context).inflate(R.layout.list, null);
        ImageView icon = (ImageView) view.findViewById(R.id.icon);
        TextView packagename = (TextView) view.findViewById(R.id.packagename);
        TextView label = (TextView) view.findViewById(R.id.label);

        icon.setImageDrawable(packageInfo.getAppIcon());
        packagename.setText(packageInfo.getAppPackageName());
        label.setText(packageInfo.getAppLabel());
        return view;
    }
}
