package com.lcy.base.imageloader.listener;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;

/**
 * 获取Bitmap回调方法
 *
 * @author lcy
 */
public interface IGetBitmapListener {

    void onBitmap(Bitmap bitmap);

    void onFailed(@Nullable Drawable errorDrawable);
}
