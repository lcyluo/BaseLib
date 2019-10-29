package com.lcy.base.core.data.protocol

open class DataResponse<out T>(
    code: Int,
    msg: String,
    val data: T
) : SimpleResponse(code, msg)