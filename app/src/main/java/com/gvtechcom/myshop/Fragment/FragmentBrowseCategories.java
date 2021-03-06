package com.gvtechcom.myshop.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.gvtechcom.myshop.Adapter.AdapterNameSubCategory;
import com.gvtechcom.myshop.Adapter.AdapterRecyclerBrowseCategoriesLeft;
import com.gvtechcom.myshop.Adapter.AdapterTopBrandsBrowseCategory;
import com.gvtechcom.myshop.Interface.SendIdCatergory;
import com.gvtechcom.myshop.MainActivity;
import com.gvtechcom.myshop.Model.BrowseCategoriesModel;
import com.gvtechcom.myshop.Model.CategoryFilterModel;
import com.gvtechcom.myshop.Model.DataViewCategoryModel;
import com.gvtechcom.myshop.Network.APIServer;
import com.gvtechcom.myshop.Network.RetrofitBuilder;
import com.gvtechcom.myshop.R;
import com.gvtechcom.myshop.Utils.Const;
import com.gvtechcom.myshop.Utils.ValidateCallApi;
import com.gvtechcom.myshop.dialog.ToastDialog;
import com.mylibrary.ui.progress.ProgressDialogCustom;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FragmentBrowseCategories extends Fragment {
    private View rootView;
    private APIServer apiServer;
    private Retrofit retrofit;
    private List<BrowseCategoriesModel> dataBrowseCategories;

    private AdapterRecyclerBrowseCategoriesLeft adapterRecyclerBrowseCategoriesLeft;
    private AdapterNameSubCategory adapterNameSubCategory;
    private AdapterTopBrandsBrowseCategory adapterTopBrands;

    private MainActivity mainActivity;
    private ToastDialog toastDialog;
    private FragmentViewCategory fragmentViewCategory;
    private FragmentManager fragmentManager;
    private ProgressDialogCustom progressDialogCustom;

    @BindView(R.id.recycler_browse_categories_left)
    RecyclerView RecyclerViewBrowseLeft;

    @BindView(R.id.recycler_name_sub_category)
    RecyclerView recyclerNameSubCategory;

    @BindView(R.id.recycler_top_brands_browse)
    RecyclerView recyclerTopBrands;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        mainActivity.setDisplayNavigationBar(true, true, false);
        mainActivity.setColorIconDarkMode(true, R.color.white);
        mainActivity.setColorNavigationBar(R.drawable.ic_back_navigation, R.drawable.bkg_search_color_gray, "apple watch", R.color.white, "#9D9690");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_browse_categories, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        toastDialog = new ToastDialog(getActivity());
        progressDialogCustom = new ProgressDialogCustom(getActivity());
        setRetrofit();
        setRecyclerView();
        callApiBrowse();
        fragmentManager = getFragmentManager();
    }


    private void setRecyclerView() {
        LinearLayoutManager layoutManagerRecyclerLeft = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        RecyclerViewBrowseLeft.setLayoutManager(layoutManagerRecyclerLeft);

        LinearLayoutManager layoutManagerRecyclerNameSubCategory = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerNameSubCategory.setLayoutManager(layoutManagerRecyclerNameSubCategory);

        LinearLayoutManager layoutManagerRecyclerTopBrands = new GridLayoutManager(getActivity(), 3);
        recyclerTopBrands.setLayoutManager(layoutManagerRecyclerTopBrands);


    }

    private void setRetrofit() {
        retrofit = RetrofitBuilder.getRetrofit(Const.BASE_URL);
        apiServer = retrofit.create(APIServer.class);
    }

    private void callApiBrowse() {
        progressDialogCustom.onShow(false, "");
        Call<BrowseCategoriesModel.BrowseCategoriesModelParser> callApiBrowseCtegoties = apiServer.GetApiBrowseCategoriesLeft("0");
        callApiBrowseCtegoties.enqueue(new Callback<BrowseCategoriesModel.BrowseCategoriesModelParser>() {
            @Override
            public void onResponse(Call<BrowseCategoriesModel.BrowseCategoriesModelParser> call, Response<BrowseCategoriesModel.BrowseCategoriesModelParser> response) {
                if (response.body().code != 200) {
                    toastDialog.onShow(response.body().message);
                } else {
                    dataBrowseCategories = response.body().data.categories;
                    if (dataBrowseCategories != null) {
                        setAdapterBrowseCategoriesLeft(dataBrowseCategories);
                        callApiMainBrowse(dataBrowseCategories.get(0).id);
                    }
                }

                progressDialogCustom.onHide();
            }

            @Override
            public void onFailure(Call<BrowseCategoriesModel.BrowseCategoriesModelParser> call, Throwable t) {
                progressDialogCustom.onHide();
            }
        });
    }

    private void setAdapterBrowseCategoriesLeft(List<BrowseCategoriesModel> dataBrowseCategories) {
        adapterRecyclerBrowseCategoriesLeft = new AdapterRecyclerBrowseCategoriesLeft(getActivity(), dataBrowseCategories);
        RecyclerViewBrowseLeft.setAdapter(adapterRecyclerBrowseCategoriesLeft);
        adapterRecyclerBrowseCategoriesLeft.setOnClickItem(new AdapterRecyclerBrowseCategoriesLeft.setOnClickItem() {
            @Override
            public void onClickItem(String idCategory) {
                callApiMainBrowse(idCategory);
            }
        });
    }

    private void setAdapterNameSubCategory(List<BrowseCategoriesModel> dataBrowseCategories) {
        if (adapterNameSubCategory == null) {
            adapterNameSubCategory = new AdapterNameSubCategory(getActivity(), dataBrowseCategories);
            recyclerNameSubCategory.setAdapter(adapterNameSubCategory);
            adapterNameSubCategory.setSenIdCategory(new SendIdCatergory() {
                @Override
                public void sendIdCategory(String idCategory) {
                    callApiViewCategory(idCategory, 1);
                }
            });
        } else {
            adapterNameSubCategory.updateData(dataBrowseCategories);
        }
    }

    private void setAdapterTopBrands(List<BrowseCategoriesModel.TopBrands> dataTopBrands) {
        if (adapterTopBrands == null) {
            adapterTopBrands = new AdapterTopBrandsBrowseCategory(dataTopBrands, getActivity());
            recyclerTopBrands.setAdapter(adapterTopBrands);
            adapterTopBrands.setSenIdCategory(new SendIdCatergory() {
                @Override
                public void sendIdCategory(String idCategory) {
                    callApiViewCategory(idCategory, 1);
                }
            });
        } else {
            adapterTopBrands.updateData(dataTopBrands);

        }
    }

    private void callApiMainBrowse(String idCategory){
        progressDialogCustom.onShow(false, "");
        Call<BrowseCategoriesModel.BrowseCategoriesModelParser> apiMainBrowse = apiServer.GetApiBrowseCategoriesMain(idCategory);
        apiMainBrowse.enqueue(new Callback<BrowseCategoriesModel.BrowseCategoriesModelParser>() {
            @Override
            public void onResponse(Call<BrowseCategoriesModel.BrowseCategoriesModelParser> call, Response<BrowseCategoriesModel.BrowseCategoriesModelParser> response) {
                progressDialogCustom.onHide();
                if (ValidateCallApi.ValidateAip(getActivity(), response.body().code, response.body().message)){
                    setAdapterTopBrands(response.body().data.top_brands);
                    setAdapterNameSubCategory(response.body().data.categories);
                }
            }

            @Override
            public void onFailure(Call<BrowseCategoriesModel.BrowseCategoriesModelParser> call, Throwable t) {
                progressDialogCustom.onHide();
            }
        });
    }

    private void callApiViewCategory(String idCategory, int page) {
        progressDialogCustom.onShow(false, "");
        Call<DataViewCategoryModel.DataViewCategoryModelParser> callApiViewCategory = apiServer.GetViewCategory(idCategory, page, Const.TOTAL_PRODUCT);
        callApiViewCategory.enqueue(new Callback<DataViewCategoryModel.DataViewCategoryModelParser>() {
            @Override
            public void onResponse(Call<DataViewCategoryModel.DataViewCategoryModelParser> call, Response<DataViewCategoryModel.DataViewCategoryModelParser> response) {
                if (ValidateCallApi.ValidateAip(getActivity(), response.body().status, response.body().content)) {
                    sendDataToViewCategory(response.body().data, idCategory);
                }
                progressDialogCustom.onHide();
            }

            @Override
            public void onFailure(Call<DataViewCategoryModel.DataViewCategoryModelParser> call, Throwable t) {
                progressDialogCustom.onHide();
            }
        });
    }


    private void sendDataToViewCategory(DataViewCategoryModel lsData, String idCategory) {
        Gson gson = new Gson();
        String dataStringJson = gson.toJson(lsData);
        Bundle bundleData = new Bundle();
        bundleData.putString("jsonDataViewCategory", dataStringJson);
        bundleData.putString("idCategoty", idCategory);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentViewCategory = new FragmentViewCategory();
        fragmentViewCategory.setArguments(bundleData);
        fragmentTransaction.add(R.id.frame_layout_home_manager, fragmentViewCategory);
        fragmentTransaction.addToBackStack("browseCategory");
        fragmentTransaction.commit();
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
