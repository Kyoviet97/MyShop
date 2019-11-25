package com.gvtechcom.myshop.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gvtechcom.myshop.Model.ResponseAddress;
import com.gvtechcom.myshop.R;

import java.util.List;

public class AdapterSpinerDistric extends BaseAdapter {
    private Context mContext;
    private List<ResponseAddress> listDistric;

    public AdapterSpinerDistric(Context mContext, List<ResponseAddress> listDistric) {
        this.mContext = mContext;
        this.listDistric = listDistric;
    }

    @Override
    public int getCount() {
        return listDistric.size();
    }

    @Override
    public Object getItem(int i) {
        return listDistric.get(i);
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
        ViewHolder viewHolder = new ViewHolder(view);
        ResponseAddress dataDistric = (ResponseAddress) getItem(i);
        if (dataDistric != null) {
            viewHolder.txtCountry.setText(dataDistric.getName());
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
