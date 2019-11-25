package com.mylibrary.networks.checknetwork;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.mylibrary.networks.checknetwork.ConnectionStatus.*;


class PollTask extends AsyncTask<Void, Void, Integer> {
    private static final String TAG = PollTask.class.getSimpleName();

    private static OkHttpClient client;

    private Configuration configuration;
    private OnPollCompletedListener listener;

    static PollTask run(Configuration configuration, OnPollCompletedListener listener) {
        PollTask pollTask = new PollTask(configuration, listener);
        return (PollTask) pollTask.execute();
    }

    private PollTask(Configuration configuration, OnPollCompletedListener listener) {
        this.configuration = configuration;
        this.listener = listener;

        if (client == null) {
            client = new OkHttpClient.Builder()
                    .connectTimeout(configuration.getTimeout(), TimeUnit.SECONDS)
                    .build();
        }
    }

    @ConnectionStatus
    @Override
    protected Integer doInBackground(Void... params) {
        Log.d(TAG, "Executing request to " + configuration.getEndpoint());

        Request request = new Request.Builder()
                .url(configuration.getEndpoint())
                .build();

        Response response;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            return DISCONNECTED;
        }

        if (response != null) {
            if (response.isSuccessful()) {
                return CONNECTED;
            }
        }

        return DISCONNECTED;
    }

    @Override
    protected void onPostExecute(@ConnectionStatus Integer integer) {
        Log.d(TAG, "Poll result: " + integer);

        if (listener != null) {
            listener.onPollCompleted(integer);
        }
    }

    interface OnPollCompletedListener {
        void onPollCompleted(@ConnectionStatus int connectionStatus);
    }
}
