package com.gvtechcom.myshop.Model;

import java.util.List;

public class FlashDealsDetails {
    public Integer total;
    public Integer current_page;
    public List<ProductGroups>  product_groups;
    public List<ProductsData> products;

    public class ProductGroups{
        public String product_group_id;
        public String product_group_name;
        public String product_group_image;
        public Boolean icSelect;
    }

    public class ProductsData{
        public String product_id;
        public String image;
        public String product_name;
        public String price_sale;
        public String percent_sale;
        public String quantity;
        public String sold;

    }


    public static class FlashDealsDetailsParser{
        public Integer code;
        public String message;
        public FlashDealsDetails response;

    }
}
