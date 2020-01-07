package com.gvtechcom.myshop.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.gvtechcom.myshop.Account.AccountActivity;
import com.gvtechcom.myshop.MainActivity;
import com.gvtechcom.myshop.R;

public class FragmentAccountManager extends Fragment {
    private View rootView;
    private FragmentManager fragmentManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_account_manager, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Fragment_Account();
    }


    private void Fragment_Account() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("com.gvtechcom.myshop.firts", Context.MODE_PRIVATE);
        boolean account_key = sharedPreferences.getBoolean("account", false);
        if (account_key) {
            fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_account_manager, new FragmentAccount(), "frag_account");
            fragmentTransaction.commit();
        } else {
            Intent intent = new Intent(getActivity(), AccountActivity.class);
            startActivity(intent);
        }
    }
}
