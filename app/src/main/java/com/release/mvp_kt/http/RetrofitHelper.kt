@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.release.mvp_kt.http

import com.release.mvp_kt.App
import com.release.mvp_kt.BuildConfig
import com.release.mvp_kt.api.NewsServiceApi
import com.release.mvp_kt.api.RecommendServiceApi
import com.release.mvp_kt.constant.Constant
import com.release.mvp_kt.constant.HttpConstant
import com.release.mvp_kt.dao.VideoInfo
import com.release.mvp_kt.http.interceptor.CacheInterceptor
import com.release.mvp_kt.http.interceptor.HeaderInterceptor2
import com.release.mvp_kt.mvp.model.bean.*
import com.release.mvp_kt.rx.SchedulerUtils
import com.release.mvp_kt.utils.StringUtils
import io.reactivex.Flowable
import io.reactivex.functions.Function
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.reactivestreams.Publisher
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
    private var builder: OkHttpClient.Builder? = null

    init {
        initOkHttpClient()
    }

    /**
     * 获取 OkHttpClient
     */
    private fun initOkHttpClient() {
        if (builder == null) {
            synchronized(RetrofitHelper::class.java) {
                if (builder == null) {

                    val sslParams = HttpsUtils.sslSocketFactory
                    val httpLoggingInterceptor = HttpLoggingInterceptor()
                    if (BuildConfig.DEBUG) {
                        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                    } else {
                        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
                    }
                    val cacheFile = File(App.context.cacheDir, "cache")
                    val cache = Cache(cacheFile, HttpConstant.MAX_CACHE_SIZE)

                    builder = OkHttpClient.Builder().run {
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

                }
            }
        }
    }

    private fun <T> createApi(baseUrl: String, cls: Class<T>): T {

        retrofit = Retrofit.Builder()
            .client(builder?.build())
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//rx网络适配器
            .build()

        return retrofit!!.create(cls)
    }

    private val newsService: NewsServiceApi by lazy {
        createApi(Constant.NEWS_HOST, NewsServiceApi::class.java)
    }

    private val recommendService: RecommendServiceApi by lazy {
        createApi(Constant.RECOMMEND_HOST, RecommendServiceApi::class.java)
    }

    /************************************ API *******************************************/

    private const val HEAD_LINE_NEWS = "T1348647909107"

    /**
     * 获取要闻
     *
     * @param newsId
     * @param page
     * @return
     */
    fun getImportantNewAPI(newsId: String, page: Int): Flowable<NewsInfoBean> {
        val type: String = if (newsId == HEAD_LINE_NEWS)
            "headline"
        else
            "list"

        return newsService.getImportantNews(type, newsId, page * Constant.PAGE)
            .flatMap(object : Function<Map<String, List<NewsInfoBean>>, Publisher<NewsInfoBean>> {
                override fun apply(stringListMap: Map<String, List<NewsInfoBean>>): Publisher<NewsInfoBean> {
                    return Flowable.fromIterable(stringListMap[newsId]) //获取List<NewsInfoBean>
                }
            })
            .compose(SchedulerUtils.ioToMain())
    }

    /**
     * 获取视频列表
     *
     * @param videoId
     * @param page
     * @return
     */
    fun getVideoListAPI(videoId: String, page: Int): Flowable<List<VideoInfo>> {
        return newsService.getVideoList(videoId, page * Constant.PAGE / 2)
            .flatMap { stringListMap -> Flowable.just<List<VideoInfo>>(stringListMap[videoId]) }
            .compose(SchedulerUtils.ioToMain())
    }

    /**
     * 获取新闻详情
     *
     * @param view
     * @param newsId
     * @return
     */
    fun getNewsDetailAPI(newsId: String): Flowable<NewsDetailInfoBean> {
        return newsService.getNewsDetail(newsId)
            .flatMap(object : Function<Map<String, NewsDetailInfoBean>, Publisher<NewsDetailInfoBean>> {
                override fun apply(stringNewsDetailInfoBeanMap: Map<String, NewsDetailInfoBean>): Publisher<NewsDetailInfoBean> {
                    return Flowable.just<NewsDetailInfoBean>(stringNewsDetailInfoBeanMap[newsId])//获取NewsDetailInfoBean
                }
            })
            .compose(SchedulerUtils.ioToMain())
    }

    /**
     * 获取专题新闻
     *
     * @param specialId
     * @return
     */
    fun getNewsSpecialAPI(specialId: String): Flowable<SpecialInfoBean> {
        return newsService.getSpecial(specialId)
            .flatMap { stringSpecialInfoBeanMap -> Flowable.just(stringSpecialInfoBeanMap[specialId]) }
            .compose(SchedulerUtils.ioToMain())
    }


    /**
     * 获取新闻图集
     *
     * @param photoId
     * @return
     */
    fun getPhotoAlbumAPI(photoId: String): Flowable<PhotoSetInfoBean> {
        return newsService
            .getPhotoAlbum(StringUtils.clipPhotoSetId(photoId))
            .compose(SchedulerUtils.ioToMain())
    }


    /**
     * 获取推荐数据
     */
    fun getRecommendData(key: String, mum: Int): Flowable<RecommendPageBean> {
        return recommendService
            .getRecommendData(key, mum * Constant.PAGE / 2)
            .compose(SchedulerUtils.ioToMain())

    }

}