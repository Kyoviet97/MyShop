package com.gvtechcom.myshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gvtechcom.myshop.Interface.OnClickRecyclerView;
import com.gvtechcom.myshop.Model.FlashDealsDetails;
import com.gvtechcom.myshop.R;

import java.util.List;

public class AdapterRecyclerDataFlashDeals extends RecyclerView.Adapter<AdapterRecyclerDataFlashDeals.ViewHolder>{
    private Context context;
    private List<FlashDealsDetails.ProductsData> lsProductsData;
    private OnClickRecyclerView onClickRecyclerView;

    public AdapterRecyclerDataFlashDeals(Context context, List<FlashDealsDetails.ProductsData> lsProductsData) {
        this.context = context;
        this.lsProductsData = lsProductsData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_just_for_you, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context)
                .load(lsProductsData.get(position).image)
                .placeholder(R.drawable.ic_icon_load_error_just_for_you)
                .error(R.drawable.ic_icon_load_error_just_for_you)
                .override(170,170)
                .into(holder.imgFlashDeals);
        holder.productName.setText(lsProductsData.get(position).product_name);
        holder.priceSale.setText("$" +lsProductsData.get(position).price_sale);
        holder.sold.setText(lsProductsData.get(position).sold + "sold");

    }

    public void upDateDataAdapterFlashDeals(List<FlashDealsDetails.ProductsData> lsProductsData){
        this.lsProductsData = lsProductsData;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return lsProductsData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgFlashDeals;
        private TextView productName;
        private TextView priceSale;
        private TextView sold;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFlashDeals = itemView.findViewById(R.id.img_just_for_you);
            productName = itemView.findViewById(R.id.txt_info_just_for_you);
            priceSale = itemView.findViewById(R.id.txt_price_just_for_you);
            sold = itemView.findViewById(R.id.txt_sold_just_for_you);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickRecyclerView != null){
                        onClickRecyclerView.onClick(lsProductsData.get(getAdapterPosition()).product_id);
                    }
                }
            });
        }
    }

    public void setOnClickItemRecyclerView(OnClickRecyclerView onClickItemRecyclerView){
        this.onClickRecyclerView = onClickItemRecyclerView;
    }



}
