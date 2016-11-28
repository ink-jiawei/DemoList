package com.inkhjw.bitmapdisplaydemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.inkhjw.bitmapdisplaydemo.ImageLoader.ImageCacheManager;

/**
 * @author hjw
 * @deprecated 图片详情界面
 */
public class ImageDetailActivity extends AppCompatActivity {
    private ImageCacheManager cacheManager;
    private RequestQueue queue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView imageView = new ImageView(this);
        setContentView(imageView);

        cacheManager = ImageCacheManager.getInstance();
        queue = Volley.newRequestQueue(this);
        cacheManager.addRequestQueue(queue);
        String imageUrl = getIntent().getStringExtra("imageUrl");
        cacheManager.loaderImage(imageUrl, imageView, R.mipmap.icon_stub, R.mipmap.icon_error);
    }
}
