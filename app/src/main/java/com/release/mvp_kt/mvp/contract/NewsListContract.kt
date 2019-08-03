package com.release.mvp_kt.mvp.contract

import com.release.mvp_kt.base.IPresenter
import com.release.mvp_kt.base.IView
import com.release.mvp_kt.mvp.model.NewsInfoBean
import com.release.mvp_kt.ui.adpater.item.NewsMultiItem


/**
 * @author Mr.release
 * @create 2019/6/26
 * @Describe
 */
interface NewsListContract {

    interface View : IView {

        fun loadAdData(data: NewsInfoBean)

        fun loadData(data: List<NewsMultiItem>)
    }

    interface Presenter : IPresenter<View> {

        fun requestData(newsId: String, page: Int,isRefresh :Boolean)
    }
}