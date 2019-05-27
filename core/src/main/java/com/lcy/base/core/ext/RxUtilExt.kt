package com.lcy.base.core.ext

import com.lcy.base.core.rx.DataConvert
import com.lcy.base.core.data.protocol.DataResponse
import com.lcy.base.core.presenter.view.IBaseView
import com.lcy.base.core.rx.BooleanConvert
import com.lcy.base.core.rx.lifecycle.RxLifecycleUtil
import io.reactivex.Flowable
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

/**
 * 扩展数据转换
 */
fun <T> Flowable<DataResponse<T>>.convertData(): Flowable<T> {
    return this.flatMap(DataConvert())
}

/**
 * 扩展Boolean类型数据转换
 */
fun <T> Flowable<DataResponse<T>>.convertBoolean(): Flowable<Boolean> {
    return this.flatMap(BooleanConvert())
}