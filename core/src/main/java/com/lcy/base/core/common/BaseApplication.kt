package com.lcy.base.core.common

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Build
import android.support.multidex.MultiDex
import com.lcy.base.core.BuildConfig
import com.lcy.base.core.injection.component.AppComponent
import com.lcy.base.core.injection.component.DaggerAppComponent
import com.lcy.base.core.injection.module.AppModule
import com.lcy.base.core.injection.module.HttpModule
import me.yokeyword.fragmentation.Fragmentation
import java.lang.ref.WeakReference
import java.util.*

/**
 * Application 基类
 *
 * @author lcy
 */
open class BaseApplication : Application() {

    /** 存储Activity栈 **/
    private val mActivityStack: Stack<WeakReference<Activity>> by lazy { Stack<WeakReference<Activity>>() }

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this

        initAppInjection()

        initFragmentation()

        //  去掉Android P 私有API提示框
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            closeAndroidPDialog()
        }

    }

    private fun initAppInjection() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(instance))
            .httpModule(HttpModule())
            .build()
    }


    /**
     * 全局伴生对象
     */
    companion object {

        private lateinit var instance: BaseApplication

        fun instance() = instance
    }

    private fun initFragmentation() {
        Fragmentation.builder()
            .stackViewMode(Fragmentation.NONE)
            .debug(BuildConfig.DEBUG)
            .handleException { e -> e.printStackTrace() }
            .install()
    }

    fun addActivity(act: Activity) {
        mActivityStack.add(WeakReference(act))
    }

    /**
     * 检查弱引用是否释放，若释放，则从栈中清理掉该元素
     */
    private fun checkWeakReference() {
        // 使用迭代器进行安全删除
        val it = mActivityStack.iterator()
        while (it.hasNext()) {
            val activityReference = it.next()
            val temp = activityReference.get()
            if (temp == null) {
                it.remove()
            }
        }
    }

    /**
     * 获取当前Activity（栈中最后一个压入的）
     */
    fun currentActivity(): Activity? {
        checkWeakReference()
        return if (!mActivityStack.isEmpty()) {
            mActivityStack.lastElement().get()
        } else null
    }

    /**
     * 关闭当前Activity（栈中最后一个压入的）
     */
    fun finishActivity() {
        val activity = currentActivity()
        if (activity != null) {
            finishActivity(activity)
        }
    }

    /**
     * 关闭指定的Activity
     *
     * @param activity
     */
    fun finishActivity(activity: Activity?) {
        if (activity != null) {
            // 使用迭代器进行安全删除
            val it = mActivityStack.iterator()
            while (it.hasNext()) {
                val activityReference = it.next()
                val temp = activityReference.get()
                // 清理掉已经释放的activity
                if (temp == null) {
                    it.remove()
                    continue
                }
                if (temp === activity) {
                    it.remove()
                }
            }
            activity.finish()
        }
    }

    /**
     * 关闭指定类名的所有Activity
     *
     * @param cls
     */
    fun finishActivity(cls: Class<*>) {
        // 使用迭代器进行安全删除
        val it = mActivityStack.iterator()
        while (it.hasNext()) {
            val activityReference = it.next()
            val activity = activityReference.get()
            // 清理掉已经释放的activity
            if (activity == null) {
                it.remove()
                continue
            }
            if (activity.javaClass == cls) {
                it.remove()
                activity.finish()
            }
        }
    }

    /**
     * 结束所有Activity
     */
    fun finishAllActivity() {
        for (activityReference in mActivityStack) {
            val activity = activityReference.get()
            activity?.finish()
        }
    }

    fun killOtherActivity() {
        var i = 0
        val size = mActivityStack.size
        while (i < size - 1) {
            val activity = mActivityStack[i].get()
            activity?.finish()
            i++
        }
        mActivityStack.clear()
    }

    @SuppressLint("PrivateApi")
    private fun closeAndroidPDialog() {
        try {
            val aClass = Class.forName("android.content.pm.PackageParser\$Package")
            val declaredConstructor = aClass.getDeclaredConstructor(String::class.java)
            declaredConstructor.isAccessible = true
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            val cls = Class.forName("android.app.ActivityThread")
            val declaredMethod = cls.getDeclaredMethod("currentActivityThread")
            declaredMethod.isAccessible = true
            val activityThread = declaredMethod.invoke(null)
            val mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown")
            mHiddenApiWarningShown.isAccessible = true
            mHiddenApiWarningShown.setBoolean(activityThread, true)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}