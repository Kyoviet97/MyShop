package com.gvtechcom.myshop.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gvtechcom.myshop.Model.KeywordsModel;
import com.gvtechcom.myshop.R;


public class AdapterKeyWordsSearch extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private KeywordsModel itemKeyword;

    public AdapterKeyWordsSearch(KeywordsModel itemKeyword) {
        this.itemKeyword = itemKeyword;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER){
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View viewHeader = layoutInflater.inflate(R.layout.header_recycler_view_search, parent, false);
            return new ViewHolderHeader(viewHeader);
        }else {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View viewItem = layoutInflater.inflate(R.layout.item_recycler_view_search, parent, false);
            return new ViewHolderItem(viewItem);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderHeader) {
            ((ViewHolderHeader) holder).txtHeader.setText(itemKeyword.lsKeywordsPopular.get(position));
            System.out.println("=================> head: " + itemKeyword.lsKeywordsHot.get(position));
        } else if (holder instanceof ViewHolderItem) {
            ((ViewHolderItem) holder) .txtItem.setText(itemKeyword.lsKeywordsPopular.get(position));
            System.out.println("=================> item: " + itemKeyword.lsKeywordsHot.get(position));
        }
    }

    class ViewHolderItem extends RecyclerView.ViewHolder {
        TextView txtItem;
        public ViewHolderItem(@NonNull View itemView) {
            super(itemView);
            txtItem = itemView.findViewById(R.id.txt_item_recycler_view_search);
        }
    }

    class ViewHolderHeader extends RecyclerView.ViewHolder {
        TextView txtHeader;
        public ViewHolderHeader(@NonNull View itemView) {
            super(itemView);
            txtHeader = itemView.findViewById(R.id.txt_header_recycler_view_search);
        }
    }

    @Override
    public int getItemCount() {
        return itemKeyword.lsKeywordsHot.size();
    }


    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0 || position == 3;
    }


}
