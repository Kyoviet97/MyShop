package com.gvtechcom.myshop.Utils;

import android.content.Context;

import com.gvtechcom.myshop.dialog.ToastDialog;
import com.mylibrary.ui.progress.ProgressDialogCustom;

public class ShowProgressBar {
    private static ProgressDialogCustom progressDialogCustom;

    public static void showProgress(Context context){
        progressDialogCustom = new ProgressDialogCustom(context);
        progressDialogCustom.onShow(false, "");
    }

    public static void hideProgress(){
       if (progressDialogCustom != null){
           progressDialogCustom.onHide();
       }
    }

}
