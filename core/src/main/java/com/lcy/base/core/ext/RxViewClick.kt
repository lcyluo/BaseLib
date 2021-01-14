package com.lcy.base.core.ext

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.lcy.base.core.common.Constants
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

fun View.rxClick(callback: (View) -> Unit) {
    Observable.create<View> {
        setOnClickListener(it::onNext)
    }.throttleFirst(Constants.WINDOW_DURATION_SHORT, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .doOnNext(callback)
        .subscribe()
        .bindLifecycle(this)
}

fun View.rxViewClick(callback: (Any) -> Unit) {
    RxView.clicks(this)
        .throttleFirst(Constants.WINDOW_DURATION_SHORT, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .doOnNext(callback)
        .subscribe()
        .bindLifecycle(this)
}

fun TextView.textChanges(callback: (CharSequence) -> Unit) {
    RxTextView.textChanges(this)
        .throttleFirst(50, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .doOnNext(callback)
        .subscribe()
        .bindLifecycle(this)
}

fun Disposable.bindLifecycle(view: View?) =
    this.apply {
        (view?.activity as? AppCompatActivity)?.lifecycle?.addObserver(LifecycleDisposable(this))
    }

val View.activity: Activity?
    get() = getActivity(context)

private fun getActivity(context: Context?): Activity? {
    if (context == null) return null
    if (context is Activity) return context
    if (context is ContextWrapper) return getActivity(context.baseContext)
    return null
}


fun ViewGroup.inflate(resId: Int): View {
    return LayoutInflater.from(context).inflate(resId, this, false)
}

class LifecycleDisposable(private val disposable: Disposable) : LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        disposable.dispose()
    }
}