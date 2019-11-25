package com.mylibrary.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mylibrary.MyApplication;
import com.mylibrary.R;
import com.mylibrary.base.BaseModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Pattern;

/**
 * Created by Annv on 3/9/18.
 */

public class Utility {

    ///util file

    /**
     * create file image
     *
     * @param bitmap     la nguon tao file image
     * @param i          la thu tu file khi co nhieu file va su dung for
     * @param rootFolder la duong dan luu file
     **/
    public static File createFileImg(Bitmap bitmap, int i, String rootFolder) {
        File rootFo = new File(rootFolder);
        File file;
        if (rootFo.exists()) {
            file = new File(rootFo.getAbsolutePath() + "/img" + i + ".png");
        } else {
            rootFo.mkdirs();
            file = new File(rootFo.getAbsolutePath() + "/img" + i + ".png");
        }
        try {
            file.createNewFile();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
            byte[] bitmapdata = bos.toByteArray();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    public static void deleteDir(List<File> fileOrDirectory) {
        for (File f : fileOrDirectory) {
            if (f != null) {
                if (f.isDirectory()) {
                    for (File child : f.listFiles()) {
                        deleteDir(child);
                    }
                }
                f.delete();
            }
        }
    }

    public static void deleteDir(File... fileOrDirectory) {
        for (File f : fileOrDirectory) {
            if (f != null) {
                if (f.isDirectory()) {
                    for (File child : f.listFiles()) {
                        deleteDir(child);
                    }
                }
                f.delete();
            }
        }
    }

    /////util json
    public static BaseModel formatJsonData(String strJson) {
        String dataString = formatJsonDataString(strJson);
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .setLenient()
                .create();
        try {
            return gson.fromJson(dataString, BaseModel.class);
        } catch (Exception e) {
            dataString = "{\"status\": 500,\"content\": \"Không có dữ liệu\",\"data\":{}}";
            return gson.fromJson(dataString, BaseModel.class);
        }
    }

    public static String formatJsonDataString(String strJson) {
        if (strJson == null) {
            strJson = "{\"status\": 500,\"content\": \"Không có dữ liệu\",\"data\":{}}";
        } else if (TextUtils.isEmpty(strJson)) {
            strJson = "{\"status\": 500,\"content\": \"Không có dữ liệu\",\"data\":{}}";
        } else if (strJson.startsWith("{") && !strJson.startsWith("{\"status\"")) {
            strJson = strJson.trim();
            strJson = strJson.substring(1, strJson.length());
            strJson = "{\"status\": 200,\"content\": \"Thành công\"," + strJson + "}";
        } else if (strJson.startsWith("[")) {
            strJson = strJson.trim();
            strJson = "{\"status\": 200,\"content\": \"Thành công\",\"data\":" + strJson + "}";
        } else if (strJson.startsWith("{\"status\"")) {
            strJson = strJson.trim();
        } else {
            strJson = "{\"status\": 501,\"content\": \"Lỗi định dạng dữ liệu\",\"data\":{}}";
        }
        return strJson;
    }

    ///util encode
    public static String encodeString(String strEndcode) {
        try {
            return URLEncoder.encode(strEndcode, "UTF-8");
        } catch (Exception e) {
            return strEndcode;
        }
    }

    ///util encode
    public static String encodeString(String... strEndcodes) {
        String endCodeResult = "";
        for (String endCode : strEndcodes) {
            try {
                endCodeResult = URLEncoder.encode(endCode, "UTF-8");
            } catch (Exception e) {
                endCodeResult = endCode;
            }
        }
        return endCodeResult;
    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();
        } catch (Exception e) {
            return s;
        }
    }


    ///util time date

    public static long getTimeMillis() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+7:00"));
        return cal.getTimeInMillis();
    }

    public static String formatDate(String date) {
        String formattedDate = null;
        try {
            SimpleDateFormat originalFormat = new SimpleDateFormat(
                    "dd/MM/yyyy", Locale.US);
            SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date12 = originalFormat.parse(date);
            formattedDate = targetFormat.format(date12);
            return formattedDate;
        } catch (Exception e) {
            return date;
        }
    }

