package com.inkhjw.beautifulday.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;

import com.inkhjw.beautifulday.R;
import com.inkhjw.beautifulday.main.view.user.PhotoSelectActivity;
import com.inkhjw.beautifulday.utils.NativeImageLoader;
import com.inkhjw.beautifulday.widget.PhotoGroupImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

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
            viewHolder = new ViewHolder(convertView);

            //用来监听ImageView的宽和高
            viewHolder.childImage.setOnMeasureListener(new PhotoGroupImageView.OnMeasureListener() {

                @Override
                public void onMeasureSize(int width, int height) {
                    mPoint.set(width, height);
                }
            });

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.childImage.setImageResource(R.mipmap.ic_launcher);
        }

        final String path = list.get(position);
        viewHolder.childImage.setTag(path);

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
            viewHolder.childImage.setImageBitmap(bitmap);
        } else {
            viewHolder.childImage.setImageResource(R.mipmap.ic_launcher);
        }
        viewHolder.childImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = handler.obtainMessage();
                msg.obj = path;
                msg.what = PhotoSelectActivity.SELECT_OK;
                handler.sendMessage(msg);
            }
        });
        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.child_image)
        PhotoGroupImageView childImage;
        @Bind(R.id.child_checkbox)
        CheckBox childCheckbox;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}