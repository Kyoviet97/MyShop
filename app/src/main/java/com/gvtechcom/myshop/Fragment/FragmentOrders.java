package com.gvtechcom.myshop.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gvtechcom.myshop.Adapter.AdapterOrderdProduct;
import com.gvtechcom.myshop.Interface.SetClickRecyclerOrders;
import com.gvtechcom.myshop.Model.OrdersProductModel;
import com.gvtechcom.myshop.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentOrders extends Fragment {
    private View rootView;
    private AdapterOrderdProduct adapterOrderdProduct;
    private List<OrdersProductModel> lsOrdersProductMode;
    @BindView(R.id.recycler_orders)
    RecyclerView recyclerOrders;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_orders, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRecycler();
        createData();
        setAdapterOrderdProduct(lsOrdersProductMode);
    }

    private void setRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerOrders.setLayoutManager(layoutManager);
    }

    private void setAdapterOrderdProduct(List<OrdersProductModel> lsOrdersProductMode){
        adapterOrderdProduct = new AdapterOrderdProduct(getActivity(), lsOrdersProductMode);
        recyclerOrders.setAdapter(adapterOrderdProduct);
        adapterOrderdProduct.setOnClickButton(new SetClickRecyclerOrders() {
            @Override
            public void setClickButtonOrders(int click) {
               setClickButton(click);
            }
        });
    }

    private void setClickButton(int click){
        switch (click){
            case 1:
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                Fragment fragmentOrderDetail = new FragmentOrderDetail();
                fragmentTransaction.add(R.id.frame_orders_manager, fragmentOrderDetail);
                fragmentTransaction.addToBackStack("order");
                fragmentTransaction.commit();
                break;

            case 2:
                break;
        }
    }

    private void createData(){
        lsOrdersProductMode = new ArrayList<>();
        lsOrdersProductMode.add(new OrdersProductModel("2019-12-18 10:24", "123123123123",
                "https://ae01.alicdn.com/kf/HTB1RzFjMgHqK1RjSZJnq6zNLpXaS.jpg_220x220.jpg",
                "FLOVEME 1M Magnetic Charge Cable Micro USB Cable For iPhone 11 Pro M…",
                "Black, For Apple iPhone, China, Qty: 1",
                "$5.2", true));

        lsOrdersProductMode.add(new OrdersProductModel("2020-01-18 10:24", "457842123",
                "https://ae01.alicdn.com/kf/H4f41a060077c4b52809d32e4e8d425122.jpg_220x220.jpg",
                "Alice. 'Why?' 'IT DOES THE BOOTS AND SHOES.' the.",
                "Black, For Apple iPhone, China, Qty: 1",
                "$56.2", false));

        lsOrdersProductMode.add(new OrdersProductModel("2020-02-11 12:24", "23465634535",
                "https://ae01.alicdn.com/kf/H83cded970f5445078c7da80be0a0462eh.jpg_220x220.jpg",
                "FLOVEME 1M Magnetic Charge Cable Micro USB Cable For iPhone 11 Pro M…",
                "Black, For Apple iPhone, China, Qty: 1",
                "$5.2", true));

        lsOrdersProductMode.add(new OrdersProductModel("2019-12-20 12:24", "42342342342",
                "https://ae01.alicdn.com/kf/H085c0ae5671c4ed8bd758ad2b7aa55241.jpg_220x220.jpg",
                "Alice went on, half to herself, as she swam",
                "Black, For Apple iPhone, China, Qty: 1",
                "$5.2", false));

            }
}