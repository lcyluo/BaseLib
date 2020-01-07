package com.lcy.base.core.ext

import android.graphics.drawable.Drawable
import android.widget.TextView

/**
 * 设置颜色直接使用colors.xml中定义的颜色即可
 */
fun TextView.setColor(resId: Int) {
    this.setTextColor(resources.getColor(resId))
}

/**
 * 设置TextView图标
 */
fun TextView.setDrawable(
    leftResId: Int = -1,
    topResId: Int = -1,
    rightResId: Int = -1,
    bottomResId: Int = -1
) {
    val leftDrawable: Drawable? = leftResId.getDrawable(this.context)
    val topDrawable: Drawable? = topResId.getDrawable(this.context)
    val rightDrawable: Drawable? = rightResId.getDrawable(this.context)
    val bottomDrawable: Drawable? = bottomResId.getDrawable(this.context)
    this.setCompoundDrawables(leftDrawable, topDrawable, rightDrawable, bottomDrawable)
}

fun TextView.setDrawableRelative(
    startResId: Int = -1,
    topResId: Int = -1,
    endResId: Int = -1,
    bottomResId: Int = -1
) {
    val startDrawable: Drawable? = startResId.getDrawable(this.context)
    val topDrawable: Drawable? = topResId.getDrawable(this.context)
    val endDrawable: Drawable? = endResId.getDrawable(this.context)
    val bottomDrawable: Drawable? = bottomResId.getDrawable(this.context)
    this.setCompoundDrawablesRelative(startDrawable, topDrawable, endDrawable, bottomDrawable)
}

