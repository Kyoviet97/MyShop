package com.gvtechcom.myshop.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gvtechcom.myshop.R;

import java.util.List;

public class AdapterSpinerPhoneNumber extends BaseAdapter {
    private Context mContext;
    private List<String> spinerCountryList;

    public AdapterSpinerPhoneNumber(Context mContext, List<String> spinerCountryList) {
        this.mContext = mContext;
        this.spinerCountryList = spinerCountryList;
    }

    @Override
    public int getCount() {
        return spinerCountryList.size();
    }

    @Override
    public Object getItem(int i) {
        return spinerCountryList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = View.inflate(mContext, R.layout.item_address_shipping, null);
        }
        AdapterSpinerPhoneNumber.ViewHolder viewHolder = new AdapterSpinerPhoneNumber.ViewHolder(view);
        spinerCountryList.get(i);
        if (spinerCountryList != null) {

            viewHolder.txtCountry.setText(spinerCountryList.get(i));

        }
        return view;
    }


    public class ViewHolder {
        private TextView txtCountry;

        public ViewHolder(View view) {
            txtCountry = view.findViewById(R.id.txt_item_address_shipping);
        }
    }
}
