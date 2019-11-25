package com.gvtechcom.myshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gvtechcom.myshop.Model.CountryInfo;
import com.gvtechcom.myshop.R;

import java.util.List;

public class AdapterRecyclerCityAddress extends RecyclerView.Adapter<AdapterRecyclerCityAddress.ViewHolder> {
    private Context context;
    private List<CountryInfo.CityInfo> cityList;

    public AdapterRecyclerCityAddress(Context context, List<CountryInfo.CityInfo> cityList) {
        this.context = context;
        this.cityList = cityList;
    }

    public void setDataList(List<CountryInfo.CityInfo> lsCountry) {
        this.cityList = lsCountry;
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
        holder.textViewCity.setText(cityList.get(position).name);
        if (cityList.get(position).isCheck != null) {
            if (cityList.get(position).isCheck) {
                holder.textViewCity.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_done, 0);
            } else {
                holder.textViewCity.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }
        }
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewCity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCity = itemView.findViewById(R.id.txt_item_address_shipping);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickedListener != null)
                        onItemClickedListener.onItemClick(getAdapterPosition(), cityList);
                }
            });

        }


    }

    public interface OnItemClickedListener {
        void onItemClick(int Position, List<CountryInfo.CityInfo> listCity);
    }

    private OnItemClickedListener onItemClickedListener;

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }

}
