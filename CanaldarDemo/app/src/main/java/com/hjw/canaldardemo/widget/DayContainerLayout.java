package com.hjw.canaldardemo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * @author hjw
 * @deprecated 每一天的容器
 */
public class DayContainerLayout extends LinearLayout {
    private Canvas canvas;

    public DayContainerLayout(Context context) {
        super(context);
    }

    public DayContainerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DayContainerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        this.canvas = canvas;
        super.onDraw(canvas);
    }

    /**
     * 设置每一天选中的背景、和当前天的背景
     *
     * @param color
     */
    public void addSelectedBackGround(int color, int layoutX, int layoutY, int layoutWidth, int layoutHeight) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        if (layoutWidth > layoutHeight) {
            canvas.drawCircle(layoutX + layoutWidth, layoutY + layoutHeight, layoutHeight, paint);
        } else {
            canvas.drawCircle(layoutX + layoutWidth, layoutY + layoutHeight, layoutWidth, paint);
        }
        postInvalidate();
    }
}
