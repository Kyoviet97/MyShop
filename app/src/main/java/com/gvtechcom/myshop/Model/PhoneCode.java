package com.gvtechcom.myshop.Model;

import java.util.List;

public class PhoneCode {
    private String NameCountry;
    private List<String> PhoneCode;
    private Boolean isCheck;

    public String getNameCountry() {
        return NameCountry;
    }

    public void setNameCountry(String nameCountry) {
        NameCountry = nameCountry;
    }

    public List<String> getPhoneCode() {
        return PhoneCode;
    }

    public void setPhoneCode(List<String> phoneCode) {
        PhoneCode = phoneCode;
    }

    public Boolean getCheck() {
        return isCheck;
    }

    public void setCheck(Boolean check) {
        isCheck = check;
    }
}