package com.mylibrary;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.mylibrary.manager.ConfigManager;
import com.mylibrary.manager.ObjectManager;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * Created by Annv on 3/9/18.
 */

public class MyApplication extends MultiDexApplication {

    public static int connectionStatus = 0;
    public static ConfigManager configManager;
    public static ObjectManager objectManager;
    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        configManager = ConfigManager.open(this);
        objectManager = new ObjectManager(configManager);
        setUpImagloader();
    }

    public static DisplayImageOptions.Builder getOptionImageLoader(int imgDefault) {
        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder()
                .showImageOnLoading(imgDefault)
                .showImageForEmptyUri(imgDefault)
                .showImageOnFail(imgDefault)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565);
        return builder;
    }

    public static DisplayImageOptions.Builder getOptionImageLoader() {
        DisplayImageOptions.Builder options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_image_default)
                .showImageForEmptyUri(R.drawable.ic_image_default)
                .showImageOnFail(R.drawable.ic_image_default)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565);
        return options;
    }

    private void setUpImagloader() {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(this);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app
        try {
            ImageLoader.getInstance().init(config.build());
        } catch (Throwable e) {

        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
