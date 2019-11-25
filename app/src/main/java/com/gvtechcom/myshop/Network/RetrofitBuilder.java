package com.gvtechcom.myshop.Network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {
    public static Retrofit retrofit = null;
    public static Retrofit getRetrofit(String BaseUrl){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                                    .readTimeout(5000, TimeUnit.MILLISECONDS)
                                    .writeTimeout(5000, TimeUnit.MILLISECONDS)
                                    .connectTimeout(5000, TimeUnit.MILLISECONDS)
                                    .retryOnConnectionFailure(true)
                                    .build();

        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit;
    }
}
