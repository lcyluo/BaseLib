package com.lcy.base.core.data.protocol

import com.google.gson.annotations.SerializedName

data class SimpleResponse(
    val code: Int,
    @SerializedName("msg", alternate = ["message"]) val msg: String
)