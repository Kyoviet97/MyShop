package com.gvtechcom.myshop.Fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.gvtechcom.myshop.Adapter.AdapterItemsYouLove;
import com.gvtechcom.myshop.Adapter.AdapterKeyWordsSearch;
import com.gvtechcom.myshop.Interface.KeywordSearch;
import com.gvtechcom.myshop.MainActivity;
import com.gvtechcom.myshop.Model.CountryInfoModel;
import com.gvtechcom.myshop.Model.ItemDetailsModel;
import com.gvtechcom.myshop.Model.KeywordsModel;
import com.gvtechcom.myshop.Network.APIServer;
import com.gvtechcom.myshop.Network.RetrofitBuilder;
import com.gvtechcom.myshop.R;
import com.gvtechcom.myshop.Utils.Const;
import com.gvtechcom.myshop.Utils.ShowProgressBar;
import com.gvtechcom.myshop.Utils.ValidateCallApi;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FragmentSearch extends Fragment implements KeywordSearch {
    private View rootView;
    private MainActivity mainActivity;
    private AdapterItemsYouLove adapterItemsYouLove;
    private AdapterKeyWordsSearch adapterKeyWordsSearch;
    private APIServer apiServer;
    private FragmentManager fragmentManager;
    private int page;
    private Boolean isLoadMore = true;
    private KeywordsModel dataKeyword;

    @BindView(R.id.main_layout_search)
    LinearLayout mainLayoutSearch;

    @BindView(R.id.recycler_view_search)
    RecyclerView recyclerViewSearch;

    @BindView(R.id.scroll_search_fragment)
    NestedScrollView scrollSearch;

    @BindView(R.id.txt_no_result)
    TextView txtNoResult;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_search, container, false);
        mainActivity = (MainActivity) getActivity();
        mainActivity.setDisplayNavigationBar(true, true, false);
        mainActivity.setColorNavigationBar(R.drawable.ic_back_navigation_white, R.drawable.bkg_search_color_white, "apple watch", R.color.color_startus_home, "#FCC39D");
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.page = 1;
        mainActivity.setEditSearchNavigation(true);
        mainActivity.setKeywordSearchl(this);
        onListenKeyboard();
        setRetrofit();
        setRecyclerView();
        callApiKeywords();
    }

    private void setRetrofit() {
        Retrofit retrofit;
        retrofit = RetrofitBuilder.getRetrofit(Const.BASE_URL);
        apiServer = retrofit.create(APIServer.class);
    }

    private void setRecyclerView() {
//        LinearLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerViewSearch.setLayoutManager(layoutManager);
    }

//    private void setDataAdapter(List<ItemYouLoveModel.Product> dataAdapter) {
//        if (adapterItemsYouLove == null) {
//            adapterItemsYouLove = new AdapterItemsYouLove(getActivity(), dataAdapter);
//            recyclerViewSearch.setAdapter(adapterItemsYouLove);
//        } else {
//            adapterItemsYouLove.UpdateAdapter(dataAdapter);
//        }
//        adapterItemsYouLove.setOnItemClickListener(new AdapterItemsYouLove.ItemClickListener() {
//            @Override
//            public void onClickListener(String productId) {
//                callApiDataItemDetail(productId);
//            }
//        });
//    }

    private void callApiKeywords(){
        Call<KeywordsModel.KeywordParser> keywordSearchCall = apiServer.GetApiKeywordsSearch();
        keywordSearchCall.enqueue(new Callback<KeywordsModel.KeywordParser>() {
            @Override
            public void onResponse(Call<KeywordsModel.KeywordParser> call, Response<KeywordsModel.KeywordParser> response) {
               if ( ValidateCallApi.ValidateAip(getActivity(), response.body().status, response.body().content)){
                   dataKeyword = response.body().dataKeywordsModel;
                    adapterKeyWordsSearch = new AdapterKeyWordsSearch(dataKeyword.lsKeywordsPopular);
                    recyclerViewSearch.setAdapter(adapterKeyWordsSearch);
                }
            }
            @Override
            public void onFailure(Call<KeywordsModel.KeywordParser> call, Throwable t) {

            }
        });
    }

    private List<String> filterKeyword(String stKeyword){
        List<String> newData = new ArrayList<>();
        int i = 0;
        for (String n : newData)
            if (n.toLowerCase().contains(stKeyword.toLowerCase())) {
                newData.add(n);
                System.out.println("=======================>" + newData.size());

            }
        return newData;
    }

    private void setAdapterKeyWordsSearch(String key){
        if (adapterKeyWordsSearch != null){
            adapterKeyWordsSearch = new AdapterKeyWordsSearch(filterKeyword(key));
        }else {
            adapterKeyWordsSearch.upDateAdapter(filterKeyword(key));
        }
    }

