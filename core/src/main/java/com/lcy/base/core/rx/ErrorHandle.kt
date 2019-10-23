package com.lcy.base.core.rx

import android.net.ParseException
import com.google.gson.JsonParseException
import com.lcy.base.core.data.net.ApiConstant
import com.lcy.base.core.data.net.ApiException
import com.lcy.base.core.presenter.view.IBaseView
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun errorHandle(t: Throwable, view: IBaseView, errorMsg: String = "未知错误") {
    view.hideProgress()
    if (t is ApiException) {
        view.showError(t.errorCode, t.message ?: errorMsg)
    } else if (t is SocketTimeoutException || t is ConnectException || t is UnknownHostException || t is HttpException) {
        view.showError(ApiConstant.NETWORK_ERROR, "网络连接异常")
    } else if (t is JsonParseException || t is JSONException || t is ParseException) {
        view.showError(ApiConstant.SERVER_ERROR, "数据解析异常")
    } else {
        view.showError(ApiConstant.UNKNOWN_ERROR, errorMsg)
    }
}