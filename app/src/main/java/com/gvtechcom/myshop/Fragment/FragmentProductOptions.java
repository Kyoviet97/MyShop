package com.gvtechcom.myshop.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.gvtechcom.myshop.Adapter.AdapterProductChidrenBands;
import com.gvtechcom.myshop.Adapter.AdapterProductChildren;
import com.gvtechcom.myshop.MainActivity;
import com.gvtechcom.myshop.Model.ItemDetailsModel;
import com.gvtechcom.myshop.R;
import com.gvtechcom.myshop.Utils.QuantityView;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentProductOptions extends Fragment {
    private View rootView;
    private AdapterProductChildren adapterProductChildrenColor;
    private AdapterProductChidrenBands adapterProductChildrenBand;
    private MainActivity mainActivity;
    private ItemDetailsModel lsDataProduct;

    @BindView(R.id.quantity_view_product_option)
    QuantityView quantityViewProductOption;


    @BindView(R.id.list_color_product)
    RecyclerView listColorProduct;

    @BindView(R.id.list_band_product)
    RecyclerView listBandProduct;

    @BindView(R.id.img_icon_product)
    ImageView imgProductOption;

    @BindView(R.id.txt_name_product)
    TextView txtNameProduct;

    @BindView(R.id.text_not_product)
    TextView textNotProduct;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            Gson gson = new Gson();
            this.lsDataProduct = gson.fromJson(bundle.getString("lsDataProduct"), ItemDetailsModel.class);
        }
    }

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
        setContentProductOption();

    }

    @OnClick({R.id.btn_buy_now})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_buy_now:
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.frame_layout_home_manager, new FragmentShippingMethod());
                fragmentTransaction.addToBackStack("product_option");
                fragmentTransaction.commit();
                break;
        }
    }

    private void init() {
        mainActivity = (MainActivity) getActivity();
        mainActivity.setColorIconDarkMode(false, R.color.white);
        mainActivity.setSubActionBar(false, false, "Product Options");
    }

    private void setContentProductOption() {
        if (lsDataProduct != null) {
            txtNameProduct.setText(lsDataProduct.name);
            setAdapterProductChildrenColor(lsDataProduct.product);
            quantityViewProductOption.setValue(lsDataProduct.sold);
            quantityViewProductOption.setClickLostAddItem(0);
        }
    }

    private void setRecycler() {
        LinearLayoutManager layoutManagerRecyclerColor = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        listColorProduct.setLayoutManager(layoutManagerRecyclerColor);

        LinearLayoutManager layoutManagerRecyclerBand = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        listBandProduct.setLayoutManager(layoutManagerRecyclerBand);
    }

    private void setAdapterProductChildrenColor(List<ItemDetailsModel.Product> lsDataColor) {
        adapterProductChildrenColor = new AdapterProductChildren(getActivity(), lsDataColor);
        listColorProduct.setAdapter(adapterProductChildrenColor);
        adapterProductChildrenColor.getDataListChildren(new AdapterProductChildren.SendListChildren() {
            @Override
            public void dataSend(int position) {
                if (position > 0) {
                    textNotProduct.setVisibility(View.GONE);
                    listBandProduct.setVisibility(View.VISIBLE);
                    setLsDataProductChildrenBands(lsDataColor.get(position).children);
                } else {
                    textNotProduct.setVisibility(View.VISIBLE);
                    listBandProduct.setVisibility(View.GONE);
                }
            }
        });

    }

    private void setLsDataProductChildrenBands(List<ItemDetailsModel.Children> lsDataBands) {
        if (adapterProductChildrenBand == null) {
            adapterProductChildrenBand = new AdapterProductChidrenBands(getActivity(), lsDataBands);
            listBandProduct.setAdapter(adapterProductChildrenBand);
        } else {
            adapterProductChildrenBand.updateAdapter(lsDataBands);
        }
    }


    private void setGlideImage(String url, View view) {
        Glide.with(getActivity())
                .load(url)
                .placeholder(R.drawable.ic_icon_load_error_cetegory)
                .error(R.drawable.ic_icon_load_error_cetegory)
                .into((ImageView) view);
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
