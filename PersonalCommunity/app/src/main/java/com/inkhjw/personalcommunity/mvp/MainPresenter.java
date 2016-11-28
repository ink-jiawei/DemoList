package com.inkhjw.personalcommunity.mvp;

/**
 * @author hjw
 * @deprecated
 */
public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;
    private MainTabChangeModelImpl mainTabChangeModel;

    public MainPresenter(MainContract.View view) {
        this.view = view;
        this.mainTabChangeModel = new MainTabChangeModelImpl();
        view.setPresenter(this);
    }

    @Override
    public void search() {

    }

    @Override
    public void edit() {

    }

    @Override
    public void onStart() {
    }

    @Override
    public void onDestroy() {
        view = null;
    }
}
