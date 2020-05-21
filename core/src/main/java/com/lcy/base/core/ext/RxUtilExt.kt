package com.lcy.base.core.ext

import com.lcy.base.core.data.protocol.DataResponse
import com.lcy.base.core.presenter.view.IBaseView
import com.lcy.base.core.rx.*
import com.lcy.base.core.rx.lifecycle.RxLifecycleUtil
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 扩展线程切换
 */
fun <T> Flowable<T>.rxSchedulerHelper(mView: IBaseView): Flowable<T> {
    return this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .compose(RxLifecycleUtil.bindToLifecycle(mView))
}

fun <T> Observable<T>.rxSchedulerHelper(mView: IBaseView): Observable<T> {
    return this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .compose(RxLifecycleUtil.bindToLifecycle(mView))
}

/**
 * 扩展数据转换
 */
fun <T> Flowable<DataResponse<T>>.convertData(): Flowable<T> {
    return this.flatMap<T>(DataConvert())
}

fun <T> Observable<DataResponse<T>>.convertData(): Observable<T> {
    return this.flatMap(DataConvertObservable())
}

/**
 * 扩展Boolean类型数据转换
 */
fun <T> Flowable<DataResponse<T>>.convertBoolean(): Flowable<Boolean> {
    return this.flatMap(BooleanConvert())
}

fun <T> Observable<DataResponse<T>>.convertBoolean(): Observable<Boolean> {
    return this.flatMap(BooleanConvertObservable())
}