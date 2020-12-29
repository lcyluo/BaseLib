package com.lcy.base.core.ext

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.lcy.base.imageloader.ImageLoader
import com.lcy.base.imageloader.ImageLoaderUtil
import com.lcy.base.imageloader.R


/**
 * 加载普通图片
 */
fun ImageView.loadImage(
    context: Context,
    path: String? = null,
    uri: Uri? = null,
    resId: Int = -1,
    placeHolder: Int = R.drawable.image_loader_ic_def_place_holder,
    errorHolder: Int = R.drawable.image_loader_ic_def_place_holder
) = ImageLoaderUtil.getInstance().displayImage(
    context,
    createImageLoader(
        path = path,
        uri = uri,
        resId = resId,
        imageView = this,
        placeHolder = placeHolder,
        errorHolder = errorHolder
    )
)

/**
 * 加载圆形图片
 */
fun ImageView.loadCircleImage(
    context: Context,
    path: String? = null,
    uri: Uri? = null,
    resId: Int = -1,
    placeHolder: Int = R.drawable.image_loader_ic_def_place_holder,
    errorHolder: Int = R.drawable.image_loader_ic_def_place_holder
) = ImageLoaderUtil.getInstance().displayImage(
    context,
    createImageLoader(
        path = path,
        uri = uri,
        resId = resId,
        imageView = this,
        circleCrop = true,
        placeHolder = placeHolder,
        errorHolder = errorHolder
    )
)

/**
 * 加载圆角图片
 */
fun ImageView.loadRoundImage(
    context: Context,
    path: String? = null,
    uri: Uri? = null,
    resId: Int = -1,
    radius: Int = 0,
    placeHolder: Int = R.drawable.image_loader_ic_def_place_holder,
    errorHolder: Int = R.drawable.image_loader_ic_def_place_holder
) = ImageLoaderUtil.getInstance().displayImage(
    context,
    createImageLoader(
        path = path,
        uri = uri,
        resId = resId,
        imageView = this,
        radius = radius,
        placeHolder = placeHolder,
        errorHolder = errorHolder
    )
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
    resId: Int = -1,
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
    if (resId > 0) {
        builder.resId(resId)
    }
    return builder.build()
}