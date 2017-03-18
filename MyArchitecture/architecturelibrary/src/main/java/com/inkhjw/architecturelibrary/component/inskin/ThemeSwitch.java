package com.inkhjw.architecturelibrary.component.inskin;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * @author hjw
 *         主题切换的控制
 */

public class ThemeSwitch {

    public void onCreate() {

    }

    public void switchT(final AppCompatActivity appCompatActivity, final String themeName) {
        LayoutInflater inflater = LayoutInflater.from(appCompatActivity);
        LayoutInflaterCompat.setFactory(inflater, new LayoutInflaterFactory() {
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
                AppCompatDelegate delegate = appCompatActivity.getDelegate();
                View view = delegate.createView(parent, name, context, attrs);
                if (view != null && (view instanceof TextView)) {
                    ((TextView) view).setTypeface(Typeface.createFromAsset(context.getAssets(), themeName));
                }
                return view;
            }
        });
    }
}
