package com.gvtechcom.myshop.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
        @SerializedName("status")
        @Expose
        public Integer code;
        @SerializedName("content")
        @Expose
        public String message;
        @SerializedName("data")
        @Expose
        public BaseGetAPIShippingAddress response;
    }
}
