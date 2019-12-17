package com.gvtechcom.myshop.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gvtechcom.myshop.Interface.OnClickRecyclerView;
import com.gvtechcom.myshop.R;

import java.util.List;

public class AdapterSearchTopHot extends RecyclerView.Adapter<AdapterSearchTopHot.ViewHolder> {
    private List<String> lsDataKeywordHot;
    private OnClickRecyclerView onClickRecyclerView;

    public AdapterSearchTopHot(List<String> lsDataKeywordHot) {
        this.lsDataKeywordHot = lsDataKeywordHot;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_search_top_hot, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtKeywordHot.setText(lsDataKeywordHot.get(position));
    }

    @Override
    public int getItemCount() {
        return lsDataKeywordHot.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtKeywordHot;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtKeywordHot = itemView.findViewById(R.id.txt_keyword_hot);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickRecyclerView != null){
                        onClickRecyclerView.onClick(lsDataKeywordHot.get(getAdapterPosition()));
                    }
                }
            });
        }
    }

    public void setOnClickRecyclerView(OnClickRecyclerView onClickRecyclerView){
        this.onClickRecyclerView = onClickRecyclerView;
    }
}
