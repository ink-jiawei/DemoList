package com.inkhjw.toucheventdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author hjw
 */

public class CusView extends View {
    public CusView(Context context) {
        super(context);
    }

    public CusView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            Log.e("debug", "View->dispatchTouchEvent");
        }
        // return false;
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Log.e("debug", "View->onTouchEvent");
        }
        // return false;
        return super.onTouchEvent(event);
    }
}
