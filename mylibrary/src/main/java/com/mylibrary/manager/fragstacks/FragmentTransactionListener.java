package com.mylibrary.manager.fragstacks;

import androidx.fragment.app.Fragment;

public interface FragmentTransactionListener {
    void onCommit(Fragment f);
}