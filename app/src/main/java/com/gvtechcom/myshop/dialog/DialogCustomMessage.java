package com.gvtechcom.myshop.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialog;

import com.gvtechcom.myshop.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogCustomMessage extends AppCompatDialog {
    private Boolean isSelect;

    @BindView(R.id.txt_title_custom_dialog)
    TextView txtTitleCustomDialog;
    @BindView(R.id.txt_message_custom_dialog)
    TextView txtMessageCustomDialog;
    @BindView(R.id.btn_cancel_custom_dialog)
    Button btnOkCustomDialog;
    @BindView(R.id.btn_ok_custom_dialog)
    Button btnCancelCustomDialog;


    public Boolean getSelect() {
        return isSelect;
    }

    public void setSelect(Boolean select) {
        isSelect = select;
    }

    public DialogCustomMessage(Context context, String Title, String Message) {
        super(context, R.style.Theme_Dialog);
        setCancelable(false);
        setContentView(R.layout.item_custom_dialog_message);
        ButterKnife.bind(this);
        txtTitleCustomDialog.setText(Title);
        txtMessageCustomDialog.setText(Message);
    }

    @OnClick({R.id.btn_cancel_custom_dialog, R.id.btn_ok_custom_dialog})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel_custom_dialog:
                setSelect(false);
                dismiss();
                break;
            case R.id.btn_ok_custom_dialog:
                setSelect(true);
                dismiss();
                break;
        }

    }
}
