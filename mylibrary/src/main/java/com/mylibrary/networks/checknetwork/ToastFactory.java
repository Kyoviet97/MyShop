package com.mylibrary.networks.checknetwork;


import android.content.Context;
import androidx.annotation.StringRes;
import android.widget.Toast;

import com.mylibrary.R;


class ToastFactory {

    static Toast getToast(Context context) {
        return getToast(context, R.string.no_server_connection_message);
    }

    static Toast getToast(Context context, String message) {
        if (context != null) {
            return Toast.makeText(context, message, Toast.LENGTH_SHORT);
        }

        return null;
    }

    static Toast getToast(Context context, @StringRes int messageRes) {
        if (context != null) {
            return Toast.makeText(context, messageRes, Toast.LENGTH_SHORT);
        }

        return null;
    }
}
