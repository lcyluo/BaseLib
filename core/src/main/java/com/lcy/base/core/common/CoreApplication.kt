package com.lcy.base.core.common

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import me.yokeyword.fragmentation.Fragmentation
import java.lang.ref.WeakReference
import java.util.*

/**
 * Application 基类
 *
 * @author lcy
 */
abstract class CoreApplication : Application() {

    /** 存储Activity栈 **/
    private val mActivityStack: Stack<WeakReference<Activity>> by lazy { Stack<WeakReference<Activity>>() }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initFragmentation()
    }

    /**
     * 全局伴生对象
     */
    companion object {

        private lateinit var instance: CoreApplication

        @JvmStatic
        fun instance() = instance
    }

    private fun initFragmentation() {
        Fragmentation.builder()
            .stackViewMode(Fragmentation.NONE)
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
            if (!activity.isFinishing && !activity.isDestroyed) {
                activity.finish()
            }
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
                if (!activity.isFinishing && !activity.isDestroyed) {
                    activity.finish()
                }
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

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}