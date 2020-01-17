package com.gvtechcom.myshop.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class MySharePreferences{
    public static void SaveSharePref(Context context, String key, String value) {
        SharedPreferences sharedPref = context.getSharedPreferences("com.gvtechcom.myshop.firts", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String GetSharePref(Context context, String key) {
        SharedPreferences sharedPref = context.getSharedPreferences("com.gvtechcom.myshop.firts", Context.MODE_PRIVATE);
        String value = sharedPref.getString(key, "Update");
        return value;
    }

    public void SaveSharePrefObject(Context context, Object object, String NameObject){
        SharedPreferences sharedPref = context.getSharedPreferences("com.gvtechcom.myshop.firts", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(object);
        prefsEditor.putString(NameObject, json);
        prefsEditor.commit();
    }

    public String GetSharePrefStringObject(Context context, String NameObject){
        SharedPreferences sharedPref = context.getSharedPreferences("com.gvtechcom.myshop.firts", Context.MODE_PRIVATE);
        String json = sharedPref.getString(NameObject, "");
        return json;
    }
}
