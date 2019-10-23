package com.lcy.base.imageloader;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.lcy.base.imageloader.listener.IGetBitmapListener;
import com.lcy.base.imageloader.listener.IGetDrawableListener;

import java.io.File;

/**
 * 图片加载入口单例,封装Glide4.8版本
 *
 * @author lcy
 */
public class ImageLoaderUtil implements IImageLoaderClient {

    private IImageLoaderClient client;

    private ImageLoaderUtil() {
        client = new GlideImageLoaderClient();
    }


    public static ImageLoaderUtil getInstance() {
        return ImageLoaderUtilHolder.holder;
    }

    private static class ImageLoaderUtilHolder {

        private static final ImageLoaderUtil holder = new ImageLoaderUtil();
    }

    /**
     * 方便兼容其他图片加载库
     * 其他图片加载库实现 IImageLoaderClient 接口,实现相关方法
     */
    public void setImageLoaderClient(Context context, IImageLoaderClient client) {
        if (this.client != null) {
            this.client.clearMemoryCache(context);
        }
        if (this.client != client) {
            this.client = client;
            if (this.client != null) {
                this.client.init(context);
            }
        }
    }

    @Override
    public void init(Context context) {

    }

    @Override
    public void destroy(Context context) {
        if (client != null) {
            client.destroy(context);
            client = null;
        }
    }

    @Override
    public File getCacheDir(Context context) {
        if (client != null) {
            return client.getCacheDir(context);
        }
        return null;
    }

    @Override
    public void clearMemoryCache(Context context) {
        if (client != null) {
            client.clearMemoryCache(context);
        }
    }

    @Override
    public void clearDiskCache(Context context) {
        if (client != null) {
            client.clearDiskCache(context);
        }
    }

    @Override
    public Bitmap getBitmapFromCache(Context context, String url) {
        if (client != null) {
            return client.getBitmapFromCache(context, url);
        }
        return null;
    }

    @Override
    public void getBitmapFromCache(Context context, String url, IGetBitmapListener listener) {
        if (client != null) {
            client.getBitmapFromCache(context, url, listener);
        }
    }

    @Override
    public void displayImage(Context ctx, ImageLoader img) {
        if (client != null) {
            client.displayImage(ctx, img);
        }
    }

    @Override
    public void displayImage(Fragment ctx, ImageLoader img) {
        if (client != null) {
            client.displayImage(ctx, img);
        }
    }

    @Override
    public void displayImage(Activity ctx, ImageLoader img) {
        if (client != null) {
            client.displayImage(ctx, img);
        }
    }

    @Override
    public void displayImage(Context context, Uri uri, ImageView imageView) {
        if (client != null) {
            client.displayImage(context, uri, imageView);
        }
    }

    @Override
    public void displayImage(Context context, String url, ImageView imageView) {
        if (client != null) {
            client.displayImage(context, url, imageView);
        }
    }

    @Override
    public void getDrawable(Context context, String url, final IGetDrawableListener listener) {
        if (client != null) {
            client.getDrawable(context, url, listener);
        }
    }

    @Override
    public void getDrawable(Context context, Uri uri, IGetDrawableListener listener) {
        if (client != null) {
            client.getDrawable(context, uri, listener);
        }
    }

    @Override
    public void displayBlurImage(Context context, int resId, ImageView imageView, int blurRadius) {
        if (client != null) {
            client.displayBlurImage(context, resId, imageView, blurRadius);
        }
    }

    @Override
    public void displayBlurImage(Context context, Uri uri, ImageView imageView, int blurRadius) {
        if (client != null) {
            client.displayBlurImage(context, uri, imageView, blurRadius);
        }
    }

    @Override
    public void displayBlurImage(Context context, String url, ImageView imageView, int blurRadius) {
        if (client != null) {
            client.displayBlurImage(context, url, imageView, blurRadius);
        }
    }

    @Override
    public void displayImageInResource(Activity activity, int resId, ImageView imageView) {
        if (client != null) {
            client.displayImageInResource(activity, resId, imageView);
        }
    }

    @Override
    public void displayImageInResource(Context context, int resId, ImageView imageView) {
        if (client != null) {
            client.displayImageInResource(context, resId, imageView);
        }
    }

    @Override
    public void displayImageInResource(Fragment fragment, int resId, ImageView imageView) {
        if (client != null) {
            client.displayImageInResource(fragment, resId, imageView);
        }
    }

    /**
     * 尽管及时取消不必要的加载是很好的实践，但这并不是必须的操作。
     * 实际上，当 Glide.with() 中传入的 Activity 或 Fragment 实例销毁时，
     * Glide 会自动取消加载并回收资源。这里我隐藏了api的调用
     * {@hide}
     */
    @Override
    public void clear(Activity activity, ImageView imageView) {
        if (client != null) {
            client.clear(activity, imageView);
        }
    }

    /**
     * 尽管及时取消不必要的加载是很好的实践，但这并不是必须的操作。
     * 实际上，当 Glide.with() 中传入的 Activity 或 Fragment 实例销毁时，
     * Glide 会自动取消加载并回收资源。这里我隐藏了api的调用
     * {@hide}
     */
    @Override
    public void clear(Context context, ImageView imageView) {
        if (client != null) {
            client.clear(context, imageView);
        }
    }

    /**
     * 尽管及时取消不必要的加载是很好的实践，但这并不是必须的操作。
     * 实际上，当 Glide.with() 中传入的 Activity 或 Fragment 实例销毁时，
     * Glide 会自动取消加载并回收资源。这里我隐藏了api的调用
     * {@hide}
     */
    @Override
    public void clear(Fragment fragment, ImageView imageView) {
        if (client != null) {
            client.clear(fragment, imageView);
        }
    }

    @Override
    public void displayImageErrorReload(Fragment fragment, String url, String fallbackUrl, ImageView imageView) {
        if (client != null) {
            client.displayImageErrorReload(fragment, url, fallbackUrl, imageView);
        }
    }

    @Override
    public void displayImageErrorReload(Activity activity, String url, String fallbackUrl, ImageView imageView) {
        if (client != null) {
            client.displayImageErrorReload(activity, url, fallbackUrl, imageView);
        }
    }

    @Override
    public void displayImageErrorReload(Context context, String url, String fallbackUrl, ImageView imageView) {
        if (client != null) {
            client.displayImageErrorReload(context, url, fallbackUrl, imageView);
        }
    }

    @Override
    public void glidePauseRequests(Context context) {
        if (client != null) {
            client.glidePauseRequests(context);
        }
    }

    @Override
    public void glidePauseRequests(Activity activity) {
        if (client != null) {
            client.glidePauseRequests(activity);
        }
    }

    @Override
    public void glidePauseRequests(Fragment fragment) {
        if (client != null) {
            client.glidePauseRequests(fragment);
        }
    }

    @Override
    public void glideResumeRequests(Context context) {
        if (client != null) {
            client.glideResumeRequests(context);
        }
    }

    @Override
    public void glideResumeRequests(Activity activity) {
        if (client != null) {
            client.glideResumeRequests(activity);
        }
    }

    @Override
    public void glideResumeRequests(Fragment fragment) {
        if (client != null) {
            client.glideResumeRequests(fragment);
        }
    }

}
