package com.gvtechcom.myshop.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.View;
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
import com.gvtechcom.myshop.Utils.ValidateInput;
import com.mylibrary.ui.input.CustomInputText;
import com.mylibrary.ui.input.DrawableClickListener;
import com.mylibrary.ui.progress.ProgressDialogCustom;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DialogChangePass extends AppCompatDialog {
    private int n = 1, m = 1, o = 1;

    private APIServer apiServer;

    private ProgressDialogCustom progressDialogCustom;

    private CustomToastDialog customToastDialog;

    @BindView(R.id.edt_old_pass)
    CustomInputText edtOldPassWord;

    @BindView(R.id.edt_new_pass)
    CustomInputText edtNewPass;

    @BindView(R.id.edt_new_pass_confirm)
    CustomInputText edtNewPassConfirm;

    public DialogChangePass(Context context) {
        super(context, R.style.Theme_Dialog);
        setCancelable(false);
        setContentView(R.layout.custom_dialog_change_pass);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        progressDialogCustom = new ProgressDialogCustom(getContext());
        customToastDialog = new CustomToastDialog(getContext());
        setShowEdtPassWord();
        Retrofit retrofitClient;
        retrofitClient = RetrofitBuilder.getRetrofit(Const.BASE_URL);
        apiServer = retrofitClient.create(APIServer.class);
    }

    @OnClick({R.id.btn_cancel_pass, R.id.btn_update_pass})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel_pass:
                dismiss();
                break;

            case R.id.btn_update_pass:
                validateInputData();
                break;
        }
    }

    private void validateInputData() {

        ValidateInput validateInput = new ValidateInput();
        String srtOldPass = edtOldPassWord.getText().toString();
        String srtNewPass = edtNewPass.getText().toString();
        String srtNewPassConfirm = edtNewPassConfirm.getText().toString();
        if (!validateInput.validatePass(srtNewPass)) {
            customToastDialog.onShow(R.drawable.ic_icon_load_error_dialog, "Password is too short", false);
        } else {
            if (!validateInput.validateTheSamePass(srtNewPass, srtNewPassConfirm)) {
                customToastDialog.onShow(R.drawable.ic_icon_load_error_dialog, "Password incorrect", false);
            } else {
                LoadApiChangePassWord(srtOldPass, srtNewPass, srtNewPassConfirm);
                dismiss();
            }
        }

    }

    private void setShowEdtPassWord() {
        edtOldPassWord.setDrawableClickListener(new DrawableClickListener() {
            @Override
            public void onClick(DrawablePosition target) {
                switch (target) {
                    case RIGHT:
                        if (n == 1) {
                            n = n + 1;
                            edtOldPassWord.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        } else {
                            edtOldPassWord.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            n = 1;
                        }

                        break;
                }
            }
        });


        edtNewPass.setDrawableClickListener(new DrawableClickListener() {
            @Override
            public void onClick(DrawablePosition target) {
                switch (target) {
                    case RIGHT:
                        if (m == 1) {
                            m = m + 1;
                            edtNewPass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        } else {
                            edtNewPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            m = 1;
                        }

                        break;
                }
            }
        });


        edtNewPassConfirm.setDrawableClickListener(new DrawableClickListener() {
            @Override
            public void onClick(DrawablePosition target) {
                switch (target) {
                    case RIGHT:
                        if (o == 1) {
                            o = o + 1;
                            edtNewPassConfirm.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        } else {
                            edtNewPassConfirm.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            o = 1;
                        }

                        break;
                }
            }
        });
    }

    private void LoadApiChangePassWord(String oldpass, String new_pass, String confirm_pass) {
        progressDialogCustom.onShow(false, "Loading...");
        MySharePreferences preferences = new MySharePreferences();
        String AccessToken = preferences.GetSharePref(getContext(), "access_token");
        String Token = preferences.GetSharePref(getContext(), "token");
        String Token_type = preferences.GetSharePref(getContext(), "token_type");
        String Authorization = Token_type + " " + Token;

        GetMD5 getMD5 = new GetMD5();
        GetTime getTime = new GetTime();
        String timeSign = String.valueOf((getTime.getCalendar() + 30000));


        Call<BaseGetApiData> call = apiServer.ChangePassWord("application/json", Authorization, oldpass, new_pass, confirm_pass, String.valueOf(getTime.getCalendar()), getMD5.md5_2(AccessToken, timeSign), "Android");
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
                customToastDialog.onShow(R.drawable.ic_icon_load_error_dialog, "An error occurred, please try again later", false);
                progressDialogCustom.onHide();
            }
        });
    }
}
