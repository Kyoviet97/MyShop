package com.gvtechcom.myshop.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BaseGetApiAddress {
    @SerializedName("status")
    @Expose
    private Integer code;
    @SerializedName("content")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<ResponseAddress> responseAddress = null;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ResponseAddress> getResponseAddress() {
        return responseAddress;
    }

    public void setResponseAddress(List<ResponseAddress> responseAddress) {
        this.responseAddress = responseAddress;
    }
}
