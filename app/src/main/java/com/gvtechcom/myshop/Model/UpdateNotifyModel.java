package com.gvtechcom.myshop.Model;

import java.util.List;

public class UpdateNotifyModel {
    public Integer current_page;
    public Integer total;
    public List<DataUpdateNoty> data;
    public String title;
    public String start_datetime;
    public String content;


    public class DataUpdateNoty{
        public String _id;
        public String title;
        public String start_datetime;
    }


    public class UpdateNotifyModelParser{
        public Integer code;
        public String message;
        public UpdateNotifyModel response;
    }

}
