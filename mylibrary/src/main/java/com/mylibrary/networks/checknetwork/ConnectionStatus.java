package com.mylibrary.networks.checknetwork;


import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SuppressWarnings("WeakerAccess")
@Retention(RetentionPolicy.SOURCE)
@IntDef({ConnectionStatus.CONNECTED, ConnectionStatus.DISCONNECTED, ConnectionStatus.UNKNOWN})
public @interface ConnectionStatus {
    int CONNECTED = 100;
    int DISCONNECTED = 101;
    int UNKNOWN = 102;
}
