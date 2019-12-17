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


public class AdapterKeyWordsSearch extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<String> dataKeyword;
    private OnClickRecyclerView onClickRecyclerView;

    public AdapterKeyWordsSearch(List<String> dataKeyword) {
        this.dataKeyword = dataKeyword;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_recycler_view_search, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).txtKeyword.setText(dataKeyword.get(position));
    }

    public void upDateAdapter(List<String> dataKeyword){
        this.dataKeyword = dataKeyword;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dataKeyword.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtKeyword;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtKeyword = itemView.findViewById(R.id.txt_item_recycler_view_search);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickRecyclerView != null){
                        onClickRecyclerView.onClick(dataKeyword.get(getAdapterPosition()));
                    }
                }
            });
        }
    }

    public void setOnClickRecyclerView(OnClickRecyclerView onClickRecyclerView) {
        this.onClickRecyclerView = onClickRecyclerView;
    }
}
