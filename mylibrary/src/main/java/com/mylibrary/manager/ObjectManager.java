package com.mylibrary.manager;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mylibrary.base.BaseModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Annv on 5/10/16.
 */
public class ObjectManager<T extends BaseModel> {

    private ConfigManager configManager;
    private Gson gson;

    public ObjectManager(ConfigManager configManager) {
        this.configManager = configManager;
        gson = new GsonBuilder().serializeNulls().setLenient().create();
    }

    public void saveDataString(Class<T> clazzKey, String value) {
        configManager.putString(clazzKey.getSimpleName(), value).commit();
    }

    public void saveDataObject(Class<T> clazzKey, T value) {
        String dataJson = gson.toJson(value);
        configManager.putString(clazzKey.getSimpleName(), dataJson).commit();
    }

    public void saveDataObject(Class clazzKey, Object value) {
        String dataJson = gson.toJson(value);
        configManager.putString(clazzKey.getSimpleName(), dataJson).commit();
    }

    public T getDataObject(Class<T> clazzKey) {
        try {
            String strData = configManager.getString(clazzKey.getSimpleName(), null);
            T result = gson.fromJson(strData, clazzKey);
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    public Object getDataObjectModel(Class clazzKey) {
        try {
            String strData = configManager.getString(clazzKey.getSimpleName(), null);
            Object result = gson.fromJson(strData, clazzKey);
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    public String getDataString(Class<T> clazzKey) {
        try {
            String strData = configManager.getString(clazzKey.getSimpleName(), null);
            return strData;
        } catch (Exception e) {
            return "";
        }
    }

    public void clearData(Class<T> clazzKey) {
        configManager.clear(clazzKey.getSimpleName()).commit();
    }

    public void deleteDataObject(Class clazzKey) {
        configManager.clear(clazzKey.getSimpleName()).commit();
    }
}
