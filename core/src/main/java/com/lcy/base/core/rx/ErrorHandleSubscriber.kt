package com.lcy.base.core.rx

import com.lcy.base.core.presenter.view.IBaseView
import io.reactivex.Observer
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable

abstract class ErrorHandleSubscriber<T> constructor(
    val view: IBaseView,
    private val errorMsg: String = "未知错误"
) : Observer<T> {

    override fun onSubscribe(@NonNull d: Disposable) {

    }

    override fun onComplete() {
        view.hideProgress()
    }

    override fun onError(t: Throwable) {
        errorHandle(t, view, errorMsg)
    }
}