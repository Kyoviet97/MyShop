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


public class AdapterItemSubCategory extends RecyclerView.Adapter<AdapterItemSubCategory.ViewHolder> {
    private Context context;
    private List<BrowseCategoriesModel.Children> lsSubCategoryChildren;

    private SendIdCatergory sendIdCatergory;

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
                .load(lsSubCategoryChildren.get(position).image)
                .placeholder(R.drawable.ic_icon_load_error_cetegory)
                .error(R.drawable.ic_icon_load_error_cetegory)
                .override(250, 250)
                .into(holder.imgSubCategoryChidren);

        holder.txtSubCategoryChidren.setText(lsSubCategoryChildren.get(position).name);

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
                    if (sendIdCatergory != null) {
                        sendIdCatergory.sendIdCategory(lsSubCategoryChildren.get(getAdapterPosition()).id);
                    }
                }
            });
        }
    }

    public void setSendIdCategory(SendIdCatergory sendIdCategory){
        this.sendIdCatergory = sendIdCategory;
    }
}
