package com.gvtechcom.myshop.Adapter;

import android.content.Context;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.gvtechcom.myshop.Model.ImageModel;
import com.gvtechcom.myshop.R;

import java.util.ArrayList;

public class AdapterImageSlide extends PagerAdapter {
    private ArrayList<ImageModel> imageModelArrayList;
    private LayoutInflater inflater;
    private Context context;

    public AdapterImageSlide(Context context, ArrayList<ImageModel> imageModelArrayList) {
        this.context = context;
        this.imageModelArrayList = imageModelArrayList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return imageModelArrayList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.item_image_slide, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout
                .findViewById(R.id.image_silde);

        Glide.with(context)
                .load(imageModelArrayList.get(position).getImageSlide())
                .placeholder(R.drawable.banner_image_slide)
                .error(R.drawable.banner_image_slide)
                .into(imageView);


        view.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}