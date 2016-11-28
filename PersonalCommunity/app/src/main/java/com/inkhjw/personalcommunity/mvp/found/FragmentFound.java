package com.inkhjw.personalcommunity.mvp.found;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.inkhjw.personalcommunity.R;
import com.inkhjw.personalcommunity.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @deprecated 发现
 */

public class FragmentFound extends BaseFragment {
    @BindView(R.id.found_item1)
    RelativeLayout found_item1;
    @BindView(R.id.found_item2)
    RelativeLayout found_item2;
    @BindView(R.id.found_item3)
    RelativeLayout found_item3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = setContentView(inflater, R.layout.fragment_found);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void init(View view) {
        super.init(view);
        if (!isInit) {
            isInit = true;
        }
        setSupportToolbar(0, getTextFromId(R.string.main_found), 0);
    }

    @OnClick({R.id.found_item1, R.id.found_item2, R.id.found_item3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.found_item1:
                break;
            case R.id.found_item2:
                break;
            case R.id.found_item3:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
