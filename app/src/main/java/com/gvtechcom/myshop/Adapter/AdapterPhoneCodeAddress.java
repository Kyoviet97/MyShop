package com.gvtechcom.myshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gvtechcom.myshop.R;

import java.util.List;

public class AdapterPhoneCodeAddress extends RecyclerView.Adapter<AdapterPhoneCodeAddress.ViewHolder> {
    private Context context;
    private List<String> dataPhoneCode;
    private String nameCountry;
    int iii = 88;

    public AdapterPhoneCodeAddress(Context context, List<String> dataPhoneCode, String nameCountry) {
        this.context = context;
        this.dataPhoneCode = dataPhoneCode;
        this.nameCountry = nameCountry;
    }

    public void setDataList(List<String> dataPhoneCode) {
        this.dataPhoneCode = dataPhoneCode;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_address_shipping, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textViewCity.setText(dataPhoneCode.get(position) + " " + nameCountry);
    }

    @Override
    public int getItemCount() {
        return dataPhoneCode.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewCity;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCity = itemView.findViewById(R.id.txt_item_address_shipping);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iii = getAdapterPosition();
                    if (iii == getAdapterPosition()) {
                        textViewCity.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_done, 0);
                    } else {
                        textViewCity.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    }

                    if (onItemClickedListener != null) {
                        onItemClickedListener.onItemClick(getAdapterPosition(), dataPhoneCode);
                    }
                }
            });

        }

    }

    public interface OnItemClickedListener {
        void onItemClick(int Position, List<String> phoneCodeList);
    }

    private OnItemClickedListener onItemClickedListener;

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }

}
