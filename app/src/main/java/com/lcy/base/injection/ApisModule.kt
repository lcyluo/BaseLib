package com.lcy.base.injection

import com.lcy.base.data.net.AppApis
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApisModule {

    @Provides
    @Singleton
    fun provideLoginService(retrofit: Retrofit): AppApis {
        return retrofit.create(AppApis::class.java)
    }

}