package com.inkhjw.beautifulday.main.view.weather;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inkhjw.beautifulday.R;
import com.inkhjw.beautifulday.base.BaseFragment;

/**
 * @author hjw
 * @deprecated
 */
public class MainFragmentWeather extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView(inflater);
    }

    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_main_weather, null);
        return view;
    }
}
