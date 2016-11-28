package com.inkhjw.beautifulday.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.inkhjw.beautifulday.R;
import com.inkhjw.beautifulday.bean.PhotoImageBean;
import com.inkhjw.beautifulday.utils.NativeImageLoader;
import com.inkhjw.beautifulday.widget.PhotoGroupImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

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
            convertView = mInflater.inflate(R.layout.photogroup_gridview_item, null);
            viewHolder = new ViewHolder(convertView);

            //用来监听ImageView的宽和高  
            viewHolder.groupImage.setOnMeasureListener(new PhotoGroupImageView.OnMeasureListener() {

                @Override
                public void onMeasureSize(int width, int height) {
                    mPoint.set(width, height);
                }
            });

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.groupImage.setImageResource(R.mipmap.ic_launcher);
        }

        viewHolder.groupTitle.setText(mImageBean.getFolderName());
        viewHolder.groupCount.setText(Integer.toString(mImageBean.getImageCounts()));
        //给ImageView设置路径Tag,这是异步加载图片的小技巧  
        viewHolder.groupImage.setTag(path);

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
            viewHolder.groupImage.setImageBitmap(bitmap);
        } else {
            viewHolder.groupImage.setImageResource(R.mipmap.ic_launcher);
        }

        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.group_image)
        PhotoGroupImageView groupImage;
        @Bind(R.id.group_count)
        TextView groupCount;
        @Bind(R.id.framelayout)
        FrameLayout framelayout;
        @Bind(R.id.group_title)
        TextView groupTitle;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}