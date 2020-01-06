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
import com.gvtechcom.myshop.Model.FeaturedCategories;
import com.gvtechcom.myshop.R;

import java.util.List;

public class AdapterFeaturedCategories extends RecyclerView.Adapter<AdapterFeaturedCategories.ViewHolder> {
    private Context context;
    private List<FeaturedCategories> lsFeaturedCategories;

    public AdapterFeaturedCategories(Context context, List<FeaturedCategories> lsFeaturedCategories) {
        this.context = context;
        this.lsFeaturedCategories = lsFeaturedCategories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_featured_categories, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Glide.with(context)
                    .load(lsFeaturedCategories.get(position).getImage())
                    .placeholder(R.drawable.banner_image_slide)
                    .error(R.drawable.ic_icon_load_error_cetegory)
                    .into(holder.imageViewFeaturedCategories);

            holder.nameFeaturedCategories.setText(lsFeaturedCategories.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return lsFeaturedCategories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewFeaturedCategories;
        TextView nameFeaturedCategories;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewFeaturedCategories = (ImageView) itemView.findViewById(R.id.image_featured_Categories_up);
            nameFeaturedCategories = (TextView) itemView.findViewById(R.id.textView_featured_Categories_up);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null){
                        itemClickListener.itemClick(lsFeaturedCategories.get(getAdapterPosition()).getCategory_id());
                    }
                }
            });
        }
    }

    public interface ItemClickListener{
        void itemClick(String idProduct);
    }

    private ItemClickListener itemClickListener;

    public void setItemClickListenr(ItemClickListener itemClickListenr){
        this.itemClickListener = itemClickListenr;
    }
}
