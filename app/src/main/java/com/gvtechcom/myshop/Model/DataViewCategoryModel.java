package com.gvtechcom.myshop.Model;

import java.util.List;

public class DataViewCategoryModel {

    public List<Products> products;

    public class Products{
        public String id;
        public String name;
        public String image;
        public String price;
        public String sold;
    }

    public class meta{
        public Pagination pagination;
    }

    public class Pagination{
        public int total;
        public int current_page;
    }

    public class DataViewCategoryModelParser{
        public int status;
        public String content;
        public DataViewCategoryModel data;
    }
}
