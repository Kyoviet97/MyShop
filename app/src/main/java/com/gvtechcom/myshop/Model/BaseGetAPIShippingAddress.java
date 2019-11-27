package com.gvtechcom.myshop.Model;

import java.util.List;

public class BaseGetAPIShippingAddress {

    public List<Data> data;

    public class Data implements Comparable<Data>{
        public String id;
        public String telephone;
        public String name;
        public Integer is_default;
        public String zip_code;
        public String phone_code;
        public String detail;

        @Override
        public int compareTo(Data o) {
            return o.is_default - is_default;
        }
    }

    public class BaseGetAPIShippingAddressParser{
        public Integer code;
        public String message;
        public BaseGetAPIShippingAddress response;
    }
}
