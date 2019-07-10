package com.release.mvp_kt.mvp.contract

import com.release.mvp_kt.mvp.model.bean.ArticleResponseBody
import com.release.mvp_kt.mvp.model.bean.HttpResult
import io.reactivex.Observable
/**
 * @author Mr.release
 * @create 2019/7/1
 * @Describe
 */
interface SearchListContract {

    interface View : CommonContract.View {

        fun showArticles(articles: ArticleResponseBody)

        fun scrollToTop()

    }

    interface Presenter : CommonContract.Presenter<View> {

        fun queryBySearchKey(page: Int, key: String)

    }

    interface Model : CommonContract.Model {

        fun queryBySearchKey(page: Int, key: String): Observable<HttpResult<ArticleResponseBody>>

    }

}