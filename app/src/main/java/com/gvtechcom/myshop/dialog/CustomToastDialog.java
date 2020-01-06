package com.gvtechcom.myshop.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialog;

import com.gvtechcom.myshop.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomToastDialog extends AppCompatDialog {
    @BindView(R.id.img_custom_toast_dialog)
    ImageView imgCustomToasDialog;
    @BindView(R.id.txt_title_custom_dialog)
    TextView txtTitleCustomDialog;
    @BindView(R.id.main_layout_button_custom_toast_dialog)
    LinearLayout mainLayoutButtonCustomToastDialog;

    public CustomToastDialog(Context context) {
        super(context, R.style.style_theme_custom_dialog_toast);
        setContentView(R.layout.item_custom_toast_dialog);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.75);
        int height = (int) (context.getResources().getDisplayMetrics().heightPixels * 0.50);
        getWindow().setLayout(width, height);
        ButterKnife.bind(this);
    }

    public void onShow(int image, String stTitle, Boolean buttom){
        imgCustomToasDialog.setImageResource(image);
        txtTitleCustomDialog.setText(stTitle);
        if (buttom) {
            mainLayoutButtonCustomToastDialog.setVisibility(View.VISIBLE);
            mainLayoutButtonCustomToastDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        } else {
            mainLayoutButtonCustomToastDialog.setVisibility(View.GONE);
        }

        show();
    }

}
