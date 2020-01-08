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
import com.gvtechcom.myshop.Model.BaseGetApiAddress;
import com.gvtechcom.myshop.Model.DataViewCategoryModel;
import com.gvtechcom.myshop.Model.JustForYou;
import com.gvtechcom.myshop.Model.ProductByCategoryModel;
import com.gvtechcom.myshop.R;

import java.util.List;

import butterknife.BindView;

public class AdapterViewCategory extends RecyclerView.Adapter<AdapterViewCategory.ViewHolder> {
    private Context context;
    private List<DataViewCategoryModel.Products> lsProductByCategory;

    public AdapterViewCategory(Context context, List<DataViewCategoryModel.Products> ls){
        this.context = context;
        this.lsProductByCategory = ls;
    }

    public void upDateAdapter(List<DataViewCategoryModel.Products> lsProductByCategoryUpdate){
        this.lsProductByCategory = lsProductByCategoryUpdate;
        notifyDataSetChanged();
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
                .load(lsProductByCategory.get(position).image)
                .placeholder(R.drawable.ic_icon_load_error_just_for_you)
                .override(200, 200)
                .error(R.drawable.ic_icon_load_error_just_for_you)
                .into(holder.imageViewJustForYou);

        holder.infoJustForYou.setText(lsProductByCategory.get(position).name);

        holder.priceJustForYou.setText("$" + lsProductByCategory.get(position).price);

        holder.soldJustForYou.setText(lsProductByCategory.get(position).sold + " sold");
    }


    @Override
    public int getItemCount() {
        return lsProductByCategory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewJustForYou;
        private TextView infoJustForYou, priceJustForYou, soldJustForYou;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewJustForYou = itemView.findViewById(R.id.img_just_for_you);
            infoJustForYou = itemView.findViewById(R.id.txt_info_just_for_you);
            priceJustForYou = itemView.findViewById(R.id.txt_price_just_for_you);
            soldJustForYou = itemView.findViewById(R.id.txt_sold_just_for_you);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (setOnClickListener != null) {
                        setOnClickListener.setOnClickListener(lsProductByCategory.get(getAdapterPosition()).id);
                    }
                }
            });
        }
    }

    public interface SetOnClickListener{
        void setOnClickListener(String idCategory);
    }

    private SetOnClickListener setOnClickListener;

    public void setOnClickListener(SetOnClickListener onClickListener){
        this.setOnClickListener = onClickListener;
    }
}
