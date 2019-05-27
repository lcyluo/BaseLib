package com.lcy.base.core.ui.activity

import com.lcy.base.core.presenter.IBasePresenter
import com.lcy.base.core.presenter.view.IBaseView
import javax.inject.Inject

abstract class BaseActivity<V : IBaseView, P : IBasePresenter<V>> : SimpleActivity(), IBaseView {

    @Inject
    protected lateinit var mPresenter: P

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    override fun initPresenter() {
        mPresenter.attachView(this as V)
    }

    override fun showError(code: Int, msg: String) {}

    override fun showProgress() {}

    override fun hideProgress() {}

}