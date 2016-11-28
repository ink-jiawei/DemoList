package com.inkhjw.personalcommunity.mvp.announced;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inkhjw.personalcommunity.R;
import com.inkhjw.personalcommunity.adapter.AnnouncedListAdapter;
import com.inkhjw.personalcommunity.base.BaseFragment;
import com.inkhjw.personalcommunity.bean.AnnouncedShopBean;
import com.inkhjw.personalcommunity.framework.refresh.DividerGridItemDecoration;
import com.inkhjw.personalcommunity.helper.ToastHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * @deprecated 夺宝界面
 */

public class FragmentAnnounced extends BaseFragment {

    @BindView(R.id.ptr_classic_frame_layout)
    PtrClassicFrameLayout ptr_classic_frame_layout;
    @BindView(R.id.recycle_view)
    RecyclerView recycle_view;

    List<AnnouncedShopBean> shopLists;
    private AnnouncedListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return setContentView(inflater, R.layout.fragment_announced);
    }

    @Override
    public void init(View view) {
        super.init(view);
        if (!isInit) {
            isInit = true;
        }
        initRecycleView();
        initRefresh();
        setSupportToolbar(0, getTextFromId(R.string.main_announced), 0);
    }

    public void initRecycleView() {
        shopLists = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            shopLists.add(new AnnouncedShopBean(R.drawable.iphone6s, "iphone 6s 128G", 37483 * i + "", new Date().getTime() + 1000 * 5 * i));
        }
        adapter = new AnnouncedListAdapter(getContext(), shopLists);
        recycle_view.setLayoutManager(new GridLayoutManager(getSuperActivity(), 2));
        recycle_view.addItemDecoration(new DividerGridItemDecoration(getSuperActivity()));
        recycle_view.setAdapter(adapter);
    }

    /**
     * 上拉刷新/下拉加载的初始化
     */
    protected void initRefresh() {
        ptr_classic_frame_layout.setLoadingMinTime(1000);
        ptr_classic_frame_layout.setPtrHandler(
                new PtrHandler() {
                    @Override
                    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                        // here check list view, not content.
                        return PtrDefaultHandler.checkContentCanBePulledDown(frame, recycle_view, header);
                    }

                    @Override
                    public void onRefreshBegin(PtrFrameLayout frame) {
                        ptr_classic_frame_layout.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ptr_classic_frame_layout.refreshComplete();
                                ToastHelper.showToast(getSuperActivity(), "下拉刷新");
                            }
                        }, 2000);
                    }
                }
        );

        //上拉加载
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
