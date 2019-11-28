package com.gvtechcom.myshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gvtechcom.myshop.Model.UpdateNotifyModel;
import com.gvtechcom.myshop.R;

import java.util.List;

public class AdapterRecyclerUpdateNotify extends RecyclerView.Adapter<AdapterRecyclerUpdateNotify.ViewHolder>{
    private Context context;
    private List<UpdateNotifyModel.DataUpdateNoty> lsDataUpdateNotify;

    public AdapterRecyclerUpdateNotify(Context context, List<UpdateNotifyModel.DataUpdateNoty> lsDataUpdateNotify) {
        this.context = context;
        this.lsDataUpdateNotify = lsDataUpdateNotify;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_recycler_update_notify, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtTitleUpdateNotify.setText(lsDataUpdateNotify.get(position).title);
        holder.txtdateTimeUpdateNotify.setText(lsDataUpdateNotify.get(position).start_datetime);
    }

    @Override
    public int getItemCount() {
        return lsDataUpdateNotify.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitleUpdateNotify, txtdateTimeUpdateNotify;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitleUpdateNotify = itemView.findViewById(R.id.title_update_notify);
            txtdateTimeUpdateNotify = itemView.findViewById(R.id.start_datetime_update_notify);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickItem != null){
                        onClickItem.onClick(lsDataUpdateNotify.get(getAdapterPosition())._id);
                    }
                }
            });

        }
    }

    public interface onClickItem{
        void onClick(String idNotify);
    }

    private onClickItem onClickItem;

    public void setOnClickItem(onClickItem clickItem){
        this.onClickItem = clickItem;
    }
}
