package com.inkhjw.beautifulday.main.view.user;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.widget.GridView;
import android.widget.Toast;

import com.inkhjw.beautifulday.R;
import com.inkhjw.beautifulday.adapter.PhotoChildAdapter;
import com.inkhjw.beautifulday.base.BaseActivity;

import java.util.List;

import butterknife.Bind;

/**
 * 相册中显示图片列表的Activity
 */
public class PhotoChildListActivity extends BaseActivity {
    @Bind(R.id.photochild_gridview)
    GridView photochild_gridview;

    private List<String> list;
    private PhotoChildAdapter adapter;

    int downX = 0;
    int downY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photochild);
    }

    @Override
    public void initView() {
        super.initView();
        initToolbar(false, "照片列表", 0);
    }

    @Override
    protected void initData() {
        super.initData();
        list = getIntent().getStringArrayListExtra("data");
        adapter = new PhotoChildAdapter(this, this, list, photochild_gridview);
        photochild_gridview.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "选中 " + adapter.getSelectItems().size() + " item", Toast.LENGTH_LONG).show();
        super.onBackPressed();
    }

    /**
     * 触摸事件,处理上滑弹出底部PopWindow
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int curX;
        int curY;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getX();
                downY = (int) event.getY();

                break;

            case MotionEvent.ACTION_UP:
                curX = (int) event.getX();
                curY = (int) event.getY();

                if (downY - curY > 100 && Math.abs(downX - curX) < 40) {

                }
                break;
        }

        return super.onTouchEvent(event);
    }

    /**
     * 获得频幕的宽度
     */
    public int getScreenW() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    /**
     * 获得频幕的宽度
     */
    public int getScreenH() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }
}