package com.inkhjw.shareviewdemo.shareview;

import android.app.Activity;

/**
 * @author hjw
 */

public interface ActivityTransparentAnimation {

    ActivityTransparentAnimation defaultActivityAnim = new ActivityTransparentAnimation() {
        @Override
        public void enterAnim(Activity activity) {
        }

        @Override
        public void exitAnim(Activity activity) {

        }
    };

    void enterAnim(Activity activity);

    void exitAnim(Activity activity);
}
