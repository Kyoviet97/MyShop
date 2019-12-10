package com.gvtechcom.myshop.dialog;

import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gvtechcom.myshop.Adapter.AdapterRecyclerCountryAddress;
import com.gvtechcom.myshop.MainActivity;
import com.gvtechcom.myshop.Model.CountryInfoModel;
import com.gvtechcom.myshop.R;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogCountryAddress extends AppCompatDialog {
    private AdapterRecyclerCountryAddress adapterRecyclerCountry;
    private List<CountryInfoModel.Data> lsCountry;
    private int position;

    @BindView(R.id.edt_search_address)
    EditText edtSearchDialog;
    @BindView(R.id.RecyclerView_address)
    RecyclerView recyclerViewAddress;
    @BindView(R.id.btn_cancel_address)
    Button CancelAddress;
    @BindView(R.id.btn_select_address)
    Button Select_Address;


    public List<CountryInfoModel.Data> getLsCountry() {
        return lsCountry;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public DialogCountryAddress(Context context, List<CountryInfoModel.Data> responseCountries) {
        super(context);
        setCancelable(false);
        setContentView(R.layout.custom_dialog_address);
        int width = (int) (getContext().getResources().getDisplayMetrics().widthPixels * 0.90);
        int height = (int) (getContext().getResources().getDisplayMetrics().heightPixels * 0.75);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        getWindow().setLayout(width, height);

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
            public void onItemClick(List<CountryInfoModel.Data> lsCountryInfo, int position) {
                for (int i = 0; i < lsCountryInfo.size(); i++) {
                    CountryInfoModel.Data dataCountry = lsCountryInfo.get(i);
                    if (i == position) {
                        dataCountry.isCheck = true;
                    } else dataCountry.isCheck = false;
                }
                adapterRecyclerCountry.notifyDataSetChanged();

                setPosition(position);

                List<CountryInfoModel.Data> cityList = lsCountryInfo;
            }
        });
    }

    private void settingRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewAddress.setLayoutManager(linearLayoutManager);
    }


    private void settingAdapter(List<CountryInfoModel.Data> responseCountryList) {
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
                List<CountryInfoModel.Data> list = filterCountry(s.toString());
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

    private List<CountryInfoModel.Data> filterCountry(String charFillter) {
        List<CountryInfoModel.Data> responseCountryList = new ArrayList<>();
        for (CountryInfoModel.Data responseCountry : lsCountry) {
            if (responseCountry != null && responseCountry.name != null) {
                if (responseCountry.name.toLowerCase().contains(charFillter.toLowerCase())) {
                    responseCountryList.add(responseCountry);
                }
            }
        }
        return responseCountryList;
    }
}
