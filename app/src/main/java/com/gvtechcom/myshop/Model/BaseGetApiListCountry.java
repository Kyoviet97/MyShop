package com.gvtechcom.myshop.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BaseGetApiListCountry {
    @SerializedName("status")
    @Expose
    private Integer code;
    @SerializedName("content")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<ResponseCountry> responseCountryList = null;

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

    public List<ResponseCountry> getResponseCountry() {
        return responseCountryList;
    }

    public void setResponseCountry(List<ResponseCountry> responseCountryList) {
        this.responseCountryList = responseCountryList;
    }
}
