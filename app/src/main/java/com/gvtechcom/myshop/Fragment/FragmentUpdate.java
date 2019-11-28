package com.gvtechcom.myshop.Fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.gvtechcom.myshop.Adapter.AdapterRecyclerUpdateNotify;
import com.gvtechcom.myshop.MainActivity;
import com.gvtechcom.myshop.Model.UpdateNotifyModel;
import com.gvtechcom.myshop.Network.APIServer;
import com.gvtechcom.myshop.Network.RetrofitBuilder;
import com.gvtechcom.myshop.R;
import com.gvtechcom.myshop.Utils.Const;
import com.gvtechcom.myshop.Utils.MySharePreferences;
import com.mylibrary.ui.progress.ProgressDialogCustom;

import java.lang.reflect.Type;
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
    private MySharePreferences mySharePreferences;
    private FragmentManager fragmentManager;

    @BindView(R.id.recycler_update_notify)
    RecyclerView recyclerUpdateNotify;


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
        if (dataUpdateNotify != null){
            setAdapterUpdateNotifi(dataUpdateNotify.response.data);
        }
    }

    private void init() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerUpdateNotify.setLayoutManager(linearLayoutManager);
        progressDialogCustom = new ProgressDialogCustom(getActivity());
        fragmentManager = getFragmentManager();
    }

    private void setAdapterUpdateNotifi(List<UpdateNotifyModel.DataUpdateNoty> lsUpdateNotifi) {
        adapterRecyclerUpdateNotify = new AdapterRecyclerUpdateNotify(getActivity(), lsUpdateNotifi);
        recyclerUpdateNotify.setAdapter(adapterRecyclerUpdateNotify);
        adapterRecyclerUpdateNotify.setOnClickItem(new AdapterRecyclerUpdateNotify.onClickItem() {
            @Override
            public void onClick(String idNotify) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment fragmentNotifyDetail = new FragmentNotifyDetail();
                Bundle bundle = new Bundle();
                bundle.putString("idNotify", idNotify);
                fragmentNotifyDetail.setArguments(bundle);
                fragmentTransaction.replace(R.id.content_home_frame_layout, fragmentNotifyDetail);
                fragmentTransaction.addToBackStack("NotifyAndUpdate");
                fragmentTransaction.commit();
            }
        });
    }


    private void setRetrofit() {
        Retrofit retrofit;
        retrofit = RetrofitBuilder.getRetrofit(Const.BASE_URL);
        apiServer = retrofit.create(APIServer.class);
    }

}
