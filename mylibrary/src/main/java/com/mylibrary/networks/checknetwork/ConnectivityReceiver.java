package com.mylibrary.networks.checknetwork;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import static android.net.ConnectivityManager.CONNECTIVITY_ACTION;
import static com.mylibrary.networks.checknetwork.ConnectionStatus.*;

public abstract class ConnectivityReceiver extends BroadcastReceiver {
    private static final String TAG = ConnectivityReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (CONNECTIVITY_ACTION.equals(intent.getAction())) {
            Log.d(TAG, "Received " + CONNECTIVITY_ACTION);

            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm != null) {
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                if (activeNetwork != null) {
                    boolean isConnected = activeNetwork.isConnectedOrConnecting();
                    if (isConnected) {
                        onConnectivityChanged(CONNECTED);
                    } else {
                        onConnectivityChanged(DISCONNECTED);
                    }
                    return;
                }
            }

            onConnectivityChanged(UNKNOWN);
        }
    }

    public abstract void onConnectivityChanged(@ConnectionStatus int connectionStatus);

    public static IntentFilter getIntentFilter() {
        return new IntentFilter(CONNECTIVITY_ACTION);
    }
}
