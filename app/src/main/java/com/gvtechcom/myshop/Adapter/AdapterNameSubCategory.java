package com.gvtechcom.myshop.Adapter;

import android.app.Fragment;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.gvtechcom.myshop.Fragment.FragmentViewCategory;
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
    private List<BrowseCategoriesModel.Children> lsNameChildren;
    private AdapterItemSubCategory adapterItemSubCategory;
    private APIServer apiServer;


    private Retrofit retrofit;

    public AdapterNameSubCategory(Context context, List<BrowseCategoriesModel.Children> lsNameChildren) {
        this.context = context;
        this.lsNameChildren = lsNameChildren;
        retrofit = RetrofitBuilder.getRetrofit(Const.BASE_URL);
        apiServer = retrofit.create(APIServer.class);

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
        adapterItemSubCategory.setOnClickListener(new AdapterItemSubCategory.setOnItemClickListener() {
            @Override
            public void setItemClick(String idCategoci) {
                Gson gson = new Gson();
                Call<ProductByCategoryModel.ProductByCategoryModelParser> call = apiServer.GetViewCategory(idCategoci, 1, 20);
                call.enqueue(new Callback<ProductByCategoryModel.ProductByCategoryModelParser>() {
                    @Override
                    public void onResponse(Call<ProductByCategoryModel.ProductByCategoryModelParser> call, Response<ProductByCategoryModel.ProductByCategoryModelParser> response) {
                        if (response.body().status != 200) {
                        } else {
                            ProductByCategoryModel dataViewCategor = response.body().data;
                            if (dataViewCategor != null) {
                                String jsonData = gson.toJson(dataViewCategor);
                                Bundle bundle = new Bundle();
                                bundle.putString("jsonDataViewCategory", jsonData);
                                AppCompatActivity activity = (AppCompatActivity) context;
                                Fragment myFragment = new FragmentViewCategory();
                                myFragment.setArguments(bundle);
                                activity.getFragmentManager().beginTransaction().add(R.id.content_home_frame_layout, myFragment).addToBackStack(null).commit();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ProductByCategoryModel.ProductByCategoryModelParser> call, Throwable t) {

                    }
                });
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
                    if (onClickListenr != null) {
                        onClickListenr.setOnClick(lsNameChildren, getAdapterPosition());
                    }
                }
            });
        }
    }

    public interface setOnClickListenr {
        void setOnClick(List<BrowseCategoriesModel.Children> lsNameChildren, int position);
    }

    private setOnClickListenr onClickListenr;

    public void setOnItemClickListener(setOnClickListenr onItemClickListener) {
        this.onClickListenr = onItemClickListener;
    }
}
