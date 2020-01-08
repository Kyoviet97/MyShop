package com.gvtechcom.myshop.Fragment;

import android.app.MediaRouteButton;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.gvtechcom.myshop.Interface.HideBackIcon;
import com.gvtechcom.myshop.MainActivity;
import com.gvtechcom.myshop.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentUpdateManager extends Fragment{
    private View rootView;
    private FragmentManager fragmentManager;
    private MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fargment_update_manager, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        fragmentManager = getFragmentManager();
        setFragmentUpdate();
    }

    private void setFragmentUpdate(){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FragmentUpdate fragmentUpdate = new FragmentUpdate();
        fragmentTransaction.replace(R.id.frame_update_manager, fragmentUpdate);
        fragmentTransaction.commit();
    }

}
