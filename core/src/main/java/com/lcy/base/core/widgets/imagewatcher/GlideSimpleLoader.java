package com.lcy.base.core.widgets.imagewatcher;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.lcy.base.imageloader.GlideApp;

public class GlideSimpleLoader implements ImageWatcher.Loader {
    @Override
    public void load(Context context, Uri uri, final ImageWatcher.LoadCallback lc) {
        GlideApp.with(context).load(uri).into(new SimpleTarget<Drawable>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                lc.onResourceReady(resource);
            }

            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                lc.onLoadFailed(errorDrawable);
            }

            @Override
            public void onLoadStarted(@Nullable Drawable placeholder) {
                lc.onLoadStarted(placeholder);
            }
        });
    }
}
