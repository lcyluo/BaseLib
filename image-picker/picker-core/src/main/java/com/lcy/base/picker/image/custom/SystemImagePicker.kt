package com.lcy.base.picker.image.custom

import android.content.Context
import com.lcy.base.picker.image.entity.sources.Camera
import com.lcy.base.picker.image.entity.sources.Gallery
import com.lcy.base.picker.image.entity.Result
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