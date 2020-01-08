package com.gvtechcom.myshop.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.gvtechcom.myshop.Adapter.AdapterRecyclerUpdateNotify;
import com.gvtechcom.myshop.Interface.HideBackIcon;
import com.gvtechcom.myshop.MainActivity;
import com.gvtechcom.myshop.Model.UpdateNotifyModel;
import com.gvtechcom.myshop.Network.APIServer;
import com.gvtechcom.myshop.Network.RetrofitBuilder;
import com.gvtechcom.myshop.R;
import com.gvtechcom.myshop.Utils.Const;
import com.gvtechcom.myshop.Utils.MySharePreferences;
import com.gvtechcom.myshop.dialog.ToastDialog;
import com.mylibrary.ui.progress.ProgressDialogCustom;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FragmentUpdate extends Fragment implements HideBackIcon {
    private View rootView;
    private APIServer apiServer;
    private UpdateNotifyModel.UpdateNotifyModelParser dataUpdateNotify;
    private AdapterRecyclerUpdateNotify adapterRecyclerUpdateNotify;
    private ProgressDialogCustom progressDialogCustom;
    private MySharePreferences mySharePreferences;
    private FragmentManager fragmentManager;
    private ToastDialog toastDialog;

    @BindView(R.id.recycler_update_notify)
    RecyclerView recyclerUpdateNotify;
    @BindView(R.id.swipe_refresh_layout_notify_update)
    SwipeRefreshLayout swipeRefreshLayoutNotifyUpdate;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mySharePreferences = new MySharePreferences();
        String data = mySharePreferences.GetSharePrefStringObject(getActivity(), "MyOjectNotify");
        Gson gson = new Gson();
        dataUpdateNotify = gson.fromJson(data, UpdateNotifyModel.UpdateNotifyModelParser.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_update_notification, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRetrofit();
        init();
        if (dataUpdateNotify != null) {
            setAdapterUpdateNotifi(dataUpdateNotify.response.data);
        }
        swipeRefreshLayoutNotifyUpdate.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataUpdateNotify();
            }
        });
    }


    private void init() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerUpdateNotify.setLayoutManager(linearLayoutManager);
        progressDialogCustom = new ProgressDialogCustom(getActivity());
        fragmentManager = getFragmentManager();
        toastDialog = new ToastDialog(getActivity());
    }

    private void setAdapterUpdateNotifi(List<UpdateNotifyModel.DataUpdateNoty> lsUpdateNotifi) {
        adapterRecyclerUpdateNotify = new AdapterRecyclerUpdateNotify(getActivity(), lsUpdateNotifi);
        recyclerUpdateNotify.setAdapter(adapterRecyclerUpdateNotify);
        swipeRefreshLayoutNotifyUpdate.setRefreshing(false);
        adapterRecyclerUpdateNotify.setOnClickItem(new AdapterRecyclerUpdateNotify.onClickItem() {
            @Override
            public void onClick(String idNotify) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                FragmentNotifyDetail fragmentNotifyDetail = new FragmentNotifyDetail();
                Bundle bundle = new Bundle();
                bundle.putString("idNotify", idNotify);
                fragmentNotifyDetail.setArguments(bundle);
                fragmentTransaction.replace(R.id.frame_update_manager, fragmentNotifyDetail);
                fragmentTransaction.addToBackStack("NotifyAndUpdate");
                fragmentTransaction.commit();
            }
        });
    }

    private void getDataUpdateNotify() {
        Call<UpdateNotifyModel.UpdateNotifyModelParser> updateNotifyModelCall = apiServer.GetDataUpdateNotify();
        updateNotifyModelCall.enqueue(new Callback<UpdateNotifyModel.UpdateNotifyModelParser>() {
            @Override
            public void onResponse(Call<UpdateNotifyModel.UpdateNotifyModelParser> call, Response<UpdateNotifyModel.UpdateNotifyModelParser> response) {
                if (response.body().code != 200) {
                    toastDialog.onShow(response.body().message);
                } else {
                    dataUpdateNotify = response.body();
                    if (dataUpdateNotify != null) {
                        setAdapterUpdateNotifi(dataUpdateNotify.response.data);
                    }
                }
            }

            @Override
            public void onFailure(Call<UpdateNotifyModel.UpdateNotifyModelParser> call, Throwable t) {
            }
        });
    }

    private void setRetrofit() {
        Retrofit retrofit;
        retrofit = RetrofitBuilder.getRetrofit(Const.BASE_URL);
        apiServer = retrofit.create(APIServer.class);
    }

    @Override
    public void setHideButtonIcon(Boolean hideButtonIcon) {
    }
}
