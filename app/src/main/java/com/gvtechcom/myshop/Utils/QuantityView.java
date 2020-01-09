package com.gvtechcom.myshop.Utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gvtechcom.myshop.R;
import com.mylibrary.ui.input.CustomInputText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuantityView extends LinearLayout {
    private int soldQuantity = 1;
    private int totalSold = 1;
    private View inflater;

    @BindView(R.id.img_lost_item)
    ImageView imgLostItem;
    @BindView(R.id.img_add_item)
    ImageView imgAddItem;
    @BindView(R.id.txt_sold_item_to_buy)
    TextView txtSoldItem;
    @BindView(R.id.total_sold_item)
    TextView txtTotalSold;

    public QuantityView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
        setSoldQuantity();

    }

    private void init(Context context){
        inflater = LayoutInflater.from(context).inflate(R.layout.quantity_view, this);
        ButterKnife.bind(this, inflater);
    }

    public void setValue(int totalSold){
        this.totalSold = totalSold;
        txtTotalSold.setText(String.valueOf(totalSold));
    }

    public int getValue(int sold){
        return soldQuantity;
    }


    private void setSoldQuantity() {
        imgLostItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (soldQuantity > 1) {
                    soldQuantity--;
                    txtTotalSold.setText(String.valueOf(totalSold - (soldQuantity - 1)));
                    txtSoldItem.setText(String.valueOf(soldQuantity));
                    imgLostItem.setImageResource(R.drawable.ic_difference_item_detail_select);
                    if (soldQuantity == 1) {
                        imgLostItem.setImageResource(R.drawable.ic_difference_item_detail);
                        imgLostItem.setClickable(false);
                    }
                }
            }
        });

        imgAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soldQuantity++;
                txtTotalSold.setText(String.valueOf(totalSold - (soldQuantity - 1)));
                txtSoldItem.setText(String.valueOf(soldQuantity));
                imgLostItem.setImageResource(R.drawable.ic_difference_item_detail_select);
                imgLostItem.setClickable(true);
            }
        });
    }
}
