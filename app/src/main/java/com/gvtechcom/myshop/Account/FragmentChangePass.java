package com.gvtechcom.myshop.Account;

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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

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

public class FragmentChangePass extends Fragment {
    private View rootView;

    private int n = 1, m = 1;

    private APIServer apiServer;

    private String phoneNumber, otpNumber;

    private FragmentManager fragmentManager;

    private ProgressDialogCustom progressDialogCustom;

    @BindView(R.id.button_change_pass)
    Button buttonChangePass;

    @BindView(R.id.editText_change_pass)
    CustomInputText editTextChangePass;

    @BindView(R.id.editText_change_pass_down)
    CustomInputText editTextChangePass2;

    @BindView(R.id.rule_change_pass)
    TextView txtRuleChangePass;

    @BindView(R.id.back_change_pass)
    TextView txtBackChange;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_change_password, container, false);
        ButterKnife.bind(this, rootView);
        init();
        return rootView;
    }

    private void init() {
        setGiaoDien();
        setShowPass();

        fragmentManager = getFragmentManager();
        progressDialogCustom = new ProgressDialogCustom(getActivity());

        Bundle bundle = getArguments();
        if (bundle != null) {
            phoneNumber = bundle.getString("TelephoneNumber");
            otpNumber = bundle.getString("OtpNumber");
        }

        Retrofit retrofitclient;
        retrofitclient = RetrofitBuilder.getRetrofit(Const.BASE_URL);
        apiServer = retrofitclient.create(APIServer.class);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick({R.id.button_change_pass, R.id.back_change_pass, R.id.rules_change_pass})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_change_pass:
                clickButtonChangePass();
                break;
            case R.id.back_change_pass:
                fragmentManager.popBackStack();
                break;
            case R.id.rules_change_pass:
                buttonRuleAccount();
                break;
        }
    }

    private void clickButtonChangePass() {
        String srtPass1 = editTextChangePass.getText().toString();
        String srtPass2 = editTextChangePass2.getText().toString();
        validatePass(srtPass1, srtPass2);
    }

    private void buttonRuleAccount() {
        fragmentManager = getFragmentManager();
        Fragment fragmentChangePass = new FragmentChangePass();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_account, fragmentChangePass);
        fragmentTransaction.addToBackStack("frag_change");
        fragmentTransaction.commit();
    }

    private void validatePass(String srtPass1, String srtPass2) {
        ValidateInput validateInput = new ValidateInput();
        if (validateInput.validatePass(srtPass1) == false) {
            Toast.makeText(getActivity(), "Password is too short", Toast.LENGTH_SHORT).show();
        } else {
            if (validateInput.validateTheSamePass(srtPass1, srtPass2) == false) {
                Toast.makeText(getActivity(), "Password incorrect", Toast.LENGTH_SHORT).show();
            } else {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("com.gvtechcom.myshop.firts", Context.MODE_PRIVATE);
                boolean isFirtsLauncher = sharedPreferences.getBoolean("register", true);
                if (isFirtsLauncher == true) {
                    GetMD5 getMD5 = new GetMD5();
                    GetTime getTime = new GetTime();
                    String timeSign = String.valueOf((getTime.getCalendar() + 30000));
                    getApiChangePass(phoneNumber, otpNumber, editTextChangePass.getText().toString(), editTextChangePass2.getText().toString()
                            , String.valueOf(getTime.getCalendar()), getMD5.md5(timeSign), "Android");
                } else {
                    GetMD5 getMD5 = new GetMD5();
                    GetTime getTime = new GetTime();
                    String timeSign = String.valueOf((getTime.getCalendar() + 30000));
                    getApiFrogotPass(phoneNumber, otpNumber, editTextChangePass.getText().toString(), editTextChangePass2.getText().toString()
                            , String.valueOf(getTime.getCalendar()), getMD5.md5(timeSign), "Android");
                }

            }
        }
    }

    private void setGiaoDien() {
        txtBackChange.setText(Html.fromHtml("<u>Quay lại</u>"));
    }

    private void setShowPass() {
        editTextChangePass.setDrawableClickListener(new DrawableClickListener() {
            @Override
            public void onClick(DrawablePosition target) {
                switch (target) {
                    case RIGHT:
                        if (n == 1) {
                            n = n + 1;
                            editTextChangePass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        } else {
                            editTextChangePass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            n = 1;
                        }

                        break;
                }
            }
        });


        editTextChangePass2.setDrawableClickListener(new DrawableClickListener() {
            @Override
            public void onClick(DrawablePosition target) {
                switch (target) {
                    case RIGHT:
                        if (m == 1) {
                            m = m + 1;
                            editTextChangePass2.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        } else {
                            editTextChangePass2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            m = 1;
                        }

                        break;
                }
            }
        });
    }

    private void getApiFrogotPass(String telephone, String otp, String password, String confirm_pass, String time, String sign, String type_app) {
        progressDialogCustom.onShow(false, "Loading...");
        Call<BaseGetApiData> call = apiServer.ForgotPassword(telephone, otp, password, confirm_pass, time, sign, type_app);
        call.enqueue(new Callback<BaseGetApiData>() {
            @Override
            public void onResponse(Call<BaseGetApiData> call, Response<BaseGetApiData> response) {
                if (response.body().getCode() != 200) {
                    progressDialogCustom.onHide();
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                } else {
                    progressDialogCustom.onHide();
                    String token = response.body().getResponse().getToken();
                    String access_token = response.body().getResponse().getAccessToken();
                    String token_type = response.body().getResponse().getTokenType();
                    String object_user_id = response.body().getResponse().getInfoUser().getObjectUserId();
                    String telephoneNumber = response.body().getResponse().getInfoUser().getTelephone();
                    String fullname = response.body().getResponse().getInfoUser().getName();
                    String email = response.body().getResponse().getInfoUser().getEmail();
                    String gender = response.body().getResponse().getInfoUser().getGender();
                    String birthday = response.body().getResponse().getInfoUser().getBirthday();

                    MySharePreferences setSharePf = new MySharePreferences();
                    setSharePf.SaveSharePref(getActivity(), "token", token);
                    setSharePf.SaveSharePref(getActivity(), "access_token", access_token);
                    setSharePf.SaveSharePref(getActivity(), "token_type", token_type);
                    setSharePf.SaveSharePref(getActivity(), "object_user_id", object_user_id);
                    setSharePf.SaveSharePref(getActivity(), "telephoneNumber", telephoneNumber);
                    setSharePf.SaveSharePref(getActivity(), "name", fullname);
                    setSharePf.SaveSharePref(getActivity(), "email", email);
                    setSharePf.SaveSharePref(getActivity(), "gender", gender);
                    setSharePf.SaveSharePref(getActivity(), "birthday", birthday);

                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.putExtra("account", "true");
                    startActivity(intent);
                    getActivity().finish();
                }
            }

            @Override
            public void onFailure(Call<BaseGetApiData> call, Throwable t) {

            }
        });
    }

    private void getApiChangePass(String telephone, String otp, String password, String confirm_pass, String time, String sign, String type_app) {
        Call<BaseGetApiData> call = apiServer.registerAccount(telephone, otp, password, confirm_pass, time, sign, type_app);
        call.enqueue(new Callback<BaseGetApiData>() {
            @Override
            public void onResponse(Call<BaseGetApiData> call, Response<BaseGetApiData> response) {
                if (response.body().getCode() != 200) {
                    Toast.makeText(getActivity(), response.body().getMessage().toString(), Toast.LENGTH_SHORT).show();
                } else {
                    String token = response.body().getResponse().getToken();
                    String access_token = response.body().getResponse().getAccessToken();
                    String token_type = response.body().getResponse().getTokenType();
                    String object_user_id = response.body().getResponse().getInfoUser().getObjectUserId();
                    String telephoneNumber = response.body().getResponse().getInfoUser().getTelephone();
                    String fullname = response.body().getResponse().getInfoUser().getName();
                    String email = response.body().getResponse().getInfoUser().getEmail();
                    String gender = response.body().getResponse().getInfoUser().getGender();
                    String birthday = response.body().getResponse().getInfoUser().getBirthday();

                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame_account, new RefCodeFrament());
                    fragmentTransaction.addToBackStack("frag_change_pass");
                    fragmentTransaction.commit();

                    MySharePreferences setSharePf = new MySharePreferences();
                    setSharePf.SaveSharePref(getActivity(), "token", token);
                    setSharePf.SaveSharePref(getActivity(), "access_token", access_token);
                    setSharePf.SaveSharePref(getActivity(), "token_type", token_type);
                    setSharePf.SaveSharePref(getActivity(), "object_user_id", object_user_id);
                    setSharePf.SaveSharePref(getActivity(), "telephoneNumber", telephoneNumber);
                    setSharePf.SaveSharePref(getActivity(), "name", fullname);
                    setSharePf.SaveSharePref(getActivity(), "email", email);
                    setSharePf.SaveSharePref(getActivity(), "gender", gender);
                    setSharePf.SaveSharePref(getActivity(), "birthday", birthday);

                }
            }

            @Override
            public void onFailure(Call<BaseGetApiData> call, Throwable t) {
                System.out.println("------>" + t.toString());
            }
        });
    }
}
