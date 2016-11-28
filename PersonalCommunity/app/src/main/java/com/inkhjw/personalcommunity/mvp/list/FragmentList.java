package com.inkhjw.personalcommunity.mvp.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.TextView;

import com.inkhjw.personalcommunity.R;
import com.inkhjw.personalcommunity.adapter.ListListAdapter;
import com.inkhjw.personalcommunity.base.BaseFragment;
import com.inkhjw.personalcommunity.bean.ShopListBean;
import com.inkhjw.personalcommunity.event.ListSelectedEvent;
import com.inkhjw.personalcommunity.framework.refresh.DividerItemDecoration;
import com.inkhjw.personalcommunity.helper.ToastHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * @deprecated 清单界面
 */

public class FragmentList extends BaseFragment {
    @BindView(R.id.ptr_classic_frame_layout)
    PtrClassicFrameLayout ptr_classic_frame_layout;
    @BindView(R.id.recycle_view)
    RecyclerView recycle_view;

    @BindView(R.id.list_selected_all)
    TextView list_selected_all;
    @BindView(R.id.list_selected_num)
    TextView list_selected_num;
    @BindView(R.id.list_selected_btn)
    Button list_selected_btn;

    List<ShopListBean> shopLists;
    private ListListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return setContentView(inflater, R.layout.fragment_list);
    }

    @Override
    public void init(View view) {
        super.init(view);
        if (!isInit) {
            isInit = true;
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this);
            }
        }
        initRecycleView();
        initRefresh();
        list_selected_num.setText("共选中" + 0 + "件商品");
        setSupportToolbar(0, getTextFromId(R.string.main_list), getTextFromId(R.string.edt));
    }

    @Override
    @OnClick({R.id.list_selected_all, R.id.list_selected_btn})
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.toolbar_right_layout:
                if (getRightText().equals(getTextFromId(R.string.edt))) {
                    setRightText(getTextFromId(R.string.cancel));
                    list_selected_btn.setText(R.string.delete);
                    list_selected_btn.setTextColor(getResources().getColor(R.color.appThemeColor));
                    list_selected_btn.setBackgroundResource(R.drawable.add_to_list_btn);
                } else {
                    setRightText(getTextFromId(R.string.edt));
                    list_selected_btn.setText(R.string.list_put);
                    list_selected_btn.setTextColor(getResources().getColor(R.color.white));
                    list_selected_btn.setBackgroundResource(R.drawable.user_chongzhi_click_selector);

                    for (int i = 0; i < shopLists.size(); i++) {
                        ListListAdapter.BindViewHolder viewHolder = (ListListAdapter.BindViewHolder) recycle_view.getChildViewHolder(recycle_view.getChildAt(i));
                        viewHolder.list_item_checked.setSelected(false);
                    }
                    list_selected_all.setSelected(false);
                }
                updateSelected();
                break;

            case R.id.list_selected_all:
                boolean isSelected = list_selected_all.isSelected();

                for (int i = 0; i < shopLists.size(); i++) {
                    ListListAdapter.BindViewHolder viewHolder = (ListListAdapter.BindViewHolder) recycle_view.getChildViewHolder(recycle_view.getChildAt(i));
                    viewHolder.list_item_checked.setSelected(!isSelected);
                }
                list_selected_all.setSelected(!isSelected);
                updateSelected();
                break;
            case R.id.list_selected_btn:
                //结算
                if ((list_selected_btn.getText().toString().trim()).equals(getTextFromId(R.string.list_put))) {
                    ToastHelper.showToast(getSuperActivity(), "结算");
                }
                //删除
                else {
                    ToastHelper.showToast(getSuperActivity(), "删除");
                }
                break;
        }
    }

    public void initRecycleView() {
        shopLists = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            shopLists.add(new ShopListBean(R.drawable.iphone6s, "iphone 6s 128G", 1000, 234));
        }
        adapter = new ListListAdapter(getContext(), shopLists);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getSuperActivity());
        recycle_view.setLayoutManager(layoutManager);
        recycle_view.addItemDecoration(new DividerItemDecoration(getSuperActivity(), DividerItemDecoration.VERTICAL_LIST));
        recycle_view.setAdapter(adapter);

        //添加滚动监听器
        recycle_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                // 当不滚动时
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    // 判断是否滚动到底部
                    //得到可见视图项最底层id
                    int lostPos = layoutManager.findLastVisibleItemPosition();
                    if (lostPos == layoutManager.getItemCount() - 1) {
                        ToastHelper.showToast(getSuperActivity(), "到底了！");
                    }
                }
            }
        });
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reposeSelected(ListSelectedEvent event) {
        updateSelected();
    }

    /**
     * 更新选中的数量
     */
    public void updateSelected() {
        int n = 0;
        for (int i = 0; i < shopLists.size(); i++) {
            ListListAdapter.BindViewHolder viewHolder = (ListListAdapter.BindViewHolder) recycle_view.getChildViewHolder(recycle_view.getChildAt(i));
            if (viewHolder.list_item_checked.isSelected()) {
                n++;
            }
        }
        list_selected_num.setText("共选中" + n + "件商品");

        if (n == shopLists.size()) {
            list_selected_all.setSelected(true);
        } else if (list_selected_all.isSelected()) {
            list_selected_all.setSelected(false);
        }
    }


    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
