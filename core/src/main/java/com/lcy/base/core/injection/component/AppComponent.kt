package com.lcy.base.core.injection.component

import android.content.Context
import com.lcy.base.core.injection.module.AppModule
import com.lcy.base.core.injection.module.HttpModule
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Application级别Component
 */
@Singleton
@Component(modules = [AppModule::class, HttpModule::class])
interface AppComponent {

    fun context(): Context

    fun provideRetrofit(): Retrofit
}
