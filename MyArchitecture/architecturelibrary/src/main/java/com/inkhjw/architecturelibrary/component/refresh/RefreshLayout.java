package com.inkhjw.architecturelibrary.component.refresh;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author hjw
 */

public abstract class RefreshLayout extends ViewGroup {
    private HeaderView headerView;

    public RefreshLayout(Context context) {
        super(context);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RefreshLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (headerView != null) {
            measureChildWithMargins(headerView.getHeaderViews().get(0), widthMeasureSpec, 0, heightMeasureSpec, 0);
        }

        /**
         * 测量孩子
         */
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child != null) {
                measureContentView(child, widthMeasureSpec, heightMeasureSpec);
            }
        }
    }

    private void measureContentView(View child,
                                    int parentWidthMeasureSpec,
                                    int parentHeightMeasureSpec) {
        final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

        final int childWidthMeasureSpec = getChildMeasureSpec(parentWidthMeasureSpec,
                getPaddingLeft() + getPaddingRight() + lp.leftMargin + lp.rightMargin, lp.width);
        final int childHeightMeasureSpec = getChildMeasureSpec(parentHeightMeasureSpec,
                getPaddingTop() + getPaddingBottom() + lp.topMargin, lp.height);

        child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        layoutChildren();
    }

    private void layoutChildren() {
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();

        // header layout
        // content layout
    }

    public void setHeaderView(View view) {
        setHeaderView(null, view);
    }

    public void setHeaderView(HeaderView tHeaderView, View view) {
        if (tHeaderView == null) {
            headerView = HeaderView.defaultHeaderView;
        } else {
            headerView = tHeaderView;
        }
        headerView.addView(view);
    }

    interface RefreshListener {
        void refreshing();

        void refreshFinish();
    }
}