//    private void callApiSearch(String keyWord) {
//        txtNoResult.setVisibility(View.GONE);
//        ShowProgressBar.showProgress(getActivity());
//        Call<ItemYouLoveModel.ItemYouLoveModelParser> callApi = apiServer.GetDataSearch(page, 8, keyWord);
//        callApi.enqueue(new Callback<ItemYouLoveModel.ItemYouLoveModelParser>() {
//            @Override
//            public void onResponse(Call<ItemYouLoveModel.ItemYouLoveModelParser> call, Response<ItemYouLoveModel.ItemYouLoveModelParser> response) {
//                ShowProgressBar.hideProgress();
//                if (ValidateCallApi.ValidateAip(getActivity(), response.body().code, response.body().message)) {
//                    setDataAdapter(response.body().response.products);
//                    if (response.body().response.products.size() == 0) {
//                        txtNoResult.setVisibility(View.VISIBLE);
//                    }else{
//                        if (response.body().response.products.size() > Const.TOTAL_PRODUCT){
//                            getNestedScrollChange();
//                        }
//                    }
//                    mainActivity.hideSoftKeyboard(getActivity());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ItemYouLoveModel.ItemYouLoveModelParser> call, Throwable t) {
//                ShowProgressBar.hideProgress();
//            }
//        });
//    }

    private void setDataItemDetails(String jsonData) {
        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragmentItemDetails = new FragmentItemDetail();
        Bundle bundle = new Bundle();
        bundle.putString("dataJson", jsonData);
        bundle.putString("fromToFragment", "homeContent");
        ShowProgressBar.hideProgress();
        fragmentItemDetails.setArguments(bundle);
        fragmentTransaction.add(R.id.content_home_frame_layout, fragmentItemDetails);
        fragmentTransaction.addToBackStack("home");
        fragmentTransaction.commit();
    }

    private void callApiDataItemDetail(String idProduct) {
        ShowProgressBar.showProgress(getActivity());
        Call<ItemDetailsModel.ItemDetailsModelParser> callApi = apiServer.GetApiItemDetails(idProduct);
        callApi.enqueue(new Callback<ItemDetailsModel.ItemDetailsModelParser>() {
            @Override
            public void onResponse(Call<ItemDetailsModel.ItemDetailsModelParser> call, Response<ItemDetailsModel.ItemDetailsModelParser> response) {
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
                ShowProgressBar.hideProgress();
            }
        });
    }

    private void getNestedScrollChange() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scrollSearch.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    int nestedScrollHight = (scrollSearch.getChildAt(0).getHeight() - scrollSearch.getHeight());
                    if (isLoadMore == true && scrollY == nestedScrollHight) {
                        isLoadMore = false;
                        System.out.println("================> 11111111111111111111");
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

    private void onListenKeyboard() {
        KeyboardVisibilityEvent.setEventListener(
                getActivity(),
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        if (isOpen) {
                            mainActivity.setHideButtonNavigation(true);
                            mainActivity.setupUI(mainLayoutSearch);
                        } else {
                            Handler handler = new Handler();
                            final Runnable r = new Runnable() {
                                public void run() {
                                    mainActivity.setHideButtonNavigation(false);
                                }
                            };
                            handler.postDelayed(r, 500);
                        }
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mainActivity.setEditSearchNavigation(false);
    }

    @Override
    public void dataKeyWord(String keyWord) {
//        callApiSearch(keyWord);
        System.out.println("=================>" + keyWord);
        filterKeyword(keyWord);
    }
}
