package com.gvtechcom.myshop.Fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.gvtechcom.myshop.Model.UpdateNotifyModel;
import com.gvtechcom.myshop.Network.APIServer;
import com.gvtechcom.myshop.Network.RetrofitBuilder;
import com.gvtechcom.myshop.R;
import com.gvtechcom.myshop.Utils.Const;
import com.mylibrary.ui.progress.ProgressDialogCustom;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FragmentNotifyDetail extends Fragment {
    private View rootView;
    private APIServer apiServer;
    private String idNotify;
    private ProgressDialogCustom progressDialogCustom;
    @BindView(R.id.title_update_notify_details)
    TextView titleUpdateNotifyDetails;
    @BindView(R.id.start_datetime_update_notify_details)
    TextView startDatetimeUpdateNotifyDetails;
    @BindView(R.id.webView_update_notitify)
    WebView webViewUpdateNotitify;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        this.idNotify = bundle.getString("idNotify");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.item_notify_details, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressDialogCustom = new ProgressDialogCustom(getActivity());
        setRetrofit();
        callApiNotifyDetail(idNotify);
    }

    private void setRetrofit() {
        Retrofit retrofit;
        retrofit = RetrofitBuilder.getRetrofit(Const.BASE_URL);
        apiServer = retrofit.create(APIServer.class);
    }

    private void setWebView(String text) {
        WebSettings webSettings = webViewUpdateNotitify.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDefaultFontSize(50);


        final Activity activity = getActivity();

        webViewUpdateNotitify.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
            }


        });
        webViewUpdateNotitify.loadData(text, "text/html", "utf-8");
    }

    private void callApiNotifyDetail(String idNotify) {
        progressDialogCustom.onShow(false, "");
        Call<UpdateNotifyModel.UpdateNotifyModelParser> call = apiServer.GetDataUpdateNotifyDetail(idNotify);
        call.enqueue(new Callback<UpdateNotifyModel.UpdateNotifyModelParser>() {
            @Override
            public void onResponse(Call<UpdateNotifyModel.UpdateNotifyModelParser> call, Response<UpdateNotifyModel.UpdateNotifyModelParser> response) {
                if (response.body().code != 200) {
                    progressDialogCustom.onHide();
                    Toast.makeText(getActivity(), response.body().message, Toast.LENGTH_SHORT).show();
                } else {
                    titleUpdateNotifyDetails.setText(response.body().response.title);
                    startDatetimeUpdateNotifyDetails.setText(response.body().response.start_datetime);
                    setWebView(response.body().response.content);
                    progressDialogCustom.onHide();
                }
            }
            @Override
            public void onFailure(Call<UpdateNotifyModel.UpdateNotifyModelParser> call, Throwable t) {
                progressDialogCustom.onHide();
            }
        });
    }
}
