package com.sun.bookread.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.sun.bookread.helper.ToolBarHelper;

/**
 * BaseActivity
 */

public abstract class BaseActivity extends AppCompatActivity {
    private ToolBarHelper mToolBarHelper;
    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        mToolBarHelper = new
                ToolBarHelper(this, layoutResID);
        toolbar = mToolBarHelper.getToolBar();

        setContentView(mToolBarHelper.getContentView());
        /*把 toolbar 设置到Activity 中*/
        setSupportActionBar(toolbar);
        /*自定义的一些操作*/
        onCreateCustomToolBar(toolbar);
    }

    /**
     * 提供toolbar对象给子类，可对Toolbar进行操作
     *
     * @param toolbar
     */
    public void onCreateCustomToolBar(Toolbar toolbar) {
        toolbar.setContentInsetsRelative(0, 0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
