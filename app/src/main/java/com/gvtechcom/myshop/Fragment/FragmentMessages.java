package com.gvtechcom.myshop.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.gvtechcom.myshop.Adapter.ViewPagerAdapterTabMessages;
import com.gvtechcom.myshop.MainActivity;
import com.gvtechcom.myshop.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentMessages extends Fragment {
    private View rootView;
    @BindView(R.id.tab_layout_messages)
    TabLayout tabLayoutMessages;
    @BindView(R.id.view_pager_messages)
    ViewPager viewPagerMessages;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_messages, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void addControl() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        ViewPagerAdapterTabMessages adapter = new ViewPagerAdapterTabMessages(fragmentManager);
        viewPagerMessages.setAdapter(adapter);
        tabLayoutMessages.setupWithViewPager(viewPagerMessages);
        viewPagerMessages.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayoutMessages));
        tabLayoutMessages.setTabsFromPagerAdapter(adapter);//deprecated
        tabLayoutMessages.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPagerMessages));

        tabLayoutMessages.getTabAt(0).setIcon(R.drawable.bkg_box_orders_messages);
        tabLayoutMessages.getTabAt(1).setIcon(R.drawable.bkg_store_messages);
        tabLayoutMessages.getTabAt(2).setIcon(R.drawable.bkg_user_headset_messages);

    }

}