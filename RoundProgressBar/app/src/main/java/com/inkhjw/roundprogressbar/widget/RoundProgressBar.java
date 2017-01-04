package com.inkhjw.roundprogressbar.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.inkhjw.roundprogressbar.R;


/**
 * @author inkhjw
 */
public class RoundProgressBar extends View {
    /**
     * 圆环背景的颜色
     */
    private int roundColor;
    /**
     * 圆环进度的颜色
     */
    private int roundProgressStartColor;
    private int roundProgressEndColor;
    /**
     * 圆环的宽度
     */
    private float roundWidth;
    /**
     * 最大进度
     */
    private int max;
    /**
     * 中间进度文本颜色
     */
    private int centerProgressTextColor;
    /**
     * 中间进度文本大小
     */
    private float centerProgressTextSize;

    private int progress;//当前进度
    private int animProgress = 0;//动画进度
    private Paint paint;//圆环画笔
    private Paint roundPaint;//圆角画笔
    private TextPaint textPaint;//文本画笔

    private float centerX;
    private float centerY;
    private float radius;

    LinearGradient backgroundCircleLinearGradient;//背景渐变
    LinearGradient progressCircleLinearGradient;//进度渐变
    private final double BOTTOM_ANGLE = 20;//底部开口夹角大小，一边

    public RoundProgressBar(Context context) {
        this(context, null);
    }

