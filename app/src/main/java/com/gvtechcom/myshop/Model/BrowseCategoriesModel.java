package com.gvtechcom.myshop.Model;

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
        public Integer code;
        public String message;
        public List<BrowseCategoriesModel> response;
    }
}
