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

import com.gvtechcom.myshop.Model.BaseGetAPIShippingAddress;
import com.gvtechcom.myshop.R;

import java.util.List;

public class AdapterRecyclerViewShipping extends RecyclerView.Adapter<AdapterRecyclerViewShipping.ViewHolder> {
    private List<BaseGetAPIShippingAddress.Data> responseAddresses;
    private Context context;

    public AdapterRecyclerViewShipping(List<BaseGetAPIShippingAddress.Data> dataShippingAddress, Context context) {
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
        String idAddress = responseAddresses.get(position).id;
        String PhoneNumber = responseAddresses.get(position).telephone;
        String FullName = responseAddresses.get(position).name;
        int intDefault = responseAddresses.get(position).is_default;
        String ZipCode = responseAddresses.get(position).zip_code;
        String PhoneCode = responseAddresses.get(position).phone_code;
        String Address = responseAddresses.get(position).detail;

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
                        onItemClickedListener.onItemClick(getAdapterPosition());
                    }
                }
            });
        }
    }

    public interface OnItemClickedListener {
        void onItemClick(int position);
    }

    private OnItemClickedListener onItemClickedListener;

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }
}
