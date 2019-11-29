package com.gvtechcom.myshop.Account;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
import com.gvtechcom.myshop.Utils.MySharePreferences;
import com.gvtechcom.myshop.Utils.ValidateInput;
import com.gvtechcom.myshop.dialog.ToastDialog;
import com.mylibrary.ui.input.CustomInputText;
import com.mylibrary.ui.input.DrawableClickListener;
import com.mylibrary.ui.progress.ProgressDialogCustom;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginFragment extends Fragment {
    private View rootView;

    private int n = 1;

    private APIServer apiServer;

    private ProgressDialogCustom progressDialogCustom;
    private ToastDialog toastDialog;

    private FragmentManager fragmentManager;

    private AccountActivity mainActivity;

    @BindView(R.id.edt_phone_number)
    EditText edtPhoneNumber;

    @BindView(R.id.edt_password)
    CustomInputText edtPassword;

    @BindView(R.id.btn_login)
    Button btnLogin;

    @BindView(R.id.btn_forgot_password)
    TextView btnForgotPassword;

    @BindView(R.id.btn_register)
    Button btnRegister;

    @BindView(R.id.layout_main_fragment_login)
    ConstraintLayout LayoutMainFragmentLogin;

    @SuppressLint("ResourceType")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, rootView);
        progressDialogCustom = new ProgressDialogCustom(getActivity());
        toastDialog = new ToastDialog(getActivity());
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();

    }

    private void init() {
        mainActivity = (AccountActivity) getActivity();
        mainActivity.setupUI(LayoutMainFragmentLogin);

        Retrofit retrofit;
        retrofit = RetrofitBuilder.getRetrofit(Const.BASE_URL);
        apiServer = retrofit.create(APIServer.class);

        buttonRegister();
        buttonForgotPassword();
        buttonLogin();
        onClickDrawableImage();
    }

    private void buttonForgotPassword() {
        btnForgotPassword.setText(Html.fromHtml("<p><u>Forgot password</u></p>"));

        btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("com.gvtechcom.myshop.firts", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("register", false);
                editor.apply();

                fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_account, new ForgotFragment(), "frag_forgot");
                fragmentTransaction.addToBackStack("frag_login");
                fragmentTransaction.commit();
            }
        });
    }

    private void buttonRegister() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("com.gvtechcom.myshop.firts", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("register", true);
                editor.apply();

                fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_account, new RegisterFragment(), "frag_register");
                fragmentTransaction.addToBackStack("frag_login");
                fragmentTransaction.commit();
            }
        });
    }

    private void validateTnput() {
        ValidateInput validateInput = new ValidateInput();

        String pass = edtPassword.getText().toString();
        String phoneNumber = edtPhoneNumber.getText().toString();

        if (!validateInput.validatePhone(phoneNumber)) {
            toastDialog.onShow("Invalid phone number");
        } else {
            if (!validateInput.validatePass(pass)) {
                toastDialog.onShow("Password is too short");
            } else {
                loadApiLogin(edtPhoneNumber.getText().toString(), edtPassword.getText().toString());

            }
        }

    }

    private void onClickDrawableImage() {
        edtPassword.setDrawableClickListener(new DrawableClickListener() {
            @Override
            public void onClick(DrawablePosition target) {
                switch (target) {
                    case RIGHT:
                        if (n == 1) {
                            n = n + 1;
                            edtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        } else {
                            edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            n = 1;
                        }

                        break;
                }
            }
        });
    }

    private void buttonLogin() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateTnput();
            }
        });

    }

    private void loadApiLogin(String telephone, String password) {
        progressDialogCustom.onShow(false, "Login...");
        GetMD5 getMD5 = new GetMD5();
        GetTime getTime = new GetTime();
        String timeSign = String.valueOf((getTime.getCalendar() + 30000));
        Call<BaseGetApiData> call = apiServer.LoginAccount(telephone, password, String.valueOf(getTime.getCalendar()), getMD5.md5(timeSign), "Andoird");
        call.enqueue(new Callback<BaseGetApiData>() {
            @Override
            public void onResponse(Call<BaseGetApiData> call, Response<BaseGetApiData> response) {
                if (response.body().getCode() != 200) {
                    progressDialogCustom.onHide();
                    toastDialog.onShow(response.body().getMessage());
                } else {
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("com.gvtechcom.myshop.firts", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("account", true);
                    editor.apply();

                    MySharePreferences preferences = new MySharePreferences();
                    preferences.SaveSharePref(getActivity(), "telephoneNumber", response.body().getResponse().getInfoUser().getTelephone());
                    preferences.SaveSharePref(getActivity(), "access_token", response.body().getResponse().getAccessToken());
                    preferences.SaveSharePref(getActivity(), "object_user_id", response.body().getResponse().getInfoUser().getObjectUserId());
                    preferences.SaveSharePref(getActivity(), "token_type", response.body().getResponse().getTokenType());
                    preferences.SaveSharePref(getActivity(), "token", response.body().getResponse().getToken());
                    preferences.SaveSharePref(getActivity(), "gender", response.body().getResponse().getInfoUser().getGender());
                    preferences.SaveSharePref(getActivity(), "email", response.body().getResponse().getInfoUser().getEmail());
                    preferences.SaveSharePref(getActivity(), "name", response.body().getResponse().getInfoUser().getName());
                    preferences.SaveSharePref(getActivity(), "birthday", response.body().getResponse().getInfoUser().getBirthday());

                    progressDialogCustom.onHide();

                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.putExtra("account", "true");
                    startActivity(intent);
                    getActivity().finish();
                }
            }

            @Override
            public void onFailure(Call<BaseGetApiData> call, Throwable t) {
                progressDialogCustom.onHide();
                toastDialog.onShow("An error occurred, please try again later");
            }
        });
    }

    @Override
    public void onDestroyView() {
        n = 1;
        super.onDestroyView();
    }
}
