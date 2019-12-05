package com.gvtechcom.myshop.Interface;


public class ClassSendDataInterface {
    private SendIdCatergory sendIdCatergory;

    public void setSendIdCatergory(SendIdCatergory sendIdCatergory) {
        this.sendIdCatergory = sendIdCatergory;
    }

    public void setID(String id){
        sendIdCatergory.sendIdCategory(id);
    }
}
