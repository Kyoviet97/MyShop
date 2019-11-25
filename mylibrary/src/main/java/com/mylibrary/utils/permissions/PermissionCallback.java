package com.mylibrary.utils.permissions;

public interface PermissionCallback {

    void onPermissionGranted();

    void onPermissionDenied();

    void onPermissionPermanentlyDenied();

}
