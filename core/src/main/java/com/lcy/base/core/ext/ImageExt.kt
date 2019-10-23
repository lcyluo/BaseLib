package com.lcy.base.core.ext

import android.content.Context
import android.database.DatabaseErrorHandler
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
    path: String,
    placeHolder: Int = R.drawable.image_loader_ic_def_place_holder,
    errorHolder: Int = R.drawable.image_loader_ic_def_place_holder
) = ImageLoaderUtil.getInstance()
    .displayImage(context, createImageLoader(path, this, false, 0, placeHolder, errorHolder))

/**
 * 加载圆形图片
 */
fun ImageView.loadCircleImage(
    context: Context,
    path: String,
    placeHolder: Int = R.drawable.image_loader_ic_def_place_holder,
    errorHolder: Int = R.drawable.image_loader_ic_def_place_holder
) = ImageLoaderUtil.getInstance()
    .displayImage(context, createImageLoader(path, this, true, 0, placeHolder, errorHolder))

/**
 * 加载圆角图片
 */
fun ImageView.loadRoundImage(
    context: Context,
    path: String,
    radius: Int = 0,
    placeHolder: Int = R.drawable.image_loader_ic_def_place_holder,
    errorHolder: Int = R.drawable.image_loader_ic_def_place_holder
) = ImageLoaderUtil.getInstance()
    .displayImage(context, createImageLoader(path, this, false, radius, placeHolder, errorHolder))

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
    path: String, imageView: ImageView,
    circleCrop: Boolean = false,
    radius: Int = 0,
    placeHolder: Int = R.drawable.image_loader_ic_def_place_holder,
    errorHolder: Int = R.drawable.image_loader_ic_def_place_holder
): ImageLoader = ImageLoader.Builder()
    .url(path)
    .placeHolder(placeHolder)
    .errorHolder(errorHolder)
    .circleCrop(circleCrop)
    .radius(radius)
    .imgView(imageView)
    .build()