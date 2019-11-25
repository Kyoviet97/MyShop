package com.mylibrary.manager.fragstacks;

public interface StackableFragment {
    public String getFragmentStackName();

    public void onFragmentScroll();
}