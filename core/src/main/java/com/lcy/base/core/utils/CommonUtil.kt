package com.lcy.base.core.utils

import android.content.Context
import android.widget.TextView
import com.lcy.base.core.ext.*

object CommonUtil {

    /**
     * 屏幕宽度
     */
    @JvmStatic
    fun displayWidth(context: Context): Int {
        return context.displayWidth()
    }

    /**
     * 屏幕高度
     */
    @JvmStatic
    fun displayHeight(context: Context): Int {
        return context.displayHeight()
    }

    /**
     * 将dp值转换为px值
     */
    @JvmStatic
    fun dp2px(context: Context, dpValue: Float): Int {
        return context.dp2px(dpValue)
    }

    /**
     * 将px值转换为dp值
     */
    @JvmStatic
    fun px2dp(context: Context, pxValue: Float): Int {
        return context.px2dp(pxValue)
    }

    /**
     * 将sp值转换为px值
     */
    @JvmStatic
    fun sp2px(context: Context, spValue: Float): Int {
        return context.sp2px(spValue)
    }

    /**
     * 将px值转换为sp值
     */
    @JvmStatic
    fun px2sp(context: Context, pxValue: Float): Int {
        return context.px2sp(pxValue)
    }

    /**
     * 给文本设置字体颜色
     */
    @JvmStatic
    fun setTextColor(textView: TextView, resId: Int) {
        return textView.setColor(resId)
    }

}