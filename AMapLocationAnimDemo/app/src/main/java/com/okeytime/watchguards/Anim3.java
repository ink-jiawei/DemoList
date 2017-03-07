package com.okeytime.watchguards;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Point;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.Projection;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;

import java.io.IOException;
import java.io.InputStream;

public class Anim3 extends AppCompatActivity implements AMap.OnMapLoadedListener {
    private MapView map_view;
    private ScanningView scanning_view;
    private Button start_anim_btn;

    private AMap map;
    private Projection projection;

    //播放音频相关
    private AudioTrack at;
    private AudioManager am;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anim3);
        init();
        map_view.onCreate(savedInstanceState);
    }

    protected void init() {
        map_view = (MapView) findViewById(R.id.map_view);
        map = map_view.getMap();
        map.setOnMapLoadedListener(this);
        scanning_view = (ScanningView) findViewById(R.id.scanning_view);
        start_anim_btn = (Button) findViewById(R.id.start_anim_btn);
        start_anim_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnim();
            }
        });

        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
    }

    public void startAnim() {
        final LatLng targetLatlng = new LatLng(39.977290, 116.337000);
        CameraUpdate mCameraUpdate = CameraUpdateFactory.newCameraPosition(
                new CameraPosition(targetLatlng, 1, 0, 0));
        map.moveCamera(mCameraUpdate); //设置初始地图状态

        //清除覆盖物
        map.clear();
        map.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.rail_circle_center_point))
                .position(targetLatlng));

        startLocationAnim(targetLatlng);
    }

    private void startLocationAnim(final LatLng targetLatlng) {
        animTime = 0;
        startRectAnim(targetLatlng);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(
                new CameraPosition(targetLatlng, 15, 0, 0));
        map.animateCamera(cameraUpdate, 1000 * 30,
                new AMap.CancelableCallback() {
                    @Override
                    public void onFinish() {
                        Log.e("debug", getClass().getName() + "定位动画完成!");
                    }

                    @Override
                    public void onCancel() {
                        Log.e("debug", getClass().getName() + "定位动画取消!");
                    }
                });
    }

    private int animTime = 0;//动画时间

    public void startRectAnim(final LatLng targetLatlng) {
        if (projection != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    animTime += 1 * 1000;
                    Point point = projection.toScreenLocation(targetLatlng);
                    Log.e("debug", "startRectAnim");

                    int curWidth = scanning_view.getWidthT();
                    int width = getScreenWidth();
//                    scanning_view.setWidthT(width);
//                    scanning_view.setHeightT(height);
                    float scaleSize = (float) (width / 2) / (float) curWidth;
                    Log.e("debug", "scaleSize=" + scaleSize);
                    scanning_view.setScaleX(scaleSize);
                    scanning_view.setScaleY(scaleSize);

                    scanning_view.setVisibility(View.VISIBLE);
                    //ObjectAnimator animatorX = ObjectAnimator.ofFloat(scanning_view, "X", (float) point.x - (float) width / 2);
                    //ObjectAnimator animatorY = ObjectAnimator.ofFloat(scanning_view, "Y", (float) point.y - (float) height / 2);
                    ObjectAnimator animatorScaleX = ObjectAnimator.ofFloat(scanning_view, "ScaleX", 1);
                    ObjectAnimator animatorScaleY = ObjectAnimator.ofFloat(scanning_view, "ScaleY", 1);

                    AnimatorSet set = new AnimatorSet();
                    set.play(animatorScaleX).with(animatorScaleY);
                    set.setDuration(500);
                    set.start();
                    set.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            playAudio();
                            scanning_view.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    scanning_view.setVisibility(View.GONE);
                                    if (animTime > 0 && animTime <= 30 * 1000) {
                                        scanning_view.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                startRectAnim(targetLatlng);
                                            }
                                        }, 300);
                                    }
                                }
                            }, 200);
                            Log.e("debug", "onAnimationEnd");
                        }
                    });
                }
            });
        }
    }

    public void playAudio() {
        if (am.isSpeakerphoneOn()) {
            am.setSpeakerphoneOn(false);
        }
        setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);
        am.setMode(AudioManager.MODE_IN_CALL);
        System.out.println(am.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL));
        System.out.println("&&&&&&&&&&&&&");
        System.out.println(am.getStreamVolume(AudioManager.STREAM_VOICE_CALL));

        int bufferSizeInBytes = AudioTrack.getMinBufferSize(44100, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);
        if (at == null) {
            at = new AudioTrack(AudioManager.STREAM_VOICE_CALL, 44100, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT, bufferSizeInBytes, AudioTrack.MODE_STREAM);
            System.out.println("22222");
            new AudioTrackThread().start();
        } else {
            if (at.getPlayState() == AudioTrack.PLAYSTATE_PLAYING) {
                System.out.println("111111111");
            } else {
                System.out.println("33333");
                at = new AudioTrack(AudioManager.STREAM_VOICE_CALL, 44100, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT, bufferSizeInBytes, AudioTrack.MODE_STREAM);
                new AudioTrackThread().start();
            }
        }
    }

    @Override
    public void onMapLoaded() {
        projection = map.getProjection();
        startAnim();
    }

    class AudioTrackThread extends Thread {

        @Override
        public void run() {
            byte[] out_bytes = new byte[44100];
            InputStream is = getResources().openRawResource(R.raw.ding);

            int length;
            at.play();
            try {
                while ((length = is.read(out_bytes)) != -1) {
                    System.out.println(length);
                    at.write(out_bytes, 0, length);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (at.getPlayState() == AudioTrack.PLAYSTATE_PLAYING) {
                at.stop();
                at.release();
                am.setMode(AudioManager.MODE_NORMAL);
            }
        }

    }

    public int getScreenHeight() {
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        int height = windowManager.getDefaultDisplay().getHeight();
        return height;
//        DisplayMetrics metrics = new DisplayMetrics();
//        getWindow().getWindowManager().getDefaultDisplay().getMetrics(metrics);
//        int height = metrics.heightPixels;
//        int width =  metrics.widthPixels;
    }

    public int getScreenWidth() {
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        int width = windowManager.getDefaultDisplay().getWidth();
        return width;
//        DisplayMetrics metrics = new DisplayMetrics();
//        getWindow().getWindowManager().getDefaultDisplay().getMetrics(metrics);
//        int height = metrics.heightPixels;
//        int width =  metrics.widthPixels;
    }

    @Override
    protected void onResume() {
        super.onResume();
        map_view.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        map_view.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        map_view.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        map_view.onSaveInstanceState(outState);
    }
}
