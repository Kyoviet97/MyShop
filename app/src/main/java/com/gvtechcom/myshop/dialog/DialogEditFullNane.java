package com.gvtechcom.myshop.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
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

public class DialogEditFullNane extends AppCompatDialog {
    private ProgressDialogCustom progressDialogCustom;
    private ToastDialog toastDialog;
    private APIServer apiServer;
    private String srtName;

    @BindView(R.id.edt_fullName_dialog)
    EditText edtFullName;

    public void setSrtName(String srtName) {
        this.srtName = srtName;
    }

    public String getSrtName() {
        return srtName;
    }

    public DialogEditFullNane(Context context, String name) {
        super(context, R.style.Theme_Dialog);
        setCancelable(false);
        setContentView(R.layout.custom_dialog_change_fullname);
        ButterKnife.bind(this);
        edtFullName.setText(name);
        init();
    }

    @OnClick({R.id.btn_update_dialog_change_fullname, R.id.btn_cancel_dialog_change_fullname})
    void clickDialogUpdateName(View view) {
        switch (view.getId()) {
            case R.id.btn_update_dialog_change_fullname:
                loadApiChangeName(edtFullName.getText().toString());
                break;
            case R.id.btn_cancel_dialog_change_fullname:
                setSrtName(" ");
                dismiss();
                break;
        }
    }

    private void init() {
        progressDialogCustom = new ProgressDialogCustom(getContext());
        toastDialog = new ToastDialog(getContext());
        Retrofit retrofitClient;
        retrofitClient = RetrofitBuilder.getRetrofit(Const.BASE_URL);
        apiServer = retrofitClient.create(APIServer.class);
    }

    private void loadApiChangeName(String name) {
        progressDialogCustom.onShow(false, "Update...");

        MySharePreferences preferences = new MySharePreferences();
        String AccessToken = preferences.GetSharePref(getContext(), "access_token");
        String Token = preferences.GetSharePref(getContext(), "token");
        String Token_type = preferences.GetSharePref(getContext(), "token_type");
        String Authorization = Token_type + " " + Token;

        GetMD5 getMD5 = new GetMD5();
        GetTime getTime = new GetTime();
        String timeSign = String.valueOf((getTime.getCalendar() + 30000));

        Call<BaseGetApiData> call = apiServer.UpdateInfoName("application/json", Authorization, name, String.valueOf(getTime.getCalendar()), getMD5.md5_2(AccessToken, timeSign), "Android");
        call.enqueue(new Callback<BaseGetApiData>() {
            @Override
            public void onResponse(Call<BaseGetApiData> call, Response<BaseGetApiData> response) {
                if (response.code() == 401) {
                    progressDialogCustom.onHide();
                    toastDialog.onShow("You have been logged out. Please login again");
                } else {
                    if (response.body().getCode() != 200) {
                        progressDialogCustom.onHide();
                        toastDialog.onShow(response.body().getMessage());

                    } else {
                        MySharePreferences preferences = new MySharePreferences();
                        preferences.SaveSharePref(getContext(), "name", response.body().getResponse().getDataUser().getName());
                        progressDialogCustom.onHide();
                        toastDialog.onShow(response.body().getMessage());
                        toastDialog.setOnDismissListener(new OnDismissListener() {
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
                toastDialog.onShow("An error occurred, please try again later");
            }
        });
    }

}
