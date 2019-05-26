package com.lcy.base.core.presenter.view

interface IBaseView {

    fun showError(code: Int, msg: String)

    fun showProgress()

    fun hideProgress()

}