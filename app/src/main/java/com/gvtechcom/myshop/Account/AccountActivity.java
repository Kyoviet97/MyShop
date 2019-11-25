package com.gvtechcom.myshop.Account;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.gvtechcom.myshop.MainActivity;
import com.gvtechcom.myshop.R;
import com.mylibrary.ui.statusbar.StatusBarCompat;

public class AccountActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        setColorStatusTran(true);
        loginFragment();

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

    private void loginFragment(){
        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame_account, new LoginFragment(), "frag_login");
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if(fragmentManager.getBackStackEntryCount() > 0){
            fragmentManager.popBackStack();
        } else{
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }
}
