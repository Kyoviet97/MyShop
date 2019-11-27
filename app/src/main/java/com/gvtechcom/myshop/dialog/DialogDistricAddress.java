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

import com.gvtechcom.myshop.Adapter.AdapterDistricAddress;
import com.gvtechcom.myshop.Model.CountryInfoModel;
import com.gvtechcom.myshop.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogDistricAddress extends AppCompatDialog {
    private AdapterDistricAddress adapterDistricAddress;
    private List<CountryInfoModel.Data> lsDistric;
    private String nameDistric;
    private String idDistric;
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

    public String getNameDistric() {
        return nameDistric;
    }

    public void setNameDistric(String nameDistric) {
        this.nameDistric = nameDistric;
    }

    public String getIdDistric() {
        return idDistric;
    }

    public void setIdDistric(String idDistric) {
        this.idDistric = idDistric;
    }

    public Boolean getSlect() {
        return isSelect;
    }

    public void setSlect(Boolean slect) {
        isSelect = slect;
    }


    public DialogDistricAddress(Context context, List<CountryInfoModel.Data> lsDistric, String nameDistricOld, String title) {
        super(context);
        setCancelable(false);
        setContentView(R.layout.custom_dialog_address);
        ButterKnife.bind(this);
        setRecyclerView();
        setAdapter(lsDistric);
        edtSearchDialog.addTextChangedListener(textWatcher);
        setNameDistric(nameDistricOld);
        this.lsDistric = lsDistric;
        txtTitleDialogAddress.setText("Select your " + title);
    }

    private void setRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewAddress.setLayoutManager(linearLayoutManager);
    }

    private void setAdapter(List<CountryInfoModel.Data> lsDistric) {
        if (adapterDistricAddress == null && lsDistric != null) {
            adapterDistricAddress = new AdapterDistricAddress(getContext(), lsDistric);
            recyclerViewAddress.setAdapter(adapterDistricAddress);
            setClickAdapter();
        } else {
            adapterDistricAddress.setAdapter(lsDistric);
            adapterDistricAddress.notifyDataSetChanged();
            setClickAdapter();
        }

    }

    private void setClickAdapter() {
        adapterDistricAddress.setOnItemClickedListener(new AdapterDistricAddress.OnItemClickedListener() {
            @Override
            public void onItemClick(int position, List<CountryInfoModel.Data> lsDistricfl) {
                setSlect(true);
                setNameDistric(lsDistricfl.get(position).name);
                setIdDistric(lsDistricfl.get(position).id);
                for (int i = 0; i < lsDistricfl.size(); i++) {
                    CountryInfoModel.Data responseAddress = lsDistricfl.get(i);
                    if (i == position) {
                        responseAddress.isCheck = true;
                    } else {
                        responseAddress.isCheck = false;
                    }
                    adapterDistricAddress.notifyDataSetChanged();
                }
            }
        });
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() != 0) {
                List<CountryInfoModel.Data> listfilter = filterRecycler(s.toString());
                setAdapter(listfilter);
            } else {
                setAdapter(lsDistric);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private List<CountryInfoModel.Data> filterRecycler(String s) {
        List<CountryInfoModel.Data> lsDistricFilter = new ArrayList<>();
        for (CountryInfoModel.Data responseAddress : lsDistric) {
            if (responseAddress != null && responseAddress.name != null) {
                if (responseAddress.name.toLowerCase().contains(s.toLowerCase())) {
                    lsDistricFilter.add(responseAddress);
                }
            }
        }
        return lsDistricFilter;
    }

    @OnClick({R.id.edt_search_address, R.id.RecyclerView_address, R.id.btn_cancel_address, R.id.btn_select_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel_address:
                setSlect(false);
                dismiss();
                break;
            case R.id.btn_select_address:
                dismiss();
                break;
        }
    }
}
