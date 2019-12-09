package com.gvtechcom.myshop.Account;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;

import com.gvtechcom.myshop.MainActivity;
import com.gvtechcom.myshop.Model.BaseGetApiData;
import com.gvtechcom.myshop.Model.CountryInfo;
import com.gvtechcom.myshop.Model.CountryInfoModel;
import com.gvtechcom.myshop.Model.GetAddressIdAddress;
import com.gvtechcom.myshop.Network.APIServer;
import com.gvtechcom.myshop.Network.RetrofitBuilder;
import com.gvtechcom.myshop.R;
import com.gvtechcom.myshop.Utils.Const;
import com.gvtechcom.myshop.Utils.GetMD5;
import com.gvtechcom.myshop.Utils.GetTime;
import com.gvtechcom.myshop.Utils.MySharePreferences;
import com.gvtechcom.myshop.Utils.ValidateCallApi;
import com.gvtechcom.myshop.dialog.DialogCityAddress;
import com.gvtechcom.myshop.dialog.DialogCountryAddress;
import com.gvtechcom.myshop.dialog.DialogCustomMessage;
import com.gvtechcom.myshop.dialog.DialogDistricAddress;
import com.gvtechcom.myshop.dialog.DialogPhoneCodeAddress;
import com.gvtechcom.myshop.dialog.ToastDialog;
import com.mylibrary.ui.progress.ProgressDialogCustom;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddShippingAddessFragment extends Fragment {
    private View rootView;
    private ProgressDialogCustom progressDialogCustom;
    private ToastDialog toastDialog;
    private int isDefault = 1;
    private APIServer apiServer;
    private HashMap<String, String> hashMapData;
    private MainActivity mainActivity;
    private FragmentManager fragmentManager;
    private List<CountryInfoModel.Data> lsDataCountry;
    private DialogCountryAddress dialogCountryAddress;
    private List<CountryInfoModel.City> lsDataCity;
    private List<CountryInfoModel.Data> lsDataDistric;
    private List<CountryInfoModel.Data> lsDatadataWard;
    private List<String> lsDataPhoneCode;
    private DialogPhoneCodeAddress dialogPhoneCodeAddress;
    private String nameCountry, phoneCode, nameCity, nameDistric, nameWard;
    private static String idAddress;

    @BindView(R.id.edt_fullname_shipping_address)
    EditText edtFullNameShippingAddress;
    @BindView(R.id.edt_phoneNumber_shipping_address)
    EditText edtPhoneNumber;
    @BindView(R.id.edt_address_line1_shipping_address)
    EditText edtAddress1;
    @BindView(R.id.text_distric_add_shipping_address)
    TextView txtDistricAddress;
    @BindView(R.id.text_phone_code_add_shipping_address)
    TextView txtPhoneCode;
    @BindView(R.id.text_country_add_shipping_address)
    TextView txtCountyShipping;
    @BindView(R.id.text_city_add_shipping_address)
    TextView txtCityShipping;
    @BindView(R.id.text_ward_add_shipping_address)
    TextView txtWardShipping;
    @BindView(R.id.edt_zip_code_shipping_address)
    EditText edtZipCode;
    @BindView(R.id.swt_set_default_shipping_address)
    SwitchCompat switchDefault;
    @BindView(R.id.layout_main_add_shipping)
    LinearLayout layoutMainAddShipping;
    @BindView(R.id.txt_delete_shipping_address)
    TextView txtDeleteShippingAddress;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_add_shipping_address, container, false);
        ButterKnife.bind(this, rootView);

        fragmentManager = getFragmentManager();
        mainActivity = (MainActivity) getActivity();
        mainActivity.setColorIconDarkMode(true, R.color.color_startusBar_white);

        progressDialogCustom = new ProgressDialogCustom(getActivity());
        toastDialog = new ToastDialog(getActivity());

        onListenKeyboard();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRetrofit();
        init();
    }


    private void setRetrofit() {
        Retrofit retrofit;
        retrofit = RetrofitBuilder.getRetrofit(Const.BASE_URL);
        apiServer = retrofit.create(APIServer.class);
    }

    private void init() {
        Bundle bundle = getArguments();
        this.idAddress = bundle.getString("idAddress");
        getApiCountry();
    }

    @OnClick({R.id.img_back_add_shipping_address, R.id.btn_save_shipping_address, R.id.txt_delete_shipping_address,
            R.id.text_country_add_shipping_address, R.id.text_city_add_shipping_address, R.id.text_distric_add_shipping_address,
            R.id.text_ward_add_shipping_address, R.id.text_phone_code_add_shipping_address})
    void clickBack(View view) {
        switch (view.getId()) {
            case R.id.img_back_add_shipping_address:
                fragmentManager.popBackStack();
                break;

            case R.id.btn_save_shipping_address:
                saveAddress();
                break;

            case R.id.text_country_add_shipping_address:
                if (dialogCountryAddress != null & lsDataCountry != null) {
                    dialogCountryAddress.show();
                }
                break;

            case R.id.text_city_add_shipping_address:
                if (lsDataCity != null){
                    DialogCityAddress dialogCityAddress = new DialogCityAddress(getActivity(), lsDataCity, "City");
                    dialogCityAddress.show();
                    dialogCityAddress.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            txtCityShipping.setText(dialogCityAddress.getNameCity());
                            getApiDistric(dialogCityAddress.getIdCity());

                        }
                    });
                }
                break;

            case R.id.text_distric_add_shipping_address:
                if (lsDataDistric != null){
                    DialogDistricAddress dialogDistricAddress = new DialogDistricAddress(getActivity(), lsDataDistric, "sdasd", "Select Your Distric");
                    dialogDistricAddress.show();
                    dialogDistricAddress.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            txtDistricAddress.setText(dialogDistricAddress.getNameDistric());
                            getApiWard(dialogDistricAddress.getIdDistric());
                        }
                    });
                }
                break;

            case R.id.text_ward_add_shipping_address:
                if (lsDatadataWard != null){
                    DialogDistricAddress dialogDistricAddress = new DialogDistricAddress(getActivity(), lsDatadataWard, "", "Select Your Ward");
                    dialogDistricAddress.show();
                    dialogCountryAddress.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            txtWardShipping.setText(dialogDistricAddress.getNameDistric());
                        }
                    });
                }
                break;

            case R.id.text_phone_code_add_shipping_address:
                if (lsDataPhoneCode != null){
                    dialogPhoneCodeAddress = new DialogPhoneCodeAddress(getActivity(), lsDataPhoneCode, nameCountry);
                    dialogPhoneCodeAddress.show();
                }

                break;

            case R.id.txt_delete_shipping_address:
                deleteAddress(idAddress);
                break;
        }
    }

    private void getApiCountry() {
        GetMD5 getMD5 = new GetMD5();
        GetTime getTime = new GetTime();
        String timeSign = String.valueOf((getTime.getCalendar() + 30000));
        Call<CountryInfoModel.CountryInfoModelParser> countryCallApi = apiServer.GetApiShippingCountryAddress(String.valueOf(getTime.getCalendar()), getMD5.md5(timeSign), "Android");
        countryCallApi.enqueue(new Callback<CountryInfoModel.CountryInfoModelParser>() {
            @Override
            public void onResponse(Call<CountryInfoModel.CountryInfoModelParser> call, Response<CountryInfoModel.CountryInfoModelParser> response) {
                if (ValidateCallApi.ValidateAip(getActivity(), response.body().code, response.body().message)) {
                    lsDataCountry = response.body().response.data;
                    dialogCountryAddress = new DialogCountryAddress(getActivity(), lsDataCountry);
                    dialogCountryAddress.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            txtCountyShipping.setText(dialogCountryAddress.getLsCountry().get(dialogCountryAddress.getPosition()).name);
                            nameCountry = dialogCountryAddress.getLsCountry().get(dialogCountryAddress.getPosition()).name;
                            txtPhoneCode.setText(dialogCountryAddress.getLsCountry().get(dialogCountryAddress.getPosition()).phone_code.get(0) + " ");
                            lsDataPhoneCode = dialogCountryAddress.getLsCountry().get(dialogCountryAddress.getPosition()).phone_code;
                            lsDataCity = lsDataCountry.get(dialogCountryAddress.getPosition()).cities;
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<CountryInfoModel.CountryInfoModelParser> call, Throwable t) {

            }
        });
    }
    private void getApiDistric(String idDistric){
        GetMD5 getMD5 = new GetMD5();
        GetTime getTime = new GetTime();
        String timeSign = String.valueOf((getTime.getCalendar() + 30000));
        Call<CountryInfoModel.CountryInfoModelParser> callApiDistric = apiServer.GetListDistrict(String.valueOf(getTime.getCalendar()), getMD5.md5(timeSign), "Android", idDistric);
        callApiDistric.enqueue(new Callback<CountryInfoModel.CountryInfoModelParser>() {
            @Override
            public void onResponse(Call<CountryInfoModel.CountryInfoModelParser> call, Response<CountryInfoModel.CountryInfoModelParser> response) {
                if (ValidateCallApi.ValidateAip(getActivity(), response.body().code, response.body().message)){
                    lsDataDistric = response.body().response.data;
                }
            }

            @Override
            public void onFailure(Call<CountryInfoModel.CountryInfoModelParser> call, Throwable t) {

            }
        });

    }
    private void getApiWard(String idWard){
        GetMD5 getMD5 = new GetMD5();
        GetTime getTime = new GetTime();
        String timeSign = String.valueOf((getTime.getCalendar() + 30000));
            Call<CountryInfoModel.CountryInfoModelParser> callApiDistric = apiServer.GetListWard(String.valueOf(getTime.getCalendar()), getMD5.md5(timeSign), "Android", idWard);
        callApiDistric.enqueue(new Callback<CountryInfoModel.CountryInfoModelParser>() {
            @Override
            public void onResponse(Call<CountryInfoModel.CountryInfoModelParser> call, Response<CountryInfoModel.CountryInfoModelParser> response) {
                if (ValidateCallApi.ValidateAip(getActivity(), response.body().code, response.body().message)){
                    lsDatadataWard = response.body().response.data;
                }
            }

            @Override
            public void onFailure(Call<CountryInfoModel.CountryInfoModelParser> call, Throwable t) {

            }
        });
    }


    private void onListenKeyboard() {
        KeyboardVisibilityEvent.setEventListener(getActivity(), new KeyboardVisibilityEventListener() {
            @Override
            public void onVisibilityChanged(boolean isOpen) {
                if (isOpen) {
                    mainActivity.setHideButtonNavigation(true);
                    mainActivity.setupUI(layoutMainAddShipping);
                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mainActivity.setHideButtonNavigation(false);
                        }
                    }, 100);
                }
            }
        });
    }
    private void saveAddress() {
        String fullName = edtFullNameShippingAddress.getText().toString();
        String telephone = edtPhoneNumber.getText().toString();
        String addressDefault = edtAddress1.getText().toString();
        String zipCode = edtZipCode.getText().toString();

        if (hashMapData.get("Country") == null || TextUtils.isEmpty(hashMapData.get("Country")) ||
                hashMapData.get("City") == null || TextUtils.isEmpty(hashMapData.get("City")) ||
                hashMapData.get("Distric") == null || TextUtils.isEmpty(hashMapData.get("Distric")) ||
                hashMapData.get("Ward") == null || TextUtils.isEmpty(hashMapData.get("Ward")) ||
                hashMapData.get("PhoneCode") == null || TextUtils.isEmpty(hashMapData.get("PhoneCode")) ||
                TextUtils.isEmpty(fullName) || TextUtils.isEmpty(telephone) ||
                TextUtils.isEmpty(addressDefault) || TextUtils.isEmpty(zipCode)) {
            toastDialog.onShow("Please fill out the form");
        } else {
            String country = hashMapData.get("Country");
            String city = hashMapData.get("City");
            String distric = hashMapData.get("Distric");
            String ward = hashMapData.get("Ward");
            String phoneCode = hashMapData.get("PhoneCode");

            if (idAddress.equals(" ")) {
                callApiCreateAdrress(fullName, phoneCode, telephone, addressDefault, country, city, distric, ward, zipCode);

            } else {
                updateAddressId(idAddress, country, addressDefault, city, distric, ward, phoneCode, telephone,
                        fullName, zipCode, String.valueOf(isDefault));
            }
        }
    }

    private void deleteAddress(String idAddress) {
        DialogCustomMessage dialogCustomMessage = new DialogCustomMessage(getActivity(), "Delete Address", "You agree to delete this address");
        dialogCustomMessage.show();
        dialogCustomMessage.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (dialogCustomMessage.getSelect() == true) {
                    progressDialogCustom.onShow(false, "Detele...");
                    MySharePreferences preferences = new MySharePreferences();
                    String AccessToken = preferences.GetSharePref(getActivity(), "access_token");
                    String Token = preferences.GetSharePref(getActivity(), "token");
                    String Token_type = preferences.GetSharePref(getActivity(), "token_type");
                    String Authorization = Token_type + " " + Token;

                    GetMD5 getMD5 = new GetMD5();
                    GetTime getTime = new GetTime();
                    String timeSign = String.valueOf((getTime.getCalendar() + 30000));
                    Call<BaseGetApiData> call = apiServer.DeleteAddress("application/json", Authorization, String.valueOf(getTime.getCalendar()), getMD5.md5_2(AccessToken, timeSign), "Android", idAddress);
                    call.enqueue(new Callback<BaseGetApiData>() {
                        @Override
                        public void onResponse(Call<BaseGetApiData> call, Response<BaseGetApiData> response) {
                            if (response.code() == 401) {
                                toastDialog.onShow("You have been logged out. Please login again");
                                progressDialogCustom.onHide();
                            } else {
                                if (response.body().getCode() != 200) {
                                    toastDialog.onShow(response.body().getMessage());
                                    progressDialogCustom.onHide();
                                } else {
                                    progressDialogCustom.onHide();
                                    toastDialog.onShow(response.body().getMessage());
                                    toastDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                        @Override
                                        public void onDismiss(DialogInterface dialog) {
                                            fragmentManager.popBackStack();
                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<BaseGetApiData> call, Throwable t) {
                            toastDialog.onShow("An error occurred, please try again later");
                        }
                    });
                }
            }
        });
    }

    private void callApiCreateAdrress(String fullName, String phoneCode, String phoneNumber, String address, String country
            , String city, String distric, String ward, String zipCode) {
        progressDialogCustom.onShow(false, "Create Account...");
        MySharePreferences preferences = new MySharePreferences();
        String AccessToken = preferences.GetSharePref(getActivity(), "access_token");
        String Token = preferences.GetSharePref(getActivity(), "token");
        String Token_type = preferences.GetSharePref(getActivity(), "token_type");
        String Authorization = Token_type + " " + Token;

        GetMD5 getMD5 = new GetMD5();
        GetTime getTime = new GetTime();
        String timeSign = String.valueOf((getTime.getCalendar() + 30000));
        Call<BaseGetApiData> call = apiServer.CreateAdrress("application/json", Authorization, distric, phoneNumber, ward, city, fullName,
                address, country, String.valueOf(isDefault), zipCode, phoneCode, String.valueOf(getTime.getCalendar()),
                getMD5.md5_2(AccessToken, timeSign), "Android");
        call.enqueue(new Callback<BaseGetApiData>() {
            @Override
            public void onResponse(Call<BaseGetApiData> call, Response<BaseGetApiData> response) {
                if (response.body().getCode() != 200) {
                    toastDialog.onShow(response.body().getMessage());
                    progressDialogCustom.onHide();
                } else {
                    progressDialogCustom.onHide();
                    fragmentManager.popBackStack();
                }
            }

            @Override
            public void onFailure(Call<BaseGetApiData> call, Throwable t) {
                progressDialogCustom.onHide();
                toastDialog.onShow("An error occurred, please try again later");
            }
        });
    }

    private void updateAddressId(String addressid, String country, String address, String city, String distric, String ward, String phoneCode, String phoneNumber,
                                 String fullName, String zipCode, String isDefault) {
        progressDialogCustom.onShow(false, "Update...");
        MySharePreferences preferences = new MySharePreferences();
        String AccessToken = preferences.GetSharePref(getActivity(), "access_token");
        String Token = preferences.GetSharePref(getActivity(), "token");
        String Token_type = preferences.GetSharePref(getActivity(), "token_type");
        String userId = preferences.GetSharePref(getActivity(), "object_user_id");
        String Authorization = Token_type + " " + Token;

        GetMD5 getMD5 = new GetMD5();
        GetTime getTime = new GetTime();
        String timeSign = String.valueOf((getTime.getCalendar() + 30000));


        Call<BaseGetApiData> call = apiServer.UpdateAdrress("application/json", Authorization, addressid, String.valueOf(getTime.getCalendar()), getMD5.md5_2(AccessToken, timeSign), "Android", userId, fullName, phoneNumber, city, ward,
                distric, country, address, isDefault, zipCode, phoneCode);

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
                        progressDialogCustom.onHide();
                        toastDialog.onShow(response.body().getMessage());
                        toastDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                fragmentManager.popBackStack();
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseGetApiData> call, Throwable t) {
                toastDialog.onShow("An error occurred, please try again later");
            }
        });
    }
}


