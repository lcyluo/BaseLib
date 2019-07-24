package com.lcy.base.picker.image.function

import android.net.Uri

import com.lcy.base.picker.image.entity.Result

fun parseResultNoExtraData(uri: Uri): Result {
    return Result.Builder(uri).build()
}