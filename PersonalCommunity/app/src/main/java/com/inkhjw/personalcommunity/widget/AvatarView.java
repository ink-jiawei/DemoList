package com.inkhjw.personalcommunity.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

public class AvatarView extends ImageView {

    private Paint mPaint;
    private Paint mBorderPaint;
    private Xfermode mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
    private Bitmap mMaskBitmap;

    private WeakReference<Bitmap> mWeakBitmap;

    /**
     * 边框的默认值
     */
    private static final int BORDER_DEFAULT = 5;

    public AvatarView(Context context) {
        this(context, null);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        mBorderPaint = new Paint();
        mPaint.setAntiAlias(true);
        mBorderPaint.setColor(Color.WHITE);
    }

    public AvatarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        mBorderPaint = new Paint();
        mPaint.setAntiAlias(true);
        mBorderPaint.setColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //在缓存中取出bitmap
        Bitmap bitmap = mWeakBitmap == null ? null : mWeakBitmap.get();

        if (null == bitmap || bitmap.isRecycled()) {
            //拿到Drawable
            Drawable drawable = getDrawable();

            if (drawable != null) {
                //获取drawable的宽和高
                int dWidth = drawable.getIntrinsicWidth();
                int dHeight = drawable.getIntrinsicHeight();

                //创建bitmap
                bitmap = Bitmap.createBitmap(getWidth(), getHeight(),
                        Bitmap.Config.ARGB_8888);
                float scale = 1.0f;
                //创建画布
                Canvas drawCanvas = new Canvas(bitmap);
                //按照bitmap的宽高，以及view的宽高，计算缩放比例；因为设置的src宽高比例可能和imageview的宽高比例不同，这里我们不希望图片失真；
                scale = getWidth() * 1.0F / Math.min(dWidth, dHeight);

                //根据缩放比例，设置bounds，相当于缩放图片了
                drawable.setBounds(0, 0, (int) (scale * dWidth),
                        (int) (scale * dHeight));
                drawable.draw(drawCanvas);
                if (mMaskBitmap == null || mMaskBitmap.isRecycled()) {
                    mMaskBitmap = getBitmap();
                }

                // Draw Bitmap.
                mPaint.reset();
                mPaint.setFilterBitmap(false);
                mPaint.setXfermode(mXfermode);
                //绘制形状
                drawCanvas.drawBitmap(mMaskBitmap, 0, 0, mPaint);

                mPaint.setXfermode(null);
                //将准备好的bitmap绘制出来
                canvas.drawBitmap(mMaskBitmap, 0, 0, null);
                //bitmap缓存起来，避免每次调用onDraw，分配内存
                mWeakBitmap = new WeakReference<Bitmap>(bitmap);
            }
        }
        //如果bitmap还存在，则直接绘制即可
        if (bitmap != null) {
            mPaint.setXfermode(null);
            canvas.drawBitmap(bitmap, 0.0f, 0.0f, mPaint);
            return;
        }
    }

    /**
     * 绘制形状
     *
     * @return
     */
    public Bitmap getBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Paint paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint1.setColor(Color.BLACK);
        canvas.drawCircle(getWidth() / 2, getWidth() / 2, getWidth() / 2 - BORDER_DEFAULT,
                paint1);

        return bitmap;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        /**
         * 如果类型是圆形，则强制改变view的宽高一致，以小值为准
         */

        int width = Math.min(getMeasuredWidth(), getMeasuredHeight());
        setMeasuredDimension(width, width);
    }

    @Override
    public void invalidate() {
        mWeakBitmap = null;
        if (mMaskBitmap != null) {
            mMaskBitmap.recycle();
            mMaskBitmap = null;
        }
        super.invalidate();
    }
}