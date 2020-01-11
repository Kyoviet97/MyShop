package com.gvtechcom.myshop.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ItemDetailsModel implements Serializable {
    public String product_id;
    public String category_id;
    public String name;
    public String description;
    public Integer sold;
    public List<String> images;
    public String percent_sale;
    public String end_datetime;
    public Integer like;
    public Integer is_specie;
    public Integer me_like;
    public ReView review;
    public List<Product> product;
    public Store store;

    @SerializedName("relates_product")
    public List<RelatesProduct> relatesproduct;

    public class ReView{
        public String rating;
        public String count_rating;
        public String user_review;
        public String content_review;
        public String user_rating;
        public String date_rating;
        public List<String> images;
    }


    public class Product{
        public String _id;
        public String species_name;
        public String classify;
        public String quantity;
        public String image;
        public String price_origin;
        public String price;
        public String parent_id;
        public String level;
        public String sku;
        public List<Children> children;
    }

    public class Children{
        public String _id;
        public String species_name;
        public String classify;
        public String quantity;
        public String image;
        public String price_origin;
        public String price;
        public String parent_id;
        public String level;
        public String sku;
        public List<Children> children;
    }

    public class Store{
        public String store_id;
        public String store_name;
        public String feedback;
        public String items;
        public String sold;
    }


    public class RelatesProduct{
        public String product_id;
        public String name;
        public String min_price;
        public String sold;
        public String image_thumbnail;
    }


    public class ItemDetailsModelParser{
        @SerializedName("status")
        @Expose
        public Integer code;
        @SerializedName("content")
        @Expose
        public String message;
        @SerializedName("data")
        @Expose
        public ItemDetailsModel response;
    }
}
