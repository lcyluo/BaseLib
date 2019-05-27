package com.lcy.base.core.rx

import com.lcy.base.core.data.net.ApiConstant
import com.lcy.base.core.data.net.ApiException
import com.lcy.base.core.data.protocol.DataResponse
import io.reactivex.Flowable
import io.reactivex.functions.Function

/**
 * 只关心成功或者失败的转换
 */
class BooleanConvert<T> : Function<DataResponse<T>, Flowable<Boolean>> {
    override fun apply(t: DataResponse<T>): Flowable<Boolean> {
        if (t.code != ApiConstant.SUCCESS) {
            return Flowable.error(ApiException(t.code, t.msg))
        }
        return Flowable.just(true)
    }
}
