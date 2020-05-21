package com.cisdi.qingzhu.jsbridge

import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.util.ArrayMap
import com.google.gson.Gson
import java.net.URLEncoder
import java.util.*

class BridgeTiny(private val mWebView: IWebView) {

    private var mUniqueId: Long = 0
    private val mCallbacks: MutableMap<String, OnBridgeCallback> = ArrayMap()
    private val mMessageHandlers: MutableMap<String, BridgeHandler>? = HashMap()
    private var mMessages: MutableList<Any>? = ArrayList()

    init {
        mWebView.addJavascriptInterface(
            BridgeJavascriptInterface(mCallbacks, this, mWebView), BridgeUtil.INTERFACE_NAME
        )
        mMessageHandlers?.putAll(Bridge.instance.getMessageHandlers())
    }

    val messageHandlers: Map<String, BridgeHandler>?
        get() = mMessageHandlers

    fun webViewLoadJs(view: IWebView) {
        view.loadUrl(String.format(BridgeUtil.JAVASCRIPT_STR, BridgeUtil.WebviewJavascriptBridge))
        if (mMessages != null) {
            for (message in mMessages!!) {
                dispatchMessage(message)
            }
            mMessages = null
        }
    }

    /**
     * 分发message 必须在主线程才分发成功
     *
     * @param message Message
     */
    fun dispatchMessage(message: Any?) {
        var messageJson = Gson().toJson(message)
        //escape special characters for json string  为json字符串转义特殊字符
        messageJson = messageJson.replace("(\\\\)([^utrn])".toRegex(), "\\\\\\\\$1$2")
        messageJson = messageJson.replace("(?<=[^\\\\])(\")".toRegex(), "\\\\\"")
        messageJson = messageJson.replace("(?<=[^\\\\])(\')".toRegex(), "\\\\\'")
        messageJson = messageJson.replace("%7B".toRegex(), URLEncoder.encode("%7B", "UTF-8"))
        messageJson = messageJson.replace("%7D".toRegex(), URLEncoder.encode("%7D", "UTF-8"))
        messageJson = messageJson.replace("%22".toRegex(), URLEncoder.encode("%22", "UTF-8"))
        val javascriptCommand = String.format(BridgeUtil.JS_HANDLE_MESSAGE_FROM_JAVA, messageJson)
        // 必须要找主线程才会将数据传递出去 --- 划重点
        if (Thread.currentThread() === Looper.getMainLooper().thread) {
            if (javascriptCommand.length >= BridgeUtil.URL_MAX_CHARACTER_NUM) {
                mWebView.evaluateJavascript(javascriptCommand, null)
            } else {
                mWebView.loadUrl(javascriptCommand)
            }
        }
    }

    fun sendResponse(data: Any?, callbackId: String?) {
        if (data !is String || !callbackId.isNullOrEmpty()) {
            return
        }
        val response = JSResponse(callbackId, data)
        if (Thread.currentThread() === Looper.getMainLooper().thread) {
            dispatchMessage(response)
        } else {
            val mainHandler = Handler(Looper.getMainLooper())
            mainHandler.post { dispatchMessage(response) }
        }
    }

    /**
     * 保存message到消息队列
     *
     * @param handlerName      handlerName
     * @param data             data
     * @param responseCallback OnBridgeCallback
     */
    fun callHandler(handlerName: String?, data: Any?, responseCallback: OnBridgeCallback?) {
        if (data == null || data !is String) {
            return
        }
        val request = JSRequest(data = data, handlerName = handlerName)
        if (responseCallback != null) {
            val callbackId = String.format(
                BridgeUtil.CALLBACK_ID_FORMAT,
                (++mUniqueId).toString() + (BridgeUtil.UNDERLINE_STR + SystemClock.currentThreadTimeMillis())
            )
            mCallbacks[callbackId] = responseCallback
            request.callbackId = callbackId
        }
        queueMessage(request)
    }

    /**
     * list<message> != null 添加到消息集合否则分发消息
     *
     * @param message Message
    </message> */
    private fun queueMessage(message: Any) {
        if (mMessages != null) {
            mMessages?.add(message)
        } else {
            dispatchMessage(message)
        }
    }

    /**
     * free memory
     */
    fun freeMemory() {
        mCallbacks.clear()
        mMessageHandlers?.clear()
        mMessages?.clear()
    }

}