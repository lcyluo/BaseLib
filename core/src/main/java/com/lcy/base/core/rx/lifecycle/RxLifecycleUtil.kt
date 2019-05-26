package com.lcy.base.core.rx.lifecycle

import com.lcy.base.core.presenter.view.IBaseView
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.annotations.NonNull

object RxLifecycleUtil {

    fun <T> bindToLifecycle(@NonNull view: IBaseView): LifecycleTransformer<T> {
        return when (view) {
            is ActivityLifecycleable -> bindToLifecycle(view as ActivityLifecycleable)
            is FragmentLifecycleable -> bindToLifecycle(view as FragmentLifecycleable)
            else -> throw IllegalArgumentException("view isn't Lifecycleable")
        }
    }

    private fun <T> bindToLifecycle(@NonNull lifecycleProvider: LifecycleProvider<*>): LifecycleTransformer<T> {
        return (lifecycleProvider as? ActivityLifecycleable)?.bindToLifecycle()
            ?: ((lifecycleProvider as? FragmentLifecycleable)?.bindToLifecycle()
                ?: throw IllegalArgumentException("Lifecycleable not match"))
    }
}