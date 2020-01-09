package com.gvtechcom.myshop.Model;

import android.widget.TextView;

public class ShippingMethodModel {
    public String txtPriceShipping;
    public String txtLineShippingOne;
    public String txtLineShippingTwo;
    public String txtLineShippingThree;
    public Boolean isSelect;


    public ShippingMethodModel(String txtTitle, String txtLineShippingOne, String txtLineShippingTwo, String txtLineShippingThree) {
        this.txtPriceShipping = txtTitle;
        this.txtLineShippingOne = txtLineShippingOne;
        this.txtLineShippingTwo = txtLineShippingTwo;
        this.txtLineShippingThree = txtLineShippingThree;
    }
}
