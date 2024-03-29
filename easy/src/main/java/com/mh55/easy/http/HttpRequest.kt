package com.mh55.easy.http

import androidx.collection.ArrayMap
import com.mh55.easy.App.ConfigBuilder
import com.mh55.easy.http.interceptor.CacheInterceptor
import com.mh55.easy.http.interceptor.HttpHeaderInterceptor
import com.mh55.easy.http.interceptor.HttpLogInterceptor
import com.mh55.easy.http.moshi.MyKotlinJsonAdapterFactory
import com.mh55.easy.http.moshi.MyStandardJsonAdapters
import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.*
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 *
 *
 *
 * 目的1：没网的时候，尝试读取缓存，避免界面空白，只需要addInterceptor和cache即可（已实现）
 * 目的2：有网的时候，总是读取网络上最新的，或者设置一定的超时时间，比如10秒内有多个同一请求，则都从缓存中获取（没实现）
 * 目的3：不同的接口，不同的缓存策略（？）
 */
object HttpRequest {
    private const val mSpName = "http_request_flag"
    private const val mKeyIsSave = "is_save"

    // 缓存 service
    private val mServiceMap = ArrayMap<String, Any>()

    // 默认的 baseUrl
    lateinit var mDefaultBaseUrl: String

    // 默认的请求头
    lateinit var mDefaultHeader: ArrayMap<String, String>

    /**
     * 存储 baseUrl，以便可以动态更改
     */
    private lateinit var mBaseUrlMap: ArrayMap<String, String>

    /**
     * 请求超时时间，秒为单位
     */
    var mDefaultTimeout = 60*10

    /**
     * 添加默认的请求头
     */
    @JvmStatic
    fun addDefaultHeader(name: String, value: String) {
        if (!this::mDefaultHeader.isInitialized) {
            mDefaultHeader = ArrayMap()
        }
        mDefaultHeader[name] = value
    }

    /**
     * 设置了 [mDefaultBaseUrl] 后，可通过此方法获取 Service
     */
    @JvmStatic
    fun <T> getService(cls: Class<T>): T {

        mDefaultBaseUrl = ConfigBuilder.mBaseHost

        if (mDefaultBaseUrl.isNullOrEmpty()) {
            throw RuntimeException("必须初始化 mBaseUrl")
        }

        return getService(cls, mDefaultBaseUrl, null)
    }

    /**
     * 如果有不同的 baseURL，那么可以相同 baseURL 的接口都放在一个 Service 钟，通过此方法来获取
     */
    @JvmStatic
    fun <T> getService(cls: Class<T>, host: String, vararg interceptors: Interceptor?): T {
        val name = cls.name

        var obj: Any? = mServiceMap[name]
        if (obj == null) {
            val httpClientBuilder = OkHttpClient.Builder()

            // 超时时间
            httpClientBuilder.connectTimeout(mDefaultTimeout.toLong(), TimeUnit.SECONDS)
            httpClientBuilder.addInterceptor(CacheInterceptor())
            httpClientBuilder.addInterceptor(HttpHeaderInterceptor())
            httpClientBuilder.addInterceptor(HttpLogInterceptor())

            // 拦截器
            interceptors.forEach { interceptor ->
                interceptor?.let {
                    httpClientBuilder.addInterceptor(it)
                }
            }

            val client = httpClientBuilder.build()
            val builder = Retrofit.Builder().client(client)
                // 基础url
                .baseUrl(host)
                // JSON解析
                .addConverterFactory(MoshiConverterFactory.create(getMoshi()))

            obj = builder.build().create(cls)
            mServiceMap[name] = obj
        }
        @Suppress("UNCHECKED_CAST")
        return obj as T
    }

    private fun getMoshi(): Moshi {
        return Moshi.Builder()
            .add(MyKotlinJsonAdapterFactory())
            .add(MyStandardJsonAdapters.FACTORY)
            .build()
    }
}
