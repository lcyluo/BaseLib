@file:Suppress("DEPRECATION")

package com.lcy.base.core.ext

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable

fun Int.getDrawable(context: Context?): Drawable? {
    if (context == null || this <= 0) return null
    val drawable = context.resources.getDrawable(this)
    drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
    return drawable
}

fun Int.getColor(context: Context?): Int {
    if (context == null || this <= 0) return Color.TRANSPARENT
    return context.resources.getColor(this)
}

fun Context.getId(resourceName: String): Int {
    return getIdentifierByType(this, resourceName, "id")
}

fun Context.getLayoutId(resourceName: String): Int {
    return getIdentifierByType(this, resourceName, "layout")
}

fun Context.getStringId(resourceName: String): Int {
    return getIdentifierByType(this, resourceName, "string")
}

fun Context.getDrawableId(resourceName: String): Int {
    return getIdentifierByType(this, resourceName, "drawable")
}

fun Context.getMipmapId(resourceName: String): Int {
    return getIdentifierByType(this, resourceName, "mipmap")
}

fun Context.getColorId(resourceName: String): Int {
    return getIdentifierByType(this, resourceName, "color")
}

fun Context.getDimenId(resourceName: String): Int {
    return getIdentifierByType(this, resourceName, "dimen")
}

fun Context.getAttrId(resourceName: String): Int {
    return getIdentifierByType(this, resourceName, "attr")
}

fun Context.getStyleId(resourceName: String): Int {
    return getIdentifierByType(this, resourceName, "style")
}

fun Context.getAnimId(resourceName: String): Int {
    return getIdentifierByType(this, resourceName, "anim")
}

fun Context.getArrayId(resourceName: String): Int {
    return getIdentifierByType(this, resourceName, "array")
}

fun Context.getIntegerId(resourceName: String): Int {
    return getIdentifierByType(this, resourceName, "integer")
}

fun Context.getBoolId(resourceName: String): Int {
    return getIdentifierByType(this, resourceName, "bool")
}

private fun getIdentifierByType(context: Context, resourceName: String, defType: String): Int {
    return context.resources.getIdentifier(resourceName, defType, context.packageName)
}
