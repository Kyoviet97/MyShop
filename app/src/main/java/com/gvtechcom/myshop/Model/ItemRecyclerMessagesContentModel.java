package com.gvtechcom.myshop.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemRecyclerMessagesContentModel {
    @SerializedName("image")
    @Expose
    public String urlImage;

    @SerializedName("stTitle")
    @Expose
    public String title;

    @SerializedName("stTime")
    @Expose
    public String time;

    @SerializedName("stContent")
    @Expose
    public String content;

    public ItemRecyclerMessagesContentModel(String urlImage, String title, String time, String content) {
        this.urlImage = urlImage;
        this.title = title;
        this.time = time;
        this.content = content;
    }
}
