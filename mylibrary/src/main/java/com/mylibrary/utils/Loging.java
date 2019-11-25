package com.mylibrary.utils;

import android.util.Log;

/**
 * Created by Annv on 7/22/17.
 */

public class Loging {

    public static void v(Class clazzz, String msg) {
        if (msg == null)
            Log.v("---------->>" + clazzz.getName() + ": ", "null");
        else
            Log.v("---------->>" + clazzz.getName() + ": ", msg);
    }

    public static void i(Class clazzz, String msg) {
        if (msg == null)
            Log.i("---------->>" + clazzz.getName() + ": ", "null");
        else
            Log.i("---------->>" + clazzz.getName() + ": ", msg);
    }


    public static void e(Class clazzz, String msg) {
        if (msg == null)
            Log.e("---------->>" + clazzz.getName() + ": ", "null");
        else
            Log.e("---------->>" + clazzz.getName() + ": ", msg);
    }

    public static void d(Class clazzz, String msg) {
        if (msg == null)
            Log.d("---------->>" + clazzz.getName() + ": ", "null");
        else
            Log.d("---------->>" + clazzz.getName() + ": ", msg);
    }

    public static void v(String tag, String msg) {
        if (msg == null)
            Log.v("---------->>" + tag + ": ", "null");
        else
            Log.v("---------->>" + tag + ": ", msg);
    }

    public static void i(String tag, String msg) {
        if (msg == null)
            Log.i("---------->>" + tag + ": ", "null");
        else
            Log.i("---------->>" + tag + ": ", msg);
    }


    public static void e(String tag, String msg) {
        if (msg == null)
            Log.e("---------->>" + tag + ": ", "null");
        else
            Log.e("---------->>" + tag + ": ", msg);
    }

    public static void d(String tag, String msg) {
        if (msg == null)
            Log.d("---------->>" + tag + ": ", "null");
        else
            Log.d("---------->>" + tag + ": ", msg);
    }

    public static void logDataLong(String TAG, String veryLongString) {
        int maxLogSize = 1000;
        for (int i = 0; i <= veryLongString.length() / maxLogSize; i++) {
            int start = i * maxLogSize;
            int end = (i + 1) * maxLogSize;
            end = end > veryLongString.length() ? veryLongString.length() : end;
            Log.v("------------>>" + TAG, veryLongString.substring(start, end));
        }
    }
}
