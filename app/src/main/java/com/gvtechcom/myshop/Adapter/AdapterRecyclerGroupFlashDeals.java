package com.gvtechcom.myshop.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gvtechcom.myshop.Model.FlashDealsDetails;
import com.gvtechcom.myshop.R;

import java.util.List;

public class AdapterRecyclerGroupFlashDeals extends RecyclerView.Adapter<AdapterRecyclerGroupFlashDeals.ViewHolder> {
    private Context context;
    private List<FlashDealsDetails.ProductGroups> lsFlashNameGroups;

    public AdapterRecyclerGroupFlashDeals(Context context, List<FlashDealsDetails.ProductGroups> lsFlashNameGroups) {
        this.context = context;
        this.lsFlashNameGroups = lsFlashNameGroups;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_group_flash_deal, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Typeface face = null;
        if (position == 0) {
            holder.imgCheckNameGroub.setVisibility(View.VISIBLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                face = context.getResources().getFont(R.font.sf_pro_text_semibold);
                holder.txtNameGroup.setTypeface(face);
            }
        } else {
            holder.imgCheckNameGroub.setVisibility(View.INVISIBLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                face = context.getResources().getFont(R.font.sf_pro_text_regular);
                holder.txtNameGroup.setTypeface(face);
            }
        }
        holder.txtNameGroup.setText(lsFlashNameGroups.get(position).product_group_name);
        if (lsFlashNameGroups.get(position).icSelect != null) {
            if (lsFlashNameGroups.get(position).icSelect) {
                holder.imgCheckNameGroub.setVisibility(View.VISIBLE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    face = context.getResources().getFont(R.font.sf_pro_text_semibold);
                }
                holder.txtNameGroup.setTypeface(face);
            } else {
                holder.imgCheckNameGroub.setVisibility(View.INVISIBLE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    face = context.getResources().getFont(R.font.sf_pro_text_regular);
                }
                holder.txtNameGroup.setTypeface(face);
            }
        }
    }

    @Override
    public int getItemCount() {
        return lsFlashNameGroups.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNameGroup;
        private ImageView imgCheckNameGroub;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameGroup = itemView.findViewById(R.id.txt_name_group_flash_deals);
            imgCheckNameGroub = itemView.findViewById(R.id.img_check_name_group_flash_deals);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onClickListner(lsFlashNameGroups, getAdapterPosition());
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onClickListner(List<FlashDealsDetails.ProductGroups> lsNameGroub, int positon);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemclickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
