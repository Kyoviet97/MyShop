package com.gvtechcom.myshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gvtechcom.myshop.Model.CountryInfoModel;
import com.gvtechcom.myshop.R;

import java.util.List;

public class AdapterRecyclerCountryAddress extends RecyclerView.Adapter<AdapterRecyclerCountryAddress.ViewHolder> {
    private Context context;
    private List<CountryInfoModel.Data> responseCountries;

    public AdapterRecyclerCountryAddress(Context context, List<CountryInfoModel.Data> responseCountries) {
        this.context = context;
        this.responseCountries = responseCountries;
    }

    public void setDataList(List<CountryInfoModel.Data> lsCountry) {
        this.responseCountries = lsCountry;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_address_shipping, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtCountryAddress.setText(responseCountries.get(position).name);
        if (responseCountries.get(position).isCheck != null) {
            if (responseCountries.get(position).isCheck) {
                holder.txtCountryAddress.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_done, 0);
            } else {
                holder.txtCountryAddress.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }
        }
    }

    @Override
    public int getItemCount() {
        return responseCountries.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtCountryAddress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCountryAddress = itemView.findViewById(R.id.txt_item_address_shipping);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickedListener != null)
                        onItemClickedListener.onItemClick(responseCountries, getAdapterPosition());
                }
            });
        }
    }

    public interface OnItemClickedListener {
        void onItemClick(List<CountryInfoModel.Data> lsCountryInfo, int position);
    }

    private OnItemClickedListener onItemClickedListener;

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }
}