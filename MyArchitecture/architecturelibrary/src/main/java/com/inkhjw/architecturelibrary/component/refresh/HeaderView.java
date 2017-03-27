package com.inkhjw.architecturelibrary.component.refresh;

import android.view.View;

import java.util.ArrayList;

/**
 * @author hjw
 *         决定头部视图的行为
 */

abstract class HeaderView {
    int STATUS_LESS_HEADER = 0;//下拉距离小于头部高度
    int STATUS_MORE_HEADER = 0;//下拉距离大于头部高度
    int STATUS_REFRESH_HEADER = 0;//下拉距离等于头部高度，且正在刷新

    public static HeaderView defaultHeaderView = new HeaderView() {
        @Override
        void statusLessHeader() {

        }

        @Override
        void statusMoreHeader() {

        }

        @Override
        void statusRefreshHeader() {

        }
    };

    abstract void statusLessHeader();

    abstract void statusMoreHeader();

    abstract void statusRefreshHeader();

    private ArrayList<View> headerViews = new ArrayList<>();

    public void addView(View view) {
        headerViews.add(view);
    }

    public void removeView(View view) {
        if (headerViews.contains(view)) {
            headerViews.remove(view);
        }
    }

    public ArrayList<View> getHeaderViews() {
        return headerViews;
    }
}
