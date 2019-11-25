package com.gvtechcom.myshop.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gvtechcom.myshop.Adapter.AdapterRecyclerUpdateNotify;
import com.gvtechcom.myshop.MainActivity;
import com.gvtechcom.myshop.Model.UpdateNotifyModel;
import com.gvtechcom.myshop.Network.APIServer;
import com.gvtechcom.myshop.Network.RetrofitBuilder;
import com.gvtechcom.myshop.R;
import com.gvtechcom.myshop.Utils.Const;
import com.mylibrary.ui.progress.ProgressDialogCustom;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FragmentUpdate extends Fragment {
    private View rootView;
    private APIServer apiServer;
    private UpdateNotifyModel.UpdateNotifyModelParser dataUpdateNotify;
    private AdapterRecyclerUpdateNotify adapterRecyclerUpdateNotify;
    private ProgressDialogCustom progressDialogCustom;

    @BindView(R.id.recycler_update_notify)
    RecyclerView recyclerUpdateNotify;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_update_notification, container, false);
        MainActivity mainActivity;
        mainActivity = (MainActivity) getActivity();
        mainActivity.setDisplayNavigationBar(false, false, false);
        mainActivity.setColorIconDarkMode(true, R.color.color_startusBar_white);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, rootView);
        setRetrofit();
        init();
        getDataUpdateNotify();

    }

    private void init() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerUpdateNotify.setLayoutManager(linearLayoutManager);
        progressDialogCustom = new ProgressDialogCustom(getActivity());
    }

    private void setAdapterUpdateNotifi(List<UpdateNotifyModel.DataUpdateNoty> lsUpdateNotifi) {
        adapterRecyclerUpdateNotify = new AdapterRecyclerUpdateNotify(getActivity(), lsUpdateNotifi);
        recyclerUpdateNotify.setAdapter(adapterRecyclerUpdateNotify);
    }


    private void setRetrofit() {
        Retrofit retrofit;
        retrofit = RetrofitBuilder.getRetrofit(Const.BASE_URL);
        apiServer = retrofit.create(APIServer.class);
    }

    private void getDataUpdateNotify() {
        progressDialogCustom.onShow(false, "Loading...");
        Call<UpdateNotifyModel.UpdateNotifyModelParser> updateNotifyModelCall = apiServer.GetDataUpdateNotify();
        updateNotifyModelCall.enqueue(new Callback<UpdateNotifyModel.UpdateNotifyModelParser>() {
            @Override
            public void onResponse(Call<UpdateNotifyModel.UpdateNotifyModelParser> call, Response<UpdateNotifyModel.UpdateNotifyModelParser> response) {
                if (response.body().code != 200) {
                    progressDialogCustom.onHide();
                    Toast.makeText(getActivity(), response.body().message, Toast.LENGTH_SHORT).show();
                } else {
                    dataUpdateNotify = response.body();
                    if (dataUpdateNotify != null) {
                        progressDialogCustom.onHide();
                        setAdapterUpdateNotifi(dataUpdateNotify.response.data);
                    }
                }
            }

            @Override
            public void onFailure(Call<UpdateNotifyModel.UpdateNotifyModelParser> call, Throwable t) {
                progressDialogCustom.onHide();
            }
        });
    }

}
