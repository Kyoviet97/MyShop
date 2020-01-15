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

public class AdapterProductChidrenBands extends RecyclerView.Adapter<AdapterProductChidrenBands.ViewHolder>{
    private Context context;
    private List<ItemDetailsModel.Children> lsChildrenBands;

    public AdapterProductChidrenBands(Context context, List<ItemDetailsModel.Children> lsChildrenBands) {
        this.context = context;
        this.lsChildrenBands = lsChildrenBands;
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

    public void updateAdapter(List<ItemDetailsModel.Children> lsChildrenBands){
        this.lsChildrenBands = lsChildrenBands;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return lsChildrenBands.size();
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
