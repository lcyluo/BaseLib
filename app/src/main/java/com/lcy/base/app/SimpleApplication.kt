package com.lcy.base.app

import android.util.Log
import com.lcy.base.core.common.BaseApplication

class SimpleApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        Log.d("", "")
    }

    companion object {

        private lateinit var instance: BaseApplication

        fun instance() = instance
    }
}