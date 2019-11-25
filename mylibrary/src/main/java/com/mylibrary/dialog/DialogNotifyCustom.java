package com.mylibrary.dialog;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialog;

import com.mylibrary.R;

/**
 * Created by Annv on 8/17/17.
 */

public class DialogNotifyCustom extends AppCompatDialog {

    TextView txtTitle;
    TextView txtContent;
    Button btnCancel;
    Button btnOk;
    public boolean isDismiss = false;

    public DialogNotifyCustom(Activity context) {
        super(context, R.style.Theme_Dialog);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_notify_custom);
        final DisplayMetrics metrics = new DisplayMetrics(); //get metrics of screen
        context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = (int) (metrics.widthPixels * 0.80); //set width to 90% of total
        getWindow().setLayout(width, LinearLayout.LayoutParams.WRAP_CONTENT); //set layout
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        txtTitle = findViewById(R.id.txt_title);
        txtContent = findViewById(R.id.txt_content);
        btnOk = findViewById(R.id.btn_ok);
        btnCancel = findViewById(R.id.btn_cancel);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDismiss = true;
                setDismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDismiss = false;
                setDismiss();
            }
        });
    }

    public DialogNotifyCustom setTextTitle(String strTitle) {
        if (!strTitle.equals("")) {
            txtTitle.setText(strTitle);
        }
        return this;
    }

    public DialogNotifyCustom setTextContent(String strContent) {
        if (!strContent.equals("")) {
            txtContent.setText(strContent);
        }
        return this;
    }

    public DialogNotifyCustom setTextButton(String strBtnOk, String strBtnCancel) {
        if (!strBtnOk.equals("")) {
            btnOk.setText(strBtnOk);
        }
        btnCancel.setText(strBtnCancel);
        if (strBtnCancel.equals("")) {
            btnCancel.setVisibility(View.GONE);
        } else {
            btnCancel.setVisibility(View.VISIBLE);
        }
        return this;
    }

    private void setDismiss() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        });
    }
}
