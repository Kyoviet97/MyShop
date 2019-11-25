package com.gvtechcom.myshop.Network;

import com.gvtechcom.myshop.Model.BaseGetApiAddress;
import com.gvtechcom.myshop.Model.BaseGetApiData;
import com.gvtechcom.myshop.Model.CountryInfo;
import com.gvtechcom.myshop.Model.FlashDealsDetails;
import com.gvtechcom.myshop.Model.GetAddressIdAddress;
import com.gvtechcom.myshop.Model.ItemYouLoveModel;
import com.gvtechcom.myshop.Model.UpdateNotifyModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIServer {
    @POST("user/register-phone")
    Call<BaseGetApiData> registerPhone(@Query("telephone") String phoneNumber,
                                       @Query("time") String time,
                                       @Query("sign") String sign,
                                       @Query("type_app") String type_app);

    @POST("user/register-confirm-otp")
    Call<BaseGetApiData> otpconfirm(@Query("telephone") String phoneNumber,
                                    @Query("otp") String otpNumber,
                                    @Query("time") String time,
                                    @Query("sign") String sign,
                                    @Query("type_app") String type_app);

    @POST("user/register-account")
    Call<BaseGetApiData> registerAccount(@Query("telephone") String phoneNumber,
                                         @Query("otp") String otpNumber,
                                         @Query("password") String password,
                                         @Query("confirm_pass") String confirm_pass,
                                         @Query("time") String time,
                                         @Query("sign") String sign,
                                         @Query("type_app") String type_app);

    @POST("user/login")
    Call<BaseGetApiData> LoginAccount(@Query("telephone") String phoneNumber,
                                      @Query("password") String password,
                                      @Query("time") String time,
                                      @Query("sign") String sign,
                                      @Query("type_app") String type_app);

    @POST("user/change-password")
    Call<BaseGetApiData> ChangePassWord(@Header("Accept") String Accept,
                                        @Header("Authorization") String Authorization,
                                        @Query("pass_current") String pass_current,
                                        @Query("new_pass") String new_pass,
                                        @Query("confirm_pass") String confirm_pass,
                                        @Query("time") String time,
                                        @Query("sign") String sign,
                                        @Query("type_app") String type_app);


    @POST("user/forgot-password-send-otp")
    Call<BaseGetApiData> ForgotPasswordSendOtp(@Query("telephone") String pass_current,
                                               @Query("time") String new_pass,
                                               @Query("sign") String confirm_pass,
                                               @Query("type_app") String time);

    @POST("user/forgot-password")
    Call<BaseGetApiData> ForgotPassword(@Query("telephone") String telephone,
                                        @Query("otp") String otp,
                                        @Query("password") String password,
                                        @Query("confirm_pass") String confirm_pass,
                                        @Query("time") String time,
                                        @Query("sign") String sign,
                                        @Query("type_app") String type_app);


    @PATCH("user/update-info")
    Call<BaseGetApiData> UpdateInfoName(@Header("Accept") String Accept,
                                        @Header("Authorization") String Authorization,
                                        @Query("name") String name,
                                        @Query("time") String time,
                                        @Query("sign") String sign,
                                        @Query("type_app") String type_app);

    @PATCH("user/update-info")
    Call<BaseGetApiData> UpdateInfoEmail(@Header("Accept") String Accept,
                                         @Header("Authorization") String Authorization,
                                         @Query("email") String email,
                                         @Query("time") String time,
                                         @Query("sign") String sign,
                                         @Query("type_app") String type_app);

    @PATCH("user/update-info")
    Call<BaseGetApiData> UpdateInfoBirthday(@Header("Accept") String Accept,
                                            @Header("Authorization") String Authorization,
                                            @Query("birthday") String birthday,
                                            @Query("time") String time,
                                            @Query("sign") String sign,
                                            @Query("type_app") String type_app);

    @PATCH("user/update-info")
    Call<BaseGetApiData> UpdateInfoGender(@Header("Accept") String Accept,
                                          @Header("Authorization") String Authorization,
                                          @Query("gender") int gender,
                                          @Query("time") String time,
                                          @Query("sign") String sign,
                                          @Query("type_app") String type_app);

    @PATCH("user/logout")
    Call<BaseGetApiData> AccountLogout(@Header("Accept") String Accept,
                                       @Header("Authorization") String Authorization,
                                       @Query("time") String time,
                                       @Query("sign") String sign,
                                       @Query("type_app") String type_app);


    @GET("user/shipping-address")
    Call<BaseGetApiAddress> GetFullAddress(@Header("Accept") String Accept,
                                           @Header("Authorization") String Authorization,
                                           @Query("time") String time,
                                           @Query("sign") String sign,
                                           @Query("type_app") String type_app);

    @GET("user/country-address")
    Call<CountryInfo.CountryParser> GetListCountry(@Query("time") String time,
                                                   @Query("sign") String sign,
                                                   @Query("type_app") String type_app);

    @GET("user/city-address")
    Call<BaseGetApiAddress> GetListDistrict(@Query("time") String time,
                                            @Query("sign") String sign,
                                            @Query("type_app") String type_app,
                                            @Query("id") String id);

    @GET("user/district-address")
    Call<BaseGetApiAddress> GetListWard(@Query("time") String time,
                                        @Query("sign") String sign,
                                        @Query("type_app") String type_app,
                                        @Query("id") String id);

    @POST("user/create-shipping-address")
    Call<BaseGetApiData> CreateAdrress(@Header("Accept") String Accept,
                                       @Header("Authorization") String Authorization,
                                       @Query("district_id") String district_id,
                                       @Query("telephone") String telephone,
                                       @Query("ward_id") String ward_id,
                                       @Query("city_id") String city_id,
                                       @Query("name") String name,
                                       @Query("specific") String specific,
                                       @Query("country_id") String country_id,
                                       @Query("is_default") String is_default,
                                       @Query("zip_code") String zip_code,
                                       @Query("phone_code") String phone_code,
                                       @Query("time") String time,
                                       @Query("sign") String sign,
                                       @Query("type_app") String type_app);

    @GET("user/edit-shipping-address")
    Call<BaseGetApiData> UpdateAdrress(
            @Header("Accept") String Accept,
            @Header("Authorization") String Authorization,
            @Query("address_id") String address_id,
            @Query("time") String time,
            @Query("sign") String sign,
            @Query("type_app") String type_app,
            @Query("user_id") String user_id,
            @Query("name") String name,
            @Query("telephone") String telephone,
            @Query("city_id") String city_id,
            @Query("ward_id") String ward_id,
            @Query("district_id") String district_id,
            @Query("country_id") String country_id,
            @Query("specific") String specific,
            @Query("is_default") String is_default,
            @Query("zip_code") String zip_code,
            @Query("phone_code") String phone_code);


    @GET("user/delete-shipping-address")
    Call<BaseGetApiData> DeleteAddress(@Header("Accept") String Accept,
                                       @Header("Authorization") String Authorization,
                                       @Query("time") String time,
                                       @Query("sign") String sign,
                                       @Query("type_app") String type_app,
                                       @Query("address_id") String address_id);

    @GET("user/show-shipping-address")
    Call<GetAddressIdAddress.GetAddressIdParser> getAddressId(@Header("Accept") String Accept,
                                                              @Header("Authorization") String Authorization,
                                                              @Query("time") String time,
                                                              @Query("sign") String sign,
                                                              @Query("type_app") String type_app,
                                                              @Query("user_id") String user_id,
                                                              @Query("address_id") String address_id);

    @GET("data-home")
    Call<BaseGetApiData> GetDataHomeBannerSlide();

    @GET("items-you-love")
    Call<ItemYouLoveModel.ItemYouLoveModelParser> GetItemYouLove(@Query("page") Integer page,
                                                                    @Query("address_id") Integer per_page);

    @GET("notifications")
    Call<UpdateNotifyModel.UpdateNotifyModelParser> GetDataUpdateNotify();

    @GET("flash-deals")
    Call<FlashDealsDetails.FlashDealsDetailsParser> GetFlashDealsDetails(@Query("page") Integer page,
                                                                        @Query("per_page") Integer per_page,
                                                                        @Query("product_group_id") String product_group_id);

}
