package com.gvtechcom.myshop.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ItemYouLoveModel {

    public class Product {
        public String id;
        public String name;
        public String image;
        public String price;
        public String sold;
    }

    public class Data{
        public List<Product> products;
        public Meta meta;
    }

    public class Meta{
        public Pagination pagination;
    }

    public class Pagination{
        public Integer total;
        public Integer current_page;
    }

    public static class ItemYouLoveModelParser {
        @SerializedName("status")
        @Expose
        public Integer code;
        @SerializedName("content")
        @Expose
        public String message;
        @SerializedName("data")
        @Expose
        public Data data;
    }
}
