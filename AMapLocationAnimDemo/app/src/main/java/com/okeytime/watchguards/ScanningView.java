package com.okeytime.watchguards;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author hjw
 */

public class ScanningView extends View {
    private Paint paint;
    private int width;
    private int height;

    public ScanningView(Context context) {
        super(context);
        init();
    }

    public ScanningView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ScanningView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawRect(canvas);
        drawRect2(canvas);
    }

    protected void drawRect(Canvas canvas) {
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);
        canvas.drawRect(getPaddingLeft(), getPaddingTop(), width + getPaddingLeft(), height + getPaddingTop(), paint);
    }

    protected void drawRect2(Canvas canvas) {
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(20);
        canvas.save();
        for (int i = 0; i < 4; i++) {
            canvas.drawLine(0, 0, 0, 30, paint);
            canvas.drawLine(0, 0, 30, 0, paint);
            canvas.rotate(90, width / 2, height / 2);
        }
        canvas.restoreToCount(canvas.getSaveCount());
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
    }

    public int getWidthT() {
        return width == 0 ? 200 : width;
    }

    public synchronized void setWidthT(int width) {
        this.width = width;
        postInvalidate();
    }

    public int getHeightT() {
        return height;
    }

    public synchronized void setHeightT(int height) {
        this.height = height;
        postInvalidate();
    }
}
