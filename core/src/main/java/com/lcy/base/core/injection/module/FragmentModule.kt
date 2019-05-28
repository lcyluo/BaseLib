package com.lcy.base.core.injection.module

import android.support.v4.app.Fragment
import com.lcy.base.core.injection.scope.FragmentScope
import dagger.Module
import dagger.Provides

/**
 * Activity级别Module
 */
@Module
class FragmentModule(private val fragment: Fragment) {

    @FragmentScope
    @Provides
    fun provideFragment(): Fragment {
        return this.fragment
    }
}
