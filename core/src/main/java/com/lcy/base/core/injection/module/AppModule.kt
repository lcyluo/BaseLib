package com.lcy.base.core.injection.module

import android.content.Context
import com.lcy.base.core.common.CoreApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Application级别Module
 */
@Module
class AppModule(private val context: CoreApplication) {

    @Singleton
    @Provides
    fun provideContext(): Context {
        return this.context
    }
}
