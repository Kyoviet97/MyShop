package com.gvtechcom.myshop.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response {
    @SerializedName("telephone")
    @Expose
    private String telephone;

    @SerializedName("otp")
    @Expose
    private String otp;

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("access_token")
    @Expose
    private String accessToken;

    @SerializedName("token_type")
    @Expose
    private String tokenType;

    @SerializedName("info_user")
    @Expose
    private InfoUser infoUser;

    @SerializedName("data_user")
    @Expose
    private DataUser dataUser;

    @SerializedName("banners")
    @Expose
    private List<Banner> banners = null;

    @SerializedName("flash_deals")
    @Expose
    private FlashDeals flashDeals;

    @SerializedName("top_selections")
    @Expose
    private List<TopNewFeaturedStore.TopSelection> topSelections = null;

    @SerializedName("news_for_you")
    @Expose
    private List<TopNewFeaturedStore.NewForYou> newsForYou = null;

    @SerializedName("stores_you_love")
    @Expose
    private List<TopNewFeaturedStore.StoreYouLove> storesYouLove = null;

    @SerializedName("feature_brands")
    @Expose
    private List<TopNewFeaturedStore.FeatureBrands> featureBrands = null;

    @SerializedName("just_for_you")
    @Expose
    private List<JustForYou> justForYou = null;

    @SerializedName("feature_categories")
    @Expose
    private List<FeaturedCategories> feature_categories = null;


    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public InfoUser getInfoUser() {
        return infoUser;
    }

    public void setInfoUser(InfoUser infoUser) {
        this.infoUser = infoUser;
    }

    public DataUser getDataUser() {
        return dataUser;
    }

    public void setDataUser(DataUser dataUser) {
        this.dataUser = dataUser;
    }

    public List<Banner> getBanners() {
        return banners;
    }

    public void setBanners(List<Banner> banners) {
        this.banners = banners;
    }

    public FlashDeals getFlashDeals() {
        return flashDeals;
    }

    public void setFlashDeals(FlashDeals flashDeals) {
        this.flashDeals = flashDeals;
    }

    public List<TopNewFeaturedStore.TopSelection> getTopSelections() {
        return topSelections;
    }

    public void setTopSelections(List<TopNewFeaturedStore.TopSelection> topSelections) {
        this.topSelections = topSelections;
    }

    public List<TopNewFeaturedStore.NewForYou> getNewsForYou() {
        return newsForYou;
    }

    public void setNewsForYou(List<TopNewFeaturedStore.NewForYou> newsForYou) {
        this.newsForYou = newsForYou;
    }

    public List<TopNewFeaturedStore.StoreYouLove> getStoresYouLove() {
        return storesYouLove;
    }

    public void setStoresYouLove(List<TopNewFeaturedStore.StoreYouLove> storesYouLove) {
        this.storesYouLove = storesYouLove;
    }

    public List<TopNewFeaturedStore.FeatureBrands> getFeatureBrands() {
        return featureBrands;
    }

    public void setFeatureBrands(List<TopNewFeaturedStore.FeatureBrands> featureBrands) {
        this.featureBrands = featureBrands;
    }

    public List<JustForYou> getJustForYou() {
        return justForYou;
    }

    public void setJustForYou(List<JustForYou> justForYou) {
        this.justForYou = justForYou;
    }

    public List<FeaturedCategories> getFeature_categories() {
        return feature_categories;
    }

    public void setFeature_categories(List<FeaturedCategories> feature_categories) {
        this.feature_categories = feature_categories;
    }
}