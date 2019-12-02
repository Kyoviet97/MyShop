package com.gvtechcom.myshop.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gvtechcom.myshop.Adapter.AdapterItemSubCategory;
import com.gvtechcom.myshop.Adapter.AdapterViewCategory;
import com.gvtechcom.myshop.Model.BaseGetApiAddress;
import com.gvtechcom.myshop.Model.BaseGetApiData;
import com.gvtechcom.myshop.Model.Response;
import com.gvtechcom.myshop.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentViewCategory extends Fragment {
    private View rootiew;

    private AdapterViewCategory adapterViewCategory;

    private Response lsDataResponse;

    @BindView(R.id.recycler_view_category_top)
    RecyclerView recyclerViewViewCategoryTop;
    @BindView(R.id.recycler_view_category_main)
    RecyclerView recyclerViewViewCategoryMain;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

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
        adapterViewCategory = new AdapterViewCategory(getActivity(), lsDataResponse.getJustForYou());
        recyclerViewViewCategoryMain.setAdapter(adapterViewCategory);

    }

    private void setRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerViewViewCategoryMain.setLayoutManager(layoutManager);
    }

}
