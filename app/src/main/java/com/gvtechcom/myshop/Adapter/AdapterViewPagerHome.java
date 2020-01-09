package com.gvtechcom.myshop.Adapter;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.gvtechcom.myshop.Fragment.FragmentAccountManager;
import com.gvtechcom.myshop.Fragment.FragmentHomeContent;
import com.gvtechcom.myshop.Fragment.FragmentHomeManager;
import com.gvtechcom.myshop.Fragment.FragmentMessages;
import com.gvtechcom.myshop.Fragment.FragmentMessagesManager;
import com.gvtechcom.myshop.Fragment.FragmentOrdersManager;
import com.gvtechcom.myshop.Fragment.FragmentUpdateManager;
import com.gvtechcom.myshop.Interface.SendTagFragment;

public class AdapterViewPagerHome extends FragmentStatePagerAdapter {

    public AdapterViewPagerHome(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment frag = null;
        switch (position) {
            case 0:
                frag = new FragmentHomeManager();
                break;
            case 1:
                frag = new FragmentMessagesManager();
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
