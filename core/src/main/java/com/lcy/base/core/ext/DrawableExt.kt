package com.lcy.base.core.ext

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.DrawableCompat
import com.lcy.base.core.ext.getColor
import com.lcy.base.core.ext.getDrawable

fun Int.tintColorRes(context: Context, resId: Int): Drawable? {
    val drawable = this.getDrawable(context) ?: return null
    return drawable.tint(resId.getColor(context))
}


fun Int.tint(context: Context, tintColor: Int): Drawable? {
    val drawable = this.getDrawable(context) ?: return null
    return drawable.tint(tintColor)
}

fun Int.tint(context: Context, colorStateList: ColorStateList): Drawable? {
    val drawable = this.getDrawable(context) ?: return null
    return drawable.tint(colorStateList)
}


fun Drawable.tint(colorStateList: ColorStateList): Drawable {
    val wrappedDrawable = DrawableCompat.wrap(this)
    DrawableCompat.setTintList(wrappedDrawable, colorStateList)
    return wrappedDrawable
}

fun Drawable.tint(tintColor: Int): Drawable {
    val wrappedDrawable = DrawableCompat.wrap(this)
    DrawableCompat.setTintList(wrappedDrawable, ColorStateList.valueOf(tintColor))
    return wrappedDrawable
}

fun Drawable.tint(context: Context, tintColorResId: Int): Drawable {
    val wrappedDrawable = DrawableCompat.wrap(this)
    DrawableCompat.setTintList(
        wrappedDrawable,
        ColorStateList.valueOf(tintColorResId.getColor(context))
    )
    return wrappedDrawable
}