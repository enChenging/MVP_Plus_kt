@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.release.mvp_kt.http

import com.release.base.http.HttpConstant
import com.release.mvp_kt.App
import com.release.mvp_kt.BuildConfig
import com.release.mvp_kt.http.api.BaseURL
import com.release.mvp_kt.http.api.NewsServiceApi
import com.release.mvp_kt.http.api.RecommendServiceApi
import com.release.mvp_kt.http.interceptor.CacheInterceptor
import com.release.mvp_kt.http.interceptor.HeaderInterceptor2
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * @author Mr.release
 * @create 2019/6/26
 * @Describe
 */
object RetrofitHelper {

    private var retrofit: Retrofit? = null
    private var retrofit2: Retrofit? = null

    /**
     * 获取 OkHttpClient
     */
    private fun getOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient().newBuilder()

        val sslParams = HttpsUtils.sslSocketFactory
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        val cacheFile = File(App.context.cacheDir, "cache")
        val cache = Cache(cacheFile, HttpConstant.MAX_CACHE_SIZE)

        builder.run {
            addInterceptor(httpLoggingInterceptor)
            addInterceptor(HeaderInterceptor2())
//            addNetworkInterceptor(CacheInterceptor())
            sslParams.sSLSocketFactory?.let {
                sslParams.trustManager?.let { it1 -> sslSocketFactory(it, it1) } }
//            cache(cache)  //添加缓存
            connectTimeout(HttpConstant.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(HttpConstant.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(HttpConstant.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            retryOnConnectionFailure(true) // 错误重连
        }
        return builder.build()
    }

    private fun getRetrofit(): Retrofit? {
        if (retrofit == null) {
            synchronized(RetrofitHelper::class.java) {
                if (retrofit == null) {
                    retrofit = Retrofit.Builder()
                        .client(getOkHttpClient())
                        .baseUrl(BaseURL.NEWS_HOST)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//rx网络适配器
                        .build()
                }
            }
        }
        return retrofit
    }

    private fun getRetrofit2(): Retrofit? {
        if (retrofit2 == null) {
            synchronized(RetrofitHelper::class.java) {
                if (retrofit2 == null) {
                    retrofit2 = Retrofit.Builder()
                        .client(getOkHttpClient())
                        .baseUrl(BaseURL.RECOMMEND_HOST)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//rx网络适配器
                        .build()
                }
            }
        }
        return retrofit2
    }

    val newsService: NewsServiceApi by lazy {
        getRetrofit()!!.create(NewsServiceApi::class.java)
    }

    val recommendService: RecommendServiceApi by lazy {
        getRetrofit2()!!.create(RecommendServiceApi::class.java)
    }

}