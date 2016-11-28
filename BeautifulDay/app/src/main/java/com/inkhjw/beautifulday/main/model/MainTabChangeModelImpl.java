package com.inkhjw.beautifulday.main.model;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.inkhjw.beautifulday.R;
import com.inkhjw.beautifulday.main.view.user.FragmentUser;
import com.inkhjw.beautifulday.main.view.MainFragment1;
import com.inkhjw.beautifulday.main.view.weather.MainFragmentWeather;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hjw
 * @deprecated
 */
public class MainTabChangeModelImpl implements MainTabChangeModel {
    private List<Fragment> fragments;
    private FragmentActivity activity;

    public MainTabChangeModelImpl(FragmentActivity activity) {
        this.activity = activity;

        fragments = new ArrayList<>();
        fragments.add(new MainFragment1());
        fragments.add(new MainFragment1());
        fragments.add(new MainFragmentWeather());
        fragments.add(new MainFragment1());
        fragments.add(new FragmentUser());
    }

    @Override
    public void onTabSelected(int position) {
        if (fragments != null) {
            if (position < fragments.size()) {
                FragmentManager fm = activity.getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = fragments.get(position);
                if (fragment.isAdded()) {
                    ft.replace(R.id.main_content, fragment);
                } else {
                    ft.add(R.id.main_content, fragment);
                }
                ft.commitAllowingStateLoss();
            }
        }

    }

    @Override
    public void onTabUnselected(int position) {
        if (fragments != null) {
            if (position < fragments.size()) {
                FragmentManager fm = activity.getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = fragments.get(position);
                ft.remove(fragment);
                ft.commitAllowingStateLoss();
            }
        }
    }

    @Override
    public void onTabReselected(int position) {

    }
}
