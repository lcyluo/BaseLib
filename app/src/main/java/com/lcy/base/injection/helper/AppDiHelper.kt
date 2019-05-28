package com.lcy.base.injection.helper

import com.lcy.base.core.injection.component.AppComponent
import com.lcy.base.core.injection.module.ActivityModule
import com.lcy.base.core.injection.module.FragmentModule
import com.lcy.base.injection.component.AppActivityComponent
import com.lcy.base.injection.component.AppFragmentComponent
import com.lcy.base.injection.component.DaggerAppActivityComponent
import com.lcy.base.injection.component.DaggerAppFragmentComponent

class AppDiHelper {

    companion object {
        fun getActivityComponent(appComponent: AppComponent, activityModule: ActivityModule): AppActivityComponent {
            return DaggerAppActivityComponent.builder()
                .appComponent(appComponent)
                .activityModule(activityModule)
                .build()
        }

        fun getFragmentComponent(appComponent: AppComponent, fragmentModule: FragmentModule): AppFragmentComponent {
            return DaggerAppFragmentComponent.builder()
                .appComponent(appComponent)
                .fragmentModule(fragmentModule)
                .build()
        }
    }

}