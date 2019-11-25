package com.mylibrary.utils.permissions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

public class PermissionUtil {

    public static final int REQUEST_PERMISSION_SETTING = 102;
    private static PermissionUtil instance;
    private PermissionCallback callback;
    private static boolean isShowSetting = true;
    /**
     * nếu @isCancelPermission = false thì show button hủy bỏ
     * không bắt buộc người dùng phải đồng ý cho xin quyền
     * nếu isCancelPermission = true sẽ ẩn button hủy bỏ,
     * và bắt buộc người dùng phải set quyền thì mới được
     * truy cập ứng dụng
     **/
    public static boolean isCancelPermission = false;

    private static PermissionUtil getInstance() {
        if (instance == null)
            instance = new PermissionUtil();
        return instance;
    }

    /**
     * example
     * {@link PermissionUtil}.contactsRead({@link Activity, {@link PermissionCallback}})
     */
    public static void contactsRead(Context context, PermissionCallback callback) {
        settingPermission(1, context, callback, null);
    }

    /**
     * example
     * {@link PermissionUtil}.contactsWrite({@link Activity, {@link PermissionCallback}})
     */
    public static void contactsWrite(Context context, PermissionCallback callback) {
        settingPermission(2, context, callback, null);
    }

    /**
     * example
     * {@link PermissionUtil}.contactsRW({@link Activity, {@link PermissionCallback}})
     */
    public static void contactsRW(Context context, PermissionCallback callback) {
        settingPermission(3, context, callback, null);

    }

    /**
     * example
     * {@link PermissionUtil}.calendarRead({@link Activity, {@link PermissionCallback}})
     */
    public static void calendarRead(Context context, PermissionCallback callback) {
        settingPermission(4, context, callback, null);
    }

    /**
     * example
     * {@link PermissionUtil}.calendarWrite({@link Activity, {@link PermissionCallback}})
     */
    public static void calendarWrite(Context context, PermissionCallback callback) {
        settingPermission(5, context, callback, null);
    }

    /**
     * example
     * {@link PermissionUtil}.calendarRW({@link Activity, {@link PermissionCallback}})
     */
    public static void calendarRW(Context context, PermissionCallback callback) {
        settingPermission(6, context, callback, null);
    }

    /**
     * example
     * {@link PermissionUtil}.storageRead({@link Activity, {@link PermissionCallback}})
     */
    public static void storageRead(Context context, PermissionCallback callback) {
        settingPermission(7, context, callback, null);
    }

    /**
     * example
     * {@link PermissionUtil}.storageWrite({@link Activity, {@link PermissionCallback}})
     */
    public static void storageWrite(Context context, PermissionCallback callback) {
        settingPermission(8, context, callback, null);
    }

    /**
     * example
     * {@link PermissionUtil}.storageRW({@link Activity, {@link PermissionCallback}})
     */
    public static void storageRW(Context context, PermissionCallback callback) {
        settingPermission(9, context, callback, null);
    }

    /**
     * example
     * {@link PermissionUtil}.locationFine({@link Activity, {@link PermissionCallback}})
     */
    public static void locationFine(Context context, PermissionCallback callback) {
        settingPermission(10, context, callback, null);
    }

    /**
     * example
     * {@link PermissionUtil}.locationCoarse({@link Activity, {@link PermissionCallback}})
     */
    public static void locationCoarse(Context context, PermissionCallback callback) {
        settingPermission(11, context, callback, null);
    }

    /**
     * example
     * {@link PermissionUtil}.locationBoth({@link Activity, {@link PermissionCallback}})
     */
    public static void locationBoth(Context context, PermissionCallback callback) {
        settingPermission(12, context, callback, null);
    }

    /**
     * example
     * {@link PermissionUtil}.camera({@link Activity, {@link PermissionCallback}})
     */
    public static void camera(Context context, PermissionCallback callback) {
        settingPermission(13, context, callback, null);
    }

    /**
     * example
     * {@link PermissionUtil}.microphone({@link Activity, {@link PermissionCallback}})
     */
    public static void microphone(Context context, PermissionCallback callback) {
        settingPermission(14, context, callback, null);
    }

    /**
     * example
     * {@link PermissionUtil}.phoneReadState({@link Activity, {@link PermissionCallback}})
     */
    public static void phoneReadState(Context context, PermissionCallback callback) {
        settingPermission(15, context, callback, null);
    }

    /**
     * example
     * {@link PermissionUtil}.phoneCall({@link Activity, {@link PermissionCallback}})
     */
    public static void phoneCall(Context context, PermissionCallback callback) {
        settingPermission(16, context, callback, null);
    }

    /**
     * example
     * {@link PermissionUtil}.phoneReadCallLog({@link Activity, {@link PermissionCallback}})
     */
    public static void phoneReadCallLog(Context context, PermissionCallback callback) {
        settingPermission(17, context, callback, null);
    }

    /**
     * example
     * {@link PermissionUtil}.phoneWriteCallLog({@link Activity, {@link PermissionCallback}})
     */
    public static void phoneWriteCallLog(Context context, PermissionCallback callback) {
        settingPermission(18, context, callback, null);
    }

    /**
     * example
     * {@link PermissionUtil}.phoneAddVoiceMail({@link Activity, {@link PermissionCallback}})
     */
    public static void phoneAddVoiceMail(Context context, PermissionCallback callback) {
        settingPermission(19, context, callback, null);
    }

    /**
     * example
     * {@link PermissionUtil}.phoneSip({@link Activity, {@link PermissionCallback}})
     */
    public static void phoneSip(Context context, PermissionCallback callback) {
        settingPermission(20, context, callback, null);
    }

