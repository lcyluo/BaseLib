package com.lcy.base.picker.image.support.utils

import android.content.Context
import android.content.pm.PackageManager

object MediaStoreCompat {

    fun hasCameraFeature(context: Context): Boolean {
        val pm = context.applicationContext.packageManager
        return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)
    }
}
