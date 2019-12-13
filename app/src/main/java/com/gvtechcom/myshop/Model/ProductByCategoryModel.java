package com.gvtechcom.myshop.Model;

import java.util.List;

public class ProductByCategoryModel {

    public List<String> top_brands;
    public Integer current_page;
    public Integer total;
    public List<Products> products;

    public class Products {
        public String product_id;
        public String product_name;
        public String image;
        public Integer price;
        public Integer sold;
    }

    public class ProductByCategoryModelParser {
        public Integer status;
        public String content;
        public ProductByCategoryModel data;
    }
}
