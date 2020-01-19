package com.gvtechcom.myshop.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gvtechcom.myshop.Adapter.AdapterItemMessagesContent;
import com.gvtechcom.myshop.Model.ItemRecyclerMessagesContentModel;
import com.gvtechcom.myshop.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentContentMessages extends Fragment {
    private View rootView;
    @BindView(R.id.recycler_massages_content)
    RecyclerView recyclerMassagesContent;
    @BindView(R.id.txt_top_massages_content)
    TextView txTMassagesContent;

    private AdapterItemMessagesContent adapterItemMessagesContentl;
    private List<ItemRecyclerMessagesContentModel> lsItemRecyclerMessages;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_content_mesagess, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRecyclerView();
        loadApiRecyclerView();
    }

    private void setRecyclerView(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerMassagesContent.setLayoutManager(layoutManager);
    }

    private void loadApiRecyclerView(){
        lsItemRecyclerMessages = new ArrayList<>();
        lsItemRecyclerMessages.add(new ItemRecyclerMessagesContentModel("url", "FLOVEME Official Flags...", "13:48", "[Order Confirmation]"));
        lsItemRecyclerMessages.add(new ItemRecyclerMessagesContentModel("url", "XIAOMI Official", "Sep 29, 2019", "Of course, we can offer you some pro…"));
        lsItemRecyclerMessages.add(new ItemRecyclerMessagesContentModel("url", "#S: Sofia", "Jul 16, 2019", "Anything I can help?"));

        lsItemRecyclerMessages.add(new ItemRecyclerMessagesContentModel("url", "FLOVEME Official Flags...", "13:48", "[Order Confirmation]"));
        lsItemRecyclerMessages.add(new ItemRecyclerMessagesContentModel("url", "XIAOMI Official", "Sep 29, 2019", "Of course, we can offer you some pro…"));
        lsItemRecyclerMessages.add(new ItemRecyclerMessagesContentModel("url", "#S: Sofia", "Jul 16, 2019", "Anything I can help?"));

        lsItemRecyclerMessages.add(new ItemRecyclerMessagesContentModel("url", "FLOVEME Official Flags...", "13:48", "[Order Confirmation]"));
        lsItemRecyclerMessages.add(new ItemRecyclerMessagesContentModel("url", "XIAOMI Official", "Sep 29, 2019", "Of course, we can offer you some pro…"));
        lsItemRecyclerMessages.add(new ItemRecyclerMessagesContentModel("url", "#S: Sofia", "Jul 16, 2019", "Anything I can help?"));

        lsItemRecyclerMessages.add(new ItemRecyclerMessagesContentModel("url", "FLOVEME Official Flags...", "13:48", "[Order Confirmation]"));
        lsItemRecyclerMessages.add(new ItemRecyclerMessagesContentModel("url", "XIAOMI Official", "Sep 29, 2019", "Of course, we can offer you some pro…"));
        lsItemRecyclerMessages.add(new ItemRecyclerMessagesContentModel("url", "#S: Sofia", "Jul 16, 2019", "Anything I can help?"));

        setAdapterItemMessagesContentl(lsItemRecyclerMessages);
    }

    private void setAdapterItemMessagesContentl(List<ItemRecyclerMessagesContentModel> lsData){
        AdapterItemMessagesContent adapterItemMessagesContent = new AdapterItemMessagesContent(getActivity(), lsData);
        recyclerMassagesContent.setAdapter(adapterItemMessagesContent);
        adapterItemMessagesContent.setSetOnClickMessagesDetail(new AdapterItemMessagesContent.SetOnClickMessagesDetail() {
            @Override
            public void onClick() {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.frame_messages_manager, new FragmentMessagesDetailOrder());
                fragmentTransaction.addToBackStack("messages");
                fragmentTransaction.commit();
            }
        });
    }
}
