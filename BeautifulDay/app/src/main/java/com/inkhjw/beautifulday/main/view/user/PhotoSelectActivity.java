package com.inkhjw.beautifulday.main.view.user;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.widget.GridView;

import com.inkhjw.beautifulday.R;
import com.inkhjw.beautifulday.adapter.PhotoSelectAdapter;
import com.inkhjw.beautifulday.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 图片选择的Activity
 */
public class PhotoSelectActivity extends BaseActivity {
    @Bind(R.id.photochild_gridview)
    GridView photochild_gridview;

    private List<String> list = new ArrayList<>();
    private PhotoSelectAdapter adapter;

    private ProgressDialog mProgressDialog;
    private final static int SCAN_OK = 1;
    public final static int SELECT_OK = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photochild);
        getImages();
    }

    @Override
    public void initView() {
        super.initView();
        initToolbar(false, "照片选择", 0);
    }

    @Override
    protected void initData() {
        super.initData();
    }

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
                ContentResolver contentResolver = PhotoSelectActivity.this.getContentResolver();

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
                    list.add(path);
                }

                //通知Handler扫描图片完成
                mHandler.sendEmptyMessage(SCAN_OK);
                cursor.close();
            }
        }).start();
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
                    adapter = new PhotoSelectAdapter(mHandler, PhotoSelectActivity.this, list, photochild_gridview);
                    photochild_gridview.setAdapter(adapter);
                    break;

                case SELECT_OK:
                    String path = (String) msg.obj;
                    Intent intent = getIntent();
                    intent.putExtra("picture", path);
                    setResult(FragmentUser.PHOTO_RESULT, intent);
                    finish();
                    break;
            }
        }
    };
}