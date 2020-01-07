package com.gvtechcom.myshop.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gvtechcom.myshop.Model.BrowseCategoriesModel;
import com.gvtechcom.myshop.R;

import java.util.List;

public class AdapterRecyclerBrowseCategoriesLeft extends RecyclerView.Adapter<AdapterRecyclerBrowseCategoriesLeft.ViewHoler> {
    private Context context;
    private List<BrowseCategoriesModel> lsBrowseCategories;

    public AdapterRecyclerBrowseCategoriesLeft(Context context, List<BrowseCategoriesModel> lsBrowseCategories) {
        this.context = context;
        this.lsBrowseCategories = lsBrowseCategories;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_recycler_browse_categories_left, parent, false);
        return new ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler holder, int position) {
        Glide.with(context)
                .load(lsBrowseCategories.get(position).image)
                .placeholder(R.drawable.ic_icon_load_error_cetegory)
                .error(R.drawable.ic_icon_load_error_cetegory)
                .override(250, 250)
                .into(holder.imageView_browse_category_left);

        holder.txt_browse_category_left.setText(lsBrowseCategories.get(position).name);
        holder.linearLayout.setBackgroundColor(Color.WHITE);
    }

    @Override
    public int getItemCount() {
        return lsBrowseCategories.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder {
        private ImageView imageView_browse_category_left;
        private TextView txt_browse_category_left;
        private LinearLayout linearLayout;

        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            imageView_browse_category_left = itemView.findViewById(R.id.img_browse_categories_left);
            txt_browse_category_left = itemView.findViewById(R.id.txt_browse_categories_left);
            linearLayout = itemView.findViewById(R.id.layout_browse_categories_left);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (setOnClickItem != null){
                        setOnClickItem.onClickItem(lsBrowseCategories.get(getAdapterPosition()).children, lsBrowseCategories.get(getAdapterPosition()).top_brands);
                    }
                }
            });

        }
    }

    public interface setOnClickItem{
        void onClickItem(List<BrowseCategoriesModel.Children> lsChildren, List<BrowseCategoriesModel.TopBrands> lsTopBrands);
    }

    private setOnClickItem setOnClickItem;

    public void setOnClickItem(setOnClickItem clickItem){
        this.setOnClickItem = clickItem;
    }
}
