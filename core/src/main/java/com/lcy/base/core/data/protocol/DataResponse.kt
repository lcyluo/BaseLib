package com.lcy.base.core.data.protocol

open class DataResponse<out T>(val data: T) : SimpleResponse()