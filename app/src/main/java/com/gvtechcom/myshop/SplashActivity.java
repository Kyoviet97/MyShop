package com.gvtechcom.myshop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Toast;

import com.gvtechcom.myshop.Model.BaseGetApiData;
import com.gvtechcom.myshop.Model.ItemYouLoveModel;
import com.gvtechcom.myshop.Network.APIServer;
import com.gvtechcom.myshop.Network.RetrofitBuilder;
import com.gvtechcom.myshop.Utils.Const;
import com.gvtechcom.myshop.Utils.MySharePreferences;
import com.mylibrary.base.BaseActivity;
import com.mylibrary.ui.statusbar.StatusBarCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SplashActivity extends BaseActivity {
    private APIServer apiServer;
    private MySharePreferences mySharePreferences;
    private CountDownTimer countDownTimer;
    private Boolean stopCount;
    private ItemYouLoveModel.ItemYouLoveModelParser objectItemYouLove;
    private BaseGetApiData objDataHomeContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        this.stopCount = false;
        init();
    }

    private void init() {
        setColorStatusTran(true);
        mySharePreferences = new MySharePreferences();
        Retrofit retrofitClient;
        retrofitClient = RetrofitBuilder.getRetrofit(Const.BASE_URL);
        apiServer = retrofitClient.create(APIServer.class);
        LoadApiHome();
        LoadApiItemYouLove();
        loading();
    }

    private void LoadApiHome() {
        Call<BaseGetApiData> call = apiServer.GetDataHomeBannerSlide();
        call.enqueue(new Callback<BaseGetApiData>() {
            @Override
            public void onResponse(Call<BaseGetApiData> call, Response<BaseGetApiData> response) {
                if (response.body().getCode() != 200) {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    objDataHomeContent = response.body();
                    if (objDataHomeContent != null) {
                        mySharePreferences.SaveSharePrefObject(getApplicationContext(), objDataHomeContent, "objDataHomeContent");
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseGetApiData> call, Throwable t) {
            }
        });
    }

    private void LoadApiItemYouLove() {
        Call<ItemYouLoveModel.ItemYouLoveModelParser> itemYouLoveModelCall = apiServer.GetItemYouLove(1, 10);
        itemYouLoveModelCall.enqueue(new Callback<ItemYouLoveModel.ItemYouLoveModelParser>() {
            @Override
            public void onResponse(Call<ItemYouLoveModel.ItemYouLoveModelParser> call, Response<ItemYouLoveModel.ItemYouLoveModelParser> response) {
                if (response.body().code != 200) {
                    Toast.makeText(SplashActivity.this, response.body().message, Toast.LENGTH_SHORT).show();
                } else {
                    objectItemYouLove = response.body();
                    if (objectItemYouLove != null) {
                        mySharePreferences.SaveSharePrefObject(getApplicationContext(), objectItemYouLove, "MyModelItemYouLove");
                    }
                }
            }

            @Override
            public void onFailure(Call<ItemYouLoveModel.ItemYouLoveModelParser> call, Throwable t) {

            }
        });
    }

    private void loading() {
        countDownTimer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (stopCount == true) {
                    countDownTimer.cancel();
                } else {
                    if (objectItemYouLove != null && objDataHomeContent != null) {
                        stopCount = true;
                        checkFirstLauncher();
                    }
                }
            }

            @Override
            public void onFinish() {
                Toast.makeText(SplashActivity.this, "Thời gian chờ quá lâu! Vui lòng thử lại", Toast.LENGTH_SHORT).show();
            }
        }.start();

    }

    private void checkFirstLauncher() {
        SharedPreferences sharedPreferences = getSharedPreferences("com.gvtechcom.myshop.firts", Context.MODE_PRIVATE);
        boolean isFirtsLauncher = sharedPreferences.getBoolean("firts_launcher", true);
        Intent intent;
        if (isFirtsLauncher == true) {
            intent = new Intent(SplashActivity.this, FirtsLauncher.class);
        } else {
            intent = new Intent(SplashActivity.this, MainActivity.class);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
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

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}
