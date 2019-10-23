package com.lcy.base.core.rx

import com.lcy.base.core.data.net.ApiConstant
import com.lcy.base.core.data.net.ApiException
import com.lcy.base.core.data.protocol.DataResponse
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.functions.Function

/**
 * 通用数据类型转换封装
 */
class DataConvert<T> : Function<DataResponse<T>, Flowable<T>> {
    override fun apply(t: DataResponse<T>): Flowable<T> {
        if (t.code != ApiConstant.SUCCESS) {
            return Flowable.error(ApiException(t.code, t.msg))
        }
        return Flowable.just(t.data)
    }
}

class DataConvertObservable<T> : Function<DataResponse<T>, Observable<T>> {
    override fun apply(t: DataResponse<T>): Observable<T> {
        if (t.code != ApiConstant.SUCCESS) {
            return Observable.error(ApiException(t.code, t.msg))
        }
        return Observable.just(t.data)
    }
}
