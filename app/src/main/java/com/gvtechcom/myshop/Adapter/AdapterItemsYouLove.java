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
import com.gvtechcom.myshop.Model.ItemYouLoveModel;
import com.gvtechcom.myshop.R;

import java.util.List;

public class AdapterItemsYouLove extends RecyclerView.Adapter<AdapterItemsYouLove.ViewHolder> {
    private Context context;
    private List<ItemYouLoveModel.Product> lsItemYouLove;

    public AdapterItemsYouLove(Context context, List<ItemYouLoveModel.Product> lsItemYouLove) {
        this.context = context;
        this.lsItemYouLove = lsItemYouLove;
    }

    public void UpdateAdapter(List<ItemYouLoveModel.Product> lsUpdate){
        this.lsItemYouLove = lsUpdate;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_just_for_you, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context)
                .load(lsItemYouLove.get(position).image)
                .override(200,200)
                .into(holder.imageViewItemsYouLove);
        holder.infoItemsYouLove.setText(lsItemYouLove.get(position).product_name);
        holder.priceItemsYouLove.setText("$" + lsItemYouLove.get(position).price);
        holder.soldItemsYouLove.setText(lsItemYouLove.get(position).sold + "sold");
    }

    @Override
    public int getItemCount() {
        return lsItemYouLove.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageViewItemsYouLove;
        TextView infoItemsYouLove, priceItemsYouLove, soldItemsYouLove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewItemsYouLove = (ImageView) itemView.findViewById(R.id.img_just_for_you);
            infoItemsYouLove = (TextView) itemView.findViewById(R.id.txt_info_just_for_you);
            priceItemsYouLove = (TextView) itemView.findViewById(R.id.txt_price_just_for_you);
            soldItemsYouLove = (TextView) itemView.findViewById(R.id.txt_sold_just_for_you);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null){
                        itemClickListener.onClickListener(lsItemYouLove.get(getAdapterPosition()).product_id);
                    }
                }
            });
        }

    }

    public interface ItemClickListener{
        void onClickListener(String productId);
    }

    private ItemClickListener itemClickListener;

    public void setOnItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }


}
