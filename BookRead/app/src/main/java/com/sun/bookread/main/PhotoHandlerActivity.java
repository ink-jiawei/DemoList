package com.sun.bookread.main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.widget.GridView;
import android.widget.ImageView;

import com.sun.bookread.R;
import com.sun.bookread.adapter.PhotoSelectAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片处理的Activity
 */
public class PhotoHandlerActivity extends Activity {
    private ImageView image;
    private final static int SCAN_OK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photohandler);
        initView();
    }

    public void initView() {
        image = (ImageView) findViewById(R.id.select_image);
    }

}