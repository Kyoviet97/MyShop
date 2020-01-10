package com.gvtechcom.myshop.Utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.gvtechcom.myshop.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StarViewVote extends LinearLayout {
    @BindView(R.id.star_one)
    TextView statOne;
    @BindView(R.id.star_two)
    TextView statTwo;
    @BindView(R.id.star_three)
    TextView statThree;
    @BindView(R.id.star_four)
    TextView statFour;
    @BindView(R.id.star_fine)
    TextView statFine;

    private View rootView;
    public StarViewVote(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        LayoutInflater inflater = LayoutInflater.from(context);
        rootView = inflater.inflate(R.layout.view_star_vote, this);
        ButterKnife.bind(this, rootView);
    }

    public void setStartNumber(Double vote) {
       String stVote = String.valueOf(vote);

    }

}
