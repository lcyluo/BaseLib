package com.lcy.base.core.data.protocol

import com.google.gson.annotations.SerializedName

data class DataResponse<out T>(
    val code: Int,
    @SerializedName("msg", alternate = ["message"]) val msg: String,
    val data: T
)