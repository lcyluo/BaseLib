package com.lcy.base.core.presenter

import com.lcy.base.core.presenter.view.IBaseView

interface IBasePresenter<in V : IBaseView> {

    fun attachView(view: V)

    fun detachView()

}