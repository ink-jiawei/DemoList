package com.hjw.testapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ImageView image_view;
    private TextView text_view;
    /**
     * 图片地址
     */
    public static final String IMAGE_URL = "http://ww4.sinaimg.cn/mw1024/0067uAQzjw1euffz8xagnj30hs0cd753.jpg";
    static final String HTTP_URL = "http://115.159.186.48/zhiyouhuifuApp/articleList";

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    int i = 0;

    public void init() {
        requestQueue = Volley.newRequestQueue(this);
        image_view = (ImageView) findViewById(R.id.image_view);
        image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });

        text_view = (TextView) findViewById(R.id.text_view);
        getData();
        getStringData();
    }

    public void getData() {

        ImageRequest request = new ImageRequest(IMAGE_URL, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                image_view.setImageBitmap(bitmap);
            }
        }, 0, 0, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                image_view.setImageResource(R.mipmap.ic_launcher);
            }
        });
        requestQueue.add(request);
    }

    public void getStringData() {

        MyStringRequest request = new MyStringRequest(Request.Method.POST, HTTP_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                text_view.setText(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                text_view.setText(volleyError.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("type", "1");
                map.put("page", "1");
                return map;
            }
        };
        requestQueue.add(request);
    }
}
