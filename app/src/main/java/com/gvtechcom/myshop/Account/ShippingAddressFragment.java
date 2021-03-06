package com.gvtechcom.myshop.Account;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.gvtechcom.myshop.Adapter.AdapterRecyclerViewShipping;
import com.gvtechcom.myshop.MainActivity;
import com.gvtechcom.myshop.Model.BaseGetAPIShippingAddress;
import com.gvtechcom.myshop.Network.APIServer;
import com.gvtechcom.myshop.Network.RetrofitBuilder;
import com.gvtechcom.myshop.R;
import com.gvtechcom.myshop.Utils.Const;
import com.gvtechcom.myshop.Utils.GetMD5;
import com.gvtechcom.myshop.Utils.GetTime;
import com.gvtechcom.myshop.Utils.MySharePreferences;
import com.gvtechcom.myshop.Utils.ValidateCallApi;
import com.gvtechcom.myshop.dialog.ToastDialog;
import com.mylibrary.ui.progress.ProgressDialogCustom;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ShippingAddressFragment extends androidx.fragment.app.Fragment {
    private View rootView;
    private APIServer apiServer;
    private ProgressDialogCustom progressDialogCustom;
    private RecyclerView recyclerView;
    private AdapterRecyclerViewShipping adapterRecyclerViewShipping;
    private androidx.fragment.app.FragmentManager fragmentManager;
    private Fragment fragment;
    private MainActivity mainActivity;
    private List<BaseGetAPIShippingAddress.Data> dataAllAddressList;
    @BindView(R.id.swipe_refresh_layout_shipping_address)
    SwipeRefreshLayout swipeRefreshLayoutShippingAddress;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_shipping_address, container, false);
        ButterKnife.bind(this, rootView);
        mainActivity = (MainActivity) getActivity();
        mainActivity.setDisplayNavigationBar(false, false, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        init();
        getApiAddress();
        swipeRefreshLayoutShippingAddress.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getApiAddress();
            }
        });
    }

    @OnClick({R.id.btn_add_an_address})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_an_address:
                addShippingAddess();
                break;
        }
    }

    private void init() {
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.setColorIconDarkMode(true, R.color.color_startusBar_white);
        Retrofit retrofit;
        retrofit = RetrofitBuilder.getRetrofit(Const.BASE_URL);
        apiServer = retrofit.create(APIServer.class);
        progressDialogCustom = new ProgressDialogCustom(getActivity());
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView_shipping_address);
        setViewRecycler();
        androidx.fragment.app.Fragment fragment = new ShippingAddressFragment();
    }

    private void addShippingAddess() {
        fragmentManager = getFragmentManager();
        androidx.fragment.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_account_manager, new AddShippingAddessFragment());
        fragmentTransaction.addToBackStack("ShippingAddress");
        fragmentTransaction.commit();
    }

    private void getApiAddress() {
        swipeRefreshLayoutShippingAddress.setRefreshing(false);
        progressDialogCustom.onShow(false, "Loading...");
        MySharePreferences preferences = new MySharePreferences();
        String AccessToken = preferences.GetSharePref(getActivity(), "access_token");
        String Token = preferences.GetSharePref(getActivity(), "token");
        String Token_type = preferences.GetSharePref(getActivity(), "token_type");
        String Authorization = Token_type + " " + Token;

        GetMD5 getMD5 = new GetMD5();
        GetTime getTime = new GetTime();
        String timeSign = String.valueOf((getTime.getCalendar() + 30000));
        String time = String.valueOf(getTime.getCalendar());

        Call<BaseGetAPIShippingAddress.BaseGetAPIShippingAddressParser> call = apiServer.GetFullAddressShipping("application/json", Authorization, time, getMD5.md5_2(AccessToken, timeSign), "Android");
        call.enqueue(new Callback<BaseGetAPIShippingAddress.BaseGetAPIShippingAddressParser>() {
            @Override
            public void onResponse(Call<BaseGetAPIShippingAddress.BaseGetAPIShippingAddressParser> call, Response<BaseGetAPIShippingAddress.BaseGetAPIShippingAddressParser> response) {
                if (response.code() == 401) {
                    progressDialogCustom.onHide();
                    ToastDialog toastDialog = new ToastDialog(getActivity());
                    toastDialog.onShow("Het phien dang nhap");
                } else {
                    if (ValidateCallApi.ValidateAip(getActivity(), response.body().code, response.body().message)) {
                        progressDialogCustom.onHide();
                        if (response.body().response.data == null) {
                        } else {
                            dataAllAddressList = new ArrayList<>();
                            dataAllAddressList = response.body().response.data;
                            Collections.sort(dataAllAddressList);
                            adapterRecyclerViewShipping = new AdapterRecyclerViewShipping(dataAllAddressList, getActivity());
                            clickAdapter();
                            recyclerView.setAdapter(adapterRecyclerViewShipping);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseGetAPIShippingAddress.BaseGetAPIShippingAddressParser> call, Throwable t) {

            }
        });
    }

    private void clickAdapter() {
        adapterRecyclerViewShipping.setOnItemClickedListener(new AdapterRecyclerViewShipping.OnItemClickedListener() {
            @Override
            public void onItemClick(int position) {
                androidx.fragment.app.Fragment fragmentAddShipping = new AddShippingAddessFragment();
                FragmentManager fragmentManager = getFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putString("idAddress", dataAllAddressList.get(position).id);
                bundle.putString("idCountry", dataAllAddressList.get(position).country_id);
                bundle.putString("idCity", dataAllAddressList.get(position).city_id);
                bundle.putString("idDistrict", dataAllAddressList.get(position).district_id);
                bundle.putString("idWard", dataAllAddressList.get(position).ward_id);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentAddShipping.setArguments(bundle);
                fragmentTransaction.replace(R.id.frame_account_manager, fragmentAddShipping);
                fragmentTransaction.addToBackStack("ShippingAddress");
                fragmentTransaction.commit();
            }
        });
    }

    private void setViewRecycler() {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

}


