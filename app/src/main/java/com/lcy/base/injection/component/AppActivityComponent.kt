package com.lcy.base.injection.component

import com.lcy.base.core.injection.component.AppComponent
import com.lcy.base.core.injection.module.ActivityModule
import com.lcy.base.core.injection.scope.ActivityScope
import com.lcy.base.injection.module.AppDiModule
import com.lcy.base.ui.activity.MainActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [AppComponent::class], modules = [ActivityModule::class, AppDiModule::class])
interface AppActivityComponent {
    fun inject(mainActivity: MainActivity)
}