    public static String convertTimeStampToDate(long time) {
        try {
            Calendar cal = Calendar.getInstance();
            TimeZone tz = cal.getTimeZone();//get your local time zone.
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            sdf.setTimeZone(tz);//set time zone.
            String localTime = sdf.format(new Date(time));
            return localTime;
        } catch (Exception e) {
            return time + "";
        }
    }

    public static String convertTimeStampToDate2(long time) {
        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();//get your local time zone.
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss dd/MM/yyyy");
        sdf.setTimeZone(tz);//set time zone.
        String localTime = sdf.format(new Date(time));
        return localTime;
    }

    public static int getAge(int year, int month, int day) {
        final Calendar birthDay = Calendar.getInstance();
        birthDay.set(year, month, day);
        final Calendar current = Calendar.getInstance();
        if (current.getTimeInMillis() < birthDay.getTimeInMillis())
            return 0;
        int age = current.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
        if (birthDay.get(Calendar.MONTH) > current.get(Calendar.MONTH) ||
                (birthDay.get(Calendar.MONTH) == current.get(Calendar.MONTH) &&
                        birthDay.get(Calendar.DATE) > current.get(Calendar.DATE)))
            age--;
        return age;
    }

    public static int getAge(String dateOfBirth) {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = formatter.parse(dateOfBirth);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date == null)
            return 0;
        final Calendar birthDay = Calendar.getInstance();
        birthDay.setTime(date);
        final Calendar current = Calendar.getInstance();
        if (current.getTimeInMillis() < birthDay.getTimeInMillis())
            return 0;
        int age = current.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
        if (birthDay.get(Calendar.MONTH) > current.get(Calendar.MONTH) ||
                (birthDay.get(Calendar.MONTH) == current.get(Calendar.MONTH) &&
                        birthDay.get(Calendar.DATE) > current.get(Calendar.DATE)))
            age--;
        return age;
    }

    private static final long MIN_CLICK_INTERVAL = 700;
    private static long mLastClickTime = 0;
    private static boolean isViewClicked = false;

    public static void eventClick(CallBackEventClick eventClick) {
        long currentClickTime = SystemClock.uptimeMillis();
        long elapsedTime = currentClickTime - mLastClickTime;
        mLastClickTime = currentClickTime;
        if (elapsedTime <= MIN_CLICK_INTERVAL)
            return;
        if (!isViewClicked) {
            isViewClicked = true;
            startTimer();
        } else {
            return;
        }
        try {
            if (eventClick != null) {
                eventClick.onSingleTapUp();
            }
        } catch (Exception ex) {
            try {
                Loging.d(Utility.class, ex.getMessage());
                if (eventClick != null) {
                    eventClick.onSingleTapUp();
                }
            } catch (Exception e) {
                Loging.d(Utility.class, "click error");
            }
        }
    }

    private static void startTimer() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                isViewClicked = false;
            }
        }, 600);
    }

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    private static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    public static int getColorResource(Context context, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ContextCompat.getColor(context, color);
        } else {
            return context.getResources().getColor(color);
        }
    }

    public static Spanned setTextHtml(String strHtml) {
        if (strHtml == null) {
            return Html.fromHtml("");
        }
        Spanned strFormat;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            strFormat = Html.fromHtml(strHtml, Html.FROM_HTML_MODE_COMPACT);
        } else {
            strFormat = Html.fromHtml(strHtml);
        }
        return strFormat;
    }

    public static String loadJSONFromAsset(Context context, String fileName) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private void validateNumber(final EditText edt, final String strPattern) {
        edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                edt.removeTextChangedListener(this);
                try {
                    String originalString = s.toString();
                    Long longval;
                    if (originalString.contains(",")) {
                        originalString = originalString.replaceAll(",", "");
                    }
                    longval = Long.parseLong(originalString);
                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    formatter.applyPattern(strPattern);
                    String formattedString = formatter.format(longval);
                    //setting text after format to EditText
                    edt.setText(formattedString);
                    edt.setSelection(edt.getText().length());
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }
                edt.addTextChangedListener(this);
            }
        });
    }

    public static String formatNumber(double valueFormat) {
        try {
            NumberFormat format = new DecimalFormat("#,###");
            return format.format(valueFormat).replace(",", ".");
        } catch (Exception e) {
            return valueFormat + "";
        }
    }

    private static DecimalFormat df, dfnd;
    private static boolean hasFractionalPart = false;
    private static EditText edt;

    public static void numberFormatNumberEdt(EditText et) {
        df = new DecimalFormat("#,###.##");
        df.setDecimalSeparatorAlwaysShown(true);
        dfnd = new DecimalFormat("#,###");
        hasFractionalPart = false;
        edt = et;
        edt.addTextChangedListener(textWatcher);
    }

    public static TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.toString().contains(String.valueOf(df.getDecimalFormatSymbols().getDecimalSeparator()))) {
                hasFractionalPart = true;
            } else {
                hasFractionalPart = false;
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            edt.removeTextChangedListener(this);
            try {
                int inilen, endlen;
                inilen = edt.getText().length();
                String v = s.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                Number n = df.parse(v);
                int cp = edt.getSelectionStart();
                if (hasFractionalPart) {
                    edt.setText(df.format(n));
                } else {
                    edt.setText(dfnd.format(n));
                }
                endlen = edt.getText().length();
                int sel = (cp + (endlen - inilen));
                if (sel > 0 && sel <= edt.getText().length()) {
                    edt.setSelection(sel);
                } else {
                    edt.setSelection(edt.getText().length() - 1);
                }
            } catch (NumberFormatException nfe) {
                // do nothing?
            } catch (ParseException e) {
                // do nothing?
            }
            edt.addTextChangedListener(this);
        }
    };

    public static void startPowerSaverIntent(final Context context) {
        boolean skipMessage = MyApplication.configManager.getBoolean("skipProtectedAppCheck", false);
        if (!skipMessage) {
            boolean foundCorrectIntent = false;
            for (final Intent intent : POWERMANAGER_INTENTS) {
                if (isCallable(context, intent)) {
                    foundCorrectIntent = true;
                    final AppCompatCheckBox dontShowAgain = new AppCompatCheckBox(context);
                    dontShowAgain.setText("Do not show again");
                    dontShowAgain.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            MyApplication.configManager.putBoolean("skipProtectedAppCheck", isChecked).commit();
                        }
                    });

                    new AlertDialog.Builder(context)
                            .setTitle(Build.MANUFACTURER + " Protected Apps")
                            .setMessage(String.format("%s requires to be enabled in 'Protected Apps' to function properly.%n", context.getString(R.string.app_name)))
                            .setView(dontShowAgain)
                            .setPositiveButton("Go to settings", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    context.startActivity(intent);
                                }
                            })
                            .setNegativeButton(android.R.string.cancel, null)
                            .show();
                    break;
                }
            }
            if (!foundCorrectIntent) {
                MyApplication.configManager.putBoolean("skipProtectedAppCheck", true).commit();
            }
        }
    }

    private static boolean isCallable(Context context, Intent intent) {
        List<ResolveInfo> list = context.getPackageManager().queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    public static List<Intent> POWERMANAGER_INTENTS = Arrays.asList(
            new Intent().setComponent(new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity")),
            new Intent().setComponent(new ComponentName("com.letv.android.letvsafe", "com.letv.android.letvsafe.AutobootManageActivity")),
            new Intent().setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.optimize.process.ProtectActivity")),
            new Intent().setComponent(new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.permission.startup.StartupAppListActivity")),
            new Intent().setComponent(new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.startupapp.StartupAppListActivity")),
            new Intent().setComponent(new ComponentName("com.oppo.safe", "com.oppo.safe.permission.startup.StartupAppListActivity")),
            new Intent().setComponent(new ComponentName("com.iqoo.secure", "com.iqoo.secure.ui.phoneoptimize.AddWhiteListActivity")),
            new Intent().setComponent(new ComponentName("com.iqoo.secure", "com.iqoo.secure.ui.phoneoptimize.BgStartUpManager")),
            new Intent().setComponent(new ComponentName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.BgStartUpManagerActivity")),
            new Intent().setComponent(new ComponentName("com.asus.mobilemanager", "com.asus.mobilemanager.entry.FunctionActivity")).setData(android.net.Uri.parse("mobilemanager://function/entry/AutoStart"))
    );

    public static boolean isValidEmailId(String email) {
        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }
}
