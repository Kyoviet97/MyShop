package com.gvtechcom.myshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gvtechcom.myshop.Model.BrowseCategoriesModel;
import com.gvtechcom.myshop.R;

import java.util.List;

public class AdapterRecyclerBrowseCategoriesLeft extends RecyclerView.Adapter<AdapterRecyclerBrowseCategoriesLeft.ViewHoler>{
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
//        Glide.with(context)
//                .load("https://ae01.alicdn.com/kf/HTB1RzFjMgHqK1RjSZJnq6zNLpXaS.jpg_220x220.jpg")
//                .placeholder(R.drawable.banner_image_slide)
//                .error(R.drawable.banner_image_slide)
//                .into(holder.imageView_browse_category_left);
//
//        holder.txt_browse_category_left.setText(lsBrowseCategories.get(position).category_name);
    }

    @Override
    public int getItemCount() {
        return lsBrowseCategories.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder {
        private ImageView imageView_browse_category_left;
        private TextView txt_browse_category_left;

        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            imageView_browse_category_left = itemView.findViewById(R.id.img_browse_categories_left);
            txt_browse_category_left = imageView_browse_category_left.findViewById(R.id.txt_browse_categories_left);
        }
    }
}
