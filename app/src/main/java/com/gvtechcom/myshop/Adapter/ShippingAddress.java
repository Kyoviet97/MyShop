package com.gvtechcom.myshop.Adapter;

public class ShippingAddress {
    private String idAddress;
    private String PhoneNumber;
    private String FullName;
    private int intDefault;
    private String ZipCode;
    private String PhoneCode;
    private String Address;

    public ShippingAddress(String idAddress, String phoneNumber, String fullName, int intDefault, String zipCode, String phoneCode, String address) {
        this.idAddress = idAddress;
        PhoneNumber = phoneNumber;
        FullName = fullName;
        this.intDefault = intDefault;
        ZipCode = zipCode;
        PhoneCode = phoneCode;
        Address = address;
    }

    public String getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(String idAddress) {
        this.idAddress = idAddress;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public int getIntDefault() {
        return intDefault;
    }

    public void setIntDefault(int intDefault) {
        this.intDefault = intDefault;
    }

    public String getZipCode() {
        return ZipCode;
    }

    public void setZipCode(String zipCode) {
        ZipCode = zipCode;
    }

    public String getPhoneCode() {
        return PhoneCode;
    }

    public void setPhoneCode(String phoneCode) {
        PhoneCode = phoneCode;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
