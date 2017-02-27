package com.inkhjw.dragvalidationdemo.widget;

import android.animation.ValueAnimator;
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
import android.view.MotionEvent;
import android.view.View;

import com.inkhjw.dragvalidationdemo.R;

import java.util.ArrayList;

/**
 * @author hjw
 *         拖动验证View
 */

public class DragValidationView extends View {
    private Paint paint;
    private float width, height, dragWidth;//初始化后基本不变

    private DragAttribute dragAttribute;//拖动参数
    private float dragDistance = 0;//当前拖动的距离
    private RectF dragView;//拖动滑块矩阵
    private float downX, downY;
    private boolean isDragIng = false;
    private boolean isAutoDrag = false;
    private float dragAnimDistance;//拖动滑块矩阵动画回弹的距离

    private DragValidationListener validationListener;

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
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DragValidationView);
        dragAttribute = new DragAttribute();
        dragAttribute.setTipText(typedArray.getString(R.styleable.DragValidationView_tipText));
        dragAttribute.setTipTextColor(typedArray.getColor(R.styleable.DragValidationView_tipTextColor, DragAttribute.DEFAULT_TIP_TEXT_COLOR));
        dragAttribute.setDragDirection(typedArray.getInt(R.styleable.DragValidationView_dragDirection, DragAttribute.DEFAULT_DRAG_DIRECTION));
        dragAttribute.setTipTextSize(typedArray.getDimension(R.styleable.DragValidationView_tipTextSize, DragAttribute.DEFAULT_TIP_TEXT_SIZE));
        dragAttribute.setTextVisible(true);
        dragAttribute.setDragRoundRadius(DragAttribute.DEFAULT_ROUND_RADIUS);

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

    private void updateWidthHeight(int measureWidth, int measureHeight) {
        if (measureWidth != width) {
            width = measureWidth;
        }
        if (measureHeight != height) {
            height = measureHeight;
        }
        dragWidth = height - getPaddingTop() - getPaddingBottom();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawValidationRect(canvas);
        drawDragView(canvas);
    }

    private void drawValidationRect(Canvas canvas) {
        //画圆角矩形
        paint.setColor(dragAttribute.getTipTextColor());
        RectF roundRect = new RectF(0, 0, width, height);
        canvas.drawRoundRect(roundRect, dragAttribute.getDragRoundRadius(), dragAttribute.getDragRoundRadius(), paint);

        //画提示文字
        if (dragAttribute.isTextVisible() && !TextUtils.isEmpty(dragAttribute.getTipText())) {
            float textWidth = paint.measureText(dragAttribute.getTipText());
            float x = (width - textWidth) / 2;
            float y = getBaseLine((height - dragAttribute.getTipTextSize()) / 2,
                    (height + dragAttribute.getTipTextSize()) / 2, paint);  //获取文字绘制垂直方向的基准线(y值)
            paint.setColor(DragAttribute.DEFAULT_TIP_TEXT_COLOR);
            canvas.drawText(dragAttribute.getTipText(), x, y, paint);
        }
    }

    private void drawDragView(Canvas canvas) {
        if (dragView == null) {
            dragView = new RectF(dragDistance, getPaddingTop(), dragDistance + dragWidth, height - getPaddingBottom());
        } else {
            dragView.left = dragDistance;
            dragView.top = getPaddingTop();
            dragView.right = dragDistance + dragWidth;
            dragView.bottom = height - getPaddingBottom();
        }
        //画滑块划过的背景
        if (dragDistance != 0) {
            paint.setColor(Color.parseColor("#7ca88c"));
            RectF roundRect = new RectF(0, 0, dragDistance + dragAttribute.getDragRoundRadius() * 2, height);
            canvas.drawRoundRect(roundRect, dragAttribute.getDragRoundRadius(), dragAttribute.getDragRoundRadius(), paint);
        }

        //画拖动滑块
        paint.setColor(Color.parseColor("#22aa8c"));
        canvas.drawRoundRect(dragView, dragAttribute.getDragRoundRadius(), dragAttribute.getDragRoundRadius(), paint);

        if (!isAutoDrag && !isDragIng) {
            //验证成功动画
            if (dragDistance + dragWidth == width) {
                drawDragSuccess(canvas);
                startSuccessAnim();
                dragFinish(true);
            } else {
                if (dragDistance == 0) {
                    drawDragBefore(canvas);
                    startBeforeAnim();
                    dragBefore();
                }
            }
        } else {
            stopBeforeAnim();
            stopSuccessAnim();
            drawDragBefore(canvas);

            if (isAutoDrag) {
                if (dragDistance + dragWidth < width) {
                    dragFinish(false);
                }
            } else if (isDragIng) {
                dragProcess(dragDistance);
            }
        }
    }

    private void drawDragBefore(Canvas canvas) {
        paint.setColor(Color.parseColor("#ffffff"));
        float spacing = 15;
        float centerX = dragView.centerX();
            float centerY = dragView.centerY();

            //一个箭头从上到下三个点
            float x1 = centerX - spacing;
            float y1 = centerY - spacing;
            float x2 = centerX;
            float y2 = centerY;
            float x3 = centerX - spacing;
            float y3 = centerY + spacing;

            for (int i = 0; i < 2; i++) {
                paint.setAlpha(alphas[i]);
            canvas.drawLine(x1 + spacing * i, y1, x2 + spacing * i, y2, paint);
            canvas.drawLine(x2 + spacing * i, y2, x3 + spacing * i, y3, paint);
        }
    }

    /**
     * 开始拖动前的动画
     */
    int[] alphas = new int[]{255,
            255};
    private ArrayList<ValueAnimator> mAnimators1 = new ArrayList<>();

    public void startBeforeAnim() {
        //Log.e("debug", "startBeforeAnim");
        if (mAnimators1.size() == 0) {
            int[] delays = new int[]{350, 0};
            for (int i = 0; i < 2; i++) {
                final int index = i;

                ValueAnimator alphaAnim = ValueAnimator.ofInt(255, 51, 255);
                alphaAnim.setDuration(1000);
                alphaAnim.setRepeatCount(-1);
                alphaAnim.setStartDelay(delays[i]);
                alphaAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        alphas[index] = (int) animation.getAnimatedValue();
                        postInvalidate();
                    }
                });
                mAnimators1.add(alphaAnim);
            }
        }
        for (ValueAnimator animator : mAnimators1) {
            if (animator != null && !animator.isStarted()) {
                animator.start();
            }
        }
    }

    public void stopBeforeAnim() {
        //Log.e("debug", "stopBeforeAnim");
        for (int i = 0; i < mAnimators1.size(); i++) {
            ValueAnimator animator = mAnimators1.get(i);
            if (animator != null && animator.isStarted()) {
                animator.removeAllUpdateListeners();
                animator.end();
                mAnimators1.remove(animator);
            }
        }
    }

    private void drawDragSuccess(Canvas canvas) {
//        paint.setColor(Color.parseColor("#ffffff"));
//        canvas.drawCircle(dragView.centerX(), dragView.centerY(), dragAttribute.getDragRoundRadius(), paint);

        //画加载文字
        if (dragAttribute.isTextVisible() && !TextUtils.isEmpty(dragAttribute.getTipText())) {
            String loadingText = "正在加载中";
            float textWidth = paint.measureText(loadingText);
            float x = (width - textWidth) / 2;
            float y = getBaseLine((height - dragAttribute.getTipTextSize()) / 2,
                    (height + dragAttribute.getTipTextSize()) / 2, paint);  //获取文字绘制垂直方向的基准线(y值)
            paint.setColor(DragAttribute.DEFAULT_TIP_TEXT_COLOR);
            canvas.drawText(loadingText, x, y, paint);
        }
        Paint successPaint = new Paint();
        successPaint.setAntiAlias(true);
        successPaint.setStyle(Paint.Style.STROKE);
        successPaint.setStrokeWidth(2);
        successPaint.setColor(Color.WHITE);
        RectF arc = new RectF(dragView.centerX() - 12, dragView.centerY() - 12, dragView.centerX() + 12, dragView.centerY() + 12);
        canvas.drawArc(arc, -45 + degrees, 300, false, successPaint);
    }

    /**
     * 绘制拖动验证成功的动画
     */
    float degrees;
    private ArrayList<ValueAnimator> mAnimators2 = new ArrayList<>();

    public void startSuccessAnim() {
        //Log.e("debug", "startSuccessAnim");
        if (mAnimators2.size() == 0) {
            ValueAnimator rotateAnim = ValueAnimator.ofFloat(0, 180, 360);
            rotateAnim.setDuration(750);
            rotateAnim.setRepeatCount(-1);
            rotateAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    degrees = (float) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
            mAnimators2.add(rotateAnim);
        }
        for (ValueAnimator animator : mAnimators2) {
            if (animator != null && !animator.isStarted()) {
                animator.start();
            }
        }
    }

    public void stopSuccessAnim() {
        //Log.e("debug", "stopBeforeAnim");
        for (int i = 0; i < mAnimators2.size(); i++) {
            ValueAnimator animator = mAnimators2.get(i);
            if (animator != null && animator.isStarted()) {
                animator.removeAllUpdateListeners();
                animator.end();
                mAnimators2.remove(animator);
            }
        }
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility != VISIBLE) {
            stopBeforeAnim();
        }
    }

    @Override
    public void computeScroll() {
        if (isAutoDrag) {
            if (dragDistance > 0) {
                dragDistance -= dragAnimDistance / 30;
            } else {
                dragDistance = 0;
                isAutoDrag = false;
            }
            postInvalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean eventResult;
        switch (dragAttribute.getDragDirection()) {
            default:
            case DragAttribute.DRAG_DIRECTION_LEFT:
                eventResult = dragLeftTouchEvent(event);
                break;
            case DragAttribute.DRAG_DIRECTION_TOP:
                eventResult = dragTopTouchEvent(event);
                break;
            case DragAttribute.DRAG_DIRECTION_RIGHT:
                eventResult = dragRightTouchEvent(event);
                break;
            case DragAttribute.DRAG_DIRECTION_BOTTOM:
                eventResult = dragBottomTouchEvent(event);
                break;
        }
        return eventResult;
    }

    public boolean dragLeftTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (inDragBound(x, y, dragView)) {
                    downX = event.getX();
                    downY = event.getY();
                    isDragIng = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (!isAutoDrag && isDragIng) {
                    float length = x - downX;
                    if (length >= 0) {
                        dragDistance = length;
                    } else {
                        if (Math.abs(length) > dragDistance) {
                            dragDistance = 0;
                        } else {
                            dragDistance -= length;
                        }
                    }
                    if (dragDistance >= 0) {
                        if (dragDistance + dragWidth >= width) {
                            dragDistance = width - dragWidth;
                            isDragIng = false;
                            isAutoDrag = false;
                        }
                        postInvalidate();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (dragDistance > 0 && dragDistance + dragWidth < width) {
                    dragAnimDistance = dragDistance;
                    isDragIng = false;
                    isAutoDrag = true;
                    postInvalidate();
                }
                break;
        }
        return true;
    }

    public boolean dragTopTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if (inDragBound(downX, downY, dragView)) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downX = event.getX();
                    downY = event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:

                    break;
                case MotionEvent.ACTION_UP:

                    break;
            }
        }
        return super.onTouchEvent(event);
    }

    public boolean dragRightTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if (inDragBound(downX, downY, dragView)) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downX = event.getX();
                    downY = event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:

                    break;
                case MotionEvent.ACTION_UP:

                    break;
            }
        }
        return super.onTouchEvent(event);
    }

    public boolean dragBottomTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if (inDragBound(downX, downY, dragView)) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downX = event.getX();
                    downY = event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:

                    break;
                case MotionEvent.ACTION_UP:

                    break;
            }
        }
        return super.onTouchEvent(event);
    }

    private boolean inDragBound(float x, float y, RectF rectF) {
        if (x > rectF.left && x < rectF.right) {
            if (y > rectF.top && y < rectF.bottom) {
                return true;
            }
        }
        return false;
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

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public synchronized void setValidationFinish() {
        isDragIng = false;
        isAutoDrag = false;
        dragDistance = 0;
        isCall = false;
        postInvalidate();
    }

    /**
     * @param top
     * @param bottom
     * @return
     */
    private float getBaseLine(float top, float bottom, Paint paint) {
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        return ((bottom + top - fontMetrics.bottom - fontMetrics.top) / 2);
    }

    public int dpsp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dpsp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public interface DragValidationListener {
        void dragBefore();

        void dragProcess(float dragLength);

        void dragFinish(boolean success);
    }

    public void setDragValidationListener(DragValidationListener validationListener) {
        if (this.validationListener == null) {
            this.validationListener = validationListener;
        }
    }

    private void dragBefore() {
        if (!isCall) {
            if (validationListener != null) {
                validationListener.dragBefore();
                isCall = true;
            }
        }
    }

    private void dragProcess(float dragLength) {
        if (isCall) {
            isCall = false;
        }
        if (validationListener != null) {
            validationListener.dragProcess(dragLength);
        }
    }

    private void dragFinish(boolean success) {
        if (!isCall) {
            if (validationListener != null) {
                validationListener.dragFinish(success);
                isCall = true;
            }
        }
    }

    /**
     * 是否回调dragBefore、dragFinish()
     */
    private boolean isCall = false;
}
