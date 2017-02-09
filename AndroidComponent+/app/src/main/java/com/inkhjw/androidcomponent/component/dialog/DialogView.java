package com.inkhjw.androidcomponent.component.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;

/**
 * @author hjw
 * @ 显示自定义布局的对话框
 */
public class DialogView extends AlertDialog {

    protected DialogView(@NonNull Context context) {
        super(context);
    }

    protected DialogView(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected DialogView(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public DialogView createDialogView(Context context) {
        Builder builder = new Builder(context, android.R.style.Theme_Dialog);
        builder.create();
        return (DialogView) builder.create();
    }
}
