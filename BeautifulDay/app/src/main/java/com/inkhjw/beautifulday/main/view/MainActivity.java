package com.inkhjw.beautifulday.main.view;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.inkhjw.beautifulday.R;
import com.inkhjw.beautifulday.base.BaseActivity;
import com.inkhjw.beautifulday.main.contract.MainContract;
import com.inkhjw.beautifulday.main.presenter.MainPresenter;

import butterknife.Bind;

/**
 * @author hjw
 */
public class MainActivity extends BaseActivity implements MainContract.View, BottomNavigationBar.OnTabSelectedListener {

    @Bind(R.id.main_bottom_navigation_bar)
    BottomNavigationBar mainBottomNavigationBar;

    /**
     * 子类自己的Presenter
     */
    private MainContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void initView() {
        super.initView();
        initToolbar(true, getResources().getString(R.string.app_name), 0);
        new MainPresenter(this);
        mainBottomNavigationBar.setTabSelectedListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        mainBottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        mainBottomNavigationBar
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC
                );
        mainBottomNavigationBar
                .setActiveColor(R.color.app_theme)
                .setInActiveColor(R.color.main_bottom_item_default_color)
                .setBarBackgroundColor(R.color.app_white);
        mainBottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.main_bottom_item1, "Home").setActiveColorResource(R.color.app_theme))
                .addItem(new BottomNavigationItem(R.drawable.main_bottom_item2, "Books").setActiveColorResource(R.color.app_theme))
                .addItem(new BottomNavigationItem(R.drawable.main_bottom_item3, "Music").setActiveColorResource(R.color.app_theme))
                .addItem(new BottomNavigationItem(R.drawable.main_bottom_item4, "Music").setActiveColorResource(R.color.app_theme))
                .addItem(new BottomNavigationItem(R.drawable.main_bottom_item5, "Games").setActiveColorResource(R.color.app_theme))
                .setFirstSelectedPosition(0)
                .initialise();

        setDefaultFragment();
    }

    /**
     * 设置默认的
     */
    private void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.main_content, new MainFragment1());
        transaction.commit();
    }

    @Override
    public void onTabSelected(int position) {
        presenter.onTabSelected(position);
    }

    @Override
    public void onTabUnselected(int position) {
        presenter.onTabUnselected(position);
    }

    @Override
    public void onTabReselected(int position) {
        presenter.onTabReselected(position);
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
