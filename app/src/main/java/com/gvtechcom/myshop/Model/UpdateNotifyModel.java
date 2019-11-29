package com.gvtechcom.myshop.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UpdateNotifyModel {
    public Integer current_page;
    public Integer total;
    public List<DataUpdateNoty> data;
    public String title;
    public String start_datetime;
    public String content;


    public class DataUpdateNoty{
        public String _id;
        public String title;
        public String start_datetime;
    }


    public class UpdateNotifyModelParser{
        @SerializedName("status")
        @Expose
        public Integer code;
        @SerializedName("content")
        @Expose
        public String message;
        @SerializedName("data")
        @Expose
        public UpdateNotifyModel response;
    }

}
