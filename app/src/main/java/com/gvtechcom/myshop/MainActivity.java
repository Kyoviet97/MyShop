package com.gvtechcom.myshop;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.gvtechcom.myshop.Account.AccountActivity;
import com.gvtechcom.myshop.Adapter.ViewPagerAdapterTabMessages;
import com.gvtechcom.myshop.Fragment.FragmentAccount;
import com.gvtechcom.myshop.Fragment.FragmentHomeContent;
import com.gvtechcom.myshop.Fragment.FragmentMessages;
import com.gvtechcom.myshop.Fragment.FragmentOrders;
import com.gvtechcom.myshop.Fragment.FragmentSearch;
import com.gvtechcom.myshop.Fragment.FragmentUpdate;
import com.gvtechcom.myshop.Fragment.FragmentViewBrand;
import com.gvtechcom.myshop.Interface.ClickActionSearch;
import com.gvtechcom.myshop.Interface.KeywordSearch;
import com.mylibrary.ui.statusbar.StatusBarCompat;
import com.mylibrary.ui.statusbar.SystemBarTintManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private int check_fragment;
    private KeywordSearch keywordSearchl;
    private ClickActionSearch clickActionSearch;

    @BindView(R.id.tablayout_home)
    TabLayout tableLayoutHome;
    @BindView(R.id.view_pager_home)
    ViewPager viewPagerHome;
    @BindView(R.id.btn_home)
    Button btn_home;
    @BindView(R.id.btn_message)
    Button btn_message;
    @BindView(R.id.btn_oders)
    Button btn_orders;
    @BindView(R.id.btn_update)
    Button btn_update;
    @BindView(R.id.btn_account)
    Button btn_account;
    @BindView(R.id.navication_top)
    RelativeLayout navigationTop;
    @BindView(R.id.img_btn_back_navigation)
    ImageView imgBtnBackNavigation;
    @BindView(R.id.searchView_Navigation)
    EditText searchViewNavigation;
    @BindView(R.id.img_shopping_cart)
    ImageView imgShoppingCart;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("CommitTransaction")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
        fragmentManager = getSupportFragmentManager();
        Fragment_Home();
        Get_Extra();
        setEditSearchNavigation(false);
        setActionSearchClick();
        this.check_fragment = 0;

        addControl();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void ClickButton(View view) {
        switch (view.getId()) {
            case R.id.btn_home:
                if (check_fragment == 1) {
                } else {
                    Fragment_Home();
                }
                break;
            case R.id.btn_message:
                if (check_fragment == 2) {
                } else {
                    Fragment_Messages();
                }
                break;
            case R.id.btn_oders:
                if (check_fragment == 3) {
                } else {
                    Fragment_Orders();
                }
                break;
            case R.id.btn_update:
                if (check_fragment == 4) {
                } else {
                    Fragment_Update();
                }
                break;
            case R.id.btn_account:
                if (check_fragment == 5) {
                } else {
                    Fragment_Account();
                }
                break;

            case R.id.img_btn_back_navigation:
                fragmentManager.popBackStack();
                break;
        }
    }

    public void navigationSelected(String button) {
        switch (button) {
            case "home":
                btn_home.setBackgroundResource(R.drawable.ic_btn_home_selected);
                btn_message.setBackgroundResource(R.drawable.ic_btn_mess);
                btn_orders.setBackgroundResource(R.drawable.ic_btn_orders);
                btn_update.setBackgroundResource(R.drawable.ic_btn_update);
                btn_account.setBackgroundResource(R.drawable.ic_btn_accound);
                break;

            case "messager":
                btn_home.setBackgroundResource(R.drawable.ic_btn_home);
                btn_message.setBackgroundResource(R.drawable.ic_btn_mess_selected);
                btn_orders.setBackgroundResource(R.drawable.ic_btn_orders);
                btn_update.setBackgroundResource(R.drawable.ic_btn_update);
                btn_account.setBackgroundResource(R.drawable.ic_btn_accound);
                break;

            case "orders":
                btn_home.setBackgroundResource(R.drawable.ic_btn_home);
                btn_message.setBackgroundResource(R.drawable.ic_btn_mess);
                btn_orders.setBackgroundResource(R.drawable.ic_btn_orders_selected);
                btn_update.setBackgroundResource(R.drawable.ic_btn_update);
                btn_account.setBackgroundResource(R.drawable.ic_btn_accound);
                break;

            case "update":
                btn_home.setBackgroundResource(R.drawable.ic_btn_home);
                btn_message.setBackgroundResource(R.drawable.ic_btn_mess);
                btn_orders.setBackgroundResource(R.drawable.ic_btn_orders);
                btn_update.setBackgroundResource(R.drawable.ic_btn_update_selected);
                btn_account.setBackgroundResource(R.drawable.ic_btn_accound);
                break;

            case "account":
                btn_home.setBackgroundResource(R.drawable.ic_btn_home);
                btn_message.setBackgroundResource(R.drawable.ic_btn_mess);
                btn_orders.setBackgroundResource(R.drawable.ic_btn_orders);
                btn_update.setBackgroundResource(R.drawable.ic_btn_update);
                btn_account.setBackgroundResource(R.drawable.ic_btn_account_selected);
                break;
        }
    }

    private void init() {
        imgBtnBackNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.popBackStack();
            }
        });

    }

    @SuppressLint("ClickableViewAccessibility")
    private void addControl() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        ViewPagerAdapterTabMessages adapter = new ViewPagerAdapterTabMessages(fragmentManager);
        viewPagerHome.setAdapter(adapter);
        tableLayoutHome.setupWithViewPager(viewPagerHome);
        viewPagerHome.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tableLayoutHome));
        tableLayoutHome.setTabsFromPagerAdapter(adapter);//deprecated
        tableLayoutHome.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPagerHome));


        tableLayoutHome.getTabAt(0).setIcon(R.drawable.bkg_box_orders_messages);
        tableLayoutHome.getTabAt(1).setIcon(R.drawable.bkg_store_messages);
        tableLayoutHome.getTabAt(2).setIcon(R.drawable.bkg_user_headset_messages);
        tableLayoutHome.getTabAt(3).setIcon(R.drawable.bkg_store_messages);
        tableLayoutHome.getTabAt(4).setIcon(R.drawable.bkg_user_headset_messages);

    }

    public void setupStatusBarView(View view) {
        SystemBarTintManager systemBarTintManager = new SystemBarTintManager(this);
        SystemBarTintManager.SystemBarConfig mConfig = systemBarTintManager.getConfig();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, mConfig.getStatusBarHeight());
        view.setLayoutParams(params);
    }

    public void setColorIconDarkMode(Boolean shouldChangeStatusBarTintToDark, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = getWindow().getDecorView();
            if (shouldChangeStatusBarTintToDark) {
                decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), color));
            } else {
                getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), color));
                decor.setSystemUiVisibility(0);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void Fragment_Home() {

        check_fragment = 1;
        navigationSelected("home");
        androidx.fragment.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_home_frame_layout, new FragmentHomeContent(), "frag_home");
        fragmentTransaction.commit();
    }

    private void Fragment_Messages() {
        check_fragment = 2;
        navigationSelected("messager");
        androidx.fragment.app.FragmentManager fragmentManagerx = getSupportFragmentManager();
        androidx.fragment.app.FragmentTransaction fragmentTransactionx = fragmentManagerx.beginTransaction();
        fragmentTransactionx.replace(R.id.content_home_frame_layout, new FragmentMessages(), "frag_messages");
        fragmentTransactionx.commit();
    }

    private void Fragment_Orders() {
        check_fragment = 3;
        navigationSelected("orders");
        androidx.fragment.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_home_frame_layout, new FragmentOrders(), "frag_orders");
        fragmentTransaction.commit();
    }

    private void Fragment_Update() {
        check_fragment = 4;
        navigationSelected("update");
        androidx.fragment.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_home_frame_layout, new FragmentUpdate(), "frag_update");
        fragmentTransaction.commit();
    }

    private void Fragment_Account() {
        check_fragment = 5;
        SharedPreferences sharedPreferences = getSharedPreferences("com.gvtechcom.myshop.firts", Context.MODE_PRIVATE);
        boolean account_key = sharedPreferences.getBoolean("account", false);
        if (account_key) {
            navigationSelected("account");
            androidx.fragment.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_home_frame_layout, new FragmentAccount(), "frag_account");
            fragmentTransaction.commit();
        } else {
            Intent intent = new Intent(MainActivity.this, AccountActivity.class);
            startActivity(intent);
        }
    }

    private void Get_Extra() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            String s = (String) bundle.get("account");
            if (s.equals("true")) {
                check_fragment = 5;
                navigationSelected("account");
                androidx.fragment.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_home_frame_layout, new FragmentAccount(), "frag_account");
                fragmentTransaction.commit();
            }
        }
    }

    public void setEditSearchNavigation(Boolean isSearch) {
        if (isSearch) {
            searchViewNavigation.setOnClickListener(null);
            searchViewNavigation.setFocusableInTouchMode(true);
            searchViewNavigation.setFocusable(true);
            searchViewNavigation.addTextChangedListener(textWatcherSearchListener);
        } else {
            searchViewNavigation.setText(null);
            searchViewNavigation.setFocusable(false);
            searchViewNavigation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.content_home_frame_layout, new FragmentSearch());
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });
        }
    }

    private void setActionSearchClick() {
        searchViewNavigation.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (clickActionSearch != null) {
                        clickActionSearch.clickActionSearch(searchViewNavigation.getText().toString());
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private final TextWatcher textWatcherSearchListener = new TextWatcher() {
        public void onTextChanged(final CharSequence s, int start, final int before, int count) {
        }

        @Override
        public void afterTextChanged(final Editable s) {
            keywordSearchl.dataKeyWord(s.toString());
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
    };

    public void setKeywordSearchl(KeywordSearch keywordSearchl) {
        this.keywordSearchl = keywordSearchl;
    }

    public void setClickActionSearch(ClickActionSearch clickActionSearch) {
        this.clickActionSearch = clickActionSearch;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setColorStatusTran(boolean isColorTran) {
        if (isColorTran) {
            StatusBarCompat.translucentStatusBar(this, true);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.WHITE);
            }
        }
    }

    public void setHideButtonNavigation(Boolean bl) {
        if (bl) {
            tableLayoutHome.setVisibility(View.GONE);
        } else {
            tableLayoutHome.setVisibility(View.VISIBLE);
        }
    }

    public void setDisplayNavigationBar(Boolean navicationTop, Boolean back, Boolean cart) {

        if (navicationTop) {
            navigationTop.setVisibility(View.VISIBLE);
        } else {
            navigationTop.setVisibility(View.GONE);
        }

        if (back) {
            imgBtnBackNavigation.setVisibility(View.VISIBLE);
        } else {
            imgBtnBackNavigation.setVisibility(View.GONE);
        }

        if (cart) {
            imgShoppingCart.setVisibility(View.VISIBLE);
        } else {
            imgShoppingCart.setVisibility(View.GONE);
        }
    }

    public void setColorNavigationBar(int iconBack, int colorSearch, String hintSearch, int color, String codeString) {
        navigationTop.setBackgroundResource(color);
        imgBtnBackNavigation.setImageResource(iconBack);
        searchViewNavigation.setBackgroundResource(colorSearch);
        searchViewNavigation.setHint(hintSearch);
        searchViewNavigation.setHintTextColor(Color.parseColor(codeString));
    }

    public void setupUI(View view) {

        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(MainActivity.this);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    public void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            finish();
        }
    }

}
