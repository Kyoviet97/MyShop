package com.mylibrary.networks;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.mylibrary.BuildConfig;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.CertificatePinner;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofit = null;
    private static int REQUEST_TIMEOUT = 60;
    private static OkHttpClient okHttpClient;
    public static MediaType MEDIA_TYPE_TEXT = MediaType.parse("text/plain");
    public static MediaType MEDIA_TYPE_IMG_FILE = MediaType.parse("image/*");

    /**
     * @param baseUrl   base url api
     * @param isShowLog = false show log request api
     */
    public static Retrofit getClient(String baseUrl, boolean isShowLog) {
        if (okHttpClient == null)
            initOkHttp(isShowLog);
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getClient(String baseUrl, Map<String, String> mapCertificate, boolean isShowLog) {
        if (okHttpClient == null)
            initOkHttp(mapCertificate, isShowLog);
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getClient(String baseUrl) {
        return getClient(baseUrl, true);
    }

    private static void initOkHttp(boolean isShowLog) {
        OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder()
                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS);
        if (isShowLog) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(interceptor);
        }
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader("Content-Type", "application/json");
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });
        okHttpClient = httpClient.build();
    }

    /**
     * @param mapCertificate add Certificate;
     * @Map<"Host name","sha1 endcode"> mapCertificate
     * <p>
     * // Adding Authorization token (API Key)
     * // Requests will be denied without API key
     * // requestBuilder.addHeader("Authorization", "");
     */
    private static void initOkHttp(Map<String, String> mapCertificate, boolean isShowLog) {
        CertificatePinner.Builder builder = new CertificatePinner.Builder();
        for (Map.Entry<String, String> entry : mapCertificate.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }
        CertificatePinner certificatePinner = builder.build();
        Set<Map.Entry<String, String>> entries = mapCertificate.entrySet();
        OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder()
                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS);
        httpClient.certificatePinner(certificatePinner);
        if (isShowLog) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(interceptor);
        }
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader("Content-Type", "application/json");
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });
        okHttpClient = httpClient.build();
    }

//    @SuppressLint("CheckResult")
//    public  DisposableSingleObserver signleRequest(Single<T> signleProcess, DisposableSingleObserver<BaseModel> disposableSingleObserver) {
//        signleProcess.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(disposableSingleObserver);
//        return disposableSingleObserver;
//    }
//
//    @SuppressLint("CheckResult")
//    public static DisposableCompletableObserver completRequest(Completable completProcess, DisposableCompletableObserver disposableSingleObserver) {
//        completProcess.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(disposableSingleObserver);
//        return disposableSingleObserver;
//    }

    public static MultipartBody.Part prepareFilePart(String partName, File file) {
        RequestBody requestFile = RequestBody.create(MEDIA_TYPE_IMG_FILE, file);
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    public static RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(MultipartBody.FORM, descriptionString);
    }
}
