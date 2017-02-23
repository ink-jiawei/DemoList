package com.inkhjw.dragvalidationdemo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.inkhjw.dragvalidationdemo.R;

/**
 * @author hjw
 *         拖动验证View
 */

public class DragValidationView extends View {
    private Paint paint;
    private DragAttribute dragAttribute;

    private int width;
    private int height;
    Context context;

    public DragValidationView(Context context) {
        super(context);
        init(context, null);
    }

    public DragValidationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public DragValidationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DragValidationView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    protected void init(Context context, AttributeSet attrs) {
        this.context = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DragValidationView);
        dragAttribute = new DragAttribute();
        dragAttribute.setTipText(typedArray.getString(R.styleable.DragValidationView_tipText));
        dragAttribute.setTipTextColor(typedArray.getColor(R.styleable.DragValidationView_tipTextColor, DragAttribute.DEFAULT_TIP_TEXT_COLOR));
        dragAttribute.setDragDirection(typedArray.getInt(R.styleable.DragValidationView_dragDirection, DragAttribute.DEFAULT_DRAG_DIRECTION));
        dragAttribute.setTipTextSize(typedArray.getDimension(R.styleable.DragValidationView_tipTextSize, DragAttribute.DEFAULT_TIP_TEXT_SIZE));
        typedArray.recycle();

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(dragAttribute.getTipTextSize());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldWidth, int oldHeight) {
        super.onSizeChanged(w, h, oldWidth, oldHeight);
        updateWidthHeight(w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("debug", "onDraw");
        drawValidationRect(canvas);
    }

    private void drawValidationRect(Canvas canvas) {
        //画圆角矩形
        paint.setColor(dragAttribute.getTipTextColor());
        RectF roundRect = new RectF(0, 0, width, height);
        canvas.drawRoundRect(roundRect, 10, 10, paint);

        float textWidth = paint.measureText(dragAttribute.getTipText());

        //画提示文字背景
        paint.setColor(Color.GRAY);
        canvas.drawRect((width - textWidth) / 2, (height - dragAttribute.getTipTextSize()) / 2, (width - textWidth) / 2 + textWidth, (height - dragAttribute.getTipTextSize()) / 2 + dragAttribute.getTipTextSize(), paint);

        //获得字体高度
        Paint.FontMetrics fm = paint.getFontMetrics();
        float textHeight = (float) (Math.ceil(fm.descent - fm.top) + 2);
        //画提示文字
        paint.setColor(DragAttribute.DEFAULT_TIP_TEXT_COLOR);
        Log.e("debug", "height=" + px2dpsp(context, height) + "|TipTextSize=" + px2dpsp(context, dragAttribute.getTipTextSize()));
        Log.e("debug", "textHeight=" + px2dpsp(context, textHeight));
        canvas.drawText(dragAttribute.getTipText(), (width - textWidth) / 2, (height + dragAttribute.getTipTextSize()) / 2, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int minWidth = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth();
        int minHeight = getPaddingBottom() + getPaddingTop() + getSuggestedMinimumHeight();

        int measureWidth = measureSizeAndState(minWidth, widthMeasureSpec, 0);
        int measureHeight = measureSizeAndState(minHeight, heightMeasureSpec, 0);
        setMeasuredDimension(measureWidth, measureHeight);
    }

    public int measureSizeAndState(int size, int measureSpec, int childMeasuredState) {
        final int specMode = MeasureSpec.getMode(measureSpec);
        final int specSize = MeasureSpec.getSize(measureSpec);
        final int result;
        switch (specMode) {
            case MeasureSpec.AT_MOST:
                if (specSize < size) {
                    result = specSize | MEASURED_STATE_TOO_SMALL;
                } else {
                    result = size;
                }
                break;
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
            case MeasureSpec.UNSPECIFIED:
            default:
                result = size;
        }
        return result | (childMeasuredState & MEASURED_STATE_MASK);
    }

    private void updateWidthHeight(int measureWidth, int measureHeight) {
        if (measureWidth != width) {
            width = measureWidth;
        }
        if (measureHeight != height) {
            height = measureHeight;
        }
    }

    public int dpsp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dpsp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
