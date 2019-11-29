package com.gvtechcom.myshop;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.gvtechcom.myshop.Account.AccountActivity;
import com.gvtechcom.myshop.Fragment.FragmentAccount;
import com.gvtechcom.myshop.Fragment.FragmentHomeContent;
import com.gvtechcom.myshop.Fragment.FragmentMessages;
import com.gvtechcom.myshop.Fragment.FragmentOrders;
import com.gvtechcom.myshop.Fragment.FragmentUpdate;
import com.mylibrary.ui.statusbar.StatusBarCompat;
import com.mylibrary.ui.statusbar.SystemBarTintManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private int check_fragment;

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
    @BindView(R.id.button_navigation)
    LinearLayout buttonNavigation;
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
        fragmentManager = getFragmentManager();
        Fragment_Home();
        Get_Extra();
        this.check_fragment = 0;
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

    public void SharedPreferences(String key, String value) {
        SharedPreferences sharedPreferences = getSharedPreferences("com.gvtechcom.myshop.firts", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void Fragment_Home() {
        check_fragment = 1;
            navigationSelected("home");
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_home_frame_layout, new FragmentHomeContent(), "frag_home");
            fragmentTransaction.commit();
    }

    private void Fragment_Messages() {
        check_fragment = 2;
            navigationSelected("messager");
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_home_frame_layout, new FragmentMessages(), "frag_messages");
            fragmentTransaction.commit();
    }

    private void Fragment_Orders() {
        check_fragment = 3;
            navigationSelected("orders");
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_home_frame_layout, new FragmentOrders(), "frag_orders");
            fragmentTransaction.commit();
    }

    private void Fragment_Update() {
        check_fragment = 4;
            navigationSelected("update");
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_home_frame_layout, new FragmentUpdate(), "frag_update");
            fragmentTransaction.commit();
    }

    private void Fragment_Account() {
        check_fragment = 5;
        SharedPreferences sharedPreferences = getSharedPreferences("com.gvtechcom.myshop.firts", Context.MODE_PRIVATE);
        boolean account_key = sharedPreferences.getBoolean("account", false);
        if (account_key) {
                navigationSelected("account");
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
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
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_home_frame_layout, new FragmentAccount(), "frag_account");
                fragmentTransaction.commit();
            }
        }
    }

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
            buttonNavigation.setVisibility(View.GONE);
        } else {
            buttonNavigation.setVisibility(View.VISIBLE);
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

    public void setColorNavigationBar(int iconBack, int colorSearch, String hintSearch, int color){
        navigationTop.setBackgroundResource(color);
        imgBtnBackNavigation.setImageResource(iconBack);
        searchViewNavigation.setBackgroundResource(colorSearch);
        searchViewNavigation.setHint(hintSearch);
        searchViewNavigation.setHintTextColor(Color.parseColor("#9CFFFFFF"));
    }

    @Override
    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
        }
    }

}
