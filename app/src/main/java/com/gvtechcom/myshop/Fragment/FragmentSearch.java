package com.gvtechcom.myshop.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
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
import com.gvtechcom.myshop.Adapter.AdapterItemsYouLove;
import com.gvtechcom.myshop.Adapter.AdapterKeyWordsSearch;
import com.gvtechcom.myshop.Adapter.AdapterSearchTopHot;
import com.gvtechcom.myshop.Interface.ClickActionSearch;
import com.gvtechcom.myshop.Interface.KeywordSearch;
import com.gvtechcom.myshop.Interface.OnClickRecyclerView;
import com.gvtechcom.myshop.MainActivity;
import com.gvtechcom.myshop.Model.ItemDetailsModel;
import com.gvtechcom.myshop.Model.ItemYouLoveModel;
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

public class FragmentSearch extends androidx.fragment.app.Fragment implements KeywordSearch, ClickActionSearch {
    private View rootView;
    private MainActivity mainActivity;
    private AdapterKeyWordsSearch adapterKeyWordsSearch;
    private AdapterSearchTopHot adapterSearchTopHot;
    private APIServer apiServer;
    private int page;
    private String nameProduct;
    private Boolean isLoadMore = true;
    private Boolean maxData = false;
    private KeywordsModel dataKeyword;


    private void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    private void setPage(int page) {
        this.page = page;
    }

    @BindView(R.id.main_layout_search)
    LinearLayout mainLayoutSearch;

    @BindView(R.id.layout_recycler_searched)
    LinearLayout layoutRecyclerSearched;

    @BindView(R.id.layout_top_keyword)
    LinearLayout layoutTopKeyword;

    @BindView(R.id.recycler_view_search)
    RecyclerView recyclerViewSearch;

    @BindView(R.id.recycler_view_searched)
    RecyclerView recyclerViewSearched;

    @BindView(R.id.recycler_view_search_top_hot)
    RecyclerView recyclerViewSearchTopHot;

    @BindView(R.id.scroll_search_fragment)
    NestedScrollView scrollSearch;

    @BindView(R.id.txt_no_result)
    TextView txtNoResult;

    @BindView(R.id.txt_top_keyword)
    TextView txtTopKeyword;

    @BindView(R.id.txt_keyword_searched)
    TextView txtKeywordSearched;

