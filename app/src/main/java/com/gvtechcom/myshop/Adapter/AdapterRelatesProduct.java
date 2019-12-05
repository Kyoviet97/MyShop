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
import com.gvtechcom.myshop.Model.ItemDetailsModel;
import com.gvtechcom.myshop.R;

import java.util.List;

public class AdapterRelatesProduct extends RecyclerView.Adapter<AdapterRelatesProduct.ViewHolder>{
    private Context context;
    private List<ItemDetailsModel.RelatesProduct> lsItemRelatesProduct;

    public AdapterRelatesProduct(Context context, List<ItemDetailsModel.RelatesProduct> lsItemRelatesProduct) {
        this.context = context;
        this.lsItemRelatesProduct = lsItemRelatesProduct;
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
                .load(lsItemRelatesProduct.get(position).image_thumbnail)
                .placeholder(R.drawable.banner_image_slide)
                .error(R.drawable.banner_image_slide)
                .into(holder.imageViewJustForYou);
        holder.infoJustForYou.setText(lsItemRelatesProduct.get(position).name);
        holder.priceJustForYou.setText("$" + lsItemRelatesProduct.get(position).min_price);
        holder.soldJustForYou.setText(lsItemRelatesProduct.get(position).sold + " sold");
    }

    @Override
    public int getItemCount() {
        return lsItemRelatesProduct.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewJustForYou;
        private TextView infoJustForYou, priceJustForYou, soldJustForYou;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewJustForYou = (ImageView) itemView.findViewById(R.id.img_just_for_you);
            infoJustForYou = (TextView) itemView.findViewById(R.id.txt_info_just_for_you);
            priceJustForYou = (TextView) itemView.findViewById(R.id.txt_price_just_for_you);
            soldJustForYou = (TextView) itemView.findViewById(R.id.txt_sold_just_for_you);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (setOnItemClickListener != null){
                        setOnItemClickListener.onItemClick(lsItemRelatesProduct.get(getAdapterPosition()).product_id);
                    }
                }
            });
        }
    }

    public interface SetOnItemClickListener{
        void onItemClick(String idProduct);
    }

    private SetOnItemClickListener setOnItemClickListener;

    public void setOnItemClickListener(SetOnItemClickListener setOnItemClickListener){
        this.setOnItemClickListener = setOnItemClickListener;
    }
}
