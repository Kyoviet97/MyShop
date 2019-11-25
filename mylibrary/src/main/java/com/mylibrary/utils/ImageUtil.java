package com.mylibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import androidx.fragment.app.Fragment;
import androidx.core.content.FileProvider;
import android.util.Base64;


import com.mylibrary.BuildConfig;
import com.mylibrary.utils.tiny.Compressor;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Annv on 7/29/16.
 */

public class ImageUtil {

    private static String pkg_name = BuildConfig.APPLICATION_ID;
    public static final String urlFile = "/sdcard/Android/data/" + pkg_name + "/imgTmp";
    public static final int SELECT_IMAGE = 1;
    public static final int CAMERA_REQUEST = 2;
    public static final int CAMERA_VIDEO_REQUEST = 3;

    public static Bitmap resizeImage(Bitmap bitmap) {
        double width = bitmap.getWidth();
        double height = bitmap.getHeight();
        Bitmap bitmapResize;
        if (width > height) {
            double rotate = width / height;
            bitmapResize = Bitmap.createScaledBitmap(bitmap, 480, (int) (480 / rotate), true);
        } else {
            double rotate = height / width;
            bitmapResize = Bitmap.createScaledBitmap(bitmap, 480, (int) (480 * rotate), true);
        }
        return bitmapResize;
    }

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

    public static void deleteRecursive(List<File> fileOrDirectory) {
        for (File f : fileOrDirectory) {
            if (f != null) {
                if (f.isDirectory()) {
                    for (File child : f.listFiles()) {
                        deleteRecursive(child);
                    }
                }
                f.delete();
            }
        }
    }

    public static void deleteRecursive(File... fileOrDirectory) {
        for (File f : fileOrDirectory) {
            if (f != null) {
                if (f.isDirectory()) {
                    for (File child : f.listFiles()) {
                        deleteRecursive(child);
                    }
                }
                f.delete();
            }
        }
    }

    public static boolean deleteRecursive(File dir) {
        if (dir == null)
            return false;
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteRecursive(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    public static class convertBitmapToFile extends AsyncTask<Bitmap, String, File> {

        private int i;
        public String rootFolder;
        private CallBackFileIMG callBackFileIMG;

        public convertBitmapToFile(int i, String rootFolder, CallBackFileIMG callBackFileIMG) {
            this.i = i;
            this.callBackFileIMG = callBackFileIMG;
            this.rootFolder = rootFolder;
        }

        @Override
        protected File doInBackground(Bitmap... bitmaps) {
            if (rootFolder.equals(""))
                rootFolder = urlFile;
            File fileImg = createFileImg(bitmaps[0], i, rootFolder);
            return fileImg;
        }

        @Override
        protected void onPostExecute(File file) {
            super.onPostExecute(file);
            callBackFileIMG.onSuccess(file);
        }
    }

    public static class convertFileToBitmap extends AsyncTask<File, String, Bitmap> {

        private CallBackBitmapIMG callBackFileIMG;

        public convertFileToBitmap(CallBackBitmapIMG callBackFileIMG) {
            this.callBackFileIMG = callBackFileIMG;
        }

        @Override
        protected Bitmap doInBackground(File... fileImg) {
            if (fileImg[0] == null || !fileImg[0].exists())
                return null;
            Bitmap bitmap = null;
            try {
                bitmap = decodeFile(fileImg[0]);
            } catch (Exception e) {
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            callBackFileIMG.onSuccess(bitmap);
        }
    }

    private static Bitmap decodeFile(File f) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);
            // The new size we want to scale to
            final int REQUIRED_SIZE = 70;
            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }
            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public static void selectImage(Fragment fragment) {
        if (Build.VERSION.SDK_INT <= 19) {
            Intent i = new Intent();
            i.setType("image/*");
            i.setAction(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            fragment.startActivityForResult(i, SELECT_IMAGE);
        } else if (Build.VERSION.SDK_INT > 19) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            fragment.startActivityForResult(intent, SELECT_IMAGE);
        }
    }

    public static void selectImage(Activity activity) {
        if (Build.VERSION.SDK_INT <= 19) {
            Intent i = new Intent();
            i.setType("image/*");
            i.setAction(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            activity.startActivityForResult(i, SELECT_IMAGE);
        } else if (Build.VERSION.SDK_INT > 19) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            activity.startActivityForResult(intent, SELECT_IMAGE);
        }
    }

    public static File getPathImage(Context context, Uri uri) {
        try {
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(uri,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            File file = new File(picturePath);
            return file;
        } catch (Exception e) {
            return null;
        }
    }

    public static Bitmap selectImageResult(Context context, Intent data) {
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        return resizeImage(BitmapFactory.decodeFile(picturePath));
    }

    public static File takePicture(Fragment fragment, String imgName) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File outputMediaFile = getOutputMediaFile(imgName, "jpg");
        Uri fileUri = null;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            fileUri = Uri.fromFile(outputMediaFile);
        } else {
            fileUri = FileProvider.getUriForFile(fragment.getActivity(), fragment.getActivity().getPackageName() + ".provider", outputMediaFile);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        fragment.startActivityForResult(intent, CAMERA_REQUEST);
        return outputMediaFile;
    }

    public static File takePicture(Activity activity, String imgName) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File outputMediaFile = getOutputMediaFile(imgName, "jpg");
        Uri fileUri = null;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            fileUri = Uri.fromFile(outputMediaFile);
        } else {
            fileUri = FileProvider.getUriForFile(activity, activity.getPackageName() + ".provider", outputMediaFile);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        activity.startActivityForResult(intent, CAMERA_REQUEST);
        return outputMediaFile;
    }

