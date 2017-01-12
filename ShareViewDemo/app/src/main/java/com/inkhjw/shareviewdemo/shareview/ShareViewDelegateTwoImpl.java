package com.inkhjw.shareviewdemo.shareview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;

import com.inkhjw.shareviewdemo.R;

/**
 * @author hjw
 */

public class ShareViewDelegateTwoImpl extends ShareViewDelegate implements TwoInterface {
    Activity activity;
    private View view;
    Bundle bundle;
    ShareViewAnimationParam param;
    long duration;

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
        return this;
    }

    @Override
    public ShareViewDelegate parseIntent(Intent intent) {
        bundle = intent.getExtras();
        return this;
    }

    @Override
    public ShareViewDelegate makeSceneTransitionAnimation(final long duration) {
        if (view == null) {
            throw new NullPointerException("please support target(view) method");
        }
        if (activity == null) {
            throw new NullPointerException("please support with(activity) method");
        }
        if (bundle == null) {
            throw new NullPointerException("please support parseIntent(intent) method");
        }

        this.duration = duration;
        view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                view.getViewTreeObserver().removeOnPreDrawListener(this);
                startSceneTransitionAnimation(capture(), duration);
                return true;
            }
        });
        return this;
    }

    @Override
    public ShareViewDelegate exitSceneTransitionAnimation() {
        if (view == null) {
            throw new NullPointerException("please support target(view) method");
        }
        if (activity == null) {
            throw new NullPointerException("please support with(activity) method");
        }

        exitSceneTransitionAnimation(param, duration);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                activity.finish();
                activity.overridePendingTransition(0, R.anim.exit);
            }
        }, duration);
        return this;
    }

    private ShareViewAnimationParam capture() {
        int[] screenLocation = new int[2];
        view.getLocationInWindow(screenLocation);

        float locationLeft = bundle.getInt(SCREEN_LOCATION_LEFT);
        float locationTop = bundle.getInt(SCREEN_LOCATION_TOP);
        float viewWidth = bundle.getInt(VIEW_WIDTH);
        float viewHeight = bundle.getInt(VIEW_HEIGHT);

        float targetLocationLeft = (int) view.getX();
        float targetLocationTop = (int) view.getY();
        float targetViewWidth = view.getWidth();
        float targetViewHeight = view.getHeight();

        float scaleX = targetViewWidth / viewWidth;
        float scaleY = targetViewHeight / viewHeight;
        float translationX = targetLocationLeft - locationLeft;
        float translationY = targetLocationTop - locationTop;

        param = new ShareViewAnimationParam(locationLeft, locationTop, scaleX, scaleY, translationX, translationY);
        return param;
    }

    private void startSceneTransitionAnimation(ShareViewAnimationParam param, long duration) {
        activity.getWindow().getDecorView().findViewById(android.R.id.content)
                .animate()
                .setDuration(duration)
                .alpha(1)
                .start();

        new ShareViewAnimation.Builder(view)
                .duration(0)
                .x(param.getX())
                .y(param.getY())
                .scaleX(1 / param.getScaleX())
                .scaleY(1 / param.getScaleY())
                .translationX(-param.getTranslationX())
                .translationY(-param.getTranslationY())
                .create()
                .startAnim();

        ShareViewAnimation shareViewAnim = new ShareViewAnimation.Builder(view)
                .duration(duration)
                .x(param.getX())
                .y(param.getY())
                .scaleX(param.getScaleX())
                .scaleY(param.getScaleY())
                .translationX(param.getTranslationX())
                .translationY(param.getTranslationY())
                .create();
        shareViewAnim.startAnim();
    }

    private void exitSceneTransitionAnimation(ShareViewAnimationParam param, long duration) {
        ShareViewAnimation shareViewAnim = new ShareViewAnimation.Builder(view)
                .duration(duration)
                .x(param.getX())
                .y(param.getY())
                .scaleX(1 / param.getScaleX())
                .scaleY(1 / param.getScaleY())
                .translationX(-param.getTranslationX())
                .translationY(-param.getTranslationY())
                .create();

        shareViewAnim.startAnim();
    }
}
