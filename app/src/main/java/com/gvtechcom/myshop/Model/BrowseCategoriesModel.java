package com.gvtechcom.myshop.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BrowseCategoriesModel {
        public String id;
        public String name;
        public String parent_id;
        public String image;
        public List<Children> children;
        public Boolean isSelect;



   public class Children{
       public String id;
       public String name;
       public String parent_id;
       public String image;
       public List<Children> children;
   }

   public class TopBrands{
       public String id;
       public String image;
       public String name;
   }

   public class Data{
       public List<BrowseCategoriesModel> categories;
       public List<TopBrands> top_brands;
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
        public Data data;

    }
}
