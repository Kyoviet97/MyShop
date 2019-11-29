package com.gvtechcom.myshop.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CountryInfoModel {
    public List<Data> data;

    public class Data{
        public String id;
        public String name;
        public List<String> phone_code;
        public List<City> cities;
        public Boolean isCheck;
    }


    public class City{
        public String id;
        public String name;
        public Boolean isCheck;
    }

    public class CountryInfoModelParser{
        @SerializedName("status")
        @Expose
        public Integer code;
        @SerializedName("content")
        @Expose
        public String message;
        @SerializedName("data")
        @Expose
        public CountryInfoModel response;
    }

}
