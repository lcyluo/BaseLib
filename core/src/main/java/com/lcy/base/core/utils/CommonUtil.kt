package com.lcy.base.core.utils

import android.content.Context

object CommonUtil {

    /**
     * 将dip或dp值转换为px值
     */
    fun dip2px(context: Context, dipValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }
}