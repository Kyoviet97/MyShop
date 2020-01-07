package com.gvtechcom.myshop.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.gvtechcom.myshop.Fragment.FragmentContentMessages;

public class ViewPagerAdapterTabMessages extends FragmentStatePagerAdapter {
     public ViewPagerAdapterTabMessages(FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment frag = null;
        switch (position){
            case 0:
                frag = new FragmentContentMessages();
                break;
            case 1:
                frag = new FragmentContentMessages();
                break;
            case 2:
                frag = new FragmentContentMessages();
                break;
        }
        return frag;
    }

    @Override
    public int getCount() {
        return 3;
    }

}
