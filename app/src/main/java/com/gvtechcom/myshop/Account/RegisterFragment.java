package com.gvtechcom.myshop.Account;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.gvtechcom.myshop.MainActivity;
import com.gvtechcom.myshop.Model.BaseGetApiData;
import com.gvtechcom.myshop.Network.APIServer;
import com.gvtechcom.myshop.Network.RetrofitBuilder;
import com.gvtechcom.myshop.R;
import com.gvtechcom.myshop.Utils.Const;
import com.gvtechcom.myshop.Utils.GetMD5;
import com.gvtechcom.myshop.Utils.GetTime;
import com.gvtechcom.myshop.Utils.ValidateInput;
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

public class RegisterFragment extends Fragment {
    private View rootView;

    private APIServer apiServer;

    private FragmentManager fragmentManager;

    private ProgressDialogCustom progressDialogCustom;

    private ToastDialog toastDialog;

    private AccountActivity accountActivity;

    @BindView(R.id.layout_main_register)
    ConstraintLayout layoutMainRegister;

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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_giao_dien_account, container, false);
        ButterKnife.bind(this, rootView);
        accountActivity = (AccountActivity) getActivity();
        accountActivity.onListenKeyboard(accountActivity, layoutMainRegister);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        progressDialogCustom = new ProgressDialogCustom(getActivity());
        toastDialog = new ToastDialog(getActivity());
        Retrofit retrofitClient;
        retrofitClient = RetrofitBuilder.getRetrofit(Const.BASE_URL);
        apiServer = retrofitClient.create(APIServer.class);
        txtDieuKhoan.setText(Html.fromHtml("<u>Terms & Conditions</u>"));
        textViewMessageDownBtn.setText(Html.fromHtml("<u>I already have an account?</u>"));
    }

    @OnClick({R.id.button_account, R.id.rules_account, R.id.textView_message_duoi_btn})
    void onClickButton(View view) {
        switch (view.getId()) {
            case R.id.button_account:
                buttonRegister();
                break;

            case R.id.rules_account:
                buttonRuleAccount();
                break;

            case R.id.textView_message_duoi_btn:
                getFragmentManager().popBackStack();
                break;
        }
    }

    private void buttonRegister() {
        ValidateInput validateInput = new ValidateInput();
        if (validateInput.validatePhone(editTextAccount.getText().toString())) {
            GetMD5 getMD5 = new GetMD5();
            GetTime getTime = new GetTime();
            String timeSign = String.valueOf((getTime.getCalendar() + 30000));
            loadApiRegister(editTextAccount.getText().toString(), String.valueOf(getTime.getCalendar()), getMD5.md5(timeSign), "Android");
        } else {
            toastDialog.onShow("Please enter the phone number");
        }
    }

    private void buttonRuleAccount() {
        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_account, new RuleAccount());
        fragmentTransaction.addToBackStack("frag_register");
        fragmentTransaction.commit();
    }

    private void loadApiRegister(String Telephone, String time, String sign, String type_app) {
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
                    fragmentManager = getFragmentManager();
                    Fragment OtpFragment = new OtpFrragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("PhoneNumber", editTextAccount.getText().toString());
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    OtpFragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.frame_account, OtpFragment);
                    fragmentTransaction.addToBackStack("frag_register");
                    fragmentTransaction.commit();
                }
            }

            @Override
            public void onFailure(@NotNull Call<BaseGetApiData> call, Throwable t) {
                progressDialogCustom.onHide();
                toastDialog.onShow("An error occurred, please try again later");
            }
        });

    }

}
