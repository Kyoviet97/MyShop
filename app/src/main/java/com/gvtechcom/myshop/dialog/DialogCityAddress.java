package com.gvtechcom.myshop.dialog;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gvtechcom.myshop.Adapter.AdapterRecyclerCityAddress;
import com.gvtechcom.myshop.Model.CountryInfoModel;
import com.gvtechcom.myshop.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogCityAddress extends AppCompatDialog {
    private String nameCity;
    private String idCity;
    private AdapterRecyclerCityAddress adapterRecyclerCityAddress;
    private List<CountryInfoModel.City> cityList;
    private Boolean isSelect = false;

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

    @OnClick({R.id.edt_search_address, R.id.RecyclerView_address, R.id.btn_cancel_address, R.id.btn_select_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel_address:
                setSelect(false);
                dismiss();
                break;
            case R.id.btn_select_address:
                setSelect(true);
                dismiss();
                break;
        }

    }

    public DialogCityAddress(Context context, List<CountryInfoModel.City> cityList, String nameCityaOld) {
        super(context);
        setCancelable(false);
        setContentView(R.layout.custom_dialog_address);
        int width = (int) (getContext().getResources().getDisplayMetrics().widthPixels * 0.90);
        int height = (int) (getContext().getResources().getDisplayMetrics().heightPixels * 0.75);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        getWindow().setLayout(width, height);
        ButterKnife.bind(this);
        this.cityList = cityList;
        this.nameCity = nameCityaOld;
        init();
    }

    private void init() {
        txtTitleDialogAddress.setText("Select your city");
        settingRecyclerView();
        settingAdapter(cityList);
        edtSearchDialog.addTextChangedListener(textWatcher);
    }

    private void settingRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewAddress.setLayoutManager(linearLayoutManager);
    }

    private void settingAdapter(List<CountryInfoModel.City> cityList) {
        if (adapterRecyclerCityAddress == null) {
            adapterRecyclerCityAddress = new AdapterRecyclerCityAddress(getContext(), cityList);
            recyclerViewAddress.setAdapter(adapterRecyclerCityAddress);
        } else {
            adapterRecyclerCityAddress.setDataList(cityList);
            adapterRecyclerCityAddress.notifyDataSetChanged();
        }

        setOnclickAdapter();
    }


    private void setOnclickAdapter() {
        adapterRecyclerCityAddress.setOnItemClickedListener(new AdapterRecyclerCityAddress.OnItemClickedListener() {
            @Override
            public void onItemClick(int Position, List<CountryInfoModel.City> listCity) {
                setSelect(true);

                setIdCity(listCity.get(Position).id);
                setNameCity(listCity.get(Position).name);

                for (int i = 0; i < listCity.size(); i ++){
                    CountryInfoModel.City dataCity = listCity.get(i);
                    if (i == Position) {
                        dataCity.isCheck = true;
                    } else dataCity.isCheck = false;
                }
                adapterRecyclerCityAddress.notifyDataSetChanged();
            }
        });
    }

    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() != 0) {
                List<CountryInfoModel.City> listfilter = filterCity(s.toString());
                settingAdapter(listfilter);
            } else {
                settingAdapter(cityList);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private List<CountryInfoModel.City> filterCity(String charFillter) {
        List<CountryInfoModel.City> cityInfosFiter = new ArrayList<>();
        for (CountryInfoModel.City responseCity : cityList) {
            if (responseCity != null && responseCity.name != null) {
                if (responseCity.name.toLowerCase().contains(charFillter.toLowerCase())) {
                    cityInfosFiter.add(responseCity);
                }
            }
        }
        return cityInfosFiter;
    }

    public Boolean getSelect() {
        return isSelect;
    }

    public void setSelect(Boolean select) {
        isSelect = select;
    }

    public String getNameCity() {
        return nameCity;
    }

    public void setNameCity(String nameCity) {
        this.nameCity = nameCity;
    }

    public String getIdCity() {
        return idCity;
    }

    public void setIdCity(String idCity) {
        this.idCity = idCity;
    }

}
