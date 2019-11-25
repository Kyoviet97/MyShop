package com.gvtechcom.myshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gvtechcom.myshop.Model.Product;
import com.gvtechcom.myshop.R;

import java.util.List;

public class AdapterFlashDeals extends RecyclerView.Adapter<AdapterFlashDeals.ViewHolder>{
    private List<Product> lsProduct;
    private Context context;

    public AdapterFlashDeals(List<Product> lsProduct, Context context) {
        this.lsProduct = lsProduct;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_flash_deals, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String imageDrawable = lsProduct.get(position).getImage();
        String Price = lsProduct.get(position).getPriceSale();
        String Amount = lsProduct.get(position).getQuantity();
        String AmountTotal = lsProduct.get(position).getSold();
        String FlashSale = lsProduct.get(position).getPercentSale();

        Glide.with(context)
                .load(lsProduct.get(position).getImage())
                .placeholder(R.drawable.banner_image_slide)
                .error(R.drawable.banner_image_slide)
                .into(holder.img_flash_deals);

        holder.txt_flash_deals_price.setText("$" + Integer.parseInt(Price));
        holder.progressBar_flash_deals.setMax(Integer.parseInt(AmountTotal));
        holder.progressBar_flash_deals.setSecondaryProgress(Integer.parseInt(AmountTotal));
        holder.progressBar_flash_deals.setProgress(Integer.parseInt(Amount));
        holder.txtFlashSale.setText("-" + FlashSale + "%");
        holder.txt_amount.setText(Amount + " / " + AmountTotal);
    }

    @Override
    public int getItemCount() {
        return lsProduct.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txt_flash_deals_price, txt_amount, txtFlashSale;
        ImageView img_flash_deals;
        ProgressBar progressBar_flash_deals;
        public ItemClickListener itemClickListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_flash_deals_price = (TextView) itemView.findViewById(R.id.txt_flash_price);
            txt_amount = (TextView) itemView.findViewById(R.id.txt_amount);
            img_flash_deals = (ImageView) itemView.findViewById(R.id.image_icon_flash_deals);
            progressBar_flash_deals = (ProgressBar) itemView.findViewById(R.id.progressBar_flash_deals);
            txtFlashSale = (TextView) itemView.findViewById(R.id.txt_flash_sale);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(OnItemClickListener != null){
                        OnItemClickListener.onItemClick(getAdapterPosition());
                    }
                }
            });

        }


    }

   public interface ItemClickListener{
        void onItemClick(int position);
   }

   private ItemClickListener OnItemClickListener;

    public void setOnItemClickListener(ItemClickListener itemClick) {
        this.OnItemClickListener = itemClick;
    }
}

