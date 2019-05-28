package com.lcy.base.core.injection.module

import android.content.Context
import com.lcy.base.core.common.BaseApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Application级别Module
 */
@Module
class AppModule(private val context: BaseApplication) {

    @Singleton
    @Provides
    fun provideContext(): Context {
        return this.context
    }
}
