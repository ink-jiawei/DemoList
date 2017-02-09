package com.inkhjw.beautifulday.main.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inkhjw.beautifulday.R;
import com.inkhjw.beautifulday.base.BaseFragment;

/**
 * @author hjw
 */
public class MainFragment1 extends BaseFragment {
    public MainFragment1() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_fragment1, null);
        return view;
    }
}