    public RoundProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
                R.styleable.RoundProgressBar);

        roundColor = mTypedArray.getColor(R.styleable.RoundProgressBar_roundColor,
                getResources().getColor(R.color.step_foot_background_color));
        roundProgressStartColor = mTypedArray.getColor(R.styleable.RoundProgressBar_roundProgressStartColor,
                getResources().getColor(R.color.step_foot_progress_end_color));
        roundProgressEndColor = mTypedArray.getColor(R.styleable.RoundProgressBar_roundProgressEndColor,
                getResources().getColor(R.color.appThemeColor));
        roundWidth = mTypedArray.getDimension(R.styleable.RoundProgressBar_roundWidth, 40);
        max = mTypedArray.getInteger(R.styleable.RoundProgressBar_max, 100);
        centerProgressTextColor = mTypedArray.getColor(R.styleable.RoundProgressBar_centerProgressTextColor,
                getResources().getColor(R.color.text_color_normal_title));
        centerProgressTextSize = mTypedArray.getDimension(R.styleable.RoundProgressBar_centerProgressTextSize,
                30);
        mTypedArray.recycle();

        init();
    }

    protected void init() {
        paint = new Paint();
        paint.setStrokeWidth(roundWidth); //设置圆环的宽度
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);

        roundPaint = new Paint();
        roundPaint.setAntiAlias(true);
        roundPaint.setStyle(Paint.Style.FILL);

        textPaint = new TextPaint();
        textPaint.setTextSize(centerProgressTextSize);
        textPaint.setColor(centerProgressTextColor);
        textPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w / 2; //获取圆心的x坐标
        centerY = h / 2;//获取圆心的y坐标
        radius = (int) (centerX - roundWidth / 2); //圆环的半径

        backgroundCircleLinearGradient = new LinearGradient(centerX - radius, centerY - radius,
                centerX + radius, centerY + radius, roundColor, roundColor, Shader.TileMode.MIRROR);
        progressCircleLinearGradient = new LinearGradient(centerX - radius, centerY - radius,
                centerX + radius, centerY + radius, roundProgressStartColor, roundProgressEndColor, Shader.TileMode.MIRROR);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBackgroundCircle(canvas);
        drawProgressCircle(canvas);
        drawProgressRound(canvas);
        drawCenterProgressText(canvas);
    }

    /**
     * 画表盘的背景圈
     */
    protected void drawBackgroundCircle(Canvas canvas) {
        paint.setShader(backgroundCircleLinearGradient);
        RectF oval = new RectF(centerX - radius, centerY - radius,
                centerX + radius, centerY + radius);
        canvas.drawArc(oval, 90 + (float) BOTTOM_ANGLE, 360 - (float) BOTTOM_ANGLE * 2, false, paint);
    }

    /**
     * 画进度的背景圈
     */
    protected void drawProgressCircle(Canvas canvas) {
        paint.setShader(progressCircleLinearGradient);
        RectF oval = new RectF(centerX - radius, centerY - radius,
                centerX + radius, centerY + radius);
        if (animProgress != 0) {
            canvas.drawArc(oval, 90 + (float) BOTTOM_ANGLE, (360 - (float) BOTTOM_ANGLE * 2) * animProgress / max, false, paint);
        }
    }

    /**
     * 画进度圈的圆角
     */
    protected void drawProgressRound(Canvas canvas) {
        float zhouChang = ((360 - 2 * (float) BOTTOM_ANGLE) / 360) * (float) (2 * Math.PI * radius);
        double sweepSize = ((double) animProgress / max) * (360 - 2 * BOTTOM_ANGLE);//扫过的角度
        double sweepLength = (sweepSize / (360 - 2 * BOTTOM_ANGLE)) * zhouChang;//扫过的弧长
        double rightSweep = 360 - 2 * BOTTOM_ANGLE;

        if (sweepSize >= rightSweep) {
            //位置不变的底部右边圆角
            roundPaint.setShader(progressCircleLinearGradient);
            drawArcRound(canvas, rightSweep);
        } else {
            roundPaint.setShader(backgroundCircleLinearGradient);
            drawArcRound(canvas, rightSweep);
        }

        if (sweepSize > 0) {
            //跟随进度的圆角
            roundPaint.setShader(progressCircleLinearGradient);
            drawArcRound(canvas, sweepSize);

            //位置不变的底部左边圆角，如果扫过的弧长小于圆角的直径，就绘制roundColor颜色圆角，否则绘制roundProgressColor颜色圆角
            roundPaint.setShader(progressCircleLinearGradient);
            drawArcRound(canvas, 0);
        } else {
            roundPaint.setShader(backgroundCircleLinearGradient);
            drawArcRound(canvas, 0);
        }
    }

    /**
     * 画圆弧的圆角
     */
    private void drawArcRound(Canvas canvas, double sweepSize) {
        double bili = Math.toRadians(sweepSize + 90 + BOTTOM_ANGLE);

        float startX;
        float startY;
        if (0 < bili && bili <= 90) {
            startX = (float) (centerX + Math.cos(bili) * radius);
            startY = (float) (centerY + Math.sin(bili) * radius);
        } else if (90 < bili && bili <= 180) {
            startX = (float) (centerX + Math.cos(bili) * radius);
            startY = (float) (centerY - Math.sin(bili) * radius);
        } else if (180 < bili && bili <= 270) {
            startX = (float) (centerX - Math.cos(bili) * radius);
            startY = (float) (centerY + Math.sin(bili) * radius);
        } else {
            startX = (float) (centerX + Math.cos(bili) * radius);
            startY = (float) (centerY - Math.sin(bili) * radius);
        }
        canvas.drawCircle(startX, startY, roundWidth / 2, roundPaint);
    }

    /**
     * 画中间的进度文本
     */
    protected void drawCenterProgressText(Canvas canvas) {
        String centerText = String.valueOf(animProgress);
        float centerTextWidth = textPaint.measureText(centerText);
        canvas.drawText(centerText, centerX - centerTextWidth / 2, centerY + centerProgressTextSize / 2, textPaint);
    }

    /**
     * 使用postInvalidate()重绘制界面(线程安全)
     */
    public synchronized void setProgress(int p) {
        if (p < 0) {
            throw new IllegalArgumentException("progress not less than 0");
        }
        if (p > max) {
            p = max;
        }
        this.progress = p;
        final int del = Math.abs(progress - animProgress);

        //开启新线程播放动画效果
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (animProgress < progress) {
                    //增加进度
                    while (animProgress < progress) {
                        animProgress += max / 100 + 1;
                        if (animProgress >= progress) {
                            animProgress = progress;
                            postInvalidate();
                            break;
                        }
                        postInvalidate();

                        try {
                            Thread.sleep(1000 / (del / (max / 100 + 1) + 1));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    //减少进度
                    while (animProgress > progress) {
                        animProgress -= max / 100 + 1;
                        if (animProgress <= progress) {
                            animProgress = progress;
                            postInvalidate();
                            break;
                        }
                        postInvalidate();

                        try {
                            Thread.sleep(1000 / (del / (max / 100 + 1) + 1));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    /**
     * 获取进度.需要同步
     *
     * @return
     */
    public synchronized int getProgress() {
        return progress;
    }

    public synchronized int getMax() {
        return max;
    }

    /**
     * 设置进度的最大值
     *
     * @param max
     */
    public synchronized void setMax(int max) {
        if (max <= 0) {
            throw new IllegalArgumentException("max not less than 0");
        }
        this.max = max;
    }

    public int getCricleColor() {
        return roundColor;
    }

    public void setCricleColor(int cricleColor) {
        this.roundColor = cricleColor;
    }

    public int getRoundProgressStartColor() {
        return roundProgressStartColor;
    }

    public void setRoundProgressStartColor(int roundProgressStartColor) {
        this.roundProgressStartColor = roundProgressStartColor;
    }

    public int getRoundProgressEndColor() {
        return roundProgressEndColor;
    }

    public void setRoundProgressEndColor(int roundProgressEndColor) {
        this.roundProgressEndColor = roundProgressEndColor;
    }

    public float getRoundWidth() {
        return roundWidth;
    }

    public void setRoundWidth(float roundWidth) {
        this.roundWidth = roundWidth;
    }

    public float getCenterX() {
        return centerX;
    }

    public void setCenterX(float centerX) {
        this.centerX = centerX;
    }

    public float getCenterY() {
        return centerY;
    }

    public void setCenterY(float centerY) {
        this.centerY = centerY;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public int getRoundColor() {
        return roundColor;
    }

    public void setRoundColor(int roundColor) {
        this.roundColor = roundColor;
    }

    public int getAnimProgress() {
        return animProgress;
    }

    public void setAnimProgress(int animProgress) {
        this.animProgress = animProgress;
    }

    public int getCenterProgressTextColor() {
        return centerProgressTextColor;
    }

    public void setCenterProgressTextColor(int centerProgressTextColor) {
        this.centerProgressTextColor = centerProgressTextColor;
    }

    public float getCenterProgressTextSize() {
        return centerProgressTextSize;
    }

    public void setCenterProgressTextSize(float centerProgressTextSize) {
        this.centerProgressTextSize = centerProgressTextSize;
    }
}
