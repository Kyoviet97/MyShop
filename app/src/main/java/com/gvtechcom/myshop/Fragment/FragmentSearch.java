package com.gvtechcom.myshop.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.gvtechcom.myshop.Adapter.AdapterItemsYouLove;
import com.gvtechcom.myshop.Interface.KeywordSearch;
import com.gvtechcom.myshop.MainActivity;
import com.gvtechcom.myshop.Model.ItemDetailsModel;
import com.gvtechcom.myshop.Model.ItemYouLoveModel;
import com.gvtechcom.myshop.Network.APIServer;
import com.gvtechcom.myshop.Network.RetrofitBuilder;
import com.gvtechcom.myshop.R;
import com.gvtechcom.myshop.Utils.Const;
import com.gvtechcom.myshop.Utils.ValidateCallApi;
import com.mylibrary.ui.progress.ProgressDialogCustom;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class   FragmentSearch extends Fragment implements KeywordSearch {
    private View rootView;
    private MainActivity mainActivity;
    private AdapterItemsYouLove adapterItemsYouLove;
    private APIServer apiServer;
    private FragmentManager fragmentManager;
    private ProgressDialogCustom progressDialogCustom;

    @BindView(R.id.main_layout_search)
    LinearLayout mainLayoutSearch;

    @BindView(R.id.recycler_view_search)
    RecyclerView recyclerViewSearch;

    @BindView(R.id.scroll_search_fragment)
    NestedScrollView scrollSearch;

    @BindView(R.id.txt_no_result)
    TextView txtNoResult;

    @BindView(R.id.progressbar_no_result)
    ProgressBar progressbarNoResult;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_search, container, false);
        mainActivity = (MainActivity) getActivity();
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainActivity.setEditSearchNavigation(true);
        mainActivity.setKeywordSearchl(this);
        onListenKeyboard();
        progressDialogCustom = new ProgressDialogCustom(getActivity());
        setRetrofit();
        setRecyclerView();
    }

    private void setRetrofit() {
        Retrofit retrofit;
        retrofit = RetrofitBuilder.getRetrofit(Const.BASE_URL);
        apiServer = retrofit.create(APIServer.class);
    }

    private void setRecyclerView() {
        LinearLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerViewSearch.setLayoutManager(layoutManager);
    }

    private void setDataAdapter(List<ItemYouLoveModel.Product> dataAdapter) {
        if (adapterItemsYouLove == null) {
            adapterItemsYouLove = new AdapterItemsYouLove(getActivity(), dataAdapter);
            recyclerViewSearch.setAdapter(adapterItemsYouLove);
        } else {
            adapterItemsYouLove.UpdateAdapter(dataAdapter);
        }
        adapterItemsYouLove.setOnItemClickListener(new AdapterItemsYouLove.ItemClickListener() {
            @Override
            public void onClickListener(String productId) {
                callApiDataItemDetail(productId);
            }
        });
    }

    private void callApiSearch(String keyWord) {
        txtNoResult.setVisibility(View.GONE);
        progressDialogCustom.onShow(false, "");
        Call<ItemYouLoveModel.ItemYouLoveModelParser> callApi = apiServer.GetDataSearch(1, 10, keyWord);
        callApi.enqueue(new Callback<ItemYouLoveModel.ItemYouLoveModelParser>() {
            @Override
            public void onResponse(Call<ItemYouLoveModel.ItemYouLoveModelParser> call, Response<ItemYouLoveModel.ItemYouLoveModelParser> response) {
                if (ValidateCallApi.ValidateAip(getActivity(), response.body().code, response.body().message)) {
                    setDataAdapter(response.body().response.products);
                    progressDialogCustom.onHide();
                    if (response.body().response.products.size() == 0){
                        txtNoResult.setVisibility(View.VISIBLE);
                    }
                    mainActivity.hideSoftKeyboard(getActivity());
                }
            }

            @Override
            public void onFailure(Call<ItemYouLoveModel.ItemYouLoveModelParser> call, Throwable t) {
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
        bundle.putString("fromToFragment", "homeContent");
        progressDialogCustom.onHide();
        fragmentItemDetails.setArguments(bundle);
        fragmentTransaction.add(R.id.content_home_frame_layout, fragmentItemDetails);
        fragmentTransaction.addToBackStack("home");
        fragmentTransaction.commit();
    }

    private void callApiDataItemDetail(String idProduct) {
        progressDialogCustom.onShow(false, "");
        Call<ItemDetailsModel.ItemDetailsModelParser> callApi = apiServer.GetApiItemDetails(idProduct);
        callApi.enqueue(new Callback<ItemDetailsModel.ItemDetailsModelParser>() {
            @Override
            public void onResponse(Call<ItemDetailsModel.ItemDetailsModelParser> call, Response<ItemDetailsModel.ItemDetailsModelParser> response) {
                if (ValidateCallApi.ValidateAip(getActivity(), response.body().code, response.body().message)) {
                    progressDialogCustom.onHide();
                    if (response.body().response != null) {
                        Gson gson = new Gson();
                        String jsonData = gson.toJson(response.body());
                        setDataItemDetails(jsonData);
                    }
                } else {
                    progressDialogCustom.onHide();
                }
            }

            @Override
            public void onFailure(Call<ItemDetailsModel.ItemDetailsModelParser> call, Throwable t) {
                progressDialogCustom.onHide();
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
        callApiSearch(keyWord);
    }
}
