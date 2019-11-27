package com.gvtechcom.myshop.Model;

import java.util.List;

public class CountryInfoModel {
    public List<Data> data;

    public class Data{
        public String id;
        public String name;
        public List<String> phone_code;
        public List<City> cities;
        public Boolean isCheck;
    }


    public class City{
        public String id;
        public String name;
    }

    public class CountryInfoModelParser{
        public Integer code;
        public String message;
        public CountryInfoModel response;
    }

}
