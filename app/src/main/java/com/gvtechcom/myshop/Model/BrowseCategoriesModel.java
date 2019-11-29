package com.gvtechcom.myshop.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BrowseCategoriesModel {
        public String category_id;
        public String category_name;
        public String parent_id;
        public String category_image;
        public List<String> top_brands;
        public List<Children> children;
        public Boolean isSelect;



   public class Children{
       public String category_id;
       public String category_name;
       public String parent_id;
       public String category_image;
       public List<String> top_brands;
       public List<Children> children;
   }

    public class BrowseCategoriesModelParser{
        @SerializedName("status")
        @Expose
        public Integer code;
        @SerializedName("content")
        @Expose
        public String message;
        @SerializedName("data")
        @Expose
        public List<BrowseCategoriesModel> response;
    }
}
