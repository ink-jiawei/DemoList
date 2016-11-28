package com.sun.bookread;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.sun.bookread.base.BaseActivity;
import com.sun.bookread.fragment.FragmentRead;
import com.sun.bookread.fragment.FragmentRead1;
import com.sun.bookread.fragment.FragmentRead2;
import com.sun.bookread.fragment.FragmentUser;

public class MainActivity extends BaseActivity implements TabHost.OnTabChangeListener {

    //定义FragmentTabHost对象
    private FragmentTabHost mTabHost;

    //定义一个布局
    private LayoutInflater layoutInflater;

    //定义数组来存放Fragment界面
    private Class fragmentArray[] = {FragmentRead.class, FragmentRead1.class, FragmentRead2.class, FragmentUser.class};

    //Tab选中显示的图片
    private int selectedImage[] = {
            R.mipmap.index_selected,
            R.mipmap.jiankang_selected,
            R.mipmap.message_selected,
            R.mipmap.user_wo_selected
    };
    //Tab没有选中显示的图片
    private int noSelectedImage[] = {
            R.mipmap.index_no_selected,
            R.mipmap.jiankang_no_selected,
            R.mipmap.message_no_selected,
            R.mipmap.user_wo_no_selected
    };

    //Tab选项卡的文字
    private String mTextviewArray[] = {"首页", "健康", "消息", "我的"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }


    @Override
    public void onCreateCustomToolBar(Toolbar toolbar) {
        super.onCreateCustomToolBar(toolbar);
        toolbar.setTitle("爱上玩");
    }


    /**
     * 初始化组件
     */
    private void initView() {
        //实例化布局对象
        layoutInflater = LayoutInflater.from(this);

        //实例化TabHost对象，得到TabHost
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        //得到fragment的个数
        int count = fragmentArray.length;

        for (int i = 0; i < count; i++) {
            //为每一个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i]).setIndicator(getTabItemView(i));
            //将Tab按钮添加进Tab选项卡中
            mTabHost.addTab(tabSpec, fragmentArray[i], null);
            //设置Tab按钮的背景
            mTabHost.getTabWidget().setDividerDrawable(R.color.transparent);
        }
        mTabHost.setOnTabChangedListener(this);
    }

    /**
     * 给Tab按钮设置图标和文字
     */
    private View getTabItemView(int index) {
        View view = layoutInflater.inflate(R.layout.fragmenttabhost_item, null);

        ImageView tab_widget_image = (ImageView) view.findViewById(R.id.tab_widget_image);
        TextView tab_widget_tv = (TextView) view.findViewById(R.id.tab_widget_tv);
        tab_widget_tv.setText(mTextviewArray[index]);
        if (index == 0) {
            tab_widget_image.setImageResource(selectedImage[index]);
            tab_widget_tv.setTextColor(getResources().getColor(R.color.appThemeColor1));
        } else {
            tab_widget_image.setImageResource(noSelectedImage[index]);
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
        for (int i = 0; i < mTextviewArray.length; i++) {
            View view = mTabHost.getTabWidget().getChildAt(i);
            ImageView tab_weidget_image = (ImageView) view.findViewById(R.id.tab_widget_image);
            TextView tab_weidget_tv = (TextView) view.findViewById(R.id.tab_widget_tv);
            if (mTextviewArray[i].equals(tabId)) {
                tab_weidget_image.setImageResource(selectedImage[i]);
                tab_weidget_tv.setTextColor(getResources().getColor(R.color.appThemeColor1));
            } else {
                tab_weidget_image.setImageResource(noSelectedImage[i]);
                tab_weidget_tv.setTextColor(getResources().getColor(R.color.tabNoSelectedColor));
            }
        }
    }
}
