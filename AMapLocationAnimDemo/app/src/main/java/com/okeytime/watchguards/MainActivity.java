package com.okeytime.watchguards;

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

public class MainActivity extends AppCompatActivity implements AMap.OnMapLoadedListener {
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
        setContentView(R.layout.activity_main);
        init();
        map_view.onCreate(savedInstanceState);

        startAnim();
    }

    protected void init() {
        map_view = (MapView) findViewById(R.id.map_view);
        map = map_view.getMap();
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
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher))
                .position(targetLatlng));

        animCount = 0;
        startLocationAnim(targetLatlng);
    }

    private int animCount = 0;

    private void startLocationAnim(final LatLng targetLatlng) {
        if (++animCount <= 3) {
            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(
                    new CameraPosition(targetLatlng, (animCount * 3) + 3, 0, 0));
            map.animateCamera(cameraUpdate, 1000 * 8 * (4 - animCount),
                    new AMap.CancelableCallback() {
                        @Override
                        public void onFinish() {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        startRectAnim(targetLatlng);
                                        playAudio();
                                        Log.e("debug", getClass().getName() + "第" + animCount + "次定位动画完成!");
                                        Thread.sleep(2000);
                                        startLocationAnim(targetLatlng);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                        }

                        @Override
                        public void onCancel() {
                            Log.e("debug", getClass().getName() + "第" + animCount + "次定位动画取消!");
                        }
                    });
        }
    }

    public void startRectAnim(LatLng targetLatlng) {
        if (projection != null) {
            Point point = projection.toScreenLocation(targetLatlng);
            ObjectAnimator animator = ObjectAnimator.ofFloat(scanning_view, "translationX", -100, point.x);
            animator.setDuration(1500);
            animator.start();
            Log.e("debug", "startRectAnim");
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
    }

    class AudioTrackThread extends Thread {

        @Override
        public void run() {
            byte[] out_bytes = new byte[44100];
            InputStream is = getResources().openRawResource(R.raw.ding2);

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
