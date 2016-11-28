package com.inkhjw.personalcommunity.mvp.indiana;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inkhjw.personalcommunity.R;
import com.inkhjw.personalcommunity.adapter.IndianaListAdapter;
import com.inkhjw.personalcommunity.base.BaseFragment;
import com.inkhjw.personalcommunity.bean.ShopListBean;
import com.inkhjw.personalcommunity.framework.banner.Banner;
import com.inkhjw.personalcommunity.framework.banner.BannerConfig;
import com.inkhjw.personalcommunity.framework.banner.Transformer;
import com.inkhjw.personalcommunity.framework.imageloader.GlideImageLoader;
import com.inkhjw.personalcommunity.framework.refresh.DividerGridItemDecoration;
import com.inkhjw.personalcommunity.framework.refresh.HeaderAndFooterWrapper;
import com.inkhjw.personalcommunity.helper.ToastHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * @deprecated 夺宝界面
 */

public class FragmentIndiana extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.ptr_classic_frame_layout)
    PtrClassicFrameLayout ptr_classic_frame_layout;
    @BindView(R.id.recycle_view)
    RecyclerView recycle_view;

    private TextView indiana_sound_user_name;
    private TextView indiana_sound_time;
    private TextView indiana_sound_shop_name;

    List<Integer> banners;
    List<ShopListBean> shopLists;
    private IndianaListAdapter adapter;

    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return setContentView(inflater, R.layout.fragment_indiana);
    }

    @Override
    public void init(View view) {
        super.init(view);
        if (!isInit) {
            isInit = true;
        }
        initRecycleView();
        initRefresh();
        setSupportToolbar(R.drawable.toolbar_search, R.drawable.main_title, R.drawable.toolbar_message);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_layout:
                Intent intentSearch = new Intent(getSuperActivity(), SearchActivity.class);
                startActivity(intentSearch);
                getSuperActivity().overridePendingTransition(R.anim.translate_right_in, R.anim.translate_zero);
                break;

            case R.id.toolbar_right_layout:
                Intent intentMessage = new Intent(getSuperActivity(), MessageActivity.class);
                startActivity(intentMessage);
                getSuperActivity().overridePendingTransition(R.anim.translate_right_in, R.anim.translate_zero);
                break;

            case R.id.indiana_type:
                Intent intentType = new Intent(getSuperActivity(), TypeActivity.class);
                startActivity(intentType);
                getSuperActivity().overridePendingTransition(R.anim.translate_right_in, R.anim.translate_zero);
                break;
            case R.id.indiana_hot_shop:
                Intent intentOptimization = new Intent(getSuperActivity(), OptimizationActivity.class);
                startActivity(intentOptimization);
                getSuperActivity().overridePendingTransition(R.anim.translate_right_in, R.anim.translate_zero);
                break;
            case R.id.indiana_share_im:
                Intent intentShareSingle = new Intent(getSuperActivity(), ShareSingleActivity.class);
                startActivity(intentShareSingle);
                getSuperActivity().overridePendingTransition(R.anim.translate_right_in, R.anim.translate_zero);
                break;
            case R.id.indiana_hot_question:
                Intent intentQuestion = new Intent(getSuperActivity(), CommonProblemsActivity.class);
                startActivity(intentQuestion);
                getSuperActivity().overridePendingTransition(R.anim.translate_right_in, R.anim.translate_zero);
                break;
            case R.id.indiana_sound:

                break;
            case R.id.indiana_bar_hot:
                ToastHelper.showToast(getSuperActivity(), "人气");
                break;
            case R.id.indiana_bar_new:
                ToastHelper.showToast(getSuperActivity(), "最新");
                break;
            case R.id.indiana_bar_progress:
                ToastHelper.showToast(getSuperActivity(), "进度");
                break;
            case R.id.indiana_bar_pople_num:
                ToastHelper.showToast(getSuperActivity(), "总需人次");
                break;
        }
    }

    public void initRecycleView() {
        shopLists = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            shopLists.add(new ShopListBean(R.drawable.iphone6s, "iphone 6s 128G", 1000, 343));
        }
        adapter = new IndianaListAdapter(getContext(), shopLists);
        recycle_view.setLayoutManager(new GridLayoutManager(getSuperActivity(), 2));
        recycle_view.addItemDecoration(new DividerGridItemDecoration(getSuperActivity()));
        initHeaderFooter(adapter);
        recycle_view.setAdapter(mHeaderAndFooterWrapper);
    }

    public void initHeaderFooter(IndianaListAdapter adapter) {
        View header = LayoutInflater.from(getSuperActivity()).inflate(R.layout.fragment_indiana_header, null);
        Banner banner = (Banner) header.findViewById(R.id.banner);
        initBanner(banner);

        LinearLayout indiana_type = (LinearLayout) header.findViewById(R.id.indiana_type);
        LinearLayout indiana_hot_shop = (LinearLayout) header.findViewById(R.id.indiana_hot_shop);
        LinearLayout indiana_share_im = (LinearLayout) header.findViewById(R.id.indiana_share_im);
        LinearLayout indiana_hot_question = (LinearLayout) header.findViewById(R.id.indiana_hot_question);

        LinearLayout indiana_sound = (LinearLayout) header.findViewById(R.id.indiana_sound);

        indiana_sound_user_name = (TextView) header.findViewById(R.id.indiana_sound_user_name);
        indiana_sound_time = (TextView) header.findViewById(R.id.indiana_sound_time);
        indiana_sound_shop_name = (TextView) header.findViewById(R.id.indiana_sound_shop_name);
        updateSoundView();

        TextView indiana_bar_hot = (TextView) header.findViewById(R.id.indiana_bar_hot);
        TextView indiana_bar_new = (TextView) header.findViewById(R.id.indiana_bar_new);
        TextView indiana_bar_progress = (TextView) header.findViewById(R.id.indiana_bar_progress);
        TextView indiana_bar_pople_num = (TextView) header.findViewById(R.id.indiana_bar_pople_num);

        indiana_type.setOnClickListener(this);
        indiana_hot_shop.setOnClickListener(this);
        indiana_share_im.setOnClickListener(this);
        indiana_hot_question.setOnClickListener(this);
        indiana_sound.setOnClickListener(this);

        indiana_bar_hot.setOnClickListener(this);
        indiana_bar_new.setOnClickListener(this);
        indiana_bar_progress.setOnClickListener(this);
        indiana_bar_pople_num.setOnClickListener(this);

        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(adapter);
        mHeaderAndFooterWrapper.addHeaderView(header);
    }

    private void initBanner(Banner banner) {
        banners = new ArrayList<>();
        banners.add(R.drawable.indiana_banner01);
        banners.add(R.drawable.indiana_banner01);
        banners.add(R.drawable.indiana_banner01);
        banners.add(R.drawable.indiana_banner01);
        banners.add(R.drawable.indiana_banner01);

        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(banners);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Default);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(3 * 1000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.start();
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

    /**
     * 更新幸运通知
     */
    public void updateSoundView() {
        indiana_sound_user_name.setText("哆啦A梦");
        indiana_sound_time.setText("2分钟前");
        indiana_sound_shop_name.setText("时光机");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
