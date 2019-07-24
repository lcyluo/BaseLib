package com.lcy.base.picker.image.entity.sources

import android.support.annotation.IdRes
import com.lcy.base.picker.image.ui.gallery.BasicGalleryFragment
import kotlin.reflect.KClass

/**
 * This annotation will be marked open Gallery，it will conflict with [Camera]
 */
@Retention
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
annotation class Gallery(

    /**
     * the UI component that will be displayed.
     */
    val componentClazz: KClass<*> = BasicGalleryFragment::class,

    /**
     * the UI component type, as [Fragment] or [Activity].
     *
     * If you use custom UI component by [Activity], then set this value false.
     */
    val openAsFragment: Boolean = true,

    /**
     * the [Fragment] container
     */
    @IdRes val containerViewId: Int = 0
)
