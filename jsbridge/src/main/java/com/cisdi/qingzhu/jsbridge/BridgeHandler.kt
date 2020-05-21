package com.cisdi.qingzhu.jsbridge

import android.content.Context

abstract class BridgeHandler {

    abstract fun handler(context: Context, data: String?, function: CallBackFunction?)

}