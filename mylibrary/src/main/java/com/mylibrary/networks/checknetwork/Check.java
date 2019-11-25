package com.mylibrary.networks.checknetwork;

import android.content.Context;
import androidx.annotation.StringRes;
import com.google.android.material.snackbar.Snackbar;
import android.widget.Toast;

/**
 * A subclass of {@link Monitor} that makes a one-time network connectivity check
 * and has polling disabled.
 */
@SuppressWarnings("WeakerAccess")
public class Check extends Monitor {
    private Check(Context context) {
        super(context);
    }

    @Override
    void start() {
        poll();
    }

    @Override
    void stop() {
        // Nothing to do here...
    }

    public static class Builder extends Monitor.Builder {

        Builder(Context context) {
            super(new Check(context));
        }

        @Override
        public Builder configure(Configuration configuration) {
            super.configure(configuration);
            return this;
        }

        /**
         * This method is disabled while using {@link NoNet#check(Context)}.
         *
         * @throws IllegalStateException Cannot poll while using {@link NoNet#check(Context)}
         *
         * @return This {@link Check.Builder}
         */
        @Override
        public Builder poll() {
            throw new IllegalStateException("Cannot poll while using NoNet.check()");
        }

        @Override
        public Builder toast() {
            super.toast();
            return this;
        }

        @Override
        public Builder toast(String message) {
            super.toast(message);
            return this;
        }

        @Override
        public Builder toast(@StringRes int messageRes) {
            super.toast(messageRes);
            return this;
        }

        @Override
        public Builder toast(Toast toast) {
            super.toast(toast);
            return this;
        }

        @Override
        public Builder snackbar() {
            super.snackbar();
            return this;
        }

        @Override
        public Builder snackbar(String message) {
            super.snackbar(message);
            return this;
        }

        @Override
        public Builder snackbar(@StringRes int messageRes) {
            super.snackbar(messageRes);
            return this;
        }

        @Override
        public Builder snackbar(Snackbar snackbar) {
            super.snackbar(snackbar);
            return this;
        }

        @Override
        public Builder banner() {
            super.banner();
            return this;
        }

        @Override
        public Builder banner(String message) {
            super.banner(message);
            return this;
        }

        @Override
        public Builder banner(@StringRes int messageRes) {
            super.banner(messageRes);
            return this;
        }

        @Override
        public Builder banner(BannerView banner) {
            super.banner(banner);
            return this;
        }

        @Override
        public Builder callback(Callback callback) {
            super.callback(callback);
            return this;
        }

        public Check start() {
            monitor.start();
            return (Check) monitor;
        }
    }
}
