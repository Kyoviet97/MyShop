package com.gvtechcom.myshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gvtechcom.myshop.Model.TopNewFeaturedStore;
import com.gvtechcom.myshop.R;

import java.util.List;

public class AdapterTopNewFeaturedStore extends RecyclerView.Adapter<AdapterTopNewFeaturedStore.ViewHolder> {
    private Context context;
    private List<TopNewFeaturedStore.TopSelection> lsTopSelection;
    private List<TopNewFeaturedStore.NewForYou> lsNewForYou;
    private List<TopNewFeaturedStore.FeatureBrands> lsFeature;
    private List<TopNewFeaturedStore.StoreYouLove> lsStoreYouLove;

    public AdapterTopNewFeaturedStore(Context context, List<TopNewFeaturedStore.TopSelection> lsTopSelection,
                                      List<TopNewFeaturedStore.NewForYou> lsNewForYou, List<TopNewFeaturedStore.FeatureBrands> lsFeature,
                                      List<TopNewFeaturedStore.StoreYouLove> lsStoreYouLove) {
        this.context = context;
        this.lsTopSelection = lsTopSelection;
        this.lsNewForYou = lsNewForYou;
        this.lsFeature = lsFeature;
        this.lsStoreYouLove = lsStoreYouLove;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_top_selection, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position == 0){
            holder.imgIconTopSelection.setImageResource(R.drawable.ic_top_selection);
            holder.txtTopSelection.setText("Top Selection");
            Glide.with(context)
                    .load(lsTopSelection.get(position).product_image)
                    .placeholder(R.drawable.banner_image_slide)
                    .error(R.drawable.banner_image_slide)
                    .into(holder.imgTopSelectOne);

            Glide.with(context)
                    .load(lsTopSelection.get(position).product_image)
                    .placeholder(R.drawable.banner_image_slide)
                    .error(R.drawable.banner_image_slide)
                    .into(holder.imgTopSelectTwo);
        }

        if (position == 1){
            holder.imgIconTopSelection.setImageResource(R.drawable.ic_new_for_you);
            holder.txtTopSelection.setText("New for You");
            Glide.with(context)
                    .load(lsNewForYou.get(position).product_image)
                    .placeholder(R.drawable.banner_image_slide)
                    .error(R.drawable.banner_image_slide)
                    .into(holder.imgTopSelectOne);

            Glide.with(context)
                    .load(lsTopSelection.get(position).product_image)
                    .placeholder(R.drawable.banner_image_slide)
                    .error(R.drawable.banner_image_slide)
                    .into(holder.imgTopSelectTwo);
        }

        if (position == 3){
            holder.imgIconTopSelection.setImageResource(R.drawable.ic_featured_brands);
            holder.txtTopSelection.setText("Featured");
            Glide.with(context)
                    .load(lsTopSelection.get(position).product_image)
                    .placeholder(R.drawable.banner_image_slide)
                    .error(R.drawable.banner_image_slide)
                    .into(holder.imgTopSelectOne);

            Glide.with(context)
                    .load(lsTopSelection.get(position).product_image)
                    .placeholder(R.drawable.banner_image_slide)
                    .error(R.drawable.banner_image_slide)
                    .into(holder.imgTopSelectTwo);
        }

        if (position == 3){
            holder.imgIconTopSelection.setImageResource(R.drawable.ic_store_you_love);
            holder.txtTopSelection.setText("Store You'll Love");
            Glide.with(context)
                    .load(lsTopSelection.get(position).product_image)
                    .placeholder(R.drawable.banner_image_slide)
                    .error(R.drawable.banner_image_slide)
                    .into(holder.imgTopSelectOne);

            Glide.with(context)
                    .load(lsTopSelection.get(position).product_image)
                    .placeholder(R.drawable.banner_image_slide)
                    .error(R.drawable.banner_image_slide)
                    .into(holder.imgTopSelectTwo);
        }





    }

    @Override
    public int getItemCount() {
        return lsTopSelection.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgIconTopSelection, imgTopSelectOne, imgTopSelectTwo;
        TextView txtTopSelection;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTopSelection = (TextView) itemView.findViewById(R.id.textView_top_selection);
            imgIconTopSelection = (ImageView) itemView.findViewById(R.id.img_icon_top_selection);
            imgTopSelectOne = (ImageView) itemView.findViewById(R.id.img_top_selection_one);
            imgTopSelectTwo = (ImageView) itemView.findViewById(R.id.img_top_selection_two);
        }
    }

    public interface OnClickListener{
        void onItemClick(String product_id_top_select, String product_id_new_for_you, String store_id_store_you_love, String address_id_store_you_love,
        String brand_id_feature, String user_id_feature);
    }

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }
}
