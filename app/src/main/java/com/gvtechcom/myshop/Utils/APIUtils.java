package com.gvtechcom.myshop.Utils;

import com.gvtechcom.myshop.Network.APIServer;
import com.gvtechcom.myshop.Network.RetrofitBuilder;

public class APIUtils {
    public static APIServer apiServer(){
        return RetrofitBuilder.getRetrofit(Const.BASE_URL).create(APIServer.class);
    }
}
