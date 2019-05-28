package com.lcy.base.injection.module

import com.lcy.base.data.net.AppApis
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class AppDiModule {

    @Provides
    fun provideLoginService(retrofit: Retrofit): AppApis {
        return retrofit.create(AppApis::class.java)
    }

}