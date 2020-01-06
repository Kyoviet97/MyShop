package com.gvtechcom.myshop.Utils;

import android.content.Context;

import com.gvtechcom.myshop.R;
import com.gvtechcom.myshop.dialog.CustomToastDialog;
import com.gvtechcom.myshop.dialog.ToastDialog;
import com.mylibrary.ui.progress.ProgressDialogCustom;

public class ValidateCallApi {
    public static Boolean ValidateAip(Context context, Integer value, String mes) {
        CustomToastDialog customToastDialog = new CustomToastDialog(context);
        Boolean isPass = false;
        if (value != 200) {
            customToastDialog.onShow( R.drawable.ic_icon_load_error_dialog, mes, false);
            isPass = false;
        } else {
            isPass = true;
        }
        return isPass;
    }

}
