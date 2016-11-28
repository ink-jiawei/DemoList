package com.inkhjw.personalcommunity.base;


/**
 * @author hjw
 * @deprecated MVP架构模式中Presenter基类
 */
public interface BasePresenter {
    /**
     * 一些初始化工作
     */
    void onStart();

    /**
     * 资源的释放
     */
    void onDestroy();
}
