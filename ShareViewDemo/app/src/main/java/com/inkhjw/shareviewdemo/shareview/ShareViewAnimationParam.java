package com.inkhjw.shareviewdemo.shareview;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author hjw
 */

public class ShareViewAnimationParam implements Parcelable {
    private float x;
    private float y;
    private float scaleX;
    private float scaleY;
    private float translationX;
    private float translationY;

    public ShareViewAnimationParam(float x, float y, float scaleX, float scaleY, float translationX, float translationY) {
        this.x = x;
        this.y = y;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.translationX = translationX;
        this.translationY = translationY;
    }

    protected ShareViewAnimationParam(Parcel in) {
        x = in.readFloat();
        y = in.readFloat();
        scaleX = in.readFloat();
        scaleY = in.readFloat();
        translationX = in.readFloat();
        translationY = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(x);
        dest.writeFloat(y);
        dest.writeFloat(scaleX);
        dest.writeFloat(scaleY);
        dest.writeFloat(translationX);
        dest.writeFloat(translationY);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShareViewAnimationParam> CREATOR = new Creator<ShareViewAnimationParam>() {
        @Override
        public ShareViewAnimationParam createFromParcel(Parcel in) {
            return new ShareViewAnimationParam(in);
        }

        @Override
        public ShareViewAnimationParam[] newArray(int size) {
            return new ShareViewAnimationParam[size];
        }
    };

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getScaleX() {
        return scaleX;
    }

    public void setScaleX(float scaleX) {
        this.scaleX = scaleX;
    }

    public float getScaleY() {
        return scaleY;
    }

    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
    }

    public float getTranslationX() {
        return translationX;
    }

    public void setTranslationX(float translationX) {
        this.translationX = translationX;
    }

    public float getTranslationY() {
        return translationY;
    }

    public void setTranslationY(float translationY) {
        this.translationY = translationY;
    }
}
