package com.gvtechcom.myshop.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialog;

import com.gvtechcom.myshop.Model.BaseGetApiData;
import com.gvtechcom.myshop.Network.APIServer;
import com.gvtechcom.myshop.Network.RetrofitBuilder;
import com.gvtechcom.myshop.R;
import com.gvtechcom.myshop.Utils.Const;
import com.gvtechcom.myshop.Utils.GetMD5;
import com.gvtechcom.myshop.Utils.GetTime;
import com.gvtechcom.myshop.Utils.MySharePreferences;
import com.mylibrary.ui.progress.ProgressDialogCustom;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DialogEditGender extends AppCompatDialog {
    private APIServer apiServer;
    private ProgressDialogCustom progressDialogCustom;
    private CustomToastDialog customToastDialog;
    private String strGender = "";

    @BindView(R.id.checkBox_male)
    RadioButton checkMaleGender;
    @BindView(R.id.checkBox_female)
    RadioButton checkFemaleGender;


    public void setStrGender(String strGender) {
        this.strGender = strGender;
    }

    public String getStrGender() {
        return strGender;
    }

    public DialogEditGender(Context context, String txtGenderAccount) {
        super(context, R.style.Theme_Dialog);
        setContentView(R.layout.custom_change_gender);
        ButterKnife.bind(this);
        init();
        setStrGender(txtGenderAccount);
        if (txtGenderAccount.equals("Male")) {
            checkMaleGender.setChecked(true);
        } else {
            checkFemaleGender.setChecked(true);
        }
    }

    private void init() {
        progressDialogCustom = new ProgressDialogCustom(getContext());
        customToastDialog = new CustomToastDialog(getContext());

        Retrofit retrofitClient;
        retrofitClient = RetrofitBuilder.getRetrofit(Const.BASE_URL);
        apiServer = retrofitClient.create(APIServer.class);

    }

    private void loadApiChangeGender(int gender) {
        progressDialogCustom.onShow(false, "Update...");

        MySharePreferences preferences = new MySharePreferences();
        String AccessToken = preferences.GetSharePref(getContext(), "access_token");
        String Token = preferences.GetSharePref(getContext(), "token");
        String Token_type = preferences.GetSharePref(getContext(), "token_type");
        String Authorization = Token_type + " " + Token;

        GetMD5 getMD5 = new GetMD5();
        GetTime getTime = new GetTime();
        String timeSign = String.valueOf((getTime.getCalendar() + 30000));

        Call<BaseGetApiData> call = apiServer.UpdateInfoGender("application/json", Authorization, gender, String.valueOf(getTime.getCalendar()), getMD5.md5_2(AccessToken, timeSign), "Android");
        call.enqueue(new Callback<BaseGetApiData>() {
            @Override
            public void onResponse(Call<BaseGetApiData> call, Response<BaseGetApiData> response) {
                if (response.code() == 401) {
                    progressDialogCustom.onHide();
                    customToastDialog.onShow(R.drawable.ic_icon_load_error_dialog, "You have been logged out. Please login again", false);
                } else {
                    if (response.body().getCode() != 200) {
                        progressDialogCustom.onHide();
                        customToastDialog.onShow(R.drawable.ic_icon_load_error_dialog, response.body().getMessage(), false);

                    } else {
                        MySharePreferences preferences = new MySharePreferences();
                        preferences.SaveSharePref(getContext(), "gender", response.body().getResponse().getDataUser().getGender());
                        progressDialogCustom.onHide();
                        customToastDialog.onShow(R.drawable.ic_tick_green_toast_dialog, response.body().getMessage(), false);
                        customToastDialog.setOnDismissListener(new OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                dismiss();
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseGetApiData> call, Throwable t) {
                progressDialogCustom.onHide();
                customToastDialog.onShow(R.drawable.ic_icon_load_error_dialog, "An error occurred, please try again later", false);

            }
        });
    }

    @OnClick({R.id.btn_update_dialog_gender, R.id.btn_cancel_dialog_gender})
    void clickDialogEditGender(View view) {
        switch (view.getId()) {
            case R.id.btn_update_dialog_gender:
                if (checkMaleGender.isChecked()) {
                    setStrGender("Male");
                    loadApiChangeGender(1);
                } else if (checkFemaleGender.isChecked()) {
                    loadApiChangeGender(2);
                    setStrGender("Female");
                }
                break;
            case R.id.btn_cancel_dialog_gender:
                setStrGender(strGender);
                dismiss();
                break;
        }
    }
}
