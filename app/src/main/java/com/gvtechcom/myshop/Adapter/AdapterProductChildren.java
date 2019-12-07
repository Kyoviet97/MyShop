package com.gvtechcom.myshop.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gvtechcom.myshop.Model.ItemDetailsModel;
import com.gvtechcom.myshop.R;

import java.util.List;
import java.util.Random;

public class AdapterProductChildren extends RecyclerView.Adapter<AdapterProductChildren.ViewHolder>{
    private Context context;
    private List<ItemDetailsModel.Product> lsProductChildren;

    public AdapterProductChildren(Context context,  List<ItemDetailsModel.Product> lsProductChildren){
        this.context = context;
        this.lsProductChildren = lsProductChildren;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_product_children, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Random r = new Random();
        int random = r.nextInt(5);
        switch (random){
            case 0:
                holder.cardViewBackground.setCardBackgroundColor(Color.RED);
                break;
            case 1:
                holder.cardViewBackground.setCardBackgroundColor(Color.BLUE);
                break;
            case 2:
                holder.cardViewBackground.setCardBackgroundColor(Color.YELLOW);
                break;
            case 3:
                holder.cardViewBackground.setCardBackgroundColor(Color.GREEN);
                break;
            case 4:
                holder.cardViewBackground.setCardBackgroundColor(Color.BLACK );
                break;
        }

        Glide.with(context)
                .load(lsProductChildren.get(position).image)
                .placeholder(R.drawable.banner_image_slide)
                .error(R.drawable.banner_image_slide)
                .into(holder.imgProductChildren);
    }

    @Override
    public int getItemCount() {
        return lsProductChildren.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgProductChildren;
        private CardView cardViewBackground;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProductChildren = itemView.findViewById(R.id.img_product_children);
            cardViewBackground = itemView.findViewById(R.id.card_view_backgroundColor);
        }
    }


}
