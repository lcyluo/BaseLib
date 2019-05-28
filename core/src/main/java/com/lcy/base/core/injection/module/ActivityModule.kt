package com.lcy.base.core.injection.module

import android.support.v7.app.AppCompatActivity
import com.lcy.base.core.injection.scope.ActivityScope
import dagger.Module
import dagger.Provides

/**
 * Activity级别Module
 */
@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @ActivityScope
    @Provides
    fun provideActivity(): AppCompatActivity {
        return this.activity
    }
}
