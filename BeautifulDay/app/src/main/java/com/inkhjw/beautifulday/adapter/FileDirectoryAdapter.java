package com.inkhjw.beautifulday.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.inkhjw.beautifulday.R;

import java.io.File;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FileDirectoryAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Bitmap directory, file;
    //存储文件名称
    private ArrayList<String> names = null;
    //存储文件路径
    private ArrayList<String> paths = null;

    //参数初始化
    public FileDirectoryAdapter(Context context, ArrayList<String> na, ArrayList<String> pa) {
        names = na;
        paths = pa;
        directory = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_wenjianjia);
        file = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_wenjian);
        //缩小图片
        directory = small(directory, 0.5f);
        file = small(file, 0.5f);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int position) {
        return names.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.file_directory_listivew_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        File f = new File(paths.get(position).toString());
        if (names.get(position).equals("@1")) {
            holder.textView.setText("/");
            holder.imageView.setImageBitmap(directory);
        } else if (names.get(position).equals("@2")) {
            holder.textView.setText("..");
            holder.imageView.setImageBitmap(directory);
        } else {
            holder.textView.setText(f.getName());
            if (f.isDirectory()) {
                holder.imageView.setImageBitmap(directory);
            } else if (f.isFile()) {
                holder.imageView.setImageBitmap(file);
            } else {
                System.out.println(f.getName());
            }
        }
        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.imageView)
        ImageView imageView;
        @Bind(R.id.textView)
        TextView textView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    private Bitmap small(Bitmap map, float num) {
        Matrix matrix = new Matrix();
        matrix.postScale(num, num);
        return Bitmap.createBitmap(map, 0, 0, map.getWidth(), map.getHeight(), matrix, true);
    }
}