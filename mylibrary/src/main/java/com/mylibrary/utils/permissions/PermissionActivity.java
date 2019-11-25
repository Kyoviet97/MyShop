package com.mylibrary.utils.permissions;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AlertDialog;
import android.text.Html;
import android.text.Spanned;
import android.view.WindowManager;

import com.mylibrary.R;
import com.mylibrary.base.BaseActivity;


@SuppressWarnings("unused")
public class PermissionActivity extends BaseActivity {

    private static final String PERMISSION = "PERMISSION";
    private String manifestPermission = "";

    private void setManifestPermission(String manifestPermission) {
        this.manifestPermission = manifestPermission;
    }

    private String getManifestPermission() {
        return manifestPermission;
    }

    static void contactsRead(Context context) {
        checkGroup(context, new String[]{Manifest.permission.READ_CONTACTS});
    }

    static void contactsWrite(Context context) {
        checkGroup(context, new String[]{Manifest.permission.WRITE_CONTACTS});
    }

    static void contactsRW(Context context) {
        checkGroup(context, new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS});
    }

    static void calendarRead(Context context) {
        checkGroup(context, new String[]{Manifest.permission.READ_CALENDAR});
    }

    static void calendarWrite(Context context) {
        checkGroup(context, new String[]{Manifest.permission.WRITE_CALENDAR});
    }

    static void calendarRW(Context context) {
        checkGroup(context, new String[]{Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR});
    }

    static void storageRead(Context context) {
        checkGroup(context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE});
    }

    static void storageWrite(Context context) {
        checkGroup(context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE});
    }

    static void storageRW(Context context) {
        checkGroup(context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE});
    }

    static void locationFine(Context context) {
        checkGroup(context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION});
    }

    static void locationCoarse(Context context) {
        checkGroup(context, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION});
    }

    static void locationBoth(Context context) {
        checkGroup(context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION});
    }

    static void camera(Context context) {
        checkGroup(context, new String[]{Manifest.permission.CAMERA});
    }

    static void microphone(Context context) {
        checkGroup(context, new String[]{Manifest.permission.RECORD_AUDIO});
    }

    static void phoneReadState(Context context) {
        checkGroup(context, new String[]{Manifest.permission.READ_PHONE_STATE});
    }

    static void phoneCall(Context context) {
        checkGroup(context, new String[]{Manifest.permission.CALL_PHONE});
    }

    static void phoneReadCallLog(Context context) {
        checkGroup(context, new String[]{Manifest.permission.READ_CALL_LOG});
    }

    static void phoneWriteCallLog(Context context) {
        checkGroup(context, new String[]{Manifest.permission.WRITE_CALL_LOG});
    }

    static void phoneAddVoiceMail(Context context) {
        checkGroup(context, new String[]{Manifest.permission.ADD_VOICEMAIL});
    }

    static void phoneSip(Context context) {
        checkGroup(context, new String[]{Manifest.permission.USE_SIP});
    }

    static void phoneOutgoing(Context context) {
        checkGroup(context, new String[]{Manifest.permission.PROCESS_OUTGOING_CALLS});
    }

    static void phoneAll(Context context) {
        checkGroup(context, new String[]{Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.READ_CALL_LOG,
                Manifest.permission.WRITE_CALL_LOG,
                Manifest.permission.ADD_VOICEMAIL,
                Manifest.permission.USE_SIP,
                Manifest.permission.PROCESS_OUTGOING_CALLS});
    }

    static void sensors(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            checkGroup(context, new String[]{Manifest.permission.BODY_SENSORS});
        }
    }

    static void smsSend(Context context) {
        checkGroup(context, new String[]{Manifest.permission.SEND_SMS});
    }

    static void smsReceive(Context context) {
        checkGroup(context, new String[]{Manifest.permission.RECEIVE_SMS});
    }

    static void smsRead(Context context) {
        checkGroup(context, new String[]{Manifest.permission.READ_SMS});
    }

    static void smsWap(Context context) {
        checkGroup(context, new String[]{Manifest.permission.RECEIVE_WAP_PUSH});
    }

    static void smsMms(Context context) {
        checkGroup(context, new String[]{Manifest.permission.RECEIVE_MMS});
    }

    static void smsAll(Context context) {
        checkGroup(context, new String[]{Manifest.permission.SEND_SMS,
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.READ_SMS,
                Manifest.permission.RECEIVE_WAP_PUSH,
                Manifest.permission.RECEIVE_WAP_PUSH});
    }

    static void mutiPermission(Context context, String[] permiss) {
        checkGroup(context, permiss);
    }

    static void checkGroup(Context context, String[] permissions) {
        if (permissions != null && permissions.length != 0) {
            Intent intent = new Intent(context, PermissionActivity.class);
            intent.putExtra(PERMISSION, permissions);
            context.startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        String[] requestedPermissions = getRequestedPermissions();
        if (checkIsEmptyPermissions(requestedPermissions)) {
            int result = checkPermissions(requestedPermissions);
            requestPermissionsIfNeeded(result, requestedPermissions);
        } else {
            onPermissionDenied();
        }
    }

    private String[] getRequestedPermissions() {
        return getIntent().getExtras() != null ? getIntent().getExtras().getStringArray(PERMISSION) : null;
    }

    private boolean checkIsEmptyPermissions(String[] requestedPermissions) {
        return requestedPermissions != null && requestedPermissions.length != 0;
    }

    private void requestPermissionsIfNeeded(int result, String[] requestedPermissions) {
        if (result == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this,
                    requestedPermissions,
                    1001);
        } else {
            onPermissionGranted();
        }
    }

    private int checkPermissions(String[] requestedPermissions) {
        int result = 0;
        for (String permission : requestedPermissions) {
            if (ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {
                result = PackageManager.PERMISSION_DENIED;
                break;
            }
        }
        return result;
    }

    private void onPermissionDenied() {
        if (PermissionUtil.getCallback() != null) {
            PermissionUtil.getCallback().onPermissionDenied();
        }
        finish();
    }

    private void onPermissionPermanentlyDenied() {
        if (PermissionUtil.getCallback() != null) {
            PermissionUtil.getCallback().onPermissionPermanentlyDenied();
        }
        finish();
    }

    private void onPermissionGranted() {
        if (PermissionUtil.getCallback() == null) {
            finish();
            return;
        }
        try {
            PermissionUtil.getCallback().onPermissionGranted();
        } catch (Exception e) {
            try {
                PermissionUtil.getCallback().onPermissionGranted();
            } catch (Exception ex) {
                finish();
            }
        }
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean b = handleGrantResult(checkGrantResults(grantResults));
        if (b) {
            onPermissionGranted();
        } else {
            showPopupNotifyPermission(getString(R.string.err_permission), PermissionUtil.isCancelPermission);
        }
    }

    /**
     * check permission
     */
    private boolean handleGrantResult(int grantResult) {
        if (grantResult == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private int checkGrantResults(int[] grantResults) {
        int result = 0;
        for (int grant : grantResults) {
            if (grant == PackageManager.PERMISSION_DENIED) {
                result = grant;
                break;
            }
        }
        return result;
    }

    /**
     * check permission trường hợp người dùng ấn vào checkbox
     * không hiện lại popup xin quyền
     */
    private void checkPermanentlyDenied() {
        String[] requestedPermissions = getRequestedPermissions();
        for (String strPer : requestedPermissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, strPer)) {
                onPermissionDenied();
            } else {
                onPermissionPermanentlyDenied();
            }
        }
    }

    private boolean isDimiss = false;

    private void showPopupNotifyPermission(final String msg, final boolean isCancel) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PermissionActivity.this);
                alertDialogBuilder.setTitle("Thông báo");
                alertDialogBuilder
                        .setMessage(setTextHtml(msg))
                        .setCancelable(false)
                        .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                isDimiss = false;
                                checkPermanentlyDenied();
                                dialog.dismiss();
                            }
                        });
                if (!isCancel) {
                    alertDialogBuilder.setNegativeButton("Hủy bỏ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            isDimiss = true;
                            dialogInterface.dismiss();
                        }
                    });
                }
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.setCancelable(false);
                alertDialog.show();
                alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        if (isDimiss) {
                            PermissionActivity.this.finish();
                        }
                    }
                });
            }
        });
    }

    private Spanned setTextHtml(String strHtml) {
        Spanned strFormat;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            strFormat = Html.fromHtml(strHtml, Html.FROM_HTML_MODE_COMPACT);
        } else {
            strFormat = Html.fromHtml(strHtml);
        }
        return strFormat;
    }
}
