package com.lcy.base.core.presenter

import com.lcy.base.core.presenter.view.IBaseView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.lang.NullPointerException
import java.lang.ref.WeakReference

open class RxPresenter<T : IBaseView> : IBasePresenter<T> {

    protected var mView: T? = null
        private set

    private val mCompositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

    override fun attachView(view: T) {
        mView = view
    }

    override fun detachView() {
        mView = null
        unSubscribe()
    }

    private fun unSubscribe() {
        mCompositeDisposable.dispose()
    }

    protected fun addSubscribe(subscription: Disposable) {
        mCompositeDisposable.add(subscription)
    }
}