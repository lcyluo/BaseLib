package com.lcy.base.ui.activity

import com.lcy.base.R
import com.lcy.base.contract.MainContract
import com.lcy.base.core.ui.activity.BaseActivity
import com.lcy.base.presenter.MainPresenter

import kotlinx.android.synthetic.main.content_main.*

class MainActivity : BaseActivity<MainContract.View, MainPresenter>(), MainContract.View {

    override fun getLayout(): Int = R.layout.activity_main

    override fun initInject() {

    }

    override fun initEventAndData() {
        mPresenter.getVersionInfo()
        helloWorld.text = "你好，世界！"
    }

    override fun initListeners() {

    }

    override fun setVersionInfo() {

    }
}
