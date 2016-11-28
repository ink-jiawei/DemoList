package com.inkhjw.beautifulday.main.presenter;

import android.support.v4.app.FragmentActivity;

import com.inkhjw.beautifulday.main.contract.MainContract;
import com.inkhjw.beautifulday.main.model.MainTabChangeModelImpl;

/**
 * @author hjw
 * @deprecated
 */
public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;
    private MainTabChangeModelImpl mainTabChangeModel;

    public MainPresenter(MainContract.View view) {
        this.view = view;
        this.mainTabChangeModel = new MainTabChangeModelImpl((FragmentActivity) view);
        view.setPresenter(this);
    }

    public void onTabSelected(int position) {
        mainTabChangeModel.onTabSelected(position);
    }

    public void onTabUnselected(int position) {
        mainTabChangeModel.onTabUnselected(position);
    }

    public void onTabReselected(int position) {
        mainTabChangeModel.onTabReselected(position);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {
        view = null;
    }
}
