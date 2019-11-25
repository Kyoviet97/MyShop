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
import com.gvtechcom.myshop.Model.JustForYou;
import com.gvtechcom.myshop.R;

import java.util.List;

public class AdapterJustForYou extends RecyclerView.Adapter<AdapterJustForYou.ViewHolder> {
    private List<JustForYou> lsJustForYou;
    private Context context;

    public AdapterJustForYou(List<JustForYou> lsJustForYou, Context context) {
        this.lsJustForYou = lsJustForYou;
        this.context = context;
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
                .load(lsJustForYou.get(position).getProduct_image())
                .placeholder(R.drawable.banner_image_slide)
                .error(R.drawable.banner_image_slide)
                .into(holder.imageViewJustForYou);
        holder.infoJustForYou.setText(lsJustForYou.get(position).getName());
        holder.priceJustForYou.setText(lsJustForYou.get(position).getPrice());
        holder.soldJustForYou.setText(lsJustForYou.get(position).getSold());


    }

    @Override
    public int getItemCount() {
        return lsJustForYou.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageViewJustForYou;
        private TextView  infoJustForYou, priceJustForYou, soldJustForYou;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewJustForYou = (ImageView) itemView.findViewById(R.id.img_just_for_you);
            infoJustForYou = (TextView) itemView.findViewById(R.id.txt_info_just_for_you);
            priceJustForYou = (TextView) itemView.findViewById(R.id.txt_price_just_for_you);
            soldJustForYou = (TextView) itemView.findViewById(R.id.txt_sold_just_for_you);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null){
                        itemClickListener.onClickListener(lsJustForYou.get(getAdapterPosition()).getProduct_id());
                    }
                }
            });
        }

    }

    public interface ItemClickListener{
        void onClickListener(String idProduct);
    }

    public ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }


}
