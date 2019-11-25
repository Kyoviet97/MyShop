package com.gvtechcom.myshop.Model;

import java.util.List;

public class CountryInfo {

    public String id;
    public String name;
    public List<String> phone_code;
    public List<CityInfo> cities;
    public Boolean isCheck;


    public class CityInfo{
        public String id;
        public String name;
        public Boolean isCheck;
    }


    public static class CountryParser{
        public int code;
        public String message;
        public List<CountryInfo> response;
    }
}
