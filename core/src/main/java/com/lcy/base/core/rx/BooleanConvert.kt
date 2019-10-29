package com.lcy.base.core.rx

import com.lcy.base.core.data.net.ApiException
import com.lcy.base.core.data.protocol.DataResponse
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.functions.Function

/**
 * 只关心成功或者失败的转换
 */
class BooleanConvert<T> : Function<DataResponse<T>, Flowable<Boolean>> {
    override fun apply(t: DataResponse<T>): Flowable<Boolean> {
        if (t.success) {
            return Flowable.error(ApiException(t.code, t.msg))
        }
        return Flowable.just(true)
    }
}

class BooleanConvertObservable<T> : Function<DataResponse<T>, Observable<Boolean>> {
    override fun apply(t: DataResponse<T>): Observable<Boolean> {
        if (t.success) {
            return Observable.error(ApiException(t.code, t.msg))
        }
        return Observable.just(true)
    }
}
