package com.gvtechcom.myshop.Model;

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
        public Integer code;
        public String message;
        public ItemYouLoveModel response;
    }
}
