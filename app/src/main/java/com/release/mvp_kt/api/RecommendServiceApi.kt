package com.release.mvp_kt.api


import com.release.mvp_kt.mvp.model.bean.RecommendPageBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Mr.release
 * @create 2019/3/29
 * @Describe
 */
interface RecommendServiceApi {

    @GET("it")
    fun getRecommendData(@Query("key") key: String, @Query("num") num: Int): Observable<RecommendPageBean>
}
