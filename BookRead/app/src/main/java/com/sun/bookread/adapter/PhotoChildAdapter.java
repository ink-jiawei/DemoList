package com.sun.bookread.adapter;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;

import com.sun.bookread.R;
import com.sun.bookread.main.PhotoChildListActivity;
import com.sun.bookread.utils.NativeImageLoader;
import com.sun.bookread.view.PhotoGroupImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PhotoChildAdapter extends BaseAdapter {
    private Point mPoint = new Point(0, 0);//用来封装ImageView的宽和高的对象
    /**
     * 用来存储图片的选中情况
     */
    private HashMap<Integer, Boolean> mSelectMap = new HashMap<Integer, Boolean>();
    private GridView mGridView;
    private List<String> list;
    private Context context;
    PhotoChildListActivity activity;
    /**
     * 用来存储所有的CheckBox的显示状态
     */
    private HashMap<Integer, Boolean> isShowCheckBox;

    // 声明PopupWindow对象的引用
    private PopupWindow popupWindow;

    public PhotoChildAdapter(PhotoChildListActivity activity, Context context, List<String> list, GridView mGridView) {
        this.list = list;
        this.mGridView = mGridView;
        this.context = context;
        this.activity = activity;
        isShowCheckBox = new HashMap<>();
        //checkBox全部设置为隐藏
        for (int i = 0; i < list.size(); i++) {
            isShowCheckBox.put(i, false);
        }
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
            viewHolder.mCheckBox = (CheckBox) convertView.findViewById(R.id.child_checkbox);

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
        viewHolder.mImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mSelectMap.put(position, true);
                v.setSelected(true);
                for (int i = 0; i < isShowCheckBox.size(); i++) {
                    isShowCheckBox.put(i, true);
                }
                PhotoChildAdapter.this.notifyDataSetChanged();
                pPopWindow();
                return true;
            }
        });

        viewHolder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //如果是未选中的CheckBox,则添加动画
                if (!mSelectMap.containsKey(position) || !mSelectMap.get(position)) {
                    addAnimation(viewHolder.mCheckBox);
                }
                mSelectMap.put(position, isChecked);
            }
        });
        //判断是否显示全部的CheckBox
        if (isShowCheckBox.containsKey(position) && isShowCheckBox.get(position)) {
            viewHolder.mCheckBox.setVisibility(View.VISIBLE);
        }
        viewHolder.mCheckBox.setChecked(mSelectMap.containsKey(position) ? mSelectMap.get(position) : false);

        //利用NativeImageLoader类加载本地图片
        Bitmap bitmap = NativeImageLoader.getInstance().loadNativeImage(path, mPoint, new NativeImageLoader.NativeImageCallBack() {

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

        return convertView;
    }

    /**
     * 给CheckBox加点击动画，利用开源库nineoldandroids设置动画
     *
     * @param view
     */
    private void addAnimation(View view) {
        float[] vaules = new float[]{0.5f, 0.6f, 0.7f, 0.8f, 0.9f, 1.0f, 1.1f, 1.2f, 1.3f, 1.25f, 1.2f, 1.15f, 1.1f, 1.0f};
        AnimatorSet set = new AnimatorSet();
        set.playTogether(ObjectAnimator.ofFloat(view, "scaleX", vaules),
                ObjectAnimator.ofFloat(view, "scaleY", vaules));
        set.setDuration(150);
        set.start();
    }


    /**
     * 获取选中的Item的position
     *
     * @return
     */
    public List<Integer> getSelectItems() {
        List<Integer> list = new ArrayList<Integer>();
        for (Iterator<Map.Entry<Integer, Boolean>> it = mSelectMap.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<Integer, Boolean> entry = it.next();
            if (entry.getValue()) {
                list.add(entry.getKey());
            }
        }

        return list;
    }

    public static class ViewHolder {
        public PhotoGroupImageView mImageView;
        public CheckBox mCheckBox;
    }

    /**
     * 弹出地图PopWindow
     */
    public void pPopWindow() {
        if (null != popupWindow) {
            popupWindow.dismiss();
            return;
        } else {
            initPopuptWindow();
        }
        // 这里是位置显示方式,在屏幕的左侧
        popupWindow.showAtLocation(mGridView, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 创建PopupWindow
     */
    protected void initPopuptWindow() {
        // TODO Auto-generated method stub
        // 获取自定义布局文件activity_popupwindow_left.xml的视图
        View popupWindow_view = LayoutInflater.from(context).inflate(R.layout.activity_photo_popwindow, null,
                false);
        // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
        popupWindow = new PopupWindow(popupWindow_view, RadioGroup.LayoutParams.MATCH_PARENT, activity.getScreenH() / 10, true);
        // 设置动画效果
        popupWindow.setAnimationStyle(R.style.AnimationFade);
        // 点击其他地方消失
        popupWindow_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }
                return false;
            }
        });
    }
}