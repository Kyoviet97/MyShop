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
import com.gvtechcom.myshop.Model.BrowseCategoriesModel;
import com.gvtechcom.myshop.R;

import java.util.List;


public class AdapterItemSubCategory extends RecyclerView.Adapter<AdapterItemSubCategory.ViewHolder> {
    private Context context;
    private List<BrowseCategoriesModel.Children> lsSubCategoryChildren;

    public AdapterItemSubCategory(Context context, List<BrowseCategoriesModel.Children> lsSubCategoryChildren) {
        this.context = context;
        this.lsSubCategoryChildren = lsSubCategoryChildren;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_sub_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context)
                .load(lsSubCategoryChildren.get(position).category_image)
                .placeholder(R.drawable.banner_image_slide)
                .error(R.drawable.banner_image_slide)
                .override(250, 250)
                .into(holder.imgSubCategoryChidren);

        holder.txtSubCategoryChidren.setText(lsSubCategoryChildren.get(position).category_name);

    }

    @Override
    public int getItemCount() {
        return lsSubCategoryChildren.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgSubCategoryChidren;
        private TextView txtSubCategoryChidren;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSubCategoryChidren = itemView.findViewById(R.id.img_sub_category_chidren);
            txtSubCategoryChidren = itemView.findViewById(R.id.txt_sub_category_chidren);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.setItemClick(lsSubCategoryChildren.get(getAdapterPosition()).category_id);
                    }
                }
            });
        }
    }

    public interface setOnItemClickListener {
        void setItemClick(String idCategoci);
    }

    private setOnItemClickListener onItemClickListener;

    public void setOnClickListener(setOnItemClickListener onClickListener) {
        this.onItemClickListener = onClickListener;
    }
}
