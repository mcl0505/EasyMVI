package com.mh55.easymvvm.http.interceptor

import android.util.Log
import com.alibaba.fastjson.JSON
import com.mh55.easymvvm.App.ConfigBuilder
import com.mh55.easymvvm.http.HttpRequest
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import okio.Buffer
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
            sb.append("\n|| 请求地址 ==> $url")
            if (HttpRequest.mDefaultHeader!=null && HttpRequest.mDefaultHeader?.size?:0 > 0){
                sb.append("\n|| 默认参数 ==> ${JSON.toJSONString(HttpRequest.mDefaultHeader)}")
            }

            val formSb = StringBuilder()
            if ("POST" == method||"PUT" == method){
                if (request.body is FormBody) {

                    val body = request.body as FormBody?
                    for (i in 0 until body!!.size) {
                        formSb.append(body!!.encodedName(i) + "=" + body!!.encodedValue(i) + ",")
                    }


                }else {
                    val mBuffer = Buffer()
                    kotlin.runCatching {
                        request.body?.writeTo(mBuffer)
                    }
                    formSb.append(mBuffer.readUtf8())
                }
            }else if ("GET" == method){
                formSb.append(url.query)
            }


            sb.append("\n|| 请求参数 ${method}==> $formSb")
            sb.append("\n|| 请求响应 ==> ${formatJson(content)}")
            sb.append("\n|| ========= End: $duration 毫秒========================================================================== ")
            Log.d(TAG, sb.toString())
        }

        return response.newBuilder()
            .body(ResponseBody.create(mediaType, content))
            .build()
    }

    private fun formatJson(json: String): String? {
        try {
            var i = 0
            val len = json.length
            while (i < len) {
                val c = json[i]
                if (c == '{') {
                    return JSONObject(json).toString(4)
                } else if (c == '[') {
                    return JSONArray(json).toString(4)
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