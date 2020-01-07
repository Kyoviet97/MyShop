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
import com.gvtechcom.myshop.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentUpdateManager extends Fragment{
    private View rootView;
    private FragmentManager fragmentManager;
    @BindView(R.id.img_back_update_fragment)
    public ImageView imgBackUpdate;

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
        imgBackUpdate.setVisibility(View.GONE);
        fragmentManager = getFragmentManager();
        setFragmentUpdate();
        imgBackUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.popBackStack();
            }
        });
    }

    private void setFragmentUpdate(){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FragmentUpdate fragmentUpdate = new FragmentUpdate();
        fragmentUpdate.setHideBackIcon(new HideBackIcon() {
            @Override
            public void setHideButtonIcon(Boolean hideButtonIcon) {
                if (hideButtonIcon) {
                    imgBackUpdate.setVisibility(View.GONE);
                } else {
                    imgBackUpdate.setVisibility(View.VISIBLE);
                }
            }
        });
        fragmentTransaction.replace(R.id.frame_update_manager, fragmentUpdate);
        fragmentTransaction.commit();
    }

}
