package com.hjw.diagramview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

/**
 * @author hjw
 * @deprecated 自定义饼状图的View
 */
public class DiagramView extends View {
    private ArrayList<DataBean> list;
    private Paint paint;
    // 饼图颜色
    private int[] colors = {Color.rgb(205, 205, 205), Color.rgb(114, 188, 223), Color.rgb(255, 123, 124), Color.rgb(57, 135, 200)};

    /**
     * 饼状图默认的大小
     */
    private int width = 0;
    private int height = 0;

    public DiagramView(Context context) {
        super(context);
        init();
    }

    public DiagramView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DiagramView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setColor(Color.RED);
        drawDiagram(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureWidth(widthMeasureSpec);
        measureHeight(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    /**
     * 测量宽度
     *
     * @param widthMeasureSpec
     */
    public void measureWidth(int widthMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize + getPaddingLeft() + getPaddingRight();
        } else {
            width = 500;
        }
    }

    /**
     * 测量高度
     *
     * @param heightMeasureSpec
     */
    public void measureHeight(int heightMeasureSpec) {
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize + getPaddingTop() + getPaddingBottom();
        } else {
            height = 500;
        }
    }

    public void drawDiagram(Canvas canvas) {
        float upAngle = 0;//开始角度<相对于起始位置的角度>
        float curAngle = 0;//结束角度<相对于起始位置的角度>
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                DataBean data = list.get(i);

                String text = data.getText();
                float num = data.getNum();

                curAngle = upAngle + (num / 100) * 360;
                Log.e("test", upAngle + ":" + curAngle);
                paint.setColor(colors[i]);
                Point point;
                if (width >= height) {
                    canvas.drawArc(new RectF(0, 0, width, width), upAngle, (num / 100) * 360, true, paint);
                    point = countTextPoint(width / 2, width / 2, width / 2, curAngle, (num / 100) * 360);
                } else {
                    canvas.drawArc(new RectF(0, 0, height, height), upAngle, (num / 100) * 360, true, paint);
                    point = countTextPoint(height / 2, height / 2, height / 2, curAngle, (num / 100) * 360);
                }
                upAngle = curAngle;

                paint.setColor(Color.BLACK);
                paint.setTextSize(18);
                canvas.drawText(text, point.x, point.y, paint);
            }
        }
    }

    /**
     * 计算扇形区域中绘制文字的坐标
     *
     * @param centerX
     * @param centerY
     * @param r
     * @param absAngle
     * @param angle
     * @return
     */
    public Point countTextPoint(float centerX, float centerY, float r, float absAngle, float angle) {
        Log.e("test", "absAngle=" + absAngle + "angle=" + angle);

        float centerAngle = absAngle - angle / 2;//扇形中线所对应的角度

        Point point = new Point();

        if (centerAngle > 0 && centerAngle <= 90) {
            point.x = (int) (centerX + Math.cos(angle / 2) * r * Math.cos(angle / 2));
            point.y = (int) (centerY + Math.cos(angle / 2) * r * Math.sin(angle / 2));
        } else if (centerAngle > 90 && centerAngle <= 180) {
            point.x = (int) (centerX - Math.cos(angle / 2) * r * Math.cos(angle / 2));
            point.y = (int) (centerY + Math.cos(angle / 2) * r * Math.sin(angle / 2));
        } else if (centerAngle > 180 && centerAngle <= 270) {
            point.x = (int) (centerX - Math.cos(angle / 2) * r * Math.cos(angle / 2));
            point.y = (int) (centerY - Math.cos(angle / 2) * r * Math.sin(angle / 2));
        } else if (centerAngle > 270 && centerAngle <= 360) {
            point.x = (int) (centerX + Math.cos(angle / 2) * r * Math.cos(angle / 2));
            point.y = (int) (centerY - Math.cos(angle / 2) * r * Math.sin(angle / 2));
        }
        Log.e("test", "point.x=" + point.x + "      point.y=" + point.y);
        return point;
    }

    /**
     * 设置饼状图的数据
     */
    public void setData(ArrayList<DataBean> list) {
        this.list = list;
    }
}
