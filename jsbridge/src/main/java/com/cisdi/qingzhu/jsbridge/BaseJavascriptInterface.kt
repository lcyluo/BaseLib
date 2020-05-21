package com.cisdi.qingzhu.jsbridge

import android.os.Handler
import android.os.Looper
import android.webkit.JavascriptInterface

abstract class BaseJavascriptInterface(
    private var mCallbacks: MutableMap<String, OnBridgeCallback>?
) {
    var mainHandler = Handler(Looper.getMainLooper())

    @JavascriptInterface
    open fun send(data: String?, callbackId: String?): String? {
        return send(data)
    }

    @JavascriptInterface
    open fun response(data: String?, responseId: String?) {
        if (!responseId.isNullOrEmpty()) {
            mainHandler.post {
                val function: OnBridgeCallback? = mCallbacks?.remove(responseId)
                function?.onCallBack(data)
            }
        }
    }

    abstract fun send(data: String?): String?
}