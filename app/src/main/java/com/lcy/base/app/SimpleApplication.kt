package com.lcy.base.app

import android.util.Log
import com.lcy.base.BuildConfig
import com.lcy.base.core.common.Constants
import com.lcy.base.core.common.CoreApplication
import com.lcy.base.core.common.HttpConfig
import com.lcy.base.core.data.net.ApiConstant
import com.lcy.base.core.injection.component.DaggerAppComponent
import com.lcy.base.core.injection.module.AppModule
import com.lcy.base.core.injection.module.HttpModule
import okhttp3.logging.HttpLoggingInterceptor

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
        val httpConfig = HttpConfig.Builder()
            .baseUrl(Constants.APP_API_HOST)
            .showLog(BuildConfig.DEBUG)
            .logger { Log.e("QZ_BUILDING", it) }
            .canProxy(false)
            .build()

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(instance))
            .httpModule(HttpModule(httpConfig))
            .build()
    }
}