package com.lcy.base.data.net

import com.lcy.base.core.data.protocol.DataResponse
import com.lcy.base.data.api.ApiAppVersion
import com.lcy.base.data.protocol.AppVersionInfo
import io.reactivex.Flowable
import javax.inject.Inject

class AppRetrofitHelper @Inject constructor(private val mApiService: AppApis) {

    fun getVersionInfo(versionInfo: ApiAppVersion): Flowable<DataResponse<AppVersionInfo>> {
        return mApiService.getVersionInfo(versionInfo)
    }

}