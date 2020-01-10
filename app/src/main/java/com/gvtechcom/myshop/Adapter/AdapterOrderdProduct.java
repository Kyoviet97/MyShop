package com.gvtechcom.myshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gvtechcom.myshop.Interface.SetClickRecyclerOrders;
import com.gvtechcom.myshop.Model.OrdersProductModel;
import com.gvtechcom.myshop.R;

import java.util.List;

public class AdapterOrderdProduct extends RecyclerView.Adapter<AdapterOrderdProduct.ViewHolder>{

    private Context context;
    private List<OrdersProductModel> lsProductModel;
    private SetClickRecyclerOrders clickRecyclerOrders;

    public AdapterOrderdProduct(Context context, List<OrdersProductModel> productModel) {
        this.context = context;
        this.lsProductModel = productModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_recycler_view_orders, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.ordersDate.setText(lsProductModel.get(position).ordersDate);
        holder.ordersId.setText(lsProductModel.get(position).ordersId);

        Glide.with(context)
                .load(lsProductModel.get(position).imgProduct)
                .error(R.drawable.ic_icon_load_error_cetegory)
                .into(holder.imgOrdersProduct);

        holder.titleProductOrders.setText(lsProductModel.get(position).titleProduct);

        holder.detailProduct.setText(lsProductModel.get(position).detailProduct);

        holder.priceProduct.setText(lsProductModel.get(position).priceProduct);

        if (lsProductModel.get(position).isComplete){
            holder.orderComplete.setVisibility(View.VISIBLE);
            holder.mainNoComplete.setVisibility(View.GONE);
        }else {
            holder.orderComplete.setVisibility(View.GONE);
            holder.mainNoComplete.setVisibility(View.VISIBLE);

            holder.btnTrackiing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickRecyclerOrders != null){
                        clickRecyclerOrders.setClickButtonOrders(1);
                    }
                }
            });

            holder.btnOrderReceived.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickRecyclerOrders != null){
                        clickRecyclerOrders.setClickButtonOrders(2);
                    }
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return lsProductModel.size();
    }

    public void setOnClickButton(SetClickRecyclerOrders setClickRecyclerOrders){
        this.clickRecyclerOrders = setClickRecyclerOrders;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView ordersDate;
        private TextView ordersId;
        private ImageView imgOrdersProduct;
        private TextView titleProductOrders;
        private TextView detailProduct;
        private TextView priceProduct;
        private TextView orderComplete;
        private LinearLayout mainNoComplete;
        private Button btnTrackiing;
        private Button btnOrderReceived;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ordersDate = itemView.findViewById(R.id.orders_date);
            ordersId = itemView.findViewById(R.id.orders_id);
            imgOrdersProduct = itemView.findViewById(R.id.img_orders_product);
            titleProductOrders = itemView.findViewById(R.id.txt_title_product_orders);
            detailProduct = itemView.findViewById(R.id.txt_detail_product);
            priceProduct = itemView.findViewById(R.id.txt_price_product);
            orderComplete = itemView.findViewById(R.id.txt_order_complete);
            mainNoComplete = itemView.findViewById(R.id.main_no_complete);
            btnTrackiing = itemView.findViewById(R.id.btn_trackiing);
            btnOrderReceived = itemView.findViewById(R.id.btn_order_received);
        }
    }
}
