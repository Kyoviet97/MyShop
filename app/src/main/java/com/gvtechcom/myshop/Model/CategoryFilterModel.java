package com.gvtechcom.myshop.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryFilterModel {

    public class Filters{
        public List<String> ids;
        public String name;
        public List<String> details;

    }

    public class Topbrands{
        public String id;
        public String name;
        public String image;
    }

    public class Data{
        public List<Filters> filters;
        public List<Topbrands> top_brands;
    }

    public class CategoryFilterModelParser{
        @SerializedName("status")
        @Expose
        public Integer status;

        @SerializedName("content")
        @Expose
        public String content;

        @SerializedName("data")
        @Expose
        public Data data;
    }
}
