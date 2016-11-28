package com.inkhjw.personalcommunity.mvp;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.inkhjw.personalcommunity.R;
import com.inkhjw.personalcommunity.base.SpeicalToolbarActivity;
import com.inkhjw.personalcommunity.mvp.announced.FragmentAnnounced;
import com.inkhjw.personalcommunity.mvp.found.FragmentFound;
import com.inkhjw.personalcommunity.mvp.indiana.FragmentIndiana;
import com.inkhjw.personalcommunity.mvp.list.FragmentList;
import com.inkhjw.personalcommunity.mvp.user.FragmentUser;

import butterknife.BindView;

public class MainActivity extends SpeicalToolbarActivity implements MainContract.View, TabHost.OnTabChangeListener {
    @BindView(R.id.realtabcontent)
    FrameLayout realtabcontent;
    @BindView(android.R.id.tabcontent)
    FrameLayout tabcontent;
    @BindView(android.R.id.tabhost)
    FragmentTabHost tabhost;

    private MainContract.Presenter presenter;

    //定义数组来存放Fragment界面
    private Class fragments[] = {FragmentIndiana.class, FragmentAnnounced.class, FragmentFound.class, FragmentList.class, FragmentUser.class};

    //Tab选中显示的图片
    private int tabSelectedIcon[] = {
            R.drawable.main_tab_indiana_p,
            R.drawable.main_tab_announced_p,
            R.drawable.main_tab_found_p,
            R.drawable.main_tab_list_p,
            R.drawable.main_tab_user_p
    };
    //Tab没有选中显示的图片
    private int tabUnSelectedIcon[] = {
            R.drawable.main_tab_indiana,
            R.drawable.main_tab_announced,
            R.drawable.main_tab_found,
            R.drawable.main_tab_list,
            R.drawable.main_tab_user
    };

    //Tab选项卡的文字
    private String tabTexts[] = {"夺宝", "最新揭晓", "发现", "清单", "我的"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void init() {
        super.init();
        new MainPresenter(this);
        initTab();
        setSupportBarTint(null);
    }

    /**
     * 初始化Tab组件
     */
    private void initTab() {
        tabhost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        int count = fragments.length;
        for (int i = 0; i < count; i++) {
            //为每一个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = tabhost.newTabSpec(tabTexts[i]).setIndicator(getTabItemView(i));
            //将Tab按钮添加进Tab选项卡中
            tabhost.addTab(tabSpec, fragments[i], null);
            //设置Tab按钮的背景
            tabhost.getTabWidget().setDividerDrawable(R.color.transparent);
        }
        tabhost.setOnTabChangedListener(this);
    }

    /**
     * 给Tab按钮设置图标和文字
     */
    private View getTabItemView(int index) {
        View view = LayoutInflater.from(this).inflate(R.layout.main_tabhost_item, null);

        ImageView tab_widget_image = (ImageView) view.findViewById(R.id.tab_widget_image);
        TextView tab_widget_tv = (TextView) view.findViewById(R.id.tab_widget_tv);
        tab_widget_tv.setText(tabTexts[index]);
        if (index == 0) {
            tab_widget_image.setImageResource(tabSelectedIcon[index]);
            tab_widget_tv.setTextColor(getResources().getColor(R.color.appThemeColor));
        } else {
            tab_widget_image.setImageResource(tabUnSelectedIcon[index]);
            tab_widget_tv.setTextColor(getResources().getColor(R.color.text_color_small_title));
        }
        return view;
    }

    /**
     * 下面的Tab发生改变时的监听
     *
     * @param tabId
     */
    @Override
    public void onTabChanged(String tabId) {
        for (int i = 0; i < tabTexts.length; i++) {
            View view = tabhost.getTabWidget().getChildAt(i);
            ImageView tab_weidget_image = (ImageView) view.findViewById(R.id.tab_widget_image);
            TextView tab_weidget_tv = (TextView) view.findViewById(R.id.tab_widget_tv);
            if (tabTexts[i].equals(tabId)) {
                tab_weidget_image.setImageResource(tabSelectedIcon[i]);
                tab_weidget_tv.setTextColor(getResources().getColor(R.color.appThemeColor));
            } else {
                tab_weidget_image.setImageResource(tabUnSelectedIcon[i]);
                tab_weidget_tv.setTextColor(getResources().getColor(R.color.text_color_small_title));
            }
        }
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
