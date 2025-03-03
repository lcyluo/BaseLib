package com.lcy.base.injection

import android.util.Log
import com.lcy.base.core.common.HttpConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HttpConfigModule {

    @Provides
    @Singleton
    fun provideHttpConfig(): HttpConfig {
        return HttpConfig.Builder()
            .baseUrl("http://m.qingzhuyun.com")
            .logger { Log.e("QZ_BUILDING", it) }
            .canProxy(false)
            .build();
    }

}