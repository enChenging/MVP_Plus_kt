package com.release.mvp_kt.api


import com.release.mvp_kt.dao.VideoInfo
import com.release.mvp_kt.mvp.model.NewsDetailInfoBean
import com.release.mvp_kt.mvp.model.NewsInfoBean
import com.release.mvp_kt.mvp.model.PhotoSetInfoBean
import com.release.mvp_kt.mvp.model.SpecialInfoBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

/**
 * @author Mr.release
 * @create 2019/3/29
 * @Describe
 */
interface NewsServiceApi {

    companion object {
        const val CACHE_CONTROL_NETWORK = "Cache-Control: public, max-age=3600"
        // 避免出现 HTTP 403 Forbidden，参考：http://stackoverflow.com/questions/13670692/403-forbidden-with-java-but-not-web-browser
        const val AVOID_HTTP403_FORBIDDEN =
            "User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11"
    }


    /**
     * 要闻
     *
     * @param type
     * @param id
     * @param pageNumber
     * @param page
     * @return
     */
    @Headers(CACHE_CONTROL_NETWORK)
    @GET("nc/article/{type}/{id}/{pageNumber}-{page}.html")
    fun getImportantNews(
        @Path("type") type: String,
        @Path("id") id: String,
        @Path("pageNumber") pageNumber: Int,
        @Path("page") page: Int
    ): Observable<Map<String, List<NewsInfoBean>>>

    /**
     * 新闻详情
     *
     * @param newsId
     * @return
     */
    @Headers(AVOID_HTTP403_FORBIDDEN)
    @GET("nc/article/{newsId}/full.html")
    fun getNewsDetail(@Path("newsId") newsId: String): Observable<Map<String, NewsDetailInfoBean>>

    /**
     * 获取专题
     *
     * @param specialIde
     * @return
     */
    @Headers(CACHE_CONTROL_NETWORK)
    @GET("nc/special/{specialId}.html")
    fun getSpecial(@Path("specialId") specialIde: String): Observable<Map<String, SpecialInfoBean>>


    /**
     * 获取图像集
     *
     * @param photoId
     * @return
     */
    @Headers(CACHE_CONTROL_NETWORK)
    @GET("photo/api/set/{photoId}.json")
    fun getPhotoAlbum(@Path("photoId") photoId: String): Observable<PhotoSetInfoBean>

    /**
     * 获取视频
     *
     * @param id
     * @param pageNumber
     * @param page
     * @return
     */
    @Headers(AVOID_HTTP403_FORBIDDEN)
    @GET("nc/video/list/{id}/n/{pageNumber}-{page}.html")
    fun getVideoList(
        @Path("id") id: String,
        @Path("pageNumber") pageNumber: Int,
        @Path("page") page: Int
    ): Observable<Map<String, List<VideoInfo>>>


}
