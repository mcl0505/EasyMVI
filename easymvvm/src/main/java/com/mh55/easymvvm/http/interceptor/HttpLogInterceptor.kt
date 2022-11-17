package com.mh55.easymvvm.http.interceptor

import android.util.Log
import com.alibaba.fastjson.JSON
import com.mh55.easymvvm.App.ConfigBuilder
import com.mh55.easymvvm.http.HttpRequest
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 *   公司名称: ~漫漫人生路~总得错几步~
 *   创建作者: Android 孟从伦
 *   创建时间: 2022/10/28
 *   功能描述: 请求日志拦截
 */
class HttpLogInterceptor  : Interceptor {
    private val TAG = "LogUtil"
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val startTime = System.currentTimeMillis()
        val response = chain.proceed(chain.request())
        val endTime = System.currentTimeMillis()
        val duration = endTime - startTime
        val mediaType = response.body!!.contentType()
        val content = response.body!!.string()
        val method = request.method
        val url = request.url

        if (ConfigBuilder.isOpenLog){
            val sb = StringBuilder()
            sb.append("LogUtil  ===============Start================================================================================ ")
            sb.append("\n|| 请求地址 $method   $url")
            if (HttpRequest.mDefaultHeader!=null && HttpRequest.mDefaultHeader?.size?:0 > 0){
                sb.append("\n 默认参数 ${JSON.toJSONString(HttpRequest.mDefaultHeader)}")
            }

            if ("POST" == method||"PUT" == method){
                if (request.body is FormBody) {
                    val formSb = StringBuilder()
                    val body = request.body as FormBody?
                    for (i in 0 until body!!.size) {
                        formSb.append(body!!.encodedName(i) + "=" + body!!.encodedValue(i) + ",")
                    }

                    sb.append("\n|| 请求参数 $formSb")
                }
            }else if ("GET" == method){
                if (!url.query.isNullOrEmpty()){
                    sb.append("\n|| 请求参数 ${url.query}")
                }
            }
            sb.append("\n|| 请求响应 ${formatJson(content,4)}")
            sb.append("\n|| ========= End: $duration 毫秒========================================================================== ")

            Log.d(TAG, sb.toString())
        }

        return response.newBuilder()
            .body(ResponseBody.create(mediaType, content))
            .build()
    }

    fun formatJson(json: String, indentSpaces: Int): String? {
        try {
            var i = 0
            val len = json.length
            while (i < len) {
                val c = json[i]
                if (c == '{') {
                    return JSONObject(json).toString(indentSpaces)
                } else if (c == '[') {
                    return JSONArray(json).toString(indentSpaces)
                } else if (!Character.isWhitespace(c)) {
                    return json
                }
                i++
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return json
    }
}