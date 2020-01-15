package com.gvtechcom.myshop.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.gvtechcom.myshop.Fragment.FragmentViewCategory;
import com.gvtechcom.myshop.Interface.SendIdCatergory;
import com.gvtechcom.myshop.Model.BrowseCategoriesModel;
import com.gvtechcom.myshop.Model.ProductByCategoryModel;
import com.gvtechcom.myshop.Network.APIServer;
import com.gvtechcom.myshop.Network.RetrofitBuilder;
import com.gvtechcom.myshop.R;
import com.gvtechcom.myshop.Utils.Const;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AdapterNameSubCategory extends RecyclerView.Adapter<AdapterNameSubCategory.Holder> {
    private Context context;
    private List<BrowseCategoriesModel> lsNameChildren;
    private AdapterItemSubCategory adapterItemSubCategory;
    private APIServer apiServer;

    private SendIdCatergory sendIdCatergory;

    private Retrofit retrofit;

    public AdapterNameSubCategory(Context context, List<BrowseCategoriesModel> lsNameChildren) {
        this.context = context;
        this.lsNameChildren = lsNameChildren;
        retrofit = RetrofitBuilder.getRetrofit(Const.BASE_URL);
        apiServer = retrofit.create(APIServer.class);

    }

    public void updateData (List<BrowseCategoriesModel> lsNameChildren){
        this.lsNameChildren = lsNameChildren;
        notifyDataSetChanged();
    }

    public void setSenIdCategory(SendIdCatergory senIdCategory){
        this.sendIdCatergory = senIdCategory;
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
        holder.txtNameSubCategory.setText(lsNameChildren.get(position).name);
        this.adapterItemSubCategory = new AdapterItemSubCategory(context, lsNameChildren.get(position).children);
        adapterItemSubCategory.setSendIdCategory(new SendIdCatergory() {
            @Override
            public void sendIdCategory(String idCategory) {
                if (sendIdCatergory != null){
                    sendIdCatergory.sendIdCategory(idCategory);
                }
            }
        });
        LinearLayoutManager linearLayoutManagerSubChidren = new GridLayoutManager(context, 3);
        holder.recyclerViewSubCategory.setLayoutManager(linearLayoutManagerSubChidren);
        holder.recyclerViewSubCategory.setAdapter(adapterItemSubCategory);
        holder.imgOpenSubChildren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.recyclerViewSubCategory.getVisibility() == View.GONE) {
                    Animation animationDown = AnimationUtils.loadAnimation(context, R.anim.animation_slide_down_recycler_view);
                    holder.recyclerViewSubCategory.setVisibility(View.VISIBLE);
                    holder.recyclerViewSubCategory.setAnimation(animationDown);
                    holder.imgOpenSubChildren.setRotation(270);
                } else {
                    holder.recyclerViewSubCategory.setVisibility(View.GONE);
                    holder.imgOpenSubChildren.setRotation(90);
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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (sendIdCatergory != null) {
                        sendIdCatergory.sendIdCategory(lsNameChildren.get(getAdapterPosition()).id);
                    }
                }
            });
        }
    }

}
