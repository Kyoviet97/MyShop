package com.mylibrary.utils.tiny;

import android.content.Context;
import android.graphics.Bitmap;

import com.mylibrary.utils.ImageUtil;
import com.mylibrary.utils.Loging;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;

/**
 * Created on : June 18, 2016
 * Author     : zetbaitsu
 * Name       : Zetra
 * GitHub     : https://github.com/zetbaitsu
 */
public class Compressor {
    //max width and height values of the compressed image is taken as 612x816
    private int maxWidth = 612;
    private int maxHeight = 816;
    private Bitmap.CompressFormat compressFormat = Bitmap.CompressFormat.JPEG;
    private int quality = 80;
    private String destinationDirectoryPath;

    public Compressor(Context context) {
        destinationDirectoryPath = context.getCacheDir().getPath() + File.separator + "images";
    }

    public Compressor setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
        return this;
    }

    public Compressor setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
        return this;
    }

    public Compressor setCompressFormat(Bitmap.CompressFormat compressFormat) {
        this.compressFormat = compressFormat;
        return this;
    }

    public Compressor setQuality(int quality) {
        this.quality = quality;
        return this;
    }

    public Compressor setDestinationDirectoryPath(String destinationDirectoryPath) {
        this.destinationDirectoryPath = destinationDirectoryPath;
        return this;
    }

    public File compressToFile(File imageFile) throws IOException {
        return compressToFile(imageFile, imageFile.getName());
    }

    public File compressToFile(File imageFile, String compressedFileName) throws IOException {
        return ImageUtil.compressImage(imageFile, maxWidth, maxHeight, compressFormat, quality,
                destinationDirectoryPath + File.separator + compressedFileName);
    }

    public Bitmap compressToBitmap(File imageFile) throws IOException {
        return ImageUtil.decodeSampledBitmapFromFile(imageFile, maxWidth, maxHeight);
    }

    public Flowable<File> compressToFileAsFlowable(final File imageFile) {
        return compressToFileAsFlowable(imageFile, imageFile.getName());
    }

    public Flowable<File> compressToFileAsFlowable(final File imageFile, final String compressedFileName) {
        return Flowable.defer(new Callable<Flowable<File>>() {
            @Override
            public Flowable<File> call() {
                try {
                    return Flowable.just(compressToFile(imageFile, compressedFileName));
                } catch (IOException e) {
                    return Flowable.error(e);
                }
            }
        });
    }

    public Flowable<Bitmap> compressToBitmapAsFlowable(final File imageFile) {
        return Flowable.defer(new Callable<Flowable<Bitmap>>() {
            @Override
            public Flowable<Bitmap> call() {
                try {
                    return Flowable.just(compressToBitmap(imageFile));
                } catch (IOException e) {
                    return Flowable.error(e);
                }
            }
        });
    }

    public interface OnCompleteTinyListener {
        void onCompleteTinyListener(File file);

        void onErrorTinyListener(String msg);
    }

    public interface OnCompleteMutiTinyListener {
        void onCompleteTinyListener();

        void onNextTinyListener(File file);

        void onErrorTinyListener(String msg);
    }

    private File fileImg;

    public void runTinySingeImage(final OnCompleteTinyListener completeTinyListener, final File fileImgTiny) {
        Observable.fromArray(fileImgTiny)
                .subscribe(new Observer<File>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(File file) {
                        if (file == null || !file.exists()) {
                            completeTinyListener.onErrorTinyListener("Không tìm thấy file ảnh! 1.1");
                            return;
                        }
                        try {
                            fileImg = setDestinationDirectoryPath(ImageUtil.urlFile + "/imgtiny/").compressToFile(file);
                        } catch (IOException e) {
                            completeTinyListener.onErrorTinyListener(e.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        completeTinyListener.onErrorTinyListener(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        if (fileImg != null) completeTinyListener.onCompleteTinyListener(fileImg);
                        else
                            completeTinyListener.onErrorTinyListener("Không tìm thấy file ảnh! 1.0");
                    }
                });
    }

    public void runTinyMutiImage(final OnCompleteMutiTinyListener completeTinyListener, final File fileImgTiny) {
        Observable.fromArray(fileImgTiny)
                .subscribe(new Observer<File>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(File file) {
                        if (file == null || !file.exists()) {
                            completeTinyListener.onErrorTinyListener("Không tìm thấy file ảnh! 1.1");
                            return;
                        }
                        try {
                            File compressToFile = setDestinationDirectoryPath(ImageUtil.urlFile + "/imgtiny/").compressToFile(file);
                            completeTinyListener.onNextTinyListener(compressToFile);
                        } catch (IOException e) {
                            completeTinyListener.onErrorTinyListener(e.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        completeTinyListener.onErrorTinyListener(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        if (fileImg != null) completeTinyListener.onCompleteTinyListener();
                        else
                            completeTinyListener.onErrorTinyListener("Không tìm thấy file ảnh! 1.0");
                    }
                });
    }
}
