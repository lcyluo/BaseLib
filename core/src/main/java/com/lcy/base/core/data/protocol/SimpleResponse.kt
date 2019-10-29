package com.lcy.base.core.data.protocol

import com.google.gson.annotations.SerializedName
import com.lcy.base.core.data.net.ApiConstant

open class SimpleResponse {

    /** 响应码 **/
    val code: Int = 0

    /** 消息内容 **/
    @SerializedName("msg", alternate = ["message"])
    val msg: String = ""

    /** 请求是否成功 **/
    val success: Boolean
        get() = code == ApiConstant.SUCCESS
}