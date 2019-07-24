@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.release.mvp_kt.http

import com.release.mvp_kt.App
import com.release.mvp_kt.BuildConfig
import com.release.mvp_kt.api.NewsServiceApi
import com.release.mvp_kt.api.RecommendServiceApi
import com.release.mvp_kt.constant.Constant
import com.release.mvp_kt.constant.HttpConstant
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
            addNetworkInterceptor(CacheInterceptor())
            sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
            cache(cache)  //添加缓存
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
//                        .baseUrl(Constant.RECOMMEND_HOST)
                        .baseUrl(Constant.NEWS_HOST)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//rx网络适配器
                        .build()
                }
            }
        }
        return retrofit
    }

    val recommendService: RecommendServiceApi by lazy {
        getRetrofit()!!.create(RecommendServiceApi::class.java)
    }

    val newsService: NewsServiceApi by lazy {
        getRetrofit()!!.create(NewsServiceApi::class.java)
    }
}