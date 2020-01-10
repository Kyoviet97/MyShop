package com.gvtechcom.myshop.Fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gvtechcom.myshop.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentOrderDetail extends Fragment {
    private View rootView;

    @BindView(R.id.img_bitmap_demo)
    ImageView imgDemo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_order_detail, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String v = String.valueOf(1.54);
        Toast.makeText(getActivity(), "" + v, Toast.LENGTH_SHORT).show();
    }


}
