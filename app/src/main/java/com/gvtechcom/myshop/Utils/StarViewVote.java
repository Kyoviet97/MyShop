package com.gvtechcom.myshop.Utils;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    private Typeface awesome5ProSolid, awesome5proRegular;
    public StarViewVote(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        LayoutInflater inflater = LayoutInflater.from(context);
        rootView = inflater.inflate(R.layout.view_star_vote, this);
        ButterKnife.bind(this, rootView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            awesome5ProSolid = getResources().getFont(R.font.awesome_5pro_solid);
            awesome5proRegular = getResources().getFont(R.font.awesome_5pro_regular);
        }
    }

    public void setStartNumber(String vote) {
       switch (vote){
           case "0.0":
               statOne.setText(R.string.noStart);
               statOne.setTypeface(awesome5proRegular);

               statTwo.setText(R.string.noStart);
               statTwo.setTypeface(awesome5proRegular);

               statThree.setText(R.string.noStart);
               statThree.setTypeface(awesome5proRegular);

               statFour.setText(R.string.noStart);
               statFour.setTypeface(awesome5proRegular);

               statFine.setText(R.string.noStart);
               statFine.setTypeface(awesome5proRegular); 
               break;

           case "0.5":
               statOne.setText(R.string.haftStar);
               statOne.setTypeface(awesome5ProSolid);

               statTwo.setText(R.string.noStart);
               statTwo.setTypeface(awesome5proRegular);

               statThree.setText(R.string.noStart);
               statThree.setTypeface(awesome5proRegular);

               statFour.setText(R.string.noStart);
               statFour.setTypeface(awesome5proRegular);

               statFine.setText(R.string.noStart);
               statFine.setTypeface(awesome5proRegular);
               break;

           case "1.0":
               statTwo.setText(R.string.noStart);
               statTwo.setTypeface(awesome5proRegular);

               statThree.setText(R.string.noStart);
               statThree.setTypeface(awesome5proRegular);

               statFour.setText(R.string.noStart);
               statFour.setTypeface(awesome5proRegular);

               statFine.setText(R.string.noStart);
               statFine.setTypeface(awesome5proRegular);
               break;

           case "1.5":
               statTwo.setText(R.string.haftStar);
               statTwo.setTypeface(awesome5ProSolid);

               statThree.setText(R.string.noStart);
               statThree.setTypeface(awesome5proRegular);

               statFour.setText(R.string.noStart);
               statFour.setTypeface(awesome5proRegular);

               statFine.setText(R.string.noStart);
               statFine.setTypeface(awesome5proRegular);
               break;

           case "2.0":
               statThree.setText(R.string.noStart);
               statThree.setTypeface(awesome5proRegular);

               statFour.setText(R.string.noStart);
               statFour.setTypeface(awesome5proRegular);

               statFine.setText(R.string.noStart);
               statFine.setTypeface(awesome5proRegular);
               break;

           case "2.5":
               statThree.setText(R.string.haftStar);
               statThree.setTypeface(awesome5ProSolid);

               statFour.setText(R.string.noStart);
               statFour.setTypeface(awesome5proRegular);

               statFine.setText(R.string.noStart);
               statFine.setTypeface(awesome5proRegular);
               break;

           case "3.0":
               statFour.setText(R.string.noStart);
               statFour.setTypeface(awesome5proRegular);

               statFine.setText(R.string.noStart);
               statFine.setTypeface(awesome5proRegular);
               break;

           case "3.5":
               statFour.setText(R.string.haftStar);
               statFour.setTypeface(awesome5ProSolid);

               statFine.setText(R.string.noStart);
               statFine.setTypeface(awesome5proRegular);
               break;

           case "4.0":
               statFine.setText(R.string.noStart);
               statFine.setTypeface(awesome5proRegular);
               break;

           case "4.5":
               statFine.setText(R.string.haftStar);
               statFine.setTypeface(awesome5ProSolid);
               break;

           case "5.0":
               break;
       }

    }

}
