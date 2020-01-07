package com.gvtechcom.myshop.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.gvtechcom.myshop.Fragment.FragmentAccountManager;
import com.gvtechcom.myshop.Fragment.FragmentHomeContent;
import com.gvtechcom.myshop.Fragment.FragmentMessages;
import com.gvtechcom.myshop.Fragment.FragmentOrdersManager;
import com.gvtechcom.myshop.Fragment.FragmentUpdateManager;

public class AdapterViewPagerHome extends FragmentStatePagerAdapter {

    public AdapterViewPagerHome(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        System.out.println("=====================>" + position);
        Fragment frag = null;
        switch (position){
            case 0:
                frag = new FragmentHomeContent();
                break;
            case 1:
                frag = new FragmentMessages();
                break;
            case 2:
                frag = new FragmentOrdersManager();
                break;
            case 3:
                frag = new FragmentUpdateManager();
                break;
            case 4:
                frag = new FragmentAccountManager();
                break;
        }
        return frag;
    }

    @Override
    public int getCount() {
        return 5;
    }

}
