package com.lcy.base.picker.image.ui

import android.content.Context
import com.lcy.base.picker.image.support.entity.Result
import com.lcy.base.picker.image.support.entity.sources.Camera
import com.lcy.base.picker.image.support.entity.sources.Gallery
import io.reactivex.Observable

interface BasicImagePicker {

    @Gallery
    fun openGallery(context: Context): Observable<Result>

    @Camera
    fun openCamera(context: Context): Observable<Result>
}