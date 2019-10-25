package com.lcy.base.core.injection.module

import android.support.annotation.NonNull
import android.util.Log
import com.lcy.base.core.BuildConfig
import com.lcy.base.core.common.Constants
import com.lcy.base.core.common.HttpConfig
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.Proxy
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class HttpModule(@NonNull private val config: HttpConfig) {

    @Singleton
    @Provides
    fun provideOkHttpBuilder(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
    }

    @Singleton
    @Provides
    fun provideClient(builder: OkHttpClient.Builder): OkHttpClient {
        if (config.isShowLog) {
            val loggingInterceptor = if (config.logger == null) {
                HttpLoggingInterceptor()
            } else {
                HttpLoggingInterceptor(config.logger)
            }
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(loggingInterceptor)
        }
        //  设置超时
        builder.connectTimeout(config.connectTimeout, TimeUnit.SECONDS)
        builder.readTimeout(config.readTimeout, TimeUnit.SECONDS)
        builder.writeTimeout(config.writeTimeout, TimeUnit.SECONDS)
        //  错误重连
        builder.retryOnConnectionFailure(true)
        if (!config.isCanProxy) {
            //  防止代理
            builder.proxy(Proxy.NO_PROXY)
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
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}