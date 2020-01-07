package com.gvtechcom.myshop.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.gvtechcom.myshop.Adapter.AdapterRecyclerDataFlashDeals;
import com.gvtechcom.myshop.Adapter.AdapterRecyclerGroupFlashDeals;
import com.gvtechcom.myshop.Interface.OnClickRecyclerView;
import com.gvtechcom.myshop.MainActivity;
import com.gvtechcom.myshop.Model.FlashDealsDetails;
import com.gvtechcom.myshop.Model.ItemDetailsModel;
import com.gvtechcom.myshop.Network.APIServer;
import com.gvtechcom.myshop.Network.RetrofitBuilder;
import com.gvtechcom.myshop.R;
import com.gvtechcom.myshop.Utils.Const;
import com.gvtechcom.myshop.Utils.ValidateCallApi;
import com.mylibrary.ui.progress.ProgressDialogCustom;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FragmentFlashDetails extends androidx.fragment.app.Fragment {
    private View rootView;
    private APIServer apiServer;
    private ProgressDialogCustom progressDialogCustom;
    private AdapterRecyclerGroupFlashDeals adapterRecyclerGroupFlashDeals;
    private AdapterRecyclerDataFlashDeals adapterRecyclerDataFlashDeals;
    private List<FlashDealsDetails.ProductGroups> lsFlashDealsProductGroups;
    private List<FlashDealsDetails.ProductsData> lsFlashDealsProductData;
    private List<FlashDealsDetails.ProductsData> lsFlashDealsProductDataTotal;
    private int page = 1;
    private FragmentManager fragmentManager;
    private MainActivity mainActivity;

    Boolean isLoadMore = true;
    Boolean dataNull = false;
    @BindView(R.id.recyclerView_product_group_name)
    RecyclerView recyclerViewProductGroupName;
    @BindView(R.id.recycler_view_flash_deals)
    RecyclerView recyclerViewFlashDeals;
    @BindView(R.id.scrollView_flash_deals_details)
    NestedScrollView ScrollViewFlashDealsDetaild;
    @BindView(R.id.progressbar_load_api_footer)
    ProgressBar progressbarLoadApiFooter;
    @BindView(R.id.img_top_image)
    ImageView imgTopImage;
    @BindView(R.id.img_icon)
    ImageView imgIcon;
    @BindView(R.id.txt_title)
    TextView txtTitle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_flash_deal, container, false);
        ButterKnife.bind(this, rootView);
        mainActivity = (MainActivity) getActivity();
        mainActivity.setDisplayNavigationBar(true, true, false);
        mainActivity.setColorNavigationBar(R.drawable.ic_back_navigation_white, R.drawable.bkg_search_color_orange, "apple watch", R.color.color_startus_home, "#FCC39D");
        imgTopImage.setBackgroundResource(R.drawable.ic_rectangle_orang);
        imgIcon.setImageResource(R.drawable.ic_flash_deals_default);
        txtTitle.setText("FLASH DEAL!!!");
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRetrofit();
        init();
        setRecyclerView();
        callApiDataFlashDealsDetails("");
    }

    private void init() {
        progressDialogCustom = new ProgressDialogCustom(getActivity());
        lsFlashDealsProductDataTotal = new ArrayList<>();
    }

    private void setRetrofit() {
        Retrofit retrofit;
        retrofit = RetrofitBuilder.getRetrofit(Const.BASE_URL);
        apiServer = retrofit.create(APIServer.class);
    }

    private void setAdapterDataFlashDealsDetails(List<FlashDealsDetails.ProductsData> lsFlashDealsProductData) {
        if (adapterRecyclerDataFlashDeals == null) {
            adapterRecyclerDataFlashDeals = new AdapterRecyclerDataFlashDeals(getActivity(), lsFlashDealsProductData);
            recyclerViewFlashDeals.setAdapter(adapterRecyclerDataFlashDeals);
            setOnClickAdapterData();
        } else {
            adapterRecyclerDataFlashDeals.upDateDataAdapterFlashDeals(lsFlashDealsProductData);
        }
    }

    private void setAdapterRecyclerGroupFlashDeals(List<FlashDealsDetails.ProductGroups> lsFlashDealsProductGroups) {
        if (adapterRecyclerGroupFlashDeals == null) {
            adapterRecyclerGroupFlashDeals = new AdapterRecyclerGroupFlashDeals(getActivity(), lsFlashDealsProductGroups);
            recyclerViewProductGroupName.setAdapter(adapterRecyclerGroupFlashDeals);
            setOnclickAdapter();
        }
    }

    private void setRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewProductGroupName.setLayoutManager(linearLayoutManager);

        LinearLayoutManager linearLayoutManagerRecyclerDataFlashDeals = new GridLayoutManager(getActivity(), 2);
        recyclerViewFlashDeals.setLayoutManager(linearLayoutManagerRecyclerDataFlashDeals);
    }

    private void callApiDataFlashDealsDetails(String idProduct) {
        progressDialogCustom.onShow(false, "");
        getNestedScrollChange(idProduct);
        Animation animation_slide_up = AnimationUtils.loadAnimation(getActivity(), R.anim.animation_slide_up_progressbar);
        Animation animation_slide_Down = AnimationUtils.loadAnimation(getActivity(), R.anim.animation_slide_down_progressbar);
        if (page == 1) {
            progressDialogCustom.onShow(false, "Loading...");
        } else {
            progressbarLoadApiFooter.setAnimation(animation_slide_up);
            progressbarLoadApiFooter.setVisibility(View.VISIBLE);
        }
        Call<FlashDealsDetails.FlashDealsDetailsParser> callApiFlashDealsDetails = apiServer.GetFlashDealsDetails(page, 10, idProduct);
        callApiFlashDealsDetails.enqueue(new Callback<FlashDealsDetails.FlashDealsDetailsParser>() {
            @Override
            public void onResponse(Call<FlashDealsDetails.FlashDealsDetailsParser> call, Response<FlashDealsDetails.FlashDealsDetailsParser> response) {
                progressDialogCustom.onHide();
                if (ValidateCallApi.ValidateAip(getActivity(), response.body().code, response.body().message)) {
                    lsFlashDealsProductData = response.body().response.products;
                    lsFlashDealsProductGroups = response.body().response.product_groups;

                    if (lsFlashDealsProductGroups != null) {
                        progressDialogCustom.onHide();
                        setAdapterRecyclerGroupFlashDeals(lsFlashDealsProductGroups);
                    }

                    if (lsFlashDealsProductData != null) {
                        progressDialogCustom.onHide();
                        progressbarLoadApiFooter.setAnimation(animation_slide_Down);
                        progressbarLoadApiFooter.setVisibility(View.GONE);
                        page = page + 1;
                        for (int i = 0; i <= lsFlashDealsProductData.size(); i++) {
                            if (i != lsFlashDealsProductData.size()) {
                                lsFlashDealsProductDataTotal.add(lsFlashDealsProductData.get(i));
                                if (lsFlashDealsProductDataTotal.size() == response.body().response.total) {
                                    dataNull = true;
                                } else {
                                    dataNull = false;
                                }
                            } else {
                                setAdapterDataFlashDealsDetails(lsFlashDealsProductDataTotal);
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<FlashDealsDetails.FlashDealsDetailsParser> call, Throwable t) {
                progressDialogCustom.onHide();
                progressbarLoadApiFooter.setAnimation(animation_slide_Down);
                progressbarLoadApiFooter.setVisibility(View.GONE);
            }
        });
    }

    private void getNestedScrollChange(String idProduct) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ScrollViewFlashDealsDetaild.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    int NestedScrollHight = (ScrollViewFlashDealsDetaild.getChildAt(0).getHeight() - ScrollViewFlashDealsDetaild.getHeight());
                    if (isLoadMore == true && scrollY == NestedScrollHight && dataNull == false) {
                        isLoadMore = false;
                        callApiDataFlashDealsDetails(idProduct);

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

    private void setOnclickAdapter() {
        adapterRecyclerGroupFlashDeals.setOnItemclickListener(new AdapterRecyclerGroupFlashDeals.OnItemClickListener() {
            @Override
            public void onClickListner(List<FlashDealsDetails.ProductGroups> lsNameGroub, int positon) {
                for (int i = 0; i < lsNameGroub.size(); i++) {
                    FlashDealsDetails.ProductGroups dataNameGroub = lsNameGroub.get(i);
                    if (i == positon) {
                        dataNameGroub.icSelect = true;
                    } else dataNameGroub.icSelect = false;
                    adapterRecyclerGroupFlashDeals.notifyDataSetChanged();
                }

                lsFlashDealsProductDataTotal.clear();
                page = 1;
                setAdapterDataFlashDealsDetails(lsFlashDealsProductData);
                callApiDataFlashDealsDetails(lsNameGroub.get(positon).product_group_id);
            }
        });
    }

    private void setOnClickAdapterData() {
        adapterRecyclerDataFlashDeals.setOnClickItemRecyclerView(new OnClickRecyclerView() {
            @Override
            public void onClick(String idProduct) {
                callApiDataItemDetail(idProduct);
            }
        });
    }

    private void callApiDataItemDetail(String idProduct) {
        progressDialogCustom.onShow(false, "");
        Call<ItemDetailsModel.ItemDetailsModelParser> callApi = apiServer.GetApiItemDetails(idProduct);
        callApi.enqueue(new Callback<ItemDetailsModel.ItemDetailsModelParser>() {
            @Override
            public void onResponse(Call<ItemDetailsModel.ItemDetailsModelParser> call, Response<ItemDetailsModel.ItemDetailsModelParser> response) {
               progressDialogCustom.onHide();
                if (ValidateCallApi.ValidateAip(getActivity(), response.body().code, response.body().message)) {
                    if (response.body().response != null) {
                        Gson gson = new Gson();
                        String jsonData = gson.toJson(response.body());
                        setDataItemDetails(jsonData);
                    }
                }
            }

            @Override
            public void onFailure(Call<ItemDetailsModel.ItemDetailsModelParser> call, Throwable t) {
                progressDialogCustom.onHide();
            }
        });
    }

    private void setDataItemDetails(String jsonData) {
        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragmentItemDetails = new FragmentItemDetail();
        Bundle bundle = new Bundle();
        bundle.putString("dataJson", jsonData);
        bundle.putString("fromToFragment", "flashDeals");
        fragmentItemDetails.setArguments(bundle);
        fragmentTransaction.add(R.id.frame_layout_home_manager, fragmentItemDetails);
        fragmentTransaction.addToBackStack("home");
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
