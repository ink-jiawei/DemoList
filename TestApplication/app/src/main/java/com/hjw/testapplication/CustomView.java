package com.hjw.testapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * @author hjw
 * @deprecated 自定义View
 */
public class CustomView extends View implements View.OnClickListener {
    /**
     * 自定义的属性
     */
    private int viewType;
    private int color;

    Paint paint;


    public CustomView(Context context) {
        super(context);
        init();
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CustomView);
        int n = ta.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = ta.getIndex(i);

            switch (attr) {
                case R.styleable.CustomView_view_type:
                    viewType = ta.getInt(attr, 0);
                    break;
                case R.styleable.CustomView_view_color:
                    color = ta.getColor(attr, Color.BLACK);
                    break;
            }
        }
        ta.recycle();
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CustomView);
        int n = ta.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = ta.getIndex(i);

            switch (attr) {
                case R.styleable.CustomView_view_type:
                    viewType = ta.getInt(attr, 0);
                    break;
                case R.styleable.CustomView_view_color:
                    color = ta.getColor(attr, Color.BLACK);
                    break;
            }
        }
        ta.recycle();
    }

    private void init() {
        this.setOnClickListener(this);
        setWillNotDraw(false);
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("test", viewType + "onDraw");
        Log.e("test", color + "onDraw");
        paint.setColor(color);
        switch (viewType) {
            case 0:
                canvas.drawRect(100, 100, 200, 200, paint);
                break;
            case 1:
                canvas.drawLine(100, 100, 200, 100, paint);
                break;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = 0;
        int height = 0;

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = 300;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = 300;
        }

        setMeasuredDimension(width, height);
    }

    public void setViewType(int type) {
        this.viewType = type;
    }

    public void setViewColor(int color) {
        this.color = color;
    }

    @Override
    public void onClick(View v) {
        if (this.viewType == 1) {
            this.viewType = 0;
        } else {
            this.viewType = 1;
        }
        this.postInvalidate();
    }
}
