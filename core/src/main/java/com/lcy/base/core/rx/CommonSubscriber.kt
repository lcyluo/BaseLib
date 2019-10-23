package com.lcy.base.core.rx

import com.lcy.base.core.presenter.view.IBaseView
import io.reactivex.subscribers.ResourceSubscriber

/**
 * 公告参数解析
 *
 * @author lcy
 */
abstract class CommonSubscriber<T> constructor(
    val view: IBaseView,
    private val errorMsg: String = "未知错误"
) :
    ResourceSubscriber<T>() {


    override fun onComplete() {
        view.hideProgress()
    }

    override fun onError(t: Throwable) {
        errorHandle(t, view, errorMsg)
    }
}