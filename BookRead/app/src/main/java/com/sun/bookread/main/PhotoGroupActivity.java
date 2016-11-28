package com.sun.bookread.main;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.sun.bookread.R;
import com.sun.bookread.adapter.PhotoGroupAdapter;
import com.sun.bookread.base.BaseActivity;
import com.sun.bookread.bean.PhotoImageBean;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 相册
 */
public class PhotoGroupActivity extends BaseActivity {
    private HashMap<String, List<String>> mGruopMap = new HashMap<String, List<String>>();
    private List<PhotoImageBean> list = new ArrayList<PhotoImageBean>();
    private final static int SCAN_OK = 1;
    private ProgressDialog mProgressDialog;

    private GridView photogroup_gridview;
    private PhotoGroupAdapter adapter;
    private TextView image_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_photogroup);
        initView();
        getImages();
        setOnListener();
    }

    @Override
    public void onCreateCustomToolBar(Toolbar toolbar) {
        super.onCreateCustomToolBar(toolbar);
        toolbar.setTitleTextColor(Color.BLACK);
        toolbar.setTitle("相册");
    }

    public void initView() {
        photogroup_gridview = (GridView) findViewById(R.id.photogroup_gridview);
        image_num = (TextView) findViewById(R.id.image_num);
    }

    public void setOnListener() {
        photogroup_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                List<String> childList = mGruopMap.get(list.get(position).getFolderName());

                Intent mIntent = new Intent(PhotoGroupActivity.this, PhotoChildListActivity.class);
                mIntent.putStringArrayListExtra("data", (ArrayList<String>) childList);
                startActivity(mIntent);

            }
        });
    }

    /**
     * 图片扫描完成后更新UI
     */
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SCAN_OK:
                    //关闭进度条
                    mProgressDialog.dismiss();

                    adapter = new PhotoGroupAdapter(PhotoGroupActivity.this, list = subGroupOfImage(mGruopMap), photogroup_gridview);
                    photogroup_gridview.setAdapter(adapter);
                    break;
            }
        }
    };

    /**
     * 利用ContentProvider扫描手机中的图片，此方法在运行在子线程中
     */
    public void getImages() {
        //显示进度条
        mProgressDialog = ProgressDialog.show(this, null, "正在加载...");

        new Thread(new Runnable() {
            @Override
            public void run() {

                // 指定要查询的uri资源
                Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                // 获取ContentResolver
                ContentResolver contentResolver = PhotoGroupActivity.this.getContentResolver();

                // 查询的字段
                String[] projection = {MediaStore.Images.Media._ID,
                        MediaStore.Images.Media.DISPLAY_NAME,
                        MediaStore.Images.Media.DATA, MediaStore.Images.Media.SIZE};
                // 条件
                String selection = MediaStore.Images.Media.MIME_TYPE + "=? or "
                        + MediaStore.Images.Media.MIME_TYPE + "=?";
                //只查询jpeg和png的图片
                String[] selectionArgs = {"image/jpeg", "image/png"};
                // 排序
                String sortOrder = MediaStore.Images.Media.DATE_MODIFIED;

                // 查询sd卡上的图片
                Cursor cursor = contentResolver.query(uri, projection, selection,
                        selectionArgs, sortOrder);
                if (cursor == null) {
                    return;
                }

                while (cursor.moveToNext()) {
                    //获取图片的路径
                    String path = cursor.getString(cursor
                            .getColumnIndex(MediaStore.Images.Media.DATA));

                    //获取该图片的父路径名
                    String parentName = new File(path).getParentFile().getName();

                    //根据父路径名将图片放入到mGruopMap中
                    if (!mGruopMap.containsKey(parentName)) {
                        List<String> chileList = new ArrayList<String>();
                        chileList.add(path);
                        mGruopMap.put(parentName, chileList);
                    } else {
                        mGruopMap.get(parentName).add(path);
                    }
                }

                //通知Handler扫描图片完成
                mHandler.sendEmptyMessage(SCAN_OK);
                cursor.close();
            }
        }).start();
    }

    /**
     * 组装分组界面GridView的数据源，因为我们扫描手机的时候将图片信息放在HashMap中
     * 所以需要遍历HashMap将数据组装成List
     *
     * @param mGruopMap
     * @return
     */
    private List<PhotoImageBean> subGroupOfImage(HashMap<String, List<String>> mGruopMap) {
        if (mGruopMap.size() == 0) {
            return null;
        }
        List<PhotoImageBean> list = new ArrayList<PhotoImageBean>();

        Iterator<Map.Entry<String, List<String>>> it = mGruopMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, List<String>> entry = it.next();
            PhotoImageBean mImageBean = new PhotoImageBean();
            String key = entry.getKey();
            List<String> value = entry.getValue();

            mImageBean.setFolderName(key);
            mImageBean.setImageCounts(value.size());
            mImageBean.setTopImagePath(value.get(0));//获取该组的第一张图片

            list.add(mImageBean);
        }
        return list;
    }
}
