package com.mh55.easymvvm.http

import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonEncodingException
import org.apache.http.conn.ConnectTimeoutException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException
import javax.net.ssl.SSLException


/**
 * 公司名称：~漫漫人生路~总得错几步~
 * 创建作者：Android 孟从伦
 * 创建时间：2022/10/3
 * 功能描述：关于服务端的统一异常处理
 */

object ExceptionHandle {
    fun handleException(
        throwable: Throwable?
    ): ResponseThrowable {
        return when (throwable) {
            is ResponseThrowable -> throwable
            is HttpException -> ResponseThrowable(
                SealedError.NetworkError(),
                throwable.message()
            )
            is JsonDataException, is JsonEncodingException, is ParseException -> ResponseThrowable(
                SealedError.ParseError(),
                throwable.message
            )
            is ConnectException -> ResponseThrowable(
                SealedError.NetworkError(),
                throwable.message
            )
            is SSLException -> ResponseThrowable(SealedError.SslError(), throwable.message)
            is ConnectTimeoutException -> ResponseThrowable(
                SealedError.TimeoutError(),
                throwable.message
            )
            is SocketTimeoutException -> ResponseThrowable(
                SealedError.TimeoutError(),
                throwable.message
            )
            is UnknownHostException -> ResponseThrowable(
                SealedError.Unknown(),
                throwable.message
            )
            else -> ResponseThrowable(
                SealedError.Unknown(),
                throwable?.message
            )
        }
    }
}