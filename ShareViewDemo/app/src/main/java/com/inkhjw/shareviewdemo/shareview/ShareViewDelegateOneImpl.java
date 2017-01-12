package com.inkhjw.shareviewdemo.shareview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * @author hjw
 */

public class ShareViewDelegateOneImpl extends ShareViewDelegate implements OneInterface {
    private View view;
    private Activity activity;

    @Override
    public ShareViewDelegate target(View view) {
        this.view = view;
        return this;
    }

    @Override
    public ShareViewDelegate with(Activity activity) {
        this.activity = activity;
        return this;
    }

    @Override
    public ShareViewDelegate bindIntent(Intent intent) {
        if (view == null) {
            throw new NullPointerException("please support target(view) method");
        }
        if (activity == null) {
            throw new NullPointerException("please support with(activity) method");
        }

        intent.putExtras(capture(view));
        activity.startActivity(intent);
        activity.overridePendingTransition(0, 0);
        return this;
    }

    private Bundle capture(@NonNull View view) {
        Bundle b = new Bundle();
        int[] screenLocation = new int[2];
        view.getLocationInWindow(screenLocation);
        b.putInt(SCREEN_LOCATION_LEFT, (int) view.getX());
        b.putInt(SCREEN_LOCATION_TOP, (int) view.getY());
        b.putInt(VIEW_WIDTH, view.getWidth());
        b.putInt(VIEW_HEIGHT, view.getHeight());

        return b;
    }

    @Override
    public ShareViewDelegate parseIntent(Intent intent) {
        return this;
    }

    @Override
    public ShareViewDelegate makeSceneTransitionAnimation(long duration) {
        return this;
    }

    @Override
    public ShareViewDelegate exitSceneTransitionAnimation() {
        return this;
    }
}
