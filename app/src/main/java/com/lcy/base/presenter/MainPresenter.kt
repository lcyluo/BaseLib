package com.lcy.base.presenter

import com.lcy.base.contract.MainContract
import com.lcy.base.core.ext.convertData
import com.lcy.base.core.ext.rxSchedulerHelper
import com.lcy.base.core.presenter.RxPresenter
import com.lcy.base.core.rx.CommonSubscriber
import com.lcy.base.data.api.ApiAppVersion
import com.lcy.base.data.net.AppRetrofitHelper
import com.lcy.base.data.protocol.AppVersionInfo
import javax.inject.Inject

class MainPresenter @Inject constructor(private val mRetrofitHelper: AppRetrofitHelper) :
    RxPresenter<MainContract.View>(), MainContract.Presenter {

    override fun getVersionInfo() {
        addSubscribe(
            mRetrofitHelper.getVersionInfo2(ApiAppVersion("1.1.0"))
                .rxSchedulerHelper(mView!!)
                .convertData()
                .subscribeWith(object : CommonSubscriber<AppVersionInfo>(view = mView!!) {
                    override fun onNext(t: AppVersionInfo) {
                        mView!!.setVersionInfo(t)
                    }
                })
        )

//        mRetrofitHelper.getVersionInfo(ApiAppVersion("1.1.0"))
//            .rxSchedulerHelper(mView!!)
//            .convertData()
//            .subscribe(object : ErrorHandleSubscriber<AppVersionInfo>(view = mView!!) {
//                override fun onNext(t: AppVersionInfo) {
//                    mView!!.setVersionInfo(t)
//                }
//            })
    }
}