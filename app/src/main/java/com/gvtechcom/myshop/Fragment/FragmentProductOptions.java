package com.gvtechcom.myshop.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gvtechcom.myshop.Adapter.AdapterProductChildren;
import com.gvtechcom.myshop.MainActivity;
import com.gvtechcom.myshop.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentProductOptions extends Fragment {
    private View rootView;
    private AdapterProductChildren adapterProductColor;
    private MainActivity mainActivity;

    @BindView(R.id.list_color_product)
    RecyclerView listColorProduct;

    @BindView(R.id.list_band_product)
    RecyclerView listBandProduct;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_product_options, container, false);
        ButterKnife.bind(this, rootView);
        init();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRecycler();
    }

    private void init() {
        mainActivity = (MainActivity) getActivity();
        mainActivity.setColorIconDarkMode(false, R.color.white);
        mainActivity.setSubActionBar(false, false, "Shipping Method");
    }

    private void setRecycler(){
        LinearLayoutManager layoutManagerRecyclerColor = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        listColorProduct.setLayoutManager(layoutManagerRecyclerColor);

        LinearLayoutManager layoutManagerRecyclerBand = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        listBandProduct.setLayoutManager(layoutManagerRecyclerBand);
    }

    private void setAdapterProductColor(){

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
