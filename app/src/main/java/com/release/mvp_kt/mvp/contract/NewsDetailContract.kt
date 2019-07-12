package com.release.mvp_kt.mvp.contract

import com.release.mvp_kt.base.IModel
import com.release.mvp_kt.base.IPresenter
import com.release.mvp_kt.base.IView
import com.release.mvp_kt.mvp.model.bean.NewsDetailInfoBean
import com.release.mvp_kt.mvp.model.bean.NewsInfoBean
import com.release.mvp_kt.ui.adpater.item.NewsMultiItem
import io.reactivex.Flowable


/**
 * @author Mr.release
 * @create 2019/6/26
 * @Describe
 */
interface NewsDetailContract {

    interface View : IView {

        fun loadData(data: NewsDetailInfoBean)
    }

    interface Presenter : IPresenter<View> {

        fun requestData(newsId: String)
    }

    interface Model : IModel {

        fun requestData(newsId: String): Flowable<NewsDetailInfoBean>
    }
}