    public static File recordVideo(Fragment fragment, String imgName) {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        File outputMediaFile = getOutputMediaFile(imgName, "mp4");
        Uri fileUri = null;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            fileUri = Uri.fromFile(outputMediaFile);
        } else {
            fileUri = FileProvider.getUriForFile(fragment.getActivity(), fragment.getActivity().getPackageName() + ".provider", outputMediaFile);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
//// set video quality (set chất lượng video 0 chất lượng thấp, 1 chất lượng cao)
        if (Utility.getDeviceName().startsWith("Sony")) {
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        } else
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
//        intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 5491520L);
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 25);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set Video file
        fragment.startActivityForResult(intent, CAMERA_VIDEO_REQUEST);
        return outputMediaFile;
    }

    public static File recordVideo(Activity activity, String imgName) {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        File outputMediaFile = getOutputMediaFile(imgName, "mp4");
        Uri fileUri = null;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            fileUri = Uri.fromFile(outputMediaFile);
        } else {
            fileUri = FileProvider.getUriForFile(activity, activity.getPackageName() + ".provider", outputMediaFile);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
//// set video quality (set chất lượng video 0 chất lượng thấp, 1 chất lượng cao)
        if (Utility.getDeviceName().startsWith("Sony")) {
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        } else
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
//        intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 5491520L);
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 25);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set Video file
        activity.startActivityForResult(intent, CAMERA_VIDEO_REQUEST);
        return outputMediaFile;
    }

    public static File getOutputMediaFile(String imgName, String styleFile) {
        File rootFo = new File(urlFile);
        File file;
        if (rootFo.exists()) {
            file = new File(rootFo.getAbsolutePath() + "/" + imgName + "." + styleFile);
        } else {
            rootFo.mkdirs();
            file = new File(rootFo.getAbsolutePath() + "/" + imgName + "." + styleFile);
        }
        return file;
    }

    public static File compressImage(File imageFile, int reqWidth, int reqHeight, Bitmap.CompressFormat compressFormat, int quality, String destinationPath) throws IOException {
        FileOutputStream fileOutputStream = null;
        File file = new File(destinationPath).getParentFile();
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            fileOutputStream = new FileOutputStream(destinationPath);
            // write the compressed bitmap at the destination specified by destinationPath.
            decodeSampledBitmapFromFile(imageFile, reqWidth, reqHeight).compress(compressFormat, quality, fileOutputStream);
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        }
        return new File(destinationPath);
    }

    public static Bitmap decodeSampledBitmapFromFile(File imageFile, int reqWidth, int reqHeight) throws IOException {
        // First decode with inJustDecodeBounds=true to check dimensions
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        Bitmap scaledBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);

        //check the rotation of the image and display it properly
//        ExifInterface exif = null;
//        try {
//            exif = new ExifInterface(imageFile.getAbsolutePath());
//        } catch (Exception e) {
//            System.out.println("-------------ex: " + e.getMessage());
//        }
//        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
//        Matrix matrix = new Matrix();
//        if (orientation == 6) {
//            matrix.postRotate(90);
//        } else if (orientation == 3) {
//            matrix.postRotate(180);
//        } else if (orientation == 8) {
//            matrix.postRotate(270);
//        }
//        scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
        return scaledBitmap;
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    /**
     * call subscribe
     * fileFlowable.subscribe(new org.reactivestreams.Subscriber<File>() {
     * implement methods onSubscribe,onNext,onComplete,onError
     * });
     */
    public static Flowable<File> tinyImage(Context context, final File fileImg) {
        Flowable<File> fileFlowable = new Compressor(context)
                .setDestinationDirectoryPath(urlFile + "/imgtiny/")
                .compressToFileAsFlowable(fileImg)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        return fileFlowable;
    }

    /**
     * tiny nhieu anh cung luc
     * <p>
     * public void tinyMutiImage(final Context context, final File... fileImg) {
     * Observable.from(fileImg).subscribe(new Subscriber<File>() {
     *
     * @Override public void onCompleted() {
     * su ly khi da hoan thanh
     * }
     * @Override public void onError(Throwable e) {
     * su ly neu bi loi
     * }
     * @Override public void onNext(File file) {
     * try {
     * File fileTiny = new Compressor(context)
     * .setDestinationDirectoryPath(ImageUtil.urlFile + "/imgtiny/")
     * .compressToFile(file);
     * sau khi tiny xong thi xuat file va duong dan file
     * } catch (IOException e) {
     * loi exception
     * e.printStackTrace();
     * unsubscribe();
     * }
     * }
     * });
     * }
     * *
     */

    public static Bitmap decodeImageCaptcha(String captchaImg) {
        if ((captchaImg.startsWith("data") ||
                captchaImg.startsWith("base64"))
                || captchaImg.indexOf(",") > 0) {
            captchaImg = captchaImg.substring(captchaImg.indexOf(",") + 1);
        }
        byte[] decodedString = Base64.decode(captchaImg, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        try {
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(decodedByte, (decodedByte.getWidth() * 3), decodedByte.getHeight() * 3, false);
            return scaledBitmap;
        } catch (Exception e) {
            return null;
        }
    }
}
