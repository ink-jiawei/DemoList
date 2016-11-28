package com.inkhjw.personalcommunity.mvp.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;

import com.inkhjw.personalcommunity.R;
import com.inkhjw.personalcommunity.adapter.AddressListAdapter;
import com.inkhjw.personalcommunity.base.NoSupportStatusBackActivity;
import com.inkhjw.personalcommunity.bean.AddressListBean;
import com.inkhjw.personalcommunity.event.AddressEdtEvent;
import com.inkhjw.personalcommunity.framework.refresh.DividerItemDecoration;
import com.inkhjw.personalcommunity.helper.ToastHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 地址管理
 */
public class AddressManagerActivity extends NoSupportStatusBackActivity {


    @BindView(R.id.personal_address_add_new)
    Button personal_address_add_new;
    @BindView(R.id.recycle_view)
    RecyclerView recycle_view;

    List<AddressListBean> addressLists;
    private AddressListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_address_manager);
    }

    @Override
    protected void init() {
        super.init();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        setSupportToolbar(R.drawable.toolbar_back_black, getTextFromId(R.string.user_presonal_address_manager), getTextFromId(R.string.user_presonal_address_add));
        setRightTextColor(R.color.announced_progress_text_color);
        initRecycleView();
        showView();
    }

    @OnClick({R.id.personal_address_add_new})
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.toolbar_right_layout:
            case R.id.personal_address_add_new:
                Intent intentAdd = new Intent(AddressManagerActivity.this, AddressEdtActivity.class);
                startActivity(intentAdd);
                overridePendingTransition(R.anim.translate_right_in, R.anim.translate_zero);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reposeAddressEdt(AddressEdtEvent event) {
        switch (event.statu) {
            case 1:

                break;
            case 2:

                break;
            case 3:
                addressLists.remove(event.position);
                adapter.notifyDataSetChanged();
                showView();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reposeAddressAdd(AddressListBean event) {
        addressLists.add(event);
        adapter.notifyDataSetChanged();
        showView();
    }

    public void initRecycleView() {
        addressLists = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            addressLists.add(new AddressListBean("何佳伟", "15283948394", "广东省", "深圳市", "宝安区", "西乡大道宝源华丰总部经济大厦A栋1116", true));
        }

        adapter = new AddressListAdapter(AddressManagerActivity.this, addressLists);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(AddressManagerActivity.this);
        recycle_view.setLayoutManager(layoutManager);
        recycle_view.addItemDecoration(new DividerItemDecoration(AddressManagerActivity.this, DividerItemDecoration.VERTICAL_LIST));
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
                        ToastHelper.showToast(AddressManagerActivity.this, "到底了！");
                    }
                }
            }
        });
    }

    public void showView() {
        if (addressLists != null && addressLists.size() > 0) {
            recycle_view.setVisibility(View.VISIBLE);
            personal_address_add_new.setVisibility(View.GONE);
        } else {
            recycle_view.setVisibility(View.GONE);
            personal_address_add_new.setVisibility(View.VISIBLE);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}