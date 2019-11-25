package com.gvtechcom.myshop.Account;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.gvtechcom.myshop.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RuleAccount extends Fragment {
    private View rootView;

    private FragmentManager fragmentManager;

    @BindView(R.id.back_ruler)
    ImageView backButtonRile;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_rules_account, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        backButtonRile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = getFragmentManager();
                fragmentManager.popBackStack();

            }
        });
    }
}
