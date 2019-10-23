package com.lcy.base.picker.image.custom

import android.content.Context
import com.lcy.base.picker.image.support.entity.sources.Camera
import com.lcy.base.picker.image.support.entity.sources.Gallery
import com.lcy.base.picker.image.support.entity.Result
import com.lcy.base.picker.image.ui.ICustomPickerConfiguration
import io.reactivex.Observable

interface SystemImagePicker {

    @Gallery
    fun openGallery(
        context: Context,
        config: ICustomPickerConfiguration?
    ): Observable<Result>

    @Camera
    fun openCamera(context: Context): Observable<Result>
}