    /**
     * example
     * {@link PermissionUtil}.phoneOutgoing({@link Activity, {@link PermissionCallback}})
     */
    public static void phoneOutgoing(Context context, PermissionCallback callback) {
        settingPermission(21, context, callback, null);
    }

    /**
     * example
     * {@link PermissionUtil}.phoneAll({@link Activity, {@link PermissionCallback}})
     */
    public static void phoneAll(Context context, PermissionCallback callback) {
        settingPermission(22, context, callback, null);
    }

    /**
     * example
     * {@link PermissionUtil}.sensors({@link Activity, {@link PermissionCallback}})
     */
    public static void sensors(Context context, PermissionCallback callback) {
        settingPermission(23, context, callback, null);
    }

    /**
     * example
     * {@link PermissionUtil}.smsSend({@link Activity, {@link PermissionCallback}})
     */
    public static void smsSend(Context context, PermissionCallback callback) {
        settingPermission(24, context, callback, null);
    }

    /**
     * example
     * {@link PermissionUtil}.smsReceive({@link Activity, {@link PermissionCallback}})
     */
    public static void smsReceive(Context context, PermissionCallback callback) {
        settingPermission(25, context, callback, null);
    }

    /**
     * example
     * {@link PermissionUtil}.smsRead({@link Activity, {@link PermissionCallback}})
     */
    public static void smsRead(Context context, PermissionCallback callback) {
        settingPermission(26, context, callback, null);
    }

    /**
     * example
     * {@link PermissionUtil}.smsWap({@link Activity, {@link PermissionCallback}})
     */
    public static void smsWap(Context context, PermissionCallback callback) {
        settingPermission(27, context, callback, null);
    }

    /**
     * example
     * {@link PermissionUtil}.smsMms({@link Activity, {@link PermissionCallback}})
     */
    public static void smsMms(Context context, PermissionCallback callback) {
        settingPermission(28, context, callback, null);
    }

    /**
     * example
     * {@link PermissionUtil}.smsAll({@link Activity, {@link PermissionCallback}})
     */
    public static void smsAll(Context context, PermissionCallback callback) {
        settingPermission(29, context, callback, null);
    }

    /**
     * example
     * {@link PermissionUtil}.checkGroup({@link Activity, {@link PermissionCallback},new String[]})
     * new String[] ở đây là danh sách các loại permission mình cần
     * new String[]{Manifest.permission.READ_CONTACTS,Manifest.permission.WRITE_CONTACTS}
     */
    public static void checkGroup(Context context, PermissionCallback callback, String[] group) {
        settingPermission(30, context, callback, group);
    }

    private static void settingPermission(int permission, Context context, PermissionCallback callback, String[] group) {
        isShowSetting = true;
        getInstance().setCallback(callback);
        switch (permission) {
            case 1:
                PermissionActivity.contactsRead(context);
                break;
            case 2:
                PermissionActivity.contactsWrite(context);
                break;
            case 3:
                PermissionActivity.contactsRW(context);
                break;
            case 4:
                PermissionActivity.calendarRead(context);
                break;
            case 5:
                PermissionActivity.calendarWrite(context);
                break;
            case 6:
                PermissionActivity.calendarRW(context);
                break;
            case 7:
                PermissionActivity.storageRead(context);
                break;
            case 8:
                PermissionActivity.storageWrite(context);
                break;
            case 9:
                PermissionActivity.storageRW(context);
                break;
            case 10:
                PermissionActivity.locationFine(context);
                break;
            case 11:
                PermissionActivity.locationCoarse(context);
                break;
            case 12:
                PermissionActivity.locationBoth(context);
                break;
            case 13:
                PermissionActivity.camera(context);
                break;
            case 14:
                PermissionActivity.microphone(context);
                break;
            case 15:
                PermissionActivity.phoneReadState(context);
                break;
            case 16:
                PermissionActivity.phoneCall(context);
                break;
            case 17:
                PermissionActivity.phoneReadCallLog(context);
                break;
            case 18:
                PermissionActivity.phoneWriteCallLog(context);
                break;
            case 19:
                PermissionActivity.phoneAddVoiceMail(context);
                break;
            case 20:
                PermissionActivity.phoneSip(context);
                break;
            case 21:
                PermissionActivity.phoneOutgoing(context);
                break;
            case 22:
                PermissionActivity.phoneAll(context);
                break;
            case 23:
                PermissionActivity.sensors(context);
                break;
            case 24:
                PermissionActivity.smsSend(context);
                break;
            case 25:
                PermissionActivity.smsReceive(context);
                break;
            case 26:
                PermissionActivity.smsRead(context);
                break;
            case 27:
                PermissionActivity.smsWap(context);
                break;
            case 28:
                PermissionActivity.smsMms(context);
                break;
            case 29:
                PermissionActivity.smsAll(context);
                break;
            case 30:
                PermissionActivity.checkGroup(context, group);
                break;
            default:
                break;
        }
    }

    static PermissionCallback getCallback() {
        return getInstance().callback;
    }

    private void setCallback(PermissionCallback callback) {
        this.callback = callback;
    }

    /**
     * trường hợp người dùng bấm vào không hiện lại popup xin quyền
     * thì lần sau bấm lại sẽ mở setting của ứng dụng để set permission cho ứng dụng
     */
    public static void openActivitySettingPermission(Activity activity, String packageName) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", packageName, null);
        intent.setData(uri);
        if (isShowSetting) {
            activity.startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
            isShowSetting = false;
        }
    }
}
