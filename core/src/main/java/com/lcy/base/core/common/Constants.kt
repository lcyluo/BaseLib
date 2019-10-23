package com.lcy.base.core.common

import com.lcy.base.core.utils.DeviceUtil

object Constants {
    /**
     * 接口请求地址
     */
    val APP_API_HOST: String = DeviceUtil.getMetaApiHost()
    /**
     * 接口请求超时
     */
    const val APP_API_TIMEOUT = 20L

}