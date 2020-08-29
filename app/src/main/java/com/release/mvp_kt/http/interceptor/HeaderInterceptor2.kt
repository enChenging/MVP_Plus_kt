package com.release.mvp_kt.http.interceptor

import android.webkit.WebSettings
import com.orhanobut.logger.Logger
import com.release.mvp_kt.App
import okhttp3.Interceptor
import okhttp3.RequestBody
import okhttp3.Response
import okio.Buffer
import okio.BufferedSink
import java.io.UnsupportedEncodingException
import java.net.URLDecoder


/**
 * @author Mr.release
 * @create 2019/6/26
 * @Describe
 */
class HeaderInterceptor2 : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        val startTime = System.currentTimeMillis()
        val response = chain.proceed(chain.request())
        val endTime = System.currentTimeMillis()
        val duration = endTime - startTime
        val content = response.body()!!.string()

        val requestBuffer = Buffer()
        val requestBody = request.body()

        if (requestBody != null)
            (requestBuffer as BufferedSink).let { requestBody.writeTo(it) }

        //打印url信息
        Logger.d(request.url().toString() +
                if (requestBody != null) "?" + parseParams(requestBody, requestBuffer) else ""
        )

        Logger.d("$duration 毫秒 \n $content")

        request.newBuilder()
            .removeHeader("User-Agent") //移除旧的
            .addHeader("User-Agent", WebSettings.getDefaultUserAgent(App.context)) //添加真正的头部
            .build()
        return chain.proceed(request)
    }

    @Throws(UnsupportedEncodingException::class)
    private fun parseParams(body: RequestBody, requestBuffer: Buffer): String {
        return if (body.contentType() != null && !body.contentType()!!.toString()
                .contains("multipart")
        ) {
            URLDecoder.decode(requestBuffer.readUtf8(), "UTF-8")
        } else "null"
    }


}