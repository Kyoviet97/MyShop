package com.gvtechcom.myshop.Model;

import com.gvtechcom.myshop.Network.APIServer;
import com.gvtechcom.myshop.Network.RetrofitBuilder;
import com.gvtechcom.myshop.Utils.Const;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GoToItemDetail {
    private Retrofit retrofit;
    private APIServer apiServer;

    public ItemDetailsModel goToItemDetail(String idProduct){
        retrofit = RetrofitBuilder.getRetrofit(Const.BASE_URL);
        apiServer = retrofit.create(APIServer.class);
        ItemDetailsModel dataItemDetail = new ItemDetailsModel();
        Call<ItemDetailsModel.ItemDetailsModelParser> call = apiServer.GetApiItemDetails(idProduct);
        call.enqueue(new Callback<ItemDetailsModel.ItemDetailsModelParser>() {
            @Override
            public void onResponse(Call<ItemDetailsModel.ItemDetailsModelParser> call, Response<ItemDetailsModel.ItemDetailsModelParser> response) {
                ItemDetailsModel dataItemDetail = response.body().response;
            }

            @Override
            public void onFailure(Call<ItemDetailsModel.ItemDetailsModelParser> call, Throwable t) {

            }
        });

        return dataItemDetail;
    }
}
