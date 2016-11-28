package com.sun.bookread.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.sun.bookread.R;
import com.sun.bookread.bean.PhotoImageBean;
import com.sun.bookread.utils.NativeImageLoader;
import com.sun.bookread.view.PhotoGroupImageView;

import java.util.List;

public class PhotoGroupAdapter extends BaseAdapter {
    private List<PhotoImageBean> list;
    private Point mPoint = new Point(0, 0);//用来封装ImageView的宽和高的对象
    private GridView mGridView;
    protected LayoutInflater mInflater;

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    public PhotoGroupAdapter(Context context, List<PhotoImageBean> list, GridView mGridView) {
        this.list = list;
        this.mGridView = mGridView;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        PhotoImageBean mImageBean = list.get(position);
        String path = mImageBean.getTopImagePath();
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.photogroup_gridview_item, null);
            viewHolder.group_image = (PhotoGroupImageView) convertView.findViewById(R.id.group_image);
            viewHolder.group_title = (TextView) convertView.findViewById(R.id.group_title);
            viewHolder.group_count = (TextView) convertView.findViewById(R.id.group_count);

            //用来监听ImageView的宽和高  
            viewHolder.group_image.setOnMeasureListener(new PhotoGroupImageView.OnMeasureListener() {

                @Override
                public void onMeasureSize(int width, int height) {
                    mPoint.set(width, height);
                }
            });

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.group_image.setImageResource(R.mipmap.ic_launcher);
        }

        viewHolder.group_title.setText(mImageBean.getFolderName());
        viewHolder.group_count.setText(Integer.toString(mImageBean.getImageCounts()));
        //给ImageView设置路径Tag,这是异步加载图片的小技巧  
        viewHolder.group_image.setTag(path);

        //利用NativeImageLoader类加载本地图片
        Bitmap bitmap = NativeImageLoader.getInstance().loadNativeImage(path, mPoint, new NativeImageLoader.NativeImageCallBack() {

            @Override
            public void onImageLoader(Bitmap bitmap, String path) {
                ImageView group_image = (ImageView) mGridView.findViewWithTag(path);
                if (bitmap != null && group_image != null) {
                    group_image.setImageBitmap(bitmap);
                }
            }
        });

        if (bitmap != null) {
            viewHolder.group_image.setImageBitmap(bitmap);
        } else {
            viewHolder.group_image.setImageResource(R.mipmap.ic_launcher);
        }

        return convertView;
    }

    public static class ViewHolder {
        public PhotoGroupImageView group_image;
        public TextView group_title;
        public TextView group_count;
    }
}