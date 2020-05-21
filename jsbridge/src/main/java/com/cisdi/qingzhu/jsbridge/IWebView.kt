package com.cisdi.qingzhu.jsbridge

import android.content.Context

interface IWebView {

    fun loadUrl(url: String?)

    fun addJavascriptInterface(obj: Any?, interfaceName: String?)

    fun evaluateJavascript(var1: String?, obj: Any?)

    fun callHandler(
        handlerName: String?,
        data: Any?,
        responseCallback: OnBridgeCallback?
    )

    fun context(): Context
}