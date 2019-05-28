package com.lcy.base.data.net

import com.lcy.base.core.data.protocol.DataResponse
import com.lcy.base.data.api.ApiAppVersion
import com.lcy.base.data.protocol.AppVersionInfo
import io.reactivex.Flowable
import retrofit2.http.Body
import retrofit2.http.POST

interface AppApis {
    /**
     * 获取App版本
     */
    @POST("api/client/version/check")
    fun getVersionInfo(@Body version: ApiAppVersion): Flowable<DataResponse<AppVersionInfo>>
}