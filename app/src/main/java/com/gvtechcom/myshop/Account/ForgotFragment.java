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
import com.gvtechcom.myshop.Utils.ValidateInput;
import com.gvtechcom.myshop.dialog.ToastDialog;
import com.mylibrary.ui.progress.ProgressDialogCustom;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ForgotFragment extends Fragment {
    private View rootView;

    private FragmentManager fragmentManager;

    private ProgressDialogCustom progressDialogCustom;
    private ToastDialog toastDialog;

    private AccountActivity accountActivity;

    private APIServer apiServer;

    @BindView(R.id.layout_main_register)
    ConstraintLayout layoutMainRegister;

    @BindView(R.id.image_account)
    ImageView image_account;

    @BindView(R.id.textView_message_tren_edt)
    TextView textView_message_tren_edt;

    @BindView(R.id.textView_message_duoi_edt)
    TextView getTextView_message_duoi_edt;

    @BindView(R.id.editText_account)
    EditText editText_account;

    @BindView(R.id.button_account)
    Button button_account;

    @BindView(R.id.textView_message_duoi_btn)
    TextView textView_message_duoi_btn;

    @BindView(R.id.rules_account)
    LinearLayout ruleAccount;

    @BindView(R.id.dieu_khoan)
    TextView txtDieuKhoan;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_giao_dien_account, container, false);
        ButterKnife.bind(this, rootView);
        Retrofit retrofit;
        accountActivity = (AccountActivity) getActivity();
        retrofit = RetrofitBuilder.getRetrofit(Const.BASE_URL);
        apiServer = retrofit.create(APIServer.class);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressDialogCustom = new ProgressDialogCustom(getActivity());
        toastDialog = new ToastDialog(getActivity());
        accountActivity.onListenKeyboard(accountActivity, layoutMainRegister);
        setGiaoDien();

    }

    private void setGiaoDien(){
        image_account.setImageResource(R.drawable.ic_forgot);
        txtDieuKhoan.setText(Html.fromHtml("<u>Terms & Conditions</u>"));
        textView_message_tren_edt.setText("I want to retrieve my password \nMy phone number is:");
        getTextView_message_duoi_edt.setText("The verification code is only sent when the phone number exists on the account");
        button_account.setText("Password retrieval");
        textView_message_duoi_btn.setText(Html.fromHtml("<u>Come back</u>"));

    }

    @OnClick({R.id.button_account, R.id.textView_message_duoi_btn, R.id.rules_account})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.button_account:
                validateInput();
                break;
            case R.id.textView_message_duoi_btn:
                fragmentManager = getFragmentManager();
                fragmentManager.popBackStack();
                break;
            case R.id.rules_account:
                buttonRuleAccount();
                break;
        }
    }

    private void validateInput(){
        ValidateInput validateInput = new ValidateInput();
        String srtEditAccount = editText_account.getText().toString();
        if (!validateInput.validatePhone(srtEditAccount)){
            Toast.makeText(getActivity(), "Invalid phone number", Toast.LENGTH_SHORT).show();
        }
        else {
            GetMD5 getMD5 = new GetMD5();
            GetTime getTime = new GetTime();
            String timeSign = String.valueOf((getTime.getCalendar() + 30000));

            loadApiForgotPassWord(editText_account.getText().toString(), String.valueOf(getTime.getCalendar()), getMD5.md5(timeSign), "Android");
        }
    }

    private void buttonRuleAccount(){
        ruleAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_account, new RuleAccount());
                fragmentTransaction.addToBackStack("frag_forgot");
                fragmentTransaction.commit();
            }
        });
    }

    private void loadApiForgotPassWord(String telephone, String time, String sign, String type_app){
        progressDialogCustom.onShow(false, "");
        Call<BaseGetApiData> call = apiServer.ForgotPasswordSendOtp(telephone, time, sign, type_app);
        call.enqueue(new Callback<BaseGetApiData>() {
            @Override
            public void onResponse(Call<BaseGetApiData> call, Response<BaseGetApiData> response) {
                if (response.body().getCode() != 200){
                    progressDialogCustom.onHide();
                    toastDialog.onShow(response.body().getMessage());
                }else {
                    progressDialogCustom.onHide();
                    fragmentManager = getFragmentManager();
                    Fragment MyFragment = new OtpFrragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("PhoneNumber", editText_account.getText().toString());
                    MyFragment.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame_account, MyFragment, "frag_forgot");
                    fragmentTransaction.addToBackStack("frag_forgot");
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
}
