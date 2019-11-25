package com.gvtechcom.myshop.Model;

public class GetAddressIdAddress {
    private String name;
    private String telephone;
    private String is_default;
    private String zip_code;
    private String phone_code;
    private String specific;

    public String getName() {
        return name;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getIs_default() {
        return is_default;
    }

    public String getZip_code() {
        return zip_code;
    }

    public String getPhone_code() {
        return phone_code;
    }

    public String getSpecific() {
        return specific;
    }

    public CountryInfos country;
    public CityInfo city;
    public DistricInfo district;
    public WardInfo ward;

    public class CountryInfos{
        String id;
        String name;

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    public class CityInfo{
        String id;
        String name;

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    public class DistricInfo{
        String id;
        String name;

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    public class WardInfo{
        String id;
        String name;

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    public class GetAddressIdParser{
        public int code;
        public String message;
        public GetAddressIdAddress response;
    }


}

