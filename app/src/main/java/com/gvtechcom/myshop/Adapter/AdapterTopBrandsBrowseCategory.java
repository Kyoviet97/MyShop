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
import com.gvtechcom.myshop.Interface.SendIdCatergory;
import com.gvtechcom.myshop.Model.BrowseCategoriesModel;
import com.gvtechcom.myshop.R;

import java.util.List;

public class AdapterTopBrandsBrowseCategory extends RecyclerView.Adapter<AdapterTopBrandsBrowseCategory.ViewHolder>{

    private List<BrowseCategoriesModel.TopBrands> lsTopBrand;
    private Context context;
    private SendIdCatergory sendIdCatergory;

    public AdapterTopBrandsBrowseCategory(List<BrowseCategoriesModel.TopBrands> lsTopBrand, Context context) {
        this.lsTopBrand = lsTopBrand;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_top_brands_view_category, parent, false);
        return new ViewHolder(view);
    }

    public void updateData(List<BrowseCategoriesModel.TopBrands> lsTopBrand){
        this.lsTopBrand = lsTopBrand;
        notifyDataSetChanged();
    }

    public void setSenIdCategory(SendIdCatergory senIdCategory){
        this.sendIdCatergory = senIdCategory;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context)
                .load(lsTopBrand.get(position).image)
                .placeholder(R.drawable.ic_icon_load_error_cetegory)
                .error(R.drawable.ic_icon_load_error_cetegory)
                .into(holder.imgTopBrands);

        holder.txtNameTopBrands.setText(lsTopBrand.get(position).name);

    }

    @Override
    public int getItemCount() {
        return lsTopBrand.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgTopBrands;
        private TextView txtNameTopBrands;
       public ViewHolder(@NonNull View itemView) {
           super(itemView);
           imgTopBrands = itemView.findViewById(R.id.img_top_brands);
           txtNameTopBrands = itemView.findViewById(R.id.txt_name_top_brands);

           itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   if (sendIdCatergory != null){
                       sendIdCatergory.sendIdCategory(lsTopBrand.get(getAdapterPosition()).id);
                   }
               }
           });
       }
   }
}
