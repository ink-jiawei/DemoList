package com.inkhjw.selfviewcode.widget.besselcurve;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author 何佳伟
 * @deprecated 利用贝塞尔曲线，实现渐变为心形的效果
 */
public class HeartShapeView extends View {
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

    //圆、爱心的属性
    private float r = 100;//半径
    private static final float C = 0.551915024494f;//用于计算圆中贝塞尔曲线控制点的位置(计算得出)
    private PointF top;//爱心上面的点(原始点坐标，当还是圆的时候)
    private static final float HEART_MOVE = 50;//爱心上面的点移动的距离

    public HeartShapeView(Context context) {
        super(context);
        init();
    }

    public HeartShapeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HeartShapeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);

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

        top = new PointF(0, 0);
    }

    public void initHeart() {
        pointLeft.x = centerP.x - r;
        pointLeft.y = centerP.y;

        pointRight.x = centerP.x + r;
        pointRight.y = centerP.y;

        pointTop.x = centerP.x;
        pointTop.y = centerP.y - r;

        pointBottom.x = centerP.x;
        pointBottom.y = centerP.y + r;

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

        top.x = pointTop.x;
        top.y = pointTop.y;
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
        canvas.drawColor(Color.GRAY);
        drawHeartShape(canvas);
    }

    public void drawHeartShape(Canvas canvas) {
        Path path = new Path();
        path.moveTo(pointLeft.x, pointLeft.y);
        path.cubicTo(point1.x, point1.y, point2.x, point2.y, pointTop.x, pointTop.y);
        path.cubicTo(point3.x, point3.y, point4.x, point4.y, pointRight.x, pointRight.y);
        path.cubicTo(point5.x, point5.y, point6.x, point6.y, pointBottom.x, pointBottom.y);
        path.cubicTo(point7.x, point7.y, point8.x, point8.y, pointLeft.x, pointLeft.y);
        canvas.drawPath(path, paint);
        AnimPostDelayed();
    }

    public void AnimPostDelayed() {

        if (pointTop.y - top.y <= HEART_MOVE) {

            pointTop.y += 1;

            point2.x += 0.1;
            point2.y += 0.1;

            point3.x -= 0.1;
            point3.y += 0.1;

            point5.x -= 0.2;
            point8.x += 0.2;

            point6.y -= 0.5;
            point7.y -= 0.5;

            postInvalidateDelayed(10);
        } else {
            initHeart();
        }
    }
}
