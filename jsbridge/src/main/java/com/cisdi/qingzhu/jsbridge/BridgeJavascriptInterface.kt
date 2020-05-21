package com.cisdi.qingzhu.jsbridge

import android.webkit.JavascriptInterface

class BridgeJavascriptInterface(
    callbacks: MutableMap<String, OnBridgeCallback>,
    private val bridge: BridgeTiny,
    private val webView: IWebView
) :
    BaseJavascriptInterface(callbacks) {

    override fun send(data: String?): String? {
        return "it is default response"
    }

    @JavascriptInterface
    fun handler(handlerName: String?, data: String?, callbackId: String?) {
        if (handlerName.isNullOrEmpty() || callbackId.isNullOrEmpty()) {
            return
        }
        mainHandler.post {
            if (bridge.messageHandlers?.containsKey(handlerName) == true) {
                val bridgeHandler: BridgeHandler =
                    bridge.messageHandlers!![handlerName] ?: return@post
                bridgeHandler.handler(webView.context(), data, CallBack(callbackId, bridge))
            }
        }
    }

    class CallBack(private val callbackId: String, private val bridge: BridgeTiny) :
        CallBackFunction {
        override fun onCallBack(data: String?) {
            bridge.sendResponse(data, callbackId)
        }
    }

}