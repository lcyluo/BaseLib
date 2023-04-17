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
    return networkInfo?.type == ConnectivityManager.TYPE_WIFI
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


