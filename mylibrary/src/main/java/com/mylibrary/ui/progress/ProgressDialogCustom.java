package com.mylibrary.ui.progress;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialog;

import com.mylibrary.R;

public class ProgressDialogCustom extends AppCompatDialog implements IDelegateProgress {

    TextView mProgressMessage;
    private DelayHandler mDelayHandler;
    final int REQUEST_SHOW = 1;
    final int REQUEST_HIDE = 2;
    private boolean mHasStarted;

    public ProgressDialogCustom(Context context) {
        super(context, R.style.Theme_Progress);
        mDelayHandler = new DelayHandler(this);
        setContentView(R.layout.progress_dialog);
        setCancelable(false);
        mProgressMessage = findViewById(R.id.progress_bar_message);
    }

    public void setMessage(CharSequence title) {
        if (TextUtils.isEmpty(title))
            mProgressMessage.setVisibility(View.GONE);
        mProgressMessage.setText(title);
    }

    @Override
    public void show() {
        mDelayHandler.cancel = false;
        mDelayHandler.sendEmptyMessageDelayed(REQUEST_SHOW, 0);
    }

    private void supperShow() {
        super.show();
    }

    @Override
    public void dismiss() {
        try {
            mDelayHandler.cancel = true;
            mDelayHandler.removeMessages(REQUEST_SHOW);
            super.dismiss();
        } catch (Exception e) {

        }
    }

    @Override
    public void onShow(boolean delay, String message) {
        if (message == null) {
            message = "Loading...";
        }
        setMessage(message);
        if (delay) {
            this.show();
        } else {
            supperShow();
        }
    }

    @Override
    public void onHide() {
        this.dismiss();
    }


    @Override
    protected void onStart() {
        super.onStart();
        mHasStarted = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mHasStarted = false;
    }

    private static class DelayHandler extends Handler {

        ProgressDialogCustom dialog;
        boolean cancel;

        public DelayHandler(ProgressDialogCustom dialog) {
            this.dialog = dialog;
        }

        @Override
        public void handleMessage(Message msg) {
            if (!cancel) {
                dialog.supperShow();
            }
        }
    }
}
