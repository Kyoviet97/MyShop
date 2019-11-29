package com.gvtechcom.myshop.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialog;

import com.gvtechcom.myshop.R;

public class ToastDialog extends AppCompatDialog {
    TextView txt;
    public ToastDialog(Context context) {
        super(context, R.style.Theme_dialog_toast);
        setContentView(R.layout.item_toast_dialog);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.90);
        int height = (int) (context.getResources().getDisplayMetrics().heightPixels * 0.2);
        getWindow().setLayout(width, height);
        setCancelable(true);
    }

    public void onShow(String message){
        txt = (TextView)findViewById(R.id.txt_messages_toast_dialog);
        txt.setText(message);
        show();
    }

    public void onHide(){
        dismiss();
    }
}
