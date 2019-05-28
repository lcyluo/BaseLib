package com.lcy.base.injection.component

import com.lcy.base.core.injection.component.AppComponent
import com.lcy.base.core.injection.module.FragmentModule
import com.lcy.base.core.injection.scope.FragmentScope
import com.lcy.base.injection.module.AppDiModule
import dagger.Component

@FragmentScope
@Component(dependencies = [AppComponent::class], modules = [FragmentModule::class, AppDiModule::class])
interface AppFragmentComponent {

}