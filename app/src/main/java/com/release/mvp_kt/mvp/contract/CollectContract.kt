package com.release.mvp_kt.mvp.contract

import com.release.mvp_kt.base.IModel
import com.release.mvp_kt.base.IPresenter
import com.release.mvp_kt.base.IView
import com.release.mvp_kt.mvp.model.bean.CollectionArticle
import com.release.mvp_kt.mvp.model.bean.CollectionResponseBody
import com.release.mvp_kt.mvp.model.bean.HttpResult
import io.reactivex.Observable

/**
 * @author Mr.release
 * @create 2019/7/1
 * @Describe
 */
interface CollectContract {

    interface View : IView {

        fun setCollectList(articles: CollectionResponseBody<CollectionArticle>)

        fun showRemoveCollectSuccess(success: Boolean)

        fun scrollToTop()

    }

    interface Presenter : IPresenter<View> {

        fun getCollectList(page: Int)

        fun removeCollectArticle(id: Int, originId: Int)

    }

    interface Model : IModel {

        fun getCollectList(page: Int): Observable<HttpResult<CollectionResponseBody<CollectionArticle>>>

        fun removeCollectArticle(id: Int, originId: Int): Observable<HttpResult<Any>>

    }

}