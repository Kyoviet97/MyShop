package com.gvtechcom.myshop.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.gvtechcom.myshop.Adapter.AdapterRelatesProduct;
import com.gvtechcom.myshop.MainActivity;
import com.gvtechcom.myshop.Model.ItemDetailsModel;
import com.gvtechcom.myshop.Network.APIServer;
import com.gvtechcom.myshop.Network.RetrofitBuilder;
import com.gvtechcom.myshop.R;
import com.gvtechcom.myshop.Utils.Const;
import com.mylibrary.ui.progress.ProgressDialogCustom;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FragmentItemDetail extends Fragment {
    private View rootView;
    private APIServer apiServer;
    private MainActivity mainActivity;
    private AdapterRelatesProduct adapterRelatesProduct;
    private String jsonData;
    private ProgressDialogCustom progressLoading;
    private Calendar calendarCurrent;
    private static Integer soldQuantity = 1;
    private CountDownTimer countDownTimerFlashDealsDetail;
    private Boolean stopCountDownTimerFlashDealsDetail = false;

    //TextView
    @BindView(R.id.txt_item_detail_description)
    TextView txtItemDetailDescription;
    @BindView(R.id.txt_item_detail_sold)
    TextView txtItemDetailSold;
    @BindView(R.id.txt_item_detail_like)
    TextView txtItemDetailLike;
    @BindView(R.id.txt_item_detail_percent_sale)
    TextView txtItemDetailPercentSale;
    @BindView(R.id.txt_item_detail_store_name)
    TextView storeName;
    @BindView(R.id.txt_item_detail_feedback_store)
    TextView storeFeedback;
    @BindView(R.id.txt_item_detail_items_store)
    TextView storeItem;
    @BindView(R.id.txt_item_detail_sold_store)
    TextView storeSold;
    @BindView(R.id.main_flash_time_in_item_detail)
    LinearLayout mainFlashTimeInItemDetail;
    @BindView(R.id.txt_vote_star)
    TextView txtvoteStar;

    // Quantily
    @BindView(R.id.img_lost_sold_product_to_buy)
    ImageView imgLostSoldProductToBuy;
    @BindView(R.id.txt_sold_product_to_buy)
    TextView txtSoilProductToBuy;
    @BindView(R.id.img_add_sold_product_to_buy)
    ImageView imgAddSolfProductToBuy;

    //Time Flash
    @BindView(R.id.txt_count_hours_item_detail)
    TextView txtCountHoursItemDetail;
    @BindView(R.id.txt_count_minute_item_detail)
    TextView txtCountMinuteItemDetail;
    @BindView(R.id.txt_count_seconds_item_detail)
    TextView txtCountSecondsItemDetail;


    //Use Review
    @BindView(R.id.txt_user_review)
    TextView txtUserReview;
    @BindView(R.id.txt_date_rating)
    TextView txtDateReview;
    @BindView(R.id.txt_content_review)
    TextView txtContentReview;
    @BindView(R.id.img_start_use_rating)
    ImageView imgStartUseRating;

    @BindView(R.id.img_vote_start_item_detail)
    ImageView imgVoteStar;

    //RecyclerView
    @BindView(R.id.recycler_related_product)
    RecyclerView recyclerRelatedProduct;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_item_detail, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        callApiItemDataDetails();
        setSoldQuntity();
    }

    private void init() {
        mainActivity = (MainActivity) getActivity();
        mainActivity.setDisplayNavigationBar(false, false, false);
        mainActivity.setHideButtonNavigation(true);
        mainActivity.setColorStatusTran(true);
        progressLoading = new ProgressDialogCustom(getActivity());
        setRetroFit();
        setViewRecyclerView();
        calendarCurrent = Calendar.getInstance();
    }

    private void setRetroFit() {
        Retrofit retrofit;
        retrofit = RetrofitBuilder.getRetrofit(Const.BASE_URL);
        apiServer = retrofit.create(APIServer.class);
    }

    private void setViewRecyclerView() {
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerRelatedProduct.setLayoutManager(linearLayoutManager);
    }

    private void setAdapterRelatesProduct(List<ItemDetailsModel.RelatesProduct> lsRelatesProduct) {
        adapterRelatesProduct = new AdapterRelatesProduct(getActivity(), lsRelatesProduct);
        if (adapterRelatesProduct != null && lsRelatesProduct != null) {
            recyclerRelatedProduct.setAdapter(adapterRelatesProduct);
        }
    }

    private void callApiItemDataDetails() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            Gson gson = new Gson();
            this.jsonData = bundle.getString("idProduct");
            ItemDetailsModel.ItemDetailsModelParser dataApiItemDetail = gson.fromJson(jsonData, ItemDetailsModel.ItemDetailsModelParser.class);
            if (dataApiItemDetail != null) {
                txtItemDetailDescription.setText(dataApiItemDetail.response.description);
                txtItemDetailSold.setText(dataApiItemDetail.response.sold + " orders");
                txtItemDetailLike.setText(dataApiItemDetail.response.like + "");
                txtItemDetailPercentSale.setText("$" + dataApiItemDetail.response.percent_sale);
                setAdapterRelatesProduct(dataApiItemDetail.response.relatesproduct);
                storeName.setText(dataApiItemDetail.response.store.store_name);
                storeFeedback.setText(dataApiItemDetail.response.store.feedback + "%");
                storeItem.setText(dataApiItemDetail.response.store.items);
                storeSold.setText(dataApiItemDetail.response.store.sold);
                timeFlashDeals(dataApiItemDetail.response.end_datetime);
                Double voteStar = Double.parseDouble(dataApiItemDetail.response.review.rating);
                setStartNumber(voteStar);
                setUserRating(dataApiItemDetail.response.review.user_review, dataApiItemDetail.response.review.date_rating, dataApiItemDetail.response.review.content_review, 1.2);
                progressLoading.onHide();
            } else {
            }
        }
    }

    private void timeFlashDeals(String time) {
        int timeFlash = Integer.parseInt(time);
        if (timeFlash != 0) {
            mainFlashTimeInItemDetail.setVisibility(View.VISIBLE);
            Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.animation_slide_down_flash_one_secont);
            mainFlashTimeInItemDetail.setAnimation(animation);
        } else {
            mainFlashTimeInItemDetail.setVisibility(View.GONE);
        }
    }

    private void setStartNumber(Double vote) {
        if (vote > 4.5) {
            txtvoteStar.setText("5");
            imgVoteStar.setImageResource(R.drawable.vote_fine_star);
        } else {
            if (vote > 4) {
                txtvoteStar.setText("4.5");
                imgVoteStar.setImageResource(R.drawable.vote_four_half_star);
            } else {
                if (vote >= 3.5) {
                    txtvoteStar.setText("4");
                    imgVoteStar.setImageResource(R.drawable.vote_four_star);
                } else {
                    if (vote > 3) {
                        txtvoteStar.setText("3.5");
                        imgVoteStar.setImageResource(R.drawable.vote_three_half_star);
                    } else {
                        if (vote > 2.5) {
                            txtvoteStar.setText("3");
                            imgVoteStar.setImageResource(R.drawable.vote_three_star);
                        } else {
                            if (vote > 2) {
                                txtvoteStar.setText("2.5");
                                imgVoteStar.setImageResource(R.drawable.vote_two_half_star);
                            } else {
                                if (vote > 1.5) {
                                    txtvoteStar.setText("2");
                                    imgVoteStar.setImageResource(R.drawable.vote_two_star);
                                } else {
                                    if (vote > 1) {
                                        txtvoteStar.setText("1.5");
                                        imgVoteStar.setImageResource(R.drawable.vote_one_half_star);
                                    } else {
                                        txtvoteStar.setText("1");
                                        imgVoteStar.setImageResource(R.drawable.vote_one_star);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void setSoldQuntity() {
        imgLostSoldProductToBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (soldQuantity > 1) {
                    soldQuantity--;
                    txtSoilProductToBuy.setText(soldQuantity.toString());
                    imgLostSoldProductToBuy.setImageResource(R.drawable.ic_difference_item_detail_select);
                    if (soldQuantity == 1) {
                        imgLostSoldProductToBuy.setImageResource(R.drawable.ic_difference_item_detail);
                        imgLostSoldProductToBuy.setClickable(false);
                    }
                }
            }
        });

        imgAddSolfProductToBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soldQuantity++;
                txtSoilProductToBuy.setText(soldQuantity.toString());
                imgLostSoldProductToBuy.setImageResource(R.drawable.ic_difference_item_detail_select);
                imgLostSoldProductToBuy.setClickable(true);

            }
        });
    }

    private void setUserRating(String name, String date, String content, Double star) {
        txtUserReview.setText(name);
        txtDateReview.setText(date);
        txtContentReview.setText(content);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        stopCountDownTimerFlashDealsDetail = true;

    }
}
