package com.gvtechcom.myshop.Fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.gvtechcom.myshop.Adapter.AdapterFeaturedCategories;
import com.gvtechcom.myshop.Adapter.AdapterFlashDeals;
import com.gvtechcom.myshop.Adapter.AdapterImageSlide;
import com.gvtechcom.myshop.Adapter.AdapterItemsYouLove;
import com.gvtechcom.myshop.Adapter.AdapterJustForYou;
import com.gvtechcom.myshop.Adapter.AdapterTopNewFeaturedStore;
import com.gvtechcom.myshop.MainActivity;
import com.gvtechcom.myshop.Model.BaseGetApiData;
import com.gvtechcom.myshop.Model.FeaturedCategories;
import com.gvtechcom.myshop.Model.FlashDealsModel;
import com.gvtechcom.myshop.Model.ImageModel;
import com.gvtechcom.myshop.Model.ItemDetailsModel;
import com.gvtechcom.myshop.Model.ItemYouLoveModel;
import com.gvtechcom.myshop.Model.JustForYou;
import com.gvtechcom.myshop.Model.Product;
import com.gvtechcom.myshop.Model.TopSelection;
import com.gvtechcom.myshop.Network.APIServer;
import com.gvtechcom.myshop.Network.RetrofitBuilder;
import com.gvtechcom.myshop.R;
import com.gvtechcom.myshop.Utils.Const;
import com.gvtechcom.myshop.Utils.MySharePreferences;
import com.gvtechcom.myshop.Utils.ValidateCallApi;
import com.gvtechcom.myshop.dialog.ToastDialog;
import com.mylibrary.ui.progress.ProgressDialogCustom;
import com.viewpagerindicator.CirclePageIndicator;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FragmentHomeContent extends Fragment {
    private View rootView;
    private MySharePreferences mySharePreferences = new MySharePreferences();
    private Boolean isLoadMore = true;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;

    private ProgressDialogCustom progressDialogCustom;
    private ToastDialog toastDialog;

    private CountDownTimer countDownTimerFlashDeals;
    private Boolean isStopCountDownTimerFlashDeals = false;

    private int page = 1;

    private FragmentManager fragmentManager;

    private APIServer apiServer;

    private BaseGetApiData obj;
    private ItemYouLoveModel.ItemYouLoveModelParser objItemYouLove;

    private ArrayList<ImageModel> imageModelArrayList;

    private ArrayList<FlashDealsModel> arrayListFlashDeals;
    private AdapterFlashDeals adapterFlashDeals;
    private List<Product> lsProductFlashDeals;

    private List<ItemYouLoveModel.Product> lsProductItemYouLove;

    private List<JustForYou> lsJustForYou;
    private AdapterJustForYou adapterJustForYou;

    private List<FeaturedCategories> lsFeaturedCategories;
    private AdapterFeaturedCategories adapterFeaturedCategories;

    private ArrayList<TopSelection> topSelectionArrayList;
    private AdapterTopNewFeaturedStore adapterTopSelection;

    private List<ItemYouLoveModel> lsItemYouLove;
    private AdapterItemsYouLove adapterItemsYouLove;

    private Handler handler;
    private Boolean isStopHandel;
    private Runnable update;

    @BindView(R.id.swipe_refresh_home_content)
    SwipeRefreshLayout swipeRefreshHomeContent;
    @BindView(R.id.card_view_flash_deals)
    CardView cardViewFlashDeals;
    @BindView(R.id.view_pager_slide)
    ViewPager viewPagerSlide;
    @BindView(R.id.recycler_view_flash_deals)
    RecyclerView recyclerViewFlashDeals;
    @BindView(R.id.recycler_view_just_for_you)
    RecyclerView recyclerJustForYou;
    @BindView(R.id.recyclerView_featured_categories)
    RecyclerView recyclerViewFeaturedCategories;
    @BindView(R.id.recycler_view_items_you_love)
    RecyclerView recyclerItemsYouLove;
    @BindView(R.id.Nested_scroll_view)
    NestedScrollView nestedScrollView;
    @BindView(R.id.FeaturedTextView)
    TextView featuredTextView;
    @BindView(R.id.txt_count_hours)
    TextView txtCountHours;
    @BindView(R.id.txt_count_minute)
    TextView txtCountMinute;
    @BindView(R.id.txt_count_seconds)
    TextView txtCountSeconds;
    @BindView(R.id.img_top_selection_one)
    ImageView imgTopSelectOne;
    @BindView(R.id.img_top_selection_two)
    ImageView imgTopSelectTwo;
    @BindView(R.id.img_new_for_you_one)
    ImageView imgNewForYouOne;
    @BindView(R.id.img_new_for_you_two)
    ImageView imgNewForYouTwo;
    @BindView(R.id.img_new_feature_brands_one)
    ImageView imgFeatureBrandsOne;
    @BindView(R.id.img_new_feature_brands_two)
    ImageView imgFeatureBrandsTwo;
    @BindView(R.id.img_stores_you_love_one)
    ImageView imgStoreYouLoveOne;
    @BindView(R.id.img_stores_you_love_two)
    ImageView imgStoreYouLoveTwo;
    @BindView(R.id.progressbar_load_api_footer)
    ProgressBar progessbar_footer;
    @BindView(R.id.txt_flash_deals_default)
    TextView txtFlashDealsDetails;
    @BindView(R.id.btn_coins_coupons)
    LinearLayout btnCoinsCoupons;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_content_home, container, false);
        ButterKnife.bind(this, rootView);
        MainActivity mainActivity;
        mainActivity = (MainActivity) getActivity();
        mainActivity.setDisplayNavigationBar(true, false, true);
        mainActivity.setHideButtonNavigation(false);
        mainActivity.setColorIconDarkMode(false, R.color.color_StatusBar);
        mainActivity.setColorNavigationBar(R.drawable.ic_back_navigation, R.drawable.bkg_search_color_white, "apple watch", R.color.color_StatusBar, "#D1D8E0");
        return rootView;
    }

    private Boolean checkstart = true;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();

        setRecyclerView();

        setItemSlideBanner();

        loadItemFlashDeals();

        setTimeFlashDeals();

        setItemJustForYou();

        setItemFeaturedCategorie();

        setItemTopNewFeaturedStore();

        getNestedScrollChange();

    }

    private void init() {
        progressDialogCustom = new ProgressDialogCustom(getActivity());
        toastDialog = new ToastDialog(getActivity());
        onListenKeyboard();
        Retrofit retrofitClient;
        retrofitClient = RetrofitBuilder.getRetrofit(Const.BASE_URL);
        apiServer = retrofitClient.create(APIServer.class);
        Gson gson = new Gson();
        String json = mySharePreferences.GetSharePrefStringObject(getActivity(), "objDataHomeContent");
        String jsonItemYouLove = mySharePreferences.GetSharePrefStringObject(getActivity(), "MyModelItemYouLove");
        obj = gson.fromJson(json, BaseGetApiData.class);
        objItemYouLove = gson.fromJson(jsonItemYouLove, ItemYouLoveModel.ItemYouLoveModelParser.class);
        fragmentManager = getFragmentManager();
        lsProductItemYouLove = new ArrayList<>();
        for (int i = 0; i <= objItemYouLove.response.products.size(); i++) {
            if (i != objItemYouLove.response.products.size()) {
                lsProductItemYouLove.add(objItemYouLove.response.products.get(i));
            } else {
                setAdapterItemsYouLove(lsProductItemYouLove);
            }
        }
        swipeRefreshHomeContent.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                page = 1;
                setItemSlideBanner();

                loadItemFlashDeals();
                setTimeFlashDeals();

                setItemJustForYou();

                setItemFeaturedCategorie();

                setItemTopNewFeaturedStore();
            }
        });

    }

    private void setRecyclerView() {
        LinearLayoutManager linearLayoutManagerViewFlashDeals = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewFlashDeals.setLayoutManager(linearLayoutManagerViewFlashDeals);

        LinearLayoutManager linearLayoutManagerJustForYou = new GridLayoutManager(getActivity(), 2);
        recyclerJustForYou.setLayoutManager(linearLayoutManagerJustForYou);

        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerItemsYouLove.setLayoutManager(linearLayoutManager);

        LinearLayoutManager linearLayoutManagerFeaturedCategories = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewFeaturedCategories.setLayoutManager(linearLayoutManagerFeaturedCategories);
    }

    private void setItemSlideBanner() {
        imageModelArrayList = new ArrayList<>();
        for (int i = 0; i <= obj.getResponse().getBanners().size(); i++) {
            if (i != obj.getResponse().getBanners().size()) {
                imageModelArrayList.add(new ImageModel(obj.getResponse().getBanners().get(i).getImage()));
            } else {
                isStopHandel = false;
                slideImageBanner();
            }
        }
    }

    private void slideImageBanner() {
        viewPagerSlide.setAdapter(new AdapterImageSlide(getActivity(), imageModelArrayList));
        viewPagerSlide.setOffscreenPageLimit(imageModelArrayList.size());
        CirclePageIndicator indicatorSlide = (CirclePageIndicator) rootView.findViewById(R.id.indicator_slide);

        indicatorSlide.setViewPager(viewPagerSlide);
        final float density = getResources().getDisplayMetrics().density;

        indicatorSlide.setRadius(3 * density);
        indicatorSlide.setStrokeWidth(0);

        NUM_PAGES = imageModelArrayList.size();
        handler = new Handler();
        update = new Runnable() {
            public void run() {
                if (isStopHandel == true) {
                    handler.removeCallbacks(update);
                } else {
                    if (currentPage == NUM_PAGES) {
                        currentPage = 0;
                    }
                    viewPagerSlide.setCurrentItem(currentPage++, true);
                }
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, 3000, 3000);

        indicatorSlide.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int pos) {
            }
        });

    }

    private void loadItemFlashDeals() {
        lsProductFlashDeals = obj.getResponse().getFlashDeals().getProducts();
        if (lsProductFlashDeals != null) {
            adapterFlashDeals = new AdapterFlashDeals(lsProductFlashDeals, getActivity());
            recyclerViewFlashDeals.setAdapter(adapterFlashDeals);
            adapterFlashDeals.setOnItemClickListener(new AdapterFlashDeals.ItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    callApiDataItemDetail(lsProductFlashDeals.get(position).getProductId());
                }
            });
        }
    }

    private void setTimeFlashDeals() {
        Calendar calendarFlashDeals = Calendar.getInstance();
        Calendar calendarCurent = Calendar.getInstance();

        calendarFlashDeals.setTimeInMillis((obj.getResponse().getFlashDeals().getEndDatetime()) * 1000L);
        int calendarFlashDealsMili = (int) calendarFlashDeals.getTimeInMillis();
        int timeDown = (int) (calendarFlashDealsMili - calendarCurent.getTimeInMillis());


        countDownTimerFlashDeals = new CountDownTimer(timeDown, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (isStopCountDownTimerFlashDeals == true) {
                    countDownTimerFlashDeals.cancel();
                } else {
                    Calendar calendarCurent1 = Calendar.getInstance();
                    calendarCurent1.getTimeInMillis();
                    int time1 = (int) calendarFlashDeals.getTimeInMillis();
                    int timeDown = (int) (time1 - calendarCurent1.getTimeInMillis());

                    String hours = String.valueOf(((timeDown / 1000) / 3600));
                    String minutes = String.valueOf((((timeDown / 1000) % 3600) / 60));
                    String seconds = String.valueOf(((timeDown / 1000) % 60));

                    if (hours.length() < 2) {
                        txtCountHours.setText("0" + hours);
                    } else {
                        txtCountHours.setText(hours);
                    }


                    if (minutes.length() < 2) {
                        txtCountMinute.setText("0" + minutes);
                    } else {
                        txtCountMinute.setText(minutes);
                    }

                    if (seconds.length() < 2) {
                        txtCountSeconds.setText("0" + seconds);
                    } else {
                        txtCountSeconds.setText(seconds);
                    }
                }
            }

            @Override
            public void onFinish() {
            }
        }.start();

    }

    private void setItemJustForYou() {
        if (obj != null) {
            lsJustForYou = obj.getResponse().getJustForYou();
            adapterJustForYou = new AdapterJustForYou(lsJustForYou, getActivity());
            adapterJustForYou.setItemClickListener(new AdapterJustForYou.ItemClickListener() {
                @Override
                public void onClickListener(String idProduct) {
                    callApiDataItemDetail(idProduct);
                }
            });
            recyclerJustForYou.setAdapter(adapterJustForYou);
        }
    }

    private void setItemFeaturedCategorie() {
        if (obj != null) {
            lsFeaturedCategories = obj.getResponse().getFeature_categories();
            adapterFeaturedCategories = new AdapterFeaturedCategories(getActivity(), lsFeaturedCategories);
            adapterFeaturedCategories.setItemClickListenr(new AdapterFeaturedCategories.ItemClickListener() {
                @Override
                public void itemClick(String idProduct) {
                    callApiDataItemDetail(idProduct);
                }
            });
            recyclerViewFeaturedCategories.setAdapter(adapterFeaturedCategories);
        }
    }

    private void setItemTopNewFeaturedStore() {
        if (obj.getResponse().getTopSelections().size() > 1) {
            setGlideImage(obj.getResponse().getTopSelections().get(obj.getResponse().getTopSelections().size() - 2).product_image, imgTopSelectOne);
            setGlideImage(obj.getResponse().getTopSelections().get(obj.getResponse().getTopSelections().size() - 1).product_image, imgTopSelectTwo);
            setClickCategori(imgTopSelectOne, obj.getResponse().getTopSelections().get(obj.getResponse().getTopSelections().size() - 2).product_id);
            setClickCategori(imgTopSelectTwo, obj.getResponse().getTopSelections().get(obj.getResponse().getTopSelections().size() - 1).product_id);

        }

        if (obj.getResponse().getNewsForYou().size() > 1) {
            setGlideImage(obj.getResponse().getNewsForYou().get(obj.getResponse().getNewsForYou().size() - 2).product_image, imgNewForYouOne);
            setGlideImage(obj.getResponse().getNewsForYou().get(obj.getResponse().getNewsForYou().size() - 1).product_image, imgNewForYouTwo);
            setClickCategori(imgNewForYouOne, obj.getResponse().getNewsForYou().get(obj.getResponse().getNewsForYou().size() - 2).product_id);
            setClickCategori(imgNewForYouTwo, obj.getResponse().getNewsForYou().get(obj.getResponse().getNewsForYou().size() - 1).product_id);

        }

        if (obj.getResponse().getFeatureBrands().size() > 1) {
            setGlideImage(obj.getResponse().getFeatureBrands().get(obj.getResponse().getFeatureBrands().size() - 2).image, imgFeatureBrandsOne);
            setGlideImage(obj.getResponse().getFeatureBrands().get(obj.getResponse().getFeatureBrands().size() - 1).image, imgFeatureBrandsTwo);
            setClickCategori(imgFeatureBrandsOne, obj.getResponse().getFeatureBrands().get(obj.getResponse().getFeatureBrands().size() - 2).brand_id);
            setClickCategori(imgFeatureBrandsTwo, obj.getResponse().getFeatureBrands().get(obj.getResponse().getFeatureBrands().size() - 1).brand_id);

        }

        if (obj.getResponse().getStoresYouLove().size() > 1) {
            setGlideImage(obj.getResponse().getStoresYouLove().get(obj.getResponse().getStoresYouLove().size() - 2).image, imgStoreYouLoveOne);
            setGlideImage(obj.getResponse().getStoresYouLove().get(obj.getResponse().getStoresYouLove().size() - 1).image, imgStoreYouLoveTwo);


        }

        swipeRefreshHomeContent.setRefreshing(false);
    }

    private void setClickCategori(View v, String idProduct) {
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callApiDataItemDetail(idProduct);
            }
        });
    }

    private void setAdapterItemsYouLove(List<ItemYouLoveModel.Product> lsItemYouLove) {
        if (adapterItemsYouLove == null) {
            adapterItemsYouLove = new AdapterItemsYouLove(getActivity(), lsItemYouLove);
            recyclerItemsYouLove.setAdapter(adapterItemsYouLove);
            adapterItemsYouLove.setOnItemClickListener(new AdapterItemsYouLove.ItemClickListener() {
                @Override
                public void onClickListener(String productId) {
                    callApiDataItemDetail(productId);
                }
            });
        } else {
            adapterItemsYouLove.UpdateAdapter(lsItemYouLove);
            recyclerItemsYouLove.setAdapter(adapterItemsYouLove);
        }
    }

    private void getNestedScrollChange() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            nestedScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    int nestedScrollHight = (nestedScrollView.getChildAt(0).getHeight() - nestedScrollView.getHeight());
                    if (isLoadMore == true && scrollY == nestedScrollHight) {
                        isLoadMore = false;
                        loadmore();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                isLoadMore = true;
                            }
                        }, 1000);
                    }
                }
            });
        }
    }

    private void loadmore() {
        Animation animation_side_up = AnimationUtils.loadAnimation(getActivity(), R.anim.animation_slide_up_progressbar);
        Animation animation_side_down = AnimationUtils.loadAnimation(getActivity(), R.anim.animation_slide_down_progressbar);
        progessbar_footer.setVisibility(View.VISIBLE);
        progessbar_footer.setAnimation(animation_side_up);
        Call<ItemYouLoveModel.ItemYouLoveModelParser> callItemYouLove = apiServer.GetItemYouLove(page, 8);
        callItemYouLove.enqueue(new Callback<ItemYouLoveModel.ItemYouLoveModelParser>() {
            @Override
            public void onResponse(Call<ItemYouLoveModel.ItemYouLoveModelParser> call, Response<ItemYouLoveModel.ItemYouLoveModelParser> response) {
                if (response.body().code != 200) {
                    Toast.makeText(getActivity(), response.body().message, Toast.LENGTH_SHORT).show();
                    progessbar_footer.setAnimation(animation_side_down);
                    progessbar_footer.setVisibility(View.GONE);
                } else {
                    for (int i = 0; i <= response.body().response.products.size(); i++) {
                        if (i != response.body().response.products.size()) {
                            lsProductItemYouLove.add(response.body().response.products.get(i));
                        } else {
                            setAdapterItemsYouLove(lsProductItemYouLove);
                            page = page + 1;
                            progessbar_footer.setAnimation(animation_side_down);
                            progessbar_footer.setVisibility(View.GONE);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ItemYouLoveModel.ItemYouLoveModelParser> call, Throwable t) {
                progessbar_footer.setVisibility(View.GONE);
            }
        });
    }

    private void onListenKeyboard() {
        MainActivity mainActivity = (MainActivity) getActivity();
        KeyboardVisibilityEvent.setEventListener(
                getActivity(),
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        if (isOpen == true) {
                            mainActivity.setHideButtonNavigation(true);
                        } else {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mainActivity.setHideButtonNavigation(false);
                                }
                            }, 100);
                        }
                    }
                });
    }

    private void setDataItemDetails(String jsonData) {
        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragmentItemDetails = new FragmentItemDetail();
        Bundle bundle = new Bundle();
        bundle.putString("dataJson", jsonData);
        bundle.putString("fromToFragment", "homeContent");
        progressDialogCustom.onHide();
        fragmentItemDetails.setArguments(bundle);
        fragmentTransaction.add(R.id.content_home_frame_layout, fragmentItemDetails);
        fragmentTransaction.addToBackStack("home");
        fragmentTransaction.commit();
    }

    private void callApiDataItemDetail(String idProduct) {
        progressDialogCustom.onShow(false, "");
        Call<ItemDetailsModel.ItemDetailsModelParser> callApi = apiServer.GetApiItemDetails(idProduct);
        callApi.enqueue(new Callback<ItemDetailsModel.ItemDetailsModelParser>() {
            @Override
            public void onResponse(Call<ItemDetailsModel.ItemDetailsModelParser> call, Response<ItemDetailsModel.ItemDetailsModelParser> response) {
                progressDialogCustom.onHide();
                if (ValidateCallApi.ValidateAip(getActivity(), response.body().code, response.body().message)) {
                    if (response.body().response != null) {
                        Gson gson = new Gson();
                        String jsonData = gson.toJson(response.body());
                        setDataItemDetails(jsonData);
                    }
                }
            }

            @Override
            public void onFailure(Call<ItemDetailsModel.ItemDetailsModelParser> call, Throwable t) {
                progressDialogCustom.onHide();
            }
        });
    }

    private void setGlideImage(String url, View view) {
        Glide.with(getActivity())
                .load(url)
                .placeholder(R.drawable.banner_image_slide)
                .error(R.drawable.banner_image_slide)
                .into((ImageView) view);
    }

    @OnClick({R.id.btn_browse_categories, R.id.txt_flash_deals_default, R.id.btn_flash_deals})
    void OnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_browse_categories:
                fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_home_frame_layout, new FragmentBrowseCategories());
                fragmentTransaction.addToBackStack("home");
                fragmentTransaction.commit();
                break;

            case R.id.txt_flash_deals_default:

            case R.id.btn_flash_deals:
                fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransactionFlashDeal = fragmentManager.beginTransaction();
                fragmentTransactionFlashDeal.replace(R.id.content_home_frame_layout, new FragmentFlashDetails());
                fragmentTransactionFlashDeal.addToBackStack("home");
                fragmentTransactionFlashDeal.commit();
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isStopHandel = true;
        isStopCountDownTimerFlashDeals = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        isStopCountDownTimerFlashDeals = false;
        setTimeFlashDeals();
    }
}
