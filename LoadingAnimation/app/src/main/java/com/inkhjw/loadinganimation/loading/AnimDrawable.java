package com.inkhjw.loadinganimation.loading;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import java.util.ArrayList;

/**
 * @author hjw
 */

public abstract class AnimDrawable extends Drawable implements Animatable {
    private Paint paint;

    public AnimDrawable(Paint paint) {
        if (paint != null) {
            this.paint = paint;
        } else {
            this.paint = new Paint();
            this.paint.setColor(Color.WHITE);
            this.paint.setStyle(Paint.Style.FILL);
            this.paint.setAntiAlias(true);
        }
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        drawDrawable(canvas, this.paint);
    }

    /**
     * 绘制drawable
     *
     * @param canvas
     */
    abstract void drawDrawable(Canvas canvas, Paint paint);

    public abstract ArrayList<ValueAnimator> createAnimators();

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }

    /**
     * 通知drawable更新
     */
    public void invalidate() {
        invalidateSelf();
    }
}
