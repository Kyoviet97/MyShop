package com.gvtechcom.myshop.Utils;

public class ValidateInput {

    public static Boolean validatePhone(String s){
        Boolean val;
        if (s.length() < 1){
            s = "1";
        }
        String strVal = s.charAt(0) + "";

        if (s.length() == 10 && strVal.equals("0")){
            val = true;
        } else {
            val = false;
        }

        return val;
    }

    public static Boolean validatePass(String s){
        Boolean val;

        if (s.length() < 6){
            val = false;
        } else {
            val = true;
        }

        return val;
    }

    public static Boolean validateTheSamePass(String pass1, String pass2){
        Boolean val;
        if (pass1.equals(pass2)){
            val = true;
        }else {
            val = false;
        }
        return val;
    }

}
