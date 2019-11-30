package com.gvtechcom.myshop.Account;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.gvtechcom.myshop.Model.BaseGetApiData;
import com.gvtechcom.myshop.Network.APIServer;
import com.gvtechcom.myshop.Network.RetrofitBuilder;
import com.gvtechcom.myshop.R;
import com.gvtechcom.myshop.Utils.Const;
import com.gvtechcom.myshop.Utils.GetMD5;
import com.gvtechcom.myshop.Utils.GetTime;
import com.gvtechcom.myshop.dialog.ToastDialog;
import com.mylibrary.ui.progress.ProgressDialogCustom;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OtpFrragment extends Fragment {
    private View rootView;
    private APIServer apiServer;
    private FragmentManager fragmentManager;
    private CountDownTimer downTimer;

    private Boolean isResend = true;
    private Boolean stopCount = false;
    private String PhoneNumberBundle;

    private ProgressDialogCustom progressDialogCustom;
    private ToastDialog toastDialog;
    private AccountActivity accountActivity;

    @BindView(R.id.layout_main_register)
    ConstraintLayout layoutMainRegister;

    @BindView(R.id.image_account)
    ImageView imageAccount;

    @BindView(R.id.textView_message_tren_edt)
    TextView textViewMessageUpEdt;

    @BindView(R.id.textView_message_duoi_edt)
    TextView txtTextViewMessageDownEdt;

    @BindView(R.id.editText_account)
    EditText editTextAccount;

    @BindView(R.id.button_account)
    Button buttonAccount;

    @BindView(R.id.textView_message_duoi_btn)
    TextView textViewMessageDownBtn;

    @BindView(R.id.rules_account)
    LinearLayout ruleAccount;

    @BindView(R.id.dieu_khoan)
    TextView txtDieuKhoan;

    public static int f = 30;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_giao_dien_account, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    @OnClick({R.id.button_account, R.id.rules_account, R.id.textView_message_duoi_btn, R.id.textView_message_duoi_edt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_account:
                buttonXacNhan();
                break;
            case R.id.textView_message_duoi_btn:
                getFragmentManager().popBackStack();
                break;
            case R.id.rules_account:
                buttonRuleAccount();
                break;
            case R.id.textView_message_duoi_edt:
                if (isResend == true) {
                    setResendOtp();
                }
                break;
        }
    }

    private void init() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            PhoneNumberBundle = bundle.getString("PhoneNumber");
        }

        accountActivity = (AccountActivity) getActivity();
        accountActivity.onListenKeyboard(accountActivity, layoutMainRegister);

        setGiaoDien();
        stopCount = false;

        Retrofit retrofitClient;
        retrofitClient = RetrofitBuilder.getRetrofit(Const.BASE_URL);
        apiServer = retrofitClient.create(APIServer.class);

        progressDialogCustom = new ProgressDialogCustom(getActivity());
        toastDialog = new ToastDialog(getActivity());
    }

    private void buttonXacNhan() {
        GetMD5 getMD5 = new GetMD5();
        GetTime getTime = new GetTime();
        String timeSign = String.valueOf((getTime.getCalendar() + 30000));
        LoadApiConfirmOtp(PhoneNumberBundle, editTextAccount.getText().toString(), String.valueOf(getTime.getCalendar()), getMD5.md5(timeSign), "Android");
    }

    private void LoadApiConfirmOtp(String telephone, String Otp, String time, String sign, String type_app) {
        progressDialogCustom.onShow(false, "Loading...");
        Call<BaseGetApiData> call = apiServer.otpconfirm(telephone, Otp, time, sign, type_app);
        call.enqueue(new Callback<BaseGetApiData>() {
            @Override
            public void onResponse(Call<BaseGetApiData> call, Response<BaseGetApiData> response) {
                if (response.body().getCode() != 200) {
                    progressDialogCustom.onHide();
                    toastDialog.onShow(response.body().getMessage());
                } else {
                    progressDialogCustom.onHide();
                    fragmentManager = getFragmentManager();
                    Fragment fragmentOtp = new FragmentChangePass();
                    Bundle bundle = new Bundle();
                    bundle.putString("TelephoneNumber", PhoneNumberBundle);
                    bundle.putString("OtpNumber", editTextAccount.getText().toString());
                    fragmentOtp.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame_account, fragmentOtp);
                    fragmentTransaction.addToBackStack("frag_otp");
                    fragmentTransaction.commit();
                }
            }


            @Override
            public void onFailure(Call<BaseGetApiData> call, Throwable t) {
                progressDialogCustom.onHide();
                toastDialog.onShow("An error occurred, please try again later");
            }
        });
    }

    private void LoadApiResendOtp(String Telephone, String time, String sign, String type_app) {
        progressDialogCustom.onShow(false, "Loading...");
        Call<BaseGetApiData> call = apiServer.registerPhone(Telephone, time, sign, type_app);
        call.enqueue(new Callback<BaseGetApiData>() {
            @Override
            public void onResponse(Call<BaseGetApiData> call, Response<BaseGetApiData> response) {
                if (response.body().getCode() != 200) {
                    progressDialogCustom.onHide();
                    toastDialog.onShow(response.body().getMessage());
                } else {
                    progressDialogCustom.onHide();
                }
            }

            @Override
            public void onFailure(@NotNull Call<BaseGetApiData> call, Throwable t) {
                progressDialogCustom.onHide();
                toastDialog.onShow("An error occurred, please try again later");
            }
        });

    }

    private void buttonRuleAccount() {
        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_account, new RuleAccount());
        fragmentTransaction.addToBackStack("frag_otp");
        fragmentTransaction.commit();
    }

    private void setGiaoDien() {
        imageAccount.setImageResource(R.drawable.ic_logo_xac_nhan);
        txtDieuKhoan.setText(Html.fromHtml("<u>Terms & Conditions</u>"));
        textViewMessageUpEdt.setText("Enter the verification code sent to your " + PhoneNumberBundle);
        editTextAccount.setHint("OTP code...");
        txtTextViewMessageDownEdt.setText("Resend OTP code" + '\n');
        txtTextViewMessageDownEdt.setTextColor(Color.parseColor("#FFAF23"));
        buttonAccount.setText("Confirm");
        textViewMessageDownBtn.setText(Html.fromHtml("<u>Change phone number</u>"));

    }


    private void setResendOtp() {
        GetMD5 getMD5 = new GetMD5();
        GetTime getTime = new GetTime();
        String timeSign = String.valueOf((getTime.getCalendar() + 30000));
        LoadApiResendOtp(PhoneNumberBundle, String.valueOf(getTime.getCalendar()), getMD5.md5(timeSign), "Android");
        downTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (stopCount == true) {
                    downTimer.cancel();
                } else {
                    isResend = false;
                    f = f - 1;
                    txtTextViewMessageDownEdt.setText("Resend OTP code: " + f);
                }
            }

            @Override
            public void onFinish() {
                stopCount = true;
                f = 30;
                isResend = true;
            }
        }.start();
    }

    @Override
    public void onDestroyView() {
        isResend = true;
        stopCount = true;
        f = 30;
        super.onDestroyView();
    }
}


