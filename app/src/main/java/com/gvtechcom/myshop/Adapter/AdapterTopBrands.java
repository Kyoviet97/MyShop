package com.gvtechcom.myshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gvtechcom.myshop.Model.CategoryFilterModel;
import com.gvtechcom.myshop.R;

import java.util.List;

public class AdapterTopBrands extends RecyclerView.Adapter<AdapterTopBrands.viewHolder> {
    private Context context;
    private List<CategoryFilterModel.Topbrands> lsTopBrands;

    public AdapterTopBrands(Context context, List<CategoryFilterModel.Topbrands> lsTopBrands) {
        this.context = context;
        this.lsTopBrands = lsTopBrands;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_top_brands, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Glide.with(context)
                .load(lsTopBrands.get(position).image)
                .placeholder(R.drawable.ic_icon_load_error_just_for_you)
                .error(R.drawable.ic_icon_load_error_just_for_you)
                .into(holder.imgTopBrands);
    }

    @Override
    public int getItemCount() {
        return lsTopBrands.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private ImageView imgTopBrands;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imgTopBrands = itemView.findViewById(R.id.img_top_brands);
        }
    }
}
