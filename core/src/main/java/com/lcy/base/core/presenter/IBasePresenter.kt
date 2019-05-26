package com.lcy.base.core.presenter

import com.lcy.base.core.presenter.view.IBaseView

interface IBasePresenter<T : IBaseView> {

    fun attachView(view: T)

    fun detachView()

}