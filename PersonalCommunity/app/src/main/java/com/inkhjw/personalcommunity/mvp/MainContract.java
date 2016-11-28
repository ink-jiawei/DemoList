package com.inkhjw.personalcommunity.mvp;


import com.inkhjw.personalcommunity.base.BasePresenter;
import com.inkhjw.personalcommunity.base.BaseView;

/**
 * @deprecated
 */
public interface MainContract {

    interface View extends BaseView<Presenter> {
    }

    interface Presenter extends BasePresenter {

        void search();

        void edit();
    }
}