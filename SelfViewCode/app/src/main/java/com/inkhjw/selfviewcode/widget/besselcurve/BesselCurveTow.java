package com.inkhjw.selfviewcode.widget.besselcurve;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author 何佳伟
 * @deprecated 二阶贝塞尔曲线
 */
public class BesselCurveTow extends View {
    private PointF point1;
    private PointF point2;
    private PointF pointControl;

    Paint paint;


    public BesselCurveTow(Context context) {
        super(context);
        init();
    }

    public BesselCurveTow(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BesselCurveTow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        point1 = new PointF(0, 0);
        point2 = new PointF(0, 0);
        pointControl = new PointF(0, 0);

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(8);
        paint.setStyle(Paint.Style.STROKE);
        paint.setTextSize(60);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.GRAY);

        paint.setColor(Color.RED);
        canvas.drawPoint(point1.x, point1.y, paint);
        canvas.drawPoint(point2.x, point2.y, paint);

        paint.setColor(Color.YELLOW);
        canvas.drawLine(point1.x, point1.y, pointControl.x, pointControl.y, paint);
        canvas.drawLine(point2.x, point2.y, pointControl.x, pointControl.y, paint);

        paint.setColor(Color.BLUE);
        drawBesselCurve(canvas);
    }

    public void drawBesselCurve(Canvas canvas) {
        Path path = new Path();
        path.moveTo(point1.x, point1.y);

        path.quadTo(pointControl.x, pointControl.y, point2.x, point2.y);
        canvas.drawPath(path, paint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int centerX = w / 2;
        int centerY = h / 2;

        point1.x = centerX - 100;
        point1.y = centerY;

        point2.x = centerX + 100;
        point2.y = centerY;

        pointControl.x = centerX;
        pointControl.y = centerY + 200;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        pointControl.x = event.getX();
        pointControl.y = event.getY();
        invalidate();
        return true;
    }
}
