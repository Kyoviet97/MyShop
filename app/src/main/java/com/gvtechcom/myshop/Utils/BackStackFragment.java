package com.gvtechcom.myshop.Utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

public class BackStackFragment extends FragmentTransaction {
    @Override
    public int commit() {
        return 0;
    }

    @Override
    public int commitAllowingStateLoss() {
        return 0;
    }

    @Override
    public void commitNow() {

    }

    @Override
    public void commitNowAllowingStateLoss() {

    }

    @NonNull
    @Override
    public FragmentTransaction addToBackStack(@Nullable String name) {
        return super.addToBackStack(name);
    }
}
