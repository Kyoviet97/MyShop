package com.gvtechcom.myshop.dialog;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DialogEditEmail extends AppCompatDialog {
    private String srtEmail = "";
    private APIServer apiServer;
    private ProgressDialogCustom progressDialogCustom;

    @BindView(R.id.edt_fullName_dialog)
    EditText editEmailAcoount;

    @BindView(R.id.txt_title_change_fullname_account)
    TextView txTitleChangeFullnameAccount;

    @BindView(R.id.txt_message_change_fullname_account)
    TextView txtMessageChangeFullnameAccount;

    public DialogEditEmail(Context context, String email) {
        super(context, R.style.Theme_Dialog);
        setCancelable(false);
        setContentView(R.layout.custom_dialog_change_fullname);
        ButterKnife.bind(this);
        init();

        editEmailAcoount.setText(email + "");
        editEmailAcoount.setHint("Email");
        txTitleChangeFullnameAccount.setText("Update Email");
        txtMessageChangeFullnameAccount.setText("Update your Email");
    }

    private void init() {
        progressDialogCustom = new ProgressDialogCustom(getContext());
        Retrofit retrofitClient;
        retrofitClient = RetrofitBuilder.getRetrofit(Const.BASE_URL);
        apiServer = retrofitClient.create(APIServer.class);
    }

    @OnClick({R.id.btn_update_dialog_change_fullname, R.id.btn_cancel_dialog_change_fullname})
    void clickDialogUpdateName(View view) {
        switch (view.getId()) {
            case R.id.btn_update_dialog_change_fullname:
                SetStrEmail(editEmailAcoount.getText().toString());
                String edtEmail = editEmailAcoount.getText().toString();

                Pattern pattern1 = Pattern.compile("^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\\.([a-zA-Z])+([a-zA-Z])+");
                Matcher matcher1 = pattern1.matcher(edtEmail);
                if (!matcher1.matches()) {
                    Toast("Invalid format");
                } else {
                    LoadApiChangeEmail(editEmailAcoount.getText().toString());
                }
                break;

            case R.id.btn_cancel_dialog_change_fullname:
                SetStrEmail(" ");
                dismiss();
                break;
        }
    }

    private void LoadApiChangeEmail(String Email) {
        progressDialogCustom.onShow(false, "Loading...");

        MySharePreferences preferences = new MySharePreferences();
        String AccessToken = preferences.GetSharePref(getContext(), "access_token");
        String Token = preferences.GetSharePref(getContext(), "token");
        String Token_type = preferences.GetSharePref(getContext(), "token_type");
        String Authorization = Token_type + " " + Token;

        GetMD5 getMD5 = new GetMD5();
        GetTime getTime = new GetTime();
        String timeSign = String.valueOf((getTime.getCalendar() + 30000));

        Call<BaseGetApiData> call = apiServer.UpdateInfoEmail("application/json", Authorization, Email, String.valueOf(getTime.getCalendar()), getMD5.md5_2(AccessToken, timeSign), "Android");
        call.enqueue(new Callback<BaseGetApiData>() {
            @Override
            public void onResponse(Call<BaseGetApiData> call, Response<BaseGetApiData> response) {
                if (response.code() == 401) {
                    Toast.makeText(getContext(), "Hết phiên đăng nhập", Toast.LENGTH_SHORT).show();
                    progressDialogCustom.onHide();
                } else {
                    if (response.body().getCode() != 200) {
                        Toast.makeText(getContext(), response.body().getMessage().toString(), Toast.LENGTH_SHORT).show();

                    } else {
                        MySharePreferences preferences = new MySharePreferences();
                        preferences.SaveSharePref(getContext(), "email", response.body().getResponse().getDataUser().getEmail());
                        progressDialogCustom.onHide();
                        dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseGetApiData> call, Throwable t) {
                System.out.println("---------->" + t.toString());
                progressDialogCustom.onHide();
            }
        });
    }

    public void SetStrEmail(String srtEmail) {
        this.srtEmail = srtEmail;
    }

    public String getStrEmail() {
        return srtEmail;
    }

    private void Toast(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }
}
