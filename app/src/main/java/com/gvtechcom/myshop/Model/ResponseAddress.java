package com.gvtechcom.myshop.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseAddress implements Comparable<ResponseAddress>{
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("telephone")
    @Expose
    private String telephone;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("is_default")
    @Expose
    private Integer isDefault;

    @SerializedName("zip_code")
    @Expose
    private String zipCode;

    @SerializedName("phone_code")
    @Expose
    private String phoneCode;

    @SerializedName("detail")
    @Expose
    private String detail;

    public Boolean isCheck;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Boolean getCheck() {
        return isCheck;
    }

    public void setCheck(Boolean check) {
        isCheck = check;
    }


    @Override
    public int compareTo(ResponseAddress o) {
        return o.getIsDefault() - getIsDefault();
    }
}
