package com.lcy.base.core.injection.module

import androidx.annotation.NonNull
import com.google.gson.Gson
import com.lcy.base.core.common.HttpConfig
import com.lcy.base.core.data.cookie.CookieJarImpl
import com.lcy.base.core.data.cookie.PersistentCookieStore
import com.lcy.base.core.utils.LenientGsonConverterFactory
import dagger.Module
import dagger.Provides
import me.jessyan.retrofiturlmanager.RetrofitUrlManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.net.Proxy
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class HttpModule(@NonNull private val config: HttpConfig) {

    @Singleton
    @Provides
    fun provideOkHttpBuilder(): OkHttpClient.Builder {
        return RetrofitUrlManager.getInstance().with(OkHttpClient.Builder())
    }

    @Singleton
    @Provides
    fun provideClient(builder: OkHttpClient.Builder): OkHttpClient {
        // 添加自定义拦截器
        if (config.interceptors != null) {
            config.interceptors.forEach { builder.addInterceptor(it) }
        }
        if (config.isShowLog) {
            val loggingInterceptor = if (config.logger == null) {
                HttpLoggingInterceptor()
            } else {
                HttpLoggingInterceptor(config.logger)
            }
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(loggingInterceptor)
        }
        // 设置超时
        builder.connectTimeout(config.connectTimeout, TimeUnit.SECONDS)
        builder.readTimeout(config.readTimeout, TimeUnit.SECONDS)
        builder.writeTimeout(config.writeTimeout, TimeUnit.SECONDS)
        // 错误重连
        builder.retryOnConnectionFailure(true)
        if (!config.isCanProxy) {
            //  防止代理
            builder.proxy(Proxy.NO_PROXY)
        }
        // Cookie自动管理
        if (config.isCookieJar) {
            if (config.cookieStore == null) {
                config.cookieStore = PersistentCookieStore()
            }
            builder.cookieJar(CookieJarImpl(config.cookieStore))
        }
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
    }

    @Singleton
    @Provides
    fun provideRetrofit(builder: Retrofit.Builder, client: OkHttpClient): Retrofit {
        return createRetrofit(builder, client)
    }

    private fun createRetrofit(builder: Retrofit.Builder, client: OkHttpClient): Retrofit {
        return builder
            .baseUrl(config.baseUrl)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(LenientGsonConverterFactory.create(Gson()))
            .build()
    }
}