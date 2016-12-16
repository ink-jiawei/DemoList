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
 * @deprecated 三阶贝塞尔曲线
 */
public class BesselCurveThree extends View {
    private PointF point1;
    private PointF point2;
    private PointF pointControl1;
    private PointF pointControl2;

    Paint paint;


    public BesselCurveThree(Context context) {
        super(context);
        init();
    }

    public BesselCurveThree(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BesselCurveThree(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        point1 = new PointF(0, 0);
        point2 = new PointF(0, 0);
        pointControl1 = new PointF(0, 0);
        pointControl2 = new PointF(0, 0);

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

        canvas.drawText("控制点1", pointControl1.x, pointControl1.y, paint);
        canvas.drawText("控制点2", pointControl2.x, pointControl2.y, paint);

        paint.setColor(Color.YELLOW);
        canvas.drawLine(point1.x, point1.y, pointControl1.x, pointControl1.y, paint);
        canvas.drawLine(pointControl1.x, pointControl1.y, pointControl2.x, pointControl2.y, paint);
        canvas.drawLine(pointControl2.x, pointControl2.y, point2.x, point2.y, paint);

        paint.setColor(Color.BLUE);
        drawBesselCurve(canvas);
    }

    public void drawBesselCurve(Canvas canvas) {
        Path path = new Path();
        path.moveTo(point1.x, point1.y);

        path.cubicTo(pointControl1.x, pointControl1.y, pointControl2.x, pointControl2.y, point2.x, point2.y);
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

        pointControl1.x = centerX - 50;
        pointControl1.y = centerY + 200;

        pointControl2.x = centerX + 150;
        pointControl2.y = centerY + 100;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (pointControl1.x <= event.getX() + 50 &&
                pointControl1.x >= event.getX() - 50 &&
                pointControl1.y <= event.getY() + 50 &&
                pointControl1.y >= event.getY() - 50) {
            pointControl1.x = event.getX();
            pointControl1.y = event.getY();
        } else {
            pointControl2.x = event.getX();
            pointControl2.y = event.getY();
        }
        invalidate();
        return true;
    }
}
