package com.gvtechcom.myshop.Account;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.mylibrary.ui.progress.ProgressDialogCustom;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ShippingAddressFragment extends Fragment {
    private View rootView;
    private APIServer apiServer;
    private ProgressDialogCustom progressDialogCustom;
    private RecyclerView recyclerView;
    private AdapterRecyclerViewShipping adapterRecyclerViewShipping;
    private FragmentManager fragmentManager;
    private Fragment fragment;
    private MainActivity mainActivity;
    private List<BaseGetAPIShippingAddress.Data> dataFullAddress;


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
    }

    @OnClick({R.id.btn_add_an_address, R.id.img_back_shipping_address})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_an_address:
                addShippingAddess();
                break;
            case R.id.img_back_shipping_address:
                fragmentManager = getFragmentManager();
                fragmentManager.popBackStack();
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

        fragment = new ShippingAddressFragment();
    }

    private void addShippingAddess() {
        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_home_frame_layout, new AddShippingAddessFragment());
        fragmentTransaction.addToBackStack("ShippingAddress");
        fragmentTransaction.commit();
    }

    private void getApiAddress() {
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
                System.out.println("========>" + response.toString());
                if (response.code() == 401) {
                    progressDialogCustom.onHide();
                    Toast.makeText(getActivity(), "Hết phiên đăng nhập!", Toast.LENGTH_SHORT).show();
                } else {
                    if (response.body().code != 200) {
                        progressDialogCustom.onHide();
                        Toast.makeText(getActivity(), response.body().message, Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialogCustom.onHide();

                        if (response.body().response.data == null) {
                            System.out.println("========================NULL");
                        } else {
                            List<BaseGetAPIShippingAddress.Data> dataAllAddressList = new ArrayList<>();
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
            public void onItemClick(String idAddress) {
                Fragment fragmentAddShipping = new AddShippingAddessFragment();
                FragmentManager fragmentManager = getFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putString("idAddress", idAddress);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentAddShipping.setArguments(bundle);
                fragmentTransaction.replace(R.id.content_home_frame_layout, fragmentAddShipping);
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


