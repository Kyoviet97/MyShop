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
import com.gvtechcom.myshop.Model.JustForYou;
import com.gvtechcom.myshop.Model.ProductByCategoryModel;
import com.gvtechcom.myshop.R;

import java.util.List;

import butterknife.BindView;

public class AdapterViewCategory extends RecyclerView.Adapter<AdapterViewCategory.ViewHolder> {
    private Context context;
    private ProductByCategoryModel lsProductByCategory;

    public AdapterViewCategory(Context context, ProductByCategoryModel lsProductByCategory) {
        this.context = context;
        this.lsProductByCategory = lsProductByCategory;
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
                .load(lsProductByCategory.products.get(position).image)
                .placeholder(R.drawable.banner_image_slide)
                .error(R.drawable.banner_image_slide)
                .into(holder.imageViewJustForYou);

        holder.infoJustForYou.setText(lsProductByCategory.products.get(position).product_name);
        holder.priceJustForYou.setText("$" + lsProductByCategory.products.get(position).price);
        holder.soldJustForYou.setText(lsProductByCategory.products.get(position).sold + " sold");
    }

    @Override
    public int getItemCount() {
        return lsProductByCategory.products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewJustForYou;
        private TextView infoJustForYou, priceJustForYou, soldJustForYou;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewJustForYou = (ImageView) itemView.findViewById(R.id.img_just_for_you);
            infoJustForYou = (TextView) itemView.findViewById(R.id.txt_info_just_for_you);
            priceJustForYou = (TextView) itemView.findViewById(R.id.txt_price_just_for_you);
            soldJustForYou = (TextView) itemView.findViewById(R.id.txt_sold_just_for_you);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (setOnClickListener != null){
                        setOnClickListener.setOnClickListener(lsProductByCategory.products.get(getAdapterPosition()).product_id);
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
