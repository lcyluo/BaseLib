package com.lcy.base.core.ext

import android.content.Context

/**
 * 获取屏幕宽度，单位像素
 */
fun Context.displayWidth(): Int = resources.displayMetrics.widthPixels

/**
 * 获取屏幕高度，单位像素
 */
fun Context.displayHeight(): Int = resources.displayMetrics.heightPixels

/**
 * dp转px
 */
fun Context.dp2px(dpValue: Float): Int {
    val scale = resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}

fun Context.px2dp(pxValue: Float): Int {
    val scale = resources.displayMetrics.density
    return (pxValue / scale + 0.5f).toInt()
}

fun Context.sp2px(spValue: Float): Int {
    val scale = resources.displayMetrics.scaledDensity
    return (spValue * scale + 0.5f).toInt()
}

fun Context.px2sp(pxValue: Float): Int {
    val scale = resources.displayMetrics.scaledDensity
    return (pxValue / scale + 0.5f).toInt()
}

