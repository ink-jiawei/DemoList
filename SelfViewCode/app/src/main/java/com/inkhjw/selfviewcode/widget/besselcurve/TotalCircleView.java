package com.inkhjw.selfviewcode.widget.besselcurve;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * @author 何佳伟
 * @deprecated 利用贝塞尔曲线，实现弹性圆的view
 */
public class TotalCircleView extends View {
    Paint paint;

    //中心点
    private PointF centerP;

    //基本点
    private PointF pointLeft;
    private PointF pointTop;
    private PointF pointRight;
    private PointF pointBottom;

    //控制点
    private PointF point1;
    private PointF point2;
    private PointF point3;
    private PointF point4;
    private PointF point5;
    private PointF point6;
    private PointF point7;
    private PointF point8;

    //圆的属性
    private PointF circleCenterPoint;
    private float r = 50;//半径
    private static final float C = 0.551915024494f;//用于计算圆中贝塞尔曲线控制点的位置(计算得出)
    private static final float TOTAL_MOVE = 40;//圆拉伸的距离
    private float moveLength = 0;//当前圆拉伸的距离

    public TotalCircleView(Context context) {
        super(context);
        init();
    }

    public TotalCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TotalCircleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor("#f15b6c"));
        paint.setStyle(Paint.Style.FILL);

        pointLeft = new PointF(0, 0);
        pointTop = new PointF(0, 0);
        pointRight = new PointF(0, 0);
        pointBottom = new PointF(0, 0);

        point1 = new PointF(0, 0);
        point2 = new PointF(0, 0);
        point3 = new PointF(0, 0);
        point4 = new PointF(0, 0);
        point5 = new PointF(0, 0);
        point6 = new PointF(0, 0);
        point7 = new PointF(0, 0);
        point8 = new PointF(0, 0);

        centerP = new PointF(0, 0);
        circleCenterPoint = new PointF(0, 0);
    }

    public void initHeart() {
        pointLeft.x = 0;
        pointLeft.y = r;

        pointTop.x = r;
        pointTop.y = 0;

        pointRight.x = r * 2;
        pointRight.y = r;

        pointBottom.x = r;
        pointBottom.y = r * 2;

        point1.x = pointLeft.x;
        point1.y = pointLeft.y - C * r;

        point2.x = pointTop.x - C * r;
        point2.y = pointTop.y;

        point3.x = pointTop.x + C * r;
        point3.y = pointTop.y;

        point4.x = pointRight.x;
        point4.y = pointRight.y - C * r;

        point5.x = pointRight.x;
        point5.y = pointRight.y + C * r;

        point6.x = pointBottom.x + C * r;
        point6.y = pointBottom.y;

        point7.x = pointBottom.x - C * r;
        point7.y = pointBottom.y;

        point8.x = pointLeft.x;
        point8.y = pointLeft.y + C * r;

        circleCenterPoint.x = pointTop.x;
        circleCenterPoint.y = pointTop.y + r;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerP.x = w / 2;
        centerP.y = h / 2;
        initHeart();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        drawHeartShape(canvas);
    }

    boolean isFirst = true;

    public void drawHeartShape(Canvas canvas) {
        Path path = new Path();
        path.moveTo(pointLeft.x, pointLeft.y);
        path.cubicTo(point1.x, point1.y, point2.x, point2.y, pointTop.x, pointTop.y);
        path.cubicTo(point3.x, point3.y, point4.x, point4.y, pointRight.x, pointRight.y);
        path.cubicTo(point5.x, point5.y, point6.x, point6.y, pointBottom.x, pointBottom.y);
        path.cubicTo(point7.x, point7.y, point8.x, point8.y, pointLeft.x, pointLeft.y);
        canvas.drawPath(path, paint);
        if (isFirst) {
            isFirst = false;
            addRight();
        }
    }

    @Override
    public void postInvalidate() {
        super.postInvalidate();
    }

    public void addLeft() {
        for (int i = 0; i < 100; i++) {
            if (pointLeft.x >= circleCenterPoint.x - r - TOTAL_MOVE) {
                pointLeft.x -= TOTAL_MOVE / 100;
                postInvalidateDelayed(10);
                Log.e("test", "addLeft:" + pointRight.x + "|" + circleCenterPoint.x);
            } else {
                lessLeft();
                break;
            }
        }
    }

    public void lessLeft() {
        for (int i = 0; i < 100; i++) {
            if (pointLeft.x <= circleCenterPoint.x - r) {
                pointLeft.x += TOTAL_MOVE / 100;
                postInvalidateDelayed(10);
                Log.e("test", "lessLeft:" + pointRight.x + "|" + circleCenterPoint.x);
            } else {
                initHeart();
                break;
            }
        }
    }

    public void addRight() {
        for (int i = 0; i < 100; i++) {
            if (pointRight.x <= circleCenterPoint.x + r + TOTAL_MOVE) {
                pointRight.x += TOTAL_MOVE / 100;
                postInvalidateDelayed(10);
            } else {
                addLeft();
                lessRight();
                break;
            }
        }
    }

    public void lessRight() {
        for (int i = 0; i < 100; i++) {
            if (pointRight.x >= circleCenterPoint.x + r) {
                pointRight.x -= TOTAL_MOVE / 100;
                postInvalidateDelayed(10);
                Log.e("test", "lessRight:" + pointRight.x + "|" + circleCenterPoint.x);
            }
        }
    }
}
