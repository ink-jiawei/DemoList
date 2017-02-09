package com.inkhjw.bluetoothsearchdemo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * @author hjw
 */

public class BlueAdapter extends BaseAdapter {
    private List<String> lists;
    private Context context;

    public BlueAdapter(List<String> lists, Context context) {
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
        TextView textView = new TextView(context);
        textView.setPadding(20, 20, 20, 20);
        textView.setText(lists.get(position));

        return textView;
    }
}
