package com.gvtechcom.myshop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mylibrary.base.BaseActivity;
import com.mylibrary.ui.statusbar.StatusBarCompat;

public class FirtsLauncher extends BaseActivity {
    Button btnClickFirts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firts_launcher);
        setColorStatusTran(true);
        initialize();
        Click_to_Main();
    }

    private void Click_to_Main() {
        btnClickFirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("com.gvtechcom.myshop.firts", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("firts_launcher", false);
                editor.apply();
                Intent intent;
                intent = new Intent(FirtsLauncher.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initialize() {
        btnClickFirts = (Button) findViewById(R.id.btn_click_firts);
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
}
