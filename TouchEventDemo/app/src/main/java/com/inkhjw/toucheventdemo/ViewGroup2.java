package com.inkhjw.toucheventdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * @author hjw
 */

public class ViewGroup2 extends FrameLayout {
    public ViewGroup2(Context context) {
        super(context);
    }

    public ViewGroup2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewGroup2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            Log.e("debug", "ViewGroup2->dispatchTouchEvent");
        }
        return false;
        //return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            Log.e("debug", "ViewGroup2->onInterceptTouchEvent");
        }
        // return true;
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Log.e("debug", "ViewGroup2->onTouchEvent");
        }
        // return true;
        return super.onTouchEvent(event);
    }
}
