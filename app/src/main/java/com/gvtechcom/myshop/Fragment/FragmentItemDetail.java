package com.gvtechcom.myshop.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.gvtechcom.myshop.MainActivity;
import com.gvtechcom.myshop.R;

public class FragmentItemDetail extends Fragment {
    private View rootView;
    private MainActivity mainActivity;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_item_detail, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        mainActivity = (MainActivity) getActivity();
        mainActivity.setDisplayNavigationBar(false, false, false);
        mainActivity.setHideButtonNavigation(true);
        mainActivity.setColorStatusTran(true);
    }
}
