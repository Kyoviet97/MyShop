package com.gvtechcom.myshop.Fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.gvtechcom.myshop.Adapter.AdapterNameSubCategory;
import com.gvtechcom.myshop.Adapter.AdapterRecyclerBrowseCategoriesLeft;
import com.gvtechcom.myshop.MainActivity;
import com.gvtechcom.myshop.Model.BrowseCategoriesModel;
import com.gvtechcom.myshop.Model.ProductByCategoryModel;
import com.gvtechcom.myshop.Network.APIServer;
import com.gvtechcom.myshop.Network.RetrofitBuilder;
import com.gvtechcom.myshop.R;
import com.gvtechcom.myshop.Utils.Const;
import com.gvtechcom.myshop.dialog.ToastDialog;

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
    private MainActivity mainActivity;
    private ToastDialog toastDialog;
    private FragmentViewCategory fragmentViewCategory;
    private FragmentItemDetail fragmentItemDetail;

    @BindView(R.id.recycler_browse_categories_left)
    RecyclerView RecyclerViewBrowseLeft;

    @BindView(R.id.recycler_name_sub_category)
    RecyclerView recyclerNameSubCategory;

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
        mainActivity = (MainActivity) getActivity();
        mainActivity.setDisplayNavigationBar(true, true, false);
        mainActivity.setColorIconDarkMode(true, R.color.white);
        mainActivity.setColorNavigationBar(R.drawable.ic_back_navigation, R.drawable.bkg_search_color_gray, "  apple watch", R.color.white, "#E8A7A7A7");
        toastDialog = new ToastDialog(getActivity());
        setRetrofit();
        setRecyclerView();
        callApiBrowse();

        fragmentItemDetail = new FragmentItemDetail();
    }


    private void setRecyclerView() {
        LinearLayoutManager layoutManagerRecyclerLeft = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        RecyclerViewBrowseLeft.setLayoutManager(layoutManagerRecyclerLeft);

        LinearLayoutManager layoutManagerRecyclerNameSubCategory = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerNameSubCategory.setLayoutManager(layoutManagerRecyclerNameSubCategory);
    }

    private void setRetrofit() {
        retrofit = RetrofitBuilder.getRetrofit(Const.BASE_URL);
        apiServer = retrofit.create(APIServer.class);
    }

    private void callApiBrowse() {
        Call<BrowseCategoriesModel.BrowseCategoriesModelParser> callApiBrowseCtegoties = apiServer.GetApiBrowseCategoriesModel();
        callApiBrowseCtegoties.enqueue(new Callback<BrowseCategoriesModel.BrowseCategoriesModelParser>() {
            @Override
            public void onResponse(Call<BrowseCategoriesModel.BrowseCategoriesModelParser> call, Response<BrowseCategoriesModel.BrowseCategoriesModelParser> response) {
                if (response.body().code != 200) {
                    toastDialog.onShow(response.body().message);
                } else {
                    dataBrowseCategories = response.body().response;
                    if (dataBrowseCategories != null) {
                        adapterRecyclerBrowseCategoriesLeft = new AdapterRecyclerBrowseCategoriesLeft(getActivity(), dataBrowseCategories);
                        RecyclerViewBrowseLeft.setAdapter(adapterRecyclerBrowseCategoriesLeft);
                        setOnClickItemAdapter();
                    }
                }
            }

            @Override
            public void onFailure(Call<BrowseCategoriesModel.BrowseCategoriesModelParser> call, Throwable t) {
            }
        });
    }

    private void callApiViewCategory(String idCategoty) {
        Gson gson = new Gson();
        Call<ProductByCategoryModel.ProductByCategoryModelParser> call = apiServer.GetViewCategory(idCategoty);
        call.enqueue(new Callback<ProductByCategoryModel.ProductByCategoryModelParser>() {
            @Override
            public void onResponse(Call<ProductByCategoryModel.ProductByCategoryModelParser> call, Response<ProductByCategoryModel.ProductByCategoryModelParser> response) {
                if (response.body().status != 200) {
                    toastDialog.onShow(response.body().content);
                } else {
                    ProductByCategoryModel dataViewCategor = response.body().data;
                    if (dataViewCategor != null) {
                        String jsonData = gson.toJson(dataViewCategor);
                        Bundle bundle = new Bundle();
                        bundle.putString("jsonDataViewCategory", jsonData);
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentViewCategory = new FragmentViewCategory();
                        fragmentViewCategory.setArguments(bundle);
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.add(R.id.content_home_frame_layout, fragmentViewCategory);
                        fragmentTransaction.addToBackStack("home");
                        fragmentTransaction.commit();
                    }
                }
            }
            @Override
            public void onFailure(Call<ProductByCategoryModel.ProductByCategoryModelParser> call, Throwable t) {

            }
        });
    }

    private void setOnClickItemAdapter() {
        adapterRecyclerBrowseCategoriesLeft.setOnClickItem(new AdapterRecyclerBrowseCategoriesLeft.setOnClickItem() {
            @Override
            public void onClickItem(String categoryId, int position) {
                adapterNameSubCategory = new AdapterNameSubCategory(getActivity(), dataBrowseCategories.get(position).children);
                recyclerNameSubCategory.setAdapter(adapterNameSubCategory);
                adapterNameSubCategory.setOnItemClickListener(new AdapterNameSubCategory.setOnClickListenr() {
                    @Override
                    public void setOnClick(List<BrowseCategoriesModel.Children> lsNameChildren, int position) {
                          callApiViewCategory(lsNameChildren.get(position).category_id);
                    }
                });
            }
        });
    }
}
