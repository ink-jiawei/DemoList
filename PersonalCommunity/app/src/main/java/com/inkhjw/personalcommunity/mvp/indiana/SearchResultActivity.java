package com.inkhjw.personalcommunity.mvp.indiana;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inkhjw.personalcommunity.R;
import com.inkhjw.personalcommunity.adapter.SearchListAdapter;
import com.inkhjw.personalcommunity.base.SpeicalToolbarActivity;
import com.inkhjw.personalcommunity.bean.ShopListBean;
import com.inkhjw.personalcommunity.framework.refresh.DividerItemDecoration;
import com.inkhjw.personalcommunity.helper.ToastHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 搜索结果
 */
public class SearchResultActivity extends SpeicalToolbarActivity implements TextView.OnEditorActionListener {
    //toolbar
    @BindView(R.id.toolbar_left_layout)
    LinearLayout toolbar_left_layout;
    @BindView(R.id.toolbar_left_image)
    ImageView toolbar_left_image;

    @BindView(R.id.toolbar_center_layout)
    LinearLayout toolbar_center_layout;
    @BindView(R.id.toolbar_center_edt)
    EditText toolbar_center_edt;

    @BindView(R.id.toolbar_right_layout)
    RelativeLayout toolbar_right_layout;
    @BindView(R.id.toolbar_right_image)
    ImageView toolbar_right_image;
    @BindView(R.id.toolbar_right_text)
    TextView toolbar_right_text;

    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    @BindView(R.id.toolbar_statusbar)
    FrameLayout toolbar_statusbar;

    @BindView(R.id.indiana_search_shop_num)
    TextView indiana_search_shop_num;
    @BindView(R.id.indiana_search_addlist_all)
    TextView indiana_search_addlist_all;
    @BindView(R.id.recycle_view)
    RecyclerView recycle_view;

    List<ShopListBean> shopLists;
    private SearchListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
    }

    @Override
    protected void init() {
        super.init();
        initRecycleView();
        setSupportBarTint(toolbar);
        toolbar_center_edt.setOnEditorActionListener(this);
        indiana_search_shop_num.setText("共" + shopLists.size() + "件商品");
        toolbar_right_text.setText("2");
    }

    @OnClick({R.id.toolbar_left_layout, R.id.toolbar_right_layout, R.id.indiana_search_addlist_all})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_layout:
                finish();
                overridePendingTransition(R.anim.translate_zero, R.anim.translate_right_out);
                break;
            case R.id.toolbar_right_layout:
                break;
            case R.id.indiana_search_addlist_all:

                break;
        }
    }

    public void initRecycleView() {
        shopLists = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            shopLists.add(new ShopListBean(true, R.drawable.iphone6s, "iphone 6s 128G", 1000, 234));
        }
        adapter = new SearchListAdapter(this, shopLists);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycle_view.setLayoutManager(layoutManager);
        recycle_view.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
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
                        ToastHelper.showToast(SearchResultActivity.this, "到底了！");
                    }
                }
            }
        });
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (i == EditorInfo.IME_NULL && keyEvent.getAction() == KeyEvent.ACTION_UP) {
            String message = textView.getText().toString();

        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
