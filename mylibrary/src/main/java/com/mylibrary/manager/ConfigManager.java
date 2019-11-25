package com.mylibrary.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.Set;

public class ConfigManager {

    private SharedPreferences pref;
    private Editor editor;

    private ConfigManager(Context activity) {
        pref = activity.getSharedPreferences(activity.getPackageName() + "_preferences", Context.MODE_PRIVATE);
    }

    public ConfigManager putString(String key, String value) {
        tryToOpenEdit();
        editor.putString(key, value);
        return this;
    }

    public static ConfigManager open(Context context) {
        return new ConfigManager(context);
    }

    private ConfigManager tryToOpenEdit() {
        if (editor == null) {
            editor = pref.edit();
        }
        return this;
    }

    public String getString(String key, String defaultValue) {
        return pref.getString(key, defaultValue);
    }

    public ConfigManager putStringSet(String key, Set<String> values) {
        tryToOpenEdit();
        editor.putStringSet(key, values);
        return this;
    }

    public Set<String> getStringSet(String key, Set<String> values) {
        return pref.getStringSet(key, values);
    }

    public ConfigManager putInt(String key, int value) {
        tryToOpenEdit();
        editor.putInt(key, value);
        return this;
    }

    public int getInt(String key, int defaultValue) {
        return pref.getInt(key, defaultValue);
    }

    public ConfigManager putLong(String key, long value) {
        tryToOpenEdit();
        editor.putLong(key, value);
        return this;
    }

    public long getLong(String key, long defaultValue) {
        return pref.getLong(key, defaultValue);
    }

    public void commit() {
        editor.commit();
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return pref.getBoolean(key, defaultValue);
    }

    public ConfigManager putBoolean(String key, boolean value) {
        tryToOpenEdit();
        editor.putBoolean(key, value);
        return this;
    }

    public ConfigManager clear() {
        tryToOpenEdit();
        editor.clear().commit();
        return this;
    }

    public ConfigManager clear(String key) {
        tryToOpenEdit();
        editor.remove(key).commit();
        return this;
    }
}
