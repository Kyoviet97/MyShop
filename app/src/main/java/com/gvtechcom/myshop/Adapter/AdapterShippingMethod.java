package com.gvtechcom.myshop.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gvtechcom.myshop.Model.ShippingMethodModel;
import com.gvtechcom.myshop.R;

import java.util.List;

public class AdapterShippingMethod extends RecyclerView.Adapter<AdapterShippingMethod.viewHolder>{
    private List<ShippingMethodModel> lsMethod;

    private SetOnClickItem onClickItem;
    private int n = 99999;

    public AdapterShippingMethod(List<ShippingMethodModel> lsMethod) {
        this.lsMethod = lsMethod;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_recycele_shipping_method, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.txtPriceShipping.setText(lsMethod.get(position).txtPriceShipping);
        holder.txtLineOne.setText(lsMethod.get(position).txtLineShippingOne);
        holder.txtLineTwo.setText(lsMethod.get(position).txtLineShippingTwo);
        holder.txtLineThree.setText(lsMethod.get(position).txtLineShippingThree);

        if (position == n){
            holder.imgTickSelect.setVisibility(View.VISIBLE);
        }else {
            holder.imgTickSelect.setVisibility(View.INVISIBLE);
        }

        if (position == 0 && n == 99999){
            holder.imgTickSelect.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return lsMethod.size();
    }


    public class viewHolder extends RecyclerView.ViewHolder {
        private TextView txtPriceShipping;
        private TextView txtLineOne;
        private TextView txtLineTwo;
        private TextView txtLineThree;
        private ImageView imgTickSelect;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            txtPriceShipping = itemView.findViewById(R.id.price_shipping_method);
            txtLineOne = itemView.findViewById(R.id.txt_line_one);
            txtLineTwo = itemView.findViewById(R.id.txt_line_two);
            txtLineThree = itemView.findViewById(R.id.txt_line_three);
            imgTickSelect = itemView.findViewById(R.id.img_tick_shipping_method);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickItem != null){
                        onClickItem.onClickItem(lsMethod, getAdapterPosition());
                    }
                    n = getAdapterPosition();
                    notifyDataSetChanged();
                }
            });
        }
    }

    public void setOnItemClick(SetOnClickItem onItemClick){
        this.onClickItem = onItemClick;
    }

    public interface SetOnClickItem{
        void onClickItem(List<ShippingMethodModel> lsData, int position);
    }
}
