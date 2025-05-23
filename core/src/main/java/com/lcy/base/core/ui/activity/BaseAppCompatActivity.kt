package com.lcy.base.core.ui.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.SystemClock
import android.view.MenuItem
import androidx.annotation.CallSuper
import androidx.annotation.CheckResult
import androidx.fragment.app.FragmentActivity
import com.lcy.base.core.common.CoreApplication
import com.lcy.base.core.common.StatusBarMode
import com.lcy.base.core.rx.lifecycle.ActivityLifecycleable
import com.lcy.base.core.utils.BarUtils
import com.lcy.base.core.widgets.LoadingDialog
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.RxLifecycle
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.android.RxLifecycleAndroid
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import me.yokeyword.fragmentation.SupportActivity

abstract class BaseAppCompatActivity : SupportActivity(), ActivityLifecycleable {

    private val lifecycleSubject = BehaviorSubject.create<ActivityEvent>()

    protected lateinit var mContext: FragmentActivity

    protected var mCompositeDisposable: CompositeDisposable? = null

    //  是否显示黑色状态栏
    protected open var showDarkStatus = true

    private var mLoadingDialog: LoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleSubject.onNext(ActivityEvent.CREATE)
        // window.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
        initWindowFlags()
        reverseStatusColor()
        setContentView(getLayout())
        mContext = this
        CoreApplication.instance().addActivity(this)

        initToolbar(savedInstanceState)
        initPresenter()
        initEventAndData()
        initListeners()
    }

    protected abstract fun getLayout(): Int

    protected abstract fun initToolbar(savedInstanceState: Bundle?)

    protected abstract fun initPresenter()

    protected abstract fun initEventAndData()

    protected abstract fun initListeners()

    protected open fun initWindowFlags() {

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            this.onBackPressedSupport()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    @CheckResult
    override fun lifecycle(): Observable<ActivityEvent> {
        return lifecycleSubject.hide()
    }

    @CheckResult
    override fun <T> bindUntilEvent(event: ActivityEvent): LifecycleTransformer<T> {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event)
    }

    @CheckResult
    override fun <T> bindToLifecycle(): LifecycleTransformer<T> {
        return RxLifecycleAndroid.bindActivity(lifecycleSubject)
    }

    private fun reverseStatusColor() {
        if (showDarkStatus) {
            val mode = BarUtils.setStatusBarLightMode(this, true)
            if (mode == StatusBarMode.OTHERWISE) {
                BarUtils.setStatusBarColor(this, Color.rgb(191, 191, 191))
            }
        }
    }

    @CallSuper
    override fun onStart() {
        super.onStart()
        lifecycleSubject.onNext(ActivityEvent.START)
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        lifecycleSubject.onNext(ActivityEvent.RESUME)
    }

    @CallSuper
    override fun onPause() {
        lifecycleSubject.onNext(ActivityEvent.PAUSE)
        super.onPause()
    }

    @CallSuper
    override fun onStop() {
        lifecycleSubject.onNext(ActivityEvent.STOP)
        super.onStop()
    }

    override fun onDestroy() {
        lifecycleSubject.onNext(ActivityEvent.DESTROY)
        unSubscribe()
        dismissLoading()
        super.onDestroy()
        CoreApplication.instance().finishActivity(this)
    }

    protected fun addSubscribe(subscription: Disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
        mCompositeDisposable?.add(subscription)
    }

    private fun unSubscribe() {
        mCompositeDisposable?.dispose()
    }

    @Deprecated("Deprecated in Java")
    override fun startActivityForResult(intent: Intent, requestCode: Int, options: Bundle?) {
        if (startActivitySelfCheck(intent)) {
            @Suppress("DEPRECATION")
            super.startActivityForResult(intent, requestCode, options)
        }
    }

    private var mActivityJumpTag: String? = null
    private var mActivityJumpTime: Long = 0

    /**
     * 检查当前 Activity 是否重复跳转了，不需要检查则重写此方法并返回 true 即可
     *
     * @param intent 用于跳转的 Intent 对象
     * @return 检查通过返回true, 检查不通过返回false
     */
    private fun startActivitySelfCheck(intent: Intent): Boolean {
        if (intent.component == null || intent.action == null) {
            return true
        }
        // 默认检查通过
        var result = true
        val tag: String? = intent.component?.className ?: intent.action
        // 标记对象
        if (tag == mActivityJumpTag && mActivityJumpTime >= SystemClock.uptimeMillis() - 500) {
            // 检查不通过
            result = false
        }
        // 记录启动标记和时间
        mActivityJumpTag = tag
        mActivityJumpTime = SystemClock.uptimeMillis()
        return result
    }

    fun showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = LoadingDialog(this)
        }
        if (!mLoadingDialog!!.isShowing) {
            mLoadingDialog?.show()
        }
    }

    fun dismissLoading() {
        if (mLoadingDialog != null) {
            if (mLoadingDialog!!.isShowing) {
                mLoadingDialog!!.dismiss()
            }
            mLoadingDialog = null
        }
    }

}