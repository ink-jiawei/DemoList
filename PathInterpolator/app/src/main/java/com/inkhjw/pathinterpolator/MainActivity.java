package com.inkhjw.pathinterpolator;

import android.animation.ObjectAnimator;
import android.graphics.Path;
import android.os.Bundle;
import android.support.v4.view.animation.PathInterpolatorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = (TextView) findViewById(R.id.tv);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pathInterpolator(v);
            }
        });
    }

    private void pathInterpolator(View view) {
        Path path = new Path();
        path.cubicTo(0.2f, 0f, 0.1f, 1f, 0.5f, 1f);
        path.lineTo(1f, 1f);

        ObjectAnimator animator = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, 500);
        animator.setInterpolator(PathInterpolatorCompat.create(path));
        animator.setDuration(10000);
        animator.start();

        spathInterpolator(view);
    }

    private void spathInterpolator(View view) {
        Path path = new Path();
        path.cubicTo(0.2f, 0f, 0.1f, 1f, 0.5f, 1f);
        path.lineTo(1f, 1f);

        ObjectAnimator animator = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, -500);
        animator.setInterpolator(PathInterpolatorCompat.create(path));
        animator.start();
    }
}
