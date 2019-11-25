package com.mylibrary.ui.recycleloadmore;

import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * Created by An on 10/14/2015.
 */
public class RecycleViewHolder extends RecyclerView.ViewHolder {
    public RecycleViewHolder(View itemView) {
        super(itemView);
        Log.e("Annv", "we should find views or add views listener in RecycleViewAdapter.onCreateDataViewHolder method," +
                "so we need to define our own ViewHolder,and find views,add listener inside it, instead of using RecycleViewHolder.");
    }
}
