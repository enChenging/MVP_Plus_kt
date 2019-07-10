package com.release.mvp_kt.http.interceptor

import com.orhanobut.logger.Logger
import com.release.mvp_kt.constant.Constant
import com.release.mvp_kt.constant.HttpConstant
import com.release.mvp_kt.utils.Sp
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author Mr.release
 * @create 2019/6/26
 * @Describe
 */
class HeaderInterceptor : Interceptor {

    /**
     * token
     */
    private var token: String by Sp(Constant.TOKEN_KEY, "")

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        val builder = request.newBuilder()

        builder.addHeader("Content-type", "application/json; charset=utf-8")
        // .header("token", token)
        // .method(request.method(), request.body())

        val domain = request.url().host()
        val url = request.url().toString()
        Logger.i("host：$domain")
        if (domain.isNotEmpty() && (url.contains(HttpConstant.COLLECTIONS_WEBSITE)
                        || url.contains(HttpConstant.UNCOLLECTIONS_WEBSITE)
                        || url.contains(HttpConstant.ARTICLE_WEBSITE)
                        || url.contains(HttpConstant.TODO_WEBSITE))) {

            val spDomain: String by Sp(domain, "")
            val cookie: String = if (spDomain.isNotEmpty()) spDomain else ""
            if (cookie.isNotEmpty()) {
                // 将 Cookie 添加到请求头
                builder.addHeader(HttpConstant.COOKIE_NAME, cookie)
            }
        }

        return chain.proceed(builder.build())
    }

}