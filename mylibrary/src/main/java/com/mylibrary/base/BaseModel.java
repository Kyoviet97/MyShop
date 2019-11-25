package com.mylibrary.base;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Annv on 5/30/16.
 */
public class BaseModel<T> implements Serializable {

    @SerializedName("status")
    public int status;
    @SerializedName("content")
    public String content;
    @SerializedName("data")
    public T data;
}
