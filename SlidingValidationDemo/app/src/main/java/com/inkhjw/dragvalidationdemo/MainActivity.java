package com.inkhjw.dragvalidationdemo;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.inkhjw.dragvalidationdemo.widget.DragValidationView;
import com.inkhjw.dragvalidationdemo.widget.DragView;

/**
 * 滑动验证效果
 */
public class MainActivity extends AppCompatActivity {
    private DragValidationView drag_view;
    private DragView drag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drag_view = (DragValidationView) findViewById(R.id.drag_view);
        drag_view.setDragValidationListener(new DragValidationView.DragValidationListener() {
            @Override
            public void dragBefore() {
                Log.e("debug", "dragBefore");
            }

            @Override
            public void dragProcess(float dragLength) {
                //Log.e("debug", "dragProcess:" + dragLength);
            }

            @Override
            public void dragFinish(boolean success) {
                Log.e("debug", "dragFinish:" + success);
                if (success) {
                    drag_view.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            drag_view.setValidationFinish();
                        }
                    }, 3000);
                }
            }
        });

        drag = (DragView) findViewById(R.id.drag);
        ObjectAnimator animator = ObjectAnimator.ofFloat(drag, "rotation", 0f, 360f);
        animator.setDuration(5000);
        animator.start();
        drag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(drag, "rotation", 0f, 360f);
                animator.setDuration(5000);
                animator.start();
            }
        });
    }
}
