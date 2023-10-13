package com.lcy.base.imageloader;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.lcy.base.imageloader.listener.IGetBitmapListener;
import com.lcy.base.imageloader.listener.IGetDrawableListener;
import com.lcy.base.imageloader.tranform.BlurBitmapTransformation;

import java.io.File;

@SuppressWarnings("all")
public class GlideImageLoaderClient implements IImageLoaderClient {

    @Override
    public void init(Context context) {
    }

    @Override
    public void destroy(Context context) {
        clearMemoryCache(context);
    }

    @Override
    public File getCacheDir(Context context) {
        return Glide.getPhotoCacheDir(context);
    }

    /**
     * 使用ui线程
     */
    @UiThread
    @Override
    public void clearMemoryCache(Context context) {
        GlideApp.get(context).clearMemory();
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void clearDiskCache(final Context context) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                //必须在子线程中  This method must be called on a background thread.
                GlideApp.get(context).clearDiskCache();
                return null;
            }
        };
    }

    @Override
    public Bitmap getBitmapFromCache(Context context, String url) {
        throw new UnsupportedOperationException("glide 不支持同步 获取缓存中 bitmap");
    }

    /**
     * 获取缓存中的图片
     */
    @Override
    public void getBitmap(Context context, String url, final IGetBitmapListener listener) {
        GlideApp.with(context).asBitmap().load(url).into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                if (listener != null) {
                    listener.onBitmap(resource);
                }
            }

            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                if (listener != null) {
                    listener.onFailed(errorDrawable);
                }
            }
        });
    }

    @Override
    public void getBitmap(Context context, Uri uri, final IGetBitmapListener listener) {
        GlideApp.with(context).asBitmap().load(uri).into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                if (listener != null) {
                    listener.onBitmap(resource);
                }
            }

            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                if (listener != null) {
                    listener.onFailed(errorDrawable);
                }
            }
        });
    }

    @Override
    public void getBitmap(Context context, int resId, final IGetBitmapListener listener) {
        GlideApp.with(context).asBitmap().load(resId).into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                if (listener != null) {
                    listener.onBitmap(resource);
                }
            }

            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                if (listener != null) {
                    listener.onFailed(errorDrawable);
                }
            }
        });
    }

    @Override
    public void displayImage(Context ctx, ImageLoader img) {
        GlideApp.with(ctx).load(loadCustom(img)).apply(getOptions(img)).listener(img.getListener()).into(img.getImgView());
    }

    @Override
    public void displayImage(Fragment ctx, ImageLoader img) {
        GlideApp.with(ctx).load(loadCustom(img)).apply(getOptions(img)).listener(img.getListener()).into(img.getImgView());
    }

    @Override
    public void displayImage(Activity ctx, ImageLoader img) {
        GlideApp.with(ctx).load(loadCustom(img)).apply(getOptions(img)).listener(img.getListener()).into(img.getImgView());
    }

    private Object loadCustom(ImageLoader img) {
        if (img.getGlideUrl() != null) {
            return img.getGlideUrl();
        }
        if (img.getUri() != null) {
            return img.getUri();
        }
        if (img.getUrl() != null) {
            return img.getUrl();
        }
        return img.getResId();
    }

    private RequestOptions getOptions(ImageLoader imageLoader) {
        RequestOptions options = new RequestOptions()
                .placeholder(imageLoader.getPlaceHolder())
                .error(imageLoader.getErrorHolder());
        if (imageLoader.isOnlyWifi()) {
            options = options.onlyRetrieveFromCache(true);
        } else {
            options = options.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        }
        //  配置是否避免内存缓存
        options = options.skipMemoryCache(imageLoader.isSkipMemoryCache());
        //  配置磁盘缓存
        options = options.diskCacheStrategy(imageLoader.isSkipDiskCache() ? DiskCacheStrategy.NONE : DiskCacheStrategy.AUTOMATIC);

        if (imageLoader.isCircleCrop()) {
            options = options.circleCrop();
        }
        if (imageLoader.getRadius() > 0) {
            options = options.transforms(new CenterCrop(), new RoundedCorners(imageLoader.getRadius()));
        }
        if (imageLoader.getImageSize() != null) {
            options = options.override(imageLoader.getImageSize().getWidth(), imageLoader.getImageSize().getHeight());
        }
        return options;
    }

    /**
     * 默认的策略是DiskCacheStrategy.AUTOMATIC
     * DiskCacheStrategy.ALL 使用DATA和RESOURCE缓存远程数据，仅使用RESOURCE来缓存本地数据。
     * DiskCacheStrategy.NONE 不使用磁盘缓存
     * DiskCacheStrategy.DATA 在资源解码前就将原始数据写入磁盘缓存
     * DiskCacheStrategy.RESOURCE 在资源解码后将数据写入磁盘缓存，即经过缩放等转换后的图片资源。
     * DiskCacheStrategy.AUTOMATIC 根据原始图片数据和资源编码策略来自动选择磁盘缓存策略。
     *
     * @param context   上下文
     * @param uri       uri
     * @param imageView into
     */
    //DiskCacheStrategy.SOURCE：缓存原始数据 DiskCacheStrategy.DATA对应Glide 3中的DiskCacheStrategy.SOURCE
    @Override
    public void displayImage(Context context, Uri uri, ImageView imageView) {
        GlideApp.with(context).load(uri).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(imageView);
    }

    @Override
    public void displayImage(Context context, String url, ImageView imageView) {
        GlideApp.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(imageView);
    }

    /**
     * @param listener 接口回调需要拿到drawable
     */
    @Override
    public void getDrawable(Context context, String url, final IGetDrawableListener listener) {
        GlideApp.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                if (listener != null) {
                    listener.onDrawable(resource);
                }
            }

            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                if (listener != null) {
                    listener.onFailed(errorDrawable);
                }
            }
        });
    }

    @Override
    public void getDrawable(Context context, Uri uri, final IGetDrawableListener listener) {
        GlideApp.with(context).load(uri).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                if (listener != null) {
                    listener.onDrawable(resource);
                }
            }
        });
    }

    private RequestOptions blurRequestOptions(int defRes, int blurRadius) {
        return new RequestOptions()
                .placeholder(defRes)
                .error(defRes)
                .transform(new BlurBitmapTransformation(blurRadius));
    }

    @Override
    public void displayBlurImage(Context context, int resId, ImageView imageView, int blurRadius) {
        GlideApp.with(context).load(resId).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).apply(blurRequestOptions(resId, blurRadius)).into(imageView);
    }

    @Override
    public void displayBlurImage(Context context, Uri uri, ImageView imageView, int blurRadius) {
        GlideApp.with(context).load(uri).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).apply(blurRequestOptions(R.drawable.image_loader_ic_def_place_holder, blurRadius)).into(imageView);
    }

    @Override
    public void displayBlurImage(Context context, String url, ImageView imageView, int blurRadius) {
        GlideApp.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).apply(blurRequestOptions(R.drawable.image_loader_ic_def_place_holder, blurRadius)).into(imageView);
    }

    /**
     * 加载资源文件
     */
    @Override
    public void displayImageInResource(Context context, int resId, ImageView imageView) {
        GlideApp.with(context).load(resId).diskCacheStrategy(DiskCacheStrategy.NONE).into(imageView);
    }

    @Override
    public void displayImageInResource(Fragment fragment, int resId, ImageView imageView) {
        GlideApp.with(fragment).load(resId).diskCacheStrategy(DiskCacheStrategy.NONE).into(imageView);
    }

    @Override
    public void displayImageInResource(Activity activity, int resId, ImageView imageView) {
        GlideApp.with(activity).load(resId).diskCacheStrategy(DiskCacheStrategy.NONE).into(imageView);
    }


    @Override
    public void clear(Activity activity, ImageView imageView) {
        GlideApp.with(activity).clear(imageView);
    }

    @Override
    public void clear(Context context, ImageView imageView) {
        GlideApp.with(context).clear(imageView);
    }

    @Override
    public void clear(Fragment fragment, ImageView imageView) {
        GlideApp.with(fragment).clear(imageView);
    }

    @Override
    public void displayImageErrorReload(Fragment fragment, String url, String fallbackUrl, ImageView imageView) {
        GlideApp.with(fragment)
                .load(url)
                .error(GlideApp.with(fragment)
                        .load(fallbackUrl))
                .into(imageView);
    }

    @Override
    public void displayImageErrorReload(Activity activity, String url, String fallbackUrl, ImageView imageView) {
        GlideApp.with(activity)
                .load(url)
                .error(GlideApp.with(activity)
                        .load(fallbackUrl))
                .into(imageView);
    }

    @Override
    public void displayImageErrorReload(Context context, String url, String fallbackUrl, ImageView imageView) {
        GlideApp.with(context)
                .load(url)
                .error(GlideApp.with(context)
                        .load(fallbackUrl))
                .into(imageView);
    }

    @Override
    public void glidePauseRequests(Context context) {
        GlideApp.with(context).pauseRequests();
    }

    @Override
    public void glidePauseRequests(Activity activity) {
        GlideApp.with(activity).pauseRequests();
    }

    @Override
    public void glidePauseRequests(Fragment fragment) {
        GlideApp.with(fragment).pauseRequests();
    }

    @Override
    public void glideResumeRequests(Context context) {
        GlideApp.with(context).resumeRequests();
    }

    @Override
    public void glideResumeRequests(Activity activity) {
        GlideApp.with(activity).resumeRequests();
    }

    @Override
    public void glideResumeRequests(Fragment fragment) {
        GlideApp.with(fragment).resumeRequests();
    }

}
