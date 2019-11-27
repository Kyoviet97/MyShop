package com.gvtechcom.myshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gvtechcom.myshop.Model.CountryInfoModel;
import com.gvtechcom.myshop.R;

import java.util.List;

public class AdapterDistricAddress extends RecyclerView.Adapter<AdapterDistricAddress.ViewHoler> {
    private Context context;
    private List<CountryInfoModel.Data> lsDistric;

    public AdapterDistricAddress(Context context, List<CountryInfoModel.Data> lsDistric) {
        this.context = context;
        this.lsDistric = lsDistric;
    }

    public void setAdapter(List<CountryInfoModel.Data> lsDistricFilter) {
        this.lsDistric = lsDistricFilter;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_address_shipping, parent, false);
        return new ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler holder, int position) {
        holder.textViewDistric.setText(lsDistric.get(position).name);
        if (lsDistric.get(position).isCheck != null) {
            if (lsDistric.get(position).isCheck) {
                holder.textViewDistric.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_done, 0);
            } else {
                holder.textViewDistric.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }
        }

    }

    @Override
    public int getItemCount() {
        return lsDistric.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder {
        TextView textViewDistric;

        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            textViewDistric = itemView.findViewById(R.id.txt_item_address_shipping);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickedListener != null) {
                        onItemClickedListener.onItemClick(getAdapterPosition(), lsDistric);
                    }
                }
            });
        }
    }

    public interface OnItemClickedListener {
        void onItemClick(int position, List<CountryInfoModel.Data> lsDistric);
    }

    protected OnItemClickedListener onItemClickedListener;

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }
}
