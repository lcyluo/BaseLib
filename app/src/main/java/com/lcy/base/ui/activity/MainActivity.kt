package com.lcy.base.ui.activity

import com.lcy.base.R
import com.lcy.base.app.SimpleApplication
import com.lcy.base.contract.MainContract
import com.lcy.base.core.ext.onClick
import com.lcy.base.core.ui.activity.BaseActivity
import com.lcy.base.data.protocol.AppVersionInfo
import com.lcy.base.injection.helper.AppDiHelper
import com.lcy.base.picker.image.core.RxImagePicker
import com.lcy.base.picker.image.custom.SystemImagePicker
import com.lcy.base.picker.image.ui.gallery.DefaultSystemGalleryConfig
import com.lcy.base.presenter.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*

import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.toast

class MainActivity : BaseActivity<MainContract.View, MainPresenter>(), MainContract.View {

    private lateinit var defaultImagePicker: SystemImagePicker

    override fun getLayout(): Int = R.layout.activity_main

    override fun initInject() {
        AppDiHelper.getActivityComponent(getAppComponent(), getActivityModule()).inject(this)
    }

    override fun initEventAndData() {
        mPresenter.getVersionInfo()
        helloWorld.text = "你好，世界！"

        defaultImagePicker = RxImagePicker.create(SystemImagePicker::class.java)
    }

    override fun initListeners() {
        mFabCamera.onClick {
            defaultImagePicker
                .openCamera(
                    this
                )
                .subscribe { result ->
                    //  LogUtil.d(result.uri.toString())
                }
        }

        mFabGallery.onClick {
            defaultImagePicker
                .openGallery(
                    this,
                    DefaultSystemGalleryConfig.defaultInstance()
                )
                .subscribe { result ->
                    //  LogUtil.d(result.uri.toString())
                }
        }
    }

    override fun setVersionInfo(versionInfo: AppVersionInfo) {

    }

    override fun showError(code: Int, msg: String) {
        toast(msg)
    }
}
