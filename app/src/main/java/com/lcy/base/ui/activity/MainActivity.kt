package com.lcy.base.ui.activity

import com.lcy.base.R
import com.lcy.base.contract.MainContract
import com.lcy.base.core.ui.activity.BaseActivity
import com.lcy.base.data.protocol.AppVersionInfo
import com.lcy.base.presenter.MainPresenter
import dagger.hilt.android.AndroidEntryPoint
import org.jetbrains.anko.toast


@AndroidEntryPoint
class MainActivity : BaseActivity<MainPresenter>(), MainContract.View {

    override fun getLayout(): Int = R.layout.activity_main

    override fun initEventAndData() {
        mPresenter.getVersionInfo()
    }

    override fun initListeners() {

    }

    override fun setVersionInfo(versionInfo: AppVersionInfo) {

    }

    override fun showError(code: Int, msg: String) {
        toast(msg)
    }

}
