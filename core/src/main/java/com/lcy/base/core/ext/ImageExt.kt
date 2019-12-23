package com.lcy.base.core.ext

import android.content.Context
import android.database.DatabaseErrorHandler
import android.net.Uri
import android.support.constraint.Placeholder
import android.widget.ImageView
import com.lcy.base.imageloader.ImageLoader
import com.lcy.base.imageloader.ImageLoaderUtil
import com.lcy.base.imageloader.R


/**
 * 加载普通图片
 */
fun ImageView.loadImage(
    context: Context,
    path: String?
) = ImageLoaderUtil.getInstance().displayImage(
    context,
    createImageLoader(path = path, imageView = this)
)

fun ImageView.loadImage(
    context: Context,
    uri: Uri?
) = ImageLoaderUtil.getInstance().displayImage(
    context,
    createImageLoader(uri = uri, imageView = this)
)

/**
 * 加载圆形图片
 */
fun ImageView.loadCircleImage(
    context: Context,
    path: String?
) = ImageLoaderUtil.getInstance().displayImage(
    context, createImageLoader(path = path, imageView = this, circleCrop = true)
)

fun ImageView.loadCircleImage(
    context: Context,
    uri: Uri?
) = ImageLoaderUtil.getInstance().displayImage(
    context, createImageLoader(uri = uri, imageView = this, circleCrop = true)
)

/**
 * 加载圆角图片
 */
fun ImageView.loadRoundImage(
    context: Context,
    path: String?,
    radius: Int = 0
) = ImageLoaderUtil.getInstance().displayImage(
    context, createImageLoader(path = path, imageView = this, radius = radius)
)

/**
 * 加载圆角图片
 */
fun ImageView.loadRoundImage(
    context: Context,
    uri: Uri?,
    radius: Int = 0
) = ImageLoaderUtil.getInstance().displayImage(
    context, createImageLoader(uri = uri, imageView = this, radius = radius)
)

/**
 * 加载本地图片
 */
fun ImageView.loadResImage(
    context: Context,
    resId: Int
) = ImageLoaderUtil.getInstance().displayImageInResource(context, resId, this)


/**
 * 取消加载
 */
fun ImageView.loadClear(context: Context) = ImageLoaderUtil.getInstance().clear(context, this)

fun createImageLoader(
    path: String? = null,
    uri: Uri? = null,
    imageView: ImageView,
    circleCrop: Boolean = false,
    radius: Int = 0,
    placeHolder: Int = R.drawable.image_loader_ic_def_place_holder,
    errorHolder: Int = R.drawable.image_loader_ic_def_place_holder
): ImageLoader {
    val builder = ImageLoader.Builder()
        .placeHolder(placeHolder)
        .errorHolder(errorHolder)
        .circleCrop(circleCrop)
        .radius(radius)
        .imgView(imageView)
    if (path != null) {
        builder.url(path)
    }
    if (uri != null) {
        builder.uri(uri)
    }
    return builder.build()
}