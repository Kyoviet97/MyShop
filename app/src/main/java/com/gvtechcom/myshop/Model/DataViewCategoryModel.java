package com.gvtechcom.myshop.Model;

import java.util.List;

public class DataViewCategoryModel {

    public List<Products> products;

    class Products{
        public String id;
        public String name;
        public String image;
        public String price;
        public String sold;
    }


    class DataViewCategoryModelParser{
        public int status;
        public String content;
        public DataViewCategoryModel data;
    }
}
