package com.lcy.base.core.ext

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.view.View
import android.view.ViewGroup

/**
 * 扩展点击事件
 */
fun View.onClick(listener: View.OnClickListener): View {
    setOnClickListener(listener)
    return this
}

/**
 * 扩展点击事件，参数为方法
 */
fun View.onClick(method: () -> Unit): View {
    setOnClickListener { method() }
    return this
}

/**
 * 将View转为Bitmap
 */
fun View.view2Bitmap(): Bitmap {
    val ret = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(ret)
    val bgDrawable = this.background
    bgDrawable?.draw(canvas) ?: canvas.drawColor(Color.WHITE)
    this.draw(canvas)
    return ret
}


var View.bottomMargin: Int
    get():Int {
        return (layoutParams as ViewGroup.MarginLayoutParams).bottomMargin
    }
    set(value) {
        (layoutParams as ViewGroup.MarginLayoutParams).bottomMargin = value
    }


var View.topMargin: Int
    get():Int {
        return (layoutParams as ViewGroup.MarginLayoutParams).topMargin
    }
    set(value) {
        (layoutParams as ViewGroup.MarginLayoutParams).topMargin = value
    }


var View.rightMargin: Int
    get():Int {
        return (layoutParams as ViewGroup.MarginLayoutParams).rightMargin
    }
    set(value) {
        (layoutParams as ViewGroup.MarginLayoutParams).rightMargin = value
    }

var View.leftMargin: Int
    get():Int {
        return (layoutParams as ViewGroup.MarginLayoutParams).leftMargin
    }
    set(value) {
        (layoutParams as ViewGroup.MarginLayoutParams).leftMargin = value
    }

/**
 * 设置边距(单位dp)
 * @param left 左边距
 * @param top 上边距
 * @param right 右边距
 * @param bottom 下边距
 */
fun View.setMargins(left: Float, top: Float, right: Float, bottom: Float) {
    setMarginPixels(
        this.context.dp2px(left),
        this.context.dp2px(top),
        this.context.dp2px(right),
        this.context.dp2px(bottom)
    )
}

/**
 * 设置边距(单位px)
 * @param leftPx 左边距
 * @param topPx 上边距
 * @param rightPx 右边距
 * @param bottomPx 下边距
 */
fun View.setMarginPixels(leftPx: Int, topPx: Int, rightPx: Int, bottomPx: Int) {
    (layoutParams as ViewGroup.MarginLayoutParams).setMargins(leftPx, topPx, rightPx, bottomPx)
}