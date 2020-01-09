package com.gvtechcom.myshop.Fragment;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gvtechcom.myshop.Adapter.AdapterShippingMethod;
import com.gvtechcom.myshop.MainActivity;
import com.gvtechcom.myshop.Model.ShippingMethodModel;
import com.gvtechcom.myshop.R;
import com.nostra13.universalimageloader.utils.L;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentShippingMethod extends Fragment {
    private View rootView;

    private MainActivity mainActivity;

    private AdapterShippingMethod adapterShippingMethod;
    private List<ShippingMethodModel> lsShippingMethod;

    @BindView(R.id.txt_weight_pack)
    TextView txtWeightPack;
    @BindView(R.id.txt_size_pack)
    TextView txtSizePack;
    @BindView(R.id.txt_processing_time)
    TextView txtProcessingTime;
    @BindView(R.id.recycler_shipping_method)
    androidx.recyclerview.widget.RecyclerView recyclerShippingMethod;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_shipping_method, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        setRecycler();
        setAdapter();
    }

    private void setRecycler(){
        LinearLayoutManager layoutManagerRecycler = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerShippingMethod.setLayoutManager(layoutManagerRecycler);
    }

    private void setAdapter(){
            lsShippingMethod = new ArrayList<>();
            lsShippingMethod.add(new ShippingMethodModel("Free Shipping", "China Post Ordinary Small Packet Plus", "Estimated Delivery: 34-60 days", "Tracking Unavailable"));
            lsShippingMethod.add(new ShippingMethodModel("Shipping: $16.42", "DHL", "Estimated Delivery: 8-17 days", "Tracking Available"));
            lsShippingMethod.add(new ShippingMethodModel("Free Shipping", "China Post Ordinary Small Packet Plus", "Estimated Delivery: 34-60 days", "Tracking Unavailable"));
            lsShippingMethod.add(new ShippingMethodModel("Shipping: $16.42", "DHL", "Estimated Delivery: 8-17 days", "Tracking Available"));
            lsShippingMethod.add(new ShippingMethodModel("Free Shipping", "China Post Ordinary Small Packet Plus", "Estimated Delivery: 34-60 days", "Tracking Unavailable"));
            lsShippingMethod.add(new ShippingMethodModel("Shipping: $16.42", "DHL", "Estimated Delivery: 8-17 days", "Tracking Available"));

            adapterShippingMethod = new AdapterShippingMethod(lsShippingMethod);
            recyclerShippingMethod.setAdapter(adapterShippingMethod);
            adapterShippingMethod.setOnItemClick(new AdapterShippingMethod.SetOnClickItem() {
                @Override
                public void onClickItem(List<ShippingMethodModel> lsDatass, int position) {
                    System.out.println("================" + position);
                }
            });
        }

    private void init() {
        mainActivity = (MainActivity) getActivity();
        mainActivity.setColorIconDarkMode(false, R.color.white);
        mainActivity.setSubActionBar(false, false, "Shipping Method");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mainActivity.setSubActionBar(true, true, "");
        mainActivity.setDisplayNavigationBar(false, false, false);
        mainActivity.setHideButtonNavigation(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mainActivity.setColorStatusTran(true);
        }
    }
}
