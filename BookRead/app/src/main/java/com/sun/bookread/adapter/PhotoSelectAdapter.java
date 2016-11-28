package com.sun.bookread.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.sun.bookread.R;
import com.sun.bookread.main.PhotoSelectActivity;
import com.sun.bookread.utils.NativeImageLoader;
import com.sun.bookread.view.PhotoGroupImageView;

import java.util.List;

/**
 * 图片选择适配器
 */
public class PhotoSelectAdapter extends BaseAdapter {
    private Point mPoint = new Point(0, 0);//用来封装ImageView的宽和高的对象
    private GridView mGridView;
    private List<String> list;
    private Context context;
    private Handler handler;


    public PhotoSelectAdapter(Handler handler, Context context, List<String> list, GridView mGridView) {
        this.list = list;
        this.mGridView = mGridView;
        this.context = context;
        this.handler = handler;
    }

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

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.photochild_gridview_item, null);
            viewHolder = new ViewHolder();
            viewHolder.mImageView = (PhotoGroupImageView) convertView.findViewById(R.id.child_image);

            //用来监听ImageView的宽和高
            viewHolder.mImageView.setOnMeasureListener(new PhotoGroupImageView.OnMeasureListener() {

                @Override
                public void onMeasureSize(int width, int height) {
                    mPoint.set(width, height);
                }
            });

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.mImageView.setImageResource(R.mipmap.ic_launcher);
        }

        String path = list.get(position);
        viewHolder.mImageView.setTag(path);

        //利用NativeImageLoader类加载本地图片
        final Bitmap bitmap = NativeImageLoader.getInstance().loadNativeImage(path, mPoint, new NativeImageLoader.NativeImageCallBack() {

            @Override
            public void onImageLoader(Bitmap bitmap, String path) {
                ImageView mImageView = (ImageView) mGridView.findViewWithTag(path);
                if (bitmap != null && mImageView != null) {
                    mImageView.setImageBitmap(bitmap);
                }
            }
        });

        if (bitmap != null) {
            viewHolder.mImageView.setImageBitmap(bitmap);
        } else {
            viewHolder.mImageView.setImageResource(R.mipmap.ic_launcher);
        }
        viewHolder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = handler.obtainMessage();
                msg.obj = bitmap;
                msg.what = PhotoSelectActivity.SELECT_OK;
                handler.sendMessage(msg);
                Toast.makeText(context, bitmap.getByteCount() / 1024 + "kb", Toast.LENGTH_LONG).show();
            }
        });
        return convertView;
    }

    public static class ViewHolder {
        public PhotoGroupImageView mImageView;
    }
}