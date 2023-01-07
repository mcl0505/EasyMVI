package com.mh55.easymvvm.http.interceptor

import com.mh55.easymvvm.http.HttpRequest
import okhttp3.Interceptor
import okhttp3.Response

/**
 *   公司名称: ~漫漫人生路~总得错几步~
 *   创建作者: Android 孟从伦
 *   创建时间: 2022/10/28
 *   功能描述: 请求头拦截
 */
class HttpHeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBuilder = request.newBuilder()
        HttpRequest.mDefaultHeader.forEach {
            requestBuilder.addHeader(it.key,it.value)
        }
        val build = requestBuilder.build()
        return chain.proceed(build)
    }
}