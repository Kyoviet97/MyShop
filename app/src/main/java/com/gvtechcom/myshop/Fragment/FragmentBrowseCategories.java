package com.gvtechcom.myshop.Fragment;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gvtechcom.myshop.Adapter.AdapterRecyclerBrowseCategoriesLeft;
import com.gvtechcom.myshop.MainActivity;
import com.gvtechcom.myshop.Model.BrowseCategoriesModel;
import com.gvtechcom.myshop.Network.APIServer;
import com.gvtechcom.myshop.Network.RetrofitBuilder;
import com.gvtechcom.myshop.R;
import com.gvtechcom.myshop.Utils.Const;

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
    private MainActivity mainActivity;

    @BindView(R.id.recycler_browse_categories_left)
    RecyclerView RecyclerViewBrowseLeft;

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
        setRetrofit();
        setRecyclerView();
        callApiBrowse();
    }

    private void setRecyclerView(){
        LinearLayoutManager layoutManagerRecyclerLeft = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        RecyclerViewBrowseLeft.setLayoutManager(layoutManagerRecyclerLeft);
    }

    private void setRetrofit(){
        retrofit = RetrofitBuilder.getRetrofit(Const.BASE_URL);
        apiServer = retrofit.create(APIServer.class);
    }

    private void callApiBrowse(){
        Call<BrowseCategoriesModel.BrowseCategoriesModelParser> callApiBrowseCtegoties = apiServer.GetApiBrowseCategoriesModel();
        callApiBrowseCtegoties.enqueue(new Callback<BrowseCategoriesModel.BrowseCategoriesModelParser>() {
            @Override
            public void onResponse(Call<BrowseCategoriesModel.BrowseCategoriesModelParser> call, Response<BrowseCategoriesModel.BrowseCategoriesModelParser> response) {
                if (response.body().code != 200){
                    Toast.makeText(getActivity(), response.body().message, Toast.LENGTH_SHORT).show();
                }else {
                    dataBrowseCategories = response.body().response;
                    if (dataBrowseCategories != null){
                        adapterRecyclerBrowseCategoriesLeft = new AdapterRecyclerBrowseCategoriesLeft(getActivity(), dataBrowseCategories);
                        RecyclerViewBrowseLeft.setAdapter(adapterRecyclerBrowseCategoriesLeft);
                    }
                }
            }

            @Override
            public void onFailure(Call<BrowseCategoriesModel.BrowseCategoriesModelParser> call, Throwable t) {
                System.out.println("=====================>" + t.toString());
            }
        });
    }
}
