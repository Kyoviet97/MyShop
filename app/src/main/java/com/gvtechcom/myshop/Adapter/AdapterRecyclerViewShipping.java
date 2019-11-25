package com.gvtechcom.myshop.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gvtechcom.myshop.Model.ResponseAddress;
import com.gvtechcom.myshop.R;

import java.util.List;

public class AdapterRecyclerViewShipping extends RecyclerView.Adapter<AdapterRecyclerViewShipping.ViewHolder> {
    private List<ResponseAddress> responseAddresses;
    private Context context;

    public AdapterRecyclerViewShipping(List<ResponseAddress> dataShippingAddress, Context context) {
        this.responseAddresses = dataShippingAddress;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_shipping_address, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String idAddress = responseAddresses.get(position).getId();
        String PhoneNumber = responseAddresses.get(position).getTelephone();
        String FullName = responseAddresses.get(position).getName();
        int intDefault = responseAddresses.get(position).getIsDefault();
        String ZipCode = responseAddresses.get(position).getZipCode();
        String PhoneCode = responseAddresses.get(position).getPhoneCode();
        String Address = responseAddresses.get(position).getDetail();

        holder.txtNameShippingAddress.setText(FullName);
        holder.txtMainShippingAddress.setText(Address + " " + ZipCode + '\n' + PhoneCode + " - " + PhoneNumber);
        if (intDefault == 1) {
            holder.imgSetdefault.setImageResource(R.drawable.ic_icon_default);
        }

    }

    @Override
    public int getItemCount() {
        return responseAddresses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNameShippingAddress, txtMainShippingAddress;
        ImageView imgSetdefault;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameShippingAddress = (TextView) itemView.findViewById(R.id.name_shipping_address);
            txtMainShippingAddress = (TextView) itemView.findViewById(R.id.main_shipping_address);
            imgSetdefault = (ImageView) itemView.findViewById(R.id.imageSetDefault);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickedListener != null) {
                        onItemClickedListener.onItemClick(responseAddresses.get(getAdapterPosition()).getId());
                    }
                }
            });
        }
    }

    public interface OnItemClickedListener {
        void onItemClick(String idAddress);
    }

    private OnItemClickedListener onItemClickedListener;

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }
}
