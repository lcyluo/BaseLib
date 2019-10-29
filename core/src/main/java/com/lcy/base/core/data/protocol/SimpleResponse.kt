package com.lcy.base.core.data.protocol

import com.google.gson.annotations.SerializedName
import com.lcy.base.core.data.net.ApiConstant

open class SimpleResponse(
    val code: Int,
    @SerializedName("msg", alternate = ["message"])
    val msg: String
) {
    /** 请求是否成功 **/
    open val success: Boolean
        get() = code == ApiConstant.SUCCESS
}