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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;

import com.gvtechcom.myshop.MainActivity;
import com.gvtechcom.myshop.Model.BaseGetApiData;
import com.gvtechcom.myshop.Model.CountryInfoModel;
import com.gvtechcom.myshop.Model.GetAddressIdAddress;
import com.gvtechcom.myshop.Network.APIServer;
import com.gvtechcom.myshop.Network.RetrofitBuilder;
import com.gvtechcom.myshop.R;
import com.gvtechcom.myshop.Utils.Const;
import com.gvtechcom.myshop.Utils.GetMD5;
import com.gvtechcom.myshop.Utils.GetTime;
import com.gvtechcom.myshop.Utils.MySharePreferences;
import com.gvtechcom.myshop.dialog.DialogCityAddress;
import com.gvtechcom.myshop.dialog.DialogCountryAddress;
import com.gvtechcom.myshop.dialog.DialogCustomMessage;
import com.gvtechcom.myshop.dialog.DialogDistricAddress;
import com.gvtechcom.myshop.dialog.DialogPhoneCodeAddress;
import com.gvtechcom.myshop.dialog.ToastDialog;
import com.mylibrary.ui.progress.ProgressDialogCustom;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

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
    private CountryInfoModel.CountryInfoModelParser countryInfoModel;
    private int positionCountry;
    private List<CountryInfoModel.City> cityInfos;
    private List<CountryInfoModel.Data> dataDistric;
    private List<CountryInfoModel.Data> dataWard;
    private List<String> dataPhoneCode;
    private String nameCountry;
    private String idAddress;

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
        setSwitchDefault();
        setRetrofit();
        init();
        getApiCountryAddress();
    }

    private void setRetrofit() {
        Retrofit retrofit;
        retrofit = RetrofitBuilder.getRetrofit(Const.BASE_URL);
        apiServer = retrofit.create(APIServer.class);
    }

    private void init() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            txtDeleteShippingAddress.setVisibility(View.VISIBLE);
            idAddress = bundle.getString("idAddress");
            getAddressId(idAddress);
        } else {
            txtDeleteShippingAddress.setVisibility(View.INVISIBLE);
            idAddress = " ";
        }

        hashMapData = new HashMap<>();
        hashMapData.clear();
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
                setDialogCountry();
                break;

            case R.id.text_city_add_shipping_address:
                setDialogCity();
                break;

            case R.id.text_distric_add_shipping_address:
                setDialogDistric();
                break;

            case R.id.text_ward_add_shipping_address:
                setDialogWard();
                break;

            case R.id.text_phone_code_add_shipping_address:
                setDialogPhoneCode();
                break;

            case R.id.txt_delete_shipping_address:
                deleteAddress(idAddress);
                break;
        }
    }

    private void onListenKeyboard() {
        KeyboardVisibilityEvent.setEventListener(
                getActivity(),
                new KeyboardVisibilityEventListener() {
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

    private void getApiCountryAddress() {
//        progressDialogCustom.onShow(false, "Loading...");
        GetMD5 getMD5 = new GetMD5();
        GetTime getTime = new GetTime();
        String timeSign = String.valueOf((getTime.getCalendar() + 30000));

        Call<CountryInfoModel.CountryInfoModelParser> CallCountry = apiServer.GetApiShippingCountryAddress(String.valueOf(getTime.getCalendar()), getMD5.md5(timeSign), timeSign);
        CallCountry.enqueue(new Callback<CountryInfoModel.CountryInfoModelParser>() {
            @Override
            public void onResponse(Call<CountryInfoModel.CountryInfoModelParser> call, Response<CountryInfoModel.CountryInfoModelParser> response) {
                if (response.body().code != 200) {
                    toastDialog.onShow(response.body().message);
                } else {
                    countryInfoModel = response.body();
                }
            }

            @Override
            public void onFailure(Call<CountryInfoModel.CountryInfoModelParser> call, Throwable t) {
                toastDialog.onShow("An error occurred, please try again later");
            }
        });
    }

    private void getApiDistric(String idCity) {
        progressDialogCustom.onShow(false, "Loading...");
        GetMD5 getMD5 = new GetMD5();
        GetTime getTime = new GetTime();
        String timeSign = String.valueOf((getTime.getCalendar() + 30000));

        Call<CountryInfoModel.CountryInfoModelParser> call = apiServer.GetListDistrict(String.valueOf(getTime.getCalendar()), getMD5.md5(timeSign), "Android", idCity);
        call.enqueue(new Callback<CountryInfoModel.CountryInfoModelParser>() {
            @Override
            public void onResponse(Call<CountryInfoModel.CountryInfoModelParser> call, Response<CountryInfoModel.CountryInfoModelParser> response) {
                if (response.body().code != 200) {
                    progressDialogCustom.onHide();
                    toastDialog.onShow(response.body().message);
                } else {
                    progressDialogCustom.onHide();
                    dataDistric = response.body().response.data;
                }
            }

            @Override
            public void onFailure(Call<CountryInfoModel.CountryInfoModelParser> call, Throwable t) {
                toastDialog.onShow("An error occurred, please try again later");
            }
        });
    }

    private void getApiWard(String idWard) {
        progressDialogCustom.onShow(false, "Loading...");
        GetMD5 getMD5 = new GetMD5();
        GetTime getTime = new GetTime();
        String timeSign = String.valueOf((getTime.getCalendar() + 30000));
        Call<CountryInfoModel.CountryInfoModelParser> callWard = apiServer.GetListWard(String.valueOf(getTime.getCalendar()), getMD5.md5(timeSign), "Android", idWard);
        callWard.enqueue(new Callback<CountryInfoModel.CountryInfoModelParser>() {
            @Override
            public void onResponse(Call<CountryInfoModel.CountryInfoModelParser> call, Response<CountryInfoModel.CountryInfoModelParser> response) {
                if (response.body().code != 200) {
                    progressDialogCustom.onHide();
                    toastDialog.onShow(response.body().message);
                } else {
                    progressDialogCustom.onHide();
                    dataWard = response.body().response.data;
                }
            }

            @Override
            public void onFailure(Call<CountryInfoModel.CountryInfoModelParser> call, Throwable t) {
                toastDialog.onShow("An error occurred, please try again later");

            }
        });
    }

    private void setDialogCountry() {
        if (countryInfoModel != null) {
            DialogCountryAddress dialogAddress = new DialogCountryAddress(getActivity(), countryInfoModel.response.data);
            int width = (int) (getActivity().getResources().getDisplayMetrics().widthPixels * 0.90);
            int height = (int) (getActivity().getResources().getDisplayMetrics().heightPixels * 0.75);
            dialogAddress.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialogAddress.getWindow().setLayout(width, height);
            dialogAddress.show();

            dialogAddress.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    positionCountry = dialogAddress.getPosition();
                    dataPhoneCode = dialogAddress.getLsCountry().get(positionCountry).phone_code;
                    txtPhoneCode.setText(dialogAddress.getLsCountry().get(positionCountry).phone_code.get(0) + " ");
                    hashMapData.put("PhoneCode", dialogAddress.getLsCountry().get(positionCountry).phone_code.get(0));
                    nameCountry = dialogAddress.getLsCountry().get(positionCountry).name;
                    txtCountyShipping.setText(nameCountry);
                    String idCountry = dialogAddress.getLsCountry().get(positionCountry).id;
                    cityInfos = dialogAddress.getLsCountry().get(positionCountry).cities;
                    if (!idCountry.equals(hashMapData.get("Country"))) {
                        txtCityShipping.setText(null);
                        txtDistricAddress.setText(null);
                        txtWardShipping.setText(null);
                        dataDistric = null;
                        dataWard = null;
                    }
                    hashMapData.put("Country", dialogAddress.getLsCountry().get(positionCountry).id);

                }
            });
        }
    }

    private void setDialogCity() {
        if (countryInfoModel != null && cityInfos != null) {
            DialogCityAddress dialogCityAddress = new DialogCityAddress(getActivity(), cityInfos, txtCityShipping.getText().toString());
            int width = (int) (getActivity().getResources().getDisplayMetrics().widthPixels * 0.90);
            int height = (int) (getActivity().getResources().getDisplayMetrics().heightPixels * 0.75);
            dialogCityAddress.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialogCityAddress.getWindow().setLayout(width, height);
            dialogCityAddress.show();
            dialogCityAddress.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    if (dialogCityAddress.getSelect()) {
                        txtCityShipping.setText(dialogCityAddress.getNameCity());
                        String idCity = dialogCityAddress.getIdCity();
                        if (hashMapData.get("City") != null && idCity != null) {
                            if (!idCity.equals(hashMapData.get("City"))) {
                                txtDistricAddress.setText(null);
                                txtWardShipping.setText(null);
                                dataDistric = null;
                                dataWard = null;
                                hashMapData.put("Distric", null);
                                hashMapData.put("Ward", null);
                            }
                        }

                        hashMapData.put("City", dialogCityAddress.getIdCity());
                        getApiDistric(dialogCityAddress.getIdCity());

                    }
                }
            });
        }
    }

    private void setDialogPhoneCode() {
        if (countryInfoModel != null && dataPhoneCode != null) {
            DialogPhoneCodeAddress dialogPhoneCodeAddress = new DialogPhoneCodeAddress(getActivity(), dataPhoneCode, nameCountry);
            int width = (int) (getActivity().getResources().getDisplayMetrics().widthPixels * 0.90);
            int height = (int) (getActivity().getResources().getDisplayMetrics().heightPixels * 0.75);
            dialogPhoneCodeAddress.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialogPhoneCodeAddress.getWindow().setLayout(width, height);
            dialogPhoneCodeAddress.show();
            dialogPhoneCodeAddress.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    txtPhoneCode.setText(dialogPhoneCodeAddress.getPhoneCode());
                    hashMapData.put("PhoneCode", dialogPhoneCodeAddress.getPhoneCode());
                }
            });
        }
    }

    private void setDialogDistric() {
        if (dataDistric != null) {
            DialogDistricAddress dialogDistricAddress = new DialogDistricAddress(getActivity(), dataDistric, txtDistricAddress.getText().toString(), "distric");
            int width = (int) (getActivity().getResources().getDisplayMetrics().widthPixels * 0.90);
            int height = (int) (getActivity().getResources().getDisplayMetrics().heightPixels * 0.75);
            dialogDistricAddress.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialogDistricAddress.getWindow().setLayout(width, height);
            dialogDistricAddress.show();
            dialogDistricAddress.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    if (dialogDistricAddress.getSlect()) {
                        txtDistricAddress.setText(dialogDistricAddress.getNameDistric());
                        String idistric = dialogDistricAddress.getIdDistric();

                        if (hashMapData.get("Distric") != null && idistric != null) {
                            if (!idistric.equals(hashMapData.get("Distric"))) {
                                txtWardShipping.setText(null);
                                dataWard = null;
                                hashMapData.put("Ward", null);
                            }
                        }
                        hashMapData.put("Distric", dialogDistricAddress.getIdDistric());
                        getApiWard(dialogDistricAddress.getIdDistric());
                    }
                }
            });
        }
    }

    private void setDialogWard() {
        if (dataWard != null) {
            DialogDistricAddress dialogDistricAddress = new DialogDistricAddress(getActivity(), dataWard, txtWardShipping.getText().toString(), "ward");
            int width = (int) (getActivity().getResources().getDisplayMetrics().widthPixels * 0.90);
            int height = (int) (getActivity().getResources().getDisplayMetrics().heightPixels * 0.75);
            dialogDistricAddress.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialogDistricAddress.getWindow().setLayout(width, height);
            dialogDistricAddress.show();
            dialogDistricAddress.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    if (dialogDistricAddress.getSlect()) {
                        txtWardShipping.setText(dialogDistricAddress.getNameDistric());
                        hashMapData.put("Ward", dialogDistricAddress.getIdDistric());
                    }
                }
            });
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

    private void setSwitchDefault() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.getBoolean("Default")) {
                switchDefault.setChecked(true);
                isDefault = 1;
            } else {
                switchDefault.setChecked(false);
                isDefault = 0;
            }

        }
        switchDefault.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isDefault = 1;
                } else {
                    isDefault = 0;
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

    private void getAddressId(String idAdd) {
        progressDialogCustom.onShow(false, "Loading...");
        MySharePreferences preferences = new MySharePreferences();
        String AccessToken = preferences.GetSharePref(getActivity(), "access_token");
        String Token = preferences.GetSharePref(getActivity(), "token");
        String Token_type = preferences.GetSharePref(getActivity(), "token_type");
        String userId = preferences.GetSharePref(getActivity(), "object_user_id");
        String Authorization = Token_type + " " + Token;

        GetMD5 getMD5 = new GetMD5();
        GetTime getTime = new GetTime();
        String timeSign = String.valueOf((getTime.getCalendar() + 30000));

        Call<GetAddressIdAddress.GetAddressIdParser> call = apiServer.getAddressId("application/json", Authorization, String.valueOf(getTime.getCalendar()), getMD5.md5_2(AccessToken, timeSign), "Android", userId, idAdd);
        call.enqueue(new Callback<GetAddressIdAddress.GetAddressIdParser>() {
            @Override
            public void onResponse(Call<GetAddressIdAddress.GetAddressIdParser> call, Response<GetAddressIdAddress.GetAddressIdParser> response) {
                if (response.code() == 401) {
                    toastDialog.onShow("You have been logged out. Please login again");
                    progressDialogCustom.onHide();
                } else {
                    if (response.body().code != 200) {
                        toastDialog.onShow(response.body().message);
                        progressDialogCustom.onHide();
                    } else {
                        GetAddressIdAddress.GetAddressIdParser dataAddressId = response.body();
                        if (dataAddressId != null) {

                            GetAddressIdAddress.CountryInfos countryInfo = dataAddressId.response.country;
                            GetAddressIdAddress.CityInfo cityInfo = dataAddressId.response.city;
                            GetAddressIdAddress.DistricInfo districInfo = dataAddressId.response.district;
                            GetAddressIdAddress.WardInfo wardInfo = dataAddressId.response.ward;
                            String fullName = dataAddressId.response.getName();
                            String phoneNumber = dataAddressId.response.getTelephone();
                            String zipCode = dataAddressId.response.getZip_code();
                            String phoneCode = dataAddressId.response.getPhone_code();
                            String address = dataAddressId.response.getSpecific();
                            String isDefault = dataAddressId.response.getIs_default();

                            getApiDistric(cityInfo.getId());
                            getApiWard(districInfo.getId());

                            hashMapData.put("PhoneCode", phoneCode);
                            hashMapData.put("Country", countryInfo.getId());
                            hashMapData.put("City", cityInfo.getId());
                            hashMapData.put("Distric", districInfo.getId());
                            hashMapData.put("Ward", wardInfo.getId());

                            edtFullNameShippingAddress.setText(fullName);
                            edtPhoneNumber.setText(phoneNumber);
                            txtPhoneCode.setText(phoneCode + " ");
                            edtAddress1.setText(address);
                            txtCountyShipping.setText(countryInfo.getName().toString());
                            txtCityShipping.setText(cityInfo.getName().toString());
                            txtDistricAddress.setText(districInfo.getName().toString());
                            txtWardShipping.setText(wardInfo.getName().toString());
                            edtZipCode.setText(zipCode);
                            if (isDefault.equals("1")) {
                                switchDefault.setChecked(true);
                            } else {
                                switchDefault.setChecked(false);
                            }
                            progressDialogCustom.onHide();
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<GetAddressIdAddress.GetAddressIdParser> call, Throwable t) {
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


