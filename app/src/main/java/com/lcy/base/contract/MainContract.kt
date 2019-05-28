package com.lcy.base.contract

import com.lcy.base.core.presenter.IBasePresenter
import com.lcy.base.core.presenter.view.IBaseView
import com.lcy.base.data.protocol.AppVersionInfo

interface MainContract {

    interface View : IBaseView {

        fun setVersionInfo(versionInfo: AppVersionInfo)

    }

    interface Presenter : IBasePresenter<View> {

        fun getVersionInfo()

    }
}