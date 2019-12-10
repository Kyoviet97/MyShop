package com.gvtechcom.myshop.Utils;

import android.content.Context;

import com.gvtechcom.myshop.dialog.ToastDialog;

public class ValidateCallApi {
    public static Boolean ValidateAip(Context context, Integer value, String mes) {
        ToastDialog toastDialog = new ToastDialog(context);
        Boolean isPass = false;
        if (value != 200) {
            toastDialog.onShow(mes);
            isPass = false;
        } else {
            isPass = true;
        }
        return isPass;
    }

}
