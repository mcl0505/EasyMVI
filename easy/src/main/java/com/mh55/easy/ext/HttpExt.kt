package com.mh55.easy.ext

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Button
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.viewModelScope
import com.google.android.material.button.MaterialButton
import com.kingja.loadsir.callback.SuccessCallback
import com.mh55.easy.EasyApp
import com.mh55.easy.R
import com.mh55.easy.http.*
import com.mh55.easy.manager.AppManager
import com.mh55.easy.mvvm.BaseViewModel
import com.mh55.easy.mvvm.intent.BaseViewIntent
import com.mh55.easy.ui.loadsir.LoadSirDefaultNetCallback
import kotlinx.coroutines.*
import java.net.ConnectException

fun <T> BaseViewModel.httpRequest(
    block: suspend () -> BaseResponse<T>,
    success: (T?) -> Unit,
    error: (ResponseThrowable) -> Unit = {},
    isLoading: Boolean = false,
    loadingMessage: String = R.string.loading_msg.getString()
): Job {
    // 开始执行请求
    if (isLoading) showLoading(loadingMessage)
    return viewModelScope.launch {
        if (!isConnected()) {
            error.invoke(ExceptionHandle.handleException(ConnectException()))
            return@launch
        }

        kotlin.runCatching {
            //请求体
            block()
        }.onSuccess {
            // 网络请求成功， 结束请求
            if (isLoading) dismissLoading()
            //校验请求结果码是否正确，不正确会抛出异常走下面的onFailure
            kotlin.runCatching {
                executeResponse(it) { coroutine ->
                    success(coroutine)
                }
            }.onFailure { error ->
                // 请求时发生异常， 执行失败回调
                executeError(ExceptionHandle.handleException(error)) {
                    error(it)
                }
            }
        }.onFailure { error ->
            // 请求时发生异常， 执行失败回调
            if (isLoading) dismissLoading()
            val responseThrowable = ExceptionHandle.handleException(error)
            executeError(responseThrowable) {
                error(it)
            }
        }
    }
}

fun BaseViewModel.httpRequestMultiple(
    block: suspend CoroutineScope.() -> Unit
){
    if (isConnected()){
        mUiChangeLiveData.postValue(BaseViewIntent.showCallback(SuccessCallback::class.java))
        viewModelScope.launch {
            block()
        }
    }else {
        mUiChangeLiveData.postValue(BaseViewIntent.showCallback(LoadSirDefaultNetCallback::class.java){_,view->
            view.findViewById<MaterialButton>(R.id.btn_net_refresh).singleClick {
                httpRequestMultiple(block)
            }
        })
    }
}




fun isConnected(): Boolean {
    val manager = AppManager.getApplication().getSystemService(
        Context.CONNECTIVITY_SERVICE
    ) as? ConnectivityManager?
    if (manager != null) {
        val info = manager.activeNetworkInfo
        return info != null && info.isAvailable
    }
    return false
}

/**
 * 请求结果过滤，判断请求服务器请求结果是否成功，不成功则会抛出异常
 */
suspend fun <T> executeResponse(response: BaseResponse<T>, success: suspend CoroutineScope.(T?) -> Unit) {
    coroutineScope {
        when {
            response.isSuccess() -> {
                success(response.getResponseData())
            }
            else -> {
                throw ResponseThrowable(
                    response.getResponseCode(),
                    response.getResponseMessage(),
                    response.getResponseMessage()
                )
            }
        }
    }
}

/**
 * 过滤错误信息，
 */
suspend fun executeError(response: ResponseThrowable, error: (ResponseThrowable) -> Unit = {}) {
    when (response.errorCode) {
        ResponseCode.Code_CancelHttp ->{}
        else -> error.invoke(response)
    }
}