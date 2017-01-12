package com.inkhjw.shareviewdemo.shareview;

import android.animation.Animator;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * @author hjw
 */

public class ShareViewAnimation {
    ViewPropertyAnimator animator;

    public ShareViewAnimation(ViewPropertyAnimator animator) {
        this.animator = animator;
    }


    public static class Builder {
        private View view;
        private long duration;
        private float x;
        private float y;
        private float scaleX;
        private float scaleY;
        private float translationX;
        private float translationY;

        public Builder(View view) {
            this.view = view;
        }

        public Builder duration(long duration) {
            this.duration = duration;
            return this;
        }

        public Builder x(float x) {
            this.x = x;
            return this;
        }

        public Builder y(float y) {
            this.y = y;
            return this;
        }

        public Builder scaleX(float scaleX) {
            this.scaleX = scaleX;
            return this;
        }

        public Builder scaleY(float scaleY) {
            this.scaleY = scaleY;
            return this;
        }

        public Builder translationX(float translationX) {
            this.translationX = translationX;
            return this;
        }

        public Builder translationY(float translationY) {
            this.translationY = translationY;
            return this;
        }

        public ShareViewAnimation create() {
            view.setVisibility(View.VISIBLE);
            view.setPivotX(x);
            view.setPivotY(y);
            ViewPropertyAnimator animator = view.animate()
                    .setDuration(duration)
                    .setInterpolator(new AccelerateDecelerateInterpolator())
                    .scaleX(scaleX)
                    .scaleY(scaleY)
                    .translationX(translationX)
                    .translationY(translationY);

            ShareViewAnimation anim = new ShareViewAnimation(animator);
            return anim;
        }
    }

    public void startAnim() {
        animator.start();
    }

    public void setAnimatorListener(Animator.AnimatorListener listener) {
        animator.setListener(listener);
    }

    public void cancelAnim() {
        if (animator != null) {
            animator.cancel();
            animator = null;
        }
    }
}
