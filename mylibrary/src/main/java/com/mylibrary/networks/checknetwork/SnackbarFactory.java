package com.mylibrary.networks.checknetwork;


import android.app.Activity;
import android.content.Context;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import com.google.android.material.snackbar.Snackbar;
import android.view.View;

import com.mylibrary.R;


class SnackbarFactory {

    static Snackbar getSnackbar(Context context) {
        return getSnackbar(context, R.string.no_server_connection_message);
    }

    static Snackbar getSnackbar(Context context, String message) {
        View view = extractView(context);
        if (view != null) {
            return Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE);
        }

        return null;
    }

    static Snackbar getSnackbar(Context context, @StringRes int messageRes) {
        View view = extractView(context);
        if (view != null) {
            return Snackbar.make(view, messageRes, Snackbar.LENGTH_INDEFINITE);
        }

        return null;
    }

    @Nullable
    private static View extractView(Context context) {
        if (context != null && context instanceof Activity) {
            return ((Activity) context).findViewById(android.R.id.content);
        }

        return null;
    }
}
