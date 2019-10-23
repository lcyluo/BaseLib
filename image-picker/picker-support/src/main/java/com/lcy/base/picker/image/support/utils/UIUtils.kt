package com.lcy.base.picker.image.support.utils

import android.content.Context
import kotlin.math.roundToInt

object UIUtils {

    fun spanCount(context: Context, gridExpectedSize: Int): Int {
        val screenWidth = context.resources.displayMetrics.widthPixels
        val expected = screenWidth.toFloat() / gridExpectedSize.toFloat()
        var spanCount = expected.roundToInt()
        if (spanCount == 0) {
            spanCount = 1
        }
        return spanCount
    }
}
