package com.inkhjw.edittextcontainer;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * @author hjw
 */

public class EditTextContainer extends ViewGroup {
    public EditTextContainer(Context context) {
        super(context);
    }

    public EditTextContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditTextContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public EditTextContainer(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
