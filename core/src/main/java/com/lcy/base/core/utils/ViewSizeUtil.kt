package com.lcy.base.core.utils

import android.graphics.Rect
import android.view.View

object ViewSizeUtil {

    fun getVisiblePercent(pView: View?): Int {
        if (pView != null && pView.isShown) {
            val displayMetrics = pView.context.resources.displayMetrics
            val displayWidth = displayMetrics.widthPixels
            val rect = Rect()
            pView.getGlobalVisibleRect(rect)
            return if (rect.top >= 0 && rect.left < displayWidth) {
                val areaVisible = rect.width() * rect.height().toDouble()
                val areaTotal = pView.width * pView.height.toDouble()
                (areaVisible / areaTotal * 100).toInt()
            } else {
                -1
            }
        }
        return -1
    }
}