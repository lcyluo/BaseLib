package com.lcy.base.imageloader;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import androidx.annotation.IdRes;

import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.request.RequestListener;
import com.lcy.base.imageloader.listener.ImageSize;

public class ImageLoader {

    /**
     * 正常加载图片
     */
    public static final int LOAD_STRATEGY_NORMAL = 0;
    /**
     * 只在WIFI下加载图片
     */
    public static final int LOAD_STRATEGY_ONLY_WIFI = 1;

    private String url;         //  需要解析的url
    private Uri uri;            //  需要解析的uri
    @IdRes
    private int resId;          //  资源ID
    private int placeHolder;    //  当没有成功加载的时候显示的图片
    private int errorHolder;    //  当图片加载失败的时候显示的图片
    private ImageView imgView;  //  ImageView的实例
    private int wifiStrategy;   //  加载策略，是否在wifi下才加载
    private boolean circleCrop; //  是否加载圆形
    private boolean onlyWifi;   //  只在WiFi下加载
    private int radius;         //  圆角
    private boolean skipMemoryCache;    //  防止内存缓存
    private boolean skipDiskCache;      //  防止磁盘缓存(主要针对验证码图片)
    private ImageSize imageSize;        //  配置加载图片的大小
    private GlideUrl glideUrl;          //  自定义缓存key
    // 是否自动重试
    private boolean autoRetry;

    private ImageLoader(Builder builder) {
        this.url = builder.url;
        this.uri = builder.uri;
        this.glideUrl = builder.glideUrl;
        this.resId = builder.resId;
        this.placeHolder = builder.placeHolder;
        this.errorHolder = builder.errorHolder;
        this.imgView = builder.imgView;
        this.wifiStrategy = builder.wifiStrategy;
        this.circleCrop = builder.circleCrop;
        this.radius = builder.radius;
        this.onlyWifi = builder.onlyWifi;
        this.skipMemoryCache = builder.skipMemoryCache;
        this.skipDiskCache = builder.skipDiskCache;
        this.imageSize = builder.imageSize;
        this.autoRetry = builder.autoRetry;
    }

    public String getUrl() {
        return url;
    }

    public Uri getUri() {
        return uri;
    }

    public int getResId() {
        return resId;
    }

    public int getPlaceHolder() {
        return placeHolder;
    }

    public int getErrorHolder() {
        return errorHolder;
    }

    public ImageView getImgView() {
        return imgView;
    }

    public int getWifiStrategy() {
        return wifiStrategy;
    }

    public boolean isCircleCrop() {
        return circleCrop;
    }

    public boolean isOnlyWifi() {
        return onlyWifi;
    }

    public boolean isSkipMemoryCache() {
        return skipMemoryCache;
    }

    public boolean isSkipDiskCache() {
        return skipDiskCache;
    }

    public int getRadius() {
        return radius;
    }

    public ImageSize getImageSize() {
        return imageSize;
    }

    public GlideUrl getGlideUrl() {
        return glideUrl;
    }

    public boolean isAutoRetry() {
        return autoRetry;
    }

    public static class Builder {
        private String url;
        private Uri uri;
        private int resId;
        private int placeHolder;
        private int errorHolder;
        private ImageView imgView;
        private int wifiStrategy;
        private boolean circleCrop;
        private int radius;
        private boolean onlyWifi;
        private boolean skipMemoryCache;
        private boolean skipDiskCache;
        private ImageSize imageSize;
        private GlideUrl glideUrl;
        private boolean autoRetry;

        public Builder() {
            this.url = "";
            this.uri = null;
            this.resId = -1;
            this.placeHolder = R.drawable.image_loader_ic_def_place_holder;
            this.errorHolder = R.drawable.image_loader_ic_def_place_holder;
            this.imgView = null;
            this.wifiStrategy = ImageLoader.LOAD_STRATEGY_NORMAL;
            this.circleCrop = false;
            this.radius = 0;
            this.onlyWifi = false;
            this.skipMemoryCache = false;
            this.skipDiskCache = false;
            this.imageSize = null;
            this.glideUrl = null;
            this.autoRetry = false;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder uri(Uri uri) {
            this.uri = uri;
            return this;
        }

        public Builder resId(@IdRes int resId) {
            this.resId = resId;
            return this;
        }

        public Builder placeHolder(int placeHolder) {
            this.placeHolder = placeHolder;
            return this;
        }

        public Builder errorHolder(int errorHolder) {
            this.errorHolder = errorHolder;
            return this;
        }

        public Builder imgView(ImageView imgView) {
            this.imgView = imgView;
            return this;
        }

        public Builder strategy(int strategy) {
            this.wifiStrategy = strategy;
            return this;
        }

        public Builder circleCrop(boolean circleCrop) {
            this.circleCrop = circleCrop;
            return this;
        }

        public Builder radius(int radius) {
            this.radius = radius;
            return this;
        }

        public Builder onlyWifi(boolean onlyWifi) {
            this.onlyWifi = onlyWifi;
            return this;
        }

        public Builder skipMemoryCache(boolean skipMemoryCache) {
            this.skipMemoryCache = skipMemoryCache;
            return this;
        }

        public Builder skipDiskCache(boolean skipDiskCache) {
            this.skipDiskCache = skipDiskCache;
            return this;
        }

        public Builder imageSize(ImageSize imageSize) {
            this.imageSize = imageSize;
            return this;
        }

        public Builder glideUrl(GlideUrl glideUrl) {
            this.glideUrl = glideUrl;
            return this;
        }

        public Builder autoRetry(boolean autoRetry) {
            this.autoRetry = autoRetry;
            return this;
        }

        public ImageLoader build() {
            return new ImageLoader(this);
        }

    }
}
