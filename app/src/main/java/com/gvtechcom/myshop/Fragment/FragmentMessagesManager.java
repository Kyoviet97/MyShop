package com.gvtechcom.myshop.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.gvtechcom.myshop.R;

public class FragmentMessagesManager extends Fragment {
    private View rootView;
    private FragmentManager fragmentMessages;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_messages_manager, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadFragmentMessages();
    }

    private void loadFragmentMessages(){
        fragmentMessages = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentMessages.beginTransaction();
        fragmentTransaction.replace(R.id.frame_messages_manager, new FragmentMessages());
        fragmentTransaction.commit();

    }
}
