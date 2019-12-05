package com.gvtechcom.myshop.Interface;

public class ClassCheckDistroyView {
    private ListtenOnDestroyView listtenOnDestroyView;
    private Boolean onDestroy;

    public void ClassSendDataInterface(ListtenOnDestroyView listtenOnDestroyView, Boolean onDestroy){
        this.listtenOnDestroyView = listtenOnDestroyView;
        this.onDestroy = onDestroy;
        listtenOnDestroyView.isDistroy(onDestroy);
    }
}
