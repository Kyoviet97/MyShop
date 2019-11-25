package com.mylibrary.base;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;

import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mylibrary.MyApplication;
import com.mylibrary.R;
import com.mylibrary.dialog.DialogNotifyCustom;
import com.mylibrary.networks.checknetwork.Monitor;
import com.mylibrary.networks.checknetwork.NoNet;
import com.mylibrary.ui.progress.ProgressDialogCustom;
import com.mylibrary.ui.statusbar.StatusBarCompat;
import com.mylibrary.ui.statusbar.SystemBarTintManager;
import com.mylibrary.utils.Loging;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.security.MessageDigest;

/**
 * Created by Annv on 8/8/16.
 */
public class BaseActivity extends AppCompatActivity implements Monitor.Callback {

    private ProgressDialogCustom progressDialog;
    private DialogNotifyCustom alertDialog;
    private boolean isDismiss = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialogCustom(this);
        alertDialog = new DialogNotifyCustom(this);
    }

    public String getKeyHash(String packageName) {
        //get keyhash
        String keyHash = "";
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    packageName,
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                keyHash = Base64.encodeToString(md.digest(), Base64.DEFAULT);
                Log.d("KeyHash:", keyHash);
            }
        } catch (Exception e) {
            keyHash = "";
        }
        return keyHash;
    }

    public DialogNotifyCustom showPopupNotify(String msg) {
        if (alertDialog == null)
            alertDialog = new DialogNotifyCustom(this);
        if (msg == null) {
            msg = "";
        }
        alertDialog.setTextContent(msg);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                alertDialog.show();
            }
        });
        return alertDialog;
    }

    public DialogNotifyCustom showPopupNotify(String strTitle, String msg) {
        if (alertDialog == null)
            alertDialog = new DialogNotifyCustom(this);
        if (msg == null) {
            msg = "";
        }
        alertDialog.setTextContent(msg);
        if (!TextUtils.isEmpty(strTitle))
            alertDialog.setTextTitle(strTitle);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                alertDialog.show();
            }
        });
        return alertDialog;
    }

    public DialogNotifyCustom showPopupNotify(String strTitle, String msg, String strBtnOk, String strBtnCan) {
        if (alertDialog == null)
            alertDialog = new DialogNotifyCustom(this);
        if (msg == null) {
            msg = "";
        }
        alertDialog.setTextContent(msg);
        if (!TextUtils.isEmpty(strTitle))
            alertDialog.setTextTitle(strTitle);
        alertDialog.setTextButton(strBtnOk, strBtnCan);

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                alertDialog.show();
            }
        });
        return alertDialog;
    }

    public void FullScreencall() {
        int currentApiVersion = Build.VERSION.SDK_INT;
        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        if (currentApiVersion >= Build.VERSION_CODES.KITKAT) {
            getWindow().getDecorView().setSystemUiVisibility(flags);
            final View decorView = getWindow().getDecorView();
            decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
                @Override
                public void onSystemUiVisibilityChange(int visibility) {
                    if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                        decorView.setSystemUiVisibility(flags);
                    }
                }
            });
        }
    }

    public void setupStatusBarView(View view) {
        SystemBarTintManager systemBarTintManager = new SystemBarTintManager(this);
        SystemBarTintManager.SystemBarConfig mConfig = systemBarTintManager.getConfig();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, mConfig.getStatusBarHeight());
        view.setLayoutParams(params);
    }

    public void setupStatusBarView(View view, boolean isShow) {
        //set statusbar tranparent
        if (isShow)
            setStatusTranslucent();
        SystemBarTintManager systemBarTintManager = new SystemBarTintManager(this);
        SystemBarTintManager.SystemBarConfig mConfig = systemBarTintManager.getConfig();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, mConfig.getStatusBarHeight());
        view.setLayoutParams(params);
    }

    public void setStatusTranslucent() {
        StatusBarCompat.translucentStatusBar(this, true);
    }

    /**
     * su dung fun callback cua snackbar
     * snackbar.addCallback(new Snackbar.Callback(){
     *
     * @Override public void onDismissed(Snackbar transientBottomBar, int event) {
     * super.onDismissed(transientBottomBar, event);
     * }
     * @Override public void onShown(Snackbar sb) {
     * super.onShown(sb);
     * }
     * });
     */
    public Snackbar notifySnackbar(View view, String msg) {
        return notifySnackbar(view, msg, "OK");
    }

    public Snackbar notifySnackbar(View view, String msg, String txtBtn) {
        final Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_INDEFINITE);
        if (!txtBtn.equals(""))
            snackbar.setAction(txtBtn, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    snackbar.dismiss();
                }
            });
        snackbar.setActionTextColor(Color.RED);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(R.id.snackbar_text);
        Typeface typeface = ResourcesCompat.getFont(this, R.font.sf_ui_display_regular);
        textView.setTypeface(typeface);
        textView.setTextColor(Color.YELLOW);
        return snackbar;
    }

    public void onHideKeyboard(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null)
            inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void show_keyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public void showWaitingProgress(String text) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialogCustom(this);
        }
        progressDialog.setCancelable(false);
        progressDialog.setMessage(text);
        progressDialog.show();
    }

    public void hideWaitingProgress() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    public String getDeviceId() {
        return Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    public int getWidthScreenSize() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    public void openForceUpdate(Context context) {
        final String appPackageName = context.getPackageName();
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    public void setTouchOutsideHideKeyBoard(View view) {
        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    onHideKeyboard(BaseActivity.this);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setTouchOutsideHideKeyBoard(innerView);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.BACKGROUND)
    public void onEvent(Object obj) {
    }

    public void postEvent(Object obj) {
        EventBus.getDefault().post(obj);
    }

    public void UnregisterEvent() {
        EventBus.getDefault().unregister(this);
    }

    @SuppressLint("CheckResult")
    public void connectInterNet(final Monitor.Callback monitorCallback) {
        NoNet.monitor(this)
                .poll()
                //show banner in actionbar
//                .banner()
                //show snackbar bottom screen
                .snackbar()
                .observe()
                .subscribe(new io.reactivex.functions.Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        monitorCallback.onConnectionEvent(integer);
                    }
                });
        NoNet.check(this)
                .toast()
                .start();
    }

    @Override
    public void onConnectionEvent(int connectionStatus) {
        Loging.e(BaseActivity.class, "Networking: " + connectionStatus);
        MyApplication.connectionStatus = connectionStatus;
    }

    public interface onBackPressedListener {
        boolean onBack();
    }

    private onBackPressedListener mOnBackPressedListener;

    public void setOnBackPressedListener(onBackPressedListener listener) {
        mOnBackPressedListener = listener;
    }

    @Override
    public void onBackPressed() {
        if (mOnBackPressedListener != null && mOnBackPressedListener.onBack()) {
            return;
        }
        super.onBackPressed();
    }

    @TargetApi(19)
    public void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public void setTingStatusbar(int draw) {
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(draw);
    }

}
