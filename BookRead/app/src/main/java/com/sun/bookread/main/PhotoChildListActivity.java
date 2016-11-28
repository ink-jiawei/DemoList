package com.sun.bookread.main;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.widget.GridView;
import android.widget.Toast;

import com.sun.bookread.R;
import com.sun.bookread.adapter.PhotoChildAdapter;

import java.util.List;

/**
 * 相册中显示图片列表的Activity
 */
public class PhotoChildListActivity extends Activity {
    private GridView mGridView;
    private List<String> list;
    private PhotoChildAdapter adapter;

    int downX = 0;
    int downY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photochild);
        initView();
        init();
    }

    public void initView() {
        mGridView = (GridView) findViewById(R.id.photochild_gridview);
    }

    public void init() {
        list = getIntent().getStringArrayListExtra("data");
        adapter = new PhotoChildAdapter(this, this, list, mGridView);
        mGridView.setAdapter(adapter);
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