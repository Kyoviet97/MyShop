package com.gvtechcom.myshop.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.gvtechcom.myshop.Adapter.AdapterItemSubCategory;
import com.gvtechcom.myshop.Adapter.AdapterViewCategory;
import com.gvtechcom.myshop.Interface.ListtenOnDestroyView;
import com.gvtechcom.myshop.MainActivity;
import com.gvtechcom.myshop.Model.BaseGetApiAddress;
import com.gvtechcom.myshop.Model.BaseGetApiData;
import com.gvtechcom.myshop.Model.GoToItemDetail;
import com.gvtechcom.myshop.Model.ItemDetailsModel;
import com.gvtechcom.myshop.Model.JustForYou;
import com.gvtechcom.myshop.Model.Product;
import com.gvtechcom.myshop.Model.ProductByCategoryModel;
import com.gvtechcom.myshop.Network.APIServer;
import com.gvtechcom.myshop.Network.RetrofitBuilder;
import com.gvtechcom.myshop.R;
import com.gvtechcom.myshop.Utils.Const;
import com.gvtechcom.myshop.Utils.ShowProgressBar;
import com.gvtechcom.myshop.Utils.ValidateCallApi;
import com.gvtechcom.myshop.dialog.ToastDialog;
import com.mylibrary.ui.progress.ProgressDialogCustom;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FragmentViewCategory extends Fragment {
    private View rootiew;
    private AdapterViewCategory adapterViewCategory;
    private MainActivity mainActivity;
    private APIServer apiServer;
    private Boolean isLoadMore = true;
    private FragmentManager fragmentManager;
    private FragmentItemDetail fragmentItemDetail;
    private ProductByCategoryModel dataViewCategory;
    private List<ProductByCategoryModel.Products> lsdataViewCategorTotal;
    private ToastDialog toastDialog;
    private int pageLoad;
    private String idCategoryBundle;
    private Boolean isMaxData;

    @BindView(R.id.recycler_view_category_top)
    RecyclerView recyclerViewViewCategoryTop;
    @BindView(R.id.recycler_view_category_main)
    RecyclerView recyclerViewViewCategoryMain;
    @BindView(R.id.netScroll_view_category)
    NestedScrollView netScrollViewCategory;
    @BindView(R.id.progressbar_load_api_view_category_footer)
    ProgressBar progressbarLoadApiViewCategoryFooter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootiew = inflater.inflate(R.layout.fragment_view_category, container, false);
        ButterKnife.bind(this, rootiew);
        toastDialog = new ToastDialog(getActivity());
        return rootiew;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRetrofit();
        setRecyclerView();
        init();
        if (dataViewCategory.total > Const.TOTAL_PRODUCT) {
            getNestedScrollChange();
        }
    }

    private void init() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            this.pageLoad = 2;
            this.isMaxData = false;
            this.lsdataViewCategorTotal = new ArrayList<>();

            Gson gson = new Gson();
            String stringJsonData = bundle.getString("jsonDataViewCategory");
            this.dataViewCategory = gson.fromJson(stringJsonData, ProductByCategoryModel.class);
            idCategoryBundle = bundle.getString("idCategoty");
            addListViewCategory(dataViewCategory.products);
        }
    }

    private void setRetrofit() {
        Retrofit retrofit;
        retrofit = RetrofitBuilder.getRetrofit(Const.BASE_URL);
        apiServer = retrofit.create(APIServer.class);
    }

    private void addListViewCategory(List<ProductByCategoryModel.Products> lsAddData){
        for (int i = 0; i <= lsAddData.size(); i++){
            if (i != lsAddData.size()){
                lsdataViewCategorTotal.add(dataViewCategory.products.get(i));
            }else {
                setAdapterViewCategory(lsdataViewCategorTotal);
            }
        }
    }

    private void setAdapterViewCategory(List<ProductByCategoryModel.Products> lsData){
        if (adapterViewCategory == null){
            adapterViewCategory = new AdapterViewCategory(getActivity(), lsData);
            recyclerViewViewCategoryMain.setAdapter(adapterViewCategory);
            setOnClickAdapter();
        }else {
            adapterViewCategory.upDateAdapter(lsData);
        }
    }

    private void setRecyclerView() {
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerViewViewCategoryMain.setLayoutManager(linearLayoutManager);
    }

    private void setOnClickAdapter() {
        adapterViewCategory.setOnClickListener(new AdapterViewCategory.SetOnClickListener() {
            @Override
            public void setOnClickListener(String idCategory) {
                callApiDataItemDetail(idCategory);
            }
        });
    }

    private void callApiViewCategory(String id) {
        Animation animation_side_up = AnimationUtils.loadAnimation(getActivity(), R.anim.animation_slide_up_progressbar);
        Animation animation_side_down = AnimationUtils.loadAnimation(getActivity(), R.anim.animation_slide_down_progressbar);
        progressbarLoadApiViewCategoryFooter.setVisibility(View.VISIBLE);
        progressbarLoadApiViewCategoryFooter.setAnimation(animation_side_up);
        Call<ProductByCategoryModel.ProductByCategoryModelParser> call = apiServer.GetViewCategory(id, pageLoad, 8);
        call.enqueue(new Callback<ProductByCategoryModel.ProductByCategoryModelParser>() {
            @Override
            public void onResponse(Call<ProductByCategoryModel.ProductByCategoryModelParser> call, Response<ProductByCategoryModel.ProductByCategoryModelParser> response) {
                if (response.body().status != 200) {
                    toastDialog.onShow(response.body().content);
                } else {
                    ProductByCategoryModel dataViewCategor = response.body().data;
                    pageLoad = pageLoad + 1;
                    progressbarLoadApiViewCategoryFooter.setAnimation(animation_side_down);
                    progressbarLoadApiViewCategoryFooter.setVisibility(View.GONE);
                    addListViewCategory(response.body().data.products);
                }
            }
            @Override
            public void onFailure(Call<ProductByCategoryModel.ProductByCategoryModelParser> call, Throwable t) {
                progressbarLoadApiViewCategoryFooter.setVisibility(View.GONE);
            }
        });
    }

    private void getNestedScrollChange() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            netScrollViewCategory.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    int nestedScrollHight = (netScrollViewCategory.getChildAt(0).getHeight() - netScrollViewCategory.getHeight());
                    if (isLoadMore && scrollY == nestedScrollHight && !isMaxData) {
                        isLoadMore = false;
                        System.out.println("====================>" + idCategoryBundle);
                        callApiViewCategory(idCategoryBundle);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                isLoadMore = true;
                            }
                        }, 1000);
                    }
                }
            });
        }
    }

    private void callApiDataItemDetail(String idProduct) {
        ShowProgressBar.showProgress(getActivity());
        Call<ItemDetailsModel.ItemDetailsModelParser> callApi = apiServer.GetApiItemDetails(idProduct);
        callApi.enqueue(new Callback<ItemDetailsModel.ItemDetailsModelParser>() {
            @Override
            public void onResponse(Call<ItemDetailsModel.ItemDetailsModelParser> call, Response<ItemDetailsModel.ItemDetailsModelParser> response) {
                ShowProgressBar.hideProgress();
                if (ValidateCallApi.ValidateAip(getActivity(), response.body().code, response.body().message)) {
                    if (response.body().response != null) {
                        Gson gson = new Gson();
                        String jsonData = gson.toJson(response.body());
                        fragmentManager = getFragmentManager();
                        fragmentItemDetail = new FragmentItemDetail();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        Bundle bundle = new Bundle();
                        bundle.putString("fromToFragment", "homeViewCategory");
                        bundle.putString("dataJson", jsonData);
                        fragmentItemDetail.setArguments(bundle);
                        fragmentTransaction.add(R.id.content_home_frame_layout, fragmentItemDetail);
                        fragmentTransaction.addToBackStack("home");
                        fragmentTransaction.commit();
                    }
                }
            }

            @Override
            public void onFailure(Call<ItemDetailsModel.ItemDetailsModelParser> call, Throwable t) {

            }
        });
    }
}
