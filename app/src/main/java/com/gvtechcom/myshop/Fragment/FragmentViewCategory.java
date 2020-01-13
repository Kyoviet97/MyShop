package com.gvtechcom.myshop.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
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
import com.gvtechcom.myshop.Model.DataViewCategoryModel;
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
import butterknife.OnClick;
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
    private DataViewCategoryModel dataViewCategory;
    private List<DataViewCategoryModel.Products> lsdataViewCategorTotal;
    private ToastDialog toastDialog;
    private int pageLoad;
    private String idCategoryBundle;
    private Boolean isMaxData = false;

    @BindView(R.id.recycler_view_category_top)
    RecyclerView recyclerViewViewCategoryTop;
    @BindView(R.id.recycler_view_category_main)
    RecyclerView recyclerViewViewCategoryMain;
    @BindView(R.id.netScroll_view_category)
    NestedScrollView netScrollViewCategory;
    @BindView(R.id.progressbar_load_api_view_category_footer)
    ProgressBar progressbarLoadApiViewCategoryFooter;
    @BindView(R.id.btn_filter)
    LinearLayout btnFilter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            Gson gson = new Gson();
            String stringJsonData = bundle.getString("jsonDataViewCategory");
            this.dataViewCategory = gson.fromJson(stringJsonData, DataViewCategoryModel.class);
            this.idCategoryBundle = bundle.getString("idCategoty");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootiew = inflater.inflate(R.layout.fragment_view_category, container, false);
        ButterKnife.bind(this, rootiew);
        return rootiew;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        setRetrofit();
        setRecyclerView();
        getNestedScrollChange();
    }

    private void init() {
        this.pageLoad = 2;
        lsdataViewCategorTotal = new ArrayList<>();
        if (dataViewCategory != null) {
            addListViewCategory(dataViewCategory);
        }
        mainActivity = (MainActivity) getActivity();
    }

    @OnClick({R.id.btn_filter})
    void onClick(View view){
        switch (view.getId()){
            case R.id.btn_filter:
                Toast.makeText(mainActivity, "CLICKKKKKKKKKK", Toast.LENGTH_SHORT).show();
                break;

            case R.id.action_bar_view_category:
                Toast.makeText(mainActivity, "pppppp", Toast.LENGTH_SHORT).show();
                break;

        }
    }

    private void setRetrofit() {
        Retrofit retrofit;
        retrofit = RetrofitBuilder.getRetrofit(Const.BASE_URL);
        apiServer = retrofit.create(APIServer.class);
    }

    private void addListViewCategory(DataViewCategoryModel lsAddData) {
        for (int i = 0; i <= lsAddData.products.size(); i++) {
            if (i != lsAddData.products.size()) {
                lsdataViewCategorTotal.add(dataViewCategory.products.get(i));
            } else {
                setAdapterViewCategory(lsdataViewCategorTotal);
            }
        }
    }

    private void setAdapterViewCategory(List<DataViewCategoryModel.Products> lsData) {
        if (adapterViewCategory == null) {
            adapterViewCategory = new AdapterViewCategory(getActivity(), lsData);
            recyclerViewViewCategoryMain.setAdapter(adapterViewCategory);
            setOnClickAdapter();
        } else {
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
        Call<DataViewCategoryModel.DataViewCategoryModelParser> call = apiServer.GetViewCategory(id, pageLoad, 8);
        call.enqueue(new Callback<DataViewCategoryModel.DataViewCategoryModelParser>() {
            @Override
            public void onResponse(Call<DataViewCategoryModel.DataViewCategoryModelParser> call, Response<DataViewCategoryModel.DataViewCategoryModelParser> response) {
                if (ValidateCallApi.ValidateAip(getActivity(), response.body().status, response.body().content)) {
                    if (response.body().data.products.size() > 0) {
                        pageLoad++;
                        addListViewCategory(response.body().data);
                    } else {
                        isMaxData = true;
                    }
                }
                progressbarLoadApiViewCategoryFooter.setAnimation(animation_side_down);
                progressbarLoadApiViewCategoryFooter.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<DataViewCategoryModel.DataViewCategoryModelParser> call, Throwable t) {
                progressbarLoadApiViewCategoryFooter.setAnimation(animation_side_down);
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
                        fragmentTransaction.add(R.id.frame_layout_home_manager, fragmentItemDetail);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mainActivity.setDisplayNavigationBar(true, false, true);
        mainActivity.setHideButtonNavigation(false);
        mainActivity.setColorIconDarkMode(false, R.color.color_StatusBar);
        mainActivity.setColorNavigationBar(R.drawable.ic_back_navigation, R.drawable.bkg_search_color_white, "apple watch", R.color.color_StatusBar, "#D1D8E0");

    }
}
