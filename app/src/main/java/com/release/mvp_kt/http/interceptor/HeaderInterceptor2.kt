package com.release.mvp_kt.http.interceptor

import com.orhanobut.logger.Logger
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
@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class HeaderInterceptor2 : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        val requestBuffer = Buffer()

        val requestBody = request.body()

        if (requestBody != null)
            (requestBuffer as BufferedSink)?. let { requestBody.writeTo(it) }
        else
            Logger.d("HeaderInterceptor2--request.body() == null")

        //打印url信息
        Logger.d(
            "HeaderInterceptor2---"+request.url().toString() + if (requestBody != null) "?" + parseParams(
                requestBody,
                requestBuffer
            ) else ""
        )

        return chain.proceed(request) ?: throw RuntimeException("Response返回值不能为空")
    }

    @Throws(UnsupportedEncodingException::class)
    private fun parseParams(body: RequestBody, requestBuffer: Buffer): String {
        return if (body.contentType() != null && !body.contentType()!!.toString().contains("multipart")) {
            URLDecoder.decode(requestBuffer.readUtf8(), "UTF-8")
        } else "null"
    }


}