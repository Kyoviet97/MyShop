package com.gvtechcom.myshop.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("percent_sale")
    @Expose
    private String percentSale;
    @SerializedName("price_sale")
    @Expose
    private String priceSale;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("sold")
    @Expose
    private String sold;
    @SerializedName("image")
    @Expose
    private String image;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getPercentSale() {
        return percentSale;
    }

    public void setPercentSale(String percentSale) {
        this.percentSale = percentSale;
    }

    public String getPriceSale() {
        return priceSale;
    }

    public void setPriceSale(String priceSale) {
        this.priceSale = priceSale;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getSold() {
        return sold;
    }

    public void setSold(String sold) {
        this.sold = sold;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}