    @BindView(R.id.progressbar_load_api_footer_search)
    ProgressBar progressbarLoadApiFooterSearch;


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
        mainActivity.setEditSearchNavigation(true);
        mainActivity.setKeywordSearchl(this);
        mainActivity.setClickActionSearch(this);
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
        LinearLayoutManager layoutManagerTopKeyword = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false);
        recyclerViewSearchTopHot.setLayoutManager(layoutManagerTopKeyword);

        LinearLayoutManager layoutManagerSearched = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewSearched.setLayoutManager(layoutManagerSearched);

        LinearLayoutManager layoutManagerSearched1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewSearch.setLayoutManager(layoutManagerSearched1);
    }

    private LinearLayoutManager setLayoutManager(Boolean bl) {
        LinearLayoutManager layoutManager = null;
        layoutManager = bl ? new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false) : new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false);
        return layoutManager;
    }

    private void callApiKeywords() {
        Call<KeywordsModel.KeywordParser> keywordSearchCall = apiServer.GetApiKeywordsSearch();
        keywordSearchCall.enqueue(new Callback<KeywordsModel.KeywordParser>() {
            @Override
            public void onResponse(Call<KeywordsModel.KeywordParser> call, Response<KeywordsModel.KeywordParser> response) {
                if (ValidateCallApi.ValidateAip(getActivity(), response.body().status, response.body().content)) {
                    dataKeyword = response.body().dataKeywordsModel;
                    setAdapterKeyWordsSearch(dataKeyword.lsKeywordsPopular);
                    setAdapterKeywordsSearched(dataKeyword.lsKeywordsHot);
                    setAdapterSearchTopHot(dataKeyword.lsKeywordsHot);
                }
            }

            @Override
            public void onFailure(Call<KeywordsModel.KeywordParser> call, Throwable t) {

            }
        });
    }

    private List<String> filterKeyword(String stKeyword) {
        List<String> newData = new ArrayList<>();
        for (String n : dataKeyword.lsKeywordsPopular)
            if (n.toLowerCase().contains(stKeyword.toLowerCase())) {
                newData.add(n);
            }
        return newData;
    }

    private void setAdapterKeyWordsSearch(List<String> lsKeywordSearch) {
        recyclerViewSearch.setLayoutManager(setLayoutManager(true));
        if (adapterKeyWordsSearch == null) {
            adapterKeyWordsSearch = new AdapterKeyWordsSearch(lsKeywordSearch);
            recyclerViewSearch.setAdapter(adapterKeyWordsSearch);
            adapterKeyWordsSearch.setOnClickRecyclerView(new OnClickRecyclerView() {
                @Override
                public void onClick(String idProduct) {
                    setPage(1);
                    setNameProduct(idProduct);
                    callApiSearch(idProduct, false);
                }
            });
        } else {
            recyclerViewSearch.setAdapter(adapterKeyWordsSearch);
            layoutRecyclerSearched.setVisibility(View.GONE);
            adapterKeyWordsSearch.upDateAdapter(lsKeywordSearch);
        }
    }

    private void setAdapterSearchTopHot(List<String> lsDataHotKeyword) {
        if (adapterSearchTopHot != null) {
            adapterSearchTopHot = new AdapterSearchTopHot(lsDataHotKeyword);
            recyclerViewSearchTopHot.setAdapter(adapterSearchTopHot);
            txtTopKeyword.setVisibility(View.VISIBLE);
            adapterSearchTopHot.setOnClickRecyclerView(new OnClickRecyclerView() {
                @Override
                public void onClick(String idProduct) {
                    setPage(1);
                    setNameProduct(idProduct);
                    callApiSearch(idProduct, false);
                }
            });
        }
    }

    private void setAdapterKeywordsSearched(List<String> lsDataKeywordsSearched) {
        adapterSearchTopHot = new AdapterSearchTopHot(lsDataKeywordsSearched);
        recyclerViewSearched.setAdapter(adapterSearchTopHot);
        txtKeywordSearched.setVisibility(View.VISIBLE);
        adapterSearchTopHot.setOnClickRecyclerView(new OnClickRecyclerView() {
            @Override
            public void onClick(String idProduct) {
                setPage(1);
                setNameProduct(idProduct);
                callApiSearch(idProduct, false);
            }
        });
    }


    private void callApiSearch(String keyWord, Boolean loadMore) {
        Animation animation_side_up = AnimationUtils.loadAnimation(getActivity(), R.anim.animation_slide_up_progressbar);
        Animation animation_side_down = AnimationUtils.loadAnimation(getActivity(), R.anim.animation_slide_down_progressbar);
        if (loadMore) {
            progressbarLoadApiFooterSearch.setVisibility(View.VISIBLE);
            progressbarLoadApiFooterSearch.setAnimation(animation_side_up);
        } else {
            ShowProgressBar.showProgress(getActivity());
        }
        txtNoResult.setVisibility(View.GONE);
        Call<ItemYouLoveModel.ItemYouLoveModelParser> callApi = apiServer.GetDataSearch(page, 8, keyWord);
        callApi.enqueue(new Callback<ItemYouLoveModel.ItemYouLoveModelParser>() {
            @Override
            public void onResponse(Call<ItemYouLoveModel.ItemYouLoveModelParser> call, Response<ItemYouLoveModel.ItemYouLoveModelParser> response) {
                ShowProgressBar.hideProgress();
                layoutTopKeyword.setVisibility(View.GONE);
                layoutRecyclerSearched.setVisibility(View.GONE);
                if (ValidateCallApi.ValidateAip(getActivity(), response.body().code, response.body().message)) {
                    progressbarLoadApiFooterSearch.setAnimation(animation_side_down);
                    progressbarLoadApiFooterSearch.setVisibility(View.GONE);
                    if (response.body().data.products.size() == 0 && !loadMore) {
                        recyclerViewSearch.setVisibility(View.GONE);
                        txtNoResult.setVisibility(View.VISIBLE);
                    } else {
                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        Fragment fragmentViewCategory = new FragmentViewCategory();
                        fragmentTransaction.replace(R.id.frame_layout_home_manager, fragmentViewCategory);
                        fragmentTransaction.addToBackStack("SearchView");
                        fragmentTransaction.commit();
                    }
                    mainActivity.hideSoftKeyboard(getActivity());
                }
            }

            @Override
            public void onFailure(Call<ItemYouLoveModel.ItemYouLoveModelParser> call, Throwable t) {
                ShowProgressBar.hideProgress();
                progressbarLoadApiFooterSearch.setAnimation(animation_side_down);
                progressbarLoadApiFooterSearch.setVisibility(View.GONE);
            }
        });
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
        txtNoResult.setVisibility(View.GONE);
        if (TextUtils.isEmpty(keyWord)) {
            recyclerViewSearch.setVisibility(View.GONE);
            layoutRecyclerSearched.setVisibility(View.VISIBLE);
            layoutTopKeyword.setVisibility(View.VISIBLE);
        } else {
            setAdapterKeyWordsSearch(filterKeyword(keyWord));
            recyclerViewSearch.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void clickActionSearch(String bl) {
        callApiSearch(bl, false);
    }
}
