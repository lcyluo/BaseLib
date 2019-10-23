package com.lcy.base.picker.image.core

import android.app.Activity
import com.lcy.base.picker.image.support.entity.ConfigProvider
import com.lcy.base.picker.image.ui.ActivityPickerViewController
import com.lcy.base.picker.image.ui.ICustomPickerConfiguration

class ImagePickerController(private val configProvider: ConfigProvider) {

    fun display() {
        configProvider.config?.onDisplay()

        if (!configProvider.asFragment)
            displayPickerViewAsActivity(configProvider.config)
        else
            displayPickerViewAsFragment(configProvider.config)
    }

    @Suppress("UNCHECKED_CAST")
    private fun displayPickerViewAsActivity(configuration: ICustomPickerConfiguration?) {
        val activityHolder = ActivityPickerViewController.instance
        activityHolder.setActivityClass(configProvider.componentClazz.java as Class<out Activity>)
        activityHolder.display(
            configProvider.fragmentActivity, configProvider.containerViewId, configuration
        )
    }

    private fun displayPickerViewAsFragment(configuration: ICustomPickerConfiguration?) {
        configProvider.pickerView.display(
            configProvider.fragmentActivity, configProvider.containerViewId, configuration
        )
    }
}
