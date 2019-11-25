package com.mylibrary.base;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import androidx.appcompat.app.AppCompatDialog;

import com.mylibrary.R;

import butterknife.ButterKnife;

/**
 * Created by Annv on 10/23/17.
 */

public abstract class BaseDialog extends AppCompatDialog {

    private boolean isDismiss = false;
    private Context mContext;

    public boolean getIsDismiss() {
        return isDismiss;
    }

    public void setDismiss(boolean dismiss) {
        isDismiss = dismiss;
    }

    public Context getmContext() {
        return mContext;
    }

    public BaseDialog(Context context) {
        super(context, R.style.Theme_Dialog);
        this.mContext = context;
        innitDialog();
    }

    public BaseDialog(Context context, int themeDialog) {
        super(context, themeDialog);
        this.mContext = context;
        innitDialog();
    }

    private void innitDialog() {
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(getLayoutContent());
        ButterKnife.bind(this);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    public abstract int getLayoutContent();
}
