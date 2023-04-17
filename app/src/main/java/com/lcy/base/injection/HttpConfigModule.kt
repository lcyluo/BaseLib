package com.lcy.base.injection

import android.util.Log
import com.lcy.base.BuildConfig
import com.lcy.base.core.common.HttpConfig
import com.lcy.base.data.net.AppApis
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HttpConfigModule {

    @Provides
    @Singleton
    fun provideHttpConfig(): HttpConfig {
        return HttpConfig.Builder()
            .baseUrl("http://m.qingzhuyun.com")
            .showLog(BuildConfig.DEBUG)
            .logger { Log.e("QZ_BUILDING", it) }
            .canProxy(false)
            .build();
    }

}