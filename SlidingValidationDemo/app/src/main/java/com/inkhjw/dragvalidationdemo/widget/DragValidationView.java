package com.inkhjw.dragvalidationdemo.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.inkhjw.dragvalidationdemo.R;

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
    private float downX;
    private float downY;
    private boolean isAutoDrag = false;
    private float dragAnimDistance;//拖动滑块矩阵动画回弹的距离

    private Indicator mIndicator;
    private static final ArrowsIndicator DEFAULT_INDICATOR = new ArrowsIndicator();
    private static final int MIN_SHOW_TIME = 500; // ms
    private static final int MIN_DELAY = 500; // ms
    private long mStartTime = -1;
    private boolean mPostedHide = false;
    private boolean mPostedShow = false;
    private boolean mDismissed = false;
    private boolean mShouldStartAnimationDrawable;

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

        if (mIndicator == null) {
            setIndicator(DEFAULT_INDICATOR);
        }
        typedArray.recycle();

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(dragAttribute.getTipTextSize());
    }

    public Indicator getIndicator() {
        return mIndicator;
    }

    public void setIndicator(Indicator d) {
        if (mIndicator != d) {
            if (mIndicator != null) {
                mIndicator.setCallback(null);
                unscheduleDrawable(mIndicator);
            }

            mIndicator = d;
            if (d != null) {
                d.setCallback(this);
            }
            postInvalidate();
        }
    }

    public void hide() {
        mDismissed = true;
        removeCallbacks(mDelayedShow);
        long diff = System.currentTimeMillis() - mStartTime;
        if (diff >= MIN_SHOW_TIME || mStartTime == -1) {
            // The progress spinner has been shown long enough
            // OR was not shown yet. If it wasn't shown yet,
            // it will just never be shown.
            setVisibility(View.GONE);
        } else {
            // The progress spinner is shown, but not long enough,
            // so put a delayed message in to hide it when its been
            // shown long enough.
            if (!mPostedHide) {
                postDelayed(mDelayedHide, MIN_SHOW_TIME - diff);
                mPostedHide = true;
            }
        }
    }

    public void show() {
        // Reset the start time.
        mStartTime = -1;
        mDismissed = false;
        removeCallbacks(mDelayedHide);
        if (!mPostedShow) {
            postDelayed(mDelayedShow, MIN_DELAY);
            mPostedShow = true;
        }
    }

    @Override
    protected boolean verifyDrawable(Drawable who) {
        return who == mIndicator
                || super.verifyDrawable(who);
    }

    void startAnimation() {
        if (getVisibility() != VISIBLE) {
            return;
        }

        if (mIndicator instanceof Animatable) {
            mShouldStartAnimationDrawable = true;
        }
        postInvalidate();
    }

    void stopAnimation() {
        if (mIndicator instanceof Animatable) {
            mIndicator.stop();
            mShouldStartAnimationDrawable = false;
        }
        postInvalidate();
    }

    @Override
    public void setVisibility(int v) {
        if (getVisibility() != v) {
            super.setVisibility(v);
            if (v == GONE || v == INVISIBLE) {
                stopAnimation();
            } else {
                startAnimation();
            }
        }
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == GONE || visibility == INVISIBLE) {
            stopAnimation();
        } else {
            startAnimation();
        }
    }

    @Override
    public void invalidateDrawable(Drawable dr) {
        if (verifyDrawable(dr)) {
            final Rect dirty = dr.getBounds();
            final int scrollX = getScrollX() + getPaddingLeft();
            final int scrollY = getScrollY() + getPaddingTop();

            invalidate(dirty.left + scrollX, dirty.top + scrollY,
                    dirty.right + scrollX, dirty.bottom + scrollY);
        } else {
            super.invalidateDrawable(dr);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldWidth, int oldHeight) {
        super.onSizeChanged(w, h, oldWidth, oldHeight);
        updateWidthHeight(w, h);
        dragWidth = height - getPaddingTop() - getPaddingBottom();
        updateDrawableBounds(w, h);
    }

    private void updateDrawableBounds(int w, int h) {
        // onDraw will translate the canvas so we draw starting at 0,0.
        // Subtract out padding for the purposes of the calculations below.
        w -= getPaddingRight() + getPaddingLeft();
        h -= getPaddingTop() + getPaddingBottom();

        int right = w;
        int bottom = h;
        int top = 0;
        int left = 0;

        if (mIndicator != null) {
            // Maintain aspect ratio. Certain kinds of animated drawables
            // get very confused otherwise.
            final int intrinsicWidth = mIndicator.getIntrinsicWidth();
            final int intrinsicHeight = mIndicator.getIntrinsicHeight();
            final float intrinsicAspect = (float) intrinsicWidth / intrinsicHeight;
            final float boundAspect = (float) w / h;
            if (intrinsicAspect != boundAspect) {
                if (boundAspect > intrinsicAspect) {
                    // New width is larger. Make it smaller to match height.
                    final int width = (int) (h * intrinsicAspect);
                    left = (w - width) / 2;
                    right = left + width;
                } else {
                    // New height is larger. Make it smaller to match width.
                    final int height = (int) (w * (1 / intrinsicAspect));
                    top = (h - height) / 2;
                    bottom = top + height;
                }
            }
            mIndicator.setBounds(left, top, right, bottom);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Log.e("debug", "onDraw");
        // drawValidationRect(canvas);
        //drawDragView(canvas);
        drawTrack(canvas);
    }

    void drawTrack(Canvas canvas) {
        Log.e("debug", "drawTrack");
        if (mIndicator != null) {
            // Translate canvas so a indeterminate circular progress bar with padding
            // rotates properly in its animation
            mIndicator.draw(canvas);
            if (mShouldStartAnimationDrawable && mIndicator instanceof Animatable) {
                mIndicator.start();
                mShouldStartAnimationDrawable = false;
            }
        }
    }

    private void drawValidationRect(Canvas canvas) {
        //画圆角矩形
        paint.setColor(dragAttribute.getTipTextColor());
        RectF roundRect = new RectF(0, 0, width, height);
        canvas.drawRoundRect(roundRect, dragAttribute.getDragRoundRadius(), dragAttribute.getDragRoundRadius(), paint);

        //画提示文字
        if (dragAttribute.isTextVisible() && !TextUtils.isEmpty(dragAttribute.getTipText())) {
            //Log.e("debug", "draw tipText");
            float textWidth = paint.measureText(dragAttribute.getTipText());
            float x = (width - textWidth) / 2;
            float y = getBaseLine((height - dragAttribute.getTipTextSize()) / 2,
                    (height + dragAttribute.getTipTextSize()) / 2, paint);  //获取文字绘制垂直方向的基准线(y值)
            paint.setColor(DragAttribute.DEFAULT_TIP_TEXT_COLOR);
            canvas.drawText(dragAttribute.getTipText(), x, y, paint);
        }
    }

    private void drawDragView(Canvas canvas) {
        //验证成功动画
        if (isAutoDrag && dragDistance + dragWidth == width) {

        } else {
            //画拖动滑块
            if (dragView == null) {
                dragView = new RectF(dragDistance, getPaddingTop(), dragDistance + dragWidth, height - getPaddingBottom());
            } else {
                dragView.left = dragDistance;
                dragView.top = getPaddingTop();
                dragView.right = dragDistance + dragWidth;
                dragView.bottom = height - getPaddingBottom();
            }
            paint.setColor(Color.parseColor("#FFE75764"));
            canvas.drawRoundRect(dragView, dragAttribute.getDragRoundRadius(), dragAttribute.getDragRoundRadius(), paint);
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

    public boolean dragLeftTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (!isAutoDrag && inDragBound(x, y, dragView)) {
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
                    if (dragDistance >= 0 && dragDistance + dragWidth <= width) {
                        postInvalidate();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (dragDistance > 0) {
                    dragAnimDistance = dragDistance;
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

    private void updateWidthHeight(int measureWidth, int measureHeight) {
        if (measureWidth != width) {
            width = measureWidth;
        }
        if (measureHeight != height) {
            height = measureHeight;
        }
    }


    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        updateDrawableState();
    }

    private void updateDrawableState() {
        final int[] state = getDrawableState();
        if (mIndicator != null && mIndicator.isStateful()) {
            mIndicator.setState(state);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void drawableHotspotChanged(float x, float y) {
        super.drawableHotspotChanged(x, y);

        if (mIndicator != null) {
            mIndicator.setHotspot(x, y);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAnimation();
        removeCallbacks();
    }

    @Override
    protected void onDetachedFromWindow() {
        stopAnimation();
        // This should come after stopAnimation(), otherwise an invalidate message remains in the
        // queue, which can prevent the entire view hierarchy from being GC'ed during a rotation
        super.onDetachedFromWindow();
        removeCallbacks();
    }

    private void removeCallbacks() {
        removeCallbacks(mDelayedHide);
        removeCallbacks(mDelayedShow);
    }

    private final Runnable mDelayedHide = new Runnable() {

        @Override
        public void run() {
            mPostedHide = false;
            mStartTime = -1;
            setVisibility(View.GONE);
        }
    };

    private final Runnable mDelayedShow = new Runnable() {

        @Override
        public void run() {
            mPostedShow = false;
            if (!mDismissed) {
                mStartTime = System.currentTimeMillis();
                setVisibility(View.VISIBLE);
            }
        }
    };

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
}
