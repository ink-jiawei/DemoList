package com.inkhjw.coordinatorlayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
/**
 AppBarLayout layout_scrollFlags属性
 (1)scroll:    将此布局和滚动时间关联。这个标识要设置在其他标识之前，没有这个标识则布局不会滚动且其他标识设置无效。
 (2)enterAlways:   任何向下滚动操作都会使此布局可见。这个标识通常被称为“快速返回”模式。
 (3)enterAlwaysCollapsed：  假设你定义了一个最小高度（minHeight）同时enterAlways也定义了，
 那么view将在到达这个最小高度的时候开始显示，并且从这个时候开始慢慢展开，当滚动到顶部的时候展开完。
 (4)exitUntilCollapsed：    当你定义了一个minHeight，此布局将在滚动到达这个最小高度的时候折叠。
 (5)snap:  当一个滚动事件结束，如果视图是部分可见的，那么它将被滚动到收缩或展开。
 例如，如果视图只有底部25%显示，它将折叠。相反，如果它的底部75%可见，那么它将完全展开。

 注意：CollapsingToolbarLayout    可以通过app:contentScrim设置折叠时工具栏布局的颜色，
 通过app:statusBarScrim设置折叠时状态栏的颜色。默认contentScrim是colorPrimary的色值，
 statusBarScrim是colorPrimaryDark的色值。

 CollapsingToolbarLayout layout_collapseMode
 (1)off：这个是默认属性，布局将正常显示，没有折叠的行为。
 (2)pin：CollapsingToolbarLayout折叠后，此布局将固定在顶部。
 (3)parallax：CollapsingToolbarLayout折叠时，此布局也会有视差折叠效果。
 */
    }
}
