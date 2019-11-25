package com.mylibrary.ui.recycleloadmore;

import android.graphics.Rect;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

/**
 * Created by Annv on 4/6/17.
 */

public class MarginDecoration extends RecyclerView.ItemDecoration {
    private int margin;

    public MarginDecoration(int margin) {
        this.margin = margin;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildLayoutPosition(view) > 0)
            outRect.top = margin;
        if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1)
            outRect.bottom = margin;
    }
}
