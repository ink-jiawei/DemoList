package com.inkhjw.selfviewcode.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Toast;

/**
 * @author hjw
 * @deprecated 速度检测器
 */
public class VelocityTrackerView extends View {
    Paint paint;
    int width;
    int height;
    /**
     * 添加速度检测
     */
    VelocityTracker velocityTracker;

    public VelocityTrackerView(Context context) {
        super(context);
        init();
    }

    public VelocityTrackerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VelocityTrackerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        width = getMeasuredWidth();
        height = getMeasuredHeight();
        canvas.drawRect(0, 0, width, height, paint);
        velocityTracker = VelocityTracker.obtain();
    }


    int downX = 0;
    int downY = 0;
    int curX = 0;
    int curY = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        /**
         * 添加速度检测
         */
        velocityTracker.addMovement(event);

        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = x;
                downY = y;

                width = (int) event.getRawX();
                height = (int) event.getRawY();
                Log.e("test", "downX" + downX + "/" + downY + " width=" + width + "   height=" + height);

                break;

            case MotionEvent.ACTION_MOVE:

                break;

            case MotionEvent.ACTION_UP:
                //计算当前速度:1000ms内滑动的像素值
                velocityTracker.computeCurrentVelocity(1000);
                float xVelocity = velocityTracker.getXVelocity();
                float yVelocity = velocityTracker.getYVelocity();

                curX = x;
                curY = y;
                Log.e("test", "downX=" + downX + "  curX=" + curX);
                Log.e("test", "downY=" + downY + "  curY=" + curY);

                /**
                 * 滑动有效的最小距离
                 */
                int smallDistance = ViewConfiguration.get(getContext()).getScaledTouchSlop();
                if (Math.abs(curX - downX) > smallDistance || Math.abs(curY - downY) > smallDistance) {

                    Toast.makeText(getContext(), "最小滑动有效距离=" + smallDistance + "    xVelocity=" + xVelocity + "   yVelocity=" + yVelocity, Toast.LENGTH_SHORT).show();
                }

                velocityTracker.clear();
                break;
        }
        return true;
    }
}
