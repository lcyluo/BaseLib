package com.lcy.base.core.ui.activity

import com.lcy.base.core.common.CoreApplication
import com.lcy.base.core.injection.component.AppComponent
import com.lcy.base.core.injection.module.ActivityModule
import com.lcy.base.core.presenter.IBasePresenter
import com.lcy.base.core.presenter.view.IBaseView
import javax.inject.Inject

abstract class BaseActivity<V : IBaseView, P : IBasePresenter<V>> : SimpleActivity(), IBaseView {

    @Inject
    protected lateinit var mPresenter: P

    protected fun getAppComponent(): AppComponent {
        return CoreApplication.instance().appComponent
    }

    protected fun getActivityModule(): ActivityModule {
        return ActivityModule(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    /**
     * 采用final关键字,关闭了 override 的遗传性,子类无法再覆写
     */
    @Suppress("UNCHECKED_CAST")
    final override fun initPresenter() {
        mPresenter.attachView(this as V)
    }

    override fun showError(code: Int, msg: String) {}

    override fun showProgress() {}

    override fun hideProgress() {}

}