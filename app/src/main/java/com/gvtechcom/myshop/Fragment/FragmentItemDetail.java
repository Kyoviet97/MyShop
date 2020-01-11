package com.gvtechcom.myshop.Fragment;

import android.os.Build;
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
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.gvtechcom.myshop.Adapter.AdapterProductChildren;
import com.gvtechcom.myshop.Adapter.AdapterRelatesProduct;
import com.gvtechcom.myshop.MainActivity;
import com.gvtechcom.myshop.Model.ItemDetailsModel;
import com.gvtechcom.myshop.Network.APIServer;
import com.gvtechcom.myshop.Network.RetrofitBuilder;
import com.gvtechcom.myshop.R;
import com.gvtechcom.myshop.Utils.Const;
import com.gvtechcom.myshop.Utils.QuantityView;
import com.gvtechcom.myshop.Utils.ShowProgressBar;
import com.gvtechcom.myshop.Utils.StarViewVote;
import com.gvtechcom.myshop.Utils.ValidateCallApi;
import com.gvtechcom.myshop.dialog.ToastDialog;
import com.mylibrary.ui.progress.ProgressDialogCustom;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    private static Integer soldQuantity = 1;
    private String fromToFragment = "";
    private ToastDialog toastDialog;

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
    @BindView(R.id.name_product_ietm_detail)
    TextView nameProductIetmDetail;

    @BindView(R.id.scroll_item_detail)
    NestedScrollView nestedScrollViewItemDetail;

    //Time Flash
    @BindView(R.id.txt_count_hours_item_detail)
    TextView txtCountHoursItemDetail;
    @BindView(R.id.txt_count_minute_item_detail)
    TextView txtCountMinuteItemDetail;
    @BindView(R.id.txt_count_seconds_item_detail)
    TextView txtCountSecondsItemDetail;

    //Quantity
    @BindView(R.id.quantity_view_home_content)
    QuantityView quantityViewHome;


    //Use Review
    @BindView(R.id.txt_user_review)
    TextView txtUserReview;
    @BindView(R.id.txt_date_rating)
    TextView txtDateReview;
    @BindView(R.id.txt_content_review)
    TextView txtContentReview;
    @BindView(R.id.img_start_use_rating)
    ImageView imgStartUseRating;

    @BindView(R.id.set_star_view)
    StarViewVote setStarView;

    //RecyclerView
    @BindView(R.id.recycler_related_product)
    RecyclerView recyclerRelatedProduct;
    @BindView(R.id.recycler_product_children_item_detail)
    RecyclerView recyclerProductChildrenItemDetail;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_item_detail, container, false);
        ButterKnife.bind(this, rootView);
        mainActivity = (MainActivity) getActivity();
        mainActivity.setDisplayNavigationBar(false, false, false);
        mainActivity.setHideButtonNavigation(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mainActivity.setColorStatusTran(true);
        }
        progressLoading = new ProgressDialogCustom(getActivity());
        toastDialog = new ToastDialog(getActivity());
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            init();
        }
    }

    private void init() {
        setRetroFit();
        setViewRecyclerView();
        checkData();
    }

    private void setRetroFit() {
        Retrofit retrofit;
        retrofit = RetrofitBuilder.getRetrofit(Const.BASE_URL);
        apiServer = retrofit.create(APIServer.class);
    }

    private void setViewRecyclerView() {
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerRelatedProduct.setLayoutManager(linearLayoutManager);
        LinearLayoutManager layoutManagerRecyclerProductChildren = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        recyclerProductChildrenItemDetail.setLayoutManager(layoutManagerRecyclerProductChildren);
    }

    private void setAdapterRelatesProduct(List<ItemDetailsModel.RelatesProduct> lsRelatesProduct) {
        adapterRelatesProduct = new AdapterRelatesProduct(getActivity(), lsRelatesProduct);
        if (lsRelatesProduct != null) {
            recyclerRelatedProduct.setAdapter(adapterRelatesProduct);
            adapterRelatesProduct.setOnItemClickListener(new AdapterRelatesProduct.SetOnItemClickListener() {
                @Override
                public void onItemClick(String idProduct) {
                    callNewApiItemDetail(idProduct);
                }
            });

        }
    }

    private void setDataRecyclerProductChildren(List<ItemDetailsModel.Product> lsProductChildren) {
        AdapterProductChildren adapterProductChildren = new AdapterProductChildren(getActivity(), lsProductChildren);
        recyclerProductChildrenItemDetail.setAdapter(adapterProductChildren);
        adapterProductChildren.getDataListChildren(new AdapterProductChildren.SendListChildren() {
            @Override
            public void dataSend(List<ItemDetailsModel.Children> lsProductChildren, int position) {
                System.out.println("================>" + lsProductChildren.size());
                for (ItemDetailsModel.Children children : lsProductChildren){
                    System.out.println("====================>" + children._id);
                }
            }
        });
    }

    private void checkData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            this.fromToFragment = bundle.getString("fromToFragment", "");
            Gson gson = new Gson();
            this.jsonData = bundle.getString("dataJson");
            ItemDetailsModel.ItemDetailsModelParser dataApiItemDetail = gson.fromJson(jsonData, ItemDetailsModel.ItemDetailsModelParser.class);
            setDataItemDataDetails(dataApiItemDetail);
            setDataRecyclerProductChildren(dataApiItemDetail.response.product);
        }
    }

    private void setDataItemDataDetails(ItemDetailsModel.ItemDetailsModelParser dataApiItemDetail) {
        if (dataApiItemDetail != null) {
            txtItemDetailDescription.setText(dataApiItemDetail.response.description);
            nameProductIetmDetail.setText(dataApiItemDetail.response.name);
            txtItemDetailSold.setText(dataApiItemDetail.response.sold + " orders");
            quantityViewHome.setValue(dataApiItemDetail.response.sold);
            if (quantityViewHome != null){
                quantityViewHome.setClickLostAddItem(dataApiItemDetail.response.is_specie);
            }
            callFragmentProductOption(dataApiItemDetail.response);

            txtItemDetailLike.setText(dataApiItemDetail.response.like + "");
            txtItemDetailPercentSale.setText("$" + dataApiItemDetail.response.percent_sale);
            storeName.setText(dataApiItemDetail.response.store.store_name);
            storeFeedback.setText(dataApiItemDetail.response.store.feedback + "%");
            storeItem.setText(dataApiItemDetail.response.store.items);
            storeSold.setText(dataApiItemDetail.response.store.sold);
            timeFlashDeals(dataApiItemDetail.response.end_datetime);
            Double voteStar = Double.parseDouble(dataApiItemDetail.response.review.rating);
            String stVote = String.valueOf(voteStar);
            setStarView.setStartNumber(stVote);
            txtvoteStar.setText(stVote);
            setUserRating(dataApiItemDetail.response.review.user_review, dataApiItemDetail.response.review.date_rating, dataApiItemDetail.response.review.content_review, 1.2);
            setDataRecyclerProductChildren(dataApiItemDetail.response.product);
            setAdapterRelatesProduct(dataApiItemDetail.response.relatesproduct);
            progressLoading.onHide();
        }
    }

    private void callNewApiItemDetail(String idProduct) {
        ShowProgressBar.showProgress(getActivity());
        Call<ItemDetailsModel.ItemDetailsModelParser> call = apiServer.GetApiItemDetails(idProduct);
        call.enqueue(new Callback<ItemDetailsModel.ItemDetailsModelParser>() {
            @Override
            public void onResponse(Call<ItemDetailsModel.ItemDetailsModelParser> call, Response<ItemDetailsModel.ItemDetailsModelParser> response) {
                ShowProgressBar.hideProgress();
                if (ValidateCallApi.ValidateAip(getActivity(), response.body().code, response.body().message)) {
                    ItemDetailsModel.ItemDetailsModelParser dataApiItemDetail = response.body();
                    if (dataApiItemDetail != null) {
                        setDataItemDataDetails(dataApiItemDetail);
                        nestedScrollViewItemDetail.scrollTo(0, 0);
                    }
                }
            }

            @Override
            public void onFailure(Call<ItemDetailsModel.ItemDetailsModelParser> call, Throwable t) {
                ShowProgressBar.hideProgress();
            }
        });
    }

    private void timeFlashDeals(String time) {
        int timeFlash = Integer.parseInt(time);
        if (timeFlash != 0) {
            mainFlashTimeInItemDetail.setVisibility(View.VISIBLE);
            Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.animation_slide_down_flash_one_secont);
            mainFlashTimeInItemDetail.setAnimation(animation);

            Calendar calendarFlashDeals = Calendar.getInstance();
            Calendar calendarCurent = Calendar.getInstance();

            calendarFlashDeals.setTimeInMillis((Integer.parseInt(time)) * 1000L);
            int calendarFlashDealsMili = (int) calendarFlashDeals.getTimeInMillis();
            int timeDown = (int) (calendarFlashDealsMili - calendarCurent.getTimeInMillis());

            CountDownTimer countDownTimer = new CountDownTimer(timeDown, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    Calendar calendarCurent1 = Calendar.getInstance();
                    calendarCurent1.getTimeInMillis();
                    int time1 = (int) calendarFlashDeals.getTimeInMillis();
                    int timeDown = (int) (time1 - calendarCurent1.getTimeInMillis());

                    String hours = String.valueOf(((timeDown / 1000) / 3600));
                    String minutes = String.valueOf((((timeDown / 1000) % 3600) / 60));
                    String seconds = String.valueOf(((timeDown / 1000) % 60));

                    if (hours.length() < 2) {
                        txtCountHoursItemDetail.setText("0" + hours);
                    } else {
                        txtCountHoursItemDetail.setText(hours);
                    }

                    if (minutes.length() < 2) {
                        txtCountMinuteItemDetail.setText("0" + minutes);
                    } else {
                        txtCountMinuteItemDetail.setText(minutes);
                    }
                    if (seconds.length() < 2) {
                        txtCountSecondsItemDetail.setText("0" + seconds);
                    } else {
                        txtCountSecondsItemDetail.setText(seconds);
                    }
                }

                @Override
                public void onFinish() {

                }
            }.start();


        } else {
            mainFlashTimeInItemDetail.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.btn_buy_now_item_details})
    void onClick(View view){
        switch (view.getId()){
            case R.id.btn_buy_now_item_details:
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.frame_layout_home_manager, new FragmentShippingMethod());
                fragmentTransaction.addToBackStack("item_detail");
                fragmentTransaction.commit();
                break;
        }
    }

    private void callFragmentProductOption(ItemDetailsModel lsDataProduct){
        quantityViewHome.setQuantityOnClick(new QuantityView.OnClickQuantity() {
            @Override
            public void onClick() {
                Gson gson = new Gson();
                String dataStringProduct = gson.toJson(lsDataProduct);
                Bundle bundle = new Bundle();
                bundle.putString("lsDataProduct", dataStringProduct);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                FragmentProductOptions productOptions = new FragmentProductOptions();
                productOptions.setArguments(bundle);
                fragmentTransaction.add(R.id.frame_layout_home_manager, productOptions);
                fragmentTransaction.addToBackStack("item_detail");
                fragmentTransaction.commit();
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mainActivity.setColorStatusTran(true);
        }
        mainActivity.setHideButtonNavigation(false);

        switch (fromToFragment) {
            case "homeViewCategory":
                mainActivity.setColorIconDarkMode(true, R.color.white);
                mainActivity.setColorNavigationBar(R.drawable.ic_back_navigation, R.drawable.bkg_search_color_gray, "apple watch", R.color.white, "#E8A7A7A7");
                mainActivity.setDisplayNavigationBar(true, true, false);
                break;

            case "homeContent":
                mainActivity.setColorIconDarkMode(false, R.color.color_StatusBar);
                mainActivity.setColorNavigationBar(R.drawable.ic_back_navigation, R.drawable.bkg_search_color_white, "apple watch", R.color.color_StatusBar, "#E8A7A7A7");
                mainActivity.setDisplayNavigationBar(true, false, true);
                break;

            case "viewBrand":
                mainActivity.setColorIconDarkMode(false, R.color.color_starttus_bar_brown);
                mainActivity.setColorNavigationBar(R.drawable.ic_back_navigation, R.drawable.bkg_search_color_brown, "apple watch", R.color.color_starttus_bar_brown, "#E8A7A7A7");
                mainActivity.setDisplayNavigationBar(true, false, true);
                break;

            case "flashDeals":
                mainActivity.setColorIconDarkMode(false, R.color.color_StatusBar);
                mainActivity.setColorNavigationBar(R.drawable.ic_back_navigation, R.drawable.bkg_search_color_orange, "apple watch", R.color.color_StatusBar, "#FCC39D");
                mainActivity.setDisplayNavigationBar(true, true, false);
                break;
        }
    }

}
