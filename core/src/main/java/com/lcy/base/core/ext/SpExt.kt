package com.lcy.base.core.ext

import com.lcy.base.core.utils.SpUtil

/**
 * 获取key对应的值
 */
fun String.getString(defaultValue: String? = null): String? {
    return SpUtil.getInstance().getString(this, defaultValue)
}

/**
 * 存储key
 */
fun String.putString(value: String) {
    SpUtil.getInstance().putString(this, value)
}

fun String.getInt(defaultValue: Int = 0): Int {
    return SpUtil.getInstance().getInt(this, defaultValue)
}

fun String.putInt(value: Int = 0) {
    SpUtil.getInstance().putInt(this, value)
}

fun String.getBool(defaultValue: Boolean = false): Boolean {
    return SpUtil.getInstance().getBoolean(this, defaultValue)
}

fun String.putBool(value: Boolean = false) {
    SpUtil.getInstance().putBoolean(this, value)
}

fun String.getFloat(defaultValue: Float = 0f): Float {
    return SpUtil.getInstance().getFloat(this, defaultValue)
}

fun String.putFloat(value: Float = 0f) {
    SpUtil.getInstance().putFloat(this, value)
}

fun String.getLong(defaultValue: Long = 0): Long {
    return SpUtil.getInstance().getLong(this, defaultValue)
}

fun String.putLong(value: Long = 0) {
    SpUtil.getInstance().putLong(this, value)
}

/**
 * key是否存在
 */
fun String.keyExit(): Boolean {
    return SpUtil.getInstance().isKeyExist(this)
}

/**
 * 移除key
 */
fun String.keyRemove() {
    SpUtil.getInstance().remove(this)
}

