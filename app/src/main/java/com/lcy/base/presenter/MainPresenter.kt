package com.lcy.base.presenter

import com.lcy.base.contract.MainContract
import com.lcy.base.core.presenter.RxPresenter

class MainPresenter : RxPresenter<MainContract.View>(), MainContract.Presenter {

    override fun getVersionInfo() {
        mView?.setVersionInfo()
    }
}