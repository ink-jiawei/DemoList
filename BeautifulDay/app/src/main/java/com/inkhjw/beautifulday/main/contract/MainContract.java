package com.inkhjw.beautifulday.main.contract;

import com.inkhjw.beautifulday.base.BasePresenter;
import com.inkhjw.beautifulday.base.BaseView;

/**
 * @author hjw
 * @deprecated
 */
public interface MainContract {

    interface View extends BaseView<Presenter> {
        void onTabSelected(int position);

        void onTabUnselected(int position);

        void onTabReselected(int position);
    }

    interface Presenter extends BasePresenter {
        void onTabSelected(int position);

        void onTabUnselected(int position);

        void onTabReselected(int position);
    }
}