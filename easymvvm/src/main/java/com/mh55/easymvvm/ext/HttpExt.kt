package com.mh55.easymvvm.ext

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.viewModelScope
import com.mh55.easymvvm.EasyApplication
import com.mh55.easymvvm.http.BaseResponse
import com.mh55.easymvvm.http.ExceptionHandle
import com.mh55.easymvvm.http.ResponseThrowable
import com.mh55.easymvvm.mvvm.BaseViewModel
import com.mh55.easymvvm.mvvm.LoadingEntity
import com.mh55.easymvvm.utils.LogUtil
import kotlinx.coroutines.*

fun <T> BaseViewModel.request(
    block: suspend () -> BaseResponse<T>,
    success: (T?) -> Unit,
    error: (ResponseThrowable) -> Unit = {},
    isLoading: Boolean = false,
    loadingMessage: String? = null
): Job {
    // 开始执行请求
    httpState.postValue(BaseViewModel.HttpState.HttpBefore(
        isLoading,
        loadingMessage?.isNotEmpty() == true,
        loadingMessage ?: ""))
    return viewModelScope.launch {
        kotlin.runCatching {
            //请求体
            block()
        }.onSuccess {
            // 网络请求成功， 结束请求
            httpState.postValue(BaseViewModel.HttpState.HttpAfter)
            //校验请求结果码是否正确，不正确会抛出异常走下面的onFailure
            kotlin.runCatching {
                executeResponse(it) { coroutine ->
                    success(coroutine)
                }
            }.onFailure { error ->
                // 请求时发生异常， 执行失败回调
                val responseThrowable = ExceptionHandle.handleException(error)
                httpState.postValue(BaseViewModel.HttpState.HttpFailed(responseThrowable.errorCode,responseThrowable.errorMsg ?: ""))
                responseThrowable.errorLog?.let { errorLog ->
                    LogUtil.e(errorLog)
                }
                // 执行失败的回调方法
                error(responseThrowable)
            }
        }.onFailure { error ->
            // 请求时发生异常， 执行失败回调
            val responseThrowable = ExceptionHandle.handleException(error)
            httpState.postValue(BaseViewModel.HttpState.HttpFailed(responseThrowable.errorCode,responseThrowable.errorMsg ?: ""))
            responseThrowable.errorLog?.let { errorLog ->
                LogUtil.e(errorLog)
            }
            // 执行失败的回调方法
            error(responseThrowable)
        }
    }
}

fun isConnected(): Boolean {
    val manager = EasyApplication.instance.getSystemService(
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
suspend fun <T> executeResponse(
    response: BaseResponse<T>,
    success: suspend CoroutineScope.(T?) -> Unit
) {
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