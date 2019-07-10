package com.release.mvp_kt.mvp.model

import com.release.mvp_kt.http.RetrofitHelper
import com.release.mvp_kt.mvp.contract.HomeContract
import com.release.mvp_kt.mvp.model.bean.Article
import com.release.mvp_kt.mvp.model.bean.ArticleResponseBody
import com.release.mvp_kt.mvp.model.bean.Banner
import com.release.mvp_kt.mvp.model.bean.HttpResult
import io.reactivex.Observable

/**
 * @author Mr.release
 * @create 2019/6/26
 * @Describe
 */
class HomeModel : CommonModel(), HomeContract.Model {

    override fun requestBanner(): Observable<HttpResult<List<Banner>>> {
        return RetrofitHelper.service.getBanners()
    }

    override fun requestTopArticles(): Observable<HttpResult<MutableList<Article>>> {
        return RetrofitHelper.service.getTopArticles()
    }

    override fun requestArticles(num: Int): Observable<HttpResult<ArticleResponseBody>> {
        return RetrofitHelper.service.getArticles(num)
    }

}