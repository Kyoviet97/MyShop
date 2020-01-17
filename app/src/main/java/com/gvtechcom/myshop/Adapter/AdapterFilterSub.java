package com.gvtechcom.myshop.Adapter;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gvtechcom.myshop.Model.CategoryFilterModel;
import com.gvtechcom.myshop.R;

import java.util.List;

public class AdapterFilterSub extends RecyclerView.Adapter<AdapterFilterSub.ViewHolder>{
    private List<CategoryFilterModel.Filters> filtersList;

    public AdapterFilterSub(List<CategoryFilterModel.Filters> filtersList) {
        this.filtersList = filtersList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_recycler_view_filter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtTitle.setText(filtersList.get(position).name);
    }

    @Override
    public int getItemCount() {
        return filtersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle;
        private ImageView imgOpenSub;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txt_title_filter);
            imgOpenSub = itemView.findViewById(R.id.img_open_sub_filter);
        }
    }
}
