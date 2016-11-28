package com.inkhjw.personalcommunity.mvp.indiana;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inkhjw.personalcommunity.R;
import com.inkhjw.personalcommunity.adapter.HistroyListAdapter;
import com.inkhjw.personalcommunity.base.SpeicalToolbarActivity;
import com.inkhjw.personalcommunity.framework.refresh.DividerItemDecoration;
import com.inkhjw.personalcommunity.helper.SharePerferenceHelper;
import com.inkhjw.personalcommunity.helper.ToastHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 搜索
 */
public class SearchActivity extends SpeicalToolbarActivity implements TextView.OnEditorActionListener
        , HistroyListAdapter.OnItemClickListener {
    //toolbar
    @BindView(R.id.toolbar_left_layout)
    LinearLayout toolbar_left_layout;
    @BindView(R.id.toolbar_left_image)
    ImageView toolbar_left_image;

    @BindView(R.id.toolbar_center_layout)
    LinearLayout toolbar_center_layout;
    @BindView(R.id.toolbar_center_edt)
    EditText toolbar_center_edt;

    @BindView(R.id.toolbar)
    LinearLayout toolbar;
    @BindView(R.id.toolbar_statusbar)
    FrameLayout toolbar_statusbar;

    @BindView(R.id.indiana_search_clear)
    TextView indiana_search_clear;
    @BindView(R.id.recycle_view)
    RecyclerView recycle_view;

    List<String> searchTexts;
    private HistroyListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    @Override
    protected void init() {
        super.init();
        initRecycleView();
        setSupportBarTint(toolbar);
        toolbar_center_edt.setOnEditorActionListener(this);
    }

    @OnClick({R.id.toolbar_left_layout, R.id.indiana_search_clear})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_layout:
                finish();
                overridePendingTransition(R.anim.translate_zero, R.anim.translate_right_out);
                break;
            case R.id.indiana_search_clear:
                if (searchTexts != null) {
                    searchTexts.clear();
                }
                adapter.notifyDataSetChanged();

                SharePerferenceHelper.StoreData.clear();
                break;
        }
    }

    public void initRecycleView() {
        searchTexts = new ArrayList<>();

        String[] histroys = SharePerferenceHelper.StoreData.getHistroySearch().split(",");
        for (int i = histroys.length - 1; i >= 0; i--) {
            if (!TextUtils.isEmpty(histroys[i]) && !searchTexts.contains(histroys[i])) {
                searchTexts.add(histroys[i]);
            }
        }
        adapter = new HistroyListAdapter(this, searchTexts);
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
                        ToastHelper.showToast(SearchActivity.this, "到底了！");
                    }
                }
            }
        });
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(int position) {
        if (position < searchTexts.size()) {
            String result = searchTexts.get(position);
            ToastHelper.showToast(SearchActivity.this, "result=" + result);
        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (i == EditorInfo.IME_NULL && keyEvent.getAction() == KeyEvent.ACTION_UP) {
            String text = textView.getText().toString();

            if (text.equals("Intent")) {
                Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.translate_right_in, R.anim.translate_zero);
            } else {
                ToastHelper.showToast(SearchActivity.this, "请输入Intent跳转");
            }
            //存储历史搜索记录
            if (!TextUtils.isEmpty(text) && !searchTexts.contains(text)) {
                searchTexts.add(0, text);
            }
            adapter.notifyDataSetChanged();
            SharePerferenceHelper.StoreData.addHistroySearch(text);
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