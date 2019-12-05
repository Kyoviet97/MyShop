package com.gvtechcom.myshop.Model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FlashDeals {

    @SerializedName("end_datetime")
    @Expose
    private Integer endDatetime;
    @SerializedName("products")
    @Expose
    private List<Product> products = null;

    public Integer getEndDatetime() {
        return endDatetime;
    }

    public void setEndDatetime(Integer endDatetime) {
        this.endDatetime = endDatetime;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

}