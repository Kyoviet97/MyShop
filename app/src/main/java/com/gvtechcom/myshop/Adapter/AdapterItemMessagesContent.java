package com.gvtechcom.myshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gvtechcom.myshop.Model.ItemRecyclerMessagesContentModel;
import com.gvtechcom.myshop.R;

import java.util.List;

public class AdapterItemMessagesContent extends RecyclerView.Adapter<AdapterItemMessagesContent.ViewHolder>{
    private Context context;
    private List<ItemRecyclerMessagesContentModel> lsMessagesContent;

    public AdapterItemMessagesContent(Context context, List<ItemRecyclerMessagesContentModel> lsMessagesContent) {
        this.context = context;
        this.lsMessagesContent = lsMessagesContent;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_recycler_messages_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context)
                .load(lsMessagesContent.get(position).urlImage)
                .placeholder(R.drawable.ic_icon_load_error_cetegory)
                .error(R.drawable.ic_icon_load_error_cetegory)
                .into(holder.imgMessagesContent);

        holder.txtTitle.setText(lsMessagesContent.get(position).title);
        holder.txtTime.setText(lsMessagesContent.get(position).time);
        holder.txtContent.setText(lsMessagesContent.get(position).content);

    }

    @Override
    public int getItemCount() {
        return lsMessagesContent.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout mainItemLayout;
        private ImageView imgMessagesContent;
        private TextView txtTitle, txtTime, txtContent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mainItemLayout = itemView.findViewById(R.id.layout_item_recycler_view_messages);
            imgMessagesContent = itemView.findViewById(R.id.img_item_recycler_view_messages);
            txtTitle = itemView.findViewById(R.id.txt_title_recycler_view_messages);
            txtTime = itemView.findViewById(R.id.txt_time_recycler_messages);
            txtContent = itemView.findViewById(R.id.txt_recycler_messages);
        }
    }
}
