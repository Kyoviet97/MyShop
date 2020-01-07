package com.gvtechcom.myshop.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.gvtechcom.myshop.Fragment.FragmentAccount;
import com.gvtechcom.myshop.Fragment.FragmentContentMessages;
import com.gvtechcom.myshop.Fragment.FragmentHomeContent;
import com.gvtechcom.myshop.Fragment.FragmentHomeManager;
import com.gvtechcom.myshop.Fragment.FragmentMessages;
import com.gvtechcom.myshop.Fragment.FragmentMessagesManager;
import com.gvtechcom.myshop.Fragment.FragmentOrders;
import com.gvtechcom.myshop.Fragment.FragmentUpdate;

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
                frag = new FragmentHomeManager();
                break;
            case 1:
                frag = new FragmentMessagesManager();
                break;
            case 2:
                frag = new FragmentOrders();
                break;
            case 3:
                frag = new FragmentUpdate();
                break;
            case 4:
                frag = new FragmentAccount();
                break;
        }
        return frag;
    }

    @Override
    public int getCount() {
        return 5;
    }

}
