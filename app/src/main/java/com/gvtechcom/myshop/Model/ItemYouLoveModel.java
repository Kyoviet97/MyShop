package com.gvtechcom.myshop.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ItemYouLoveModel {
    public Integer current_page;
    public Integer total;
    public List<Product> products;

    public class Product {
        public String product_id;
        public String product_name;
        public String image;
        public String price;
        public String sold;
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
        public ItemYouLoveModel response;
    }
}
