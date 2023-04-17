package com.lcy.base.app

import com.lcy.base.core.common.CoreApplication
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SimpleApplication : CoreApplication() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        initAppInjection()
    }

    companion object {

        private lateinit var instance: CoreApplication

        fun instance() = instance
    }

    override fun initAppInjection() {
//        val httpConfig = HttpConfig.Builder()
//            .baseUrl("http://m.qingzhuyun.com")
//            .showLog(BuildConfig.DEBUG)
//            .logger { Log.e("QZ_BUILDING", it) }
//            .canProxy(false)
//            .build()
//
//        appComponent = DaggerAppComponent.builder()
//            .httpModule(HttpModule(httpConfig))
//            .build()
    }
}