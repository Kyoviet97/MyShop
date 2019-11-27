package com.gvtechcom.myshop.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gvtechcom.myshop.Adapter.AdapterRelatesProduct;
import com.gvtechcom.myshop.MainActivity;
import com.gvtechcom.myshop.Model.ItemDetailsModel;
import com.gvtechcom.myshop.Network.APIServer;
import com.gvtechcom.myshop.Network.RetrofitBuilder;
import com.gvtechcom.myshop.R;
import com.gvtechcom.myshop.Utils.Const;
import com.mylibrary.ui.progress.ProgressDialogCustom;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FragmentItemDetail extends Fragment {
    private View rootView;
    private APIServer apiServer;
    private MainActivity mainActivity;
    private ItemDetailsModel.ItemDetailsModelParser dataApiItemDetail;
    private AdapterRelatesProduct adapterRelatesProduct;
    private String idProduct;
    private ProgressDialogCustom progressLoading;

    //TextView
    @BindView(R.id.txt_item_detail_description)
    TextView txtItemDetailDescription;
    @BindView(R.id.txt_item_detail_sold)
    TextView txtItemDetailSold;
    @BindView(R.id.txt_item_detail_like)
    TextView txtItemDetailLike;
    @BindView(R.id.txt_item_detail_percent_sale)
    TextView txtItemDetailPercentSale;
    @BindView(R.id.txt_item_detail_store_name)
    TextView storeName;
    @BindView(R.id.txt_item_detail_feedback_store)
    TextView storeFeedback;
    @BindView(R.id.txt_item_detail_items_store)
    TextView storeItem;
    @BindView(R.id.txt_item_detail_sold_store)
    TextView storeSold;

    //RecyclerView
    @BindView(R.id.recycler_related_product)
    RecyclerView recyclerRelatedProduct;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            this.idProduct = bundle.getString("idProduct");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_item_detail, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        callApiItemDataDetails(idProduct);
    }

    private void init() {
        mainActivity = (MainActivity) getActivity();
        mainActivity.setDisplayNavigationBar(false, false, false);
        mainActivity.setHideButtonNavigation(true);
        mainActivity.setColorStatusTran(true);
        progressLoading = new ProgressDialogCustom(getActivity());
        setRetroFit();
        setViewRecyclerView();
    }

    private void setRetroFit() {
        Retrofit retrofit;
        retrofit = RetrofitBuilder.getRetrofit(Const.BASE_URL);
        apiServer = retrofit.create(APIServer.class);
    }

    private void setViewRecyclerView() {
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerRelatedProduct.setLayoutManager(linearLayoutManager);
    }

    private void setAdapterRelatesProduct(List<ItemDetailsModel.RelatesProduct> lsRelatesProduct) {
        adapterRelatesProduct = new AdapterRelatesProduct(getActivity(), lsRelatesProduct);
        if (adapterRelatesProduct != null && lsRelatesProduct != null) {
            recyclerRelatedProduct.setAdapter(adapterRelatesProduct);
        }
    }

    private void callApiItemDataDetails(String productId) {
        progressLoading.onShow(false, "Loading...");
        Call<ItemDetailsModel.ItemDetailsModelParser> callApi = apiServer.GetApiItemDetails(productId);
        callApi.enqueue(new Callback<ItemDetailsModel.ItemDetailsModelParser>() {
            @Override
            public void onResponse(Call<ItemDetailsModel.ItemDetailsModelParser> call, Response<ItemDetailsModel.ItemDetailsModelParser> response) {
                if (response.body().code != 200) {
                    progressLoading.onHide();
                    Toast.makeText(mainActivity, response.body().message, Toast.LENGTH_SHORT).show();
                } else {
                    dataApiItemDetail = response.body();
                    if (dataApiItemDetail != null) {
                        txtItemDetailDescription.setText(dataApiItemDetail.response.description);
                        txtItemDetailSold.setText(dataApiItemDetail.response.sold + " orders");
                        txtItemDetailLike.setText(dataApiItemDetail.response.like + "");
                        txtItemDetailPercentSale.setText("$" + dataApiItemDetail.response.percent_sale);
                        setAdapterRelatesProduct(dataApiItemDetail.response.relatesproduct);
                        storeName.setText(dataApiItemDetail.response.store.store_name);
                        storeFeedback.setText(dataApiItemDetail.response.store.feedback + "%");
                        storeItem.setText(dataApiItemDetail.response.store.items);
                        storeSold.setText(dataApiItemDetail.response.store.sold);
                        progressLoading.onHide();
                    }
                }
            }

            @Override
            public void onFailure(Call<ItemDetailsModel.ItemDetailsModelParser> call, Throwable t) {
                progressLoading.onHide();
                System.out.println("======================> " + t.toString());
            }
        });
    }

}
