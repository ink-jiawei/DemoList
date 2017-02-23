package com.inkhjw.dragvalidationdemo.widget;

import android.graphics.Color;

/**
 * @author hjw
 *         参数
 */

public class DragAttribute {
    public static final int DRAG_DIRECTION_LEFT = 1;
    public static final int DRAG_DIRECTION_TOP = 2;
    public static final int DRAG_DIRECTION_RIGHT = 3;
    public static final int DRAG_DIRECTION_BOTTOM = 4;

    public static final int DEFAULT_DRAG_DIRECTION = DRAG_DIRECTION_LEFT;
    public static final int DEFAULT_TIP_TEXT_COLOR = Color.WHITE;
    public static final float DEFAULT_TIP_TEXT_SIZE = 14;

    private String tipText;
    private int tipTextColor;
    private float tipTextSize;
    private int dragDirection;

    public DragAttribute() {
    }

    public String getTipText() {
        return tipText;
    }

    public void setTipText(String tipText) {
        this.tipText = tipText;
    }

    public int getTipTextColor() {
        return tipTextColor;
    }

    public void setTipTextColor(int tipTextColor) {
        this.tipTextColor = tipTextColor;
    }

    public float getTipTextSize() {
        return tipTextSize;
    }

    public void setTipTextSize(float tipTextSize) {
        this.tipTextSize = tipTextSize;
    }

    public int getDragDirection() {
        return dragDirection;
    }

    public void setDragDirection(int dragDirection) {
        this.dragDirection = dragDirection;
    }
}
