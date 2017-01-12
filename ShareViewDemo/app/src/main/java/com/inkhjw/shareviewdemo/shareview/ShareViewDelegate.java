package com.inkhjw.shareviewdemo.shareview;

import android.app.Activity;
import android.support.annotation.IntDef;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author hjw
 *         实现Activity切换时共享元素的代理类<兼容API 16以下>
 */

public abstract class ShareViewDelegate implements OneInterface, TwoInterface {
    public static final int ACTIVITY_START = 0;
    public static final int ACTIVITY_END = 1;

    public static final String SCREEN_LOCATION_LEFT = String.valueOf(0X1110);
    public static final String SCREEN_LOCATION_TOP = String.valueOf(0X1111);
    public static final String VIEW_WIDTH = String.valueOf(0X1112);
    public static final String VIEW_HEIGHT = String.valueOf(0X1113);


    @IntDef({ACTIVITY_START, ACTIVITY_END})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ActivityType {
    }

    ShareViewDelegate() {
    }

    public static ShareViewDelegate create(@ActivityType int type) {
        return createT(type);
    }

    private static ShareViewDelegate createT(@ActivityType int type) {
        if (type == ACTIVITY_START) {
            return new ShareViewDelegateOneImpl();
        } else if (type == ACTIVITY_END) {
            return new ShareViewDelegateTwoImpl();
        } else {
            throw new IllegalArgumentException("this type must be ACTIVITY_START or ACTIVITY_END");
        }
    }

    public abstract ShareViewDelegate target(View view);

    public abstract ShareViewDelegate with(Activity activity);
}
