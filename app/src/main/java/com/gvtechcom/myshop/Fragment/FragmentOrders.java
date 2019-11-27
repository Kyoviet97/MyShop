package com.gvtechcom.myshop.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.gvtechcom.myshop.Model.CountryInfoModel;
import com.gvtechcom.myshop.Network.APIServer;
import com.gvtechcom.myshop.Network.RetrofitBuilder;
import com.gvtechcom.myshop.R;
import com.gvtechcom.myshop.Utils.Const;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FragmentOrders extends Fragment {
    View rootView;
    APIServer apiServer;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_orders, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        System.out.println("==========================> Call API");

        Retrofit retrofit;
        retrofit = RetrofitBuilder.getRetrofit(Const.BASE_URL);
        apiServer = retrofit.create(APIServer.class);

       Call<CountryInfoModel.CountryInfoModelParser> call = apiServer.GetApiShippingTest("a", "A", "a", "1");
       call.enqueue(new Callback<CountryInfoModel.CountryInfoModelParser>() {
           @Override
           public void onResponse(Call<CountryInfoModel.CountryInfoModelParser> call, Response<CountryInfoModel.CountryInfoModelParser> response) {
               CountryInfoModel.CountryInfoModelParser data = response.body();

               System.out.println("==========================>" + data.code.toString());
               System.out.println("==========================>" + data.response.data.get(0).cities.get(2).name);
               System.out.println("==========================>" + data.response.data.get(0).name);

           }
           @Override
           public void onFailure(Call<CountryInfoModel.CountryInfoModelParser> call, Throwable t) {

           }
       });

    }
}