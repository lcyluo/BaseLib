package com.lcy.base.imageloader;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.lcy.base.imageloader.listener.IGetBitmapListener;
import com.lcy.base.imageloader.listener.IGetDrawableListener;

import java.io.File;

/**
 * 定义图片加载客户端的一些接口方法
 *
 * @author lcy
 */

interface IImageLoaderClient {

    void init(Context context);

    void destroy(Context context);

    File getCacheDir(Context context);

    void clearMemoryCache(Context context);

    void clearDiskCache(Context context);

    Bitmap getBitmapFromCache(Context context, String url);

    void getBitmapFromCache(Context context, String url, IGetBitmapListener listener);

    void displayImage(Context ctx, ImageLoader img);

    void displayImage(Fragment ctx, ImageLoader img);

    void displayImage(Activity ctx, ImageLoader img);

    void displayImage(Context context, int resId, ImageView imageView);

    void displayImage(Context context, String url, ImageView imageView);

    void displayImage(Fragment fragment, String url, ImageView imageView);


    void getDrawable(Context context, String url, IGetDrawableListener listener);

    void displayBlurImage(Context context, String url, ImageView imageView, int blurRadius);

    void displayBlurImage(Context context, int resId, ImageView imageView, int blurRadius);


    void displayImageInResource(Activity activity, int resId, ImageView imageView);

    void displayImageInResource(Context context, int resId, ImageView imageView);

    void displayImageInResource(Fragment fragment, int resId, ImageView imageView);

    /**
     * 停止图片的加载，对某一个的Activity
     *
     * @hide
     */
    void clear(Activity activity, ImageView imageView);

    /**
     * 停止图片的加载，context
     * {@hide}
     */
    void clear(Context context, ImageView imageView);

    /**
     * 停止图片的加载，fragment
     * {@hide}
     */
    void clear(Fragment fragment, ImageView imageView);

    void displayImageErrorReload(Fragment fragment, String url, String fallbackUrl, ImageView imageView);

    void displayImageErrorReload(Activity activity, String url, String fallbackUrl, ImageView imageView);

    void displayImageErrorReload(Context context, String url, String fallbackUrl, ImageView imageView);


    //  失去焦点，建议实际的项目中少用，取消求情
    void glidePauseRequests(Context context);

    void glidePauseRequests(Activity activity);

    void glidePauseRequests(Fragment fragment);

    //  获取焦点，建议实际的项目中少用
    void glideResumeRequests(Context context);

    void glideResumeRequests(Activity activity);

    void glideResumeRequests(Fragment fragment);

}
