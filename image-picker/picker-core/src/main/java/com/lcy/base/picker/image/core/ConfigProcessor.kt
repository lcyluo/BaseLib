package com.lcy.base.picker.image.core

import com.lcy.base.picker.image.support.entity.ConfigProvider
import com.lcy.base.picker.image.scheduler.IRxImagePickerSchedulers
import com.lcy.base.picker.image.support.entity.sources.SourcesFrom
import com.lcy.base.picker.image.ui.ActivityPickerViewController
import io.reactivex.Observable

/**
 * [ConfigProcessor] is the class that processing reactive data stream.
 */
class ConfigProcessor(private val schedulers: IRxImagePickerSchedulers) {

    fun process(configProvider: ConfigProvider): Observable<*> {
        return Observable.just(0)
                .flatMap {
                    if (!configProvider.asFragment) {
                        return@flatMap ActivityPickerViewController.instance.pickImage()
                    }
                    when (configProvider.sourcesFrom) {
                        SourcesFrom.GALLERY,
                        SourcesFrom.CAMERA -> configProvider.pickerView.pickImage()
                    }
                }
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
    }
}