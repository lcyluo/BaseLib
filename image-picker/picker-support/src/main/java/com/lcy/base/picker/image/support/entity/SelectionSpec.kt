package com.lcy.base.picker.image.support.entity

import android.content.pm.ActivityInfo

import com.lcy.base.picker.image.support.filter.Filter

import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
import android.support.annotation.StyleRes
import com.lcy.base.picker.image.support.R
import com.lcy.base.picker.image.support.engine.ImageEngine
import com.lcy.base.picker.image.ui.ICustomPickerConfiguration
import com.qingmei2.rximagepicker_extension.MimeType

class SelectionSpec private constructor() : ICustomPickerConfiguration {

    var mimeTypeSet: Set<MimeType> = setOf()
    var mediaTypeExclusive: Boolean = false
    var showSingleMediaType: Boolean = false
    @StyleRes
    var themeId: Int = 0
    var orientation: Int = 0
    var countable: Boolean = false
    var maxSelectable: Int = 0
    var maxImageSelectable: Int = 0
    var maxVideoSelectable: Int = 0
    var filters: ArrayList<Filter>? = null
    var capture: Boolean = false
    var captureStrategy: CaptureStrategy? = null
    var spanCount: Int = 0
    var gridExpectedSize: Int = 0
    var thumbnailScale: Float = 0.toFloat()
    lateinit var imageEngine: ImageEngine

    init {
        reset()
    }

    private fun reset() {
        if (InstanceHolder.imageEngineHolder == null) {
            throw NullPointerException(
                "the default imageEngine can't be null, please init it by the SelectionSpec.getNewCleanInstance(imageEngine)"
            )
        }
        this.imageEngine = InstanceHolder.imageEngineHolder!!
        mimeTypeSet = MimeType.ofImage()
        mediaTypeExclusive = true
        showSingleMediaType = false
        themeId = R.style.Theme_AppCompat_Light
        orientation = SCREEN_ORIENTATION_PORTRAIT
        countable = false
        maxSelectable = 1
        maxImageSelectable = 0
        maxVideoSelectable = 0
        filters = null
        capture = false
        captureStrategy = null
        spanCount = 3
        gridExpectedSize = 0
        thumbnailScale = 0.5f
    }

    fun singleSelectionModeEnabled(): Boolean {
        return !countable && (maxSelectable == 1 || maxImageSelectable == 1 && maxVideoSelectable == 1)
    }

    fun needOrientationRestriction(): Boolean {
        return orientation != ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }

    fun onlyShowImages(): Boolean {
        return showSingleMediaType && MimeType.ofImage().containsAll(mimeTypeSet)
    }

    fun onlyShowVideos(): Boolean {
        return showSingleMediaType && MimeType.ofVideo().containsAll(mimeTypeSet)
    }

    override fun onDisplay() {
        instance = this
    }

    /**
     * Try to reset [SelectionSpec] every time when the picture selection operation ends.
     */
    override fun onFinished() {
        instance = SelectionSpec()
    }

    private object InstanceHolder {

        /**
         * The SelectionSpec being used.
         */
        var INSTANCE: SelectionSpec? = null

        /**
         * 默认的imageEngineHolder，开发者必须通过调用[SelectionSpec.getNewCleanInstance]
         * 初始化它。
         * <br></br>
         * 每次重置[SelectionSpec]时，都会将默认的图片加载引擎设置为imageEngineHolder的值，
         * 开发者也可以通过对[.setDefaultImageEngine]进行设置。
         * <br></br>
         * 如果只想单次图片选择中修改配置[ImageEngine],可以通过SelectionSpec.getInstance().imageEngine
         * 进行直接赋值。
         */
        var imageEngineHolder: ImageEngine? = null
    }

    companion object {

        var instance: SelectionSpec
            get() = InstanceHolder.INSTANCE!!
            set(selectionSpec) {
                InstanceHolder.INSTANCE = selectionSpec
            }

        /**
         * You should init the [SelectionSpec] by this method, it will instance default
         * [ImageEngine] for loading image.
         *
         * @return SelectionSpec
         */
        fun getNewCleanInstance(imageEngine: ImageEngine?): SelectionSpec {
            if (imageEngine == null) {
                throw IllegalArgumentException("the param imageEngine can't be null.")
            }
            setDefaultImageEngine(imageEngine)
            return SelectionSpec()
        }

        fun setDefaultImageEngine(imageEngine: ImageEngine) {
            InstanceHolder.imageEngineHolder = imageEngine
        }
    }
}
