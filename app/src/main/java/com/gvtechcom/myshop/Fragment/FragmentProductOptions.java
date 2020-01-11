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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.gvtechcom.myshop.Adapter.AdapterProductChildren;
import com.gvtechcom.myshop.MainActivity;
import com.gvtechcom.myshop.Model.ItemDetailsModel;
import com.gvtechcom.myshop.R;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentProductOptions extends Fragment {
    private View rootView;
    private AdapterProductChildren adapterProductChildrenColor;
    private MainActivity mainActivity;
    private ItemDetailsModel lsDataProduct;


    @BindView(R.id.list_color_product)
    RecyclerView listColorProduct;

    @BindView(R.id.list_band_product)
    RecyclerView listBandProduct;

    @BindView(R.id.img_icon_product)
    ImageView imgProductOption;

    @BindView(R.id.txt_name_product)
    TextView txtNameProduct;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
       if (bundle != null){
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

    private void init() {
        mainActivity = (MainActivity) getActivity();
        mainActivity.setColorIconDarkMode(false, R.color.white);
        mainActivity.setSubActionBar(false, false, "Options");
    }

    private void setContentProductOption(){
        if (lsDataProduct != null){
            setGlideImage(lsDataProduct.review.images.get(0), imgProductOption);
            txtNameProduct.setText(lsDataProduct.name);
            setAdapterProductChildrenColor(lsDataProduct.product);
        }
    }

    private void setRecycler(){
        LinearLayoutManager layoutManagerRecyclerColor = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        listColorProduct.setLayoutManager(layoutManagerRecyclerColor);

        LinearLayoutManager layoutManagerRecyclerBand = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        listBandProduct.setLayoutManager(layoutManagerRecyclerBand);
    }

    private void setAdapterProductChildrenColor(List<ItemDetailsModel.Product> lsDataColor){
        adapterProductChildrenColor = new AdapterProductChildren(getActivity(), lsDataColor);
        listColorProduct.setAdapter(adapterProductChildrenColor);
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
