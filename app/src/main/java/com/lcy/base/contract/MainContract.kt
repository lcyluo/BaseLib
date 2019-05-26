package com.lcy.base.contract

import com.lcy.base.core.presenter.IBasePresenter
import com.lcy.base.core.presenter.view.IBaseView

interface MainContract {

    interface View : IBaseView {

        fun setVersionInfo()

    }

    interface Presenter : IBasePresenter<View> {

        fun getVersionInfo()

    }
}