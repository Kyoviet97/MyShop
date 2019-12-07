package com.gvtechcom.myshop.Fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.gvtechcom.myshop.Adapter.AdapterItemSubCategory;
import com.gvtechcom.myshop.Adapter.AdapterViewCategory;
import com.gvtechcom.myshop.Interface.ListtenOnDestroyView;
import com.gvtechcom.myshop.MainActivity;
import com.gvtechcom.myshop.Model.BaseGetApiAddress;
import com.gvtechcom.myshop.Model.BaseGetApiData;
import com.gvtechcom.myshop.Model.GoToItemDetail;
import com.gvtechcom.myshop.Model.ItemDetailsModel;
import com.gvtechcom.myshop.Model.JustForYou;
import com.gvtechcom.myshop.Model.Product;
import com.gvtechcom.myshop.Model.ProductByCategoryModel;
import com.gvtechcom.myshop.R;
import com.gvtechcom.myshop.dialog.ToastDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentViewCategory extends Fragment{
    private View rootiew;
    private AdapterViewCategory adapterViewCategory;
    private MainActivity mainActivity;

    private GoToItemDetail goToItemDetail = new GoToItemDetail();

    @BindView(R.id.recycler_view_category_top)
    RecyclerView recyclerViewViewCategoryTop;
    @BindView(R.id.recycler_view_category_main)
    RecyclerView recyclerViewViewCategoryMain;
    private FragmentManager fragmentManager;
    private FragmentItemDetail fragmentItemDetail;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootiew = inflater.inflate(R.layout.fragment_view_category, container, false);
        ButterKnife.bind(this, rootiew);
        return rootiew;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRecyclerView();
        Bundle bundle = getArguments();
        if (bundle != null) {
            Gson gson = new Gson();
            String stringJsonData = bundle.getString("jsonDataViewCategory");
            ProductByCategoryModel dataViewCategory = gson.fromJson(stringJsonData, ProductByCategoryModel.class);
            adapterViewCategory = new AdapterViewCategory(getActivity(), dataViewCategory);
            setOnClickAdapter();
            recyclerViewViewCategoryMain.setAdapter(adapterViewCategory);
        }

    }

    private void setRecyclerView() {
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerViewViewCategoryMain.setLayoutManager(linearLayoutManager);
    }

    private void setOnClickAdapter(){
        adapterViewCategory.setOnClickListener(new AdapterViewCategory.SetOnClickListener() {
            @Override
            public void setOnClickListener(String idCategory) {
                fragmentManager = getFragmentManager();
                fragmentItemDetail = new FragmentItemDetail();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putBoolean("fromViewCategory", true);
                fragmentItemDetail.setArguments(bundle);
                fragmentTransaction.add(R.id.content_home_frame_layout, fragmentItemDetail);
                fragmentTransaction.addToBackStack("home");
                fragmentTransaction.commit();
            }
        });
    }
}
