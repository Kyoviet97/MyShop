package com.gvtechcom.myshop.Model;

public class OrdersProductModel {
    public String ordersDate;
    public String ordersId;
    public String imgProduct;
    public String titleProduct;
    public String detailProduct;
    public String priceProduct;
    public Boolean isComplete;

    public OrdersProductModel(String ordersDate, String ordersId, String imgProduct, String titleProduct, String detailProduct, String priceProduct, Boolean isComplete) {
        this.ordersDate = ordersDate;
        this.ordersId = ordersId;
        this.imgProduct = imgProduct;
        this.titleProduct = titleProduct;
        this.detailProduct = detailProduct;
        this.priceProduct = priceProduct;
        this.isComplete = isComplete;
    }
}
