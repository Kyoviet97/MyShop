package com.gvtechcom.myshop.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import androidx.appcompat.app.AppCompatDialog;

import com.gvtechcom.myshop.R;

import butterknife.ButterKnife;

public class DialogViewCategoryFilter extends AppCompatDialog {
    public DialogViewCategoryFilter(Context context) {
        super(context, R.style.style_theme_custom_dialog_toast);
        setContentView(R.layout.dialog_view_category_filter);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 1.0);
        int height = (int) (context.getResources().getDisplayMetrics().heightPixels * 1.0);
        getWindow().setLayout(width, height);
        ButterKnife.bind(this);
    }
}
