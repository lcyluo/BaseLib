@file:Suppress("DEPRECATION")

package com.lcy.base.core.ext

import android.content.Context
import android.content.Context.*
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import android.os.Build
import android.telephony.TelephonyManager
import android.view.View
import android.view.ViewGroup

fun View.dip2px(dipValue: Float): Int {
    val scale = this.resources.displayMetrics.density
    return (dipValue * scale + 0.5f).toInt()
}

fun View.px2dip(pxValue: Float): Int {
    val scale = this.resources.displayMetrics.density
    return (pxValue / scale + 0.5f).toInt()
}

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
 * 打开网络设置
 */
fun Context.openWirelessSettins() {
    startActivity(
        Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    )
}

/**
 * 网络是否连接
 */
fun Context.isConnected(): Boolean {
    val info = this.getActiveNetworkInfo() ?: return false
    return info.isConnected
}

/**
 * 手机网络是否可用
 */
fun Context.isMobileDataEnabled(): Boolean {
    try {
        val service = this.getSystemService(TELEPHONY_SERVICE) ?: return false
        val tm = service as TelephonyManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return tm.isDataEnabled
        }
        val getMobileDataEnabledMethod = tm.javaClass.getDeclaredMethod("getDataEnabled")
        return getMobileDataEnabledMethod.invoke(tm) as Boolean
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return false
}

/**
 * 手机网络是否可用
 */
fun Context.isMobileDataConnected(): Boolean {
    val info = this.getActiveNetworkInfo() ?: return false
    return (info.isAvailable && info.type == ConnectivityManager.TYPE_MOBILE)
}

/**
 * WIFI是否可用
 */
fun Context.isWifiEnabled(): Boolean {
    val service = this.getSystemService(WIFI_SERVICE) ?: return false
    val manager = service as WifiManager
    return manager.isWifiEnabled
}

/**
 * WIFI是否已连接
 */
fun Context.isWifiConnected(): Boolean {
    val service = this.getSystemService(TELEPHONY_SERVICE) ?: return false
    val cm = service as ConnectivityManager
    val networkInfo = cm.activeNetworkInfo
    return networkInfo.type == ConnectivityManager.TYPE_WIFI
}

/**
 * 网络连接名称
 */
fun Context.networkOperatorName(): String {
    val service = this.getSystemService(TELEPHONY_SERVICE) ?: return ""
    val telephonyManager = service as TelephonyManager
    return telephonyManager.networkOperatorName
}

/**
 * 获取网络状态
 */
private fun Context.getActiveNetworkInfo(): NetworkInfo? {
    val service = this.getSystemService(CONNECTIVITY_SERVICE) ?: return null
    val manager = service as ConnectivityManager
    return manager.activeNetworkInfo
}


