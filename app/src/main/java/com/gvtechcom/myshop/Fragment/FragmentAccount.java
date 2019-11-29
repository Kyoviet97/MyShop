package com.gvtechcom.myshop.Fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.gvtechcom.myshop.Account.FragmentUpdateNotify;
import com.gvtechcom.myshop.Account.ShippingAddressFragment;
import com.gvtechcom.myshop.MainActivity;
import com.gvtechcom.myshop.Model.BaseGetApiData;
import com.gvtechcom.myshop.Network.APIServer;
import com.gvtechcom.myshop.Network.RetrofitBuilder;
import com.gvtechcom.myshop.R;
import com.gvtechcom.myshop.Utils.Const;
import com.gvtechcom.myshop.Utils.GetMD5;
import com.gvtechcom.myshop.Utils.GetTime;
import com.gvtechcom.myshop.Utils.MySharePreferences;
import com.gvtechcom.myshop.dialog.DialogChangePass;
import com.gvtechcom.myshop.dialog.DialogCustomMessage;
import com.gvtechcom.myshop.dialog.DialogEditEmail;
import com.gvtechcom.myshop.dialog.DialogEditFullNane;
import com.gvtechcom.myshop.dialog.DialogEditGender;
import com.gvtechcom.myshop.dialog.ToastDialog;
import com.mylibrary.ui.progress.ProgressDialogCustom;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FragmentAccount extends Fragment {
    private View rootView;
    private Dialog dialogChangeBirthDay;
    private FragmentManager fragmentManager;
    private APIServer apiServer;
    private ProgressDialogCustom progressDialogCustom;
    private ToastDialog toastDialog;
    private MySharePreferences sharePreferences;

    @BindView(R.id.txt_full_name_account)
    TextView txtFullNameAccount;
    @BindView(R.id.txt_phone_number_account)
    TextView txtPhoneNumberAccount;
    @BindView(R.id.txt_email_account)
    TextView txtEmailAccount;
    @BindView(R.id.txt_gender_account)
    TextView txtGenderAccount;
    @BindView(R.id.txt_birthday_account)
    TextView txtBirthdayAccount;
    @BindView(R.id.btn_logout_account)
    Button btnLogoutAccount;
    @BindView(R.id.btn_shipping_address)
    Button btnShippingAddress;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_account, container, false);
        ButterKnife.bind(this, rootView);
        MainActivity mainActivity;
        mainActivity = (MainActivity) getActivity();
        mainActivity.setDisplayNavigationBar(false, false, false);
        mainActivity.setColorIconDarkMode(true, R.color.color_startusBar_white);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        super.onViewCreated(view, savedInstanceState);
        init();

    }

    private void init() {
        fragmentManager = getFragmentManager();
        sharePreferences = new MySharePreferences();
        getDataLocal();

        progressDialogCustom = new ProgressDialogCustom(getActivity());
        toastDialog = new ToastDialog(getActivity());

        Retrofit retrofitClient;
        retrofitClient = RetrofitBuilder.getRetrofit(Const.BASE_URL);
        apiServer = retrofitClient.create(APIServer.class);
    }

    @OnClick({R.id.img_edit_full_name_acoount, R.id.txt_gender_account, R.id.txt_birthday_account, R.id.txt_email_account
            , R.id.btn_change_password_dialog, R.id.btn_logout_account, R.id.btn_shipping_address, R.id.btn_share_to_friends})
    void onClickView(View view) {
        switch (view.getId()) {
            case R.id.img_edit_full_name_acoount:
                setClickButtonEditFullName();
                break;
            case R.id.txt_gender_account:
                setClickButtonGender();
                break;
            case R.id.txt_birthday_account:
                setClickButtonEditBirthday();
                break;
            case R.id.txt_email_account:
                setClickButtonEditEmail();
                break;
            case R.id.btn_change_password_dialog:
                setClickButtonChangePass();
                break;
            case R.id.btn_logout_account:
                setClickButtonLogoutAccount();
                break;
            case R.id.btn_shipping_address:
                setClickShippingAddress();
                break;
            case R.id.btn_share_to_friends:
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_home_frame_layout, new FragmentUpdateNotify());
                fragmentTransaction.commit();
                break;

        }
    }

    private void setClickButtonEditFullName() {
        String srtName = txtFullNameAccount.getText().toString();
        DialogEditFullNane dialogEditFullNane = new DialogEditFullNane(getActivity(), srtName);
        dialogEditFullNane.show();
        dialogEditFullNane.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                getDataLocal();
            }
        });
    }

    private void setClickButtonGender() {
        String strGender = txtGenderAccount.getText().toString();
        DialogEditGender dialogEditGender = new DialogEditGender(getActivity(), strGender);
        dialogEditGender.show();
        dialogEditGender.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                getDataLocal();
            }
        });
    }

    private void setClickButtonEditBirthday() {
        dialogChangeBirthDay = new Dialog(getActivity(), R.style.Theme_Dialog);
        dialogChangeBirthDay.setContentView(R.layout.custom_dialog_edit_birthday);
        dialogChangeBirthDay.setCancelable(true);

        Button btnUpdateBirthday = (Button) dialogChangeBirthDay.findViewById(R.id.btn_update_dialog_change_birthday);
        Button btnCancelBirthday = (Button) dialogChangeBirthDay.findViewById(R.id.btn_cancel_dialog_change_birthday);

        Calendar calendar = Calendar.getInstance();
        Calendar calendarCheck = Calendar.getInstance();
        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);


        NumberPicker numberDay = (NumberPicker) dialogChangeBirthDay.findViewById(R.id.number_picker_d);
        NumberPicker numberMonth = (NumberPicker) dialogChangeBirthDay.findViewById(R.id.number_picker_m);
        NumberPicker numberYear = (NumberPicker) dialogChangeBirthDay.findViewById(R.id.number_picker_y);

        numberDay.setMinValue(1);
        numberDay.setMaxValue(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        numberDay.setValue(day);

        numberMonth.setMinValue(1);
        numberMonth.setMaxValue(12);
        numberMonth.setDisplayedValues(new String[]{"January", "February", "March", "April", "May", "June", "July", "August",
                "September", "October", "November", "December"});
        numberMonth.setValue(month);

        numberYear.setMinValue(year - 150);
        numberYear.setMaxValue(year);
        numberYear.setValue(year);

        numberMonth.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                calendarCheck.set(numberYear.getValue(), newVal - 1, 1);
                numberDay.setMaxValue(calendarCheck.getActualMaximum(Calendar.DAY_OF_MONTH));
            }
        });

        numberYear.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                calendarCheck.set(newVal, numberMonth.getValue() - 1, 1);
                numberDay.setMaxValue(calendarCheck.getActualMaximum(Calendar.DAY_OF_MONTH));
            }
        });

        btnUpdateBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((year - numberYear.getValue()) < 15) {
                    toastDialog.onShow("You are under 15 years old");
                } else {
                    calendar.set(numberYear.getValue(), numberMonth.getValue() - 1, numberDay.getValue());
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/YYYY");
                    String birthday = simpleDateFormat.format(calendar.getTime());
                    loadApiChangeBirthDay(birthday);
                }
            }
        });

        btnCancelBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogChangeBirthDay.dismiss();
            }
        });
        dialogChangeBirthDay.show();
    }

    private void setClickButtonEditEmail() {
        String strEmail = txtEmailAccount.getText().toString();
        DialogEditEmail dialogEditEmail = new DialogEditEmail(getActivity(), strEmail);
        dialogEditEmail.show();
        dialogEditEmail.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (dialogEditEmail.getStrEmail().equals(" ")) {
                } else {
                    getDataLocal();
                }
            }
        });
    }

    private void setClickButtonChangePass() {
        DialogChangePass dialogChangePass = new DialogChangePass(getActivity());
        dialogChangePass.show();
    }

    private void setClickButtonLogoutAccount() {
        accountLogout();
    }

    private void setClickShippingAddress() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_home_frame_layout, new ShippingAddressFragment());
        fragmentTransaction.addToBackStack("frag_account");
        fragmentTransaction.commit();
    }

    private void getDataLocal() {
        txtPhoneNumberAccount.setText(sharePreferences.GetSharePref(getActivity(), "telephoneNumber"));

        String txtFullName = sharePreferences.GetSharePref(getActivity(), "name");
        if (txtFullName.isEmpty() || txtFullName.equals("Update")) {
            txtFullNameAccount.setHint("UPDATE");
        } else {
            txtFullNameAccount.setText(txtFullName);
        }

        String txtEmail = sharePreferences.GetSharePref(getActivity(), "email");
        if (txtEmail.isEmpty() || txtEmail.equals("Update")) {
            txtEmailAccount.setHint("UPDATE");
        } else {
            txtEmailAccount.setText(txtEmail);
        }

        String txtBirthday = sharePreferences.GetSharePref(getActivity(), "birthday");
        if (txtBirthday.isEmpty() || txtBirthday.equals("Update")) {
            txtBirthdayAccount.setHint("UPDATE");
        } else {
            txtBirthdayAccount.setText(txtBirthday);
        }

        String txtGender = sharePreferences.GetSharePref(getActivity(), "gender");
        if (txtGender.isEmpty() || txtGender.equals("Update")) {
            txtGenderAccount.setHint("UPDATE");
        } else {
            if (txtGender.equals("2")) {
                txtGenderAccount.setText("Female");
            } else {
                txtGenderAccount.setText("Male");
            }
        }
    }

    private void loadApiChangeBirthDay(String birthday) {
        progressDialogCustom.onShow(false, "Update ...");

        String AccessToken = sharePreferences.GetSharePref(getActivity(), "access_token");
        String Token = sharePreferences.GetSharePref(getActivity(), "token");
        String Token_type = sharePreferences.GetSharePref(getActivity(), "token_type");
        String Authorization = Token_type + " " + Token;

        GetMD5 getMD5 = new GetMD5();
        GetTime getTime = new GetTime();
        String timeSign = String.valueOf((getTime.getCalendar() + 30000));

        Call<BaseGetApiData> call = apiServer.UpdateInfoBirthday("application/json", Authorization, birthday, String.valueOf(getTime.getCalendar()), getMD5.md5_2(AccessToken, timeSign), "Android");
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
                        sharePreferences.SaveSharePref(getActivity(), "birthday", response.body().getResponse().getDataUser().getBirthday().toString());
                        getDataLocal();
                        progressDialogCustom.onHide();
                        toastDialog.onShow(response.body().getMessage());
                        toastDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                dialogChangeBirthDay.dismiss();
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

    private void accountLogout() {
        DialogCustomMessage dialogCustomMessage = new DialogCustomMessage(getActivity(), "Logout Account", "You want to logout");
        dialogCustomMessage.show();
        dialogCustomMessage.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (dialogCustomMessage.getSelect() == true) {
                    progressDialogCustom.onShow(false, "Logout...");
                    String AccessToken = sharePreferences.GetSharePref(getActivity(), "access_token");
                    String Token = sharePreferences.GetSharePref(getActivity(), "token");
                    String Token_type = sharePreferences.GetSharePref(getActivity(), "token_type");
                    String Authorization = Token_type + " " + Token;

                    GetMD5 getMD5 = new GetMD5();
                    GetTime getTime = new GetTime();
                    String timeSign = String.valueOf((getTime.getCalendar() + 30000));

                    Call<BaseGetApiData> call = apiServer.AccountLogout("application/json", Authorization, String.valueOf(getTime.getCalendar()), getMD5.md5_2(AccessToken, timeSign), "Android");
                    call.enqueue(new Callback<BaseGetApiData>() {
                        @Override
                        public void onResponse(Call<BaseGetApiData> call, Response<BaseGetApiData> response) {
                            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("com.gvtechcom.myshop.firts", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("account", false);
                            editor.putBoolean("firts_launcher", false);
                            editor.apply();

                            progressDialogCustom.onHide();

                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }

                        @Override
                        public void onFailure(Call<BaseGetApiData> call, Throwable t) {
                            progressDialogCustom.onHide();
                        }
                    });
                }
            }
        });


    }


}