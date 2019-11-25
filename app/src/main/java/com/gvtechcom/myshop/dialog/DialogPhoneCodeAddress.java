package com.gvtechcom.myshop.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gvtechcom.myshop.Adapter.AdapterPhoneCodeAddress;
import com.gvtechcom.myshop.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogPhoneCodeAddress extends AppCompatDialog {
    private AdapterPhoneCodeAddress adapterPhoneCodeAddress;
    private List<String> phoneCodeList;
    private String phoneCode;
    private String phoneCountry;

    @BindView(R.id.edt_search_address)
    EditText edtSearchDialog;
    @BindView(R.id.RecyclerView_address)
    RecyclerView recyclerViewAddress;
    @BindView(R.id.btn_cancel_address)
    Button CancelAddress;
    @BindView(R.id.btn_select_address)
    Button Select_Address;
    @BindView(R.id.txt_title_dialog_address)
    TextView txtTitleDialogAddress;

    public String getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }

    public DialogPhoneCodeAddress(Context context, List<String> dataPhoneCode, String nameCountry) {
        super(context);
        setCancelable(false);
        setContentView(R.layout.custom_dialog_address);
        ButterKnife.bind(this);
        this.phoneCodeList = dataPhoneCode;
        this.phoneCountry = nameCountry;
        init();
    }

    @OnClick({R.id.edt_search_address, R.id.RecyclerView_address, R.id.btn_cancel_address, R.id.btn_select_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel_address:
                dismiss();
                break;
            case R.id.btn_select_address:
                dismiss();
                break;
        }

    }

    private void init() {
        txtTitleDialogAddress.setText("Select your phone codes");
        setViewRecycler();
        setAdapterPhoneCode();
    }

    private void setViewRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerViewAddress.setLayoutManager(linearLayoutManager);
    }

    private void setAdapterPhoneCode() {
        if (phoneCodeList != null) {
            adapterPhoneCodeAddress = new AdapterPhoneCodeAddress(getContext(), phoneCodeList, phoneCountry);
            recyclerViewAddress.setAdapter(adapterPhoneCodeAddress);
            setClickAdapter();
        }

    }

    private void setClickAdapter() {
        adapterPhoneCodeAddress.setOnItemClickedListener(new AdapterPhoneCodeAddress.OnItemClickedListener() {
            @Override
            public void onItemClick(int position, List<String> phoneCodeList) {
                setPhoneCode(phoneCodeList.get(position) + " ");
            }
        });
    }


}
