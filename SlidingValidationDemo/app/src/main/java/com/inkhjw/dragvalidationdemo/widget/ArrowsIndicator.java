package com.inkhjw.dragvalidationdemo.widget;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Jack on 2015/10/16.
 */
public class  ArrowsIndicator extends Indicator {

    public static final int ALPHA = 255;

    int[] alphas = new int[]{ALPHA,
            ALPHA};

    @Override
    public void draw(Canvas canvas, Paint paint) {
        float spacing = 15;
        float length = 30;
        float centerX = getWidth() / 2;
        float centerY = getHeight() / 2;
        float cell = (float) Math.sqrt(length * length / 2);
        Log.e("debug", "cell=" + cell);

        //一个箭头从上到下三个点
        float x1 = centerX - spacing / 2 - cell;
        float y1 = centerY - cell;
        float x2 = centerX - spacing / 2;
        float y2 = centerY;
        float x3 = centerX - spacing / 2 - cell;
        float y3 = centerY + cell;

        for (int i = 0; i < 2; i++) {
            paint.setAlpha(alphas[i]);
            canvas.drawLine(x1 + spacing * i, y1, x2 + spacing * i, y2, paint);
            canvas.drawLine(x2 + spacing * i, y2, x3 + spacing * i, y3, paint);
        }
    }

    @Override
    public ArrayList<ValueAnimator> onCreateAnimators() {
        ArrayList<ValueAnimator> animators = new ArrayList<>();
        int[] delays = new int[]{350, 0};
        for (int i = 0; i < 2; i++) {
            final int index = i;

            ValueAnimator alphaAnim = ValueAnimator.ofInt(255, 51, 255);
            alphaAnim.setDuration(1000);
            alphaAnim.setRepeatCount(-1);
            alphaAnim.setStartDelay(delays[i]);
            addUpdateListener(alphaAnim, new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    alphas[index] = (int) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
            animators.add(alphaAnim);
        }
        return animators;
    }


}
