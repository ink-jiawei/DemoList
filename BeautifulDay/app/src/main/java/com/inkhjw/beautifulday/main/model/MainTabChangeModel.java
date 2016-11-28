package com.inkhjw.beautifulday.main.model;

/**
 * @author hjw
 * @deprecated 主界面Tab切换
 */
public interface MainTabChangeModel {
    void onTabSelected(int position);

    void onTabUnselected(int position);

    void onTabReselected(int position);
}
