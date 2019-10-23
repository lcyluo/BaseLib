package com.lcy.base.picker.image.support.entity

import android.support.annotation.IdRes
import android.support.v4.app.FragmentActivity
import com.lcy.base.picker.image.support.entity.sources.SourcesFrom
import com.lcy.base.picker.image.ui.ICustomPickerConfiguration
import com.lcy.base.picker.image.ui.ICustomPickerView
import kotlin.reflect.KClass

/**
 * Entity class for user's configuration.
 */
data class ConfigProvider(
    val componentClazz: KClass<*>,
    val asFragment: Boolean,
    val sourcesFrom: SourcesFrom,
    @param:IdRes val containerViewId: Int,
    /** runtime injection **/
    val fragmentActivity: FragmentActivity,
    val pickerView: ICustomPickerView,
    val config: ICustomPickerConfiguration?
)