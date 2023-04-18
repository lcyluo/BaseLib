package com.lcy.base.app

import com.lcy.base.core.common.CoreApplication
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SimpleApplication : CoreApplication() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {

        private lateinit var instance: CoreApplication

        fun instance() = instance
    }
}