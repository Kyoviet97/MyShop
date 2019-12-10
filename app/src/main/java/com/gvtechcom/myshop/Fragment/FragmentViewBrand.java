package com.gvtechcom.myshop.Fragment;

import android.app.Fragment;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.gvtechcom.myshop.MainActivity;
import com.gvtechcom.myshop.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentViewBrand extends Fragment {
    private View rootView;

    @BindView(R.id.img_top_image)
    ImageView imgTopImage;
    @BindView(R.id.img_icon)
    ImageView imgIcon;
    @BindView(R.id.txt_title)
    TextView txtTitle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_flash_deal, container, false);
        ButterKnife.bind(this, rootView);
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.setDisplayNavigationBar(true, true, false);
        mainActivity.setColorNavigationBar(R.drawable.ic_back_navigation_white, R.drawable.bkg_search_color_brown, "   apple watch", R.color.color_starttus_bar_brown, "#9D9690");
        mainActivity.setColorIconDarkMode(false, R.color.color_starttus_bar_brown);
        imgTopImage.setBackgroundResource(R.drawable.rectangle_view_brand);
        txtTitle.setText("Xiaomi Official Store");
        imgIcon.setImageResource(R.drawable.ic_flash_deals_default);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
