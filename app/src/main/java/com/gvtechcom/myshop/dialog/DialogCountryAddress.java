package com.gvtechcom.myshop.dialog;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gvtechcom.myshop.Adapter.AdapterRecyclerCountryAddress;
import com.gvtechcom.myshop.Model.CountryInfo;
import com.gvtechcom.myshop.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogCountryAddress extends AppCompatDialog {
    private AdapterRecyclerCountryAddress adapterRecyclerCountry;
    private List<CountryInfo> lsCountry;
    private int position;

    @BindView(R.id.edt_search_address)
    EditText edtSearchDialog;
    @BindView(R.id.RecyclerView_address)
    RecyclerView recyclerViewAddress;
    @BindView(R.id.btn_cancel_address)
    Button CancelAddress;
    @BindView(R.id.btn_select_address)
    Button Select_Address;


    public List<CountryInfo> getLsCountry() {
        return lsCountry;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

//    public void setLsCountry(List<CountryInfo> lsCountry) {
//        this.lsCountry = lsCountry;
//    }

    public DialogCountryAddress(Context context, List<CountryInfo> responseCountries) {
        super(context);
        setCancelable(false);
        setContentView(R.layout.custom_dialog_address);
        ButterKnife.bind(this);
        settingRecyclerView();
        this.lsCountry = responseCountries;
        settingAdapter(responseCountries);
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
        edtSearchDialog.addTextChangedListener(textWatcher);
    }

    private void setOnclickAdapter() {
        adapterRecyclerCountry.setOnItemClickedListener(new AdapterRecyclerCountryAddress.OnItemClickedListener() {
            @Override
            public void onItemClick(List<CountryInfo> lsCountryInfo, int position) {
                for (int i = 0; i < lsCountryInfo.size(); i++) {
                    CountryInfo dataCountry = lsCountryInfo.get(i);
                    if (i == position) {
                        dataCountry.isCheck = true;
                    } else dataCountry.isCheck = false;
                }
                adapterRecyclerCountry.notifyDataSetChanged();

                setPosition(position);

                List<CountryInfo> cityList = lsCountryInfo;
            }
        });
    }

    private void settingRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewAddress.setLayoutManager(linearLayoutManager);
    }


    private void settingAdapter(List<CountryInfo> responseCountryList) {
        if (adapterRecyclerCountry == null) {
            adapterRecyclerCountry = new AdapterRecyclerCountryAddress(getContext(), responseCountryList);
            recyclerViewAddress.setAdapter(adapterRecyclerCountry);
        } else {
            adapterRecyclerCountry.setDataList(responseCountryList);
            adapterRecyclerCountry.notifyDataSetChanged();
        }
        setOnclickAdapter();

    }

    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() != 0) {
                List<CountryInfo> list = filterCountry(s.toString());
                settingAdapter(list);
            } else {
                settingAdapter(lsCountry);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private List<CountryInfo> filterCountry(String charFillter) {
        List<CountryInfo> responseCountryList = new ArrayList<>();
        for (CountryInfo responseCountry : lsCountry) {
            if (responseCountry != null && responseCountry.name != null) {
                if (responseCountry.name.toLowerCase().contains(charFillter.toLowerCase())) {
                    responseCountryList.add(responseCountry);
                }
            }
        }
        return responseCountryList;
    }


}
