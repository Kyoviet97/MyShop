package com.gvtechcom.myshop.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialog;

import com.gvtechcom.myshop.R;

public class ToastDialog extends AppCompatDialog {
    private TextView txt;
    private Button btn;
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
        btn = (Button)findViewById(R.id.button_ok_toast_dialog);
        txt.setText(message);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        show();
    }

    public void onHide(){
        dismiss();
    }
}
