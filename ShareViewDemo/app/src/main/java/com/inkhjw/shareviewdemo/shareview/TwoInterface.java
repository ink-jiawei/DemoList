package com.inkhjw.shareviewdemo.shareview;

import android.content.Intent;

/**
 * @author hjw
 */

public interface TwoInterface {

    ShareViewDelegate parseIntent(Intent intent);

    ShareViewDelegate makeSceneTransitionAnimation(long duration);

    ShareViewDelegate exitSceneTransitionAnimation();
}
