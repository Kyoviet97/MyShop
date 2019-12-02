package com.gvtechcom.myshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gvtechcom.myshop.Model.BaseGetApiAddress;
import com.gvtechcom.myshop.Model.JustForYou;
import com.gvtechcom.myshop.Model.ProductByCategoryModel;
import com.gvtechcom.myshop.R;

import java.util.List;

import butterknife.BindView;

public class AdapterViewCategory extends RecyclerView.Adapter<AdapterViewCategory.ViewHolder> {
    private Context context;
    private List<JustForYou> lsProductByCategory;

    public AdapterViewCategory(Context context, List<JustForYou> lsProductByCategory) {
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
            imageViewJustForYou = (ImageView) itemView.findViewById(R.id.img_just_for_you);
            infoJustForYou = (TextView) itemView.findViewById(R.id.txt_info_just_for_you);
            priceJustForYou = (TextView) itemView.findViewById(R.id.txt_price_just_for_you);
            soldJustForYou = (TextView) itemView.findViewById(R.id.txt_sold_just_for_you);
        }
    }
}
