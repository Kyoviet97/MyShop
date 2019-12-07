package com.gvtechcom.myshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.gvtechcom.myshop.Model.ItemDetailsModel;
import com.gvtechcom.myshop.R;

import java.util.List;

public class AdapterChildrenOfProduct extends RecyclerView.Adapter<AdapterChildrenOfProduct.ViewHolder> {
    private Context context;
    private List<ItemDetailsModel.Children> childrenList;

    public AdapterChildrenOfProduct(Context context, List<ItemDetailsModel.Children> childrenList) {
        this.context = context;
        this.childrenList = childrenList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_product_children, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        
    }

    @Override
    public int getItemCount() {
        return childrenList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgProductChildren;
        private CardView cardViewBackground;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProductChildren = itemView.findViewById(R.id.img_product_children);
            cardViewBackground = itemView.findViewById(R.id.card_view_backgroundColor);
        }
    }
}
