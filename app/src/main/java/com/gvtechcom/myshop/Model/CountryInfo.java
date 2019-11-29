package com.gvtechcom.myshop.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CountryInfo {

    public String id;
    public String name;
    public List<String> phone_code;
    public List<CityInfo> cities;
    public Boolean isCheck;


    public class CityInfo{
        public String id;
        public String name;
        public Boolean isCheck;
    }


    public static class CountryParser{
        @SerializedName("status")
        @Expose
        public Integer code;
        @SerializedName("content")
        @Expose
        public String message;
        @SerializedName("data")
        @Expose
        public List<CountryInfo> response;
    }
}
