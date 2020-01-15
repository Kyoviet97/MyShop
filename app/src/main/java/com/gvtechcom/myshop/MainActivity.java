package com.gvtechcom.myshop;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;
import com.gvtechcom.myshop.Adapter.AdapterViewPagerHome;
import com.gvtechcom.myshop.Fragment.FragmentHomeContent;
import com.gvtechcom.myshop.Fragment.FragmentSearch;
import com.gvtechcom.myshop.Interface.ClickActionSearch;
import com.gvtechcom.myshop.Interface.KeywordSearch;
import com.gvtechcom.myshop.Utils.NonSwipeableViewPager;
import com.mylibrary.ui.statusbar.StatusBarCompat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private KeywordSearch keywordSearchl;
    private ClickActionSearch clickActionSearch;

    @BindView(R.id.tablayout_home)
    TabLayout tableLayoutHome;
    @BindView(R.id.view_pager_home)
    NonSwipeableViewPager viewPagerHome;
    @BindView(R.id.navication_top)
    RelativeLayout navigationTop;
    @BindView(R.id.img_btn_back_navigation)
    ImageView imgBtnBackNavigation;
    @BindView(R.id.searchView_Navigation)
    EditText searchViewNavigation;
    @BindView(R.id.img_shopping_cart)
    ImageView imgShoppingCart;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.sub_action_bar_activity_content)
    RelativeLayout subActionBar;
    @BindView(R.id.img_back_sub_action_bar)
    ImageView backButtonSubActionBar;
    @BindView(R.id.txt_title_sub_action_bar)
    TextView titleSubActionBar;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint({"CommitTransaction"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        fragmentManager = getSupportFragmentManager();
        setEditSearchNavigation(false);
        setActionSearchClick();
        addControl();
        backTabHome();
    }


    @OnClick({R.id.img_btn_back_navigation, R.id.btn_apply_filter, R.id.btn_reset_filter})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_btn_back_navigation:
                fragmentManager.popBackStack();
                break;
            case R.id.btn_apply_filter:
                Toast.makeText(this, "OKOKOKOKO", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void addControl() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        AdapterViewPagerHome adapter = new AdapterViewPagerHome(fragmentManager);
        viewPagerHome.setAdapter(adapter);
        viewPagerHome.setOffscreenPageLimit(5);
        tableLayoutHome.setupWithViewPager(viewPagerHome);
        viewPagerHome.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tableLayoutHome));
        tableLayoutHome.setTabsFromPagerAdapter(adapter);//deprecated
        tableLayoutHome.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPagerHome));

        tableLayoutHome.getTabAt(0).setIcon(R.drawable.bkg_btn_home);
        tableLayoutHome.getTabAt(1).setIcon(R.drawable.bkg_btn_messages);
        tableLayoutHome.getTabAt(2).setIcon(R.drawable.bkg_btn_orders);
        tableLayoutHome.getTabAt(3).setIcon(R.drawable.bkg_btn_update);
        tableLayoutHome.getTabAt(4).setIcon(R.drawable.bkg_btn_account);

        onTabSelect();

    }

    private void onTabSelect() {
        tableLayoutHome.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        setSubActionBar(true, true, "");
                        setDisplayNavigationBar(true, false, true);
                        setColorIconDarkMode(false, R.color.color_StatusBar);
                        break;

                    case 1:
                        setSubActionBar(false, true, "Messages");
                        setClickTabActionBar();
                        break;
                    case 2:
                        setSubActionBar(false, true, "Orders");
                        setClickTabActionBar();
                        break;
                    case 3:
                        setSubActionBar(false, true, "Update & Notification");
                        setClickTabActionBar();
                        break;
                    case 4:
                        setSubActionBar(false, true, "My Account");
                        setClickTabActionBar();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setClickTabActionBar(){
        setDisplayNavigationBar(false, false, false);
        setColorIconDarkMode(true, R.color.white);
    }

    public void backTabHome() {
        viewPagerHome.setCurrentItem(0);
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
                    fragmentTransaction.replace(R.id.frame_layout_home_manager, new FragmentSearch());
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

    @SuppressLint("WrongConstant")
    public void setOnpenDrawerRight(){
        drawerLayout.openDrawer(Gravity.END);
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    setColorStatusTran(true);
                }

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    setColorStatusTran(false);
                }
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    public void setSubActionBar(Boolean hideSubAction, Boolean hideBackButton, String titleSubAction) {
        if (hideSubAction) {
            subActionBar.setVisibility(View.GONE);
            navigationTop.setVisibility(View.VISIBLE);
        } else {
            subActionBar.setVisibility(View.VISIBLE);
            titleSubActionBar.setText(titleSubAction);
            navigationTop.setVisibility(View.GONE);

            if (hideBackButton) {
                backButtonSubActionBar.setVisibility(View.GONE);
            } else {
                backButtonSubActionBar.setVisibility(View.VISIBLE);
                backButtonSubActionBar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragmentManager.popBackStack();
                    }
                });
            }
        }
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
            System.out.println("=====================>" + viewPagerHome.getCurrentItem());
        } else {
            finish();
        }
    }

}
