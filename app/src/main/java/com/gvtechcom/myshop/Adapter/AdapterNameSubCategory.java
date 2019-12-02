package com.gvtechcom.myshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gvtechcom.myshop.Model.BrowseCategoriesModel;
import com.gvtechcom.myshop.R;

import java.util.List;

public class AdapterNameSubCategory extends RecyclerView.Adapter<AdapterNameSubCategory.Holder> {
    private Context context;
    private List<BrowseCategoriesModel.Children> lsNameChildren;
    private AdapterItemSubCategory adapterItemSubCategory;
    private Boolean onOpenRecycler = true;

    public AdapterNameSubCategory(Context context, List<BrowseCategoriesModel.Children> lsNameChildren) {
        this.context = context;
        this.lsNameChildren = lsNameChildren;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_name_sub_category, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.txtNameSubCategory.setText(lsNameChildren.get(position).category_name);
        this.adapterItemSubCategory = new AdapterItemSubCategory(context, lsNameChildren.get(position).children);
        LinearLayoutManager linearLayoutManagerSubChidren = new GridLayoutManager(context, 3);
        holder.recyclerViewSubCategory.setLayoutManager(linearLayoutManagerSubChidren);
        holder.recyclerViewSubCategory.setAdapter(adapterItemSubCategory);

        holder.imgOpenSubChildren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onOpenRecycler){
                    Animation animationDown = AnimationUtils.loadAnimation(context, R.anim.animation_slide_down_recycler_view);
                    holder.recyclerViewSubCategory.setVisibility(View.VISIBLE);
                    holder.recyclerViewSubCategory.setAnimation(animationDown);
                    holder.imgOpenSubChildren.setRotation(270);
                    onOpenRecycler = false;
                }else {
                    Animation animationUp = AnimationUtils.loadAnimation(context, R.anim.animation_slide_down_recycler_view);
                    holder.recyclerViewSubCategory.setVisibility(View.GONE);
                    holder.imgOpenSubChildren.setRotation(90);
                    onOpenRecycler = true;
                }
            }
        });


        if (lsNameChildren.get(position).children.size() > 0) {
            holder.imgOpenSubChildren.setVisibility(View.VISIBLE);
        } else {
            holder.imgOpenSubChildren.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    public int getItemCount() {
        return lsNameChildren.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        private TextView txtNameSubCategory;
        private ImageView imgOpenSubChildren;
        private RecyclerView recyclerViewSubCategory;

        public Holder(@NonNull View itemView) {
            super(itemView);
            txtNameSubCategory = itemView.findViewById(R.id.txt_name_sub_category);
            imgOpenSubChildren = itemView.findViewById(R.id.img_open_sub_children);
            recyclerViewSubCategory = itemView.findViewById(R.id.recycler_sub_category);

        }
    }
}
